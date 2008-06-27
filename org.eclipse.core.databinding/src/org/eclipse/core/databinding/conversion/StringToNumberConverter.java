/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.core.databinding.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParser;
import org.eclipse.core.internal.databinding.conversion.StringToNumberParser.ParseResult;
import org.eclipse.core.internal.databinding.validation.NumberFormatConverter;

import com.ibm.icu.text.NumberFormat;

/**
 * Converts a String to a Number using <code>NumberFormat.parse(...)</code>.
 * This class is thread safe.
 * 
 * @since 1.0
 */
public class StringToNumberConverter extends NumberFormatConverter {
	private Class toType;
	/**
	 * NumberFormat instance to use for conversion. Access must be synchronized.
	 */
	private NumberFormat numberFormat;

	/**
	 * Minimum possible value for the type. Can be <code>null</code> as
	 * BigInteger doesn't have bounds.
	 */
	private final Number min;
	/**
	 * Maximum possible value for the type. Can be <code>null</code> as
	 * BigInteger doesn't have bounds.
	 */
	private final Number max;

	/**
	 * The boxed type of the toType;
	 */
	private final Class boxedType;

	private static final Integer MIN_INTEGER = new Integer(Integer.MIN_VALUE);
	private static final Integer MAX_INTEGER = new Integer(Integer.MAX_VALUE);

	private static final Double MIN_DOUBLE = new Double(-Double.MAX_VALUE);
	private static final Double MAX_DOUBLE = new Double(Double.MAX_VALUE);

	private static final Long MIN_LONG = new Long(Long.MIN_VALUE);
	private static final Long MAX_LONG = new Long(Long.MIN_VALUE);

	private static final Float MIN_FLOAT = new Float(-Float.MAX_VALUE);
	private static final Float MAX_FLOAT = new Float(Float.MAX_VALUE);

	/**
	 * @param numberFormat
	 * @param toType
	 * @param min
	 *            minimum possible value for the type, can be <code>null</code>
	 *            as BigInteger doesn't have bounds
	 * @param max
	 *            maximum possible value for the type, can be <code>null</code>
	 *            as BigInteger doesn't have bounds
	 * @param boxedType
	 *            a convenience that allows for the checking against one type
	 *            rather than boxed and unboxed types
	 */
	private StringToNumberConverter(NumberFormat numberFormat, Class toType,
			Number min, Number max, Class boxedType) {
		super(String.class, toType, numberFormat);

		this.toType = toType;
		this.numberFormat = numberFormat;
		this.min = min;
		this.max = max;
		this.boxedType = boxedType;
	}

	/**
	 * Converts the provided <code>fromObject</code> to the requested
	 * {@link #getToType() to type}.
	 * 
	 * @see org.eclipse.core.databinding.conversion.IConverter#convert(java.lang.Object)
	 * @throws IllegalArgumentException
	 *             if the value isn't in the format required by the NumberFormat
	 *             or the value is out of range for the
	 *             {@link #getToType() to type}.
	 * @throws IllegalArgumentException
	 *             if conversion was not possible
	 */
	public Object convert(Object fromObject) {
		ParseResult result = StringToNumberParser.parse(fromObject,
				numberFormat, toType.isPrimitive());

		if (result.getPosition() != null) {
			// this shouldn't happen in the pipeline as validation should catch
			// it but anyone can call convert so we should return a properly
			// formatted message in an exception
			throw new IllegalArgumentException(StringToNumberParser
					.createParseErrorMessage((String) fromObject, result
							.getPosition()));
		} else if (result.getNumber() == null) {
			// if an error didn't occur and the number is null then it's a boxed
			// type and null should be returned
			return null;
		}

		/*
		 * Technically the checks for ranges aren't needed here because the
		 * validator should have validated this already but we shouldn't assume
		 * this has occurred.
		 */
		if (Integer.class.equals(boxedType)) {
			if (StringToNumberParser.inIntegerRange(result.getNumber())) {
				return new Integer(result.getNumber().intValue());
			}
		} else if (Double.class.equals(boxedType)) {
			if (StringToNumberParser.inDoubleRange(result.getNumber())) {
				return new Double(result.getNumber().doubleValue());
			}
		} else if (Long.class.equals(boxedType)) {
			if (StringToNumberParser.inLongRange(result.getNumber())) {
				return new Long(result.getNumber().longValue());
			}
		} else if (Float.class.equals(boxedType)) {
			if (StringToNumberParser.inFloatRange(result.getNumber())) {
				return new Float(result.getNumber().floatValue());
			}
		} else if (BigInteger.class.equals(boxedType)) {
			return new BigDecimal(result.getNumber().doubleValue())
					.toBigInteger();
		}

		if (min != null && max != null) {
			throw new IllegalArgumentException(StringToNumberParser
					.createOutOfRangeMessage(min, max, numberFormat));
		}

		/*
		 * Fail safe. I don't think this could even be thrown but throwing the
		 * exception is better than returning null and hiding the error.
		 */
		throw new IllegalArgumentException(
				"Could not convert [" + fromObject + "] to type [" + toType + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * @param primitive
	 *            <code>true</code> if the convert to type is an int
	 * @return to Integer converter for the default locale
	 */
	public static StringToNumberConverter toInteger(boolean primitive) {
		return toInteger(NumberFormat.getIntegerInstance(), primitive);
	}

	/**
	 * @param numberFormat
	 * @param primitive
	 * @return to Integer converter with the provided numberFormat
	 */
	public static StringToNumberConverter toInteger(NumberFormat numberFormat,
			boolean primitive) {
		return new StringToNumberConverter(numberFormat,
				(primitive) ? Integer.TYPE : Integer.class, MIN_INTEGER,
				MAX_INTEGER, Integer.class);
	}

	/**
	 * @param primitive
	 *            <code>true</code> if the convert to type is a double
	 * @return to Double converter for the default locale
	 */
	public static StringToNumberConverter toDouble(boolean primitive) {
		return toDouble(NumberFormat.getNumberInstance(), primitive);
	}

	/**
	 * @param numberFormat
	 * @param primitive
	 * @return to Double converter with the provided numberFormat
	 */
	public static StringToNumberConverter toDouble(NumberFormat numberFormat,
			boolean primitive) {
		return new StringToNumberConverter(numberFormat,
				(primitive) ? Double.TYPE : Double.class, MIN_DOUBLE,
				MAX_DOUBLE, Double.class);
	}

	/**
	 * @param primitive
	 *            <code>true</code> if the convert to type is a long
	 * @return to Long converter for the default locale
	 */
	public static StringToNumberConverter toLong(boolean primitive) {
		return toLong(NumberFormat.getIntegerInstance(), primitive);
	}

	/**
	 * @param numberFormat
	 * @param primitive
	 * @return to Long converter with the provided numberFormat
	 */
	public static StringToNumberConverter toLong(NumberFormat numberFormat,
			boolean primitive) {
		return new StringToNumberConverter(numberFormat,
				(primitive) ? Long.TYPE : Long.class, MIN_LONG, MAX_LONG,
				Long.class);
	}

	/**
	 * @param primitive
	 *            <code>true</code> if the convert to type is a float
	 * @return to Float converter for the default locale
	 */
	public static StringToNumberConverter toFloat(boolean primitive) {
		return toFloat(NumberFormat.getNumberInstance(), primitive);
	}

	/**
	 * @param numberFormat
	 * @param primitive
	 * @return to Float converter with the provided numberFormat
	 */
	public static StringToNumberConverter toFloat(NumberFormat numberFormat,
			boolean primitive) {
		return new StringToNumberConverter(numberFormat,
				(primitive) ? Float.TYPE : Float.class, MIN_FLOAT, MAX_FLOAT,
				Float.class);
	}

	/**
	 * @return to BigInteger converter for the default locale
	 */
	public static StringToNumberConverter toBigInteger() {
		return toBigInteger(NumberFormat.getIntegerInstance());
	}

	/**
	 * @param numberFormat
	 * @return to BigInteger converter with the provided numberFormat
	 */
	public static StringToNumberConverter toBigInteger(NumberFormat numberFormat) {
		return new StringToNumberConverter(numberFormat, BigInteger.class,
				null, null, BigInteger.class);
	}
}

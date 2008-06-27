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

package org.eclipse.core.tests.internal.databinding.validation;

import org.eclipse.core.internal.databinding.conversion.NumberToByteConverter;
import org.eclipse.core.internal.databinding.validation.NumberToByteValidator;
import org.eclipse.core.internal.databinding.validation.NumberToNumberValidator;

import com.ibm.icu.text.NumberFormat;

/**
 * @since 1.1
 */
public class NumberToByteValidatorTest extends NumberToNumberValidatorTestHarness {
	/* (non-Javadoc)
	 * @see org.eclipse.core.tests.internal.databinding.validation.NumberToNumberValidatorTestHarness#doGetOutOfRangeNumber()
	 */
	protected Number doGetOutOfRangeNumber() {
		return new Integer(Byte.MAX_VALUE + 1);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.tests.internal.databinding.validation.NumberToNumberValidatorTestHarness#doGetToBoxedTypeValidator()
	 */
	protected NumberToNumberValidator doGetToBoxedTypeValidator(Class fromType) {
		NumberToByteConverter converter = new NumberToByteConverter(NumberFormat.getInstance(),
				fromType, false);
		return new NumberToByteValidator(converter);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.tests.internal.databinding.validation.NumberToNumberValidatorTestHarness#doGetToPrimitiveValidator()
	 */
	protected NumberToNumberValidator doGetToPrimitiveValidator(Class fromType) {
		NumberToByteConverter converter = new NumberToByteConverter(NumberFormat.getInstance(),
				fromType, true);
		return new NumberToByteValidator(converter);
	}
}

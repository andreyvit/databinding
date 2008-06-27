/*******************************************************************************
 * Copyright (c) 2007, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Matthew Hall - bug 213145
 *******************************************************************************/

package org.eclipse.jface.databinding.conformance.swt;

import junit.framework.Test;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.jface.databinding.conformance.ObservableValueContractTest;
import org.eclipse.jface.databinding.conformance.delegate.IObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.util.DelegatingRealm;
import org.eclipse.jface.databinding.conformance.util.SuiteBuilder;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Display;

/**
 * Tests for IObservableValue that don't mutate the value.
 * <p>
 * This class is experimental and can change at any time. It is recommended to
 * not subclass or assume the test names will not change. The only API that is
 * guaranteed to not change are the constructors. The tests will remain public
 * and not final in order to allow for consumers to turn off a test if needed by
 * subclassing.
 * </p>
 * 
 * @since 3.2
 */
public class SWTObservableValueContractTest extends ObservableValueContractTest {
	private IObservableValueContractDelegate delegate;

	/**
	 * @param delegate
	 */
	public SWTObservableValueContractTest(
			IObservableValueContractDelegate delegate) {
		this(null, delegate);
	}

	public SWTObservableValueContractTest(String testName,
			IObservableValueContractDelegate delegate) {
		super(testName, delegate);
		this.delegate = delegate;
	}

	/**
	 * Creates a new observable passing the realm for the current display.
	 * @return observable
	 */
	protected IObservable doCreateObservable() {
		Display display = Display.getCurrent();
		if (display == null) {
			display = new Display();
		}
		DelegatingRealm delegateRealm = new DelegatingRealm(SWTObservables
				.getRealm(display));
		delegateRealm.setCurrent(true);

		return delegate.createObservable(delegateRealm);
	}

	public static Test suite(IObservableValueContractDelegate delegate) {
		return new SuiteBuilder().addObservableContractTest(SWTObservableValueContractTest.class, delegate).build();
	}
}

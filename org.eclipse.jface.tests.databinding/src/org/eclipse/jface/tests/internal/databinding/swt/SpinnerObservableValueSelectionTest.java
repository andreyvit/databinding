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

package org.eclipse.jface.tests.internal.databinding.swt;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.conformance.ObservableDelegateTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.swt.SWTMutableObservableValueContractTest;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.internal.databinding.swt.SWTProperties;
import org.eclipse.jface.internal.databinding.swt.SpinnerObservableValue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

/**
 * @since 3.2
 */
public class SpinnerObservableValueSelectionTest extends ObservableDelegateTest {
	private Delegate delegate;

	private Spinner spinner;

	private IObservableValue observable;
	
	public SpinnerObservableValueSelectionTest() {
		this(null);
	}

	public SpinnerObservableValueSelectionTest(String testName) {
		super(testName, new Delegate());
	}

	protected void setUp() throws Exception {
		super.setUp();

		delegate = (Delegate) getObservableContractDelegate();
		observable = (IObservableValue) getObservable();
		spinner = delegate.spinner;
	}

	protected IObservable doCreateObservable() {
		return getObservableContractDelegate().createObservable(SWTObservables.getRealm(Display.getDefault()));
	}

	public void testGetValue() throws Exception {
		int value = 100;
		spinner.setSelection(value);
		assertEquals(new Integer(value), observable.getValue());
	}

	public void testSetValue() throws Exception {
		int value = 100;
		observable.setValue(new Integer(value));
		assertEquals(value, spinner.getSelection());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(SpinnerObservableValueSelectionTest.class.toString());
		suite.addTestSuite(SpinnerObservableValueSelectionTest.class);
		suite.addTest(SWTMutableObservableValueContractTest.suite(new Delegate()));
		return suite;
	}

	/* package */static class Delegate extends
			AbstractObservableValueContractDelegate {
		private Shell shell;

		Spinner spinner;

		public void setUp() {
			shell = new Shell();
			spinner = new Spinner(shell, SWT.NONE);
			spinner.setMaximum(1000);
		}

		public void tearDown() {
			shell.dispose();
		}

		public IObservableValue createObservableValue(Realm realm) {
			return new SpinnerObservableValue(realm, spinner, SWTProperties.SELECTION);
		}

		public void change(IObservable observable) {
			spinner
					.setSelection(createIntegerValue(
							(IObservableValue) observable).intValue());
			spinner.notifyListeners(SWT.Selection, null);
		}

		public Object getValueType(IObservableValue observable) {
			return Integer.TYPE;
		}

		public Object createValue(IObservableValue observable) {
			return createIntegerValue(observable);
		}

		private Integer createIntegerValue(IObservableValue observable) {
			return new Integer(((Integer) observable.getValue()).intValue() + 1);
		}
	}
}

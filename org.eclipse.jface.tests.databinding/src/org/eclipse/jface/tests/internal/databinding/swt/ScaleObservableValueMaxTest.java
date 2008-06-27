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
import org.eclipse.jface.internal.databinding.swt.ScaleObservableValue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

/**
 * @since 3.2
 */
public class ScaleObservableValueMaxTest extends ObservableDelegateTest {
	private Delegate delegate;

	private Scale scale;

	private IObservableValue observable;

	public ScaleObservableValueMaxTest() {
		this(null);
	}

	public ScaleObservableValueMaxTest(String testName) {
		super(testName, new Delegate());
	}

	protected void setUp() throws Exception {
		super.setUp();

		delegate = (Delegate) getObservableContractDelegate();
		observable = (IObservableValue) getObservable();
		scale = delegate.scale;
	}

	protected IObservable doCreateObservable() {
		return getObservableContractDelegate().createObservable(
				SWTObservables.getRealm(Display.getDefault()));
	}

	public void testGetValue() throws Exception {
		int max = 100;
		scale.setMaximum(max);
		assertEquals(new Integer(max), observable.getValue());
	}

	public void testSetValue() throws Exception {
		int max = 100;
		observable.setValue(new Integer(max));
		assertEquals(max, scale.getMaximum());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(ScaleObservableValueMaxTest.class.toString());
		suite.addTestSuite(ScaleObservableValueMaxTest.class);
		suite.addTest(SWTMutableObservableValueContractTest.suite(new Delegate()));
		return suite;
	}

	/* package */static class Delegate extends
			AbstractObservableValueContractDelegate {
		private Shell shell;

		Scale scale;

		public void setUp() {
			shell = new Shell();
			scale = new Scale(shell, SWT.NONE);
			scale.setMaximum(1000);
		}

		public void tearDown() {
			shell.dispose();
		}

		public IObservableValue createObservableValue(Realm realm) {
			return new ScaleObservableValue(realm, scale, SWTProperties.MAX);
		}

		public void change(IObservable observable) {
			IObservableValue observableValue = (IObservableValue) observable;
			observableValue.setValue(createValue(observableValue));
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

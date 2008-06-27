/*******************************************************************************
 * Copyright (c) 2007 Matthew Hall and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthew Hall - initial API and implementation (bug 212235)
 *     Matthew Hall - bug 213145
 ******************************************************************************/

package org.eclipse.jface.tests.internal.databinding.swt;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.swt.SWTMutableObservableValueContractTest;
import org.eclipse.jface.databinding.conformance.util.ValueChangeEventTracker;
import org.eclipse.jface.internal.databinding.swt.ShellObservableValue;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;
import org.eclipse.swt.widgets.Shell;

/**
 * Tests for ShellObservableValue
 * 
 * @since 1.2
 */
public class ShellObservableValueTest extends AbstractDefaultRealmTestCase {
	String oldValue;
	String newValue;
	Shell shell;
	ShellObservableValue observable;
	ValueChangeEventTracker tracker;

	protected void setUp() throws Exception {
		super.setUp();
		shell = new Shell();
		observable = new ShellObservableValue(shell);
		oldValue = "old";
		newValue = "new";
		shell.setText(oldValue);
		tracker = ValueChangeEventTracker.observe(observable);
	}

	public void tearDown() throws Exception {
		observable.dispose();
		observable = null;
		shell.dispose();
		shell = null;
		super.tearDown();
	}

	public void testGetValueType() {
		assertEquals(String.class, observable.getValueType());
	}

	public void testSetValue_FiresValueChangeEvent() {
		observable.setValue(newValue);

		assertEquals(1, tracker.count);
		assertEquals(oldValue, tracker.event.diff.getOldValue());
		assertEquals(newValue, tracker.event.diff.getNewValue());
	}

	public void testSetValue_NullConvertedToEmptyString() {
		observable.setValue(null);

		assertEquals("", observable.getValue());
		assertEquals("", shell.getText());
	}

	public void testShellSetText_GetValueReturnsSame() {
		assertEquals(oldValue, observable.getValue());

		shell.setText(newValue);

		assertEquals(newValue, observable.getValue());
	}

	public void testShellSetText_NoValueChangeEvent() {
		shell.setText(newValue);
		assertEquals(0, tracker.count);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(ShellObservableValueTest.class.toString());
		suite.addTestSuite(ShellObservableValueTest.class);
		suite.addTest(SWTMutableObservableValueContractTest.suite(new Delegate()));
		return suite;
	}

	static class Delegate extends AbstractObservableValueContractDelegate {
		Shell shell;

		public void setUp() {
			super.setUp();
			shell = new Shell();
		}

		public void tearDown() {
			shell.dispose();
			shell = null;
			super.tearDown();
		}

		public IObservableValue createObservableValue(Realm realm) {
			return new ShellObservableValue(realm, shell);
		}

		public Object getValueType(IObservableValue observable) {
			return String.class;
		}

		public void change(IObservable observable) {
			IObservableValue observableValue = (IObservableValue) observable;
			observableValue.setValue(createValue(observableValue));
		}

		int counter;

		public Object createValue(IObservableValue observable) {
			return Integer.toString(counter++);
		}
	}
}

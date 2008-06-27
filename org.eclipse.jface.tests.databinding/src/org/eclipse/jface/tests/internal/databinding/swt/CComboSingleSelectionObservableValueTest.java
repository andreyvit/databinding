/*******************************************************************************
 * Copyright (c) 2007 Brad Reynolds and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Brad Reynolds - initial API and implementation
 *     Ashley Cambrell - bug 198903
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
import org.eclipse.jface.databinding.swt.ISWTObservable;
import org.eclipse.jface.internal.databinding.swt.CComboSingleSelectionObservableValue;
import org.eclipse.jface.tests.databinding.AbstractSWTTestCase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Shell;

/**
 * @since 3.2
 */
public class CComboSingleSelectionObservableValueTest extends AbstractSWTTestCase {
	public void testSetValue() throws Exception {
		CCombo combo = new CCombo(getShell(), SWT.NONE);
		CComboSingleSelectionObservableValue observableValue = new CComboSingleSelectionObservableValue(
				combo);
		combo.add("Item1");
		combo.add("Item2");

		assertEquals(-1, combo.getSelectionIndex());
		assertEquals(-1, ((Integer) observableValue.getValue()).intValue());

		Integer value = new Integer(1);
		observableValue.setValue(value);
		assertEquals("combo selection index", value.intValue(), combo
				.getSelectionIndex());
		assertEquals("observable value", value, observableValue.getValue());

		assertEquals("Item2", combo.getText());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(CComboSingleSelectionObservableValueTest.class.getName());
		suite.addTestSuite(CComboSingleSelectionObservableValueTest.class);
		suite.addTest(SWTMutableObservableValueContractTest.suite(new Delegate()));
		return suite;
	}

	/* package */static class Delegate extends
			AbstractObservableValueContractDelegate {
		private CCombo combo;
		private Shell shell;

		public void setUp() {
			shell = new Shell();
			combo = new CCombo(shell, SWT.NONE);
			combo.add("0");
			combo.add("1");
		}

		public void tearDown() {
			shell.dispose();
		}

		public IObservableValue createObservableValue(Realm realm) {
			return new CComboSingleSelectionObservableValue(realm, combo);
		}

		public void change(IObservable observable) {
			int index = _createValue((IObservableValue) observable);
			combo.select(index);
			combo.notifyListeners(SWT.Selection, null);
		}

		public Object getValueType(IObservableValue observable) {
			return Integer.TYPE;
		}

		public Object createValue(IObservableValue observable) {
			return new Integer(_createValue(observable));
		}
		
		private int _createValue(IObservableValue observable) {
			CCombo combo = ((CCombo) ((ISWTObservable) observable).getWidget());
			int value = Math.max(0, combo.getSelectionIndex());
			
			//returns either 0 or 1 depending upon current value
			return Math.abs(value - 1);
		}
	}
}

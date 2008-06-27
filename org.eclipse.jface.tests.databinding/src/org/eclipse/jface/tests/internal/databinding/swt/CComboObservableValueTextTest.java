/*******************************************************************************
 * Copyright (c) 2006 Brad Reynolds and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Brad Reynolds - initial API and implementation
 *     Matthew Hall - bug 213145
 ******************************************************************************/

package org.eclipse.jface.tests.internal.databinding.swt;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.swt.SWTMutableObservableValueContractTest;
import org.eclipse.jface.databinding.conformance.util.ValueChangeEventTracker;
import org.eclipse.jface.databinding.swt.ISWTObservable;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.internal.databinding.swt.CComboObservableValue;
import org.eclipse.jface.internal.databinding.swt.SWTProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @since 3.2
 */
public class CComboObservableValueTextTest extends TestCase {
	private Delegate delegate;

	private CCombo combo;

	protected void setUp() throws Exception {
		super.setUp();

		delegate = new Delegate();
		delegate.setUp();
		combo = delegate.combo;
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		delegate.tearDown();
	}

	public void testModify_NotifiesObservable() throws Exception {
		IObservableValue observable = delegate
				.createObservableValue(SWTObservables.getRealm(Display
						.getDefault()));
		ValueChangeEventTracker listener = ValueChangeEventTracker
				.observe(observable);

		combo.setText((String) delegate.createValue(observable));

		assertEquals("Observable was not notified.", 1, listener.count);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(CComboObservableValueTextTest.class.getName());
		suite.addTestSuite(CComboObservableValueTextTest.class);
		suite.addTest(SWTMutableObservableValueContractTest.suite(new Delegate()));
		return suite;
	}

	/* package */static class Delegate extends
			AbstractObservableValueContractDelegate {
		/* package */CCombo combo;

		private Shell shell;

		public void setUp() {
			shell = new Shell();
			combo = new CCombo(shell, SWT.NONE);
		}

		public void tearDown() {
			shell.dispose();
		}

		public IObservableValue createObservableValue(Realm realm) {
			return new CComboObservableValue(realm, combo, SWTProperties.TEXT);
		}

		public void change(IObservable observable) {
			CCombo combo = (CCombo) ((ISWTObservable) observable).getWidget();
			combo.setText(combo.getText() + "a");
		}

		public Object getValueType(IObservableValue observable) {
			return String.class;
		}

		public Object createValue(IObservableValue observable) {
			return observable.getValue() + "a";
		}
	}
}

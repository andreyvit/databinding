/*******************************************************************************
 * Copyright (c) 2007-2008 Matthew Hall and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthew Hall - initial API and implementation (bug 212518)
 ******************************************************************************/

package org.eclipse.core.tests.internal.databinding.observable;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.observable.ConstantObservableValue;
import org.eclipse.jface.databinding.conformance.ObservableValueContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.delegate.IObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.util.SuiteBuilder;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests for ConstantObservableValue
 * 
 * @since 1.1
 */
public class ConstantObservableValueTest extends AbstractDefaultRealmTestCase {
	public void testConstructor_NullRealm() {
		try {
			new ConstantObservableValue(null, null, null);
			fail("Constructor should throw an exception when null realm is passed in");
		} catch (RuntimeException expected) {
		}
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("ConstantValueTest");
		suite.addTestSuite(ConstantObservableValueTest.class);
		suite.addTest(UnchangeableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
	}

	private static class Delegate extends
			AbstractObservableValueContractDelegate {
		public IObservableValue createObservableValue(Realm realm) {
			return new ConstantObservableValue(realm, new Object(),
					Object.class);
		}

		public Object getValueType(IObservableValue observable) {
			return Object.class;
		}
	}

	/**
	 * Non-API--this class is public so that SuiteBuilder can access it.
	 */
	public static class UnchangeableObservableValueContractTest extends
			ObservableValueContractTest {
		public UnchangeableObservableValueContractTest(String name,
				IObservableValueContractDelegate delegate) {
			super(name, delegate);
		}

		public void testChange_OrderOfNotifications() {
			// disabled
		}

		public void testChange_ValueChangeEvent() {
			// disabled
		}

		public void testChange_ValueChangeEventDiff() {
			// disabled
		}

		public void testChange_ValueChangeEventFiredAfterValueIsSet() {
			// disabled
		}

		public void testRemoveValueChangeListener_RemovesListener()
				throws Exception {
			// disabled
		}

		public void testChange_ChangeEvent() {
			// disabled
		}

		public void testChange_EventObservable() {
			// disabled
		}

		public void testChange_ObservableRealmIsTheCurrentRealm() {
			// disabled
		}

		public void testChange_RealmCheck() {
			// disabled
		}

		public void testRemoveChangeListener_RemovesListener() {
			// disabled
		}

		public void testIsStale_RealmChecks() {
			// disabled
		}

		public void testIsStale_GetterCalled() throws Exception {
			// disabled
		}

		public static Test suite(IObservableValueContractDelegate delegate) {
			return new SuiteBuilder().addObservableContractTest(
					UnchangeableObservableValueContractTest.class, delegate)
					.build();
		}
	}
}

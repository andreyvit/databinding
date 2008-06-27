/*******************************************************************************
 * Copyright (c) 2007 Matthew Hall and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthew Hall - initial API and implementation (bug 212223)
 *     Matthew Hall - bug 213145
 ******************************************************************************/

package org.eclipse.jface.tests.internal.databinding.swt;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.swt.SWTMutableObservableValueContractTest;
import org.eclipse.jface.databinding.conformance.util.ValueChangeEventTracker;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.internal.databinding.provisional.swt.AbstractSWTObservableValue;
import org.eclipse.jface.internal.databinding.swt.DelayedObservableValue;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

/**
 * Tests for DelayedObservableValue
 * 
 * @since 1.2
 */
public class DelayedObservableValueTest extends AbstractDefaultRealmTestCase {
	private Display display;
	private Shell shell;
	private Object oldValue;
	private Object newValue;
	private SWTObservableValueStub target;
	private DelayedObservableValue delayed;

	protected void setUp() throws Exception {
		super.setUp();
		display = Display.getCurrent();
		shell = new Shell(display);
		target = new SWTObservableValueStub(SWTObservables.getRealm(display),
				shell);
		oldValue = new Object();
		newValue = new Object();
		target.setValue(oldValue);
		delayed = new DelayedObservableValue(1, target);
	}

	protected void tearDown() throws Exception {
		target.dispose();
		target = null;
		shell.dispose();
		shell = null;
		display = null;
		super.tearDown();
	}

	public void testIsStale_WhenTargetIsStale() {
		assertFalse(target.isStale());
		assertFalse(delayed.isStale());

		target.fireStale();

		assertTrue(target.isStale());
		assertTrue(delayed.isStale());
	}

	public void testIsStale_DuringDelay() {
		assertFalse(target.isStale());
		assertFalse(delayed.isStale());

		target.setValue(newValue);

		assertFalse(target.isStale());
		assertTrue(delayed.isStale());
	}

	public void testGetValueType_SameAsTarget() {
		assertEquals(target.getValueType(), delayed.getValueType());
	}

	public void testGetValue_FiresPendingValueChange() {
		assertFiresPendingValueChange(new Runnable() {
			public void run() {
				final Object value = delayed.getValue();
				assertEquals(newValue, value);
			}
		});
	}

	public void testFocusOut_FiresPendingValueChange() {
		assertFiresPendingValueChange(new Runnable() {
			public void run() {
				// simulate focus-out event
				shell.notifyListeners(SWT.FocusOut, new Event());
			}
		});
	}

	public void testSetValue_PropagatesToTarget() {
		assertEquals(oldValue, delayed.getValue());
		assertEquals(oldValue, target.getValue());

		delayed.setValue(newValue);

		assertEquals(newValue, target.getValue());
		assertEquals(newValue, delayed.getValue());
	}

	public void testSetValue_CachesGetValueFromTarget() {
		Object overrideValue = target.overrideValue = new Object();

		assertEquals(oldValue, delayed.getValue());
		assertEquals(oldValue, target.getValue());

		delayed.setValue(newValue);

		assertEquals(overrideValue, target.getValue());
		assertEquals(overrideValue, delayed.getValue());
	}

	public void testSetValue_FiresValueChangeEvent() {
		ValueChangeEventTracker targetTracker = ValueChangeEventTracker
				.observe(target);
		ValueChangeEventTracker delayedTracker = ValueChangeEventTracker
				.observe(delayed);

		delayed.setValue(newValue);

		assertEquals(1, targetTracker.count);
		assertEquals(oldValue, targetTracker.event.diff.getOldValue());
		assertEquals(newValue, targetTracker.event.diff.getNewValue());

		assertEquals(1, delayedTracker.count);
		assertEquals(oldValue, delayedTracker.event.diff.getOldValue());
		assertEquals(newValue, delayedTracker.event.diff.getNewValue());
	}

	public void testWait_FiresPendingValueChange() {
		assertFiresPendingValueChange(new Runnable() {
			public void run() {
				// Give plenty of time for display to run timer task
				long timeout = time() + 5000;
				do {
					sleep(10);
					processDisplayEvents();
				} while (delayed.isStale() && time() < timeout);
			}

			private void sleep(int delay) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

			private void processDisplayEvents() {
				while (display.readAndDispatch()) {
				}
			}

			private long time() {
				return System.currentTimeMillis();
			}
		});
	}

	private void assertFiresPendingValueChange(Runnable runnable) {
		ValueChangeEventTracker tracker = ValueChangeEventTracker
				.observe(delayed);

		target.setValue(newValue);
		assertTrue(delayed.isStale());
		assertEquals(0, tracker.count);

		runnable.run();

		assertFalse(delayed.isStale());
		assertEquals(1, tracker.count);
		assertEquals(oldValue, tracker.event.diff.getOldValue());
		assertEquals(newValue, tracker.event.diff.getNewValue());
	}

	static class SWTObservableValueStub extends AbstractSWTObservableValue {
		private Object value;
		private boolean stale;

		Object overrideValue;

		public SWTObservableValueStub(Realm realm, Widget widget) {
			super(realm, widget);
		}

		protected Object doGetValue() {
			return value;
		}

		protected void doSetValue(Object value) {
			Object oldValue = this.value;
			if (overrideValue != null)
				value = overrideValue;
			this.value = value;
			stale = false;
			fireValueChange(Diffs.createValueDiff(oldValue, value));
		}

		public Object getValueType() {
			return Object.class;
		}

		protected void fireStale() {
			stale = true;
			super.fireStale();
		}

		public boolean isStale() {
			return stale;
		}
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(DelayedObservableValueTest.class.getName());
		suite.addTestSuite(DelayedObservableValueTest.class);
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
			return new DelayedObservableValue(0, new SWTObservableValueStub(
					realm, shell));
		}

		public Object getValueType(IObservableValue observable) {
			return Object.class;
		}

		public void change(IObservable observable) {
			IObservableValue observableValue = (IObservableValue) observable;
			observableValue.setValue(createValue(observableValue));
		}

		public Object createValue(IObservableValue observable) {
			return new Object();
		}
	}
}

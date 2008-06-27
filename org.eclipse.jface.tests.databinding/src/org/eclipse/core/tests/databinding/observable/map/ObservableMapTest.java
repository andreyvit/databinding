/*******************************************************************************
 * Copyright (c) 2006, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.core.tests.databinding.observable.map;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.eclipse.core.databinding.observable.map.IMapChangeListener;
import org.eclipse.core.databinding.observable.map.MapChangeEvent;
import org.eclipse.core.databinding.observable.map.MapDiff;
import org.eclipse.core.databinding.observable.map.ObservableMap;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.databinding.conformance.util.RealmTester;

/**
 * @since 3.2
 * 
 */
public class ObservableMapTest extends TestCase {
	ObservableMapStub map;

	protected void setUp() throws Exception {
		RealmTester.setDefault(new CurrentRealm(true));
		map = new ObservableMapStub(new HashMap());
	}

	protected void tearDown() throws Exception {
		RealmTester.setDefault(null);
	}

	public void testDisposeMapChangeListeners() throws Exception {
		class MapChangeListener implements IMapChangeListener {
			int count;

			public void handleMapChange(MapChangeEvent event) {
				count++;
			}
		}

		MapChangeListener listener = new MapChangeListener();
		map.addMapChangeListener(listener);

		assertEquals(0, listener.count);
		map.fireMapChange(null);
		assertEquals(1, listener.count);

		map.dispose();
		try {
			map.fireMapChange(null);
		} catch (Exception e) {
			// do nothing
		}

		assertEquals("listener should not have been notified", 1,
				listener.count);
	}

	public void testIsStaleRealmChecks() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			public void run() {
				map.isStale();
			}
		});
	}

	public void testSetStaleRealmChecks() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			public void run() {
				map.setStale(true);
			}
		});
	}

	public void testFireMapChangeRealmChecks() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			public void run() {
				map.fireMapChange(null);
			}
		});
	}

	static class ObservableMapStub extends ObservableMap {
		/**
		 * @param wrappedMap
		 */
		public ObservableMapStub(Map wrappedMap) {
			super(wrappedMap);
		}

		protected void fireMapChange(MapDiff diff) {
			super.fireMapChange(diff);
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2006 Brad Reynolds and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Brad Reynolds - initial API and implementation
 ******************************************************************************/

package org.eclipse.core.tests.databinding.observable.map;

import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.core.databinding.observable.map.AbstractObservableMap;
import org.eclipse.core.databinding.observable.map.MapDiff;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.databinding.conformance.util.RealmTester;

/**
 * @since 3.2
 */
public class AbstractObservableMapTest extends TestCase {
	private AbstractObservableMapStub map;

	protected void setUp() throws Exception {
		RealmTester.setDefault(new CurrentRealm(true));
		map = new AbstractObservableMapStub();
	}
	
	protected void tearDown() throws Exception {
		RealmTester.setDefault(null);
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
	
	public void testFireStaleRealmChecks() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() { 
			public void run() {
				map.fireStale();
			}
		});
	}
	
	public void testFireChangeRealmChecks() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			public void run() {
				map.fireChange();
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
	
	static class AbstractObservableMapStub extends AbstractObservableMap {
		public Set entrySet() {
			return null;
		}
		
		protected void fireChange() {
			super.fireChange();
		}
		
		protected void fireMapChange(MapDiff diff) {
			super.fireMapChange(diff);
		}
		
		protected void fireStale() {
			super.fireStale();
		}
	}
}

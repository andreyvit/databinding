/*******************************************************************************
 * Copyright (c) 2007, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.core.tests.databinding.beans;

import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.beans.JavaBeanObservableList;
import org.eclipse.core.internal.databinding.beans.JavaBeanObservableMap;
import org.eclipse.core.internal.databinding.beans.JavaBeanObservableSet;
import org.eclipse.core.internal.databinding.beans.JavaBeanObservableValue;
import org.eclipse.core.tests.internal.databinding.beans.Bean;
import org.eclipse.jface.databinding.conformance.util.ChangeEventTracker;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

/**
 * @since 3.2
 */
public class PojoObservablesTest extends AbstractDefaultRealmTestCase {
	private Bean pojo;
	private String propertyName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		pojo = new Bean();
		propertyName = "value";
	}

	public void testObserveValue_ReturnsJavaBeanObservableValue()
			throws Exception {
		IObservableValue value = PojoObservables.observeValue(pojo, propertyName);

		assertNotNull(value);
		assertTrue(value instanceof JavaBeanObservableValue);
	}

	public void testObserveValue_DoesNotAttachListeners() throws Exception {
		IObservableValue value = PojoObservables.observeValue(pojo, propertyName);

		ChangeEventTracker.observe(value);
		assertFalse(pojo.hasListeners(propertyName));
	}

	public void testObservableValueWithRealm_ReturnsJavaBeanObservable()
			throws Exception {
		CurrentRealm realm = new CurrentRealm(true);
		IObservableValue value = PojoObservables.observeValue(realm, pojo,
				propertyName);

		assertNotNull(value);
		assertTrue(value instanceof JavaBeanObservableValue);
	}

	public void testObservableMap_ReturnsJavaBeanObservableMap()
			throws Exception {
		IObservableSet set = new WritableSet();
		set.add(new Bean());

		IObservableMap map = PojoObservables.observeMap(set, Bean.class,
				propertyName);
		assertNotNull(map);
		assertTrue(map instanceof JavaBeanObservableMap);
	}
	
	public void testObservableMap_DoesNotAttachListeners() throws Exception {
		IObservableSet set = new WritableSet();
		set.add(pojo);
		
		IObservableMap map = PojoObservables.observeMap(set, Bean.class, propertyName);
		assertFalse(pojo.hasListeners(propertyName));
		ChangeEventTracker.observe(map);
		assertFalse(pojo.hasListeners(propertyName));
	}
	
	public void testObserveMaps_ReturnsMaps() throws Exception {
		IObservableSet set = new WritableSet();
		set.add(pojo);
		
		IObservableMap[] maps = PojoObservables.observeMaps(set, Bean.class, new String[] {"value", "class"});
		assertEquals(2, maps.length);
	}
	
	public void testObserveListWithElementType_ReturnsJavaBeanObservableList() throws Exception {
		IObservableList list = PojoObservables.observeList(Realm.getDefault(), pojo, "list", String.class);
		assertTrue(list instanceof JavaBeanObservableList);
	}
	
	public void testObserveListWithElementType_DoesNotAttachListeners() throws Exception {
		IObservableList observable = PojoObservables.observeList(Realm.getDefault(), pojo, "list", String.class);
		assertFalse(pojo.hasListeners("list"));
		ChangeEventTracker.observe(observable);
		assertFalse(pojo.hasListeners("list"));
	}
	
	public void testObserveList_ReturnsJavaBeanObservableList() throws Exception {
		IObservableList observable = PojoObservables.observeList(Realm.getDefault(), pojo, "list");
		assertTrue(observable instanceof JavaBeanObservableList);
	}
	
	public void testObserveList_DoesNotAttachListeners() throws Exception {
		IObservableList observable = PojoObservables.observeList(Realm.getDefault(), pojo, "list");
		assertFalse(pojo.hasListeners("list"));
		ChangeEventTracker.observe(observable);
		assertFalse(pojo.hasListeners("list"));
	}
	
	public void testObserveSetWithElementType_ReturnsJavaBeanObservableList() throws Exception {
		IObservableSet list = PojoObservables.observeSet(Realm.getDefault(), pojo, "set", String.class);
		assertTrue(list instanceof JavaBeanObservableSet);
	}
	
	public void testObserveSetWithElementType_DoesNotAttachListeners() throws Exception {
		IObservableSet observable = PojoObservables.observeSet(Realm.getDefault(), pojo, "set", String.class);
		assertFalse(pojo.hasListeners("set"));
		ChangeEventTracker.observe(observable);
		assertFalse(pojo.hasListeners("set"));
	}
	
	public void testObserveSet_ReturnsJavaBeanObservableList() throws Exception {
		IObservableSet list = PojoObservables.observeSet(Realm.getDefault(), pojo, "set");
		assertTrue(list instanceof JavaBeanObservableSet);
	}
	
	public void testObserveSet_DoesNotAttachListeners() throws Exception {
		IObservableSet observable = PojoObservables.observeSet(Realm.getDefault(), pojo, "set");
		assertFalse(pojo.hasListeners("set"));
		ChangeEventTracker.observe(observable);
		assertFalse(pojo.hasListeners("set"));
	}
	
	public void testValueFactory_DoesNotAttachListeners() throws Exception {
		IObservableFactory factory = PojoObservables.valueFactory(Realm.getDefault(), "value");
		IObservableValue observable = (IObservableValue) factory.createObservable(pojo);
		
		assertFalse(pojo.hasListeners("value"));
		ChangeEventTracker.observe(observable);
		assertFalse(pojo.hasListeners("value"));
	}
	
	public void testListFactory_DoesNotAttachListeners() throws Exception {
		IObservableFactory factory = PojoObservables.listFactory(Realm.getDefault(), "list", String.class);
		IObservableList observable = (IObservableList) factory.createObservable(pojo);
		
		assertFalse(pojo.hasListeners("value"));
		ChangeEventTracker.observe(observable);
		assertFalse(pojo.hasListeners("value"));
	}
	
	public void testSetFactory_DoesNotAttachListeners() throws Exception {
		IObservableFactory factory = PojoObservables.setFactory(Realm.getDefault(), propertyName);
		IObservableSet observable = (IObservableSet) factory.createObservable(pojo);
		
		assertFalse(pojo.hasListeners("set"));
		ChangeEventTracker.observe(observable);
		assertFalse(pojo.hasListeners("set"));
	}
	
	public void testSetFactoryWithElementType_DoesNotAttachListeners() throws Exception {
		IObservableFactory factory = PojoObservables.setFactory(Realm.getDefault(), propertyName, String.class);
		IObservableSet observable = (IObservableSet) factory.createObservable(pojo);
		
		assertFalse(pojo.hasListeners("set"));
		ChangeEventTracker.observe(observable);
		assertFalse(pojo.hasListeners("set"));
	}
}

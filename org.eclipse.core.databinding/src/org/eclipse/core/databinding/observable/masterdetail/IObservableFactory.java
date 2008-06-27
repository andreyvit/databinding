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

package org.eclipse.core.databinding.observable.masterdetail;

import org.eclipse.core.databinding.observable.IObservable;

/**
 * Generates an {@link IObservable} when passed a target instance.
 * 
 * @since 1.0
 */
public interface IObservableFactory {

	/**
	 * Creates an observable for the given target object.
	 * 
	 * @param target
	 * @return the new observable
	 */
	public IObservable createObservable(Object target);

}

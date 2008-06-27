/*******************************************************************************
 * Copyright (c) 2007 Brad Reynolds and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Brad Reynolds - initial API and implementation
 ******************************************************************************/

package org.eclipse.jface.databinding.conformance.delegate;

import org.eclipse.core.databinding.observable.IObservable;

/**
 * Abstract implementation of {@link IObservableContractDelegate}.
 * 
 * @since 1.1
 */
public abstract class AbstractObservableContractDelegate implements
		IObservableContractDelegate {

	public void setUp() {
		// no op
	}

	public void tearDown() {
		// no op
	}

	public void change(IObservable observable) {
		// no op
	}

	public void setStale(IObservable observable, boolean stale) {
		// no op
	}
}

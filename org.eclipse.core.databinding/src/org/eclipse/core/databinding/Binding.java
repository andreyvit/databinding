/*******************************************************************************
 * Copyright (c) 2006, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Brad Reynolds - bug 159768
 *     Boris Bokowski - bug 218269
 *     Matthew Hall - bug 218269
 *******************************************************************************/

package org.eclipse.core.databinding;

import java.util.Collections;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;

/**
 * This abstract class represents a binding between a model and a target. Newly
 * created instances need to be added to a data binding context using
 * {@link #init(DataBindingContext)}.
 * 
 * @since 1.0
 */
public abstract class Binding extends ValidationStatusProvider {

	protected DataBindingContext context;
	private IObservable target;
	private IObservable model;
	
	/**
	 * Creates a new binding.
	 * 
	 * @param target target observable
	 * @param model model observable
	 */
	public Binding(IObservable target, IObservable model) {
		this.target = target;
		this.model = model;
	}
	
	/**
	 * Initializes this binding with the given context and adds it to the list
	 * of bindings of the context.
	 * <p>
	 * Subclasses may extend, but must call the super implementation.
	 * </p>
	 * 
	 * @param context
	 */
	public final void init(DataBindingContext context) {
		this.context = context;
		preInit();
		context.addBinding(this);
		postInit();
	}
	
	/**
	 * Called by {@link #init(DataBindingContext)} after setting
	 * {@link #context} but before adding this binding to the context.
	 * Subclasses may use this method to perform initialization that could not
	 * be done in the constructor. Care should be taken not to cause any events
	 * while running this method.
	 */
	protected abstract void preInit();
	
	/**
	 * Called by {@link #init(DataBindingContext)} after adding this binding to
	 * the context. Subclasses may use this method to perform initialization
	 * that may cause events to be fired, including BindingEvents that are
	 * forwarded to the data binding context.
	 */
	protected abstract void postInit();

	/**
	 * @return an observable value containing the current validation status
	 */
	public abstract IObservableValue getValidationStatus();

	/**
	 * Updates the model's state from the target's state at the next reasonable
	 * opportunity. There is no guarantee that the state will have been updated
	 * by the time this call returns.
	 */
	public abstract void updateTargetToModel();

	/**
	 * Updates the target's state from the model's state at the next reasonable
	 * opportunity. There is no guarantee that the state will have been updated
	 * by the time this call returns.
	 */
	public abstract void updateModelToTarget();
	
	/**
	 * Validates the target's state at the next reasonable
	 * opportunity. There is no guarantee that the validation status will have been updated
	 * by the time this call returns.
	 */
	public abstract void validateTargetToModel();
	
	/**
	 * Validates the model's state at the next reasonable
	 * opportunity. There is no guarantee that the validation status will have been updated
	 * by the time this call returns.
	 */
	public abstract void validateModelToTarget();
	
	/**
	 * Disposes of this Binding. Subclasses may extend, but must call super.dispose().
	 */
	public void dispose() {
		if (context != null) {
			context.removeBinding(this);
		}
		context = null;
		target = null;
		model = null;
		super.dispose();
	}

	/**
	 * @param context
	 */
	/* package */ void setDataBindingContext(DataBindingContext context) {
		this.context = context;
	}

	/**
	 * @return target observable
	 */
	public IObservable getTarget() {
		return target;
	}

	/**
	 * @since 1.1
	 */
	public IObservableList getTargets() {
		return Observables.staticObservableList(context.getValidationRealm(),
				Collections.singletonList(target));
	}

	/**
	 * @return model observable
	 */
	public IObservable getModel() {
		return model;
	}

	/**
	 * @since 1.1
	 */
	public IObservableList getModels() {
		return Observables.staticObservableList(context.getValidationRealm(),
				Collections.singletonList(model));
	}
}

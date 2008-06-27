/*******************************************************************************
 * Copyright (c) 2006, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     The Pampered Chef - initial API and implementation
 *******************************************************************************/

package org.eclipse.jface.examples.databinding.model;

import java.util.Date;

import org.eclipse.jface.examples.databinding.ModelObject;

/**
 * @since 1.0
 *
 */
public class SimpleOrder extends ModelObject {

	private int orderNumber;
	private Date date;

	/**
	 * @return Returns the date.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date The date to set.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return Returns the orderNumber.
	 */
	public int getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber The orderNumber to set.
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @param i
	 * @param date
	 */
	public SimpleOrder(int i, Date date) {
		this.orderNumber = i;
		this.date = date;
	}

}

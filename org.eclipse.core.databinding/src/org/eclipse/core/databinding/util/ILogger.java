/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Chris Gross (schtoo@schtoo.com) - initial API and implementation
 *       (bug 49497 [RCP] JFace dependency on org.eclipse.core.runtime enlarges standalone JFace applications)
 *******************************************************************************/

package org.eclipse.core.databinding.util;

import org.eclipse.core.runtime.IStatus;

/**
 * A mechanism to log errors throughout JFace Data Binding.
 * <p>
 * Clients may provide their own implementation to change how errors are logged
 * from within JFace Data Binding.
 * </p>
 * 
 * @see Policy#getLog()
 * @see Policy#setLog(ILogger)
 * @since 1.1
 */
public interface ILogger {

	/**
	 * Logs the given status.
	 * 
	 * @param status
	 *            the status to log
	 */
	public void log(IStatus status);

}

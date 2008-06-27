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

package org.eclipse.core.internal.databinding.observable.tree;

/**
 * @since 3.3
 * 
 */
public abstract class TreeDiffVisitor {

	/**
	 * Visits the given tree diff.
	 * 
	 * @param diff
	 *            the diff to visit
	 * @param currentPath
	 *            the current path (the diff's element is the last segment of
	 *            the path)
	 * 
	 * @return <code>true</code> if the tree diff's children should be
	 *         visited; <code>false</code> if they should be skipped.
	 */
	public abstract boolean visit(TreeDiffNode diff, TreePath currentPath);
}

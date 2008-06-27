/*******************************************************************************
 * Copyright (c) 2005-2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Brad Reynolds - bugs 137877, 152543, 152540, 116920, 164247, 164653,
 *                     159768, 170848, 147515
 *     Bob Smith - bug 198880
 *     Ashley Cambrell - bugs 198903, 198904
 *     Matthew Hall - bugs 210115, 212468, 212223, 206839, 208858, 208322,
 *                    212518, 215531, 221351, 184830, 213145, 218269
 *******************************************************************************/
package org.eclipse.jface.tests.databinding;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.tests.databinding.AggregateValidationStatusTest;
import org.eclipse.core.tests.databinding.DatabindingContextTest;
import org.eclipse.core.tests.databinding.ListBindingTest;
import org.eclipse.core.tests.databinding.ObservablesManagerTest;
import org.eclipse.core.tests.databinding.UpdateStrategyTest;
import org.eclipse.core.tests.databinding.UpdateValueStrategyTest;
import org.eclipse.core.tests.databinding.ValueBindingTest;
import org.eclipse.core.tests.databinding.beans.BeansObservablesTest;
import org.eclipse.core.tests.databinding.beans.PojoObservablesTest;
import org.eclipse.core.tests.databinding.conversion.NumberToStringConverterTest;
import org.eclipse.core.tests.databinding.conversion.StringToNumberConverterTest;
import org.eclipse.core.tests.databinding.observable.AbstractObservableTest;
import org.eclipse.core.tests.databinding.observable.DiffsTest;
import org.eclipse.core.tests.databinding.observable.Diffs_ListDiffTests;
import org.eclipse.core.tests.databinding.observable.ObservableTrackerTest;
import org.eclipse.core.tests.databinding.observable.ObservablesTest;
import org.eclipse.core.tests.databinding.observable.RealmTest;
import org.eclipse.core.tests.databinding.observable.list.AbstractObservableListTest;
import org.eclipse.core.tests.databinding.observable.list.ComputedListTest;
import org.eclipse.core.tests.databinding.observable.list.ListDiffTest;
import org.eclipse.core.tests.databinding.observable.list.ListDiffVisitorTest;
import org.eclipse.core.tests.databinding.observable.list.ObservableListTest;
import org.eclipse.core.tests.databinding.observable.list.WritableListTest;
import org.eclipse.core.tests.databinding.observable.map.AbstractObservableMapTest;
import org.eclipse.core.tests.databinding.observable.map.CompositeMapTest;
import org.eclipse.core.tests.databinding.observable.map.ObservableMapTest;
import org.eclipse.core.tests.databinding.observable.map.WritableMapTest;
import org.eclipse.core.tests.databinding.observable.set.AbstractObservableSetTest;
import org.eclipse.core.tests.databinding.observable.set.ObservableSetTest;
import org.eclipse.core.tests.databinding.observable.set.UnionSetTest;
import org.eclipse.core.tests.databinding.observable.set.WritableSetTest;
import org.eclipse.core.tests.databinding.observable.value.AbstractObservableValueTest;
import org.eclipse.core.tests.databinding.observable.value.AbstractVetoableValueTest;
import org.eclipse.core.tests.databinding.observable.value.ComputedValueTest;
import org.eclipse.core.tests.databinding.observable.value.WritableValueTest;
import org.eclipse.core.tests.databinding.validation.MultiValidatorTest;
import org.eclipse.core.tests.databinding.validation.ValidationStatusTest;
import org.eclipse.core.tests.internal.databinding.BindingMessagesTest;
import org.eclipse.core.tests.internal.databinding.BindingStatusTest;
import org.eclipse.core.tests.internal.databinding.QueueTest;
import org.eclipse.core.tests.internal.databinding.RandomAccessListIteratorTest;
import org.eclipse.core.tests.internal.databinding.beans.BeanObservableListDecoratorTest;
import org.eclipse.core.tests.internal.databinding.beans.BeanObservableSetDecoratorTest;
import org.eclipse.core.tests.internal.databinding.beans.BeanObservableValueDecoratorTest;
import org.eclipse.core.tests.internal.databinding.beans.JavaBeanObservableArrayBasedListTest;
import org.eclipse.core.tests.internal.databinding.beans.JavaBeanObservableArrayBasedSetTest;
import org.eclipse.core.tests.internal.databinding.beans.JavaBeanObservableListTest;
import org.eclipse.core.tests.internal.databinding.beans.JavaBeanObservableMapTest;
import org.eclipse.core.tests.internal.databinding.beans.JavaBeanObservableSetTest;
import org.eclipse.core.tests.internal.databinding.beans.JavaBeanObservableValueTest;
import org.eclipse.core.tests.internal.databinding.beans.ListenerSupportTest;
import org.eclipse.core.tests.internal.databinding.conversion.DateConversionSupportTest;
import org.eclipse.core.tests.internal.databinding.conversion.IdentityConverterTest;
import org.eclipse.core.tests.internal.databinding.conversion.IntegerToStringConverterTest;
import org.eclipse.core.tests.internal.databinding.conversion.NumberToBigDecimalTest;
import org.eclipse.core.tests.internal.databinding.conversion.NumberToBigIntegerConverterTest;
import org.eclipse.core.tests.internal.databinding.conversion.NumberToByteConverterTest;
import org.eclipse.core.tests.internal.databinding.conversion.NumberToDoubleConverterTest;
import org.eclipse.core.tests.internal.databinding.conversion.NumberToFloatConverterTest;
import org.eclipse.core.tests.internal.databinding.conversion.NumberToIntegerConverterTest;
import org.eclipse.core.tests.internal.databinding.conversion.NumberToLongConverterTest;
import org.eclipse.core.tests.internal.databinding.conversion.NumberToShortConverterTest;
import org.eclipse.core.tests.internal.databinding.conversion.ObjectToPrimitiveValidatorTest;
import org.eclipse.core.tests.internal.databinding.conversion.StatusToStringConverterTest;
import org.eclipse.core.tests.internal.databinding.conversion.StringToBooleanConverterTest;
import org.eclipse.core.tests.internal.databinding.conversion.StringToByteConverterTest;
import org.eclipse.core.tests.internal.databinding.conversion.StringToCharacterConverterTest;
import org.eclipse.core.tests.internal.databinding.conversion.StringToNumberParserByteTest;
import org.eclipse.core.tests.internal.databinding.conversion.StringToNumberParserDoubleTest;
import org.eclipse.core.tests.internal.databinding.conversion.StringToNumberParserFloatTest;
import org.eclipse.core.tests.internal.databinding.conversion.StringToNumberParserIntegerTest;
import org.eclipse.core.tests.internal.databinding.conversion.StringToNumberParserLongTest;
import org.eclipse.core.tests.internal.databinding.conversion.StringToNumberParserShortTest;
import org.eclipse.core.tests.internal.databinding.conversion.StringToNumberParserTest;
import org.eclipse.core.tests.internal.databinding.conversion.StringToShortConverterTest;
import org.eclipse.core.tests.internal.databinding.observable.ConstantObservableValueTest;
import org.eclipse.core.tests.internal.databinding.observable.EmptyObservableListTest;
import org.eclipse.core.tests.internal.databinding.observable.EmptyObservableSetTest;
import org.eclipse.core.tests.internal.databinding.observable.MapEntryObservableValueTest;
import org.eclipse.core.tests.internal.databinding.observable.ProxyObservableListTest;
import org.eclipse.core.tests.internal.databinding.observable.ProxyObservableSetTest;
import org.eclipse.core.tests.internal.databinding.observable.StalenessObservableValueTest;
import org.eclipse.core.tests.internal.databinding.observable.UnmodifiableObservableListTest;
import org.eclipse.core.tests.internal.databinding.observable.UnmodifiableObservableSetTest;
import org.eclipse.core.tests.internal.databinding.observable.ValidatedObservableListTest;
import org.eclipse.core.tests.internal.databinding.observable.ValidatedObservableSetTest;
import org.eclipse.core.tests.internal.databinding.observable.ValidatedObservableValueTest;
import org.eclipse.core.tests.internal.databinding.observable.masterdetail.DetailObservableListTest;
import org.eclipse.core.tests.internal.databinding.observable.masterdetail.DetailObservableSetTest;
import org.eclipse.core.tests.internal.databinding.observable.masterdetail.DetailObservableValueTest;
import org.eclipse.core.tests.internal.databinding.validation.AbstractStringToNumberValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.NumberToByteValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.NumberToDoubleValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.NumberToFloatValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.NumberToIntegerValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.NumberToLongValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.NumberToShortValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.NumberToUnboundedNumberValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.StringToByteValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.StringToCharacterValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.StringToDoubleValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.StringToFloatValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.StringToIntegerValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.StringToLongValidatorTest;
import org.eclipse.core.tests.internal.databinding.validation.StringToShortValidatorTest;
import org.eclipse.jface.tests.databinding.scenarios.BindingScenariosTestSuite;
import org.eclipse.jface.tests.databinding.swt.SWTObservablesTest;
import org.eclipse.jface.tests.databinding.viewers.ObservableListTreeContentProviderTest;
import org.eclipse.jface.tests.databinding.viewers.ObservableMapLabelProviderTest;
import org.eclipse.jface.tests.databinding.viewers.ObservableSetContentProviderTest;
import org.eclipse.jface.tests.databinding.viewers.ObservableSetTreeContentProviderTest;
import org.eclipse.jface.tests.databinding.viewers.ViewersObservablesTest;
import org.eclipse.jface.tests.examples.databinding.mask.internal.EditMaskLexerAndTokenTest;
import org.eclipse.jface.tests.examples.databinding.mask.internal.EditMaskParserTest;
import org.eclipse.jface.tests.internal.databinding.swt.ButtonObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.CComboObservableValueSelectionTest;
import org.eclipse.jface.tests.internal.databinding.swt.CComboObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.CComboObservableValueTextTest;
import org.eclipse.jface.tests.internal.databinding.swt.CComboSingleSelectionObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.CLabelObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.ComboObservableValueSelectionTest;
import org.eclipse.jface.tests.internal.databinding.swt.ComboObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.ComboObservableValueTextTest;
import org.eclipse.jface.tests.internal.databinding.swt.ComboSingleSelectionObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.ControlObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.DelayedObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.LabelObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.ListSingleSelectionObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.SWTObservableListTest;
import org.eclipse.jface.tests.internal.databinding.swt.ScaleObservableValueMaxTest;
import org.eclipse.jface.tests.internal.databinding.swt.ScaleObservableValueMinTest;
import org.eclipse.jface.tests.internal.databinding.swt.ScaleObservableValueSelectionTest;
import org.eclipse.jface.tests.internal.databinding.swt.ShellObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.SpinnerObservableValueMaxTest;
import org.eclipse.jface.tests.internal.databinding.swt.SpinnerObservableValueMinTest;
import org.eclipse.jface.tests.internal.databinding.swt.SpinnerObservableValueSelectionTest;
import org.eclipse.jface.tests.internal.databinding.swt.SpinnerObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.TableObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.TableSingleSelectionObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.TextEditableObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.swt.TextObservableValueFocusOutTest;
import org.eclipse.jface.tests.internal.databinding.swt.TextObservableValueModifyTest;
import org.eclipse.jface.tests.internal.databinding.swt.TextObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.viewers.ObservableViewerElementSetTest;
import org.eclipse.jface.tests.internal.databinding.viewers.SelectionProviderMultiSelectionObservableListTest;
import org.eclipse.jface.tests.internal.databinding.viewers.SelectionProviderSingleSelectionObservableValueTest;
import org.eclipse.jface.tests.internal.databinding.viewers.ViewerElementMapTest;
import org.eclipse.jface.tests.internal.databinding.viewers.ViewerElementSetTest;
import org.eclipse.jface.tests.internal.databinding.viewers.ViewerElementWrapperTest;
import org.eclipse.jface.tests.internal.databinding.viewers.ViewerInputObservableValueTest;

public class BindingTestSuite extends TestSuite {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new BindingTestSetup(new BindingTestSuite());
	}

	public BindingTestSuite() {
		// org.eclipse.core.tests.databinding
		addTestSuite(AggregateValidationStatusTest.class);
		addTestSuite(DatabindingContextTest.class);
		addTestSuite(ListBindingTest.class);
		addTestSuite(UpdateStrategyTest.class);
		addTestSuite(UpdateValueStrategyTest.class);
		addTestSuite(ValueBindingTest.class);
		addTestSuite(ObservablesManagerTest.class);

		// org.eclipse.core.tests.databinding.beans
		addTestSuite(BeansObservablesTest.class);
		addTestSuite(PojoObservablesTest.class);

		// org.eclipse.core.tests.databinding.conversion
		addTestSuite(NumberToStringConverterTest.class);
		addTestSuite(StringToNumberConverterTest.class);

		// org.eclipse.core.tests.databinding.observable
		addTest(AbstractObservableTest.suite());
		addTestSuite(Diffs_ListDiffTests.class);
		addTestSuite(DiffsTest.class);
		addTestSuite(ObservablesTest.class);
		addTestSuite(ObservableTrackerTest.class);
		addTestSuite(RealmTest.class);

		// org.eclipse.core.tests.databinding.observable.list
		addTest(AbstractObservableListTest.suite());
		addTest(ComputedListTest.suite());
		addTestSuite(ListDiffTest.class);
		addTestSuite(ListDiffVisitorTest.class);
		addTest(ObservableListTest.suite());
		addTest(WritableListTest.suite());

		// org.eclipse.core.tests.databinding.observable.map
		addTestSuite(AbstractObservableMapTest.class);
		addTestSuite(ObservableMapTest.class);
		addTestSuite(WritableMapTest.class);
		addTestSuite(CompositeMapTest.class);

		// org.eclipse.core.tests.databinding.observable.set
		addTest(AbstractObservableSetTest.suite());
		addTest(ObservableSetTest.suite());
		addTest(UnionSetTest.suite());
		addTest(WritableSetTest.suite());
		
		//org.eclipse.core.tests.databinding.observable.value
		addTestSuite(AbstractObservableValueTest.class);
		addTestSuite(AbstractVetoableValueTest.class);
		addTestSuite(ComputedValueTest.class);
		addTest(WritableValueTest.suite());
		
		//org.eclipse.core.tests.databinding.validation
		addTestSuite(MultiValidatorTest.class);
		addTestSuite(ValidationStatusTest.class);
		
		// org.eclipse.core.tests.internal.databinding
		addTestSuite(BindingMessagesTest.class);
		addTestSuite(BindingStatusTest.class);
		addTestSuite(RandomAccessListIteratorTest.class);
		addTestSuite(QueueTest.class);

		// org.eclipse.core.tests.internal.databinding.conversion
		addTestSuite(DateConversionSupportTest.class);
		addTestSuite(IdentityConverterTest.class);
		addTestSuite(IntegerToStringConverterTest.class);
		addTestSuite(NumberToBigDecimalTest.class);
		addTestSuite(NumberToBigIntegerConverterTest.class);
		addTestSuite(NumberToByteConverterTest.class);
		addTestSuite(NumberToDoubleConverterTest.class);
		addTestSuite(NumberToFloatConverterTest.class);
		addTestSuite(NumberToIntegerConverterTest.class);
		addTestSuite(NumberToLongConverterTest.class);
		addTestSuite(NumberToShortConverterTest.class);
		addTestSuite(ObjectToPrimitiveValidatorTest.class);
		addTestSuite(StatusToStringConverterTest.class);
		addTestSuite(StringToBooleanConverterTest.class);
		addTestSuite(StringToByteConverterTest.class);
		addTestSuite(StringToCharacterConverterTest.class);
		addTestSuite(StringToNumberParserByteTest.class);
		addTestSuite(StringToNumberParserDoubleTest.class);
		addTestSuite(StringToNumberParserFloatTest.class);
		addTestSuite(StringToNumberParserIntegerTest.class);
		addTestSuite(StringToNumberParserLongTest.class);
		addTestSuite(StringToNumberParserShortTest.class);
		addTestSuite(StringToNumberParserTest.class);
		addTestSuite(StringToShortConverterTest.class);

		//org.eclipse.core.tests.internal.databinding.internal.beans
		addTest(BeanObservableListDecoratorTest.suite());
		addTestSuite(BeanObservableSetDecoratorTest.class);
		addTestSuite(BeanObservableValueDecoratorTest.class);
		addTestSuite(BeanObservableListDecoratorTest.class);
		addTest(JavaBeanObservableArrayBasedListTest.suite());
		addTest(JavaBeanObservableArrayBasedSetTest.suite());
		addTest(JavaBeanObservableListTest.suite());
		addTest(JavaBeanObservableMapTest.suite());
		addTest(JavaBeanObservableSetTest.suite());
		addTest(JavaBeanObservableValueTest.suite());
		addTestSuite(ListenerSupportTest.class);
		
		//org.eclipse.core.tests.internal.databinding.observable
		addTest(ConstantObservableValueTest.suite());
		addTest(EmptyObservableListTest.suite());
		addTest(EmptyObservableSetTest.suite());
		addTest(MapEntryObservableValueTest.suite());
		addTest(ProxyObservableListTest.suite());
		addTest(ProxyObservableSetTest.suite());
		addTest(StalenessObservableValueTest.suite());
		addTest(UnmodifiableObservableListTest.suite());
		addTest(UnmodifiableObservableSetTest.suite());
		addTest(ValidatedObservableValueTest.suite());
		addTest(ValidatedObservableListTest.suite());
		addTest(ValidatedObservableSetTest.suite());
//		addTest(ValidatedObservableMapTest.suite());
		
		// org.eclipse.core.tests.internal.databinding.observable.masterdetail
		addTest(DetailObservableListTest.suite());
		addTest(DetailObservableSetTest.suite());
		addTest(DetailObservableValueTest.suite());

		// org.eclipse.core.tests.internal.databinding.validation
		addTestSuite(AbstractStringToNumberValidatorTest.class);
		addTestSuite(NumberToByteValidatorTest.class);
		addTestSuite(NumberToDoubleValidatorTest.class);
		addTestSuite(NumberToFloatValidatorTest.class);
		addTestSuite(NumberToIntegerValidatorTest.class);
		addTestSuite(NumberToLongValidatorTest.class);
		addTestSuite(NumberToShortValidatorTest.class);
		addTestSuite(NumberToUnboundedNumberValidatorTest.class);
		addTestSuite(StringToByteValidatorTest.class);
		addTestSuite(StringToCharacterValidatorTest.class);
		addTestSuite(StringToDoubleValidatorTest.class);
		addTestSuite(StringToFloatValidatorTest.class);
		addTestSuite(StringToIntegerValidatorTest.class);
		addTestSuite(StringToLongValidatorTest.class);
		addTestSuite(StringToShortValidatorTest.class);

		// org.eclipse.jface.tests.databinding.scenarios
		addTest(BindingScenariosTestSuite.suite());
		// The files in this package are in the above test suite

		//org.eclipse.jface.tests.databinding.swt
		addTestSuite(SWTObservablesTest.class);
		
		// org.eclipse.jface.tests.databinding.viewers
		addTestSuite(ObservableListTreeContentProviderTest.class);
		addTestSuite(ObservableMapLabelProviderTest.class);
		addTestSuite(ObservableSetContentProviderTest.class);
		addTestSuite(ObservableSetTreeContentProviderTest.class);
		addTestSuite(ViewersObservablesTest.class);
		
		//org.eclipse.jface.tests.example.databinding.mask.internal
		addTestSuite(EditMaskLexerAndTokenTest.class);
		addTestSuite(EditMaskParserTest.class);

		//org.eclipse.jface.tests.internal.databinding.internal.swt
		addTest(ButtonObservableValueTest.suite());
		addTestSuite(CComboObservableValueTest.class);
		addTest(CComboObservableValueSelectionTest.suite());
		addTest(CComboObservableValueTextTest.suite());
		addTestSuite(CComboSingleSelectionObservableValueTest.class);
		addTest(CComboSingleSelectionObservableValueTest.suite());
		addTest(CLabelObservableValueTest.suite());
		addTestSuite(ComboObservableValueTest.class);
		addTest(ComboObservableValueSelectionTest.suite());
		addTest(ComboObservableValueTextTest.suite());
		addTestSuite(ComboSingleSelectionObservableValueTest.class);
		addTest(DelayedObservableValueTest.suite());
		
		addTest(SWTObservableListTest.suite());
		
		addTestSuite(ControlObservableValueTest.class);
		addTest(LabelObservableValueTest.suite());
		addTestSuite(ListSingleSelectionObservableValueTest.class);
		addTest(ScaleObservableValueMinTest.suite());
		addTest(ScaleObservableValueMaxTest.suite());
		addTest(ScaleObservableValueSelectionTest.suite());
		
		addTest(ShellObservableValueTest.suite());
		
		addTestSuite(SpinnerObservableValueTest.class);
		addTest(SpinnerObservableValueMinTest.suite());
		addTest(SpinnerObservableValueMaxTest.suite());
		addTest(SpinnerObservableValueSelectionTest.suite());
		
		addTestSuite(TableObservableValueTest.class);
		addTest(TableSingleSelectionObservableValueTest.suite());
		addTest(TextEditableObservableValueTest.suite());
		addTest(TextObservableValueFocusOutTest.suite());
		addTest(TextObservableValueModifyTest.suite());
		addTestSuite(TextObservableValueTest.class);
		
		//org.eclipse.jface.tests.internal.databinding.internal.viewers
		addTest(ObservableViewerElementSetTest.suite());
		addTestSuite(SelectionProviderMultiSelectionObservableListTest.class);
		addTestSuite(SelectionProviderSingleSelectionObservableValueTest.class);
		addTestSuite(ViewerElementMapTest.class);
		addTestSuite(ViewerElementSetTest.class);
		addTestSuite(ViewerElementWrapperTest.class);
		addTest(ViewerInputObservableValueTest.suite());
	}

	/**
	 * @param testCase
	 *            TODO
	 * @return true if the given test is temporarily disabled
	 */
	public static boolean failingTestsDisabled(TestCase testCase) {
		System.out.println("Ignoring disabled test: "
				+ testCase.getClass().getName() + "." + testCase.getName());
		return true;
	}

	/**
	 * @param testSuite
	 *            TODO
	 * @return true if the given test is temporarily disabled
	 */
	public static boolean failingTestsDisabled(TestSuite testSuite) {
		System.out.println("Ignoring disabled test: "
				+ testSuite.getClass().getName() + "." + testSuite.getName());
		return true;
	}
}
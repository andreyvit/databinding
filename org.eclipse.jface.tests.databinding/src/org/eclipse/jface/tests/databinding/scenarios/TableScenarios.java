/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Brad Reynolds - bug 116920
 *     Brad Reynolds - bug 160000
 *******************************************************************************/
package org.eclipse.jface.tests.databinding.scenarios;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.examples.databinding.model.Account;
import org.eclipse.jface.examples.databinding.model.Catalog;
import org.eclipse.jface.examples.databinding.model.Category;
import org.eclipse.jface.examples.databinding.model.SampleData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * To run the tests in this class, right-click and select "Run As JUnit Plug-in
 * Test". This will also start an Eclipse instance. To clean up the launch
 * configuration, open up its "Main" tab and select "[No Application] - Headless
 * Mode" as the application to run.
 */

public class TableScenarios extends ScenariosTestCase {

	private TableViewer tableViewer;

	private Catalog catalog;

	Category category;

	private TableColumn firstNameColumn;

	private TableColumn lastNameColumn;

	private TableColumn stateColumn;

	Image[] images;

	private TableColumn fancyColumn;

	protected void setUp() throws Exception {
		super.setUp();
		getComposite().setLayout(new FillLayout());
		tableViewer = new TableViewer(getComposite());
		tableViewer.getTable().setLinesVisible(true);
		tableViewer.getTable().setHeaderVisible(true);
		firstNameColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
		firstNameColumn.setWidth(50);
		lastNameColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
		lastNameColumn.setWidth(50);
		stateColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
		stateColumn.setWidth(50);
		fancyColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
		fancyColumn.setWidth(250);

		catalog = SampleData.CATALOG_2005; // Lodging source
		category = SampleData.WINTER_CATEGORY;

		images = new Image[] {
				getShell().getDisplay().getSystemImage(SWT.ICON_ERROR),
				getShell().getDisplay().getSystemImage(SWT.ICON_WARNING),
				getShell().getDisplay().getSystemImage(SWT.ICON_INFORMATION), };
	}

	protected void tearDown() throws Exception {
		// do any teardown work here
		super.tearDown();
		tableViewer.getTable().dispose();
		tableViewer = null;
		firstNameColumn = null;
		lastNameColumn = null;
		stateColumn = null;
	}

	public void testScenario01() {
		// Show that a TableViewer with three columns renders the accounts
		Account[] accounts = catalog.getAccounts();

		IObservableList list = new WritableList(new ArrayList(), Account.class);
		list.addAll(Arrays.asList(accounts));

		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		IObservableMap[] attributeMaps = BeansObservables.observeMaps(
				contentProvider.getKnownElements(), Account.class,
				new String[] { "firstName", "lastName", "state" });

		tableViewer.setContentProvider(contentProvider);
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(
				attributeMaps));
		tableViewer.setInput(list);

		Table table = tableViewer.getTable();

		// Verify the data in the table columns matches the accounts
		for (int i = 0; i < accounts.length; i++) {
			Account account = catalog.getAccounts()[i];
			TableItem item = table.getItem(i);

			assertEquals(account.getFirstName(), item.getText(0));
			assertEquals(account.getLastName(), item.getText(1));
			assertEquals(account.getState(), item.getText(2));
		}
	}

	public void testScenario02() throws SecurityException,
			IllegalArgumentException {
		// Show that a TableViewer with three columns can be used to update
		// columns
		// Account[] accounts = catalog.getAccounts();
		//
		// TableModelDescription tableModelDescription = new
		// TableModelDescription(new Property(catalog, "accounts"), new
		// String[]{"firstName","lastName","state"});
		// // tableViewerDescription.addEditableColumn("firstName");
		// // tableViewerDescription.addEditableColumn("lastName", null, null,
		// new PhoneConverter());
		// // tableViewerDescription.addEditableColumn("state", null, null, new
		// StateConverter());
		// getDbc().bind(tableViewer,
		// tableModelDescription, null);
		//
		// Account account = accounts[0];
		// // Select the first item in the table
		// tableViewer.editElement(account, 0);
		// // Set the text property of the cell editor which is now active over
		// the "firstName" column
		// CellEditor[] cellEditors = tableViewer.getCellEditors();
		// TextCellEditor firstNameEditor = (TextCellEditor) cellEditors[0];
		// // Change the firstName and test it goes to the model
		// enterText((Text) firstNameEditor.getControl(), "Bill");
		// // Check whether the model has changed
		// assertEquals("Bill",account.getFirstName());
	}

	public void testScenario04() {
		// // Show that when an item is added to a collection the table gets an
		// extra item
		// Account[] accounts = catalog.getAccounts();
		//		
		// TableViewerDescription tableViewerDescription = new
		// TableViewerDescription(
		// tableViewer);
		// tableViewerDescription.addColumn("firstName");
		// tableViewerDescription.addColumn("lastName");
		// tableViewerDescription.addColumn("state");
		// tableViewerDescription.addColumn(null,new IConverter(){
		//			
		// public Class getModelType() {
		// return Account.class;
		// }
		//			
		// public Class getTargetType() {
		// return ViewerLabel.class;
		// }
		//			
		// public Object convertTargetToModel(Object targetObject) {
		// return null;
		// }
		//			
		// public Object convertModelToTarget(Object modelObject) {
		// Account account = (Account) modelObject;
		// return new ViewerLabel(account.toString(), images[new
		// Random().nextInt(images.length)]);
		// }});
		// getDbc().bind(tableViewerDescription,
		// new Property(catalog, "accounts"), null);
		//
		// //interact();
		//		
		// // Verify the number of accounts matches the number of items in the
		// table
		// assertEquals(tableViewer.getTable().getItemCount(),accounts.length);
		// // Add a new account and verify that the number of items in the table
		// increases
		// Account newAccount = new Account();
		// newAccount.setFirstName("Finbar");
		// newAccount.setLastName("McGoo");
		// newAccount.setLastName("NC");
		// catalog.addAccount(newAccount);
		// // The number of items should have gone up by one
		// assertEquals(tableViewer.getTable().getItemCount(),accounts.length +
		// 1);
		// // The number of items should still match the number of accounts
		// (i.e. test the model)
		// assertEquals(tableViewer.getTable().getItemCount(),catalog.getAccounts().length);
		// // Remove the account that was just added
		// catalog.removeAccount(newAccount);
		// // The number of items should match the original
		// assertEquals(tableViewer.getTable().getItemCount(),accounts.length);
		// // The number of items should still match the number of accounts
		// (i.e. test the model is reset)
		// assertEquals(tableViewer.getTable().getItemCount(),catalog.getAccounts().length);
		//		
		// // Test adding and removing to the model on a non UI thread
		// int numberOfAccounts = catalog.getAccounts().length;
		// final Account barney = new Account();
		// barney.setFirstName("Barney");
		// barney.setLastName("Smith");
		// barney.setLastName("CA");
		// invokeNonUI(new Runnable(){
		// public void run(){
		// catalog.addAccount(barney);
		// }
		// });
		// spinEventLoop(0);
		// // The number of items should have gone up by one
		// assertEquals(tableViewer.getTable().getItemCount(),numberOfAccounts +
		// 1);
		//		
		// invokeNonUI(new Runnable(){
		// public void run(){
		// catalog.removeAccount(barney);
		// }
		// });
		// spinEventLoop(0);
		// // The number of items should have reverted to the original number
		// before barney was added and removed
		// assertEquals(tableViewer.getTable().getItemCount(),numberOfAccounts);
		//		
	}

	public void testScenario03() {
		// // Show that converters work for table columns
		// Account[] accounts = catalog.getAccounts();
		//
		// TableViewerDescription tableViewerDescription = new
		// TableViewerDescription(
		// tableViewer);
		// tableViewerDescription.addEditableColumn("lastName");
		// tableViewerDescription.addEditableColumn("phone", null, null ,
		// new PhoneConverter());
		// tableViewerDescription.addEditableColumn("state", null, null ,
		// new StateConverter());
		// getDbc().bind(tableViewerDescription,
		// new Property(catalog, "accounts"), null);
		//
		// // Verify that the data in the the table columns matches the expected
		// // What we are looking for is that the phone numbers are converterted
		// to
		// // nnn-nnn-nnnn and that
		// // the state letters are converted to state names
		// // Verify the data in the table columns matches the accounts
		// PhoneConverter phoneConverter = new PhoneConverter();
		// StateConverter stateConverter = new StateConverter();
		// for (int i = 0; i < accounts.length; i++) {
		// Account account = catalog.getAccounts()[i];
		// // Check the phone number
		// String col_phone = ((ITableLabelProvider) tableViewer
		// .getLabelProvider()).getColumnText(account, 1);
		// assertEquals(getValue((String)phoneConverter
		// .convertModelToTarget(account.getPhone())), col_phone);
		// String col_state = ((ITableLabelProvider) tableViewer
		// .getLabelProvider()).getColumnText(account, 2);
		// assertEquals(getValue((String)stateConverter
		// .convertModelToTarget(account.getState())), col_state);
		// }
	}

	public void testScenario05() {
		// // Show that when the model changes then the UI refreshes to reflect
		// this
		//
		// TableViewerDescription tableViewerDescription = new
		// TableViewerDescription(
		// tableViewer);
		// tableViewerDescription.addColumn("lastName");
		// tableViewerDescription.addColumn("phone",
		// new PhoneConverter());
		// tableViewerDescription.addColumn("state",
		// new StateConverter());
		// getDbc().bind(tableViewerDescription,
		// new Property(catalog, "accounts"), null);
		//		
		// final Account account = catalog.getAccounts()[0];
		// String lastName = tableViewer.getTable().getItem(0).getText(0);
		// // Check the firstName in the TableItem is the same as the model
		// assertEquals(lastName,account.getLastName());
		// // Now change the model and check again
		// account.setLastName("Gershwin");
		// lastName = tableViewer.getTable().getItem(0).getText(0);
		// assertEquals(lastName,account.getLastName());
		//		
		// // Test the model update on a non UI thread
		// invokeNonUI(new Runnable(){
		// public void run(){
		// account.setLastName("Mozart");
		// }
		// });
		// spinEventLoop(0);
		// lastName = tableViewer.getTable().getItem(0).getText(0);
		// assertEquals(lastName,account.getLastName());
		//		
	}

	public void testScenario06() {
		// // Check that explicit type means that defaulting of converters works
		// TableViewerDescription tableViewerDescription = new
		// TableViewerDescription(
		// tableViewer);
		// tableViewerDescription.addEditableColumn("price");
		// tableViewerDescription.getColumn(0).setPropertyType(Double.TYPE);
		// getDbc().bind(tableViewerDescription,
		// new Property(catalog, "transporations"), null);
		// Transportation transporation = catalog.getTransporations()[0];
		// tableViewer.editElement(transporation, 0);
		// // Set the text property of the cell editor which is now active over
		// the "firstName" column
		// CellEditor[] cellEditors = tableViewer.getCellEditors();
		// TextCellEditor priceEditor = (TextCellEditor) cellEditors[0];
		// // Change the firstName and test it goes to the model
		// enterText((Text) priceEditor.getControl(), "123.45");
		// // Verify the model is updated
		// assertEquals(transporation.getPrice(),123.45,0);

	}

	public void testScenario07() {
		// // Verify that even when a column's property type is not set, that it
		// is worked out lazily from the target type
		// TableViewerDescription tableViewerDescription = new
		// TableViewerDescription(
		// tableViewer);
		// tableViewerDescription.addEditableColumn("price");
		// // The column's type is not set to be Double.TYPE. This will be
		// inferred once the first Transportation object is set
		// // into the ObservableCollection
		// getDbc().bind(tableViewerDescription,
		// new Property(catalog, "transporations"), null);
		// Transportation transporation = catalog.getTransporations()[0];
		// tableViewer.editElement(transporation, 0);
		// // Set the text property of the cell editor which is now active over
		// the "firstName" column
		// CellEditor[] cellEditors = tableViewer.getCellEditors();
		// TextCellEditor priceEditor = (TextCellEditor) cellEditors[0];
		// // Change the firstName and test it goes to the model
		// enterText((Text) priceEditor.getControl(), "123.45");
		// // Verify the model is updated
		// assertEquals(transporation.getPrice(),123.45,0);
		//		
	}

	public void testScenario08_00() {
		// // Verify that binding to a Collection property (rather than an
		// array) works when specifying data type
		// TableViewerDescription tableViewerDescription = new
		// TableViewerDescription(
		// tableViewer);
		// tableViewerDescription.addEditableColumn("userId");
		// tableViewerDescription.addEditableColumn("password");
		// getDbc().bind(tableViewerDescription,
		// new Property(catalog, "signons", Signon.class, null), null);
		// Signon firstSignon = (Signon) catalog.getSignons().get(0);
		// // Verify the UI matches the model
		// TableItem firstTableItem = tableViewer.getTable().getItem(0);
		// assertEquals(firstTableItem.getText(1),firstSignon.getPassword());
		// // Change the model and ensure the UI refreshes
		// firstSignon.setPassword("Eclipse123Rocks");
		// assertEquals("Eclipse123Rocks",firstSignon.getPassword());
		// assertEquals(firstTableItem.getText(1),firstSignon.getPassword());
		// // Change the GUI and ensure the model refreshes
		// tableViewer.editElement(firstSignon, 1);
		// CellEditor[] cellEditors = tableViewer.getCellEditors();
		// TextCellEditor passwordEditor = (TextCellEditor) cellEditors[1];
		// enterText((Text) passwordEditor.getControl(), "Cricket11Players");
		// assertEquals("Cricket11Players",firstSignon.getPassword());
		//		
	}

	public void testScenario08_01() {
		// // Verify that binding to a Collection property (rather than an
		// array) works without specifying data type
		// TableViewerDescription tableViewerDescription = new
		// TableViewerDescription(
		// tableViewer);
		// tableViewerDescription.addEditableColumn("userId");
		// tableViewerDescription.addEditableColumn("password");
		// getDbc().bind(tableViewerDescription,
		// new Property(catalog, "signons"), null);
		// Signon firstSignon = (Signon) catalog.getSignons().get(0);
		// // Verify the UI matches the model
		// TableItem firstTableItem = tableViewer.getTable().getItem(0);
		// assertEquals(firstTableItem.getText(1),firstSignon.getPassword());
		// // Change the model and ensure the UI refreshes
		// firstSignon.setPassword("Eclipse123Rocks");
		// assertEquals("Eclipse123Rocks",firstSignon.getPassword());
		// assertEquals(firstTableItem.getText(1),firstSignon.getPassword());
		// // Change the GUI and ensure the model refreshes
		// tableViewer.editElement(firstSignon, 1);
		// CellEditor[] cellEditors = tableViewer.getCellEditors();
		// TextCellEditor passwordEditor = (TextCellEditor) cellEditors[1];
		// enterText((Text) passwordEditor.getControl(), "Cricket11Players");
		// assertEquals("Cricket11Players",firstSignon.getPassword());
		//		
	}

	public void testScenario09() {
		// // Verify that nested properties work. Catalog has adventures.
		// Adventure has defaultLodging. Loding has name.
		// TableViewerDescription tableViewerDescription = new
		// TableViewerDescription(tableViewer);
		// tableViewerDescription.addColumn("name");
		// tableViewerDescription.addColumn("defaultLodging.name");
		// getDbc().bind(tableViewerDescription,new Property(category,
		// "adventures"),null);
		//		
	}
	/**
	 * public void testScenario10(){ // Verify that for TIME_EARLY updating
	 * occurs on a per key basic for a TextCellEditor // Show that converters
	 * work for table columns Account[] accounts = catalog.getAccounts();
	 * Account firstAccount = accounts[0];
	 * SampleData.getSWTObservableFactory().setUpdateTime(DataBindingContext.TIME_EARLY);
	 * TableViewerDescription tableViewerDescription = new
	 * TableViewerDescription(tableViewer);
	 * tableViewerDescription.addEditableColumn("lastName");
	 * tableViewerDescription.addColumn("lastName");
	 * getDbc().bind(tableViewerDescription,new Property(catalog, "accounts"),
	 * null); // Verify that the first account is shown in the first row with
	 * the last name correctly
	 * assertEquals(tableViewer.getTable().getItem(0).getData(),firstAccount);
	 * assertEquals(tableViewer.getTable().getItem(0).getText(0),firstAccount.getLastName());
	 * assertEquals(tableViewer.getTable().getItem(0).getText(1),firstAccount.getLastName()); //
	 * Create a cell editor over the first column
	 * tableViewer.editElement(firstAccount, 0); // Set the text property of the
	 * cell editor which is now active over the "firstName" column CellEditor[]
	 * cellEditors = tableViewer.getCellEditors(); TextCellEditor
	 * lastNameCellEditor = (TextCellEditor) cellEditors[0];
	 * ((Text)lastNameCellEditor.getControl()).setText("E"); // Verify that the
	 * key press goes to the model assertEquals(firstAccount.getLastName(),"E"); }
	 */
}

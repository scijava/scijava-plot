package net.imagej.ui.swing.widget;

import org.scijava.table.Table;
import org.scijava.table.TableDisplay;
import org.scijava.display.DisplayService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.swing.widget.SwingInputWidget;
import org.scijava.widget.InputWidget;
import org.scijava.widget.WidgetModel;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

/**
 * @author Matthias Arzt
 */
@Plugin(type = InputWidget.class)
public class SwingTableWidget extends SwingInputWidget<Table<?,?>>{

	@Parameter
	DisplayService displayService;

	private JComboBox<TableItem> tableComboBox;

	@Override
	protected void doRefresh() {
		get().setValue(getValue());
	}

	@Override
	public void set(final WidgetModel model) {
		super.set(model);
		initializeTableComboBox();
		updateSelectedTable();
	}

	private void initializeTableComboBox() {
		List<TableDisplay> tableDisplays = displayService.getDisplaysOfType(TableDisplay.class);
		Vector<TableItem> tables = new Vector<>();
		for(TableDisplay tableDisplay : tableDisplays)
			tables.add(new TableItem(tableDisplay));
		tableComboBox = new JComboBox<>(tables);
		tableComboBox.addActionListener(e -> updateSelectedTable());
		getComponent().add(tableComboBox);
	}

	private void updateSelectedTable() {
		WidgetModel model = get();
		model.setValue(getValue());
	}

	@Override
	public boolean supports(final WidgetModel model) { return model.isType(Table.class); }

	@Override
	public Table<?, ?> getValue() {
		TableItem selected = (TableItem) tableComboBox.getSelectedItem();
		return (selected != null) ? selected.get() : null;
	}

	private static class ComboBoxItem<E> {

		final String label;
		final E value;

		ComboBoxItem(String label, E value) {
			this.label = label;
			this.value = value;
		}

		@Override
		public String toString() { return label; }

		public E get() { return value; }

	}

	private static class TableItem extends ComboBoxItem<Table<?,?>> {
		TableItem(TableDisplay display) { super(display.getName(), display.get(0)); }
	}

}

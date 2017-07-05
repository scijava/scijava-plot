package net.imagej.ui.swing.widget;

import org.scijava.module.MethodCallException;
import org.scijava.plugin.Plugin;
import org.scijava.ui.swing.widget.SwingInputWidget;
import org.scijava.widget.InputWidget;
import org.scijava.widget.WidgetModel;

import javax.swing.*;

/**
 * @author Matthias Arzt
 */
@Plugin(type = InputWidget.class)
public class SwingMutableChoicesWidget<T> extends SwingInputWidget<MutableChoices<T>> {

	private JComboBox<ComboBoxItem<T>> comboBox;
	private DefaultComboBoxModel<ComboBoxItem<T>> comboBoxModel;
	private MyMutableChoices<T> value;

	@Override
	protected void doRefresh() {
		comboBox.setSelectedItem(value.value);
	}

	@Override
	public void set(final WidgetModel model) {
		super.set(model);
		initializeTableComboBox();
		initializeMutableChoices(model);
	}

	private void initializeTableComboBox() {
		comboBoxModel = new DefaultComboBoxModel<>();
		comboBox = new JComboBox<>(comboBoxModel);
		comboBox.addActionListener(e -> updateSelection());
		getComponent().add(comboBox);
	}

	private void initializeMutableChoices(WidgetModel model) {
		value = new MyMutableChoices<>(this);
		model.setValue(value);
		try {
			model.getItem().initialize(model.getModule());
			model.getPanel().refresh();
		} catch (MethodCallException e) {
			e.printStackTrace();
		}
	}

	private void updateSelection() {
		get().setValue(getValue());
	}

	@Override
	public boolean supports(final WidgetModel model) {
		return model.isType(MutableChoices.class);
	}

	@Override
	public MutableChoices<T> getValue() {
		ComboBoxItem<T> selectedItem = (ComboBoxItem<T>) comboBox.getSelectedItem();
		value.value = (selectedItem != null) ? selectedItem.get() : null;
		return value;
	}

	public void setChoices(Iterable<T> choices, MutableChoices.PrettyPrinter printer) {
		comboBoxModel.removeAllElements();
		if(! get().getItem().isRequired())
			comboBoxModel.addElement(new ComboBoxItem<T>("", null));
		for(T choice : choices)
			comboBoxModel.addElement(new ComboBoxItem<T>(printer.toString(choice), choice));
	}

	static private class MyMutableChoices<T> implements MutableChoices<T> {

		SwingMutableChoicesWidget<T> widget;
		private T value;
		private Iterable<T> choices;

		MyMutableChoices(SwingMutableChoicesWidget<T> widget) {
			this.widget = widget;
		}

		void detach() {
			widget = null;
		}

		@Override
		public void setChoices(Iterable<T> choices, PrettyPrinter<T> printer) {
			if (widget != null)
				widget.setChoices(choices, printer);
		}

		@Override
		public T get() {
			return value;
		}

	}

	static private class ComboBoxItem<E> {

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
}

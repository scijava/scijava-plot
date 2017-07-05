package net.imagej.ui.swing.widget;

import org.scijava.module.MethodCallException;
import org.scijava.plugin.Plugin;
import org.scijava.ui.swing.widget.SwingInputWidget;
import org.scijava.widget.InputWidget;
import org.scijava.widget.WidgetModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Matthias Arzt
 */
@Plugin(type = InputWidget.class)
public class SwingMultipleChoicesWidget<T> extends SwingInputWidget<MultipleChoices<T>> {

	Map<JCheckBox, T> checkBoxes = new HashMap<>();

	MyMultipleChoices<T> result;

	@Override
	public void set(final WidgetModel model) {
		super.set(model);
		getComponent().setLayout(new BoxLayout(getComponent(), BoxLayout.PAGE_AXIS));
		result = new MyMultipleChoices<T>(this);
		get().setValue(result);
		try {
			model.getItem().initialize(model.getModule());
			model.getPanel().refresh();
		} catch (MethodCallException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doRefresh() {
		checkBoxes.forEach((checkBox, choice) -> checkBox.setSelected(result.get(choice)));
	}

	@Override
	public MultipleChoices<T> getValue() {
		return result;
	}

	@Override
	public boolean supports(final WidgetModel model) {
		return model.isType(MultipleChoices.class);
	}

	private void setChoices(Iterable<T> choices, MultipleChoices.PrettyPrinter<T> prettyPrinter) {
		JPanel panel = getComponent();
		for(JCheckBox oldCheckBox : checkBoxes.keySet())
			panel.remove(oldCheckBox);
		checkBoxes.clear();
		result.get().clear();
		if(choices != null)
			for(T choice : choices) {
				String text = prettyPrinter.toString(choice);
				JCheckBox checkBox = new JCheckBox(text);
				checkBoxes.put(checkBox, choice);
				checkBox.addActionListener(e -> checkBoxChanged(e));
				panel.add(checkBox);
			}
		panel.revalidate();
	}

	private void checkBoxChanged(ActionEvent e) {
		JCheckBox source = (JCheckBox) e.getSource();
		T choice = checkBoxes.get(source);
		result.set(choice, source.isSelected());
		get().setValue(result);
	}

	static private class MyMultipleChoices<T> implements MultipleChoices<T> {

		final SwingMultipleChoicesWidget<T> widget;

		Set<T> selected;

		private MyMultipleChoices(SwingMultipleChoicesWidget<T> widget) {
			this.widget = widget;
			selected = new HashSet<T>();
		}

		@Override
		public void setChoices(Iterable<T> choices, PrettyPrinter<T> prettyPrinter) {
			widget.setChoices(choices, prettyPrinter);
		}

		@Override
		public boolean get(T value) {
			return selected.contains(value);
		}

		@Override
		public void set(T choice, boolean value) {
			if(value)
				selected.add(choice);
			else
				selected.remove(choice);
		}

		@Override
		public Set<T> get() {
			return selected;
		}

		@Override
		public void set(Set<T> s) {
			selected.clear();
			selected.addAll(s);
		}

	}

}

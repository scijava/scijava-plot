package net.imagej.ui.swing.widget;

import java.util.Set;

/**
 * @author Matthais Arzt
 */
public interface MultipleChoices<T> {

	void setChoices(Iterable<T> choices, PrettyPrinter<T> prettyPrinter);

	boolean get(T choice);

	Set<T> get();

	void set(Set<T> selected);

	void set(T choice, boolean value);

	interface PrettyPrinter<T> {
		String toString(T value);
	}

}

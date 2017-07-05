package net.imagej.ui.swing.widget;

/**
 * @author Matthias Arzt
 */
public interface MutableChoices<T> {

	T get();

	default void setChoices(Iterable<T> choices) { setChoices(choices, v -> v.toString()); }

	void setChoices(Iterable<T> choices, PrettyPrinter<T> printer);

	interface PrettyPrinter<T> {
		String toString(T value);
	}
}

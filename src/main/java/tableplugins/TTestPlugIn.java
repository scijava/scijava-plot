package tableplugins;

import org.scijava.table.Column;
import org.scijava.table.Table;
import org.apache.commons.math3.stat.inference.TTest;
import org.scijava.ItemIO;
import org.scijava.ItemVisibility;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import net.imagej.ui.swing.widget.MutableChoices;

@Plugin(type = Command.class, menuPath="Table>Calculate t-Test")
public class TTestPlugIn implements Command {

	static final private String UNPAIRED = "unpaired t-test";
	static final private String PAIRED = "paired t-test";
	static final private String HOMOSCEDASTIC = "homoscedastic t-test";

	@Parameter(label = "Select t-Test", choices = {UNPAIRED, PAIRED, HOMOSCEDASTIC})
	public String kindOfTTest = UNPAIRED;

	@Parameter(label = "Table", callback = "tableChanged")
	public Table<Column<?>, ?> table;

	@Parameter(label = "Column with first sample", initializer = "tableChanged")
	public MutableChoices<Column<Double>> firstColumn;

	@Parameter(label = "Second with second sample", initializer = "tableChanged")
	public MutableChoices<Column<Double>> secondColumn;

	@Parameter(label = "Properties", visibility = ItemVisibility.MESSAGE)
	private final String LABEL_PROPERTIES = "two-sampled, two-tailed t-tests comparing mean";

	@Parameter(label = "p-value", type = ItemIO.OUTPUT)
	public double p_value;

	private void tableChanged() {
		Iterable<Column<Double>> l = TablePluginUtils.filterDoubleColumns(table);
		if(firstColumn != null)
			firstColumn.setChoices(l, c -> c.getHeader());
		if(secondColumn != null)
			secondColumn.setChoices(l, c -> c.getHeader());
	}

	@Override
	public void run() {
		Column<Double> s1 = firstColumn.get();
		Column<Double> s2 = secondColumn.get();
		switch (kindOfTTest) {
			case UNPAIRED:
				p_value = tTest(s1,s2);
				return;
			case PAIRED:
				p_value = pairedTTest(s1,s2);
				return;
			case HOMOSCEDASTIC:
				p_value = homoscedasticTTest(s1,s2);
				return;
			default:
				throw new IllegalArgumentException();
		}
	}

	static public double tTest(Column<Double> sample1, Column<Double> sample2) {
		return new TTest().tTest(TablePluginUtils.toArray(sample1), TablePluginUtils.toArray(sample2));
	}

	static public double pairedTTest(Column<Double> sample1, Column<Double> sample2) {
		return new TTest().pairedTTest(TablePluginUtils.toArray(sample1), TablePluginUtils.toArray(sample2));
	}

	static public double homoscedasticTTest(Column<Double> sample1, Column<Double> sample2) {
		return new TTest().homoscedasticTTest(TablePluginUtils.toArray(sample1), TablePluginUtils.toArray(sample2));
	}
}

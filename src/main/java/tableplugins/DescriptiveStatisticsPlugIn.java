package tableplugins;

import org.scijava.table.*;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.display.Display;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Command.class, menuPath="Table>Calculate Descriptive Statistics")
public class DescriptiveStatisticsPlugIn implements Command {

	@Parameter
	public Display<Table<Column<?>,?>> activeTableDisplay;

	@Parameter(label = "Descriptive Statistics", type = ItemIO.OUTPUT)
	public Table<?,?> output;

	@Override
	public void run() {
		GenericTable table = setupResultTable();
		for(Column<Double> column : TablePluginUtils.filterDoubleColumns(activeTableDisplay.get(0)))
			table.add(setupStatisticsColumn(column));
		output = table;
	}

	static private GenericTable setupResultTable() {
		GenericTable table = new DefaultGenericTable();
		table.setRowCount(8);
		table.setRowHeader(0,"mean");
		table.setRowHeader(1,"standard deviation");
		table.setRowHeader(2,"variance");
		table.setRowHeader(3,"min");
		table.setRowHeader(4,"max");
		table.setRowHeader(5,"sum");
		table.setRowHeader(6,"median");
		table.setRowHeader(7,"N");
		return table;
	}

	private static DoubleColumn setupStatisticsColumn(Column<Double> column) {
		SummaryStatistics stat = calculateSummaryStatistics(column);
		double median = calculateMean(column);
		DoubleColumn c = new DoubleColumn();
		c.setHeader(column.getHeader());
		c.add(stat.getMean());
		c.add(stat.getStandardDeviation());
		c.add(stat.getVariance());
		c.add(stat.getMin());
		c.add(stat.getMax());
		c.add(stat.getSum());
		c.add(median);
		c.add((double) stat.getN());
		return c;
	}

	public static double calculateMean(Column<Double> column) {
		return new Median().evaluate(TablePluginUtils.toArray(column));
	}

	public static SummaryStatistics calculateSummaryStatistics(Column<Double> column) {
		SummaryStatistics stat = new SummaryStatistics();
		for(double x : column)
			stat.addValue(x);
		return stat;
	}

}

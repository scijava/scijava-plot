package tableplugins;

import net.imagej.table.Column;
import net.imagej.table.DoubleColumn;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Matthias Arzt
 */
public class TablePluginUtils {

	static public Iterable<Column<Double>> filterDoubleColumns(Iterable<Column<?>> input) {
		List output = new LinkedList();
		if(input != null)
			for(Column<?> col : input)
				if(col.getType().equals(Double.class))
					output.add(col);
		return output;
	}

	static public double[] toArray(Column<Double> column) {
		if(column instanceof DoubleColumn)
			return ((DoubleColumn) column).getArray();

		double[] result = new double[column.size()];
		for(int i = 0, n = column.size(); i < n; i++)
			result[i] = column.get(i);
		return result;
	}

}

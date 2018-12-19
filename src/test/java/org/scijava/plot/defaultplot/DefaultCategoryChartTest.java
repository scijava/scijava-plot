package org.scijava.plot.defaultplot;

import org.junit.Test;
import org.scijava.plot.BarSeries;
import org.scijava.plot.CategoryChart;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

public class DefaultCategoryChartTest
{
	@Test
	public void testIgnoreCategory() {
		CategoryChart chart = newChart();
		final List< String > categories = Arrays.asList( "a", "c", "b" );
		chart.categoryAxis().setManualCategories( categories );
		assertEquals( categories, chart.getCategories() );
	}

	@Test
	public void testAddCategory() {
		CategoryChart chart = newChart();
		final List< String > categories = Arrays.asList( "a", "x", "c", "b" );
		chart.categoryAxis().setManualCategories( categories );
		assertEquals( categories, chart.getCategories() );
	}

	@Test
	public void testSortManuallySpecifiedCategories() {
		CategoryChart chart = newChart();
		final List< String > categories = Arrays.asList( "a", "x", "c", "b" );
		chart.categoryAxis().setManualCategories( categories );
		chart.categoryAxis().setOrder( String::compareTo );
		assertEquals( Arrays.asList( "a", "b", "c", "x" ), chart.getCategories() );
	}

	@Test
	public void testCategorySorting() {
		CategoryChart chart = newChart();
		chart.categoryAxis().<String>setOrder( Comparator.reverseOrder() );
		assertEquals( Arrays.asList( "d", "c", "b", "a" ), chart.getCategories() );
	}

	private CategoryChart newChart() {
		CategoryChart chart = new DefaultCategoryChart<>();

		Map<String, Double> data = new TreeMap<>();
		data.put("a", 1.0);
		data.put("b", 2.0);
		data.put("c", 3.0);
		data.put("d", 4.0);

		BarSeries bars = chart.addBarSeries();
		bars.setValues(data);

		return chart;
	}
}

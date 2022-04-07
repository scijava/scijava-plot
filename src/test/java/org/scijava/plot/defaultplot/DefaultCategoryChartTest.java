/*-
 * #%L
 * Plots for SciJava.
 * %%
 * Copyright (C) 2017 - 2022 SciJava developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
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

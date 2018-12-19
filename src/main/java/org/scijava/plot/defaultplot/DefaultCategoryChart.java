/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2016 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
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

import org.scijava.plot.BarSeries;
import org.scijava.plot.BoxSeries;
import org.scijava.plot.CategoryAxis;
import org.scijava.plot.CategoryChart;
import org.scijava.plot.CategoryChartItem;
import org.scijava.plot.LineSeries;
import org.scijava.plot.NumberAxis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Default implementation of {@link CategoryChart}.
 *
 * @author Matthias Arzt
 */
public class DefaultCategoryChart<C> extends AbstractPlot implements CategoryChart {

	private final NumberAxis valueAxis;

	private final CategoryAxis categoryAxis;

	private final List<CategoryChartItem > items;

	public DefaultCategoryChart() {
		valueAxis = new DefaultNumberAxis();
		categoryAxis = new DefaultCategoryAxis();
		items = new LinkedList<>();
	}

	// -- CategoryChart methods --

	@Override
	public LineSeries addLineSeries() {
		return addItem(new DefaultLineSeries());
	}

	@Override
	public BarSeries addBarSeries() {
		return addItem(new DefaultBarSeries());
	}

	@Override
	public BoxSeries addBoxSeries() {
		return addItem(new DefaultBoxSeries());
	}

	@Override
	public NumberAxis numberAxis() {
		return valueAxis;
	}

	@Override
	public CategoryAxis categoryAxis() {
		return categoryAxis;
	}

	@Override
	public List<CategoryChartItem> getItems() {
		return Collections.unmodifiableList(items);
	}

	@Override
	public List<Object> getCategories()
	{
		List< Object > categories = unsortedCategories();
		return sortCategories( categories );

	}

	private List< Object > sortCategories( List< Object > categories )
	{
		if(categoryAxis.hasOrder())
		{
			if( ! ( categories instanceof ArrayList ) )
				categories = new ArrayList<>( categories );
			categories.sort( ( Comparator ) categoryAxis.getOrder() );
		}
		return categories;
	}

	private List< Object > unsortedCategories()
	{
		if(categoryAxis.hasManualCategories())
		{
			return categoryAxis.getManualCategories();
		}
		else
		{
			Set< Object > set = new HashSet<>();
			for ( CategoryChartItem item : items )
				set.addAll( item.getCategories() );
			return new ArrayList<>( set );
		}
	}

	// -- private helper methods --

	private <T extends CategoryChartItem> T addItem(T value) {
		items.add(value);
		return value;
	}
}

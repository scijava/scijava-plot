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

package net.imagej.plot.defaultplot;

import net.imagej.plot.CategoryAxis;
import net.imagej.plot.CategoryChart;
import net.imagej.plot.CategoryChartItem;

import java.util.*;

/**
 * Default Implementation of CategoryAxis."
 *
 * @author Matthias Arzt
 */
class DefaultCategoryAxis extends AbstractLabeled implements CategoryAxis {

	private final CategoryChart chart;

	private List categories = null;

	private Comparator comparator = null;

	DefaultCategoryAxis(CategoryChart chart) {
		this.chart = chart;
	}

	// -- CategoryAxis methods --

	@Override
	public void setManualCategories(List<?> categories) {
		this.categories = Objects.requireNonNull(categories);
	}

	@Override
	public void clearManualCategories() {
		this.categories = null;
	}

	@Override
	public boolean hasManualCategories() {
		return categories != null;
	}

	@Override
	public void setOrder(Comparator comparator) {
		this.comparator = Objects.requireNonNull(comparator);
	}

	@Override
	public void clearOrder() {
		this.comparator = null;
	}

	@Override
	public List<Object> getCategories() {
		List<Object> result = getCategoriesDefaultOrder();
		if(comparator != null)
			result.sort(comparator);
		return result;
	}

	// -- private helper methods

	private List<Object> getCategoriesDefaultOrder() {
		if(categories == null) {
			Set<Object> allCategories = newEmptySetOfCategories();
			for (CategoryChartItem item : chart.getItems())
				allCategories.addAll(item.getCategories());
			return new ArrayList<>(allCategories);
		} else
			return new ArrayList<>(categories); // Make copy to avoid the list passed to setManualCategories to be sorted.
	}

	private Set<Object> newEmptySetOfCategories() {
		return new HashSet<>();
	}
}

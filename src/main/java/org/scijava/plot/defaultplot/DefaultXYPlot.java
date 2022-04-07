/*
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

import org.scijava.plot.NumberAxis;
import org.scijava.plot.XYPlot;
import org.scijava.plot.XYPlotItem;
import org.scijava.plot.XYSeries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The default implementation of the {@link XYPlot} interface.
 *
 * @author Matthias Arzt
 */
public class DefaultXYPlot extends AbstractPlot implements XYPlot {

	private final DefaultNumberAxis xAxis;

	private final DefaultNumberAxis yAxis;

	private final List<XYPlotItem > items;

	public DefaultXYPlot() {
		xAxis = new DefaultNumberAxis();
		yAxis = new DefaultNumberAxis();
		items = new LinkedList<>();
	}

	// -- XYPlot methods --

	@Override
	public XYSeries addXYSeries() {
		XYSeries result = new DefaultXYSeries();
		items.add(result);
		return result;
	}

	@Override
	public NumberAxis xAxis() {
		return xAxis;
	}

	@Override
	public NumberAxis yAxis() {
		return yAxis;
	}

	@Override
	public List<XYPlotItem> getItems() {
		return Collections.unmodifiableList(items);
	}

}

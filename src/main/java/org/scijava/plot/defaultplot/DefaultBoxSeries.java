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

import org.scijava.plot.BoxSeries;
import org.scijava.util.ColorRGB;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Default implementation of {@link BoxSeries}.
 *
 * @author Matthias Arzt
 */
class DefaultBoxSeries extends AbstractChartItem implements BoxSeries {

	private ColorRGB color = null;

	private Map<?, Collection<Double>> values = Collections.emptyMap();

	// -- BoxSeries methods --

	@Override
	public Map<?, Collection<Double>> getValues() {
		return values;
	}

	@Override
	public void setValues(Map<?, ? extends Collection<Double>> values) {
		this.values = Collections.unmodifiableMap(values);
	}

	@Override
	public ColorRGB getColor() {
		return color;
	}

	@Override
	public void setColor(ColorRGB color) {
		this.color = color;
	}

	// -- CategoryChartItem methods --

	@Override
	public Collection<Object> getCategories() {
		return Collections.unmodifiableCollection( values.keySet() );
	}
}

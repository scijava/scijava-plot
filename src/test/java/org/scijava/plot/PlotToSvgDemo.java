package org.scijava.plot;

import org.scijava.Context;
import org.scijava.io.IOService;
import org.scijava.plugin.Parameter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author
 */
public class PlotToSvgDemo {

	@Parameter
	private PlotService plotService;

	@Parameter
	private IOService ioService;

	public static void main(String... args) throws IOException {
		PlotToSvgDemo demo = new PlotToSvgDemo();
		new Context().inject(demo);
		demo.run();
	}

	private void run() throws IOException {
		Path path = Paths.get(System.getProperty("user.home"), "chart.svg");
		Plot plot = getExamplePlot();
		ioService.save(plot, path.toString());
	}

	private Plot getExamplePlot() {
		XYPlot plot = plotService.newXYPlot();
		plot.setTitle("Hello World!");
		plot.xAxis().setLabel("x");
		plot.yAxis().setLabel("y");
		List<Double> xs = IntStream.rangeClosed(0, 100).mapToObj(x -> (double) x * 2. * Math.PI / 100.).collect(Collectors.toList());
		List<Double> ys = xs.stream().map(Math::sin).collect(Collectors.toList());
		XYSeries series = plot.addXYSeries();
		series.setLabel("y = sin(x)");
		series.setValues( xs, ys );
		return plot;
	}
}

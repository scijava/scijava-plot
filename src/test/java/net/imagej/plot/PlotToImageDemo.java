package net.imagej.plot;

import ij.ImagePlus;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.ARGBType;
import org.scijava.Context;
import org.scijava.convert.ConvertService;
import org.scijava.plugin.Parameter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Demonstrates how to convert an {@link Plot} to {@link Img} and {@link ImagePlus}.
 *
 * @author Matthias Arzt
 */
public class PlotToImageDemo {

	@Parameter
	PlotService plotService;

	@Parameter
	ConvertService convertService;

	private void run() {
		XYPlot plot = getXyPlot();

		// convert to Img
		Img<ARGBType> img = convertService.convert(plot, Img.class);
		ImageJFunctions.show(img);

		// convert to ImagePlus
		ImagePlus imagePlus = convertService.convert(plot, ImagePlus.class);
		imagePlus.show();
	}

	private XYPlot getXyPlot() {
		XYPlot plot = plotService.newXYPlot();
		XYSeries series = plot.addXYSeries();
		List<Double> xs = IntStream.rangeClosed(0, 100).mapToObj(x -> (double) x * 2. * Math.PI / 100.).collect(Collectors.toList());
		List<Double> ys = xs.stream().map(Math::sin).collect(Collectors.toList());
		series.setValues( xs, ys );
		return plot;
	}

	public static void main(String... args) {
		PlotToImageDemo demo = new PlotToImageDemo();
		new Context().inject(demo);
		demo.run();
	}
}

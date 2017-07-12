package net.imagej.plot.convert;

import ij.IJ;
import ij.ImagePlus;
import net.imagej.plot.AbstractPlot;
import org.jfree.chart.JFreeChart;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.ConversionRequest;
import org.scijava.convert.ConvertService;
import org.scijava.convert.Converter;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Converter plugin, that converts an {@link AbstractPlot} to {@link ImagePlus}.
 *
 * @author Matthias Arzt
 * @see ConvertService
 */
@Plugin(type = Converter.class, priority = Priority.NORMAL_PRIORITY)
public class PlotToImagePlusConverter extends AbstractConverter<AbstractPlot, ImagePlus> {

	@Parameter
	ConvertService convertService;

	@Override
	public boolean canConvert(ConversionRequest request) {
		return ImagePlus.class.equals(request.destClass()) &&
				AbstractPlot.class.isAssignableFrom(request.sourceClass()) &&
				convertService.supports(new ConversionRequest(
						request.sourceObject(), request.sourceType(), JFreeChart.class));
	}

	@Override
	public <T> T convert(Object o, Class<T> aClass) {
		if(o instanceof AbstractPlot && ImagePlus.class.equals(aClass)) {
			@SuppressWarnings("unchecked")
			T t = (T) toImagePlus((AbstractPlot) o);
			return t;
		}
		return null;
	}

	private ImagePlus toImagePlus(AbstractPlot plot) {
		JFreeChart chart = convertService.convert(plot, JFreeChart.class);
		ImagePlus imp = IJ.createImage(plot.getTitle(), "RGB", plot.getPreferredWidth(), plot.getPreferredHeight(), 1);
		BufferedImage image = imp.getBufferedImage();
		chart.draw(image.createGraphics(), new Rectangle2D.Float(0, 0, imp.getWidth(), imp.getHeight()));
		imp.setImage(image);
		return imp;
	}

	@Override
	public Class<ImagePlus> getOutputType() {
		return ImagePlus.class;
	}

	@Override
	public Class<AbstractPlot> getInputType() {
		return AbstractPlot.class;
	}
}

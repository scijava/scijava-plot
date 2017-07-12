package net.imagej.plot.convert;

import ij.ImagePlus;
import net.imagej.plot.AbstractPlot;
import net.imagej.plot.CategoryChart;
import net.imagej.plot.PlotService;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.ARGBType;
import org.junit.Test;
import org.scijava.Context;
import org.scijava.convert.ConvertService;

import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link PlotToImgConverter} and {@link PlotToImagePlusConverter}
 *
 * @author Matthias Arzt
 */
public class PlotToImageConverterTest {

	private final Context context = new Context(PlotService.class, ConvertService.class);

	private final ConvertService convertService = context.service(ConvertService.class);

	private final PlotService plotService = context.service(PlotService.class);

	@Test
	public void testCanConvertCategoryChartToImagePlus() {
		testCanConvertCategoryChartTo(ImagePlus.class);
	}

	@Test
	public void testCanConvertCategoryChartToImg() {
		testCanConvertCategoryChartTo(Img.class);
	}

	@Test
	public void testWontConvertAbstractPlotToImagePlus() {
		testWontConvertAbstractPlotTo(ImagePlus.class);
	}

	@Test
	public void testWontConvertAbstractPlotToImg() {
		testWontConvertAbstractPlotTo(Img.class);
	}

	public void testCanConvertCategoryChartTo(Class<?> destClass) {
		CategoryChart<String> chart = plotService.newCategoryChart(String.class);
		assertTrue(convertService.supports(chart, destClass));
		assertTrue(convertService.supports(chart, (Type) destClass));
	}

	public void testWontConvertAbstractPlotTo(Class<?> destClass) {
		AbstractPlot ap = new CustomAbstractPlot();
		assertFalse(convertService.supports(ap, destClass));
		assertFalse(convertService.supports(ap, (Type) destClass));
	}

	@Test
	public void testConversionToImagePlus() {
		// setup
		CategoryChart chart = plotService.newCategoryChart(String.class);
		chart.setTitle("Hello World!");
		chart.setPreferredSize(12,23);
		// process
		ImagePlus imp = convertService.convert(chart, ImagePlus.class);
		// test
		assertEquals(chart.getTitle(), imp.getTitle());
		assertEquals(chart.getPreferredWidth(), imp.getWidth());
		assertEquals(chart.getPreferredHeight(), imp.getHeight());
	}

	@Test
	public void testConversionToImg() {
		// setup
		CategoryChart chart = plotService.newCategoryChart(String.class);
		chart.setTitle("Hello World!");
		chart.setPreferredSize(12,23);
		// process
		Img<ARGBType> img = convertService.convert(chart, Img.class);
		// test
		assertEquals(chart.getPreferredWidth(), img.max(0) + 1);
		assertEquals(chart.getPreferredHeight(), img.max(1) + 1);
	}

	// -- Helper class --

	class CustomAbstractPlot implements AbstractPlot {
		@Override
		public void setTitle(String title) {

		}

		@Override
		public String getTitle() {
			return null;
		}

		@Override
		public void setPreferredSize(int width, int height) {

		}

		@Override
		public int getPreferredWidth() {
			return 0;
		}

		@Override
		public int getPreferredHeight() {
			return 0;
		}
	}
}

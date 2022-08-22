[![](https://github.com/scijava/scijava-plot/actions/workflows/build-main.yml/badge.svg)](https://github.com/scijava/scijava-plot/actions/workflows/build-main.yml)

# SciJava PlotService

UI-independent framework for creating scientific plots.


# Usage and Demos

Run the following Groovy snippet from the [Script Editor](https://github.com/scijava/script-editor):

```groovy
#@ UIService uiService
#@ PlotService plotService

plot = plotService.newXYPlot()
plot.setTitle("XY Plot")
series = plot.addXYSeries()
series.setLabel("Series 1")
series.setValues([1,2,3], [7,3,9])
uiService.show(plot)
```

Several Java demos (XY, BoxPlot, Category plots, etc.) are available in [scijava-ui-swing](https://github.com/scijava/scijava-ui-swing/tree/909e16b5cbf1b92e1d8ccce96a3521050c93174a/src/test/java/org/scijava/ui/swing/viewer/plot).

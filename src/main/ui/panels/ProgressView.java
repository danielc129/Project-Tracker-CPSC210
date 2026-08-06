package ui.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.ProgressSnapshot;
import model.Project;
import ui.ProjectTrackerUI;

// The progress view screen for the project tracker UI
// shows a chart of completion percentage over time for a project
@ExcludeFromJacocoGeneratedReport
public class ProgressView extends JPanel  {
    private ProjectTrackerUI controller;
    private Project project;

    // EFFECTS: Creates a progress view for the given project, with 
    //          the given base UI controller 
    public ProgressView(ProjectTrackerUI controller, Project project) {
        this.controller = controller;
        this.project = project;
        GridBagConstraints gbConstraints = new GridBagConstraints();
        JButton exitProjectButton = new JButton("Back");
        exitProjectButton.addActionListener(e -> controller.updateProjectListView());
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.anchor = GridBagConstraints.LINE_START;
        add(exitProjectButton, gbConstraints);


        TimeSeriesCollection dataset = createDataset();
        
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Progress History", "Time", "Completion Percentage", dataset, true, true, false);
        ((DateAxis) ((XYPlot) chart.getPlot()).getDomainAxis()).setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        gbConstraints.gridy = 1;
        add(chartPanel, gbConstraints);
    }

    // EFFECTS: returns a TimeSeriesCollection representation of this project's progress history 
    private TimeSeriesCollection createDataset() {
        TimeSeries timeSeries = new TimeSeries("Completion Percentage");
        List<ProgressSnapshot> progressHistoryList = project.getProgressHistory();
        for (ProgressSnapshot snapshot : progressHistoryList) {
            Date date = Date.from(snapshot.getTime().atZone(ZoneId.systemDefault()).toInstant());
            timeSeries.add(new Millisecond(date), snapshot.getCompletionPercentage());
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(timeSeries);

        return dataset;
    }
}


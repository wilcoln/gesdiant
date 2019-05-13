package sample;

import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;


public class Histogram extends Parent {
    public Pane histogramBuilder(int nbInsc, int nbNoIns, int nbNoFini){
        Pane pane=new Pane();
        final CategoryAxis xAxis=new CategoryAxis();
        final NumberAxis yAxis=new NumberAxis();
        final BarChart<Number,String> barChart=new BarChart(xAxis,yAxis);
        barChart.setPrefSize(300,300);
        barChart.setTitle("Bilan Inscritption");
        xAxis.setLabel("Status Etudiants");
        yAxis.setLabel("Nombre d'étudiants");
        XYChart.Series serie1=new XYChart.Series();
        serie1.setName("Etudiants Inscrits");
        serie1.getData().add(new XYChart.Data<>("Inscrits",nbInsc));
        XYChart.Series serie2=new XYChart.Series();
        serie2.setName("Etudiants Non Inscrits");
        serie2.getData().add(new XYChart.Data<>("Non Inscrits",nbNoIns));
        XYChart.Series serie3=new XYChart.Series();
        serie3.setName("Pas terminé");
        serie3.getData().add(new XYChart.Data<>("Pas terminés",nbNoFini));
        barChart.getData().addAll(serie1,serie2,serie3);
        pane.getChildren().add(barChart);
        return pane;
    }
}

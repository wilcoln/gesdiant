package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CercleGraphics extends Parent {
    public Pane drawGraphics(int nbInscrit, int nbNinscrits, int nbPasFini){
        Pane pane=new Pane();
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("inscrits", nbInscrit),
                        new PieChart.Data("non inscrits", nbNinscrits),
                        new PieChart.Data("non terminés", nbPasFini));

        final PieChart chart = new PieChart();
        chart.setData(pieChartData);
        chart.setTitle("Bilan d'inscription");
        chart.setPrefSize(300,300);
        final Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: 12 algerian;");
        chart.getData().stream().forEach((data) -> {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    (MouseEvent e) -> {
                        caption.setTranslateX(e.getSceneX());
                        caption.setTranslateY(e.getSceneY());
                        caption.setText(String.valueOf((int)(data.getPieValue()))
                                + " étudiants");
                    });
        });

        pane.getChildren().addAll(chart, caption);
         return pane;
    }
}

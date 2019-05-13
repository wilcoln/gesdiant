package sample;

import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

public class Courbe extends Parent {
    public Pane courbeBuilder(int nbInsMsp,int nbInsGI,int nbInsGC, int nbInsGM,int nbInsGind,int nbInsGelec,
                              int nbInsGTel,
                              int nbNoInsMsp,int nbNoInsGI,int nbNoInsGC, int nbNoInsGM,int nbNoInsGind,int nbNoInsGelec,
                              int nbNoInsGTel,
                              int nbNoFiniMsp,int nbNoFiniGI,int nbNoFiniGC, int nbNoFiniGM,int nbNoFiniGind,int nbNoFiniGelec,
                              int nbNoFiniGTel)
    {
        Pane pane=new Pane();
        final CategoryAxis xAxis=new CategoryAxis();
        final NumberAxis yAxis=new NumberAxis();
        xAxis.setLabel("Niveaux");
        yAxis.setLabel("Nombre étudiants");
        final LineChart<String,Number> lineChart=new LineChart<String, Number>(xAxis,yAxis);
        lineChart.setTitle("Bilan d'inscription");
        lineChart.setCreateSymbols(false);
        lineChart.setPrefSize(300,300);
        XYChart.Series serie1=new XYChart.Series();
        serie1.setName("Inscrits");
        serie1.getData().add(new XYChart.Data<>("MSP",nbInsMsp));
        serie1.getData().add(new XYChart.Data<>("GI",nbInsGI));
        serie1.getData().add(new XYChart.Data<>("GC",nbInsGC));
        serie1.getData().add(new XYChart.Data<>("GM",nbInsGM));
        serie1.getData().add(new XYChart.Data<>("Gind",nbInsGind));
        serie1.getData().add(new XYChart.Data<>("GELEC",nbInsGelec));
        serie1.getData().add(new XYChart.Data<>("GTEL",nbInsGTel));
        XYChart.Series serie2=new XYChart.Series();
        serie2.setName("Pas inscrits");
        serie2.getData().add(new XYChart.Data<>("MSP",nbNoInsMsp));
        serie2.getData().add(new XYChart.Data<>("GI",nbNoInsGI));
        serie2.getData().add(new XYChart.Data<>("GC",nbNoInsGC));
        serie2.getData().add(new XYChart.Data<>("GM",nbNoInsGM));
        serie2.getData().add(new XYChart.Data<>("Gind",nbNoInsGind));
        serie2.getData().add(new XYChart.Data<>("GELEC",nbNoInsGelec));
        serie2.getData().add(new XYChart.Data<>("GTEL",nbNoInsGTel));
        XYChart.Series serie3=new XYChart.Series();
        serie3.setName("Inscriptions inachévées");
        serie3.getData().add(new XYChart.Data<>("MSP",nbNoFiniMsp));
        serie3.getData().add(new XYChart.Data<>("GI",nbNoFiniGI));
        serie3.getData().add(new XYChart.Data<>("GC",nbNoFiniGC));
        serie3.getData().add(new XYChart.Data<>("GM",nbNoFiniGM));
        serie3.getData().add(new XYChart.Data<>("Gind",nbNoFiniGind));
        serie3.getData().add(new XYChart.Data<>("GELEC",nbNoFiniGelec));
        serie3.getData().add(new XYChart.Data<>("GTEL",nbNoFiniGTel));
        lineChart.getData().addAll(serie1,serie2,serie3);
        pane.getChildren().add(lineChart);
        return pane;
    }


}

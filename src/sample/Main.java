package sample;
import java.net.MalformedURLException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.data.Provider;
import sample.models.EditStage;
import sample.models.Etudiant;

import java.sql.*;
import java.util.ArrayList;

public class Main extends Application {
    public static int nbIm = 0;
    private Provider provider;
    private ArrayList<Etudiant> etudiants;
    private ArrayList<Etudiant> toDisplay;
    private ToggleGroup sortMode = null;
    private Pagination navigationPane;
    private Pane pane;
    private int itemsPerPage = 6;
    private int sexinfo;
    private boolean valideinfo;
    private Stage primaryStage;

    public Main() throws SQLException, ClassNotFoundException {
        provider = new Provider();
        navigationPane = new Pagination(20);
        refreshEtudiants();
        toDisplay = etudiants;
    }

    public void refreshEtudiants() throws SQLException, ClassNotFoundException {
        etudiants = provider.query();
    }

    public void displayEtudiants() {
        int nb = (int)Math.ceil(toDisplay.size()*1.0/itemsPerPage);
        navigationPane = new Pagination(nb);
        // styling the navigation pane.... Incredible!!
        navigationPane.setMinWidth(900);
        navigationPane.setMaxHeight(540);
        navigationPane.setMinHeight(540);
        navigationPane.setMaxWidth(900);
        //
        navigationPane.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
        pane.getChildren().clear();
        pane.getChildren().add(navigationPane);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        VBox root = new VBox();
        Scene scene = new Scene(root, 1500, 750);
        scene.getStylesheets().add(getClass().getResource("deviantFX/css/deviantFX.min.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
        //----------------------------------------------------------------------------------------------
        StackPane header = new StackPane();
        root.getChildren().add(header);
        header.getStyleClass().add("header");
        Label titleApp = new Label("Gesdiant");
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("img/student.png")));
        icon.setFitHeight(64);
        icon.setFitWidth(64);
        titleApp.setGraphic(icon);
        titleApp.setGraphicTextGap(10);
        titleApp.getStyleClass().add("titleApp");
        header.getChildren().add(titleApp);
        header.setPadding(new Insets(5));
        // Fin Header
        StackPane searchContainer = new StackPane();
        searchContainer.getStyleClass().add("search-container");
        root.getChildren().add(searchContainer);
        TextField searchField = new TextField();
        searchField.getStyleClass().add("search-field");
        searchField.setPromptText("Rechercher !");
        searchField.setFocusTraversable(false);
        searchContainer.getChildren().add(searchField);
        // Fin Barre de recherche

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(20, 50, 50, 50));
        root.getChildren().add(hbox);
        VBox filterPane = new VBox();
        filterPane.setSpacing(10);
        filterPane.setAlignment(Pos.CENTER);
        hbox.getChildren().add(filterPane);
        hbox.setSpacing(50);
        //Setting the chart pane
        VBox chartPane = new VBox();
        ComboBox chartMode = new ComboBox();
        chartMode.getItems().addAll(
                "Historigramme",
                "Circulaire",
                "Courbes"
        );
        chartMode.setValue("Historigramme");
        chartPane.getChildren().add(chartMode);
        chartPane.setAlignment(Pos.CENTER);
        chartPane.setPrefWidth(400);
        chartPane.setPadding(new Insets(40));
        chartMode.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {
            switch (t1) {
                case "Historigramme":
                    chartPane.getChildren().remove(1);
                    chartPane.getChildren().add((new Histogram()).histogramBuilder(5,10,3));
                    break;
                case "Circulaire":
                    chartPane.getChildren().remove(1);
                    chartPane.getChildren().add((new CercleGraphics()).drawGraphics(5,10,3));
                    break;
                case "Courbes":
                    chartPane.getChildren().remove(1);
                    chartPane.getChildren().add((new Courbe())
                            .courbeBuilder(5,10,3,5,5,
                                    2, 6,3,3,3,
                                    3,3, 9,10,5,
                                    3,5,3,5,3,5));
                    break;
            }
        });
        //Setting the navigation Pane
        pane = new Pane();
        navigationPane = new Pagination((int)Math.ceil(etudiants.size()*1.0/6));
        pane.getChildren().add(navigationPane);
        navigationPane.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
        hbox.getChildren().add(pane);
        navigationPane.setMinWidth(900);
        navigationPane.setMaxHeight(540);
        navigationPane.setMinHeight(540);
        navigationPane.setMaxWidth(900);
        hbox.getChildren().add(chartPane);
        chartPane.getChildren().add((new Histogram()).histogramBuilder(5,10,3));
        //------------
        Label sortby = new Label("SEARCH OPTIONS");
        sortby.getStyleClass().addAll("sort-by");
        filterPane.getChildren().add(sortby);
        
        ToggleButton all = new ToggleButton("Tous");
        all.getStyleClass().addAll("btn", "btn-toggle", "btn-c");
        all.setToggleGroup(sortMode);

        ToggleButton male = new ToggleButton("Hommes");
        male.getStyleClass().addAll("btn", "btn-toggle", "btn-c");
        male.setToggleGroup(sortMode);

        ToggleButton valide = new ToggleButton(("Inscrits"));
        valide.getStyleClass().addAll("btn", "btn-toggle", "btn-c");
        valide.setToggleGroup(sortMode);

        ToggleButton female = new ToggleButton("Femmes");
        female.getStyleClass().addAll("btn", "btn-toggle", "btn-c");
        female.setToggleGroup(sortMode);

        filterPane.getChildren().addAll(all, male, female,valide);

        final ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll(
                "01 / page",
                "02 / page",
                "03 / page",
                "04 / page",
                "05 / page",
                "06 / page"
        );
        comboBox.setValue("06 / page");
        filterPane.getChildren().add(new Separator());
        filterPane.getChildren().add(comboBox);

        Button ajout = new Button("+");
        ajout.setFont(new Font("arial",30));
        ajout.getStyleClass().addAll("add","elevated");
        filterPane.getChildren().add(new Separator());
        filterPane.getChildren().add(ajout);
        ajout.setOnAction(e -> {
            nouvelEtudiant();
        });
        searchField.setOnKeyTyped(e -> {
            String contenu = searchField.getText();
            toDisplay = new ArrayList<>();
            for (Etudiant ev : etudiants) {
                if (ev.getNom().toUpperCase().contains(contenu.toUpperCase()) || ev.getPrenom().toUpperCase().contains(contenu.toUpperCase()))
                    toDisplay.add(ev);
            }
            displayEtudiants();
        });
        male.setOnAction(e -> {
            toDisplay = new ArrayList<>();
            for (Etudiant ev : etudiants) {
                if (ev.getSex().equals("M"))
                    toDisplay.add(ev);
            }
            displayEtudiants();
        });
        female.setOnAction(e -> {
            toDisplay = new ArrayList<>();
            for (Etudiant ev : etudiants) {
                if (ev.getSex().equals("F"))
                    toDisplay.add(ev);
            }
            displayEtudiants();
        });
        valide.setOnAction(e -> {
            toDisplay = new ArrayList<>();
            for (Etudiant ev : etudiants) {
                if (ev.getMontant()>0)
                    toDisplay.add(ev);
            }
            displayEtudiants();
        });
        all.setOnAction(e -> {
            toDisplay = etudiants;
            displayEtudiants();
        });
        comboBox.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {
            switch (t1) {
                case "01 / page":
                    itemsPerPage = 1;
                    break;
                case "02 / page":
                    itemsPerPage = 2;
                    break;
                case "03 / page":
                    itemsPerPage = 3;
                    break;
                case "04 / page":
                    itemsPerPage = 4;
                    break;
                case "05 / page":
                    itemsPerPage = 5;
                    break;
                case "06 / page":
                    itemsPerPage = 6;
                    break;
            }
            displayEtudiants();
        });
        //----------------------------------------------------------------------------------------------
        primaryStage.setTitle("Gestionnaire d'étudiants - Gesdiant");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        launch(args);
    }
    public FlowPane createPage(int pageIndex) {
        FlowPane box = new FlowPane();
        box.setHgap(20);
        box.setVgap(20);
        box.setPadding(new Insets(35, 20, 0, 50));
        int page = pageIndex * itemsPerPage;
        for (int i = page; i < page + itemsPerPage; i++) {
            if(i<toDisplay.size()){
                Item element = new Item(toDisplay.get(i));
                box.getChildren().add(element);
            }
        }
        return box;
    }

    class Item extends GridPane {
        private Label imageFrame = new Label();
        private Label nom;
        private Button delete = new Button("Delete");
        private Button edit = new Button("Edit");
        private Text infos[] = new Text[5];
        private String imageUrl = "img/student.png";

        public Item(Etudiant e) {
            double nbc = e.getNbc();
            nom = new Label(e.getNom() + " " + e.getPrenom());
            nom.getStyleClass().addAll("badge", "elevated");
            nom.setStyle("-fx-font-weight: bold;-fx-font-size: 13px; -fx-font-family: 'Arial Rounded MT Bold';" +
                    "-fx-min-width: 250px;-fx-text-fill: #ffc747");
            if(e.getMontant()>0){
                nom.setStyle("-fx-font-weight: bold;-fx-font-size: 13px; -fx-font-family: 'Arial Rounded MT Bold';" +
                        "-fx-min-width: 250px;-fx-text-fill: white");
                nom.getStyleClass().add("badge-success");
            }

            if (nbc < 0.3)
                imageFrame.getStyleClass().add("frame1");
            else if (nbc < 0.6)
                imageFrame.getStyleClass().add("frame2");
            else
                imageFrame.getStyleClass().add("frame3");
            infos[0] = new Text("AGE     : " + String.valueOf(e.getAge()) + " ans");
            infos[1] = new Text("GENRE   : " + e.getSex());
            infos[2] = new Text("NIVEAU  : " + String.valueOf(e.getNiveau()));
            infos[3] = new Text("DPTM    : " + e.getDepartement());
            infos[4] = new Text("ADRESSE : " + e.getAdresse());
            delete.getStyleClass().addAll("btn", "btn-danger", "btn-fix");
            edit.getStyleClass().addAll("btn", "btn-warning", "btn-fix");
            this.setPadding(new Insets(15));
            this.setHgap(20);
            this.setVgap(10);
            this.add(nom, 0, 0);
            this.add(imageFrame, 0, 1);
            this.setStyle("-fx-background-color: #e8e8e8");
            GridPane.setColumnSpan(nom, 2);
            imageFrame.setText(e.getNom().charAt(0) + "");
            imageFrame.setStyle("-fx-font-weight: bold;" +
                    "-fx-font-family:'French Script MT';" +
                    "-fx-font-size: 30px;");
            GridPane.setRowSpan(imageFrame, 3);
            this.add(edit, 0, 4);
            this.add(delete, 0, 5);

            for (int i = 0; i < 5; i++) {
                this.add(infos[i], 1, i + 1);
            }
            this.getStyleClass().addAll("item", "elevated", "rounded");
            delete.setOnAction(ev -> {
                try {
                    provider.delete(e);
                    refreshEtudiants();
                    toDisplay.remove(e);
                    displayEtudiants();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            });
            edit.setOnAction(ev -> {
                updateEtudiant(e);
            });
            imageFrame.setGraphic( new ImageView(new Image(getClass().getResourceAsStream((imageUrl)))));
            imageFrame.setOnMouseClicked( ev ->{
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Toutes les Images", "*.*"),
                        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                        new FileChooser.ExtensionFilter("PNG", "*.png")
                );
                fileChooser.setTitle("Choose image !");
                try {
                    System.out.println("yes");
                    imageFrame.setGraphic(new ImageView(new Image(fileChooser.showOpenDialog(primaryStage).toURI().toURL().toString())));
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            });
        }

    }

    public void nouvelEtudiant() {
        EditStage stage = new EditStage("Ajout Etudiant");
        Etudiant newE = new Etudiant();
        //--------------------------
        stage.getOkButton().setOnAction(event -> {
            newE.setNom(stage.getNom().getText());
            newE.setPrenom(stage.getPrenom().getText());
            newE.setAge(Integer.parseInt(stage.getAge().getText()));
            newE.setSex(stage.getSex());
            newE.setNiveau(Integer.parseInt(stage.getNiveau().getText()));
            newE.setDepartement(stage.getDepartement().getText());
            newE.setAdresse(stage.getAdresse().getText());
            if(!stage.getMontant().getText().equals(""))
                 newE.setMontant(Integer.parseInt(stage.getMontant().getText()));
            try {
                provider.insert(newE);
                refreshEtudiants();
                toDisplay = etudiants;
                displayEtudiants();
                if(sortMode.getSelectedToggle()!=null)
                    sortMode.getSelectedToggle().setSelected(false);
            } catch (ClassNotFoundException | SQLException e1) {
                e1.printStackTrace();
            }
            stage.close();
        });
        //----------------------
        stage.show();
    }

    public void updateEtudiant(Etudiant e) {
        EditStage stage = new EditStage("Edit Etudiant");
        stage.getNom().setText(e.getNom());
        stage.getPrenom().setText(e.getPrenom());
        if(e.getSex().equals("M"))
            stage.getMale().setSelected(true);
        else{
            stage.getMale().setSelected(false);
            stage.getFemale().setSelected(true);
        }

        stage.getNiveau().setText(String.valueOf(e.getNiveau()));
        stage.getAge().setText(String.valueOf(e.getAge()));
        stage.getDepartement().setText(e.getDepartement());
        stage.getAdresse().setText(e.getAdresse());
        stage.getMontant().setText(String.valueOf(e.getMontant()));
        Etudiant newE = new Etudiant();
        //--------------------------
        stage.getOkButton().setOnAction(event -> {
            newE.setNom(stage.getNom().getText());
            newE.setPrenom(stage.getPrenom().getText());
            newE.setAge(Integer.parseInt(stage.getAge().getText()));
            newE.setSex(stage.getSex());
            newE.setNiveau(Integer.parseInt(stage.getNiveau().getText()));
            newE.setDepartement(stage.getDepartement().getText());
            newE.setAdresse(stage.getAdresse().getText());
            newE.setMontant(Integer.parseInt(stage.getMontant().getText()));
            try {
                provider.update(e, newE);
                refreshEtudiants();
                toDisplay.add(toDisplay.indexOf(e), newE);
                toDisplay.remove(toDisplay.indexOf(newE) + 1);
                displayEtudiants();
            } catch (SQLException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            stage.close();
        });
        stage.show();
    }
}
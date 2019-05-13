
package sample.models;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditStage extends Stage {
    GridPane root = new GridPane();
    Scene scene = new Scene(root, 350, 400);
    private Button okButton = new Button("Valider");
    private TextField nom = new TextField();
    private TextField prenom = new TextField();
    private TextField adresse = new TextField();
    private TextField departement = new TextField();
    public TextField getNiveau() {
        return niveau;
    }
    public TextField montant = new TextField();

    private TextField niveau = new TextField();

    public TextField getNom() {
        return nom;
    }

    public TextField getPrenom() {
        return prenom;
    }

    public TextField getAdresse() {
        return adresse;
    }

    public TextField getDepartement() {
        return departement;
    }

    public TextField getAge() {
        return age;
    }
    public TextField getMontant(){return montant;}

    public RadioButton getMale() {
        return male;
    }
    public String getSex(){
        if(male.isSelected())
            return "M";
        return "F";
    }

    public RadioButton getFemale() {
        return female;
    }

    private TextField age = new TextField();
    private RadioButton  male = new RadioButton();
    private RadioButton female = new RadioButton();
    private ToggleGroup sexGroup = new ToggleGroup();
    public EditStage(String title){
        this.setScene(scene);
        this.setTitle(title);
        male.setToggleGroup(sexGroup);
        female.setToggleGroup(sexGroup);
        okButton.getStyleClass().addAll("btn","btn-primary");
        root.setPadding(new Insets(20));
        scene.getStylesheets().add(getClass().getResource("../deviantFX/css/deviantFX.min.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("../css/editStyle.css").toExternalForm());
        male.setSelected(true);
        male.setText("Male");
        female.setText("Female");
        root.setHgap(20);
        root.setVgap(10);
        root.add(new Label("Nom : "),0,1);
        root.add(nom, 1,1);
        root.add(new Label("Prenom : "), 0,2);
        root.add(prenom, 1,2);
        root.add(new Label("Age : "), 0,3);
        root.add(age, 1,3);
        root.add(new Label("Sexe : "), 0,4);
        root.add(male, 1,4);
        root.add(female, 1,4);
        GridPane.setHalignment(female, HPos.RIGHT);
        root.add(new Label("Niveau : "), 0,5);
        root.add(niveau, 1,5);
        root.add(new Label("Département : "), 0,6);
        root.add(departement, 1,6);
        root.add(new Label("Adresse : "), 0,7);
        root.add(adresse, 1,7);
        root.add(new Label("Montant : "),0,8 );
        root.add(montant,1,8);
        root.add(okButton, 1,10);
        GridPane.setHalignment(okButton, HPos.RIGHT);
    }
    public Button getOkButton() {
        return okButton;
    }
}

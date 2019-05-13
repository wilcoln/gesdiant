package sample.data;

import sample.models.Etudiant;

import java.sql.*;
import java.util.ArrayList;

public class Provider {
    private Connection db;
    private Statement query;
    private PreparedStatement insertionValues;
    private  PreparedStatement updateValues;
    private PreparedStatement deleteValues;
    public Provider() throws SQLException, ClassNotFoundException {
        db = (new DbHelper()).getDataBase();
        query = db.createStatement();
        insertionValues = db.prepareStatement("insert into etudiants (nom, prenom, age, sex, niveau, departement, adresse, montant)" +
        "values (?, ?, ?, ?, ?, ?, ?,?)");
        updateValues = db.prepareStatement("update etudiants" + " set nom = ?, prenom = ?, age = ?, sex = ?, niveau = ?, departement = ?, adresse = ?, montant = ? where id = ?");
        deleteValues = db.prepareStatement("delete from etudiants where id = ?");
    }
    public ArrayList<Etudiant> query() throws SQLException {
        ArrayList<Etudiant> etudiants = new ArrayList<>();
        ResultSet resultSet = query.executeQuery("select * from etudiants");
        while(resultSet.next()){
            etudiants.add((new Etudiant(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8), resultSet.getInt(9))).setId(resultSet.getInt(1)));
        }
        return etudiants;
    }
    public void insert(Etudiant newE) throws SQLException {
        insertionValues.setString(1,newE.getNom());
        insertionValues.setString(2,newE.getPrenom());
        insertionValues.setInt(3,newE.getAge());
        insertionValues.setString(4,newE.getSex());
        insertionValues.setInt(5,newE.getNiveau());
        insertionValues.setString(6,newE.getDepartement());
        insertionValues.setString(7,newE.getAdresse());
        insertionValues.setInt(8,newE.getMontant());
        insertionValues.executeUpdate();
    }
    public void update(Etudiant oldE, Etudiant newE) throws SQLException {
        updateValues.setString(1,newE.getNom());
        updateValues.setString(2,newE.getPrenom());
        updateValues.setInt(3,newE.getAge());
        updateValues.setString(4,newE.getSex());
        updateValues.setInt(5,newE.getNiveau());
        updateValues.setString(6,newE.getDepartement());
        updateValues.setString(7,newE.getAdresse());
        updateValues.setInt(8,newE.getMontant());
        updateValues.setInt(9,oldE.getId());
        updateValues.executeUpdate();

    }
    public void delete(Etudiant oldE) throws SQLException {
        deleteValues.setInt(1,oldE.getId());
        deleteValues.execute();
    }
}

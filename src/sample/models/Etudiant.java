package sample.models;

public class Etudiant {
    public void setNom(String mNom) {
        this.mNom = mNom;
    }

    public void setPrenom(String mPrenom) {
        this.mPrenom = mPrenom;
    }

    public void setAge(int mAge) {
        this.mAge = mAge;
    }

    public void setSex(String mSex) {
        this.mSex = mSex;
    }

    public void setAdresse(String mAdresse) {
        this.mAdresse = mAdresse;
    }

    public void setNiveau(int mNiveau) {
        this.mNiveau = mNiveau;
    }

    public void setDepartement(String mDepartement) {
        this.mDepartement = mDepartement;
    }

    public void setMontant(int mMontant){ this.mMontant = mMontant;}

    public void setNbc(double mNbc) {
        this.mNbc = mNbc;
    }

    private int mId;
    private String mNom = null;
    private String mPrenom;
    private int mAge;
    private String mSex ;
    private String mAdresse;
    private int mNiveau;
    private String mDepartement;
    private int mMontant;
    private double mNbc = Math.random();
    public Etudiant(String nom, String prenom, int age, String sex){
        mNom = nom;
        mPrenom = prenom;
        mAge = age;
        mSex = sex;
    }
    public Etudiant(){
    }
    public Etudiant setId(int id){
        mId = id;
        return  this;
    }
    public Etudiant(String nom, String prenom, int age, String sex, int niveau, String departement){
        this(nom,prenom,age,sex);
        mNiveau = niveau;
        mDepartement = departement;
    }
    public Etudiant(String nom, String prenom, int age, String sex, int niveau, String departement, String adresse){
        this(nom,prenom,age,sex,niveau,departement);
        mAdresse = adresse;
    }
    public Etudiant(String nom, String prenom, int age, String sex, int niveau, String departement, String adresse, int montant){
        this(nom,prenom,age,sex,niveau,departement,adresse);
        mMontant = montant;
    }

    public String getNom(){
        return mNom;
    }
    public String getPrenom(){
        return mPrenom;
    }
    public int getNiveau(){
        return mNiveau;
    }
    public String getAdresse(){
        return mAdresse;
    }
    public int getAge() {
        return mAge;
    }
    public String getDepartement(){
        return mDepartement;
    }
    public String getSex(){
        return mSex;
    }
    public int getId(){
        return mId;
    }
    public String toString(){
        return " Mat : " + mId + " Nom : " + mNom +" " +  mPrenom + " Sex : " + mSex + " Niveau : " + mNiveau + " Departement : " + mDepartement;
    }
    public int getMontant(){
        return mMontant;
    }

    public double getNbc() {
        return mNbc;
    }
}

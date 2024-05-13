package models.users;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



public class Reclamation {
    private int id_rec;
    private String TypeR;
    private String DescriptionR;
    private String Objet;
    private String DateR;
    private String etat;
    private int idc;

    public int getIdc() {
        return this.idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public String getEtat() {
        return this.etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Reclamation(int id, String TypeR, String DescriptionR, String Objet, String DateR) {
        this.id_rec = id;
        this.TypeR = TypeR;
        this.DescriptionR = DescriptionR;
        this.Objet = Objet;
        this.DateR = DateR;
    }

    public Reclamation(String TypeR, String DescriptionR, String Objet) {
        this.TypeR = TypeR;
        this.DescriptionR = DescriptionR;
        this.Objet = Objet;
    }

    public Reclamation(int idc) {
        this.idc = idc;
    }

    public Reclamation(String TypeR, String DescriptionR, String Objet, String DateR) {
        this.TypeR = TypeR;
        this.DescriptionR = DescriptionR;
        this.Objet = Objet;
        this.DateR = DateR;
    }

    public Reclamation() {
    }

    public int getId_rec() {
        return id_rec;
    }

    public void setId_rec(int id_rec) {
        this.id_rec = id_rec;
    }

    public String getObjet() {
        return this.Objet;
    }

    public void setObjet(String Objet) {
        this.Objet = Objet;
    }



    public String getTypeR() {
        return this.TypeR;
    }

    public void setTypeR(String TypeR) {
        this.TypeR = TypeR;
    }

    public String getDescriptionR() {
        return this.DescriptionR;
    }

    public void setDescriptionR(String DescriptionR) {
        this.DescriptionR = DescriptionR;
    }

    public String getDateR() {
        return this.DateR;
    }

    public void setDateR(String DateR) {
        this.DateR = DateR;
    }

    public String toString() {
        return "Reclamation{TypeR=" + this.TypeR + ", DescriptionR=" + this.DescriptionR + ", DateR=" + this.DateR + '}';
    }
}

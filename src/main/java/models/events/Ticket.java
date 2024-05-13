package models.events;

public class Ticket {
    int Idticket;

    public Ticket() {

    }


    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    int Idevent;
    int Iduser;
String qrcode;
    public int getIdticket() {
        return Idticket;
    }
    public Ticket(int idevent, int iduser, String qrcode) {
        Idevent = idevent;
        Iduser = iduser;
        this.qrcode = qrcode;
    }


    public void setIdticket(int idticket) {
        Idticket = idticket;
    }

    public int getIdevent() {
        return Idevent;
    }

    public void setIdevent(int idevent) {
        Idevent = idevent;
    }

    public int getIduser() {
        return Iduser;
    }

    public void setIduser(int iduser) {
        Iduser = iduser;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "Idticket=" + Idticket +
                ", Idevent=" + Idevent +
                ", Iduser=" + Iduser +
                '}';
    }
}

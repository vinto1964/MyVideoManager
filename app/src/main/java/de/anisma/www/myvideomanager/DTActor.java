package de.anisma.www.myvideomanager;

/**
 * Created by Alfa on 01.04.2015.
 */
public class DTActor {
    long lActor_ID = -1;
    String sActorFirstName;
    String sActorLastName;
    String sBirthday;
    String sVita;
    String sSex;
    String sImage;

    public DTActor(long lActor_ID, String sActorFirstName, String sActorLastName) {
        this.lActor_ID = lActor_ID;
        this.sActorFirstName = sActorFirstName;
        this.sActorLastName = sActorLastName;
        this.sBirthday = "";
        this.sVita = "";
        this.sSex = "";
        this.sImage = "";
    }

    public DTActor(long lActor_ID, String sActorFirstName, String sActorLastName, String sBirthday, String sSex, String sVita) {
        this.lActor_ID = lActor_ID;
        this.sActorFirstName = sActorFirstName;
        this.sActorLastName = sActorLastName;
        this.sBirthday = sBirthday;
        this.sVita = sVita;
        this.sSex = sSex;
        this.sImage = "";
    }

    public DTActor(long lActor_ID, String sActorFirstName, String sActorLastName, String sBirthday, String sSex, String sImage, String sVita) {
        this.lActor_ID = lActor_ID;
        this.sActorFirstName = sActorFirstName;
        this.sActorLastName = sActorLastName;
        this.sBirthday = sBirthday;
        this.sVita = sVita;
        this.sSex = sSex;
        this.sImage = sImage;
    }

    public long getlActor_ID() {
        return lActor_ID;
    }

    public void setlActor_ID(long lActor_ID) {
        this.lActor_ID = lActor_ID;
    }

    public String getsActorFirstName() {
        return sActorFirstName;
    }

    public void setsActorFirstName(String sActorFirstName) {
        this.sActorFirstName = sActorFirstName;
    }

    public String getsActorLastName() {
        return sActorLastName;
    }

    public void setsActorLastName(String sActorLastName) {
        this.sActorLastName = sActorLastName;
    }

    public String getsSex() { return sSex; }

    public void setsSex(String sSex) { this.sSex = sSex; }

    public String getsBirthday() {
        return sBirthday;
    }

    public void setsBirthday(String sBirthday) {
        this.sBirthday = sBirthday;
    }

    public String getsVita() {
        return sVita;
    }

    public void setsVita(String sVita) {
        this.sVita = sVita;
    }

    public String getsImage() { return sImage; }

    public void setsImage(String sImage) { this.sImage = sImage; }
}

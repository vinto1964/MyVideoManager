package de.anisma.www.myvideomanager;

/**
 * Created by Marc on 12.04.2015.
 */
public class DTActorRole {
    long lAR_ID = -1;
    int iAROrder;
    String sARRoleInFilm;
    String sARFirstName;
    String sARLastName;
    String sImagePath;

    public DTActorRole(long lAR_ID, int iAROrder, String sARRoleInFilm, String sARFirstName, String sARLastName, String sImagePath) {
        this.lAR_ID = lAR_ID;
        this.iAROrder = iAROrder;
        this.sARRoleInFilm = sARRoleInFilm;
        this.sARFirstName = sARFirstName;
        this.sARLastName = sARLastName;
        this.sImagePath = sImagePath;
    }

    public long getlAR_ID() {
        return lAR_ID;
    }

    public void setlAR_ID(long lAR_ID) {
        this.lAR_ID = lAR_ID;
    }

    public int getiAROrder() {
        return iAROrder;
    }

    public void setiAROrder(int iAROrder) {
        this.iAROrder = iAROrder;
    }

    public String getsARRoleInFilm() {
        return sARRoleInFilm;
    }

    public void setsARRoleInFilm(String sARRoleInFilm) {
        this.sARRoleInFilm = sARRoleInFilm;
    }

    public String getsARFirstName() {
        return sARFirstName;
    }

    public void setsARFirstName(String sARFirstName) {
        this.sARFirstName = sARFirstName;
    }

    public String getsARLastName() {
        return sARLastName;
    }

    public void setsARLastName(String sARLastName) {
        this.sARLastName = sARLastName;
    }

    public String getsImagePath() {
        return sImagePath;
    }

    public void setsImagePath(String sImagePath) {
        this.sImagePath = sImagePath;
    }
}

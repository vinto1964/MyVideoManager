package de.anisma.www.myvideomanager;

/**
 * Created by Marc on 14.03.2015.
 */
public class DTFilmItem {
    String sFilmTitle;
    String sFilmOTitle;
    int intFilmYear;
    int intFilmRanking;
    String sFilmImagePath;

    public DTFilmItem(String sFilmTitle, String sFilmOTitle, int intFilmYear, int intFilmRanking, String sFilmImagePath) {
        this.sFilmTitle = sFilmTitle;
        this.sFilmOTitle = sFilmOTitle;
        this.intFilmYear = intFilmYear;
        this.intFilmRanking = intFilmRanking;
        this.sFilmImagePath = sFilmImagePath;
    }

    public String getsFilmTitle() {
        return sFilmTitle;
    }

    public void setsFilmTitle(String sFilmTitle) {
        this.sFilmTitle = sFilmTitle;
    }

    public String getsFilmOTitle() {
        return sFilmOTitle;
    }

    public void setsFilmOTitle(String sFilmOTitle) {
        this.sFilmOTitle = sFilmOTitle;
    }

    public int getIntFilmYear() {
        return intFilmYear;
    }

    public void setIntFilmYear(int intFilmYear) {
        this.intFilmYear = intFilmYear;
    }

    public int getIntFilmRanking() {
        return intFilmRanking;
    }

    public void setIntFilmRanking(int intFilmRanking) {
        this.intFilmRanking = intFilmRanking;
    }

    public String getsFilmImagePath() {
        return sFilmImagePath;
    }

    public void setsFilmImagePath(String sFilmImagePath) {
        this.sFilmImagePath = sFilmImagePath;
    }
}

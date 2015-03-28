package de.anisma.www.myvideomanager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Marc on 14.03.2015.
 */
public class DTFilmItem {

    long lFilm_ID = -1;
    String sFilmTitle;
    String sFilmSubtitle;
    String sFilmOTitle;
    int intFilmPubYear;
    String sFilmCountry;
    String sFilmImage;
    String sFilmPlot;
    String sFilmComment;
    int iFilmRanking;
    int iFilmFSK;
    int iFilmEAN;
    int iFilmDuration;

    public DTFilmItem(long lFilm_ID, String sFilmTitle, String sFilmSubtitle,
                      String sFilmOTitle, int intFilmPubYear, String sFilmCountry,
                      String sFilmImage, String sFilmPlot, String sFilmComment,
                      int iFilmRanking, int iFilmFSK, int iFilmEAN, int iFilmDuration) {
        this.lFilm_ID = lFilm_ID;
        this.sFilmTitle = sFilmTitle;
        this.sFilmSubtitle = sFilmSubtitle;
        this.sFilmOTitle = sFilmOTitle;
        this.intFilmPubYear = intFilmPubYear;
        this.sFilmCountry = sFilmCountry;
        this.sFilmImage = sFilmImage;
        this.sFilmPlot = sFilmPlot;
        this.sFilmComment = sFilmComment;
        this.iFilmRanking = iFilmRanking;
        this.iFilmFSK = iFilmFSK;
        this.iFilmEAN = iFilmEAN;
        this.iFilmDuration = iFilmDuration;
    }

    public long getlFilm_ID() { return lFilm_ID; }

    public void setlFilm_ID(long lFilm_ID) {
        this.lFilm_ID = lFilm_ID;
    }

    public String getsFilmTitle() {
        return sFilmTitle;
    }

    public void setsFilmTitle(String sFilmTitle) {
        this.sFilmTitle = sFilmTitle;
    }

    public String getsFilmSubtitle() {
        return sFilmSubtitle;
    }

    public void setsFilmSubtitle(String sFilmSubtitle) {
        this.sFilmSubtitle = sFilmSubtitle;
    }

    public String getsFilmOTitle() {
        return sFilmOTitle;
    }

    public void setsFilmOTitle(String sFilmOTitle) {
        this.sFilmOTitle = sFilmOTitle;
    }

    public int getIntFilmPubYear() {
        return intFilmPubYear;
    }

    public void setIntFilmPubYear(int intFilmPubYear) {
        this.intFilmPubYear = intFilmPubYear;
    }

    public String getsFilmCountry() {
        return sFilmCountry;
    }

    public void setsFilmCountry(String sFilmCountry) {
        this.sFilmCountry = sFilmCountry;
    }

    public String getsFilmImage() {
        return sFilmImage;
    }

    public void setsFilmImage(String sFilmImage) {
        this.sFilmImage = sFilmImage;
    }

    public String getsFilmPlot() {
        return sFilmPlot;
    }

    public void setsFilmPlot(String sFilmPlot) {
        this.sFilmPlot = sFilmPlot;
    }

    public String getsFilmComment() {
        return sFilmComment;
    }

    public void setsFilmComment(String sFilmComment) {
        this.sFilmComment = sFilmComment;
    }

    public int getiFilmRanking() {
        return iFilmRanking;
    }

    public void setiFilmRanking(int iFilmRanking) {
        this.iFilmRanking = iFilmRanking;
    }

    public int getiFilmFSK() {
        return iFilmFSK;
    }

    public void setiFilmFSK(int iFilmFSK) {
        this.iFilmFSK = iFilmFSK;
    }

    public int getiFilmEAN() {
        return iFilmEAN;
    }

    public void setiFilmEAN(int iFilmEAN) {
        this.iFilmEAN = iFilmEAN;
    }

    public int getiFilmDuration() {
        return iFilmDuration;
    }

    public void setiFilmDuration(int iFilmDuration) {
        this.iFilmDuration = iFilmDuration;
    }


}

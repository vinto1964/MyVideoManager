package de.anisma.www.myvideomanager;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 14.03.2015.
 */
public class AppGlobal extends Application {

    CHDatabase dbVideo;

    List<DTFilmItem> ldFilmItems = new ArrayList<DTFilmItem>();
    List<String> listGenre = new ArrayList<String>();

    int iPosSelect = -1;

    FilmListAdapter lsAdapt;


}

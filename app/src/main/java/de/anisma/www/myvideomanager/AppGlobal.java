package de.anisma.www.myvideomanager;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 14.03.2015.
 */
public class AppGlobal extends Application {

    List<DTFilmItem> ldFilmItems = new ArrayList<DTFilmItem>();
    int iPosSelect = -1;

    FilmListAdapter lsAdapt;


}

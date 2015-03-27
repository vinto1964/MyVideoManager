package de.anisma.www.myvideomanager;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alfa on 27.03.2015.
 */
public class CHDatabase extends SQLiteOpenHelper {

    private static final int DATABEASE_VERSION = 1;
    private static final String DATABASENAME = "myVideoDB";
    private static final String TBLVIDEOMANAGER = "videomanager";
    private static final String ID_FILM = "id_film";
    private static final String TITEL = "title";
    private static final String SUBTITLE = "subtitle";
    private static final String ORI_TITEL = "original_title";
    private static final String PUBYEAR = "publishing_year";
    private static final String COUNTRY = "country";
    private static final String IMAGE = "image";
    private static final String PLOT = "plot";
    private static final String COMMENT = "comment";
    private static final String RANKING = "ranking";
    private static final String DURATION = "duration";
    private static final String FSK = "fsk";
    private static final String EAN = "ean";

    private static final String TBLGENREIS = "genre_is";
    private static final String ID_GENRE = "id_genre";

    private static final String TBLGENRE = "genre";
    private static final String GENRE = "genre";

    private static final String TBLPERSONSIS = "persons";
    private static final String ID_PERSON = "id_person";
    private static final String ID_FUNCTION = "id_function";
    private static final String ID_ROLE = "id_role";

    private static final String TBLPEOPLE = "people";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String BIRTHDAY = "birthday";
    private static final String SEX = "sex";
    private static final String VITA = "vita";

    private static final String TBLFUNCTIONS = "functions";
    private static final String FUNCTION = "function";

    private static final String TBLROLES = "roles";
    private static final String ROLE = "role";

    public CHDatabase(Context context){
        super(context, DATABASENAME, null, DATABEASE_VERSION);
    }

    public CHDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sCreate = "CREATE TABLE " + TBLVIDEOMANAGER + " ( " +
                ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITEL + " TEXT, " +
                SUBTITLE + " TEXT, " +
                ORI_TITEL + " TEXT, " +
                PUBYEAR + " INTEGER, " +
                COUNTRY + " TEXT, " +
                IMAGE + " TEXT, " +
                PLOT + " TEXT, " +
                COMMENT + " TEXT, " +
                RANKING + " INTEGER, " +
                DURATION + " INTEGER, " +
                FSK + " INTEGER, " +
                EAN + " INTEGER);";
        db.execSQL(sCreate);

        sCreate = "CREATE TABLE " + TBLGENREIS + " ( " +
                ID_GENRE + " INTEGER, " +
                ID_FILM + " INTEGER, " +
                " PRIMARY KEY (" + ID_GENRE + ", " + ID_FILM + " ));";
        db.execSQL(sCreate);

        sCreate = "CREATE TABLE " + TBLGENRE + " ( " +
                ID_GENRE + " INTEGER PRIMARY KEY, " +
                GENRE + " TEXT);";
        db.execSQL(sCreate);

        sCreate = "CREATE TABLE " + TBLPERSONSIS + " ( " +
                ID_FILM + " INTEGER, " +
                ID_PERSON + " INTEGER, " +
                ID_FUNCTION + " INTEGER, " +
                ID_ROLE + " INTEGER, " +
                " PRIMARY KEY ( " + ID_FILM + ", " + ID_PERSON + ", " + ID_FUNCTION + ") );";
        db.execSQL(sCreate);

        sCreate = "CREATE TABLE " + TBLPEOPLE + " ( " +
                ID_PERSON + " INTEGER PRIMARY KEY, " +
                FIRSTNAME + " TEXT, " +
                LASTNAME + " TEXT, " +
                BIRTHDAY + " TEXT, " +
                SEX + " TEXT, " +
                IMAGE + " TEXT, " +
                VITA + " TEXT); ";
        db.execSQL(sCreate);

        sCreate = "CREATE TABLE " + TBLFUNCTIONS + " ( " +
                ID_FUNCTION + " INTEGER PRIMARY KEY, " +
                FUNCTION + " TEXT);";
        db.execSQL(sCreate);

        sCreate = "CREATE TABLE " + TBLROLES + " ( " +
                ID_ROLE + " INTEGER PRIMARY KEY, " +
                ROLE + " TEXT);";
        db.execSQL(sCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }






}

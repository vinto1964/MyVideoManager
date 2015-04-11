package de.anisma.www.myvideomanager;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alfa on 27.03.2015.
 */
public class CHDatabase extends SQLiteOpenHelper {

    private Context ctx;
    private String sChoice;

    private static final int DATABASE_VERSION = 1;
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

    private static final String TBLPERSONSIS = "person_is";
    private static final String ID_PERSON = "id_person";
    private static final String ID_FUNCTION = "id_function";
    private static final String ID_ROLE = "id_role";
    private static final String ROLEORDER = "role_order";

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


    public CHDatabase(Context context) {
        super(context, DATABASENAME, null, DATABASE_VERSION);
    }

    public CHDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        this.ctx = context;
        this.sChoice = ctx.getString(R.string.sChoice);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB", "onCreate");

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
                RANKING + " REAL, " +
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
                ID_GENRE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GENRE + " TEXT);";
        db.execSQL(sCreate);

        sCreate = "CREATE TABLE " + TBLPERSONSIS + " ( " +
                ID_FILM + " INTEGER, " +
                ID_PERSON + " INTEGER, " +
                ID_FUNCTION + " INTEGER, " +
                ID_ROLE + " INTEGER, " +
                ROLEORDER + " INTEGER, " +
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
                ID_FUNCTION + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FUNCTION + " TEXT);";
        db.execSQL(sCreate);

        sCreate = "CREATE TABLE " + TBLROLES + " ( " +
                ID_ROLE + " INTEGER PRIMARY KEY, " +
                ROLE + " TEXT);";
        db.execSQL(sCreate);

        initGenre(db);
        initFunktion(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1){
            db.execSQL("DROP TABLE IF EXISTS " + TBLVIDEOMANAGER);
            db.execSQL("DROP TABLE IF EXISTS " + TBLFUNCTIONS);
            db.execSQL("DROP TABLE IF EXISTS " + TBLGENRE);
            db.execSQL("DROP TABLE IF EXISTS " + TBLGENREIS);
            db.execSQL("DROP TABLE IF EXISTS " + TBLPEOPLE);
            db.execSQL("DROP TABLE IF EXISTS " + TBLPERSONSIS);
            db.execSQL("DROP TABLE IF EXISTS " + TBLROLES);
            onCreate(db);
        }
    }

    public void loadAllFilms(List<DTFilmItem> filmlist, String whereClause){
        if(filmlist == null) {
            filmlist = new ArrayList<DTFilmItem>();
        }
        else {
            filmlist.clear();
        }

        SQLiteDatabase db = null;
        String sQuery = "SELECT * FROM " + TBLVIDEOMANAGER;
        if(!whereClause.isEmpty()) {
            sQuery += " WHERE " + TITEL + " LIKE '%" + whereClause + "%'";
        }

        try{
            db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(sQuery, null);
            if(cursor.moveToFirst()) {
                do {
                    long lFilmID = cursor.getLong(0);
                    String sFilmTitle = cursor.getString(1);
                    String sFilmSubtitle = cursor.getString(2);
                    String sFilmOTitle = cursor.getString(3);
                    int intFilmPubYear = cursor.getInt(4);
                    String sFilmCountry = cursor.getString(5);
                    String sFilmImage = cursor.getString(6);
                    String sFilmPlot = cursor.getString(7);
                    String sFilmComment = cursor.getString(8);
                    float iFilmRanking = cursor.getInt(9);
                    int iFilmDuration = cursor.getInt(10);
                    int iFilmFSK = cursor.getInt(11);
                    int iFilmEAN = cursor.getInt(12);

                    filmlist.add(new DTFilmItem(lFilmID,
                                                sFilmTitle,
                                                sFilmSubtitle,
                                                sFilmOTitle,
                                                intFilmPubYear,
                                                sFilmCountry,
                                                sFilmImage,
                                                sFilmPlot,
                                                sFilmComment,
                                                iFilmRanking,
                                                iFilmDuration,
                                                iFilmFSK,
                                                iFilmEAN));
                }
                while (cursor.moveToNext());
            }
        }
        catch (Exception ex) {
            ;
        }
        finally {
            if(db != null) {
                db.close();
            }
        }
    }

    public long insertFilm(DTFilmItem film){
        film.setlFilm_ID(insertFilm(film.getsFilmTitle(),
                                    film.getsFilmSubtitle(),
                                    film.getsFilmOTitle(),
                                    film.getIntFilmPubYear(),
                                    film.getsFilmCountry(),
                                    film.getsFilmImage(),
                                    film.getsFilmPlot(),
                                    film.getsFilmComment(),
                                    film.getfFilmRanking(),
                                    film.getiFilmDuration(),
                                    film.getiFilmFSK(),
                                    film.getiFilmEAN()));
        return film.getlFilm_ID();

    }

    public long insertFilmImage(DTFilmItem film){
        long lResult = -1;
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(IMAGE, film.getsFilmImage());
            lResult = db.insert(TBLVIDEOMANAGER, null, cv);
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
        return lResult;
    }

    public void updateFilmImage(DTFilmItem film) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(IMAGE, film.getsFilmImage());
            db.update(TBLVIDEOMANAGER, cv, ID_FILM + " = ?", new String[] {String.valueOf(film.getlFilm_ID())});
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }

    }

    public long insertFilm(String sFilmTitle, String sFilmSubtitle,
                           String sFilmOTitle, int intFilmPubYear, String sFilmCountry,
                           String sFilmImage, String sFilmPlot, String sFilmComment,
                           float iFilmRanking, int iFilmDuration, int iFilmFSK, int iFilmEAN) {

        long lResult = -1;
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(TITEL, sFilmTitle);
            cv.put(SUBTITLE, sFilmSubtitle);
            cv.put(ORI_TITEL, sFilmOTitle);
            cv.put(PUBYEAR, intFilmPubYear);
            cv.put(COUNTRY, sFilmCountry);
            cv.put(IMAGE, sFilmImage);
            cv.put(PLOT, sFilmPlot);
            cv.put(COMMENT, sFilmComment);
            cv.put(RANKING, iFilmRanking);
            cv.put(DURATION, iFilmDuration);
            cv.put(FSK, iFilmFSK);
            cv.put(EAN, iFilmEAN);

            lResult = db.insert(TBLVIDEOMANAGER, null, cv);

        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
        return lResult;
    }

    public void updateFilm(DTFilmItem film) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(TITEL, film.getsFilmTitle());
            cv.put(SUBTITLE, film.getsFilmSubtitle());
            cv.put(ORI_TITEL, film.getsFilmOTitle());
            cv.put(PUBYEAR, film.getIntFilmPubYear());
            cv.put(COUNTRY, film.getsFilmCountry());
            cv.put(IMAGE, film.getsFilmImage());
            cv.put(PLOT, film.getsFilmPlot());
            cv.put(COMMENT, film.getsFilmComment());
            cv.put(RANKING, film.getfFilmRanking());
            cv.put(DURATION, film.getiFilmDuration());
            cv.put(FSK, film.getiFilmFSK());
            cv.put(EAN, film.getiFilmEAN());

            db.update(TBLVIDEOMANAGER, cv, ID_FILM + " = ?", new String[] {String.valueOf(film.getlFilm_ID())});

        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
    }

    public long checkFilm(String titel) {
        long lResult = -1;
        SQLiteDatabase db = null;

        String sQuery = "SELECT * FROM " + TBLVIDEOMANAGER + " WHERE " + TITEL + " LIKE '" + titel + "';";
        try {
            db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(sQuery, null);
            if(cursor.moveToFirst()) {
                lResult = cursor.getLong(0);
            }
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }

        return lResult;
    }


    public List loadAllActors(List<DTActor> actorsList, String whereClause, long filmID) {
        if(actorsList == null) {
            actorsList = new ArrayList<DTActor>();
        }
        else {
            actorsList.clear();
        }
        SQLiteDatabase db = null;

        String sQuery = "SELECT * FROM " + TBLPEOPLE ;
        if(!whereClause.isEmpty()) {
            sQuery += " WHERE " + LASTNAME + " LIKE '%" + whereClause + "%' OR " + FIRSTNAME + " LIKE '%" + whereClause + "%'";
        }
        if(filmID > -1) {
            sQuery += " WHERE " + ID_FILM + " = " + filmID;
        }

        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sQuery, null);

            if(cursor.moveToFirst()) {
                do {

                    long lActorID = cursor.getLong(0);
                    String sFirstname   = cursor.getString(1);
                    String sLastname    = cursor.getString(2);
                    String sBirthday    = cursor.getString(3);
                    String sSex         = cursor.getString(4);
                    String sImage       = cursor.getString(5);
                    String sVita        = cursor.getString(6);

                    actorsList.add(new DTActor(lActorID, sFirstname, sLastname, sSex, sBirthday, sImage, sVita));

                } while(cursor.moveToNext());
            }
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
        return actorsList;
    }

    public DTActor loadActor(long actorID) {
        SQLiteDatabase db = null;
        String sQuery = "SELECT * FROM " + TBLPEOPLE + " WHERE " + ID_PERSON + " = ?";
        List<DTActor> actorList = new ArrayList<DTActor>();

        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sQuery,  new String[] {String.valueOf(actorID)});
            if(cursor.moveToFirst()) {
                do {

                    long lActorID = cursor.getLong(0);
                    String sFirstname = cursor.getString(1);
                    String sLastname = cursor.getString(2);
                    String sBirthday = cursor.getString(3);
                    String sSex = cursor.getString(4);
                    String sImage = cursor.getString(5);
                    String sVita = cursor.getString(6);

                    actorList.add(new DTActor(lActorID, sFirstname, sLastname, sBirthday, sSex, sImage, sVita));
                } while (cursor.moveToNext());
            }
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
        return actorList.get(0);
    }

    public DTActor loadActorForDialog(long filmID) {
        SQLiteDatabase db = null;

        String sQuery = "SELECT pi." + ROLEORDER + ", r." + ROLE + ", p." + FIRSTNAME + ", p." + LASTNAME + " FROM " + TBLPERSONSIS +
                        " pi JOIN " + TBLROLES + " r ON (pi." + ID_ROLE + " = r." + ID_ROLE + ") JOIN " + TBLPEOPLE + " p ON (pi" +
                        ID_PERSON + " = p." + ID_PERSON + ") WHERE pi." + ID_FILM + " = ? ORDER BY pi." + ROLEORDER + " ASC";
        List<DTActor> actorList = new ArrayList<DTActor>();

        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sQuery,  new String[] {String.valueOf(filmID)});
            if(cursor.moveToFirst()) {
                do {

                    int iRoleOrder = cursor.getInt(0);
                    String sRoel = cursor.getString(1);
                    String sFirstname = cursor.getString(2);
                    String sLastname = cursor.getString(3);



                    //actorList.add(new DTActor(sFirstname, sLastname));
                } while (cursor.moveToNext());
            }
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
        return actorList.get(0);
    }


    public DTActor loadActor(String firstname, String lastname) {
        SQLiteDatabase db = null;
        String sQuery = "SELECT * FROM " + TBLPEOPLE + " WHERE " + FIRSTNAME + " like '" +  firstname + "'  AND " + LASTNAME + " like '" + lastname + "';";
        List<DTActor> actorList = new ArrayList<DTActor>();

        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sQuery, null);
            if(cursor.moveToFirst()) {
                do {

                    long lActorID = cursor.getLong(0);
                    String sFirstname = cursor.getString(1);
                    String sLastname = cursor.getString(2);
                    String sBirthday = cursor.getString(3);
                    String sSex = cursor.getString(4);
                    String sImage = cursor.getString(5);
                    String sVita = cursor.getString(6);

                    actorList.add(new DTActor(lActorID, sFirstname, sLastname, sBirthday, sSex, sImage, sVita));
                } while (cursor.moveToNext());
            }
        }
        catch (Exception ex) { }
        finally { if(db != null){db.close();} }
        return actorList.get(0);
    }

    public long checkActor(String firstname, String lastname) {
        long lResult = -1;
        SQLiteDatabase db = null;
        String sQuery = "SELECT " + ID_PERSON + " FROM " + TBLPEOPLE + " WHERE " + FIRSTNAME + " like '" +  firstname + "'  AND " + LASTNAME + " like '" + lastname + "';";
        List<DTActor> actorList = new ArrayList<DTActor>();

        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sQuery, null);
            if(cursor.moveToFirst()) {
                lResult = cursor.getLong(0);
            }
        }
        catch (Exception ex) { }
        finally { if(db != null){db.close();} }
        return lResult;
    }

    public long insertActor(String sActorFirstName, String sActorLastName, String sSex, String sBirthday, String sImage, String sVita) {
        long lResult = -1;
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(FIRSTNAME, sActorFirstName);
            cv.put(LASTNAME, sActorLastName);
            cv.put(SEX, sSex);
            cv.put(BIRTHDAY, sBirthday);
            cv.put(IMAGE, sImage);
            cv.put(VITA, sVita);

            lResult = db.insert(TBLPEOPLE, null, cv);
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }

        return lResult;
    }

    public long insertActor(DTActor actor) {
        actor.setlActor_ID(insertActor(actor.getsActorFirstName(),
                                        actor.getsActorLastName(),
                                        actor.getsSex(),
                                        actor.getsBirthday(),
                                        actor.getsImage(),
                                        actor.getsVita()));
        return actor.getlActor_ID();
    }

    public void updatePerson(DTActor person) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(FIRSTNAME, person.getsActorFirstName());
            cv.put(LASTNAME, person.getsActorLastName());
            cv.put(BIRTHDAY, person.getsBirthday());
            cv.put(SEX, person.getsSex());
            cv.put(IMAGE, person.getsImage());
            cv.put(VITA, person.getsVita());

            db.update(TBLPEOPLE, cv, ID_PERSON + " = ?", new String[] {String.valueOf(person.getlActor_ID())});
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
    }


    public void deleteActor(long id_person){
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
            db.delete(TBLPERSONSIS, ID_PERSON + " = ? ", new String[] {String.valueOf(id_person)});
            db.delete(TBLPEOPLE, ID_PERSON + " = ? ", new String[] {String.valueOf(id_person)});
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
    }

    public void deleteActorFromFilm(long id_person, long id_film){
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
            db.delete(TBLPERSONSIS, ID_FILM + " = ? AND " + ID_PERSON + " = ?", new String[] {String.valueOf(id_film), String.valueOf(id_person)});
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
    }

    public void updatePlot(DTFilmItem film) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(PLOT, film.getsFilmPlot());
            db.update(TBLVIDEOMANAGER, cv, ID_FILM + " = ?", new String[] {String.valueOf(film.getlFilm_ID())});
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
    }

    public long insertActorImage(DTActor actor){
        long lResult = -1;
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(IMAGE, actor.getsImage());
            lResult = db.insert(TBLPEOPLE, null, cv);
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
        return lResult;
    }

    public void updateActorImage(DTActor actor) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(IMAGE, actor.getsImage());
            db.update(TBLPEOPLE, cv, ID_PERSON + " = ?", new String[] {String.valueOf(actor.getlActor_ID())});
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }

    }


    public void updateCommentRating(DTFilmItem film) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COMMENT, film.getsFilmPlot());
            cv.put(RANKING, film.getfFilmRanking());
            db.update(TBLVIDEOMANAGER, cv, ID_FILM + " = ?", new String[] {String.valueOf(film.getlFilm_ID())});
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
    }

    public List loadAllGenre() {
        List<String> listGenre = new ArrayList<String>();
        listGenre.add("Bitte auswählen:");
        SQLiteDatabase db = null;
        String sQuery = "SELECT " + GENRE + " FROM " + TBLGENRE + " ORDER BY " + GENRE + " ASC";
        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery(sQuery, null);
            if(cursor.moveToFirst()) {
                do {
                    String genre = cursor.getString(0);
                    listGenre.add(genre);
                } while (cursor.moveToNext());
            }
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
        return listGenre;
    }


    public List loadAllFilmGenre(long filmID){
        List<String> listGenre = new ArrayList<String>();
        SQLiteDatabase db = null;
        String sQuery = "SELECT " + GENRE + " FROM " + TBLGENRE + " g JOIN " + TBLGENREIS + " gi ON (g." + ID_GENRE + " = gi." + ID_GENRE + ") WHERE gi." + ID_FILM + " = ? ";

        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sQuery, new String[]{String.valueOf(filmID)});
            if(cursor.moveToFirst()){
                do {
                    String genre = cursor.getString(0);
                    listGenre.add(genre);
                } while (cursor.moveToNext());
            }
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }

        return listGenre;
    }

    public long isFilmGenre(long filmID, String genre){
        long lResult = -1;
        SQLiteDatabase db = null;

        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT " + ID_GENRE + " FROM " + TBLGENREIS + " WHERE " + ID_FILM + " = ? AND " + GENRE + " = '" + genre + "'",
                    new String[] {String.valueOf(filmID)});

            if(cursor.moveToFirst()) {
                lResult = cursor.getLong(0);
            }
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }

        return lResult;
    }



    public void insertGenreIs(long filmID, long genreID){
        long lResult = -1;
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(ID_GENRE, genreID);
            cv.put(ID_FILM, filmID);
            lResult = db.insert(TBLGENREIS, null, cv);
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
    }

    public long getGenreID(String genre) {
        long lResult = -1;
        SQLiteDatabase db = null;
        String sQuery = "SELECT " + ID_GENRE + " FROM " + TBLGENRE + " WHERE " + GENRE + " = '" +  genre + "';";

        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sQuery, null);
            if(cursor.moveToFirst()) {
                lResult = cursor.getLong(0);
            }
        }
        catch (Exception ex) { }
        finally { if(db != null){db.close();} }
        return lResult;
    }


    public void deleteGenreFromFilm(long filmID, String genre) {
        SQLiteDatabase db = null;

        long genreID = getGenreID(genre);

        try {
            db = this.getWritableDatabase();
            db.delete(TBLGENREIS, ID_FILM + " = ? AND " + ID_GENRE + " = ?", new String[] {String.valueOf(filmID), String.valueOf(genreID)});
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
    }

    public void deleteFilm(long filmID){
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            db.delete(TBLVIDEOMANAGER, ID_FILM + " = ?", new String[]{String.valueOf(filmID)});
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
    }

    public long checkRole(String role) {
        long lResult = -1;
        SQLiteDatabase db = null;

        String sQuery = "SELECT * FROM " + TBLROLES + " WHERE " + ROLE + " LIKE '" + role + "';";
        try {
            db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(sQuery, null);
            if(cursor.moveToFirst()) {
                lResult = cursor.getLong(0);
            }
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }

        return lResult;
    }

    public long insertRole(String role) {
        long lResult = -1;
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(ROLE, role);
            lResult = db.insert(TBLROLES, null, cv);
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
        return lResult;
    }

    public long checkPersonIs(long id_film, long id_person, long id_function){
        long lResult = -1;
        SQLiteDatabase db = null;
        String sQuery = "SELECT * FROM " + TBLPERSONSIS + " WHERE " + ID_FILM + " = ? AND " + ID_PERSON + " = ? AND " + ID_ROLE + " = ? ";
        try {
            Cursor cursor = db.rawQuery(sQuery, new String[] {String.valueOf(id_film), String.valueOf(id_person), String.valueOf(id_function)});
            if(cursor.moveToFirst()) {
                lResult = cursor.getLong(0);
            }
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }

        return lResult;
    }

    public long insertPersonIs(long id_film, long id_person, long id_function, long id_role, int role_order ) {
        long lResult = -1;
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(ID_FILM, id_film);
            cv.put(ID_PERSON, id_person);
            cv.put(ID_FUNCTION, id_function);
            if(id_role > -1) {
                cv.put(ID_ROLE, id_role);
            }
            if(role_order > -1 ) {
                cv.put(ROLEORDER, role_order);
            }
            lResult = db.insert(TBLPERSONSIS, null, cv);
        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }
        return lResult;
    }

    public List loadFilmActorsList(long id_film) {
        List<String> actorsList = new ArrayList<String>();

        SQLiteDatabase db = null;
        //String sQuery = "SELECT " + GENRE + " FROM " + TBLGENRE + " g JOIN " + TBLGENREIS + " gi ON (g." + ID_GENRE + " = gi." + ID_GENRE + ") WHERE gi." + ID_FILM + " = ?";
        String sQuery = "SELECT p." + ID_PERSON + ", " + FIRSTNAME + ", " + LASTNAME + " FROM " + TBLPEOPLE + " p JOIN " + TBLPERSONSIS + " pi ON (p." + ID_PERSON + " = pi." + ID_PERSON + ") WHERE " + ID_FILM + " = ? " +
                        " ORDER BY " + ROLEORDER + " ASC, " + FIRSTNAME + " ASC ";


        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sQuery, new String[] {String.valueOf(id_film)});
            if(cursor.moveToFirst()) {
                do {
                    long id_Person = cursor.getLong(0);
                    String firstname = cursor.getString(1);
                    String lastname = cursor.getString(2);

                    actorsList.add(firstname + ", " + lastname + "<" + id_Person);
                } while (cursor.moveToNext());
            }

        }
        catch (Exception ex) {}
        finally { if(db != null){db.close();} }

        return actorsList;
    }


    public static int getDatabeaseVersion() {
        return DATABASE_VERSION;
    }

    // ********************************************************************************************************
    // Datenbank Initialisierung
    public void initGenre( SQLiteDatabase db ) {

        String [] genresD = {"Abenteuer", "Kinder", "Komödie", "Drama", "Science Fiction", "Thriller", "Western",
                "Mystery", "Horror", "EndOfWorld", "Action", "Romanze", "Fantasy","Krieg",
                "Dokumentation", "Monumental", "Animation", "Katastrophe", "Erotik", "Biografie",
                "Weltraum", "Liebe", "Geschichte", "Sport", "Familie", "Krimi"};
        
        
        for(int i = 0; i < genresD.length; i++) {

            long lResult = -1;
           // SQLiteDatabase db = null;

            try {
                //db = this.getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put(GENRE, genresD[i]);
                lResult = db.insert(TBLGENRE, null, cv);

            } catch (Exception ex) { }

        }
    }

    public  void initFunktion(SQLiteDatabase db) {
        String[] functionD	=	{"Regisseur", "Schauspieler"};
        long lResult = -1;
      //  SQLiteDatabase db = null;
        try{
         //   db = this.getWritableDatabase();

            for(int i = 0; i < functionD.length; i++) {
                ContentValues cv = new ContentValues();
                cv.put(FUNCTION, functionD[i]);
                lResult = db.insert(TBLFUNCTIONS, null, cv);
            }
        }
        catch (Exception ex) {}
      //  finally { if(db != null){ db.close();}}
    }

    // ********************************************************************************************************
}


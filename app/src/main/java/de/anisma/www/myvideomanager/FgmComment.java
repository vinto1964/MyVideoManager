package de.anisma.www.myvideomanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alfa on 30.03.2015.
 */
public class FgmComment extends Fragment {

    int val;
    int iPos = -1;
    int iDeleteGenre = -1;

    RatingBar rbRating;
    Spinner spGenre;
    EditText edComment;
    ListView lvGenres;
    ArrayAdapter lvAdapt;
    List<String> genreList = new ArrayList<>();

    String genre;

    public FgmComment() {
    }

    public static FgmComment newInstance(int pos, int iPosSelect) {
        FgmComment f = new FgmComment();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        args.putInt("selected", iPosSelect);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            val = getArguments().getInt("position");
            iPos = getArguments().getInt("selected");
        }
        else {
            val = 3;
            iPos = -1;
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        List<String> listGenre = new ArrayList<String>();
        listGenre = myApp.dbVideo.loadAllGenre();
        View view = inflater.inflate(R.layout.activity_mact_film_details_comment, container, false);

        rbRating    = (RatingBar) view.findViewById(R.id.rbRating);
        spGenre     = (Spinner) view.findViewById(R.id.spGenre);
        ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listGenre);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenre.setAdapter(spAdapter);

        spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //saveDatas();
                saveGenre();
                reloadGenre();
                spGenre.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        edComment   = (EditText) view.findViewById(R.id.edComment);
        lvGenres    = (ListView) view.findViewById(R.id.lvGenres);
        lvGenres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        lvGenres.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                iDeleteGenre = position;
                doShowAlertDialog();
                return false;
            }
        });

        if(iPos > -1) {
            loadInfos();
        }
        return view;
    }

    private void doShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Genre löschen");
        builder.setMessage("Möchten Sie wirklich löschen?");
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_important);

        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteGenreFromFilm();
            }
        });
        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteGenreFromFilm() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        if(iDeleteGenre > -1) {
            myApp.dbVideo.deleteGenreFromFilm(myApp.ldFilmItems.get(iPos).getlFilm_ID(), genreList.get(iDeleteGenre));
            genreList.remove(iDeleteGenre);
            reloadGenre();
        }
        iDeleteGenre = -1;
    }

    private void loadInfos() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        String s = "";
        DTFilmItem film = myApp.ldFilmItems.get(iPos);
        rbRating.setRating(film.getfFilmRanking());
        edComment.setText(film.getsFilmComment());

        reloadGenre();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(iPos > -1) {
            reloadGenre();
        }
    }

    private void reloadGenre() {
        if(iPos > -1) {
            AppGlobal myApp = (AppGlobal) getActivity().getApplication();
            DTFilmItem film = myApp.ldFilmItems.get(iPos);
            genreList = myApp.dbVideo.loadAllFilmGenre(film.getlFilm_ID());
            lvAdapt = new ArrayAdapter(this.getActivity().getBaseContext(),
                    android.R.layout.simple_list_item_1,
                    genreList);
            lvGenres.setAdapter(lvAdapt);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveDatas();
    }

    private void saveDatas() {
        if(iPos > -1) {
            AppGlobal myApp = (AppGlobal) getActivity().getApplication();
            DTFilmItem film = myApp.ldFilmItems.get(iPos);
            film.setsFilmComment(edComment.getText().toString());
            film.setfFilmRanking(rbRating.getRating());
            myApp.dbVideo.updateCommentRating(film);
        }
    }

    private void saveGenre() {
        if(iPos > -1) {
            AppGlobal myApp = (AppGlobal) getActivity().getApplication();
            DTFilmItem film = myApp.ldFilmItems.get(iPos);
            genre = String.valueOf(spGenre.getSelectedItem());

            if (!myApp.dbVideo.isFilmGenre(film.getlFilm_ID(), genre)) {
                //myApp.dbVideo.insertGenreIs(film.getlFilm_ID(), myApp.dbVideo.getGenreID(genre));
                myApp.dbVideo.insertGenreIs(film.getlFilm_ID(), myApp.dbVideo.getGFID("genre", genre));
            }
        }
    }


}

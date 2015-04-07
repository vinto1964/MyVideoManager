package de.anisma.www.myvideomanager;

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

    RatingBar rbRating;
    Spinner spGenre;
    EditText edComment;
    TextView tvGenre;
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
        View view = inflater.inflate(R.layout.activity_mact_film_details_comment, container, false);

        rbRating    = (RatingBar) view.findViewById(R.id.rbRating);
        spGenre     = (Spinner) view.findViewById(R.id.spGenre);
        spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                saveDatas();
                reloadGenre();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        edComment   = (EditText) view.findViewById(R.id.edComment);
        tvGenre     = (TextView) view.findViewById(R.id.tvGenre);
        lvGenres    = (ListView) view.findViewById(R.id.lvGenres);

        if(iPos > -1) {
            loadInfos();
        }
        return view;
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

    private void setGenreSelection(String s){
        CursorAdapter cursorAdapter = (CursorAdapter) spGenre.getAdapter();
        for(int i = 0; i < cursorAdapter.getCount(); i++) {
            if(String.valueOf(cursorAdapter.getItem(i)).compareTo(s) == 0) {
                spGenre.setSelection(i);
            }
        }
    }

    private void saveDatas() {
        if(iPos > -1) {
            AppGlobal myApp = (AppGlobal) getActivity().getApplication();
            DTFilmItem film = myApp.ldFilmItems.get(iPos);
            film.setsFilmComment(edComment.getText().toString());
            film.setfFilmRanking(rbRating.getRating());
            myApp.dbVideo.updateCommentRating(film);

            genre = String.valueOf(spGenre.getSelectedItem());
            if (myApp.dbVideo.isFilmGenre(film.getlFilm_ID(), spGenre.getSelectedItemPosition()) > 0 &&
                    spGenre.getSelectedItemPosition() != 0) {     // update
                myApp.dbVideo.updateGenreIs(film.getlFilm_ID(), spGenre.getSelectedItemPosition());
            } else {  // insert
                myApp.dbVideo.insertGenreIs(film.getlFilm_ID(), spGenre.getSelectedItemPosition());
            }
        }
    }
}

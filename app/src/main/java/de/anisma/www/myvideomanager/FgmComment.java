package de.anisma.www.myvideomanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

/**
 * Created by Alfa on 30.03.2015.
 */
public class FgmComment extends Fragment {

    int val;
    int iPos = -1;

    RatingBar rbRating;
    Spinner spGenre;
    EditText edComment;


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
        edComment   = (EditText) view.findViewById(R.id.edComment);

        if(iPos > -1) {
            loadInfos();
        }

        return view;
    }

    private void loadInfos() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        DTFilmItem film = myApp.ldFilmItems.get(iPos);
        rbRating.setRating(film.getfFilmRanking());
        edComment.setText(film.getsFilmComment());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        DTFilmItem film = myApp.ldFilmItems.get(myApp.iPosSelect);
        film.setsFilmComment(edComment.getText().toString());
        film.setfFilmRanking(rbRating.getRating());
        myApp.dbVideo.updateCommentRating(film);

    }
}

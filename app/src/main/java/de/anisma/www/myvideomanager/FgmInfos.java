package de.anisma.www.myvideomanager;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Alfa on 30.03.2015.
 */
public class FgmInfos extends Fragment implements View.OnClickListener {

    int val;

    Intent intent;
    Activity act;
    EditText edTitle, edSubtitel, edOTitel, edPubYear, edCountry, edFSK, edDuration, edEAN, edPlot;
    ImageView ivCover;
    ImageButton ibSave, ibDelete;

    int iPos = -1;

    public FgmInfos() {
        this.act = this.getActivity();
    }

    public static FgmInfos newInstance(int pos, int iPosSelect) {
        FgmInfos f = new FgmInfos();
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
            val = 0;
            iPos = -1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mact_film_details_infos, container, false);

        edTitle     = (EditText) view.findViewById(R.id.edTitel);
        edSubtitel  = (EditText) view.findViewById(R.id.edSubtitle);
        edOTitel    = (EditText) view.findViewById(R.id.edOTitle);
        edPubYear   = (EditText) view.findViewById(R.id.edPubYear);
        edCountry   = (EditText) view.findViewById(R.id.edCountry);
        edFSK       = (EditText) view.findViewById(R.id.edFSK);
        edDuration  = (EditText) view.findViewById(R.id.edDuration);
        edEAN       = (EditText) view.findViewById(R.id.edEAN);

        ivCover = (ImageView) view.findViewById(R.id.ivCover);
        ivCover.setOnClickListener(this);

        ibSave = (ImageButton) view.findViewById(R.id.ibSave);
        ibSave.setOnClickListener(this);

        ibDelete = (ImageButton) view.findViewById(R.id.ibDelete);
        if(iPos > -1) {
            ibDelete.setClickable(true);
            loadFilm();
        }
        ibDelete.setOnClickListener(this);

        return view;
    }

    private void loadFilm() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        DTFilmItem film = myApp.ldFilmItems.get(iPos);
        edTitle.setText(film.getsFilmTitle());
        edSubtitel.setText(film.getsFilmSubtitle());
        edOTitel.setText(film.getsFilmOTitle());
        edPubYear.setText(film.getIntFilmPubYear() < 0 ? "" : "" + film.getIntFilmPubYear());
        edCountry.setText(film.getsFilmCountry());
        edFSK.setText(film.getiFilmFSK() < 0 ? "" : "" + film.getiFilmFSK());
        edDuration.setText(film.getiFilmDuration() < 0 ? "" : "" + film.getiFilmDuration());
        edEAN.setText(film.getiFilmEAN() < 0 ? "" : "" + film.getiFilmEAN());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCover:

                break;

            case R.id.ibSave:
                saveEntry();
                break;

            case R.id.ibDelete:
                deleteEntry();
                break;
        }
    }

    private void deleteEntry() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        myApp.dbVideo.deleteFilm(myApp.ldFilmItems.get(iPos).getlFilm_ID());
        myApp.ldFilmItems.remove(iPos);
        iPos = -1;

        edTitle.setText("");
        edSubtitel.setText("");
        edOTitel.setText("");
        edPubYear.setText("");
        edCountry.setText("");
        edFSK.setText("");
        edDuration.setText("");
        edEAN.setText("");
//        myApp.iPosSelect = -1;
    }

    private void saveEntry() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        if(iPos < 0) {  // Noch nicht in der Datenbank
            if(!(edTitle.getText().toString().isEmpty())) {

                if(myApp.dbVideo.checkFilm(edTitle.getText().toString()) < 0) {
                    myApp.ldFilmItems.add(new DTFilmItem(
                            -1,
                            edTitle.getText().toString(),
                            edSubtitel.getText().toString(),
                            edOTitel.getText().toString(),
                            edPubYear.getText().toString().isEmpty() ? -1 : Integer.parseInt(edPubYear.getText().toString()),
                            edCountry.getText().toString(),
                            "", // Bildpfad zuerst leer
                            edDuration.getText().toString().isEmpty() ? -1 : Integer.parseInt(edDuration.getText().toString()),
                            edFSK.getText().toString().isEmpty() ? -1 : Integer.parseInt(edFSK.getText().toString()),
                            edEAN.getText().toString().isEmpty() ? -1 : Integer.parseInt(edEAN.getText().toString())
                    ));
                    long lRes = myApp.dbVideo.insertFilm(myApp.ldFilmItems.get(myApp.ldFilmItems.size() - 1));
                }
            }
            //myApp.iPosSelect = myApp.ldFilmItems.size() - 1;

        }
        else { // Ein Eintrag wurde bearbeitet
            DTFilmItem filmUpdate = myApp.ldFilmItems.get(iPos);
            filmUpdate.setsFilmTitle(edTitle.getText().toString());
            filmUpdate.setsFilmSubtitle(edSubtitel.getText().toString());
            filmUpdate.setsFilmOTitle(edOTitel.getText().toString());
            filmUpdate.setIntFilmPubYear(edPubYear.getText().toString().isEmpty() ? -1 : Integer.parseInt(edPubYear.getText().toString()));
            filmUpdate.setsFilmCountry(edCountry.getText().toString());
            filmUpdate.setsFilmImage("");
            filmUpdate.setiFilmDuration(edDuration.getText().toString().isEmpty() ? -1 : Integer.parseInt(edDuration.getText().toString()));
            filmUpdate.setiFilmFSK(edFSK.getText().toString().isEmpty() ? -1 : Integer.parseInt(edFSK.getText().toString()));
            filmUpdate.setiFilmEAN(edEAN.getText().toString().isEmpty() ? -1 : Integer.parseInt(edEAN.getText().toString()));
            myApp.dbVideo.updateFilm(filmUpdate);
 //           myApp.iPosSelect = iPos;

        }

    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link android.app.Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
        saveEntry();
    }




}
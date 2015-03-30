package de.anisma.www.myvideomanager;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Alfa on 30.03.2015.
 */
public class FgmInfos extends Fragment implements View.OnClickListener {

    int val;
    Activity act;
    EditText edTitle, edSubtitel, edOTitel, edPubYear, edCountry, edFSK, edDuration, edEAN, edPlot;
    ImageView ivCover;
    ImageButton ibSave;

    int iPos = -1;
    long lFilmID = -1;
    List<DTFilmItem> ldFilmItems;

    public FgmInfos() {
        this.act = this.getActivity();
    }

    public static FgmInfos newInstance(int pos) {
        FgmInfos f = new FgmInfos();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        val = getArguments() != null ? getArguments().getInt("position") : 0;
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

        ibSave = (ImageButton) view.findViewById(R.id.ibSave1);
        ibSave.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCover:

                break;

            case R.id.ibSave1:
                saveEntry();

                break;
        }
    }

    private void saveEntry() {

        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        if(iPos < 0) {  // Noch nicht in der Datenbank
            if(!(edTitle.getText().toString().isEmpty())) {

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
        else { // Ein Eintrag wurde bearbeitet

        }

    }


}
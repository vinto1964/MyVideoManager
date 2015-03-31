package de.anisma.www.myvideomanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Alfa on 30.03.2015.
 */
public class FgmPlot extends Fragment {

    int val;
    int iPos = -1;
    TextView tvFilmTitel;
    EditText edPlot;

    public FgmPlot() {
    }

    public static FgmPlot newInstance(int pos, int iPosSelect) {
        FgmPlot f = new FgmPlot();
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
            val = 2;
            iPos = -1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mact_film_details_plot, container, false);
        tvFilmTitel = (TextView) view.findViewById(R.id.tvFilmTitel);
        edPlot = (EditText) view.findViewById(R.id.edPlot);

        if(iPos > -1) {
            loadPlot();
        }


        return view;
    }

    private void loadPlot() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        DTFilmItem film = myApp.ldFilmItems.get(iPos);
        tvFilmTitel.setText(film.getsFilmTitle());
        edPlot.setText(film.getsFilmPlot());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        myApp.ldFilmItems.get(iPos).setsFilmPlot(edPlot.getText().toString());
        DTFilmItem film = myApp.ldFilmItems.get(iPos);
        myApp.dbVideo.updatePlot(film);

    }
}

package de.anisma.www.myvideomanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alfa on 30.03.2015.
 */
public class FgmPlot extends Fragment {

    int val;

    public FgmPlot() {
    }

    public static FgmPlot newInstance(int pos) {
        FgmPlot f = new FgmPlot();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        val = getArguments() != null ? getArguments().getInt("position") : 2;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mact_film_details_plot, container, false);

        return view;
    }

}

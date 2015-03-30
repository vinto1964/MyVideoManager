package de.anisma.www.myvideomanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alfa on 30.03.2015.
 */
public class FgmActors extends Fragment {

    int val;

    public FgmActors() {
    }

    public static FgmActors newInstance(int pos) {
        FgmActors f = new FgmActors();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        val = getArguments() != null ? getArguments().getInt("position") : 1;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mact_film_details_actors, container, false);

        return view;
    }


}

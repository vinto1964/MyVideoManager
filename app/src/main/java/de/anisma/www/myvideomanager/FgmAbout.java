package de.anisma.www.myvideomanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Marc on 01.05.2015.
 */
public class FgmAbout extends Fragment {
    int val;

    public FgmAbout() {
    }

    public static FgmAbout newInstance(int pos) {
        FgmAbout f = new FgmAbout();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            val = getArguments().getInt("position");
        }
        else {
            val = 2;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fgm_help_about, container, false);


        return view;
    }


}

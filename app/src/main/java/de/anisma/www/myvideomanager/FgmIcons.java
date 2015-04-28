package de.anisma.www.myvideomanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Marc on 28.04.2015.
 */
public class FgmIcons  extends Fragment {

    int val;

    public FgmIcons() {
    }

    public static FgmIcons newInstance(int pos) {
        FgmIcons f = new FgmIcons();
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
            val = 3;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fgm_help_icons, container, false);


        return view;
    }


}

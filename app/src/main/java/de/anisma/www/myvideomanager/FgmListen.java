package de.anisma.www.myvideomanager;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Marc on 01.05.2015.
 */
public class FgmListen extends Fragment{

    int val;

    public FgmListen() {
    }

    public static FgmListen newInstance(int pos) {
        FgmListen f = new FgmListen();
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
            val = 1;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fgm_help_listen, container, false);
        return view;
    }
}

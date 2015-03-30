package de.anisma.www.myvideomanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alfa on 30.03.2015.
 */
public class FgmComment extends Fragment {

    int val;

    public FgmComment() {
    }

    public static FgmComment newInstance(int pos) {
        FgmComment f = new FgmComment();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        val = getArguments() != null ? getArguments().getInt("position") : 3;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mact_film_details_comment, container, false);

        return view;
    }

}

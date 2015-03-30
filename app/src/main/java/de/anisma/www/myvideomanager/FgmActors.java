package de.anisma.www.myvideomanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * Created by Alfa on 30.03.2015.
 */
public class FgmActors extends Fragment {

    int val;

    ImageView ivActorFoto;
    EditText edActRole, edActRoleOrder, edActFirstName, edActLastName, edPlot;
    ImageButton ibAddActor, ibDeleteActor;
    Spinner spFunction;
    ListView lvActors;


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

        ivActorFoto     = (ImageView) view.findViewById(R.id.ivActorFoto);
        edActRole       = (EditText) view.findViewById(R.id.edActRole);
        edActRoleOrder  = (EditText) view.findViewById(R.id.edActRoleOrder);
        edActFirstName  = (EditText) view.findViewById(R.id.edActFirstName);
        edActLastName   = (EditText) view.findViewById(R.id.edActLastName);
        ibAddActor      = (ImageButton) view.findViewById(R.id.ibFilmAdd);
        ibDeleteActor   = (ImageButton) view.findViewById(R.id.ibDeleteActor);
        lvActors        = (ListView) view.findViewById(R.id.lvActors);
        spFunction      = (Spinner) view.findViewById(R.id.spFunction);



        return view;
    }


}

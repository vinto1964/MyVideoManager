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
    int iPos = -1;

    ImageView ivActorFoto;
    EditText edActRole, edActRoleOrder, edActFirstName, edActLastName, edPlot;
    ImageButton ibDeleteActor;
    Spinner spFunction;
    ListView lvActors;


    public FgmActors() {
    }

    public static FgmActors newInstance(int pos, int iPosSelect) {
        FgmActors f = new FgmActors();
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
            val = 1;
            iPos = -1;
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mact_film_details_actors, container, false);

        ivActorFoto     = (ImageView) view.findViewById(R.id.ivActorFoto);
        edActRole       = (EditText) view.findViewById(R.id.edActRole);
        edActRoleOrder  = (EditText) view.findViewById(R.id.edActRoleOrder);
        edActFirstName  = (EditText) view.findViewById(R.id.edActFirstName);
        edActLastName   = (EditText) view.findViewById(R.id.edActLastName);
        ibDeleteActor   = (ImageButton) view.findViewById(R.id.ibDeleteActor);
        lvActors        = (ListView) view.findViewById(R.id.lvActors);
        spFunction      = (Spinner) view.findViewById(R.id.spFunction);



        return view;
    }




}

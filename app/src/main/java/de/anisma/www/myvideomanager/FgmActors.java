package de.anisma.www.myvideomanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alfa on 30.03.2015.
 */
public class FgmActors extends Fragment implements View.OnClickListener {

    AppGlobal myApp;

    private final int ACTION_FROM_BUTTON = 1;
    private final int ACTION_FROM_DESTROY = 0;

    int val;
    int iPos = -1;
    int iDeleteActor = -1;


    ImageView ivActorFoto;
    EditText edActRole, edActRoleOrder, edActFirstName, edActLastName, edPlot;
    ImageButton ibSave, ibEditActor;
    Spinner spFunction;
    ListView lvActors;
    List<String>actorsList = new ArrayList<>();
    List<String> listFuntion = new ArrayList<String>();
    ArrayAdapter actorListAdapter;
    ArrayAdapter<String> spAdapter;

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

        ibSave         = (ImageButton) view.findViewById(R.id.ibSave);
        ibSave.setOnClickListener(this);
        ibEditActor = (ImageButton) view.findViewById(R.id.ibEditActor);
        ibEditActor.setOnClickListener(this);

        lvActors        = (ListView) view.findViewById(R.id.lvActors);
        lvActors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SActActors.class);
                intent.putExtra("actorID", Integer.parseInt(actorsList.get(position).substring(actorsList.get(position).indexOf("<")+1)));
                startActivity(intent);
            }
        });

        spFunction      = (Spinner) view.findViewById(R.id.spFunction);

        if(iPos > -1){
            loadDatas();
        }
        return view;
    }

    private void doShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Schauspieler/in löschen");
        builder.setMessage("Möchten Sie wirklich löschen?");
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_important);

        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteActorFromFilm();
            }
        });
        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteActorFromFilm() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        if(iDeleteActor > -1) {

            String idPerson = actorsList.get(iDeleteActor).substring(actorsList.get(iDeleteActor).indexOf("<")+1);
            long lPersonID = Long.parseLong(idPerson);

            myApp.dbVideo.deleteActorFromFilm(lPersonID, myApp.ldFilmItems.get(iPos).getlFilm_ID());

            actorsList.remove(iDeleteActor);
            loadPersonList();
        }
        iDeleteActor = -1;
    }

    private void loadDatas() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        DTFilmItem film = myApp.ldFilmItems.get(iPos);

        listFuntion = myApp.dbVideo.loadAllFunction(true);
        spAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listFuntion);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFunction.setAdapter(spAdapter);

        edActRole.setHint( getString(R.string.sRole) + " in " + film.getsFilmTitle());
        loadPersonList();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ibSave:
                saveDatas(ACTION_FROM_BUTTON);
                break;

            case R.id.ibEditActor:
                showDialog();
                break;

        }
    }

    private void saveDatas(int source) {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        DTFilmItem film;
        if(iPos > -1) {
            film = myApp.ldFilmItems.get(iPos);
        }
        else {

            int pos = myApp.ldFilmItems.size()-1;

            film = myApp.ldFilmItems.get(myApp.ldFilmItems.size()-1);
        }
        int checkOrder = -1;
        long id_role = -1;

        // Nur wenn Vorname und Nachname eingegeben worden sind
        if(!edActFirstName.getText().toString().isEmpty() && !edActLastName.getText().toString().isEmpty()) {

                    /* Überprüfen:  1. ob Actor in der DB vorhanden ist
                                    2. Wenn Schauspieler => Role schon vorhanden? / Wenn Regisseur => nicht relevant
                                    3. ob Actor schon in zusammenhang mit Film und Role eingetragen ist ==> Actor kann mehrere Rolen spielen

                       Wenn Actor als Person vorhanden => nur Role
                       Wenn Person + Role vorhanden => person_is
                    */
            if(!edActRoleOrder.getText().toString().isEmpty()) {
                checkOrder = Integer.parseInt(edActRoleOrder.getText().toString());
            }

            if((checkOrder > -1) && (edActRole.getText().toString().isEmpty())) {
                Toast.makeText(this.getActivity().getBaseContext(), "Bitte geben Sie eine Role ein, oder löschen Sie die Reihenfolge!", Toast.LENGTH_SHORT).show();
            }

            // 1
            if(myApp.dbVideo.checkActor(edActFirstName.getText().toString(), edActLastName.getText().toString()) < 0) { // Actor nicht vorhanden
                saveActor(edActFirstName.getText().toString(), edActLastName.getText().toString());
            }
            DTActor actor = myApp.dbVideo.loadActor(edActFirstName.getText().toString(), edActLastName.getText().toString());

            // 2
            if(spFunction.getSelectedItemPosition() == 0){  // Für ein Schauspieler
                if(!edActRole.getText().toString().isEmpty()) {
                    if(myApp.dbVideo.checkRole(edActRole.getText().toString()) < 0 ) { // Role schon in der DB
                        myApp.dbVideo.insertRole(edActRole.getText().toString());
                    }
                    id_role = myApp.dbVideo.checkRole(edActRole.getText().toString());
                }
                else {
                    id_role = -1;
                }
            }

            // 3
            long function_id = myApp.dbVideo.checkFunction(String.valueOf(spFunction.getSelectedItem()));
            if(!myApp.dbVideo.checkPersonIs(film.getlFilm_ID(), actor.getlActor_ID(), function_id)){ // Schauspieler für Film vorhanden
                myApp.dbVideo.insertPersonIs(   film.getlFilm_ID(),
                        actor.getlActor_ID(),
                        function_id,
                        id_role,
                        checkOrder);
            }
            else {
                myApp.dbVideo.updatePersonIs(   film.getlFilm_ID(),
                        actor.getlActor_ID(),
                        function_id,
                        id_role,
                        checkOrder);
            }
            loadPersonList();
            clearFields();
        }
        else {
            if(source > 0) {
                Toast.makeText(this.getActivity().getBaseContext(), "Bitte geben Sie Vorname und Nachnamen ein!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showDialog() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fgmactors_edit_actor_dialog);
        //dialog.setTitle(Resources.getSystem().getString(R.string.sChoiceActor));
        dialog.setTitle("Bitte auswählen zum Bearbeiten od. Long Touch zum Löschen aus der Liste:");

        final CCheckThis cto = new CCheckThis();
        final ListView lvForEditActor = (ListView) dialog.findViewById(R.id.lvForEditActor);

        //ActorListAdapter actorListAdapter;
        final List<DTActor>  actorsListForDialog = new ArrayList<DTActor>();

        myApp.dbVideo.loadActorsForDialog(actorsListForDialog, myApp.ldFilmItems.get(iPos).getlFilm_ID());
        ActorListAdapter actorListAdapter = new ActorListAdapter(getActivity(), R.layout.listview_actors, actorsListForDialog);
        lvForEditActor.setAdapter(actorListAdapter);
        lvForEditActor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadRoleForActor(actorsListForDialog.get(position).getlActor_ID());
                dialog.dismiss();
            }
        });
        lvForEditActor.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                iDeleteActor = position;
                doShowAlertDialog();
                return false;
            }
        });

        dialog.show();

    }

    private void loadRoleForActor(long position) {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        DTActorRole actorRole = myApp.dbVideo.loadActorForRole(myApp.ldFilmItems.get(iPos).getlFilm_ID(), position);

        edActRole.setText(actorRole.getsARRoleInFilm());
        edActRoleOrder.setText(actorRole.getiAROrder() > -1 ? "" + actorRole.getiAROrder() : "");
        edActFirstName.setText(actorRole.getsARFirstName());
        edActLastName.setText(actorRole.getsARLastName());
        if(!actorRole.getsImagePath().isEmpty()) {
            createFoto(actorRole.getsImagePath());
        }
    }

    private void clearFields() {
        edActRole.setText("");
        edActRoleOrder.setText("");
        edActFirstName.setText("");
        edActLastName.setText("");
        createFoto("@mipmap/ic_actor");
    }

    private void saveActor(String firstname, String lastname) {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        DTActor actor = new DTActor(-1,
                    firstname,
                    lastname,
                    "",
                    "",
                    "");
        myApp.dbVideo.insertActor(actor);
    }

    private void loadPersonList() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        List<DTActorRole>listActorRoles = new ArrayList<DTActorRole>();
        List<String>actorsListSimple = new ArrayList<>();
        String sOutput = "";
        if(iPos > -1) {
            actorsList = myApp.dbVideo.loadFilmActorsList(myApp.ldFilmItems.get(iPos).getlFilm_ID());
            for (int i = 0; i < actorsList.size(); i++) {
                String personID = actorsList.get(i).substring(actorsList.get(i).indexOf("<") + 1);

                listActorRoles.add(myApp.dbVideo.loadActorForRole(myApp.ldFilmItems.get(iPos).getlFilm_ID(), Integer.parseInt(personID)));
            }
            for (DTActorRole dtar : listActorRoles) {
                String sOutputN = dtar.getsARFirstName() + ", " + dtar.getsARLastName();
                if (!dtar.getsARRoleInFilm().isEmpty()) {
                    sOutputN += " (" + dtar.getsARRoleInFilm() + ")";
                }
                actorsListSimple.add(sOutputN);
            }
        }
        else {
            sOutput = edActFirstName.getText().toString() + ", " + edActLastName.getText().toString();
            if(!edActRole.getText().toString().isEmpty()) {
                sOutput += " (" + edActRole.getText().toString() + ")";
            }
            actorsListSimple.add(sOutput);
        }
        actorListAdapter = new ArrayAdapter(this.getActivity().getBaseContext(), android.R.layout.simple_list_item_1, actorsListSimple);
        lvActors.setAdapter(actorListAdapter);
    }

    class CCheckThis {
        boolean bValue = false;
    }

    private void createFoto(String uri){
        Bitmap btm = null;

        if(uri.compareTo("@mipmap/ic_actor") == 0 || uri.isEmpty()){
            try {
                btm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_actor);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            if (android.os.Build.VERSION.SDK_INT < 19){
                try {
                    btm = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), Uri.parse(uri));
                }
                catch (FileNotFoundException e) { e.printStackTrace(); }
                catch (IOException e) { e.printStackTrace(); }
            }
            else {
                try {
                    this.getActivity().getContentResolver().notifyChange(Uri.parse(uri), null);
                    ContentResolver cr = this.getActivity().getContentResolver();
                    btm = android.provider.MediaStore.Images.Media.getBitmap(cr, Uri.parse(uri));
                }
                catch (FileNotFoundException e) { e.printStackTrace();  }
                catch (IOException e) { e.printStackTrace(); }
            }
        }
        Bitmap scaledFoto = Bitmap.createScaledBitmap(btm, 70, 70, false);
        ivActorFoto.setImageBitmap(scaledFoto);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveDatas(ACTION_FROM_DESTROY);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(iPos > -1) {
           loadDatas();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

}

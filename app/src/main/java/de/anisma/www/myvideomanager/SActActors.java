package de.anisma.www.myvideomanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.List;


public class SActActors extends ActionBarActivity implements View.OnClickListener {

    AppGlobal myApp;
    List<DTActor> listActors;
    ImageView ivActorFoto;
    
    EditText edLastName, edFirstName, edBirthday, edVita;
    Switch swSex;
    ImageButton ibSave, ibDelActor;
    int iPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sact_actors);

        myApp = (AppGlobal) getApplication();
        Intent intent = getIntent();
        iPos = intent.getIntExtra("Position", -1);


        ivActorFoto = (ImageView) findViewById(R.id.ivActorFoto);
        edLastName  = (EditText) findViewById(R.id.edLastName);
        edFirstName = (EditText) findViewById(R.id.edFirstName);
        edBirthday  = (EditText) findViewById(R.id.edBirthday);
        edVita      = (EditText) findViewById(R.id.edVita);       
        ibSave      = (ImageButton) findViewById(R.id.ibSave);
        ibDelActor  = (ImageButton) findViewById(R.id.ibDelActor);
        swSex       = (Switch) findViewById(R.id.swSex);
        ibSave.setOnClickListener(this);
        ibDelActor.setOnClickListener(this);



        if(iPos > -1) {
            loadActor();
        }
    }

    private void loadActor() {

        DTActor actor = myApp.dbVideo.loadActor(myApp.listActorItems.get(iPos).getlActor_ID());
        // TODO image

        edLastName.setText(actor.getsActorLastName());
        edFirstName.setText(actor.getsActorFirstName());
        edBirthday.setText(actor.getsBirthday());
        edVita.setText(actor.getsVita());

        if(actor.getsSex().compareTo("w") == 0) {
            swSex.setChecked(false);
        }
        else {
            swSex.setChecked(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sact_actors, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibSave:
                saveActor();
                break;

            case R.id.ibDelActor:
                myApp.dbVideo.deleteActor(myApp.listActorItems.get(iPos).getlActor_ID());
                myApp.listActorItems.remove(iPos);
                break;
        }
        clearFields();
    }

    private void saveActor() {
        if(!(edFirstName.getText().toString().isEmpty() && edLastName.getText().toString().isEmpty() )){
            // Prüfen, on Person in DB ist => wenn Ja <==> updaten
            String firstname = edFirstName.getText().toString();
            String lastname = edLastName.getText().toString();

            if(myApp.dbVideo.checkActor(firstname, lastname) > 0) { // Person vorhanden
                myApp.dbVideo.updatePerson(new DTActor(myApp.dbVideo.checkActor(firstname, lastname),
                        edFirstName.getText().toString(),
                        edLastName.getText().toString(),
                        edBirthday.getText().toString(),
                        swSex.isChecked() ? "m" : "w",
                        edVita.getText().toString()));
            }
            else { // Einfügen
                myApp.dbVideo.insertActor(new DTActor(-1,
                        edFirstName.getText().toString(),
                        edLastName.getText().toString(),
                        edBirthday.getText().toString(),
                        swSex.isChecked() ? "m" : "w",
                        edVita.getText().toString()));
            }
        }
    }

    private void clearFields() {
        //ivActorFoto = (ImageView) findViewById(R.id.ivActorFoto);
        edLastName.setText("");
        edFirstName.setText("");
        edBirthday.setText("");
        edVita.setText("");
        swSex.setChecked(false);
    }
}

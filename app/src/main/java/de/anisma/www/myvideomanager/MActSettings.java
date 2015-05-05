package de.anisma.www.myvideomanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MActSettings extends ActionBarActivity implements View.OnClickListener {

    AppGlobal myApp;
    CHDatabase table;

    TextView tvSettingTitle, tvHelpText;
    ImageButton ibSave, ibDeleteChoice;
    Spinner spChoice;
    EditText edChoice;

    String artSetting;
    String saveChoice = "";
    String saveFunction = "";

    List<String> listChoice = new ArrayList<String>();
    List<String> listGenre = new ArrayList<String>();
    List<String> listFunction = new ArrayList<String>();

    ArrayAdapter<String> spAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mact_settings);

        myApp = (AppGlobal) getApplication();

        Intent intent = getIntent();
        artSetting = intent.getStringExtra("genre");

        tvSettingTitle  = (TextView) findViewById(R.id.tvSettingTitle);
        tvHelpText      = (TextView) findViewById(R.id.tvHelpText);
        spChoice        = (Spinner) findViewById(R.id.spChoice);
        edChoice        = (EditText) findViewById(R.id.edChoice);
        ibSave          = (ImageButton) findViewById(R.id.ibSave);
        ibDeleteChoice  = (ImageButton) findViewById(R.id.ibDeleteChoice);

        if(artSetting.compareTo("ok") == 0) {
            // Genre bearbeiten
            tvSettingTitle.setText(getString(R.string.sGenreSetting));
            tvHelpText.setText(getString(R.string.sChoiceGenre));
            loadChoice();
        }
        else {
            // Funktion bearbeiten
            tvSettingTitle.setText(getString(R.string.sFunctionSetting));
            tvHelpText.setText(getString(R.string.sChoiceFunction));
            loadChoice();
        }
        ibSave.setOnClickListener(this);
        ibDeleteChoice.setOnClickListener(this);
    }

    private void loadChoice() {
        if(artSetting.compareTo("ok") == 0) {
            listChoice = myApp.dbVideo.loadAllGenre();
        }
        else {
            listChoice = myApp.dbVideo.loadAllFunction(false);
        }
        spAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listChoice);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spChoice.setAdapter(spAdapter);
        spChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fetchSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void saveChoice() {
        long choiceID;
        if(!edChoice.getText().toString().isEmpty()) {
            if(artSetting.compareTo("ok") == 0) {
                choiceID = myApp.dbVideo.getGFID("genre", saveChoice);
                if(choiceID > -1) {
                    myApp.dbVideo.updateGF("genre", choiceID, edChoice.getText().toString());
                }
                else {
                    myApp.dbVideo.insertGF("genre", edChoice.getText().toString());
                }
            }
            else {
                choiceID = myApp.dbVideo.getGFID("functions", saveChoice);
                if(choiceID > -1) {
                    myApp.dbVideo.updateGF("functions", choiceID, edChoice.getText().toString());
                }
                else {
                    myApp.dbVideo.insertGF("functions", edChoice.getText().toString());
                }
            }
            loadChoice();
        }
    }


    private void fetchSelectedItem() {
        if(spChoice.getSelectedItemPosition() != 0) {
            saveChoice = String.valueOf(spChoice.getSelectedItem());
            edChoice.setText(saveChoice);
            spChoice.setSelection(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mact_settings, menu);
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibSave:
                saveChoice();
                break;

            case R.id.ibDeleteChoice:
                deleteChoice();
                break;
        }
    }

    private void deleteChoice() {
        long choiceID;
        if(!edChoice.getText().toString().isEmpty()) {
            fetchSelectedItem();
            if(artSetting.compareTo("ok") == 0) {
                choiceID = myApp.dbVideo.getGFID("genre", saveChoice);
            }
            else {
                choiceID = myApp.dbVideo.getGFID("functions", saveChoice);
            }
            if(choiceID > -1) {
                if(artSetting.compareTo("ok") == 0) {
                    //myApp.dbVideo.deleteGenre(choiceID);
                    myApp.dbVideo.deleteGF("genre", choiceID);
                }
                else {
                    myApp.dbVideo.deleteGF("functions", choiceID);
                }
            }
            edChoice.setText("");
        }
        loadChoice();
    }

}

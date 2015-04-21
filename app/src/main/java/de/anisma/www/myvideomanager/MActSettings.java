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


public class MActSettings extends ActionBarActivity {

    AppGlobal myApp;

    TextView tvSettingTitle, tvHelpText;
    ImageButton ibSave;
    Spinner spChoice;
    EditText edChoice;

    String artSetting;
    String saveChoice = "";
    String newChoice = "";

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




        if(artSetting.compareTo("ok") == 0) {
            // Genre bearbeiten
            tvSettingTitle.setText(getString(R.string.sGenreSetting));
            tvHelpText.setText(getString(R.string.sChoiceGenre));
            List<String> listGenre = new ArrayList<String>();
            listGenre = myApp.dbVideo.loadAllGenre();
            ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listGenre);
            spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spChoice.setAdapter(spAdapter);
            spChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    fetchGenre();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else {
            // Funktion bearbeiten
            tvSettingTitle.setText(getString(R.string.sFunctionSetting));
            tvHelpText.setText(getString(R.string.sChoiceFunction));



        }

        ibSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void fetchGenre() {
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
}

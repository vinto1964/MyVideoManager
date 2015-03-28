package de.anisma.www.myvideomanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;



public class MActFilmDetails extends ActionBarActivity implements View.OnClickListener {

    String sEditable;
    EditText edTitle, edSubTitel, edOTitel, edPubYear, edCountry, edPlot, edComment;
    EditText edRanking, edFSK, edEAN, edDuration;
    ImageButton ibSave1, ibSave2, ibNext, ibBack;
    RatingBar rbRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mact_film_details);

        Intent intent = getIntent();
        sEditable = intent.getStringExtra("Edit");

        edTitle = (EditText) findViewById(R.id.edTitel);
        edSubTitel = (EditText) findViewById(R.id.edSubtitle);
        edOTitel = (EditText) findViewById(R.id.edOTitle);
        edPubYear = (EditText) findViewById(R.id.edPubYear);
        edCountry = (EditText) findViewById(R.id.edCountry);
        edPlot = (EditText) findViewById(R.id.edPlot);
        edComment = (EditText) findViewById(R.id.edComment);

        ibSave1 = (ImageButton) findViewById(R.id.ibSave1);
        ibSave1.setOnClickListener(this);

//        ibSave2 = (ImageButton) findViewById(R.id.ibSave2);
//        ibSave2.setOnClickListener(this);

        ibNext = (ImageButton) findViewById(R.id.ibNext);
        ibNext.setOnClickListener(this);

/*        ibBack = (ImageButton) findViewById(R.id.ibBack);
        ibBack.setOnClickListener(this);*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mact_film_details, menu);
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
        switch(v.getId()) {

            case R.id.ibSave1:
                break;

/*            case R.id.ibSave2:
                break;*/

            case R.id.ibNext:

                setContentView(R.layout.activity_mact_film_details_2);

                break;

/*            case R.id.ibBack:
                setContentView(R.layout.activity_mact_film_details);
                break;*/

        }
    }
}



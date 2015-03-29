package de.anisma.www.myvideomanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    List<DTFilmItem> filmlist = new ArrayList<DTFilmItem>();
    ImageView imgMASearch;
    ImageButton ibFilmAdd;
    ListView lvMA;
    CHDatabase dbVideo;


    int iPos = -1;
    long lFilmID = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("DB", "vor onCreate");

        dbVideo = new CHDatabase(this);
        imgMASearch = (ImageView) findViewById(R.id.imgMASearch);

        lvMA = (ListView)findViewById(R.id.lvMA);

        ibFilmAdd = (ImageButton) findViewById(R.id.ibFilmAdd);
        ibFilmAdd.setOnClickListener(this);

        loadFilmList();

    }

    private void loadFilmList() {
        dbVideo.loadAllFilms(filmlist);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        switch (v.getId()){
            // Hinzufügen
            case R.id.ibFilmAdd:
                //Intent intent = new Intent(this, MActFilmDetails.class);
                Intent intent = new Intent(this, TActFilmDetails.class);
                intent.putExtra("Position", iPos);
                startActivity(intent);
                break;
        }
    }
}

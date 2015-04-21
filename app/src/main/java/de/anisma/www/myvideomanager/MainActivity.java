package de.anisma.www.myvideomanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    AppGlobal myApp;

    List<DTFilmItem> ldFilmItems;
    FilmListAdapter flAdapter;
    EditText edMASearch;
    ImageView imgMASearch;
    ImageButton ibFilmAdd, ibShowActor, ibCancelSearch, ibNoFilmografy;
    ListView lvMA;

    int iPos = -1;
    int iActorID = -1;
    int delPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myApp = (AppGlobal) getApplication();
        Intent intent = getIntent();
        iActorID = intent.getIntExtra("actorID", -1);

        myApp.dbVideo = new CHDatabase(this);

        edMASearch = (EditText) findViewById(R.id.edMASearch);
        imgMASearch = (ImageView) findViewById(R.id.imgMASearch);
        imgMASearch.setOnClickListener(this);

        lvMA = (ListView)findViewById(R.id.lvMA);

        ibFilmAdd = (ImageButton) findViewById(R.id.ibFilmAdd);
        ibFilmAdd.setOnClickListener(this);

        ibShowActor = (ImageButton) findViewById(R.id.ibShowActor);
        ibShowActor.setOnClickListener(this);

        ibCancelSearch = (ImageButton) findViewById(R.id.ibCancelSearch);
        ibCancelSearch.setOnClickListener(this);

        ibNoFilmografy = (ImageButton) findViewById(R.id.ibNoFilmografy);
        ibNoFilmografy.setOnClickListener(this);

        if(iActorID > -1) {
            ibNoFilmografy.setVisibility(View.VISIBLE);
            loadFilmListFromActor(iActorID);
        }
        else {
            ibNoFilmografy.setVisibility(View.INVISIBLE);
            loadFilmList("");
        }
    }


    @Override
    protected void onResume(){
        super.onResume();
        flAdapter.notifyDataSetChanged();
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
        if(id == R.id.action_addGenre) {
            Intent intent = new Intent(this, MActSettings.class);
            intent.putExtra("genre", "ok");
            startActivity(intent);
        }
        if(id == R.id.action_addFunction) {
            Intent intent = new Intent(this, MActSettings.class);
            intent.putExtra("genre", "not");
            startActivity(intent);
        }
        if(id == R.id.action_help) {
            Toast.makeText(this, "help gewählt: " + id, Toast.LENGTH_LONG).show();
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
                Intent intentFilm = new Intent(this, TActFilmDetails.class);
                intentFilm.putExtra("position", -1);
                startActivity(intentFilm);
                break;

            case R.id.ibShowActor:
                Intent intentActor = new Intent(this, MActActors.class);
                startActivity(intentActor);
                break;

            case R.id.imgMASearch:
                loadFilmList(edMASearch.getText().toString());
                flAdapter.notifyDataSetChanged();
                ibCancelSearch.setVisibility(View.VISIBLE);
                break;

            case R.id.ibCancelSearch:
                loadFilmList("");
                flAdapter.notifyDataSetChanged();
                ibCancelSearch.setVisibility(View.INVISIBLE);
                break;

            case R.id.ibNoFilmografy:
                ibNoFilmografy.setVisibility(View.INVISIBLE);
                loadFilmList("");
                break;

        }
    }

    private void loadFilmList(String whereClause) {
        myApp.dbVideo.loadAllFilms(myApp.ldFilmItems, whereClause);

        flAdapter = new FilmListAdapter(this, R.layout.ma_listview_filme, myApp.ldFilmItems);
        lvMA.setAdapter(flAdapter);

        lvMA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, TActFilmDetails.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        lvMA.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                delPosition = position;
                doShowAlertDialog();
                return false;
            }
        });

    }

    private void loadFilmListFromActor(int actorID) {
        myApp.dbVideo.loadAllFilmsFromActor(myApp.ldFilmItems, actorID);

        flAdapter = new FilmListAdapter(this, R.layout.ma_listview_filme, myApp.ldFilmItems);
        lvMA.setAdapter(flAdapter);

        lvMA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, TActFilmDetails.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

    }

    private void deleteEntry() {
        myApp.dbVideo.deleteFilm(myApp.ldFilmItems.get(delPosition).getlFilm_ID());
        myApp.ldFilmItems.remove(delPosition);
        iPos = -1;
        loadFilmList("");
    }

    private void doShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Film löschen");
        builder.setMessage("Wollen Sie wirklich den Film löschen?");
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_important);

        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteEntry();
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

}

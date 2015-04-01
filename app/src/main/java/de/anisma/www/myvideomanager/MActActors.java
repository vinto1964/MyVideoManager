package de.anisma.www.myvideomanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MActActors extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    AppGlobal myApp;
    ListView lvActorslist;
    ImageButton ibSave;
    ActorListAdapter actorListAdapter;
    List actorsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mact_actors);

        myApp = (AppGlobal) getApplication();
        actorsList = new ArrayList<DTActor>();

        lvActorslist = (ListView) findViewById(R.id.lvActorslist);
        actorListAdapter = new ActorListAdapter(this, R.layout.listview_actors, myApp.dbVideo.loadAllActors(actorsList));
        lvActorslist.setAdapter(actorListAdapter);
        lvActorslist.setOnItemClickListener(this);

        ibSave = (ImageButton) findViewById(R.id.ibSave);
        ibSave.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        actorsList = myApp.dbVideo.loadAllActors(actorsList);
        actorListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mact_actors, menu);

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
                Intent intentActor = new Intent(this, SActActors.class);
                intentActor.putExtra("Position", -1);
                startActivity(intentActor);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, SActActors.class);
        intent.putExtra("Position", position);
        startActivity(intent);

    }
}

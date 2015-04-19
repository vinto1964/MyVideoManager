package de.anisma.www.myvideomanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MActActors extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    AppGlobal myApp;
    ListView lvActorslist;
    EditText edSearchActor;
    ImageButton ibSave, ibSearchActor, ibDeleteSearchActor;
    ActorListAdapter actorListAdapter;
    List actorsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mact_actors);

        myApp = (AppGlobal) getApplication();
        actorsList = new ArrayList<DTActor>();

        edSearchActor = (EditText) findViewById(R.id.edSearchActor);
        lvActorslist = (ListView) findViewById(R.id.lvActorslist);

        ibSave = (ImageButton) findViewById(R.id.ibSave);
        ibSave.setOnClickListener(this);

        ibSearchActor = (ImageButton) findViewById(R.id.ibSearchActor);
        ibSearchActor.setOnClickListener(this);

        ibDeleteSearchActor = (ImageButton) findViewById(R.id.ibDeleteSearchArctor);
        ibDeleteSearchActor.setOnClickListener(this);

        loadActorList("");
    }

    private void loadActorList(String whereClause) {
        myApp.dbVideo.loadAllActors(myApp.listActorItems, whereClause);
        actorListAdapter = new ActorListAdapter(this, R.layout.listview_actors, myApp.listActorItems);
        lvActorslist.setAdapter(actorListAdapter);
        lvActorslist.setOnItemClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        myApp.dbVideo.loadAllActors(myApp.listActorItems, "");
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
                intentActor.putExtra("position", -1);
                startActivity(intentActor);
                break;

            case R.id.ibSearchActor:
                myApp.dbVideo.loadAllActors(myApp.listActorItems, edSearchActor.getText().toString());
                actorListAdapter.notifyDataSetChanged();
                ibDeleteSearchActor.setVisibility(View.VISIBLE);
                break;

            case R.id.ibDeleteSearchArctor:
                loadActorList("");
                ibDeleteSearchActor.setVisibility(View.INVISIBLE);
                edSearchActor.setText("");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, SActActors.class);

        String sConvert = "" + myApp.listActorItems.get(position).getlActor_ID();
        intent.putExtra("actorID", Integer.parseInt(sConvert));
//        intent.putExtra("position", position);
        startActivity(intent);

    }
}

package de.anisma.www.myvideomanager;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


public class SActActors extends ActionBarActivity implements View.OnClickListener {

    AppGlobal myApp;
    private static final int RQ_GALLERY_PICK = 1;

    List<DTActor> listActors;
    ImageView ivActorFoto;
    DTActor actor;
    
    EditText edLastName, edFirstName, edBirthday, edVita;
    Switch swSex;
    ImageButton ibSave, ibDelActor, ibGoogleWS, ibFilmografy;
    int iPos = -1;
    int iActorID = -1;
    Uri fileUri, targetUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sact_actors);

        myApp = (AppGlobal) getApplication();
        Intent intent = getIntent();
        iActorID = intent.getIntExtra("actorID", -1);
        iPos = intent.getIntExtra("position", -1);

        ivActorFoto = (ImageView) findViewById(R.id.ivActorFoto);
        ivActorFoto.setOnClickListener(this);

        edLastName  = (EditText) findViewById(R.id.edLastName);
        edFirstName = (EditText) findViewById(R.id.edFirstName);
        edBirthday  = (EditText) findViewById(R.id.edBirthday);
        edVita      = (EditText) findViewById(R.id.edVita);       
        ibSave      = (ImageButton) findViewById(R.id.ibSave);
        ibDelActor  = (ImageButton) findViewById(R.id.ibDelActor);
        ibGoogleWS  = (ImageButton) findViewById(R.id.ibGoogleWebsearch);
        ibFilmografy = (ImageButton) findViewById(R.id.ibFilmografy);
        ibFilmografy.setVisibility(View.INVISIBLE);
        swSex       = (Switch) findViewById(R.id.swSex);
        ibSave.setOnClickListener(this);
        ibDelActor.setOnClickListener(this);
        ibGoogleWS.setOnClickListener(this);
        ibFilmografy.setOnClickListener(this);

        if(iPos > -1) {
            actor = myApp.dbVideo.loadActor(myApp.listActorItems.get(iPos).getlActor_ID());
            loadActor();
        }
        if(iActorID > -1) {
            ibFilmografy.setVisibility(View.VISIBLE);
            actor = myApp.dbVideo.loadActor(iActorID);
            loadActor();
        }
    }

    private void loadActor() {
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

        createFoto(actor.getsImage().toString());

        if (android.os.Build.VERSION.SDK_INT < 19) {
            fileUri = Uri.parse(actor.getsImage().toString().toString());
        }
        else {
            targetUri = Uri.parse(actor.getsImage().toString().toString());
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
                clearFields();
                break;

            case R.id.ibDelActor:
                myApp.dbVideo.deleteActor(myApp.listActorItems.get(iPos).getlActor_ID());
                myApp.listActorItems.remove(iPos);
                clearFields();
                break;

            case R.id.ivActorFoto:
                loadActorImage();
                break;

            case R.id.ibGoogleWebsearch:
                findFotoWithBrowser();
                break;

            case R.id.ibFilmografy:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("actorID", iActorID);
                startActivity(intent);
                break;
        }
    }

    private void findFotoWithBrowser() {
        String query = URLEncoder.encode(edLastName.getText().toString() + "+" + edFirstName.getText().toString());
        String url = "https://www.google.de/search?site=&tbm=isch&source=hp&biw=1244&bih=954&q=" + query;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void saveActor() {
        String imagePfad = "";

        if (android.os.Build.VERSION.SDK_INT < 19){
            if(fileUri != null) {
                imagePfad = fileUri.toString();
            }
        }
        else {
            if(targetUri != null) {
                imagePfad = targetUri.toString();
            }
        }

        if(!(edFirstName.getText().toString().isEmpty() && edLastName.getText().toString().isEmpty() )){
            // Prüfen, on Person in DB ist => wenn Ja <==> updaten
            String firstname = edFirstName.getText().toString();
            String lastname = edLastName.getText().toString();

            if(iActorID > -1) { // Person vorhanden
                //myApp.dbVideo.updatePerson(new DTActor(myApp.dbVideo.checkActor(firstname, lastname),
                myApp.dbVideo.updatePerson(new DTActor( iActorID,
                                                        edFirstName.getText().toString(),
                                                        edLastName.getText().toString(),
                                                        edBirthday.getText().toString(),
                                                        swSex.isChecked() ? "m" : "w",
                                                        imagePfad,
                                                        edVita.getText().toString()));
            }
            else { // Einfügen
                if(myApp.dbVideo.checkActor(edFirstName.getText().toString(), edLastName.getText().toString()) < 0) {
                    myApp.dbVideo.insertActor(new DTActor(-1,
                            edFirstName.getText().toString(),
                            edLastName.getText().toString(),
                            edBirthday.getText().toString(),
                            swSex.isChecked() ? "m" : "w",
                            imagePfad,
                            edVita.getText().toString()));
                }
                else {
                    Toast.makeText(this, "Schauspieler/in schon vorhanden", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void clearFields() {
        createFoto("@mipmap/ic_actor");
        edLastName.setText("");
        edFirstName.setText("");
        edBirthday.setText("");
        edVita.setText("");
        swSex.setChecked(false);
    }

    private void loadActorImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RQ_GALLERY_PICK);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null) {
            if (android.os.Build.VERSION.SDK_INT < 19) { //dies für api < kitkat
                onActivityResultOlder(requestCode, resultCode, data);
            } else {
                onActivityResultKitKat(requestCode, resultCode, data);
            }
        }
    }

    // bis kitkat (< 4.4)
    protected void onActivityResultOlder(int requestCode, int resultCode, Intent data) {
        fileUri = data.getData();
        if (requestCode == RQ_GALLERY_PICK) {
            Bitmap bmFotoOrig;
            try {
                bmFotoOrig = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                Bitmap bmFoto = Bitmap.createScaledBitmap(bmFotoOrig, 100, 100, false);
                ivActorFoto.setImageBitmap(bmFoto);
                saveActor();
                saveImage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            getContentResolver().delete(fileUri, null, null);
        }
    }

    // ab kitkat (>= android 4.4)
    protected void onActivityResultKitKat(int requestCode, int resultCode, Intent data) {
        if (requestCode == RQ_GALLERY_PICK) {
            Uri mUri = null;
            if (data != null) {
                mUri = data.getData();
                targetUri = mUri;
            }
            if (mUri == null) {
                mUri = targetUri;
            }
            try {
                getContentResolver().notifyChange(mUri, null);
                ContentResolver cr = getContentResolver();
                Bitmap bmFotoOrig = android.provider.MediaStore.Images.Media.getBitmap(cr, mUri);

                Bitmap bmFoto = Bitmap.createScaledBitmap(bmFotoOrig, 100, 100, false);
                ivActorFoto.setImageBitmap(bmFoto);
                saveActor();
                saveImage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                getContentResolver().delete(fileUri, null, null);
            } catch (Exception e) {
            }
        }
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
                    btm = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(uri));
                }
                catch (FileNotFoundException e) { e.printStackTrace(); }
                catch (IOException e) { e.printStackTrace(); }
            }
            else {
                try {
                    getContentResolver().notifyChange(Uri.parse(uri), null);
                    ContentResolver cr = getContentResolver();
                    btm = android.provider.MediaStore.Images.Media.getBitmap(cr, Uri.parse(uri));
                }
                catch (FileNotFoundException e) { e.printStackTrace();  }
                catch (IOException e) { e.printStackTrace(); }
            }
        }
        Bitmap scaledFoto = Bitmap.createScaledBitmap(btm, 100, 100, false);
        ivActorFoto.setImageBitmap(scaledFoto);
    }

    private void saveImage() {
        if(iPos > -1) {
            if (android.os.Build.VERSION.SDK_INT < 19) {
                if (!fileUri.toString().isEmpty()) {
                    myApp.listActorItems.get(iPos).setsImage(fileUri.toString());
                } else {
                    if (!targetUri.toString().isEmpty()) {
                        myApp.listActorItems.get(iPos).setsImage(targetUri.toString());
                    }
                }
                myApp.dbVideo.updateActorImage(myApp.listActorItems.get(iPos));
            }
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}


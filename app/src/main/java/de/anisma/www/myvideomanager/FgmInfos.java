package de.anisma.www.myvideomanager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by Alfa on 30.03.2015.
 */
public class FgmInfos extends Fragment implements View.OnClickListener {

    private static final int RQ_GALLERY_PICK = 1;

    int val;
    Intent intent;
    Activity act;
    EditText edTitle, edSubtitel, edOTitel, edPubYear, edCountry, edFSK, edDuration, edEAN, edPlot;
    ImageView ivCover;
    ImageButton ibSave, ibDelete;
    Uri fileUri, targetUri;

    int iPos = -1;

    public FgmInfos() {
        this.act = this.getActivity();
    }

    public static FgmInfos newInstance(int pos, int iPosSelect) {
        FgmInfos f = new FgmInfos();
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
            val = 0;
            iPos = -1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mact_film_details_infos, container, false);

        edTitle     = (EditText) view.findViewById(R.id.edTitel);
        edSubtitel  = (EditText) view.findViewById(R.id.edSubtitle);
        edOTitel    = (EditText) view.findViewById(R.id.edOTitle);
        edPubYear   = (EditText) view.findViewById(R.id.edPubYear);
        edCountry   = (EditText) view.findViewById(R.id.edCountry);
        edFSK       = (EditText) view.findViewById(R.id.edFSK);
        edDuration  = (EditText) view.findViewById(R.id.edDuration);
        edEAN       = (EditText) view.findViewById(R.id.edEAN);

        ivCover = (ImageView) view.findViewById(R.id.ivCover);
        ivCover.setOnClickListener(this);

        ibSave = (ImageButton) view.findViewById(R.id.ibSave);
        ibSave.setOnClickListener(this);

        ibDelete = (ImageButton) view.findViewById(R.id.ibDelete);
        if(iPos > -1) {
            ibDelete.setClickable(true);
            loadFilm();
        }
        ibDelete.setOnClickListener(this);

        return view;
    }

    private void loadFilm() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        DTFilmItem film = myApp.ldFilmItems.get(iPos);
        edTitle.setText(film.getsFilmTitle());
        edSubtitel.setText(film.getsFilmSubtitle());
        edOTitel.setText(film.getsFilmOTitle());
        edPubYear.setText(film.getIntFilmPubYear() < 0 ? "" : "" + film.getIntFilmPubYear());
        edCountry.setText(film.getsFilmCountry());
        edFSK.setText(film.getiFilmFSK() < 0 ? "" : "" + film.getiFilmFSK());
        edDuration.setText(film.getiFilmDuration() < 0 ? "" : "" + film.getiFilmDuration());
        edEAN.setText(film.getiFilmEAN() < 0 ? "" : "" + film.getiFilmEAN());

        createFoto(film.getsFilmImage().toString());

        if (android.os.Build.VERSION.SDK_INT < 19) {
            fileUri = Uri.parse(film.getsFilmImage().toString());
        }
        else {
            targetUri = Uri.parse(film.getsFilmImage().toString());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCover:
                loadFilmCover();
                break;

            case R.id.ibSave:
                saveEntry();
                break;

            case R.id.ibDelete:
                deleteEntry();
                break;
        }
    }

    private void deleteEntry() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        myApp.dbVideo.deleteFilm(myApp.ldFilmItems.get(iPos).getlFilm_ID());
        myApp.ldFilmItems.remove(iPos);
        iPos = -1;

        edTitle.setText("");
        edSubtitel.setText("");
        edOTitel.setText("");
        edPubYear.setText("");
        edCountry.setText("");
        edFSK.setText("");
        edDuration.setText("");
        edEAN.setText("");
        createFoto("@drawable/cover");
    }

    private void saveEntry() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();

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
        if(iPos < 0) {  // Noch nicht in der Datenbank
            if(!(edTitle.getText().toString().isEmpty())) {
                if(myApp.dbVideo.checkFilm(edTitle.getText().toString()) < 0) {
                    myApp.ldFilmItems.add(new DTFilmItem(
                            -1,
                            edTitle.getText().toString(),
                            edSubtitel.getText().toString(),
                            edOTitel.getText().toString(),
                            edPubYear.getText().toString().isEmpty() ? -1 : Integer.parseInt(edPubYear.getText().toString()),
                            edCountry.getText().toString(),
                            imagePfad,
                            edDuration.getText().toString().isEmpty() ? -1 : Integer.parseInt(edDuration.getText().toString()),
                            edFSK.getText().toString().isEmpty() ? -1 : Integer.parseInt(edFSK.getText().toString()),
                            edEAN.getText().toString().isEmpty() ? -1 : Integer.parseInt(edEAN.getText().toString())
                    ));
                    long lRes = myApp.dbVideo.insertFilm(myApp.ldFilmItems.get(myApp.ldFilmItems.size() - 1));
                }
            }
        }
        else { // Ein Eintrag wurde bearbeitet
            DTFilmItem filmUpdate = myApp.ldFilmItems.get(iPos);
            filmUpdate.setsFilmTitle(edTitle.getText().toString());
            filmUpdate.setsFilmSubtitle(edSubtitel.getText().toString());
            filmUpdate.setsFilmOTitle(edOTitel.getText().toString());
            filmUpdate.setIntFilmPubYear(edPubYear.getText().toString().isEmpty() ? -1 : Integer.parseInt(edPubYear.getText().toString()));
            filmUpdate.setsFilmCountry(edCountry.getText().toString());
            filmUpdate.setsFilmImage(imagePfad);
            filmUpdate.setiFilmDuration(edDuration.getText().toString().isEmpty() ? -1 : Integer.parseInt(edDuration.getText().toString()));
            filmUpdate.setiFilmFSK(edFSK.getText().toString().isEmpty() ? -1 : Integer.parseInt(edFSK.getText().toString()));
            filmUpdate.setiFilmEAN(edEAN.getText().toString().isEmpty() ? -1 : Integer.parseInt(edEAN.getText().toString()));
            myApp.dbVideo.updateFilm(filmUpdate);
        }
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link android.app.Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        saveEntry();
    }

    private void loadFilmCover() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RQ_GALLERY_PICK);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (android.os.Build.VERSION.SDK_INT < 19) { //dies für api < kitkat
            onActivityResultOlder(requestCode, resultCode, data );
        } else {
            onActivityResultKitKat(requestCode, resultCode, data );
        }

    }

    // bis kitkat (< 4.4)
    protected void onActivityResultOlder(int requestCode, int resultCode, Intent data) {
        fileUri = data.getData();
        if (requestCode == RQ_GALLERY_PICK) {
            Bitmap bmFotoOrig;
            try {
                bmFotoOrig = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), fileUri);
                Bitmap bmFoto = Bitmap.createScaledBitmap(bmFotoOrig, 160, 226, false);
                ivCover.setImageBitmap(bmFoto);
                saveEntry();
                saveImage();
            }
            catch (FileNotFoundException e) { e.printStackTrace(); }
            catch (IOException e) { e.printStackTrace(); }
        }
        else {
            this.getActivity().getContentResolver().delete(fileUri, null, null);
        }
    }

    // ab kitkat (>= android 4.4)
    protected void onActivityResultKitKat(int requestCode, int resultCode, Intent data) {
        if(requestCode == RQ_GALLERY_PICK) {
            Uri mUri = null;
            if (data != null) {
                mUri = data.getData();
            }
            if (mUri == null) {
                mUri = targetUri;
            }
            try {
                this.getActivity().getContentResolver().notifyChange(mUri, null);
                ContentResolver cr = this.getActivity().getContentResolver();
                Bitmap bmFotoOrig = android.provider.MediaStore.Images.Media.getBitmap(cr, mUri);

                Bitmap bmFoto = Bitmap.createScaledBitmap(bmFotoOrig, 160, 226, false);
                ivCover.setImageBitmap(bmFoto);
                saveEntry();
                saveImage();
            }
            catch (FileNotFoundException e) { e.printStackTrace(); }
            catch (IOException e) { e.printStackTrace(); }
        }
        else {
            try {
                this.getActivity().getContentResolver().delete(fileUri, null, null);
            }
            catch (Exception e) { }
        }
    }

    private void createFoto(String uri){
        Bitmap btm = null;

        if(uri.compareTo("@drawable/cover") == 0 || uri.isEmpty()){
            try {
                btm = BitmapFactory.decodeResource(getResources(), R.drawable.cover);
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
        Bitmap scaledFoto = Bitmap.createScaledBitmap(btm, 160, 226, false);
        ivCover.setImageBitmap(scaledFoto);
    }

    private void saveImage() {
        AppGlobal myApp = (AppGlobal) getActivity().getApplication();
        if(iPos > -1) {
            if (android.os.Build.VERSION.SDK_INT < 19) {
                if (!fileUri.toString().isEmpty()) {
                    myApp.ldFilmItems.get(iPos).setsFilmImage(fileUri.toString());
                } else {
                    if (!targetUri.toString().isEmpty()) {
                        myApp.ldFilmItems.get(iPos).setsFilmImage(targetUri.toString());
                    }
                }
                myApp.dbVideo.updateFilmImage(myApp.ldFilmItems.get(iPos));
            }
        }
    }


}
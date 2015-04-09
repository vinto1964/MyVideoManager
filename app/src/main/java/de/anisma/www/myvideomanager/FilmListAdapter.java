package de.anisma.www.myvideomanager;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Marc on 14.03.2015.
 */
public class FilmListAdapter extends ArrayAdapter<DTFilmItem> {

    
    List<DTFilmItem> listFilmItems;
    Context ctx;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public FilmListAdapter(Context context, int resource, List<DTFilmItem> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.listFilmItems = objects;
    }

    /**
     * {@inheritDoc}
     *
     * @param position
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ma_listview_filme, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.ivFilm           = (ImageView) convertView.findViewById(R.id.imgMALVFilme);
            viewHolder.tvFilmTitle      = (TextView) convertView.findViewById(R.id.tvLayLVFilmTitle);
            viewHolder.tvFilmSubtitle   = (TextView) convertView.findViewById(R.id.tvLayLVFilmSubtitle);
            viewHolder.tvFilmOTitle     = (TextView) convertView.findViewById(R.id.tvLayLVFilmOTitle);
            viewHolder.tvFilmYear       = (TextView) convertView.findViewById(R.id.tvLayLVFilmYear);
            viewHolder.tvFilmRanking    = (TextView) convertView.findViewById(R.id.tvLayLVFilmRanking);
            viewHolder.tvFilmFSK        = (TextView) convertView.findViewById(R.id.tvLayLVFilmFSK);
            viewHolder.tvFilmDuration   = (TextView) convertView.findViewById(R.id.tvLayLVFilmDuration);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DTFilmItem eintrag = listFilmItems.get(position);

        // TODO: Bild einfügen

        viewHolder.tvFilmTitle.setText(eintrag.getsFilmTitle());
        viewHolder.tvFilmSubtitle.setText(eintrag.getsFilmSubtitle());
        viewHolder.tvFilmOTitle.setText(eintrag.getsFilmOTitle());
        viewHolder.tvFilmYear.setText(eintrag.getIntFilmPubYear() < 0 ? "" : "" + eintrag.getIntFilmPubYear());
        viewHolder.tvFilmRanking.setText(eintrag.getfFilmRanking() < 0 ? "": "" + eintrag.getfFilmRanking());
        viewHolder.tvFilmFSK.setText(eintrag.getiFilmFSK() < 0 ? "":"Ab " + eintrag.getiFilmFSK() + " J.");
        viewHolder.tvFilmDuration.setText(eintrag.getiFilmDuration() < 0 ? "":"" + eintrag.getiFilmDuration() + " min");

        viewHolder.ivFilm.setImageBitmap(createFoto(eintrag.getsFilmImage()));

        return convertView;
    }

    static class ViewHolder {
        String imgFilmPath;
        ImageView ivFilm;
        TextView tvFilmTitle;
        TextView tvFilmSubtitle;
        TextView tvFilmOTitle;
        TextView tvFilmYear;
        TextView tvFilmRanking;
        TextView tvFilmFSK;
        TextView tvFilmDuration;
    }

    private Bitmap createFoto(String uri){
        Bitmap btm = null;

        if(uri.compareTo("@drawable/cover") == 0 ||uri.isEmpty()){
            try {
                btm = BitmapFactory.decodeResource(this.ctx.getResources(), R.drawable.cover);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            if (android.os.Build.VERSION.SDK_INT < 19){
                try {
                    btm = MediaStore.Images.Media.getBitmap(this.ctx.getContentResolver(), Uri.parse(uri));
                }
                catch (FileNotFoundException e) { e.printStackTrace(); }
                catch (IOException e) { e.printStackTrace(); }
            }
            else {
                try {
                    this.ctx.getContentResolver().notifyChange(Uri.parse(uri), null);
                    ContentResolver cr = this.ctx.getContentResolver();
                    btm = android.provider.MediaStore.Images.Media.getBitmap(cr, Uri.parse(uri));
                }
                catch (FileNotFoundException e) { e.printStackTrace();  }
                catch (IOException e) { e.printStackTrace(); }
            }
        }
        return Bitmap.createScaledBitmap(btm, 160, 226, false);
    }
}

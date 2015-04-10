package de.anisma.www.myvideomanager;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
 * Created by Alfa on 01.04.2015.
 */
public class ActorListAdapter extends ArrayAdapter<DTActor>{

    //MActActors ctx;
    Context ctx;
    List<DTActor> lsActors;

   // public ActorListAdapter(MActActors context, int layoutid, List objects) {
    public ActorListAdapter(Context context, int layoutid, List objects) {
        super(context, layoutid, objects);
        this.ctx = context;
        this.lsActors = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_actors, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivPortrait   = (ImageView) convertView.findViewById(R.id.ivActorFoto);
            viewHolder.tvFirstname  = (TextView) convertView.findViewById(R.id.tvActorFirstName);
            viewHolder.tvLastname   = (TextView) convertView.findViewById(R.id.tvActorLastName);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DTActor actor = lsActors.get(position);

        viewHolder.tvFirstname.setText(actor.getsActorFirstName());
        viewHolder.tvLastname.setText(actor.getsActorLastName());
        viewHolder.ivPortrait.setImageBitmap(createFoto(actor.getsImage()));

        return convertView;
    }

    static class ViewHolder {
        ImageView ivPortrait;
        TextView tvFirstname;
        TextView tvLastname;
    }

    private Bitmap createFoto(String uri){
        Bitmap btm = null;

        if(uri.compareTo("@mipmap/ic_actor") == 0 ||uri.isEmpty()){
            try {
                btm = BitmapFactory.decodeResource(this.ctx.getResources(), R.mipmap.ic_actor);
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
        return Bitmap.createScaledBitmap(btm, 70, 80, false);
    }

}

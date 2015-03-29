package de.anisma.www.myvideomanager;

import android.content.Context;
import android.graphics.Picture;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        viewHolder.tvFilmYear.setText(""+eintrag.getIntFilmPubYear());
        viewHolder.tvFilmRanking.setText(""+eintrag.getiFilmRanking());

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
    }


}

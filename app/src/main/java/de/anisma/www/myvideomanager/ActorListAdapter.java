package de.anisma.www.myvideomanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alfa on 01.04.2015.
 */
public class ActorListAdapter extends ArrayAdapter<DTActor>{

    MActActors ctx;
    List<DTActor> lsActors;

    public ActorListAdapter(MActActors context, int layoutid, List objects) {
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

            //viewHolder.ivPortrait = ...
            viewHolder.tvFirstname  = (TextView) convertView.findViewById(R.id.tvActorFirstName);
            viewHolder.tvLastname   = (TextView) convertView.findViewById(R.id.tvActorLastName);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DTActor actor = lsActors.get(position);
        //viewHolder.ivPortrait.set..
        viewHolder.tvFirstname.setText(actor.getsActorFirstName());
        viewHolder.tvLastname.setText(actor.getsActorLastName());

        return convertView;
    }

    static class ViewHolder {
        ImageView ivPortrait;
        TextView tvFirstname;
        TextView tvLastname;

    }


}

package com.example.renaissance.epros;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Renaissance on 1/18/2017.
 */

public class TemanListAdapter extends BaseAdapter {
    private final Activity activity;
    private final List<Teman> temanItems;
    private LayoutInflater inflater;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public TemanListAdapter(Activity activity, List<Teman> temanItems) {
        this.activity = activity;
        this.temanItems = temanItems;
    }


    @Override
    public int getCount() {
        return temanItems.size();
    }

    @Override
    public Object getItem(int position) {
        return temanItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, null);
        }

        if (imageLoader == null) {
            imageLoader = AppController.getInstance().getImageLoader();
        }

        NetworkImageView thumbnail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView namateman = (TextView) convertView.findViewById(R.id.namateman);
        TextView statusteman = (TextView) convertView.findViewById(R.id.statusteman);
        Teman t = temanItems.get(position);

        thumbnail.setImageUrl(t.getUrlFoto(), imageLoader);

        namateman.setText(t.getNamateman());

        statusteman.setText(t.getStatusteman());

        return convertView;
    }
}

package com.arunabraham.solsticecontactsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arunabraham.solsticecontactsapp.R;
import com.arunabraham.solsticecontactsapp.Tuple;

import java.util.ArrayList;

public class MyContactInfoAdapter extends ArrayAdapter<Tuple<String, String>> {

    ArrayList<Tuple<String, String>> detailList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyContactInfoAdapter(Context context, ArrayList<Tuple<String, String>> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        detailList = objects;
    }

    @Override
    public Tuple<String, String> getItem(int position) {
        return detailList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view2, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Tuple<String, String> item = getItem(position);
        vh.textViewName.setText(item.getX());
        vh.textViewCompany.setText(item.getY());
        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView textViewName;
        public final TextView textViewCompany;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewName, TextView textViewCompany) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.textViewName = textViewName;
            this.textViewCompany = textViewCompany;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewCompany = (TextView) rootView.findViewById(R.id.textViewCompany);
            return new ViewHolder(rootView, imageView, textViewName, textViewCompany);
        }
    }
}

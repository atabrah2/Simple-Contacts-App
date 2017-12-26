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
import com.arunabraham.solsticecontactsapp.model.Contact;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MyContactAdapter extends ArrayAdapter<Contact> {

    ArrayList<Contact> contactList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyContactAdapter(Context context, ArrayList<Contact> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        contactList = objects;
    }

    @Override
    public Contact getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Contact item = getItem(position);
        if (item.getIsFavorite() == true) {
            int unicode = 0x2B50;
            String star = getEmojiByUnicode(unicode);
            String display = star + item.getName();
            vh.textViewName.setText(display);
        }
        else {
            vh.textViewName.setText(item.getName());
        }
        vh.textViewCompany.setText(item.getCompanyName());
        Picasso.with(context).load(item.getSmallImageURL()).placeholder(R.drawable.user_icon_small).error(R.drawable.user_icon_small).into(vh.imageView);

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
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}

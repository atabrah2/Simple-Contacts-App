package com.arunabraham.solsticecontactsapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arunabraham.solsticecontactsapp.R;
import com.arunabraham.solsticecontactsapp.Tuple;
import com.arunabraham.solsticecontactsapp.adapter.MyContactInfoAdapter;
import com.arunabraham.solsticecontactsapp.model.Address;
import com.arunabraham.solsticecontactsapp.model.Contact;
import com.arunabraham.solsticecontactsapp.model.Phone;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Arun on 12/23/2017.
 */

public class ContactDetail extends AppCompatActivity {
    private String name;
    private String id;
    private String companyName;
    private Boolean isFavorite;
    private String largeImageURL;
    private String emailAddress;
    private String birthdate;
    private Phone phone = new Phone();
    private String home = null;
    private String mobile = null;
    private String work = null;
    private Address address = new Address();
    private String fullAddress;
    private ListView listView3;
    private MyContactInfoAdapter adapter;
    private ArrayList<Tuple<String, String>> detail = new ArrayList<Tuple<String, String>>();
    private String x;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        contact = (Contact) getIntent().getExtras().getSerializable("contact_data");;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("updated_contact", contact);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        final Button imgButton = (Button) findViewById(R.id.imageButtonSelector);
        if (contact.getIsFavorite() == true) {
            imgButton.setSelected(true);
        }
        else {
            imgButton.setSelected(false);
        }
        imgButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imgButton.setSelected(!imgButton.isSelected());
                contact.setIsFavorite(!contact.getIsFavorite());
            }
        });
        TextView text1 = (TextView) findViewById(R.id.textView2);
        TextView text2 = (TextView) findViewById(R.id.textView5);
        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        name = contact.getName();
        id = contact.getId();
        companyName = contact.getCompanyName();
        isFavorite = contact.getIsFavorite();
        largeImageURL = contact.getLargeImageURL();
        emailAddress = contact.getEmailAddress();
        birthdate = contact.getBirthdate();
        phone = contact.getPhone();
        home = phone.getHome();
        mobile = phone.getMobile();
        work = phone.getWork();
        address = contact.getAddress();
        fullAddress = address.getStreet() + "\n" + address.getCity() + ", " + address.getState() + " " + address.getZipCode() + ", " + address.getCountry();
        if (home != null) {
            x = "Phone (Home):";
            Tuple<String, String> h = new Tuple(x, home);
            detail.add(h);
        }
        if (mobile != null) {
            x = "Phone (Mobile):";
            Tuple<String, String> m = new Tuple(x, mobile);
            detail.add(m);
        }
        if (work != null) {
            x = "Phone (Work):";
            Tuple<String, String> w = new Tuple(x, work);
            detail.add(w);
        }
        x = "Address:";
        Tuple<String, String> a = new Tuple(x, fullAddress);
        detail.add(a);
        x = "Birthdate:";
        Tuple<String, String> b = new Tuple(x, birthdate);
        detail.add(b);
        x = "Email:";
        Tuple<String, String> e = new Tuple(x, emailAddress);
        detail.add(e);
        text1.setText(name);
        text2.setText(companyName);
        Picasso.with(this).load(largeImageURL).placeholder(R.drawable.user_icon_large).error(R.drawable.user_icon_large).into(imageView);
        listView3 = (ListView) findViewById(R.id.listView3);
        adapter = new MyContactInfoAdapter(ContactDetail.this, detail);
        listView3.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("updated_contact", contact);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

package com.arunabraham.solsticecontactsapp.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arunabraham.solsticecontactsapp.R;
import com.arunabraham.solsticecontactsapp.adapter.MyContactAdapter;
import com.arunabraham.solsticecontactsapp.model.Contact;
import com.arunabraham.solsticecontactsapp.retrofit.api.ApiService;
import com.arunabraham.solsticecontactsapp.retrofit.api.RetroClient;
import com.arunabraham.solsticecontactsapp.utils.InternetConnection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    /**
     * Views
     */
    private ListView listView;
    private ListView listView2;

    private View parentView;

    private List<Contact> contactList;
    private ArrayList<Contact> favorites = new ArrayList<Contact>();
    private ArrayList<Contact> notfavorites = new ArrayList<Contact>();
    private MyContactAdapter adapter;
    private MyContactAdapter adapter2;
    private Contact contact = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        contact = null;
        if (this.getIntent().getExtras() != null) {
            contact = (Contact) getIntent().getExtras().getSerializable("updated_contact");
        }
        /**
         * Array List for Binding Data from JSON to this List
         */
        contactList = new List<Contact>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Contact> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(Contact contact) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Contact> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends Contact> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Contact get(int i) {
                return null;
            }

            @Override
            public Contact set(int i, Contact contact) {
                return null;
            }

            @Override
            public void add(int i, Contact contact) {

            }

            @Override
            public Contact remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<Contact> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Contact> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<Contact> subList(int i, int i1) {
                return null;
            }
        };

        parentView = findViewById(R.id.parentLayout);

        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                passObject(favorites.get(position));
            }
        });
        listView2 = (ListView) findViewById(R.id.listView2);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                passObject(notfavorites.get(position));
            }
        });

        /**
         * Checking Internet Connection
         */
        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle(getString(R.string.string_getting_json_title));
            dialog.setMessage(getString(R.string.string_getting_json_message));
            dialog.show();

            //Creating an object of our api interface
            ApiService api = RetroClient.getApiService();

            /**
             * Calling JSON
             */
            Call<List<Contact>> call = api.getMyJSON();

            /**
             * Enqueue Callback will be call when get response...
             */
            call.enqueue(new Callback<List<Contact>>() {
                @Override
                public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                    //Dismiss Dialog
                    dialog.dismiss();

                    if(response.isSuccessful()) {
                        /**
                         * Got Successfully
                         */
                        contactList = response.body();

                        /**
                         * Binding that List to Adapter
                         */
                        for (int i = 0; i < contactList.size(); i++) {
                            if ((contact != null) && (contactList.get(i).getId().equals(contact.getId()))){
                                contactList.get(i).setIsFavorite(contact.getIsFavorite());
                            }
                            if (contactList.get(i).getIsFavorite() == true) {
                                favorites.add(contactList.get(i));
                            } else {
                                notfavorites.add(contactList.get(i));
                            }
                        }
                        Collections.sort(favorites, new CustomComparator());
                        Collections.sort(notfavorites, new CustomComparator());

                        adapter = new MyContactAdapter(MainActivity.this, favorites);
                        listView.setAdapter(adapter);
                        adapter2 = new MyContactAdapter(MainActivity.this, notfavorites);
                        listView2.setAdapter(adapter2);

                    } else {
                        Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Contact>> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            Snackbar.make(parentView, R.string.string_internet_connection_not_available, Snackbar.LENGTH_LONG).show();
        }
    }
    private void passObject(Contact contact) {
        Intent intent = new Intent(this, ContactDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("contact_data", contact);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public class CustomComparator implements Comparator<Contact> {
        @Override
        public int compare(Contact o1, Contact o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
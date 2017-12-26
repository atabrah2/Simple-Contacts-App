package com.arunabraham.solsticecontactsapp.retrofit.api;

import com.arunabraham.solsticecontactsapp.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Pratik Butani.
 */
public interface ApiService {

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @GET("/technical-challenge/v3/contacts.json")
    Call<List<Contact>> getMyJSON();
}

package com.android.stephen.mtgpos.utils;

import android.content.ContentValues;
import android.content.Context;

import com.android.stephen.mtgpos.callback.VolleyCallback;

/**
 * Created by Stephen Uy on 3/3/2017.
 */

public class CustomerAPI {

    Context context;
    public CustomerAPI(Context c){
        this.context = c;
    }

    public void generateCustomerID(VolleyCallback callback, String custIDTemp){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.CUSTOMER_ID_TEMP.getValue(),custIDTemp);
        con.wGet(context, callback, API.CUSTOMER, Task.GENERATE_CUSTOMER_ID);
    }

    public void getUpline(VolleyCallback callback, String customerID){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.CUSTOMER_ID_UP.getValue(),customerID);
        con.wGet(context, callback, API.CUSTOMER, Task.GET_UPLINE);
    }

    public void saveNewCustomer(VolleyCallback callback, String customerEN, String customerPictureEN, String customerUplineEN){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.CUSTOMER_EN.getValue(),customerEN);
        contentValues.put(Parameters.CUSTOMER_PICTURE_EN.getValue(),customerPictureEN);
        contentValues.put(Parameters.CUSTOMER_UPLINE_EN.getValue(),customerUplineEN);
        con.wGet(context, callback, API.CUSTOMER, Task.SAVE_NEW_CUSTOMER);
    }
}

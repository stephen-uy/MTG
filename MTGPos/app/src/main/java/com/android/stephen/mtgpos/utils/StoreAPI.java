package com.android.stephen.mtgpos.utils;

import android.content.ContentValues;
import android.content.Context;

import com.android.stephen.mtgpos.callback.VolleyCallback;

/**
 * Created by Stephen Uy on 3/3/2017.
 */

public class StoreAPI{

    Context context;
    public StoreAPI(Context c){
        this.context = c;
    }

    public void getStoreDetails(VolleyCallback callback, String username, String password, String macaddress){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.USERNAME.getValue(),username);
        contentValues.put(Parameters.PASSWORD.getValue(),password);
        contentValues.put(Parameters.MAC_ADDRESS.getValue(),macaddress);
        con.wGet(context, callback, API.STORE, Task.STORE_DETAILS, contentValues);
    }

    public void getStoreItems(VolleyCallback callback, String storeID){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.STORE_ID.getValue(),storeID);
        con.wGet(context, callback, API.STORE, Task.ITEMS, contentValues);
    }

    public void getStoreProduct(VolleyCallback callback, String storeID){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.STORE_ID.getValue(),storeID);
        con.wGet(context, callback, API.STORE, Task.PRODUCTS, contentValues);
    }

    public void getStoreProductItem(VolleyCallback callback, String storeID){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.STORE_ID.getValue(),storeID);
        con.wGet(context, callback, API.STORE, Task.PRODUCT_ITEM, contentValues);
    }

    public void getStoreStock(VolleyCallback callback, String storeID){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.STORE_ID.getValue(),storeID);
        con.wGet(context, callback, API.STORE, Task.STOCKS, contentValues);
    }

    public void getUPointsHistory(VolleyCallback callback, String storeID, String from, String to){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.STORE_ID.getValue(),storeID);
        contentValues.put(Parameters.FROM.getValue(),from);
        contentValues.put(Parameters.TO.getValue(),to);
        con.wGet(context, callback, API.STORE, Task.UPOINTS_HISTORY, contentValues);
    }

    public void getStoreUPoints(VolleyCallback callback, String storeID){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.STORE_ID.getValue(),storeID);
        con.wGet(context, callback, API.STORE, Task.STORE_UPOINTS, contentValues);
    }

    public void getStocksList(VolleyCallback callback, String storeID, String itemID){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.STORE_ID.getValue(),storeID);
        contentValues.put(Parameters.ITEM_ID.getValue(),itemID);
        con.wGet(context, callback, API.STORE, Task.STOCKS_LIST, contentValues);
    }

    public void getProductList(VolleyCallback callback, String storeID, String productID, String categoryID){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.STORE_ID.getValue(),storeID);
        contentValues.put(Parameters.PRODUCT_ID.getValue(),productID);
        contentValues.put(Parameters.CATEGORY_ID.getValue(),categoryID);
        con.wGet(context, callback, API.STORE, Task.PRODUCT_LIST, contentValues);
    }

    public void getStoreStockList(VolleyCallback callback, String storeID){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.STORE_ID.getValue(),storeID);
        con.wGet(context, callback, API.STORE, Task.STORE_STOCKS_LIST, contentValues);
    }

    public void saveNewStock(VolleyCallback callback, String username, String stockRegEN){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.USERNAME.getValue(),username);
        contentValues.put(Parameters.STOCK_REG_EN.getValue(),stockRegEN);
        con.wGet(context, callback, API.STORE, Task.SAVE_NEW_STOCK, contentValues);
    }
}

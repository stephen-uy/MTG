package com.android.stephen.mtgpos.utils;

import android.content.ContentValues;
import android.content.Context;

import com.android.stephen.mtgpos.callback.VolleyCallback;

/**
 * Created by Stephen Uy on 3/3/2017.
 */

public class ItemAPI {

    Context context;
    public ItemAPI(Context c){
        this.context = c;
    }

    public void getItemTypeLookUp(VolleyCallback callback, String storeID){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.STORE_ID.getValue(),storeID);
        con.wGet(context, callback, API.ITEM, Task.ITEM_TYPE, true);
    }
}

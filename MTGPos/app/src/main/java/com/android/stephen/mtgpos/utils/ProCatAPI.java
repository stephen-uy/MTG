package com.android.stephen.mtgpos.utils;

import android.content.Context;

import com.android.stephen.mtgpos.callback.VolleyCallback;

/**
 * Created by Stephen Uy on 3/3/2017.
 */

public class ProCatAPI {

    Context context;
    public ProCatAPI(Context c){
        this.context = c;
    }

    public void getProductCategoryList(VolleyCallback callback){
        HttpVolleyConnector con = new HttpVolleyConnector();
        con.wGet(context, callback, API.PROCAT, Task.PRODUCT_CATEGORY_LIST);
    }
}

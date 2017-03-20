package com.android.stephen.mtgpos.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.callback.VolleyCallback;
import com.android.stephen.mtgpos.model.LookUpModel;
import com.android.stephen.mtgpos.model.StoreModel;
import com.android.stephen.mtgpos.utils.APIHelper;
import com.android.stephen.mtgpos.utils.Helper;
import com.android.stephen.mtgpos.utils.ItemAPI;
import com.android.stephen.mtgpos.utils.ProCatAPI;
import com.android.stephen.mtgpos.utils.StoreAPI;
import com.android.stephen.mtgpos.utils.Task;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class SplashScreen extends AppCompatActivity implements VolleyCallback{

    private String storeID;
    private String username;
    private String level;
    private String password;
    private String macaddress;
    private ProgressDialog progressDialog;
    private StoreAPI storeAPI;
    private ItemAPI itemAPI;
    private ProCatAPI proCatAPI;
    LinkedList<LookUpModel> lookUpModelLinkedList;
    LinkedList<StoreModel> storeModelLinkedList;
    private int rowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setUpData();
        showProgress(true);
        callAPI(Task.ITEMS);
    }

    private void callAPI(Task task) {
        storeAPI = new StoreAPI(this);
        itemAPI = new ItemAPI(this);
        proCatAPI = new ProCatAPI(this);
        switch (task) {
            case ITEMS:
                storeAPI.getStoreItems(this, storeID);
                break;
            case PRODUCTS:
                storeAPI.getStoreProduct(this, storeID);
                break;
            case PRODUCT_ITEM:
                storeAPI.getStoreProductItem(this, storeID);
                break;
            case STOCKS:
                storeAPI.getStoreStock(this, storeID);
                break;
            case ITEM_TYPE:
                itemAPI.getItemTypeLookUp(this, storeID);
                break;
            case PRODUCT_CATEGORY_LIST:
                proCatAPI.getProductCategoryList(this);
                break;
        }
    }

    private void setUpData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("user");
            storeID = bundle.getString("storeID");
            level = bundle.getString("level");
            password = bundle.getString("password");
            macaddress = bundle.getString("macaddress");
        }
    }

    private void showProgress(boolean show){
        progressDialog = Helper.buildProgressSpinnerDialog(this, getString(R.string.loading));
        if (show)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }

    private void goToNext(){
        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        i.putExtra("user", username);
        i.putExtra("password", password);
        i.putExtra("level", level);
        i.putExtra("storeID", storeID);
        i.putExtra("macaddress", Helper.getMacAddress(this));
        startActivity(i);
        finish();
    }

    @Override
    public void onResponseReady(Task task, LinkedHashMap<String, String> response) {

    }

    @Override
    public void onResponseReady(Task task, LinkedList<LinkedHashMap<String, String>> hashMaps) {
        switch (task){
            case ITEMS:
                parseItems(hashMaps);
                break;
            case PRODUCTS:
                parseProducts(hashMaps);
                break;
            case PRODUCT_ITEM:
                parseProductItem(hashMaps);
                break;
            case STOCKS:
                parseStocks(hashMaps);
                break;
            case ITEM_TYPE:
                parseItemType(hashMaps);
                break;
            case PRODUCT_CATEGORY_LIST:
                parseProCat(hashMaps);
                break;
        }
    }

    private void parseProCat(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        lookUpModelLinkedList = APIHelper.setUpProCat(hashMaps);
        if (lookUpModelLinkedList.size() > 0) {
            rowCount = APIHelper.insertProCatData(this, lookUpModelLinkedList);
            if (rowCount == lookUpModelLinkedList.size())
                goToNext();
            else
                showProgress(false);
        } else {
            goToNext();
        }
    }

    private void parseItemType(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        lookUpModelLinkedList = APIHelper.setUpItemType(hashMaps);
        if (lookUpModelLinkedList.size() > 0) {
            rowCount = APIHelper.insertItemTypeData(this, lookUpModelLinkedList);
            if (rowCount == lookUpModelLinkedList.size())
                callAPI(Task.PRODUCT_CATEGORY_LIST);
            else
                showProgress(false);
        } else {
            callAPI(Task.PRODUCT_CATEGORY_LIST);
        }
    }

    private void parseStocks(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        storeModelLinkedList = APIHelper.setUpStoreStocksData(hashMaps);
        if (storeModelLinkedList.size() > 0) {
            rowCount = APIHelper.insertStoreStocksData(this, storeModelLinkedList);
            if (rowCount == storeModelLinkedList.size())
                callAPI(Task.ITEM_TYPE);
            else
                showProgress(false);
        }
    }

    private void parseProductItem(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        lookUpModelLinkedList = APIHelper.setUpProductItemData(hashMaps);
        if (lookUpModelLinkedList.size() > 0) {
            rowCount = APIHelper.insertProductItemData(this, lookUpModelLinkedList);
            if (rowCount == lookUpModelLinkedList.size())
                callAPI(Task.STOCKS);
            else
                showProgress(false);
        }
    }

    private void parseProducts(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        lookUpModelLinkedList = APIHelper.setUpProductsData(hashMaps);
        if (lookUpModelLinkedList.size() > 0) {
            rowCount = APIHelper.insertProductData(this, lookUpModelLinkedList);
            if (rowCount == lookUpModelLinkedList.size())
                callAPI(Task.PRODUCT_ITEM);
            else
                showProgress(false);
        }
    }

    private void parseItems(LinkedList<LinkedHashMap<String, String>> hashMaps){
        lookUpModelLinkedList = APIHelper.setUpItemsData(hashMaps);
        if (lookUpModelLinkedList.size() > 0) {
            rowCount = APIHelper.insertItemData(this, lookUpModelLinkedList);
            if (rowCount == lookUpModelLinkedList.size())
                callAPI(Task.PRODUCTS);
            else
                showProgress(false);
        }
    }
}

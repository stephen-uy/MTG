package com.android.stephen.mtgpos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.stephen.mtgpos.model.LookUpModel;

import java.util.LinkedList;

/**
 * Created by Stephen Uy on 2/6/2017.
 */

public class LookUpHandler extends SQLiteDBHandler {
    private static LookUpHandler instance;

    public LookUpHandler(Context context) {
        super(context);
    }

    public static LookUpHandler getInstance(Context context){
        if(instance == null) {
            instance = new LookUpHandler(context);
        }
        return instance;
    }

    public void addItem(LookUpModel lookUpModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumItem.StoreID.toString(), lookUpModel.getStoreID());
        values.put(DBModels.enumItem.ItemID.toString(), lookUpModel.getItemID());
        values.put(DBModels.enumItem.ItemDesc.toString(), lookUpModel.getItemDesc());
        values.put(DBModels.enumItem.QtyPerItemType.toString(), lookUpModel.getQtyPerItemType());
        values.put(DBModels.enumItem.ItemType.toString(), lookUpModel.getItemType());
        values.put(DBModels.enumItem.IsActive.toString(), lookUpModel.getIsActive());
        values.put(DBModels.enumItem.DateLastUpdated.toString(), lookUpModel.getDateLastUpdated());
        // Inserting Row
        db.insert(DBModels.enumTables.StoreItem.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addItemTypeLookUp(LookUpModel lookUpModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumItemTypeLookUp.Type.toString(), lookUpModel.getType());
        values.put(DBModels.enumItemTypeLookUp.Description.toString(), lookUpModel.getDescription());
        values.put(DBModels.enumItemTypeLookUp.IsActive.toString(), lookUpModel.getIsActive());
        // Inserting Row
        db.insert(DBModels.enumTables.ItemTypeLookUp.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addProduct(LookUpModel lookUpModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumProduct.StoreID.toString(), lookUpModel.getStoreID());
        values.put(DBModels.enumProduct.ProductID.toString(), lookUpModel.getProductID());
        values.put(DBModels.enumProduct.ProductDesc.toString(), lookUpModel.getProductDesc());
        values.put(DBModels.enumProduct.SellingPrice.toString(), lookUpModel.getSellingPrice());
        values.put(DBModels.enumProduct.RebatePoints.toString(), lookUpModel.getRebatePoints());
        values.put(DBModels.enumProduct.SharePoints.toString(), lookUpModel.getSharePoints());
        values.put(DBModels.enumProduct.Picture.toString(), lookUpModel.getPicture());
        values.put(DBModels.enumProduct.NoOfServing.toString(), lookUpModel.getNoOfServing());
        values.put(DBModels.enumProduct.IsActive.toString(), lookUpModel.getIsActive());
        // Inserting Row
        db.insert(DBModels.enumTables.Product.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addProductItem(LookUpModel lookUpModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumProductItem.StoreID.toString(), lookUpModel.getStoreID());
        values.put(DBModels.enumProductItem.ItemID.toString(), lookUpModel.getItemID());
        values.put(DBModels.enumProductItem.ProductID.toString(), lookUpModel.getProductID());
        values.put(DBModels.enumProductItem.QtyPerServe.toString(), lookUpModel.getQtyPerServe());
        values.put(DBModels.enumProductItem.IsActive.toString(), lookUpModel.getIsActive());
        // Inserting Row
        db.insert(DBModels.enumTables.ProductItem.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addProductCategory(LookUpModel lookUpModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumProductCategory.CatID.toString(), lookUpModel.getStoreID());
        values.put(DBModels.enumProductCategory.CatDesc.toString(), lookUpModel.getItemID());
        values.put(DBModels.enumProductCategory.IsActive.toString(), lookUpModel.getIsActive());
        // Inserting Row
        db.insert(DBModels.enumTables.ProductCategory.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public LinkedList<LookUpModel> getAllItems() {
        LinkedList<LookUpModel> lookUpModelList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StoreItem.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LookUpModel lookUpModel = new LookUpModel();
                lookUpModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                lookUpModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.StoreID.toString())));
                lookUpModel.setItemID(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.ItemID.toString())));
                lookUpModel.setItemDesc(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.ItemDesc.toString())));
                lookUpModel.setQtyPerItemType(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.QtyPerItemType.toString())));
                lookUpModel.setItemType(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.ItemType.toString())));
                lookUpModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.IsActive.toString())));
                lookUpModel.setDateLastUpdated(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.DateLastUpdated.toString())));
                // Adding contact to list
                lookUpModelList.add(lookUpModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return lookUpModelList;
    }

    public LinkedList<LookUpModel> getAllItemsWithStocks() {
        LinkedList<LookUpModel> lookUpModelList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM "+ DBModels.enumTables.StoreItem + " a INNER JOIN "+
                DBModels.enumTables.Stocks + " b ON a."+ DBModels.enumItem.ItemID + "=b." +
                DBModels.enumItem.ItemID + " WHERE a." + DBModels.enumStocks.ItemID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LookUpModel lookUpModel = new LookUpModel();
                lookUpModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                lookUpModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.StoreID.toString())));
                lookUpModel.setItemID(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.ItemID.toString())));
                lookUpModel.setItemDesc(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.ItemDesc.toString())));
                lookUpModel.setQtyPerItemType(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.QtyPerItemType.toString())));
                lookUpModel.setItemType(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.ItemType.toString())));
                lookUpModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.IsActive.toString())));
                lookUpModel.setDateLastUpdated(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.DateLastUpdated.toString())));
                lookUpModel.setQuantity(cursor.getString(cursor.getColumnIndex(DBModels.enumStocks.Quantity.toString())));
                // Adding contact to list
                lookUpModelList.add(lookUpModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return lookUpModelList;
    }

    public LinkedList<LookUpModel> getAllItemTypeLookUp() {
        LinkedList<LookUpModel> lookUpModelList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.ItemTypeLookUp.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LookUpModel lookUpModel = new LookUpModel();
                lookUpModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                lookUpModel.setType(cursor.getString(cursor.getColumnIndex(DBModels.enumItemTypeLookUp.Type.toString())));
                lookUpModel.setDescription(cursor.getString(cursor.getColumnIndex(DBModels.enumItemTypeLookUp.Description.toString())));
                lookUpModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumItemTypeLookUp.IsActive.toString())));
                // Adding contact to list
                lookUpModelList.add(lookUpModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return lookUpModelList;
    }

    public LinkedList<LookUpModel> getAllProducts() {
        LinkedList<LookUpModel> lookUpModelList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.Product.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LookUpModel lookUpModel = new LookUpModel();
                lookUpModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                lookUpModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumProduct.StoreID.toString())));
                lookUpModel.setProductID(cursor.getString(cursor.getColumnIndex(DBModels.enumProduct.ProductID.toString())));
                lookUpModel.setProductDesc(cursor.getString(cursor.getColumnIndex(DBModels.enumProduct.ProductDesc.toString())));
                lookUpModel.setSellingPrice(cursor.getString(cursor.getColumnIndex(DBModels.enumProduct.SellingPrice.toString())));
                lookUpModel.setRebatePoints(cursor.getString(cursor.getColumnIndex(DBModels.enumProduct.RebatePoints.toString())));
                lookUpModel.setSharePoints(cursor.getString(cursor.getColumnIndex(DBModels.enumProduct.SharePoints.toString())));
                lookUpModel.setPicture(cursor.getString(cursor.getColumnIndex(DBModels.enumProduct.Picture.toString())));
                lookUpModel.setNoOfServing(cursor.getString(cursor.getColumnIndex(DBModels.enumProduct.NoOfServing.toString())));
                lookUpModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumProduct.IsActive.toString())));
                // Adding contact to list
                lookUpModelList.add(lookUpModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return lookUpModelList;
    }

    public LinkedList<LookUpModel> getAllProductItems() {
        LinkedList<LookUpModel> lookUpModelList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.ProductItem.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LookUpModel lookUpModel = new LookUpModel();
                lookUpModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                lookUpModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumProductItem.StoreID.toString())));
                lookUpModel.setItemID(cursor.getString(cursor.getColumnIndex(DBModels.enumProductItem.ItemID.toString())));
                lookUpModel.setProductID(cursor.getString(cursor.getColumnIndex(DBModels.enumProductItem.ProductID.toString())));
                lookUpModel.setQtyPerServe(cursor.getString(cursor.getColumnIndex(DBModels.enumProductItem.QtyPerServe.toString())));
                lookUpModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumProductItem.IsActive.toString())));
                // Adding contact to list
                lookUpModelList.add(lookUpModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return lookUpModelList;
    }

    public LinkedList<LookUpModel> getAllProductCategory() {
        LinkedList<LookUpModel> lookUpModelList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.ProductCategory.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LookUpModel lookUpModel = new LookUpModel();
                lookUpModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                lookUpModel.setCatID(cursor.getString(cursor.getColumnIndex(DBModels.enumProductCategory.CatID.toString())));
                lookUpModel.setCatDesc(cursor.getString(cursor.getColumnIndex(DBModels.enumProductCategory.CatDesc.toString())));
                lookUpModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumProductCategory.IsActive.toString())));
                // Adding contact to list
                lookUpModelList.add(lookUpModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return lookUpModelList;
    }

    // Getting table row count
    public int getRowCounts(String table) {
        String countQuery = "SELECT  * FROM " + table;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        Log.d("getRowCounts","" + count);
        return count;
    }

    public LinkedList<LookUpModel> getProductItemDetails(String productID){
        LinkedList<LookUpModel> lookUpModelList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM "+ DBModels.enumTables.ProductItem + " a INNER JOIN "+
                DBModels.enumTables.StoreItem + " b ON a."+ DBModels.enumProductItem.ItemID + "=b." +
                DBModels.enumItem.ItemID + " WHERE a." + DBModels.enumProductItem.ProductID + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(productID)});
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LookUpModel lookUpModel = new LookUpModel();
                lookUpModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                lookUpModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumProductItem.StoreID.toString())));
                lookUpModel.setItemID(cursor.getString(cursor.getColumnIndex(DBModels.enumProductItem.ItemID.toString())));
                lookUpModel.setItemDesc(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.ItemDesc.toString())));
                lookUpModel.setProductID(cursor.getString(cursor.getColumnIndex(DBModels.enumProductItem.ProductID.toString())));
                lookUpModel.setQtyPerServe(cursor.getString(cursor.getColumnIndex(DBModels.enumProductItem.QtyPerServe.toString())));
                lookUpModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumProductItem.IsActive.toString())));
                // Adding contact to list
                lookUpModelList.add(lookUpModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return lookUpModelList;
    }
}

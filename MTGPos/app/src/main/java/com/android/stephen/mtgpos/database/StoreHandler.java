package com.android.stephen.mtgpos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.stephen.mtgpos.model.StoreModel;

import java.util.LinkedList;

/**
 * Created by Stephen Uy on 1/31/2017.
 */

public class StoreHandler extends SQLiteDBHandler {
    private static StoreHandler instance;

    public StoreHandler(Context context) {
        super(context);
    }

    public static StoreHandler getInstance(Context context){
        if(instance == null) {
            instance = new StoreHandler(context);
        }
        return instance;
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new user
    public boolean addStore(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStore.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStore.FranName.toString(), storeModel.getFranchiseeName());
        values.put(DBModels.enumStore.Street.toString(), storeModel.getStreet());
        values.put(DBModels.enumStore.City.toString(), storeModel.getCity());
        values.put(DBModels.enumStore.Municipality.toString(), storeModel.getMunicipality());
        values.put(DBModels.enumStore.Province.toString(), storeModel.getProvince());
        values.put(DBModels.enumStore.ZipCode.toString(), storeModel.getZipCode());
        values.put(DBModels.enumStore.Longitude.toString(), storeModel.getLongitude());
        values.put(DBModels.enumStore.Latitude.toString(), storeModel.getLatitude());
        values.put(DBModels.enumStore.Email.toString(), storeModel.getEmailAddress());
        values.put(DBModels.enumStore.Mobile.toString(), storeModel.getMobileNumber());
        values.put(DBModels.enumStore.Phone.toString(), storeModel.getPhone());
        values.put(DBModels.enumStore.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumStore.IsActive.toString(), storeModel.getIsActive());
        values.put(DBModels.enumStore.IsUploaded.toString(), storeModel.getIsUploaded());

        // Inserting Row
        long result = db.insert(DBModels.enumTables.Store.toString(), null, values);
        Log.d("addStore-result","" + result);
//        db.close(); // Closing database connection
        if (result != -1)
            return true;
        else
            return false;
    }

    public void addStoreUser(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStoreUser.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStoreUser.UserName.toString(), storeModel.getUserName());
        values.put(DBModels.enumStoreUser.Password.toString(), storeModel.getPassword());
        values.put(DBModels.enumStoreUser.Fname.toString(), storeModel.getFirstName());
        values.put(DBModels.enumStoreUser.Mname.toString(), storeModel.getMiddleName());
        values.put(DBModels.enumStoreUser.Lname.toString(), storeModel.getLastName());
        values.put(DBModels.enumStoreUser.Level.toString(), storeModel.getLevel());
        values.put(DBModels.enumStoreUser.RegBy.toString(), storeModel.getRegBy());
        values.put(DBModels.enumStoreUser.RegDate.toString(), storeModel.getRegDate());
        values.put(DBModels.enumStoreUser.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumStoreUser.IsActive.toString(), storeModel.getIsActive());
        values.put(DBModels.enumStoreUser.IsUploaded.toString(), storeModel.getIsUploaded());

        // Inserting Row
        db.insert(DBModels.enumTables.StoreUser.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public int updateStoreUser(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStoreUser.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStoreUser.UserName.toString(), storeModel.getUserName());
        values.put(DBModels.enumStoreUser.Password.toString(), storeModel.getPassword());
        values.put(DBModels.enumStoreUser.Fname.toString(), storeModel.getFirstName());
        values.put(DBModels.enumStoreUser.Mname.toString(), storeModel.getMiddleName());
        values.put(DBModels.enumStoreUser.Lname.toString(), storeModel.getLastName());
        values.put(DBModels.enumStoreUser.Level.toString(), storeModel.getLevel());
        values.put(DBModels.enumStoreUser.RegBy.toString(), storeModel.getRegBy());
        values.put(DBModels.enumStoreUser.RegDate.toString(), storeModel.getRegDate());
        values.put(DBModels.enumStoreUser.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumStoreUser.IsActive.toString(), storeModel.getIsActive());
        values.put(DBModels.enumStoreUser.IsUploaded.toString(), storeModel.getIsUploaded());

//        db.close(); // Closing database connection
        return db.update(DBModels.enumTables.StoreUser.toString(), values, DBModels.recID.toString() + " = ?",
                new String[] { storeModel.getRecID() });
    }

    public void addStoreAccount(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStoreAccount.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStoreAccount.TotalPoints.toString(), storeModel.getTotalPoints());
        values.put(DBModels.enumStoreAccount.RemainingPoints.toString(), storeModel.getRemainingPoints());
        values.put(DBModels.enumStoreAccount.TotalWDPoints.toString(), storeModel.getTotalWDPoints());

        // Inserting Row
        db.insert(DBModels.enumTables.StoreAccount.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addStorePointsHistory(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStorePointsHistory.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStorePointsHistory.PointsRef.toString(), storeModel.getPointsRef());
        values.put(DBModels.enumStorePointsHistory.ModeOfPayment.toString(), storeModel.getModeOfPayment());
        values.put(DBModels.enumStorePointsHistory.Amount.toString(), storeModel.getAmount());
        values.put(DBModels.enumStorePointsHistory.Points.toString(), storeModel.getPoints());
        values.put(DBModels.enumStorePointsHistory.DateDeposited.toString(), storeModel.getDateDeposited());
        values.put(DBModels.enumStorePointsHistory.ReceivedDate.toString(), storeModel.getReceivedDate());
        values.put(DBModels.enumStorePointsHistory.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumStorePointsHistory.IsActive.toString(), storeModel.getIsActive());
        values.put(DBModels.enumStorePointsHistory.IsUploaded.toString(), storeModel.getIsUploaded());

        // Inserting Row
        db.insert(DBModels.enumTables.StorePointsHistory.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addStoreStocksReg(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStoreStocksReg.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStoreStocksReg.StocksRef.toString(), storeModel.getStocksRef());
        values.put(DBModels.enumStoreStocksReg.ItemID.toString(), storeModel.getItemID());
        values.put(DBModels.enumStoreStocksReg.Quantity.toString(), storeModel.getQuantity());
        values.put(DBModels.enumStoreStocksReg.DateReg.toString(), storeModel.getDateReg());
        values.put(DBModels.enumStoreStocksReg.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumStoreStocksReg.IsActive.toString(), storeModel.getIsActive());

        // Inserting Row
        db.insert(DBModels.enumTables.StoreStocksReg.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addStoreStocks(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStoreStocks.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStoreStocks.ItemID.toString(), storeModel.getItemID());
        values.put(DBModels.enumStoreStocks.Quantity.toString(), storeModel.getQuantity());
        values.put(DBModels.enumStoreStocks.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumStoreStocks.IsActive.toString(), storeModel.getIsActive());
        values.put(DBModels.enumStoreStocks.IsUploaded.toString(), storeModel.getIsUploaded());

        // Inserting Row
        db.insert(DBModels.enumTables.StoreStocks.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addStoreLogs(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStoreLogs.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStoreLogs.UserName.toString(), storeModel.getUserName());
        values.put(DBModels.enumStoreLogs.AuditDate.toString(), storeModel.getAuditDate());
        values.put(DBModels.enumStoreLogs.AuditDesc.toString(), storeModel.getAuditDesc());
        values.put(DBModels.enumStoreLogs.StocksRef.toString(), storeModel.getStocksRef());
        values.put(DBModels.enumStoreLogs.PurRef.toString(), storeModel.getPurchaseRef());
        values.put(DBModels.enumStoreLogs.CustID.toString(), storeModel.getCustomerID());
        values.put(DBModels.enumStoreLogs.IsUploaded.toString(), storeModel.getIsUploaded());

        // Inserting Row
        db.insert(DBModels.enumTables.StoreLogs.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addStorePurchased(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStorePurchased.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStorePurchased.PurRef.toString(), storeModel.getPurchaseRef());
        values.put(DBModels.enumStorePurchased.PurDate.toString(), storeModel.getPurchaseDate());
        values.put(DBModels.enumStorePurchased.TotalAmt.toString(), storeModel.getTotalAmt());
        values.put(DBModels.enumStorePurchased.TotalQty.toString(), storeModel.getTotalQty());
        values.put(DBModels.enumStorePurchased.RebatePoints.toString(), storeModel.getRebatePoints());
        values.put(DBModels.enumStorePurchased.SharePoints.toString(), storeModel.getSharePoints());
        values.put(DBModels.enumStorePurchased.RegBy.toString(), storeModel.getRegBy());
        values.put(DBModels.enumStorePurchased.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumStorePurchased.IsActive.toString(), storeModel.getIsActive());
        values.put(DBModels.enumStorePurchased.IsUploaded.toString(), storeModel.getIsUploaded());

        // Inserting Row
        db.insert(DBModels.enumTables.StorePurchased.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addStorePurchasedDetails(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStorePurchasedDetails.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStorePurchasedDetails.PurRef.toString(), storeModel.getPurchaseRef());
        values.put(DBModels.enumStorePurchasedDetails.ProductID.toString(), storeModel.getProductID());
        values.put(DBModels.enumStorePurchasedDetails.Quantity.toString(), storeModel.getQuantity());
        values.put(DBModels.enumStorePurchasedDetails.IsActive.toString(), storeModel.getIsActive());
        values.put(DBModels.enumStorePurchasedDetails.IsUploaded.toString(), storeModel.getIsUploaded());

        // Inserting Row
        db.insert(DBModels.enumTables.StorePurchasedDetails.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public LinkedList<StoreModel> getStore() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.Store.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.StoreID.toString())));
                storeModel.setFranchiseeName(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.FranName.toString())));
                storeModel.setStreet(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Street.toString())));
                storeModel.setCity(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.City.toString())));
                storeModel.setMunicipality(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Municipality.toString())));
                storeModel.setProvince(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Province.toString())));
                storeModel.setZipCode(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.ZipCode.toString())));
                storeModel.setLongitude(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Longitude.toString())));
                storeModel.setLatitude(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Latitude.toString())));
                storeModel.setEmailAddress(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Email.toString())));
                storeModel.setMobileNumber(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Mobile.toString())));
                storeModel.setPhone(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Phone.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.IsActive.toString())));
                storeModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.IsUploaded.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public StoreModel getLoginDetailsByUsernamePassword(String username, String password) {
        StoreModel storeModel = new StoreModel();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StoreUser.toString() +
                " WHERE " + DBModels.enumStoreUser.UserName.toString() + " = '" + username + "' AND "
                + DBModels.enumStoreUser.Password + " = '" + password + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
            storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.StoreID.toString())));
            storeModel.setUserName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.UserName.toString())));
            storeModel.setPassword(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Password.toString())));
            storeModel.setFirstName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Fname.toString())));
            storeModel.setMiddleName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Mname.toString())));
            storeModel.setLastName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Lname.toString())));
            storeModel.setLevel(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Level.toString())));
            storeModel.setRegBy(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.RegBy.toString())));
            storeModel.setRegDate(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.RegDate.toString())));
            storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Remarks.toString())));
            storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.IsActive.toString())));
            storeModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.IsUploaded.toString())));
        } else {
            storeModel = null;
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModel;
    }

    public LinkedList<StoreModel> getAllStoreUser() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StoreUser.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.StoreID.toString())));
                storeModel.setUserName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.UserName.toString())));
                storeModel.setPassword(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Password.toString())));
                storeModel.setFirstName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Fname.toString())));
                storeModel.setMiddleName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Mname.toString())));
                storeModel.setLastName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Lname.toString())));
                storeModel.setLevel(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Level.toString())));
                storeModel.setRegBy(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.RegBy.toString())));
                storeModel.setRegDate(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.RegDate.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.IsActive.toString())));
                storeModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.IsUploaded.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllStoreAccount() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StoreAccount.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreAccount.StoreID.toString())));
                storeModel.setTotalPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreAccount.TotalPoints.toString())));
                storeModel.setRemainingPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreAccount.RemainingPoints.toString())));
                storeModel.setTotalWDPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreAccount.TotalWDPoints.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllStorePointsHistory() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StorePointsHistory.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePointsHistory.StoreID.toString())));
                storeModel.setPointsRef(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePointsHistory.PointsRef.toString())));
                storeModel.setModeOfPayment(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePointsHistory.ModeOfPayment.toString())));
                storeModel.setAmount(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePointsHistory.Amount.toString())));
                storeModel.setPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePointsHistory.Points.toString())));
                storeModel.setDateDeposited(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePointsHistory.DateDeposited.toString())));
                storeModel.setReceivedDate(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePointsHistory.ReceivedDate.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePointsHistory.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePointsHistory.IsActive.toString())));
                storeModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePointsHistory.IsUploaded.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllStoreStocksReg() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StoreStocksReg.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.StoreID.toString())));
                storeModel.setStocksRef(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.StocksRef.toString())));
                storeModel.setItemID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.ItemID.toString())));
                storeModel.setQuantity(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.Quantity.toString())));
                storeModel.setDateReg(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.DateReg.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.IsActive.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllStoreStocksRegWithItems() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM "+ DBModels.enumTables.StoreStocksReg + " a INNER JOIN "+
                DBModels.enumTables.Item + " b ON a."+ DBModels.enumStoreStocksReg.ItemID + "=b." +
                DBModels.enumItem.ItemID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.StoreID.toString())));
                storeModel.setStocksRef(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.StocksRef.toString())));
                storeModel.setItemID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.ItemID.toString())));
                storeModel.setItemDesc(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.ItemDesc.toString())));
                storeModel.setQuantity(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.Quantity.toString())));
                storeModel.setDateReg(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.DateReg.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.IsActive.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public String getStoreStocksRef() {
        String stocksRef;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StoreStocksReg.toString() + " ORDER BY "
                + DBModels.enumStoreStocksReg.StocksRef + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null)
            cursor.moveToFirst();

        stocksRef = cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocksReg.StocksRef.toString()));

        cursor.close();
        db.close();
        // return contact list
        return stocksRef;
    }

    public LinkedList<StoreModel> getAllStoreStocks() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StoreStocks.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocks.StoreID.toString())));
                storeModel.setItemID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocks.ItemID.toString())));
                storeModel.setQuantity(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocks.Quantity.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocks.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocks.IsActive.toString())));
                storeModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocks.IsUploaded.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllStoreStocksWithItems() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM "+ DBModels.enumTables.StoreStocks + " a INNER JOIN "+
                DBModels.enumTables.Item + " b ON a."+ DBModels.enumStoreStocks.ItemID + "=b." +
                DBModels.enumItem.ItemID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocks.StoreID.toString())));
                storeModel.setItemID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocks.ItemID.toString())));
                storeModel.setItemDesc(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.ItemDesc.toString())));
                storeModel.setQuantity(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocks.Quantity.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocks.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocks.IsActive.toString())));
                storeModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreStocks.IsUploaded.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllStoreLogs() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StoreLogs.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreLogs.StoreID.toString())));
                storeModel.setUserName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreLogs.UserName.toString())));
                storeModel.setAuditDate(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreLogs.AuditDate.toString())));
                storeModel.setAuditDesc(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreLogs.AuditDesc.toString())));
                storeModel.setStocksRef(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreLogs.StocksRef.toString())));
                storeModel.setPurchaseRef(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreLogs.PurRef.toString())));
                storeModel.setCustomerID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreLogs.CustID.toString())));
                storeModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreLogs.IsUploaded.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllStorePurchased() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StorePurchased.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchased.StoreID.toString())));
                storeModel.setPurchaseRef(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchased.PurRef.toString())));
                storeModel.setPurchaseDate(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchased.PurDate.toString())));
                storeModel.setTotalAmt(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchased.TotalAmt.toString())));
                storeModel.setTotalQty(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchased.TotalQty.toString())));
                storeModel.setRebatePoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchased.RebatePoints.toString())));
                storeModel.setSharePoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchased.SharePoints.toString())));
                storeModel.setRegBy(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchased.RegBy.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchased.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchased.IsActive.toString())));
                storeModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchased.IsUploaded.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllStorePurchasedDetails() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StorePurchasedDetails.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchasedDetails.StoreID.toString())));
                storeModel.setPurchaseRef(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchasedDetails.PurRef.toString())));
                storeModel.setProductID(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchasedDetails.ProductID.toString())));
                storeModel.setQuantity(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchasedDetails.Quantity.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchasedDetails.IsActive.toString())));
                storeModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumStorePurchasedDetails.IsUploaded.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
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

//     Deleting single row
    public void deleteStoreUser(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBModels.enumTables.StoreUser.toString(), DBModels.recID + " = ?",
                new String[] { storeModel.getRecID() });
        db.close();
    }
}

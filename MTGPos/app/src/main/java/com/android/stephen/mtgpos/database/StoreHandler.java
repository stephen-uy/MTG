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
        values.put(DBModels.enumStore.StoreName.toString(), storeModel.getStoreName());
        values.put(DBModels.enumStore.Street.toString(), storeModel.getStreet());
        values.put(DBModels.enumStore.City.toString(), storeModel.getCity());
        values.put(DBModels.enumStore.Municipality.toString(), storeModel.getMunicipality());
        values.put(DBModels.enumStore.Province.toString(), storeModel.getProvince());
        values.put(DBModels.enumStore.ZipCode.toString(), storeModel.getZipCode());
        values.put(DBModels.enumStore.Region.toString(), storeModel.getRegion());
        values.put(DBModels.enumStore.Island.toString(), storeModel.getIsland());
        values.put(DBModels.enumStore.Longitude.toString(), storeModel.getLongitude());
        values.put(DBModels.enumStore.Latitude.toString(), storeModel.getLatitude());
        values.put(DBModels.enumStore.Email.toString(), storeModel.getEmailAddress());
        values.put(DBModels.enumStore.Mobile.toString(), storeModel.getMobileNumber());
        values.put(DBModels.enumStore.Phone.toString(), storeModel.getPhone());
        values.put(DBModels.enumStore.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumStore.IsActive.toString(), storeModel.getIsActive());
        values.put(DBModels.enumStore.IsMTGStore.toString(), storeModel.getIsMTGStore());
        values.put(DBModels.enumStore.AppType.toString(), storeModel.getAppType());
        values.put(DBModels.enumStore.DateReg.toString(), storeModel.getDateReg());
        values.put(DBModels.enumStore.RegBy.toString(), storeModel.getRegBy());
        values.put(DBModels.enumStore.DateLastUpdated.toString(), storeModel.getDateLastUpdated());

        // Inserting Row
        long result = db.insert(DBModels.enumTables.Store.toString(), null, values);
        Log.d("addStore-result","" + result);
//        db.close(); // Closing database connection
        if (result > 0)
            return true;
        else
            return false;
    }

    public void addStoreUser(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStoreUser.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStoreUser.UserName.toString(), storeModel.getUserName());
        values.put(DBModels.enumStoreUser.SaltPass.toString(), storeModel.getSaltPass());
        values.put(DBModels.enumStoreUser.Pass.toString(), storeModel.getPass());
        values.put(DBModels.enumStoreUser.FName.toString(), storeModel.getFirstName());
        values.put(DBModels.enumStoreUser.MName.toString(), storeModel.getMiddleName());
        values.put(DBModels.enumStoreUser.LName.toString(), storeModel.getLastName());
        values.put(DBModels.enumStoreUser.Level.toString(), storeModel.getLevel());
        values.put(DBModels.enumStoreUser.RegBy.toString(), storeModel.getRegBy());
        values.put(DBModels.enumStoreUser.DateReg.toString(), storeModel.getDateReg());
        values.put(DBModels.enumStoreUser.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumStoreUser.IsActive.toString(), storeModel.getIsActive());
        values.put(DBModels.enumStoreUser.IsOwner.toString(), storeModel.getIsOwner());
        values.put(DBModels.enumStoreUser.DateLastUpdated.toString(), storeModel.getDateLastUpdated());

        // Inserting Row
        db.insert(DBModels.enumTables.StoreUser.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public int updateStoreUser(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStoreUser.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStoreUser.UserName.toString(), storeModel.getUserName());
        values.put(DBModels.enumStoreUser.Pass.toString(), storeModel.getPass());
        values.put(DBModels.enumStoreUser.FName.toString(), storeModel.getFirstName());
        values.put(DBModels.enumStoreUser.MName.toString(), storeModel.getMiddleName());
        values.put(DBModels.enumStoreUser.LName.toString(), storeModel.getLastName());
        values.put(DBModels.enumStoreUser.Level.toString(), storeModel.getLevel());
        values.put(DBModels.enumStoreUser.RegBy.toString(), storeModel.getRegBy());
        values.put(DBModels.enumStoreUser.DateReg.toString(), storeModel.getDateReg());
        values.put(DBModels.enumStoreUser.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumStoreUser.IsActive.toString(), storeModel.getIsActive());
        values.put(DBModels.enumStoreUser.IsOwner.toString(), storeModel.getIsOwner());
        values.put(DBModels.enumStoreUser.DateLastUpdated.toString(), storeModel.getDateLastUpdated());

//        db.close(); // Closing database connection
        return db.update(DBModels.enumTables.StoreUser.toString(), values, DBModels.recID.toString() + " = ?",
                new String[] { storeModel.getRecID() });
    }

    public void addStoreUPoints(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStoreUPoints.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStoreUPoints.TotalPoints.toString(), storeModel.getTotalPoints());
        values.put(DBModels.enumStoreUPoints.RemainingPoints.toString(), storeModel.getRemainingPoints());
        values.put(DBModels.enumStoreUPoints.TotalWDPoints.toString(), storeModel.getTotalWDPoints());

        // Inserting Row
        db.insert(DBModels.enumTables.StoreUPoints.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addStoreUPointsHistory(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStoreUPointsHistory.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStoreUPointsHistory.PointsRef.toString(), storeModel.getPointsRef());
        values.put(DBModels.enumStoreUPointsHistory.ModeOfPayment.toString(), storeModel.getModeOfPayment());
        values.put(DBModels.enumStoreUPointsHistory.Amount.toString(), storeModel.getAmount());
        values.put(DBModels.enumStoreUPointsHistory.UPoints.toString(), storeModel.getuPoints());
        values.put(DBModels.enumStoreUPointsHistory.DateOfTransaction.toString(), storeModel.getDateDeposited());
        values.put(DBModels.enumStoreUPointsHistory.DateReg.toString(), storeModel.getReceivedDate());
        values.put(DBModels.enumStoreUPointsHistory.RegBy.toString(), storeModel.getRegBy());
        values.put(DBModels.enumStoreUPointsHistory.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumStoreUPointsHistory.IsActive.toString(), storeModel.getIsActive());
        values.put(DBModels.enumStoreUPointsHistory.OldTotalRemainingPoints.toString(), storeModel.getOldTotalRemainingPoints());
        values.put(DBModels.enumStoreUPointsHistory.OldTotalPoints.toString(), storeModel.getOldTotalPoints());
        values.put(DBModels.enumStoreUPointsHistory.NewTotalRemainingPoints.toString(), storeModel.getNewTotalRemainingPoints());
        values.put(DBModels.enumStoreUPointsHistory.NewTotalPoints.toString(), storeModel.getNewTotalPoints());
        values.put(DBModels.enumStoreUPointsHistory.IsAddToStore.toString(), storeModel.getIsAddToStore());
        values.put(DBModels.enumStoreUPointsHistory.TranType.toString(), storeModel.getTranType());
//        values.put(DBModels.enumStoreUPointsHistory.DateLastUpdated.toString(), storeModel.getDateLastUpdated());

        // Inserting Row
        db.insert(DBModels.enumTables.StoreUPointsHistory.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addStocksRegistration(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStocksRegistration.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStocksRegistration.StocksRef.toString(), storeModel.getStocksRef());
        values.put(DBModels.enumStocksRegistration.ItemID.toString(), storeModel.getItemID());
        values.put(DBModels.enumStocksRegistration.Quantity.toString(), storeModel.getQuantity());
        values.put(DBModels.enumStocksRegistration.DateReg.toString(), storeModel.getDateReg());
        values.put(DBModels.enumStocksRegistration.RegBy.toString(), storeModel.getRegBy());
        values.put(DBModels.enumStocksRegistration.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumStocksRegistration.IsActive.toString(), storeModel.getIsActive());

        // Inserting Row
        db.insert(DBModels.enumTables.StocksRegistration.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addStocks(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumStocks.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumStocks.ItemID.toString(), storeModel.getItemID());
        values.put(DBModels.enumStocks.Quantity.toString(), storeModel.getQuantity());
        values.put(DBModels.enumStocks.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumStocks.IsActive.toString(), storeModel.getIsActive());

        // Inserting Row
        db.insert(DBModels.enumTables.Stocks.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addAuditLogs(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumAuditLogs.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumAuditLogs.AuditDate.toString(), storeModel.getAuditDate());
        values.put(DBModels.enumAuditLogs.AuditDesc.toString(), storeModel.getAuditDesc());
        values.put(DBModels.enumAuditLogs.Module.toString(), storeModel.getModule());
        values.put(DBModels.enumAuditLogs.PurRef.toString(), storeModel.getPurchaseRef());
        values.put(DBModels.enumAuditLogs.RegBy.toString(), storeModel.getRegBy());

        // Inserting Row
        db.insert(DBModels.enumTables.AuditLogs.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addPurchased(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumPurchased.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumPurchased.PurRef.toString(), storeModel.getPurchaseRef());
        values.put(DBModels.enumPurchased.PurDate.toString(), storeModel.getPurchaseDate());
        values.put(DBModels.enumPurchased.TotalAmt.toString(), storeModel.getTotalAmt());
        values.put(DBModels.enumPurchased.TotalQty.toString(), storeModel.getTotalQty());
        values.put(DBModels.enumPurchased.TotalRPoints.toString(), storeModel.getTotalRPoints());
        values.put(DBModels.enumPurchased.TotalSPoints.toString(), storeModel.getTotalSPoints());
        values.put(DBModels.enumPurchased.RegBy.toString(), storeModel.getRegBy());
        values.put(DBModels.enumPurchased.DateReg.toString(), storeModel.getDateReg());
        values.put(DBModels.enumPurchased.Remarks.toString(), storeModel.getRemarks());
        values.put(DBModels.enumPurchased.IsActive.toString(), storeModel.getIsActive());

        // Inserting Row
        db.insert(DBModels.enumTables.Purchased.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addPurchasedProductDetails(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumPurchasedProductDetails.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumPurchasedProductDetails.PurRef.toString(), storeModel.getPurchaseRef());
        values.put(DBModels.enumPurchasedProductDetails.ProductID.toString(), storeModel.getProductID());
        values.put(DBModels.enumPurchasedProductDetails.Quantity.toString(), storeModel.getQuantity());
        values.put(DBModels.enumPurchasedProductDetails.RPoints.toString(), storeModel.getrPoints());
        values.put(DBModels.enumPurchasedProductDetails.SPoints.toString(), storeModel.getsPoints());
        values.put(DBModels.enumPurchasedProductDetails.AmtPurchased.toString(), storeModel.getAmtPurchased());
        values.put(DBModels.enumPurchasedProductDetails.IsActive.toString(), storeModel.getIsActive());

        // Inserting Row
        db.insert(DBModels.enumTables.PurchasedProductDetails.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addPurchasedItemDetails(StoreModel storeModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumPurchasedItemDetails.StoreID.toString(), storeModel.getStoreID());
        values.put(DBModels.enumPurchasedItemDetails.PurRef.toString(), storeModel.getPurchaseRef());
        values.put(DBModels.enumPurchasedItemDetails.ProductID.toString(), storeModel.getProductID());
        values.put(DBModels.enumPurchasedItemDetails.ItemID.toString(), storeModel.getItemID());

        // Inserting Row
        db.insert(DBModels.enumTables.PurchasedItemDetails.toString(), null, values);
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
                storeModel.setStoreName(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.StoreName.toString())));
                storeModel.setStreet(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Street.toString())));
                storeModel.setCity(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.City.toString())));
                storeModel.setMunicipality(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Municipality.toString())));
                storeModel.setProvince(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Province.toString())));
                storeModel.setZipCode(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.ZipCode.toString())));
                storeModel.setRegion(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Region.toString())));
                storeModel.setIsland(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Island.toString())));
                storeModel.setLongitude(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Longitude.toString())));
                storeModel.setLatitude(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Latitude.toString())));
                storeModel.setEmailAddress(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Email.toString())));
                storeModel.setMobileNumber(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Mobile.toString())));
                storeModel.setPhone(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Phone.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.IsActive.toString())));
                storeModel.setIsMTGStore(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.IsMTGStore.toString())));
                storeModel.setAppType(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.AppType.toString())));
                storeModel.setDateReg(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.DateReg.toString())));
                storeModel.setRegBy(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.RegBy.toString())));
                storeModel.setDateLastUpdated(cursor.getString(cursor.getColumnIndex(DBModels.enumStore.DateLastUpdated.toString())));
                // Adding store to list
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
                " WHERE " + DBModels.enumStoreUser.UserName.toString() + " = '" + username.toUpperCase() + "' AND "
                + DBModels.enumStoreUser.Pass.toString() + " = '" + password + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
            storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.StoreID.toString())));
            storeModel.setUserName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.UserName.toString())));
            storeModel.setSaltPass(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.SaltPass.toString())));
            storeModel.setPass(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Pass.toString())));
            storeModel.setFirstName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.FName.toString())));
            storeModel.setMiddleName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.MName.toString())));
            storeModel.setLastName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.LName.toString())));
            storeModel.setLevel(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Level.toString())));
            storeModel.setRegBy(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.RegBy.toString())));
            storeModel.setDateReg(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.DateReg.toString())));
            storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Remarks.toString())));
            storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.IsActive.toString())));
            storeModel.setIsOwner(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.IsOwner.toString())));
            storeModel.setDateLastUpdated(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.DateLastUpdated.toString())));
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
                storeModel.setPass(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Pass.toString())));
                storeModel.setFirstName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.FName.toString())));
                storeModel.setMiddleName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.MName.toString())));
                storeModel.setLastName(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.LName.toString())));
                storeModel.setLevel(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Level.toString())));
                storeModel.setRegBy(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.RegBy.toString())));
                storeModel.setDateReg(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.DateReg.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.IsActive.toString())));
                storeModel.setIsOwner(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.IsOwner.toString())));
                storeModel.setDateLastUpdated(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUser.DateLastUpdated.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllStoreUPoints() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StoreUPoints.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPoints.StoreID.toString())));
                storeModel.setTotalPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPoints.TotalPoints.toString())));
                storeModel.setRemainingPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPoints.RemainingPoints.toString())));
                storeModel.setTotalWDPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPoints.TotalWDPoints.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllStoreUPointsHistory() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StoreUPointsHistory.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.StoreID.toString())));
                storeModel.setPointsRef(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.PointsRef.toString())));
                storeModel.setModeOfPayment(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.ModeOfPayment.toString())));
                storeModel.setAmountDeposited(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.Amount.toString())));
                storeModel.setuPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.UPoints.toString())));
                storeModel.setDateDeposited(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.DateOfTransaction.toString())));
                storeModel.setDateReg(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.DateReg.toString())));
                storeModel.setRegBy(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.RegBy.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.IsActive.toString())));
                storeModel.setOldTotalRemainingPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.OldTotalRemainingPoints.toString())));
                storeModel.setOldTotalPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.OldTotalPoints.toString())));
                storeModel.setNewTotalRemainingPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.NewTotalRemainingPoints.toString())));
                storeModel.setNewTotalPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.NewTotalPoints.toString())));
                storeModel.setIsAddToStore(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.IsAddToStore.toString())));
                storeModel.setTranType(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.TranType.toString())));
//                storeModel.setDateLastUpdated(cursor.getString(cursor.getColumnIndex(DBModels.enumStoreUPointsHistory.DateLastUpdated.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllStocksRegistration() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StocksRegistration.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.StoreID.toString())));
                storeModel.setStocksRef(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.StocksRef.toString())));
                storeModel.setItemID(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.ItemID.toString())));
                storeModel.setQuantity(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.Quantity.toString())));
                storeModel.setDateReg(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.DateReg.toString())));
                storeModel.setRegBy(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.RegBy.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.IsActive.toString())));
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
        String selectQuery = "SELECT * FROM "+ DBModels.enumTables.StocksRegistration + " a INNER JOIN "+
                DBModels.enumTables.StoreItem + " b ON a."+ DBModels.enumStocksRegistration.ItemID + "=b." +
                DBModels.enumItem.ItemID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.StoreID.toString())));
                storeModel.setStocksRef(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.StocksRef.toString())));
                storeModel.setItemID(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.ItemID.toString())));
                storeModel.setItemDesc(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.ItemDesc.toString())));
                storeModel.setQuantity(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.Quantity.toString())));
                storeModel.setDateReg(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.DateReg.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.IsActive.toString())));
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
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.StocksRegistration.toString() + " ORDER BY "
                + DBModels.enumStocksRegistration.StocksRef + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null)
            cursor.moveToFirst();

        stocksRef = cursor.getString(cursor.getColumnIndex(DBModels.enumStocksRegistration.StocksRef.toString()));

        cursor.close();
        db.close();
        // return contact list
        return stocksRef;
    }

    public LinkedList<StoreModel> getAllStocks() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.Stocks.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStocks.StoreID.toString())));
                storeModel.setItemID(cursor.getString(cursor.getColumnIndex(DBModels.enumStocks.ItemID.toString())));
                storeModel.setQuantity(cursor.getString(cursor.getColumnIndex(DBModels.enumStocks.Quantity.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStocks.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStocks.IsActive.toString())));
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
        String selectQuery = "SELECT * FROM "+ DBModels.enumTables.Stocks + " a INNER JOIN "+
                DBModels.enumTables.StoreItem + " b ON a."+ DBModels.enumStocks.ItemID + "=b." +
                DBModels.enumItem.ItemID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumStocks.StoreID.toString())));
                storeModel.setItemID(cursor.getString(cursor.getColumnIndex(DBModels.enumStocks.ItemID.toString())));
                storeModel.setItemDesc(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.ItemDesc.toString())));
                storeModel.setQuantity(cursor.getString(cursor.getColumnIndex(DBModels.enumStocks.Quantity.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumStocks.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumStocks.IsActive.toString())));
                storeModel.setItemType(cursor.getString(cursor.getColumnIndex(DBModels.enumItem.ItemType.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllAuditLogs() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.AuditLogs.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumAuditLogs.StoreID.toString())));
                storeModel.setAuditDate(cursor.getString(cursor.getColumnIndex(DBModels.enumAuditLogs.AuditDate.toString())));
                storeModel.setAuditDesc(cursor.getString(cursor.getColumnIndex(DBModels.enumAuditLogs.AuditDesc.toString())));
                storeModel.setModule(cursor.getString(cursor.getColumnIndex(DBModels.enumAuditLogs.Module.toString())));
                storeModel.setPurchaseRef(cursor.getString(cursor.getColumnIndex(DBModels.enumAuditLogs.PurRef.toString())));
                storeModel.setRegBy(cursor.getString(cursor.getColumnIndex(DBModels.enumAuditLogs.RegBy.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllPurchased() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.Purchased.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchased.StoreID.toString())));
                storeModel.setPurchaseRef(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchased.PurRef.toString())));
                storeModel.setPurchaseDate(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchased.PurDate.toString())));
                storeModel.setTotalAmt(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchased.TotalAmt.toString())));
                storeModel.setTotalQty(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchased.TotalQty.toString())));
                storeModel.setTotalRPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchased.TotalRPoints.toString())));
                storeModel.setTotalSPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchased.TotalSPoints.toString())));
                storeModel.setRegBy(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchased.RegBy.toString())));
                storeModel.setDateReg(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchased.DateReg.toString())));
                storeModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchased.Remarks.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchased.IsActive.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllPurchasedProductDetails() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.PurchasedProductDetails.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchasedProductDetails.StoreID.toString())));
                storeModel.setPurchaseRef(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchasedProductDetails.PurRef.toString())));
                storeModel.setProductID(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchasedProductDetails.ProductID.toString())));
                storeModel.setQuantity(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchasedProductDetails.Quantity.toString())));
                storeModel.setrPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchasedProductDetails.RPoints.toString())));
                storeModel.setsPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchasedProductDetails.SPoints.toString())));
                storeModel.setAmtPurchased(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchasedProductDetails.AmtPurchased.toString())));
                storeModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchasedProductDetails.IsActive.toString())));
                // Adding contact to list
                storeModelLinkedList.add(storeModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return storeModelLinkedList;
    }

    public LinkedList<StoreModel> getAllPurchasedItemDetails() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.PurchasedItemDetails.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoreModel storeModel = new StoreModel();
                storeModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                storeModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchasedItemDetails.StoreID.toString())));
                storeModel.setPurchaseRef(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchasedItemDetails.PurRef.toString())));
                storeModel.setProductID(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchasedItemDetails.ProductID.toString())));
                storeModel.setItemID(cursor.getString(cursor.getColumnIndex(DBModels.enumPurchasedItemDetails.ItemID.toString())));
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

    public void deleteTable(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE IF EXISTS " + table;
        db.execSQL(query);
        db.close();
        Log.d("deleteTable",query);
    }

    public void createTable(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
        Log.d("createTable",query);
    }
}

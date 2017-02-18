package com.android.stephen.mtgpos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.stephen.mtgpos.model.CustomerModel;

import java.util.LinkedList;

/**
 * Created by Stephen Uy on 1/30/2017.
 */

public class CustomerHandler extends SQLiteDBHandler{

    private static CustomerHandler instance;

    public CustomerHandler(Context context) {
        super(context);
    }

    public static CustomerHandler getInstance(Context context){
        if(instance == null) {
            instance = new CustomerHandler(context);
        }
        return instance;
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new customer
    public boolean addCustomer(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumCustomer.StoreID.toString(), customerModel.getStoreID());
        values.put(DBModels.enumCustomer.CustID.toString(), customerModel.getCustomerID());
        values.put(DBModels.enumCustomer.UpCustID.toString(), customerModel.getUpCustomerID());
        values.put(DBModels.enumCustomer.Fname.toString(), customerModel.getFirstName());
        values.put(DBModels.enumCustomer.Mname.toString(), customerModel.getMiddleName());
        values.put(DBModels.enumCustomer.Lname.toString(), customerModel.getLastName());
        values.put(DBModels.enumCustomer.BirthDate.toString(), customerModel.getBirthDate());
        values.put(DBModels.enumCustomer.Email.toString(), customerModel.getEmailAddress());
        values.put(DBModels.enumCustomer.Mobile.toString(), customerModel.getMobileNumber());
        values.put(DBModels.enumCustomer.Remarks.toString(), customerModel.getRemarks());
        values.put(DBModels.enumCustomer.Password.toString(), customerModel.getPassword());
        values.put(DBModels.enumCustomer.IsStoreOwner.toString(), customerModel.getIsStoreOwner());
        values.put(DBModels.enumCustomer.IsActive.toString(), customerModel.getIsActive());
        values.put(DBModels.enumCustomer.IsUploaded.toString(), customerModel.getIsUploaded());

        // Inserting Row
        long result = db.insert(DBModels.enumTables.Customer.toString(), null, values);
        Log.d("addCustomer-result","" + result);
//        db.close(); // Closing database connection
        if (result != -1)
            return true;
        else
            return false;
    }

    public boolean addCustomerPicture(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumCustomerPicture.CustID.toString(), customerModel.getCustomerID());
        values.put(DBModels.enumCustomerPicture.Picture.toString(), customerModel.getPicture());
        values.put(DBModels.enumCustomerPicture.DateCaptured.toString(), customerModel.getDateCaptured());
        // Inserting Row
//        db.insert(DBModels.enumTables.CustomerPicture.toString(), null, values);
        // Inserting Row
        long result = db.insert(DBModels.enumTables.CustomerPicture.toString(), null, values);
        Log.d("addCustomerPic-result","" + result);
//        db.close(); // Closing database connection
        if (result != -1)
            return true;
        else
            return false;
//        db.close(); // Closing database connection
    }

    public void addCustomerPictureHistory(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumCustomerPictureHistory.CustID.toString(), customerModel.getCustomerID());
        values.put(DBModels.enumCustomerPictureHistory.Picture.toString(), customerModel.getPicture());
        values.put(DBModels.enumCustomerPictureHistory.DateCaptured.toString(), customerModel.getDateCaptured());
        // Inserting Row
        db.insert(DBModels.enumTables.CustomerPictureHistory.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addCustomerPoints(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumCustomerPoints.StoreID.toString(), customerModel.getStoreID());
        values.put(DBModels.enumCustomerPoints.CustID.toString(), customerModel.getCustomerID());
        values.put(DBModels.enumCustomerPoints.TotalPoints.toString(), customerModel.getTotalPoints());
        values.put(DBModels.enumCustomerPoints.RemainingPoints.toString(), customerModel.getRemainingPoints());
        values.put(DBModels.enumCustomerPoints.WithdrawPoints.toString(), customerModel.getWithdrawPoints());
        values.put(DBModels.enumCustomerPoints.IsUploaded.toString(), customerModel.getIsUploaded());

        // Inserting Row
        db.insert(DBModels.enumTables.CustomerPoints.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addCustomerUpline(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumCustomerUpline.StoreID.toString(), customerModel.getStoreID());
        values.put(DBModels.enumCustomerUpline.CustID.toString(), customerModel.getCustomerID());
        values.put(DBModels.enumCustomerUpline.CustIDUp1.toString(), customerModel.getCustomerUpID1());
        values.put(DBModels.enumCustomerUpline.CustIDUp2.toString(), customerModel.getCustomerUpID2());
        values.put(DBModels.enumCustomerUpline.CustIDUp3.toString(), customerModel.getCustomerUpID3());
        values.put(DBModels.enumCustomerUpline.CustIDUp4.toString(), customerModel.getCustomerUpID4());
        values.put(DBModels.enumCustomerUpline.IsUploaded.toString(), customerModel.getIsUploaded());

        // Inserting Row
        db.insert(DBModels.enumTables.CustomerUpline.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public void addCustomerBonusPoints(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumCustomerBonusPoints.StoreID.toString(), customerModel.getStoreID());
        values.put(DBModels.enumCustomerBonusPoints.PurRef.toString(), customerModel.getPurchaseReference());
        values.put(DBModels.enumCustomerBonusPoints.CustID.toString(), customerModel.getCustomerID());
        values.put(DBModels.enumCustomerBonusPoints.FromCustID.toString(), customerModel.getFromCustomerID());
        values.put(DBModels.enumCustomerBonusPoints.Points.toString(), customerModel.getPoints());
        values.put(DBModels.enumCustomerBonusPoints.DateReceived.toString(), customerModel.getDateReceived());
        values.put(DBModels.enumCustomerBonusPoints.PointsType.toString(), customerModel.getPointsType());
        values.put(DBModels.enumCustomerBonusPoints.IsUploaded.toString(), customerModel.getIsUploaded());

        // Inserting Row
        db.insert(DBModels.enumTables.CustomerBonusPoints.toString(), null, values);
//        db.close(); // Closing database connection
    }

    public String getOwnerCustomerID(){
        // Select All Query
        String ownerCustID;
        String selectQuery = "SELECT * FROM " + DBModels.enumTables.Customer.toString() +
                " WHERE " + DBModels.enumCustomer.IsStoreOwner.toString() + " = 'Y';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        ownerCustID = cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.CustID.toString()));

        cursor.close();
        db.close();
        return ownerCustID.toUpperCase();
    }

    public CustomerModel getCustomerUplines(String custID, String storeID){
        // Select All Query
        String selectQuery = "SELECT * FROM " + DBModels.enumTables.CustomerUpline.toString() +
                " WHERE " + DBModels.enumCustomerUpline.CustID.toString() + " = '" + custID + "' AND "
                + DBModels.enumCustomerUpline.StoreID + " = '" + storeID + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        CustomerModel customerModel = new CustomerModel();

        if (cursor != null) {
            cursor.moveToFirst();
            customerModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
            customerModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.StoreID.toString())));
            customerModel.setCustomerID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.CustID.toString())));
            customerModel.setCustomerUpID1(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.CustIDUp1.toString())));
            customerModel.setCustomerUpID2(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.CustIDUp2.toString())));
            customerModel.setCustomerUpID3(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.CustIDUp3.toString())));
            customerModel.setCustomerUpID4(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.CustIDUp4.toString())));
            customerModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.IsUploaded.toString())));
        }

        cursor.close();
        db.close();
        return customerModel;
    }

    // Getting All Customer
    public LinkedList<CustomerModel> getAllCustomer() {
        LinkedList<CustomerModel> customerModelList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.Customer.toString() + " WHERE "
                + DBModels.enumCustomer.IsStoreOwner.toString() + "='N';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerModel customerModel = new CustomerModel();
                customerModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                customerModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.StoreID.toString())));
                customerModel.setCustomerID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.CustID.toString())));
                customerModel.setUpCustomerID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.UpCustID.toString())));
                customerModel.setFirstName(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Fname.toString())));
                customerModel.setMiddleName(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Mname.toString())));
                customerModel.setLastName(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Lname.toString())));
                customerModel.setBirthDate(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.BirthDate.toString())));
                customerModel.setEmailAddress(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Email.toString())));
                customerModel.setMobileNumber(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Mobile.toString())));
                customerModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Remarks.toString())));
                customerModel.setPassword(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Password.toString())));
                customerModel.setIsStoreOwner(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.IsStoreOwner.toString())));
                customerModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.IsActive.toString())));
                customerModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.IsUploaded.toString())));
                // Adding contact to list
                customerModelList.add(customerModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return customerModelList;
    }

    public LinkedList<CustomerModel> getAllCustomerWithPicture() {
        LinkedList<CustomerModel> customerModelList = new LinkedList<>();
        // Select All Query
//        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.Customer.toString() + " WHERE "
//                + DBModels.enumCustomer.IsStoreOwner.toString() + "='N';";
        String selectQuery = "SELECT * FROM "+ DBModels.enumTables.Customer + " a INNER JOIN "+
                DBModels.enumTables.CustomerPicture + " b ON a."+ DBModels.enumCustomer.CustID + "=b." +
                DBModels.enumCustomerPicture.CustID + " WHERE " + DBModels.enumCustomer.IsStoreOwner.toString() + "='N';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerModel customerModel = new CustomerModel();
                customerModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                customerModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.StoreID.toString())));
                customerModel.setCustomerID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.CustID.toString())));
                customerModel.setUpCustomerID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.UpCustID.toString())));
                customerModel.setFirstName(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Fname.toString())));
                customerModel.setMiddleName(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Mname.toString())));
                customerModel.setLastName(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Lname.toString())));
                customerModel.setBirthDate(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.BirthDate.toString())));
                customerModel.setEmailAddress(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Email.toString())));
                customerModel.setMobileNumber(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Mobile.toString())));
                customerModel.setRemarks(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Remarks.toString())));
                customerModel.setPassword(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.Password.toString())));
                customerModel.setIsStoreOwner(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.IsStoreOwner.toString())));
                customerModel.setIsActive(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.IsActive.toString())));
                customerModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomer.IsUploaded.toString())));
                customerModel.setPicture(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPicture.Picture.toString())));
                // Adding contact to list
                customerModelList.add(customerModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return customerModelList;
    }

    public LinkedList<CustomerModel> getAllCustomerUpline() {
        LinkedList<CustomerModel> customerModelList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.CustomerUpline.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerModel customerModel = new CustomerModel();
                customerModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                customerModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.StoreID.toString())));
                customerModel.setCustomerID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.CustID.toString())));
                customerModel.setCustomerUpID1(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.CustIDUp1.toString())));
                customerModel.setCustomerUpID2(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.CustIDUp2.toString())));
                customerModel.setCustomerUpID3(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.CustIDUp3.toString())));
                customerModel.setCustomerUpID4(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.CustIDUp4.toString())));
                customerModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerUpline.IsUploaded.toString())));
                // Adding contact to list
                customerModelList.add(customerModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return customerModelList;
    }

    public LinkedList<CustomerModel> getAllCustomerPicture() {
        LinkedList<CustomerModel> customerModelList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.CustomerPicture.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerModel customerModel = new CustomerModel();
                customerModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                customerModel.setCustomerID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPicture.CustID.toString())));
                customerModel.setPicture(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPicture.Picture.toString())));
                customerModel.setDateCaptured(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPicture.DateCaptured.toString())));
                // Adding contact to list
                customerModelList.add(customerModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return customerModelList;
    }

    public String getCustomerPictureByCustomerID(String customerID) {
        String picture;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.CustomerPicture.toString() + " WHERE "
                + DBModels.enumCustomerPicture.CustID.toString() + "='" + customerID + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        picture = cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPicture.Picture.toString()));

        cursor.close();
        db.close();
        // return contact list
        return picture;
    }

    public LinkedList<CustomerModel> getAllCustomerPictureHistory() {
        LinkedList<CustomerModel> customerModelList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.CustomerPictureHistory.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerModel customerModel = new CustomerModel();
                customerModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                customerModel.setCustomerID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPictureHistory.CustID.toString())));
                customerModel.setPicture(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPictureHistory.Picture.toString())));
                customerModel.setDateCaptured(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPictureHistory.DateCaptured.toString())));
                // Adding contact to list
                customerModelList.add(customerModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return customerModelList;
    }

    public LinkedList<CustomerModel> getAllCustomerPoints() {
        LinkedList<CustomerModel> customerModelList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.CustomerPoints.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerModel customerModel = new CustomerModel();
                customerModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                customerModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPoints.StoreID.toString())));
                customerModel.setCustomerID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPoints.CustID.toString())));
                customerModel.setTotalPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPoints.TotalPoints.toString())));
                customerModel.setRemainingPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPoints.RemainingPoints.toString())));
                customerModel.setWithdrawPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPoints.WithdrawPoints.toString())));
                customerModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerPoints.IsUploaded.toString())));
                // Adding contact to list
                customerModelList.add(customerModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return customerModelList;
    }

    public LinkedList<CustomerModel> getAllCustomerBonusPoints() {
        LinkedList<CustomerModel> customerModelList = new LinkedList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModels.enumTables.CustomerBonusPoints.toString();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerModel customerModel = new CustomerModel();
                customerModel.setRecID(cursor.getString(cursor.getColumnIndex(DBModels.recID)));
                customerModel.setStoreID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerBonusPoints.StoreID.toString())));
                customerModel.setPurchaseReference(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerBonusPoints.PurRef.toString())));
                customerModel.setCustomerID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerBonusPoints.CustID.toString())));
                customerModel.setFromCustomerID(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerBonusPoints.FromCustID.toString())));
                customerModel.setPoints(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerBonusPoints.Points.toString())));
                customerModel.setDateReceived(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerBonusPoints.DateReceived.toString())));
                customerModel.setPointsType(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerBonusPoints.PointsType.toString())));
                customerModel.setIsUploaded(cursor.getString(cursor.getColumnIndex(DBModels.enumCustomerBonusPoints.IsUploaded.toString())));
                // Adding contact to list
                customerModelList.add(customerModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return customerModelList;
    }

    // Updating one row in Customer
    public int updateCustomer(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModels.enumCustomer.StoreID.toString(), customerModel.getStoreID());
        values.put(DBModels.enumCustomer.CustID.toString(), customerModel.getCustomerID());
        values.put(DBModels.enumCustomer.UpCustID.toString(), customerModel.getUpCustomerID());
        values.put(DBModels.enumCustomer.Fname.toString(), customerModel.getFirstName());
        values.put(DBModels.enumCustomer.Mname.toString(), customerModel.getMiddleName());
        values.put(DBModels.enumCustomer.Lname.toString(), customerModel.getLastName());
        values.put(DBModels.enumCustomer.BirthDate.toString(), customerModel.getBirthDate());
        values.put(DBModels.enumCustomer.Email.toString(), customerModel.getEmailAddress());
        values.put(DBModels.enumCustomer.Mobile.toString(), customerModel.getMobileNumber());
        values.put(DBModels.enumCustomer.Remarks.toString(), customerModel.getRemarks());
        values.put(DBModels.enumCustomer.Password.toString(), customerModel.getPassword());
        values.put(DBModels.enumCustomer.IsStoreOwner.toString(), customerModel.getIsStoreOwner());
        values.put(DBModels.enumCustomer.IsActive.toString(), customerModel.getIsActive());
        values.put(DBModels.enumCustomer.IsUploaded.toString(), customerModel.getIsUploaded());

        // updating row
        return db.update(DBModels.enumTables.Customer.toString(), values, DBModels.enumCustomer.CustID.toString() + " = ?",
                new String[] { customerModel.getCustomerID() });
    }

    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }


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
}

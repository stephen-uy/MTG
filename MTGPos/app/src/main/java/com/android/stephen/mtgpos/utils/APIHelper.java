package com.android.stephen.mtgpos.utils;

import android.content.Context;
import android.util.Log;

import com.android.stephen.mtgpos.database.CustomerHandler;
import com.android.stephen.mtgpos.database.DBModels;
import com.android.stephen.mtgpos.database.LookUpHandler;
import com.android.stephen.mtgpos.database.StoreHandler;
import com.android.stephen.mtgpos.model.CustomerModel;
import com.android.stephen.mtgpos.model.LookUpModel;
import com.android.stephen.mtgpos.model.StoreModel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by Stephen Uy on 3/6/2017.
 */

public class APIHelper {

    public static void insertStoreUser(Context context, StoreModel storeModel) {
        StoreHandler storeHandler = new StoreHandler(context);
        int rowCount = storeHandler.getRowCounts(DBModels.enumTables.StoreUser.toString());
        storeHandler.addStoreUser(storeModel);
    }

    public static void insertStoreDetails(Context context, StoreModel storeModel) {
        StoreHandler storeHandler = new StoreHandler(context);
        int rowCount = storeHandler.getRowCounts(DBModels.enumTables.Store.toString());
        storeHandler.addStore(storeModel);
    }

    public static void insertStoreUPoints(Context context, StoreModel storeModel) {
        StoreHandler storeHandler = new StoreHandler(context);
        int rowCount = storeHandler.getRowCounts(DBModels.enumTables.StoreUPoints.toString());
        storeHandler.addStoreUPoints(storeModel);
    }

    public static void insertCustomerData(Context context) {
        CustomerHandler customerHandler = new CustomerHandler(context);
        CustomerModel customerModel = new CustomerModel();
        int rowCount = customerHandler.getRowCounts(DBModels.enumTables.Customer.toString());
        if (rowCount == 0) {
            //set up customer
            customerModel.setStoreID("2");
            customerModel.setUpCustomerID("SCC110482");
            customerModel.setFirstName("Stephen");
            customerModel.setMiddleName("Mallorca");
            customerModel.setLastName("Uy");
            customerModel.setBirthDate("Sep 20, 1990");
            customerModel.setEmailAddress("stephen.mallorca.uy@gmail.com");
            customerModel.setMobileNumber("09088607234");
            customerModel.setRemarks("franchise owner");
            customerModel.setIsStoreOwner("Y");
            customerModel.setIsActive("Y");
            customerModel.setIsUploaded("N");
            customerModel.setCustomerID(Helper.generateCustomerID(customerModel));

            //Add franchisee as 1st customer in customer table
            customerHandler.addCustomer(customerModel);

            //set up customer upline of franchisee
            customerModel.setCustomerUpID1("SCC110482");
            customerModel.setCustomerUpID2("");
            customerModel.setCustomerUpID3("");
            customerModel.setCustomerUpID4("");
            //add franchisee in customer upline table
            customerHandler.addCustomerUpline(customerModel);
        }
    }

    public static int insertItemData(Context context, LinkedList<LookUpModel> lookUpModelLinkedList){
        LookUpHandler lookUpHandler = new LookUpHandler(context);
        for(LookUpModel model : lookUpModelLinkedList){
            lookUpHandler.addItem(model);
        }

        int rowCount = lookUpHandler.getRowCounts(DBModels.enumTables.StoreItem.toString());
        return rowCount;
    }

    public static int insertProductData(Context context, LinkedList<LookUpModel> lookUpModelLinkedList){
        LookUpHandler lookUpHandler = new LookUpHandler(context);
        for(LookUpModel model : lookUpModelLinkedList){
            lookUpHandler.addProduct(model);
        }

        int rowCount = lookUpHandler.getRowCounts(DBModels.enumTables.Product.toString());
        return rowCount;
    }

    public static int insertProductItemData(Context context, LinkedList<LookUpModel> lookUpModelLinkedList){
        LookUpHandler lookUpHandler = new LookUpHandler(context);
        for(LookUpModel model : lookUpModelLinkedList){
            lookUpHandler.addProductItem(model);
        }

        int rowCount = lookUpHandler.getRowCounts(DBModels.enumTables.ProductItem.toString());
        return rowCount;
    }

    public static int insertItemTypeData(Context context, LinkedList<LookUpModel> lookUpModelLinkedList){
        LookUpHandler lookUpHandler = new LookUpHandler(context);
        for(LookUpModel model : lookUpModelLinkedList){
            lookUpHandler.addItemTypeLookUp(model);
        }

        int rowCount = lookUpHandler.getRowCounts(DBModels.enumTables.ItemTypeLookUp.toString());
        return rowCount;
    }

    public static int insertProCatData(Context context, LinkedList<LookUpModel> lookUpModelLinkedList){
        LookUpHandler lookUpHandler = new LookUpHandler(context);
        for(LookUpModel model : lookUpModelLinkedList){
            lookUpHandler.addProductCategory(model);
        }

        int rowCount = lookUpHandler.getRowCounts(DBModels.enumTables.ProductCategory.toString());
        return rowCount;
    }

    public static int insertStoreStocksData(Context context, LinkedList<StoreModel> storeModelLinkedList){
        StoreHandler storeHandler = new StoreHandler(context);
        for(StoreModel model : storeModelLinkedList){
            storeHandler.addStocks(model);
        }

        int rowCount = storeHandler.getRowCounts(DBModels.enumTables.Stocks.toString());
        return rowCount;
    }

    public static void insertStoreStocksRegData(Context context){
        LinkedList<StoreModel> storeModelLinkedList;
        StoreHandler storeHandler = new StoreHandler(context);
        storeModelLinkedList = setUpStoreStocksRegData(context);
        int rowCount = storeHandler.getRowCounts(DBModels.enumTables.StocksRegistration.toString());
        if (rowCount == 0) {
            for(StoreModel model : storeModelLinkedList){
                storeHandler.addStocksRegistration(model);
            }
        }
    }

    public static StoreModel setUpStoreDetails(HashMap<String, String> hashMap){
        StoreModel storeModel = new StoreModel();
        if (hashMap.size() > 0) {
            //Store
            storeModel.setStoreID(hashMap.get(DBModels.enumStore.StoreID.toString()));
            storeModel.setStoreName(hashMap.get(DBModels.enumStore.StoreName.toString()));
            storeModel.setStreet(hashMap.get(DBModels.enumStore.Street.toString()));
            storeModel.setCity(hashMap.get(DBModels.enumStore.City.toString()));
            storeModel.setMunicipality(hashMap.get(DBModels.enumStore.Municipality.toString()));
            storeModel.setProvince(hashMap.get(DBModels.enumStore.Province.toString()));
            storeModel.setZipCode(hashMap.get(DBModels.enumStore.ZipCode.toString()));
            storeModel.setRegion(hashMap.get(DBModels.enumStore.Region.toString()));
            storeModel.setIsland(hashMap.get(DBModels.enumStore.Island.toString()));
            storeModel.setLongitude(hashMap.get(DBModels.enumStore.Longitude.toString()));
            storeModel.setLatitude(hashMap.get(DBModels.enumStore.Latitude.toString()));
            storeModel.setEmailAddress(hashMap.get(DBModels.enumStore.Email.toString()));
            storeModel.setMobileNumber(hashMap.get(DBModels.enumStore.Mobile.toString()));
            storeModel.setPhone(hashMap.get(DBModels.enumStore.Phone.toString()));
            storeModel.setRemarks(hashMap.get(DBModels.enumStore.Remarks.toString()));
            storeModel.setIsActive("Y");
            storeModel.setIsMTGStore(hashMap.get(DBModels.enumStore.IsMTGStore.toString()));
            storeModel.setAppType(hashMap.get(DBModels.enumStore.AppType.toString()));
            storeModel.setDateReg(Helper.getDateTimeWithFormat());
            storeModel.setRegBy(hashMap.get(DBModels.enumStoreUser.UserName.toString()));
            storeModel.setDateLastUpdated(Helper.getDateTimeWithFormat());
            //Store User
            storeModel.setUserName(hashMap.get(DBModels.enumStoreUser.UserName.toString()));
//            storeModel.setPass(hashMap.get(DBModels.enumStoreUser.Pass.toString()));
            storeModel.setLevel(hashMap.get(DBModels.enumStoreUser.Level.toString()));
            storeModel.setFirstName(hashMap.get(DBModels.enumStoreUser.FName.toString()));
            storeModel.setMiddleName(hashMap.get(DBModels.enumStoreUser.MName.toString()));
            storeModel.setLastName(hashMap.get(DBModels.enumStoreUser.LName.toString()));
            storeModel.setIsOwner(hashMap.get(DBModels.enumStoreUser.IsOwner.toString()));
            //Store UPoints
            storeModel.setTotalPoints(hashMap.get(DBModels.enumStoreUPoints.TotalPoints.toString()));
            storeModel.setRemainingPoints(hashMap.get(DBModels.enumStoreUPoints.RemainingPoints.toString()));
            storeModel.setTotalWDPoints(hashMap.get(DBModels.enumStoreUPoints.TotalWDPoints.toString()));
        }

        return storeModel;
    }

    public static LinkedList<StoreModel> setUpStockList(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        //Stock List
        for (int i = 0; i < hashMaps.size(); i++) {
            storeModelLinkedList.add(new StoreModel(hashMaps.get(i).get(DBModels.enumStocks.StoreID.toString()),
                    hashMaps.get(i).get(DBModels.enumStocks.ItemID.toString()),
                    hashMaps.get(i).get(DBModels.enumStocks.Quantity.toString()),
                    hashMaps.get(i).get(DBModels.enumItem.ItemDesc.toString()),
                    hashMaps.get(i).get(DBModels.enumItem.ItemType.toString()),
                    hashMaps.get(i).get(DBModels.enumItem.QtyPerItemType.toString()),
                    hashMaps.get(i).get(DBModels.enumStocks.Remarks.toString()),
                    hashMaps.get(i).get(DBModels.enumStocks.IsActive.toString())
            ));
        }

        Log.d("setUpStockList-size","" + storeModelLinkedList.size());
        return storeModelLinkedList;
    }

    public static LinkedList<LookUpModel> setUpItemsData(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        LinkedList<LookUpModel> lookUpModelLinkedList = new LinkedList<>();
        //Items
        for (int i = 0; i < hashMaps.size(); i++) {
            lookUpModelLinkedList.add(new LookUpModel(hashMaps.get(i).get(DBModels.enumItem.StoreID.toString()),
                    hashMaps.get(i).get(DBModels.enumItem.ItemID.toString()),
                    hashMaps.get(i).get(DBModels.enumItem.ItemDesc.toString()),
                    hashMaps.get(i).get(DBModels.enumItem.QtyPerItemType.toString()),
                    hashMaps.get(i).get(DBModels.enumItem.ItemType.toString()),
                    hashMaps.get(i).get(DBModels.enumItem.IsActive.toString()),
                    hashMaps.get(i).get(DBModels.enumItem.DateLastUpdated.toString())
                    ));
        }

        Log.d("setUpItemsData-size","" + lookUpModelLinkedList.size());
        return lookUpModelLinkedList;
    }

    public static LinkedList<LookUpModel> setUpProductsData(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        LinkedList<LookUpModel> lookUpModelLinkedList = new LinkedList<>();
        //Products
        for (int i = 0; i < hashMaps.size(); i++) {
            lookUpModelLinkedList.add(new LookUpModel(hashMaps.get(i).get(DBModels.enumProduct.StoreID.toString()),
                    hashMaps.get(i).get(DBModels.enumProduct.ProductID.toString()),
                    hashMaps.get(i).get(DBModels.enumProduct.ProductDesc.toString()),
                    hashMaps.get(i).get(DBModels.enumProduct.SellingPrice.toString()),
                    hashMaps.get(i).get(DBModels.enumProduct.RebatePoints.toString()),
                    hashMaps.get(i).get(DBModels.enumProduct.SharePoints.toString()),
//                    hashMap.get(i).get(DBModels.enumProduct.Picture.toString()),
                    "",
                    hashMaps.get(i).get(DBModels.enumProduct.NoOfServing.toString()),
                    hashMaps.get(i).get(DBModels.enumProduct.IsActive.toString())
            ));
        }

        Log.d("setUpProductsData-size","" + lookUpModelLinkedList.size());
        return lookUpModelLinkedList;
    }

    public static LinkedList<LookUpModel> setUpProductItemData(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        LinkedList<LookUpModel> lookUpModelLinkedList = new LinkedList<>();
        //ProductItems
        for (int i = 0; i < hashMaps.size(); i++) {
            lookUpModelLinkedList.add(new LookUpModel(hashMaps.get(i).get(DBModels.enumProductItem.StoreID.toString()),
                    hashMaps.get(i).get(DBModels.enumProductItem.ProductID.toString()),
                    hashMaps.get(i).get(DBModels.enumProductItem.ItemID.toString()),
                    hashMaps.get(i).get(DBModels.enumProductItem.QtyPerServe.toString()),
                    hashMaps.get(i).get(DBModels.enumProductItem.IsActive.toString())
            ));
        }

        Log.d("setUpProductItem-size","" + lookUpModelLinkedList.size());
        return lookUpModelLinkedList;
    }

    public static LinkedList<LookUpModel> setUpItemType(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        LinkedList<LookUpModel> lookUpModelLinkedList = new LinkedList<>();
        //ProductItems
        for (int i = 0; i < hashMaps.size(); i++) {
            lookUpModelLinkedList.add(new LookUpModel(hashMaps.get(i).get(DBModels.enumItemTypeLookUp.Type.toString()),
                    hashMaps.get(i).get(DBModels.enumItemTypeLookUp.Description.toString()),
                    hashMaps.get(i).get(DBModels.enumItemTypeLookUp.IsActive.toString())
            ));
        }

        Log.d("setUpItemType-size","" + lookUpModelLinkedList.size());
        return lookUpModelLinkedList;
    }

    public static LinkedList<LookUpModel> setUpProCat(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        LinkedList<LookUpModel> lookUpModelLinkedList = new LinkedList<>();
        //ProductCategory
        for (int i = 0; i < hashMaps.size(); i++) {
            lookUpModelLinkedList.add(new LookUpModel(hashMaps.get(i).get(DBModels.enumProductCategory.CatID.toString()),
                    hashMaps.get(i).get(DBModels.enumProductCategory.CatDesc.toString()),
                    hashMaps.get(i).get(DBModels.enumProductCategory.IsActive.toString()),
                    ""
            ));
        }

        Log.d("setUpProCat-size","" + lookUpModelLinkedList.size());
        return lookUpModelLinkedList;
    }

    public static LinkedList<StoreModel> setUpStoreStocksData(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        //Stocks
        for (int i = 0; i < hashMaps.size(); i++) {
            storeModelLinkedList.add(new StoreModel(hashMaps.get(i).get(DBModels.enumStocks.StoreID.toString()),
                    hashMaps.get(i).get(DBModels.enumStocks.ItemID.toString()),
                    hashMaps.get(i).get(DBModels.enumStocks.Quantity.toString()),
                    hashMaps.get(i).get(DBModels.enumStocks.Remarks.toString()),
                    hashMaps.get(i).get(DBModels.enumStocks.IsActive.toString())
            ));
        }

        Log.d("setUpStoreStocks-size","" + storeModelLinkedList.size());
        return storeModelLinkedList;
    }

    public static LinkedList<StoreModel> setUpStoreStocksRegData(Context context) {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        //Items
        storeModelLinkedList.add(new StoreModel("1", "1","1","100",Helper.getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "2","2","100",Helper.getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "3","3","100",Helper.getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "4","4","5",Helper.getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "5","5","5",Helper.getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "6","6","5",Helper.getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "7","7","5",Helper.getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "8","8","5",Helper.getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "9","9","5",Helper.getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "10","10","5",Helper.getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "11","11","5",Helper.getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "12","12","5",Helper.getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "13","13","5",Helper.getDateWithFormat(),"", "Y"));

        Log.d("setUpStocksReg-size","" + storeModelLinkedList.size());
        return storeModelLinkedList;
    }

    public static LinkedList<StoreModel> setUpStoreUPointsHistoryData(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        //Stocks
        for (int i = 0; i < hashMaps.size(); i++) {
            storeModelLinkedList.add(new StoreModel(hashMaps.get(i).get(DBModels.enumStoreUPointsHistory.PointsRef.toString()),
                    hashMaps.get(i).get(DBModels.enumStoreUPointsHistory.Amount.toString()),
                    hashMaps.get(i).get(DBModels.enumStoreUPointsHistory.UPoints.toString()),
                    hashMaps.get(i).get(DBModels.enumStoreUPointsHistory.DateOfTransaction.toString()),
                    hashMaps.get(i).get(DBModels.enumStoreUPointsHistory.OldTotalPoints.toString()),
                    hashMaps.get(i).get(DBModels.enumStoreUPointsHistory.OldTotalRemainingPoints.toString()),
                    hashMaps.get(i).get(DBModels.enumStoreUPointsHistory.NewTotalPoints.toString()),
                    hashMaps.get(i).get(DBModels.enumStoreUPointsHistory.NewTotalRemainingPoints.toString()),
                    hashMaps.get(i).get(DBModels.enumStoreUPointsHistory.IsActive.toString())
            ));
        }

        Log.d("setUPointsHistory-size","" + storeModelLinkedList.size());
        return storeModelLinkedList;
    }

    public static CustomerModel setUpCustomerUplineData(LinkedHashMap<String, String> hashMaps) {
        CustomerModel customerModel;
        //Customer Upline
        customerModel = new CustomerModel(hashMaps.get(DBModels.enumCustomerUpline.CustID.toString()),
                hashMaps.get(DBModels.enumCustomerUpline.CustIDUp1.toString()),
                hashMaps.get(DBModels.enumCustomerUpline.CustIDUp2.toString()),
                hashMaps.get(DBModels.enumCustomerUpline.CustIDUp3.toString()),
                hashMaps.get(DBModels.enumCustomerUpline.CustIDUp4.toString()));

        Log.d("setUpCustUpline-size","" + customerModel.getCustomerUpID1());
        return customerModel;
    }
}

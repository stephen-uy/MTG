package com.android.stephen.mtgpos.database;

/**
 * Created by Stephen Uy on 1/27/2017.
 */

public class DBModels {

    public static String recID = "RecID";
    // Table names
    public enum enumTables{ Store, StoreUser, StoreUPoints, StoreUPointsHistory,
        Purchased, PurchasedProductDetails, PurchasedItemDetails,
        StocksRegistration, Stocks,
        Customer, CustomerUpline, CustomerUPoints, CustomerReceivedUPoints, CustomerPicture, CustomerPictureHistory,
        StoreItem, ItemTypeLookUp,
        Product, ProductItem, ProductCategory,
        AuditLogs,}

    //Item tables
    public enum enumItem { StoreID, ItemID, ItemDesc, QtyPerItemType, ItemType, IsActive, DateLastUpdated}
    public enum enumItemTypeLookUp { Type, Description, IsActive}

    //Product tables
    public enum enumProduct{ StoreID, ProductID, ProductDesc, SellingPrice, RebatePoints, SharePoints, Picture, IsActive, NoOfServing}
    public enum enumProductItem { StoreID, ProductID, ItemID, QtyPerServe, IsActive}
    public enum enumProductCategory { CatID, CatDesc, IsActive}

    //Store tables
    public enum enumStore {StoreID, StoreName, Street, City, Municipality, Province, ZipCode, Region, Island,
        Longitude, Latitude, Email, Mobile, Phone, Remarks, IsActive, IsMTGStore, AppType, DateReg, RegBy, DateLastUpdated}
    public enum enumStoreUser {StoreID, UserName, SaltPass, Pass, FName, MName, LName, Level,
        RegBy, DateReg, Remarks, IsActive, IsOwner, DateLastUpdated}
    public enum enumStoreUPoints {StoreID, TotalPoints, RemainingPoints, TotalWDPoints}
    public enum enumStoreUPointsHistory {StoreID, PointsRef, ModeOfPayment, Amount, UPoints, DateOfTransaction,
        DateReg, RegBy, Remarks, OldTotalRemainingPoints, OldTotalPoints, NewTotalRemainingPoints, NewTotalPoints,
        IsAddToStore, TranType, IsActive}
    public enum enumPurchased {StoreID, PurRef, PurDate, TotalAmt, TotalQty, TotalRPoints, TotalSPoints, RegBy,
        DateReg, Remarks, CustID, IsActive}
    public enum enumPurchasedProductDetails {StoreID, PurRef, ProductID, Quantity, RPoints, SPoints, AmtPurchased, IsActive}
    public enum enumPurchasedItemDetails{StoreID, PurRef, ProductID, ItemID}

    //Stocks tables
    public enum enumStocksRegistration {StoreID, StocksRef, ItemID, Quantity, DateReg, RegBy, Remarks, IsActive}
    public enum enumStocks {StoreID, ItemID, Quantity, Remarks, IsActive}

    //Customer tables
    public enum enumCustomer {StoreID, CustID, UpCustID, Fname, Mname, Lname, BirthDate,
        Email, Mobile, Remarks, IsStoreOwner, IsActive, Password}
    public enum enumCustomerPicture{CustID, Picture, DateCaptured}
//    public enum enumCustomerPictureHistory{RegBy, Picture, DateCaptured}
    public enum enumCustomerUpline {CustID, CustIDUp1, CustIDUp2, CustIDUp3, CustIDUp4}
    public enum enumCustomerUPoints {CustID, TotalPoints, RemainingPoints, WithdrawPoints}
    public enum enumCustomerReceivedUPoints {StoreID, PurRef, CustID, FromCustID, Points, DateReceived, PointsType, IsUploaded}

    //User & Logs tables
    public enum enumAuditLogs {StoreID, AuditDate, AuditDesc, Module, PurRef, RegBy}

    //Customer tables
    public static String createTableCustomerReceivedUPoints = "CREATE TABLE " + enumTables.CustomerReceivedUPoints.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumCustomerReceivedUPoints.StoreID.toString() + " TEXT,"
            + enumCustomerReceivedUPoints.PurRef.toString() + " TEXT,"
            + enumCustomerReceivedUPoints.CustID.toString() + " TEXT,"
            + enumCustomerReceivedUPoints.FromCustID.toString() + " TEXT,"
            + enumCustomerReceivedUPoints.Points.toString() + " TEXT,"
            + enumCustomerReceivedUPoints.DateReceived.toString() + " TEXT,"
            + enumCustomerReceivedUPoints.PointsType.toString() + " TEXT,"
            + enumCustomerReceivedUPoints.IsUploaded.toString() + " TEXT" + ")";

    public static String createTableCustomerUPoints = "CREATE TABLE " + enumTables.CustomerUPoints.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + enumCustomerUPoints.StoreID.toString() + " TEXT,"
            + enumCustomerUPoints.CustID.toString() + " TEXT,"
            + enumCustomerUPoints.TotalPoints.toString() + " TEXT,"
            + enumCustomerUPoints.RemainingPoints.toString() + " TEXT,"
            + enumCustomerUPoints.WithdrawPoints.toString() + " TEXT" + ")";

    public static String createTableCustomerUpline = "CREATE TABLE " + enumTables.CustomerUpline.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + enumCustomerUpline.StoreID.toString() + " TEXT,"
            + enumCustomerUpline.CustID.toString() + " TEXT,"
            + enumCustomerUpline.CustIDUp1.toString() + " TEXT,"
            + enumCustomerUpline.CustIDUp2.toString() + " TEXT,"
            + enumCustomerUpline.CustIDUp3.toString() + " TEXT,"
            + enumCustomerUpline.CustIDUp4.toString() + " TEXT" + ")";

    public static String createTableCustomerPicture = "CREATE TABLE " + enumTables.CustomerPicture.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumCustomerPicture.CustID.toString() + " TEXT,"
            + enumCustomerPicture.Picture.toString() + " TEXT,"
            + enumCustomerPicture.DateCaptured.toString() + " TEXT" + ")";

//    public static String createTableCustomerPictureHistory = "CREATE TABLE " + enumTables.CustomerPictureHistory.toString() + "("
//            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + enumCustomerPictureHistory.RegBy.toString() + " TEXT,"
//            + enumCustomerPictureHistory.Picture.toString() + " TEXT,"
//            + enumCustomerPictureHistory.DateCaptured.toString() + " TEXT" + ")";

    public static String createTableCustomer = "CREATE TABLE " + enumTables.Customer.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumCustomer.StoreID.toString() + " TEXT,"
            + enumCustomer.CustID.toString() + " TEXT,"
            + enumCustomer.UpCustID.toString() + " TEXT,"
            + enumCustomer.Fname.toString() + " TEXT,"
            + enumCustomer.Mname.toString() + " TEXT,"
            + enumCustomer.Lname.toString() + " TEXT,"
            + enumCustomer.BirthDate.toString() + " TEXT,"
            + enumCustomer.Email.toString() + " TEXT,"
            + enumCustomer.Mobile.toString() + " TEXT,"
            + enumCustomer.Remarks.toString() + " TEXT,"
            + enumCustomer.Password.toString() + " TEXT,"
            + enumCustomer.IsStoreOwner.toString() + " TEXT,"
            + enumCustomer.IsActive.toString() + " TEXT" + ")";

    //Store tables
    public static String createTablePurchasedItemDetails = "CREATE TABLE " + enumTables.PurchasedItemDetails.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumPurchasedItemDetails.StoreID.toString() + " TEXT,"
            + enumPurchasedItemDetails.PurRef.toString() + " TEXT,"
            + enumPurchasedItemDetails.ProductID.toString() + " TEXT,"
            + enumPurchasedItemDetails.ItemID.toString() + " TEXT" + ")";

    public static String createTablePurchasedProductDetails = "CREATE TABLE " + enumTables.PurchasedProductDetails.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumPurchasedProductDetails.StoreID.toString() + " TEXT,"
            + enumPurchasedProductDetails.PurRef.toString() + " TEXT,"
            + enumPurchasedProductDetails.ProductID.toString() + " TEXT,"
            + enumPurchasedProductDetails.Quantity.toString() + " TEXT,"
            + enumPurchasedProductDetails.RPoints.toString() + " TEXT,"
            + enumPurchasedProductDetails.SPoints.toString() + " TEXT,"
            + enumPurchasedProductDetails.AmtPurchased.toString() + " TEXT,"
            + enumPurchasedProductDetails.IsActive.toString() + " TEXT" + ")";

    public static String createTablePurchased = "CREATE TABLE " + enumTables.Purchased.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumPurchased.StoreID.toString() + " TEXT,"
            + enumPurchased.PurRef.toString() + " TEXT,"
            + enumPurchased.PurDate.toString() + " TEXT,"
            + enumPurchased.TotalAmt.toString() + " TEXT,"
            + enumPurchased.TotalQty.toString() + " TEXT,"
            + enumPurchased.TotalRPoints.toString() + " TEXT,"
            + enumPurchased.TotalSPoints.toString() + " TEXT,"
            + enumPurchased.RegBy.toString() + " TEXT,"
            + enumPurchased.Remarks.toString() + " TEXT,"
            + enumPurchased.CustID.toString() + " TEXT,"
            + enumPurchased.IsActive.toString() + " TEXT,"
            + enumPurchased.DateReg.toString() + " TEXT" + ")";

    public static String createTableAuditLogs = "CREATE TABLE " + enumTables.AuditLogs.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumAuditLogs.StoreID.toString() + " TEXT,"
            + enumAuditLogs.AuditDate.toString() + " TEXT,"
            + enumAuditLogs.AuditDesc.toString() + " TEXT,"
            + enumAuditLogs.Module.toString() + " TEXT,"
            + enumAuditLogs.PurRef.toString() + " TEXT,"
            + enumAuditLogs.RegBy.toString() + " TEXT" + ")";

    public static String createTableStocks = "CREATE TABLE " + enumTables.Stocks.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStocks.StoreID.toString() + " TEXT,"
            + enumStocks.ItemID.toString() + " TEXT,"
            + enumStocks.Quantity.toString() + " TEXT,"
            + enumStocks.Remarks.toString() + " TEXT,"
            + enumStocks.IsActive.toString() + " TEXT" + ")";

    public static String createTableStocksRegistration = "CREATE TABLE " + enumTables.StocksRegistration.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStocksRegistration.StoreID.toString() + " TEXT,"
            + enumStocksRegistration.StocksRef.toString() + " TEXT,"
            + enumStocksRegistration.ItemID.toString() + " TEXT,"
            + enumStocksRegistration.Quantity.toString() + " TEXT,"
            + enumStocksRegistration.DateReg.toString() + " TEXT,"
            + enumStocksRegistration.RegBy.toString() + " TEXT,"
            + enumStocksRegistration.Remarks.toString() + " TEXT,"
            + enumStocksRegistration.IsActive.toString() + " TEXT" + ")";

    public static String createTableStoreUPointsHistory = "CREATE TABLE " + enumTables.StoreUPointsHistory.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStoreUPointsHistory.StoreID.toString() + " TEXT,"
            + enumStoreUPointsHistory.PointsRef.toString() + " TEXT,"
            + enumStoreUPointsHistory.ModeOfPayment.toString() + " TEXT,"
            + enumStoreUPointsHistory.Amount.toString() + " TEXT,"
            + enumStoreUPointsHistory.UPoints.toString() + " TEXT,"
            + enumStoreUPointsHistory.DateOfTransaction.toString() + " TEXT,"
            + enumStoreUPointsHistory.DateReg.toString() + " TEXT,"
            + enumStoreUPointsHistory.RegBy.toString() + " TEXT,"
            + enumStoreUPointsHistory.Remarks.toString() + " TEXT,"
            + enumStoreUPointsHistory.OldTotalRemainingPoints.toString() + " TEXT,"
            + enumStoreUPointsHistory.OldTotalPoints.toString() + " TEXT,"
            + enumStoreUPointsHistory.NewTotalRemainingPoints.toString() + " TEXT,"
            + enumStoreUPointsHistory.NewTotalPoints.toString() + " TEXT,"
            + enumStoreUPointsHistory.IsAddToStore.toString() + " TEXT,"
            + enumStoreUPointsHistory.TranType.toString() + " TEXT,"
//            + enumStoreUPointsHistory.DateLastUpdated.toString() + " TEXT,"
            + enumStoreUPointsHistory.IsActive.toString() + " TEXT" + ")";

    public static String createTableStoreUPoints = "CREATE TABLE " + enumTables.StoreUPoints.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStoreUPoints.StoreID.toString() + " TEXT,"
            + enumStoreUPoints.TotalPoints.toString() + " TEXT,"
            + enumStoreUPoints.RemainingPoints.toString() + " TEXT,"
            + enumStoreUPoints.TotalWDPoints.toString() + " TEXT" + ")";

    public static String createTableStoreUser = "CREATE TABLE " + enumTables.StoreUser.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStoreUser.StoreID.toString() + " TEXT,"
            + enumStoreUser.UserName.toString() + " TEXT,"
            + enumStoreUser.SaltPass.toString() + " TEXT,"
            + enumStoreUser.Pass.toString() + " TEXT,"
            + enumStoreUser.FName.toString() + " TEXT,"
            + enumStoreUser.MName.toString() + " TEXT,"
            + enumStoreUser.LName.toString() + " TEXT,"
            + enumStoreUser.Level.toString() + " TEXT,"
            + enumStoreUser.RegBy.toString() + " TEXT,"
            + enumStoreUser.DateReg.toString() + " TEXT,"
            + enumStoreUser.Remarks.toString() + " TEXT,"
            + enumStoreUser.IsActive.toString() + " TEXT,"
            + enumStoreUser.IsOwner.toString() + " TEXT,"
            + enumStoreUser.DateLastUpdated.toString() + " TEXT" + ")";

    public static String createTableStore = "CREATE TABLE " + enumTables.Store.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStore.StoreID.toString() + " TEXT,"
            + enumStore.StoreName.toString() + " TEXT,"
            + enumStore.Street.toString() + " TEXT,"
            + enumStore.City.toString() + " TEXT,"
            + enumStore.Municipality.toString() + " TEXT,"
            + enumStore.Province.toString() + " TEXT,"
            + enumStore.ZipCode.toString() + " TEXT,"
            + enumStore.Region.toString() + " TEXT,"
            + enumStore.Island.toString() + " TEXT,"
            + enumStore.Longitude.toString() + " TEXT,"
            + enumStore.Latitude.toString() + " TEXT,"
            + enumStore.Email.toString() + " TEXT,"
            + enumStore.Mobile.toString() + " TEXT,"
            + enumStore.Phone.toString() + " TEXT,"
            + enumStore.Remarks.toString() + " TEXT,"
            + enumStore.IsActive.toString() + " TEXT,"
            + enumStore.IsMTGStore.toString() + " TEXT,"
            + enumStore.AppType.toString() + " TEXT,"
            + enumStore.DateReg.toString() + " TEXT,"
            + enumStore.RegBy.toString() + " TEXT,"
            + enumStore.DateLastUpdated.toString() + " TEXT" + ")";

    public static String createTableItem = "CREATE TABLE " + enumTables.StoreItem.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumItem.StoreID.toString() + " TEXT,"
            + enumItem.ItemID.toString() + " TEXT,"
            + enumItem.ItemDesc.toString() + " TEXT,"
            + enumItem.QtyPerItemType.toString() + " TEXT,"
            + enumItem.ItemType.toString() + " TEXT,"
            + enumItem.IsActive.toString() + " TEXT,"
            + enumItem.DateLastUpdated.toString() + " TEXT" + ")";

    public static String createTableItemTypeLookUp = "CREATE TABLE " + enumTables.ItemTypeLookUp.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumItemTypeLookUp.Type.toString() + " TEXT,"
            + enumItemTypeLookUp.Description.toString() + " TEXT,"
            + enumItemTypeLookUp.IsActive.toString() + " TEXT" + ")";

    public static String createTableProduct = "CREATE TABLE " + enumTables.Product.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumProduct.StoreID.toString() + " TEXT,"
            + enumProduct.ProductID.toString() + " TEXT,"
            + enumProduct.ProductDesc.toString() + " TEXT,"
            + enumProduct.SellingPrice.toString() + " TEXT,"
            + enumProduct.RebatePoints.toString() + " TEXT,"
            + enumProduct.SharePoints.toString() + " TEXT,"
            + enumProduct.Picture.toString() + " TEXT,"
            + enumProduct.NoOfServing.toString() + " TEXT,"
            + enumProduct.IsActive.toString() + " TEXT" + ")";

    public static String createTableProductItem = "CREATE TABLE " + enumTables.ProductItem.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumProductItem.StoreID.toString() + " TEXT,"
            + enumProductItem.ProductID.toString() + " TEXT,"
            + enumProductItem.ItemID.toString() + " TEXT,"
            + enumProductItem.QtyPerServe.toString() + " TEXT,"
            + enumProductItem.IsActive.toString() + " TEXT" + ")";

    public static String createTableProductCategory = "CREATE TABLE " + enumTables.ProductCategory.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumProductCategory.CatID.toString() + " TEXT,"
            + enumProductCategory.CatDesc.toString() + " TEXT,"
            + enumProductCategory.IsActive.toString() + " TEXT" + ")";

    //For upgrade version of sqlite db
//    public static final String alterTableCategory = "ALTER TABLE " + enumTables.Category.toString() + " ADD COLUMN " + enumCategory.CatDesc.toString() + " TEXT";
}

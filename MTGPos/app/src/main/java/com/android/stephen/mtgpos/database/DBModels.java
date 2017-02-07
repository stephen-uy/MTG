package com.android.stephen.mtgpos.database;

/**
 * Created by Stephen Uy on 1/27/2017.
 */

public class DBModels {

    public static String recID = "RecID";
    // Table names
    public enum enumTables{ Product, Store, StoreUser, StoreAccount, StorePointsHistory, StoreStocksReg, StoreStocks, StoreLogs,
        StorePurchased, StorePurchasedDetails, Customer, CustomerUpline, CustomerPoints, CustomerBonusPoints,
        CustomerPicture, CustomerPictureHistory, Item, ProductItem,
        StoreStocksRegMTG, StoreStocksMTG, StorePurchasedMTG, StorePurchasedDetailsMTG}

    //Look Up tables
    public enum enumProduct{ StoreID, ProductID, ProductDesc, SellingPrice, RebatePoints, SharePoints, Picture, IsActive}
    public enum enumItem { StoreID, ItemID, ItemDesc, QtyPerPack, IsActive}
    public enum enumProductItem { StoreID, ProductID, ItemID, QtyPerServe, IsActive}

    //Store tables
    public enum enumStore {StoreID, FranName, Street, City, Municipality, Province, ZipCode,
        Longitude, Latitude, Email, Mobile, Phone, Remarks, IsActive, IsUploaded}
    public enum enumStoreUser {StoreID, UserName, Password, Fname, Mname, Lname, Level,
        RegBy, RegDate, Remarks, IsActive, IsUploaded}
    public enum enumStoreAccount{StoreID, TotalPoints, RemainingPoints, TotalWDPoints}
    public enum enumStorePointsHistory{StoreID, PointsRef, ModeOfPayment, Amount, Points, DateDeposited,
        ReceivedDate, Remarks, IsActive, IsUploaded}
    public enum enumStoreStocksReg {StoreID, StocksRef, ItemID, Quantity, DateReg, Remarks, IsActive}
    public enum enumStoreStocks {StoreID, ItemID, Quantity, Remarks, IsActive, IsUploaded}
    public enum enumStoreLogs {StoreID, UserName, AuditDate, AuditDesc, StocksRef, PurRef, CustID, IsUploaded}
    public enum enumStorePurchased {StoreID, PurRef, PurDate, TotalAmt, TotalQty, RebatePoints, SharePoints, RegBy,
        Remarks, IsActive, IsUploaded}
    public enum enumStorePurchasedDetails {StoreID, PurRef, ProductID, Quantity, IsActive, IsUploaded}

    //Customer tables
    public enum enumCustomer {StoreID, CustID, UpCustID, Fname, Mname, Lname, BirthDate,
        Email, Mobile, Remarks, IsStoreOwner, IsActive, IsUploaded, Password}
    public enum enumCustomerPicture{CustID, Picture, DateCaptured}
    public enum enumCustomerPictureHistory{CustID, Picture, DateCaptured}
    public enum enumCustomerUpline {StoreID, CustID, CustIDUp1, CustIDUp2, CustIDUp3, CustIDUp4, IsUploaded}
    public enum enumCustomerPoints {StoreID, CustID, TotalPoints, RemainingPoints, WithdrawPoints, IsUploaded}
    public enum enumCustomerBonusPoints {StoreID, PurRef, CustID, FromCustID, Points, DateReceived, PointsType, IsUploaded}

    //Customer tables
    public static String createTableCustomerBonusPoints = "CREATE TABLE " + enumTables.CustomerBonusPoints.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumCustomerBonusPoints.StoreID.toString() + " TEXT,"
            + enumCustomerBonusPoints.PurRef.toString() + " TEXT,"
            + enumCustomerBonusPoints.CustID.toString() + " TEXT,"
            + enumCustomerBonusPoints.FromCustID.toString() + " TEXT,"
            + enumCustomerBonusPoints.Points.toString() + " TEXT,"
            + enumCustomerBonusPoints.DateReceived.toString() + " TEXT,"
            + enumCustomerBonusPoints.PointsType.toString() + " TEXT,"
            + enumCustomerBonusPoints.IsUploaded.toString() + " TEXT" + ")";

    public static String createTableCustomerPoints = "CREATE TABLE " + enumTables.CustomerPoints.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumCustomerPoints.StoreID.toString() + " TEXT,"
            + enumCustomerPoints.CustID.toString() + " TEXT,"
            + enumCustomerPoints.TotalPoints.toString() + " TEXT,"
            + enumCustomerPoints.RemainingPoints.toString() + " TEXT,"
            + enumCustomerPoints.WithdrawPoints.toString() + " TEXT,"
            + enumCustomerPoints.IsUploaded.toString() + " TEXT" + ")";

    public static String createTableCustomerUpline = "CREATE TABLE " + enumTables.CustomerUpline.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumCustomerUpline.StoreID.toString() + " TEXT,"
            + enumCustomerUpline.CustID.toString() + " TEXT,"
            + enumCustomerUpline.CustIDUp1.toString() + " TEXT,"
            + enumCustomerUpline.CustIDUp2.toString() + " TEXT,"
            + enumCustomerUpline.CustIDUp3.toString() + " TEXT,"
            + enumCustomerUpline.CustIDUp4.toString() + " TEXT,"
            + enumCustomerUpline.IsUploaded.toString() + " TEXT" + ")";

    public static String createTableCustomerPicture = "CREATE TABLE " + enumTables.CustomerPicture.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumCustomerPicture.CustID.toString() + " TEXT,"
            + enumCustomerPicture.Picture.toString() + " TEXT,"
            + enumCustomerPicture.DateCaptured.toString() + " TEXT" + ")";

    public static String createTableCustomerPictureHistory = "CREATE TABLE " + enumTables.CustomerPictureHistory.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumCustomerPictureHistory.CustID.toString() + " TEXT,"
            + enumCustomerPictureHistory.Picture.toString() + " TEXT,"
            + enumCustomerPictureHistory.DateCaptured.toString() + " TEXT" + ")";

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
            + enumCustomer.IsActive.toString() + " TEXT,"
            + enumCustomer.IsUploaded.toString() + " TEXT" + ")";

    //Store tables
    public static String createTableStorePurchasedDetails = "CREATE TABLE " + enumTables.StorePurchasedDetails.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStorePurchasedDetails.StoreID.toString() + " TEXT,"
            + enumStorePurchasedDetails.PurRef.toString() + " TEXT,"
            + enumStorePurchasedDetails.ProductID.toString() + " TEXT,"
            + enumStorePurchasedDetails.Quantity.toString() + " TEXT,"
            + enumStorePurchasedDetails.IsActive.toString() + " TEXT,"
            + enumStorePurchasedDetails.IsUploaded.toString() + " TEXT" + ")";

    public static String createTableStorePurchased = "CREATE TABLE " + enumTables.StorePurchased.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStorePurchased.StoreID.toString() + " TEXT,"
            + enumStorePurchased.PurRef.toString() + " TEXT,"
            + enumStorePurchased.PurDate.toString() + " TEXT,"
            + enumStorePurchased.TotalAmt.toString() + " TEXT,"
            + enumStorePurchased.TotalQty.toString() + " TEXT,"
            + enumStorePurchased.RebatePoints.toString() + " TEXT,"
            + enumStorePurchased.SharePoints.toString() + " TEXT,"
            + enumStorePurchased.RegBy.toString() + " TEXT,"
            + enumStorePurchased.Remarks.toString() + " TEXT,"
            + enumStorePurchased.IsActive.toString() + " TEXT,"
            + enumStorePurchased.IsUploaded.toString() + " TEXT" + ")";

    public static String createTableStoreLogs = "CREATE TABLE " + enumTables.StoreLogs.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStoreLogs.StoreID.toString() + " TEXT,"
            + enumStoreLogs.UserName.toString() + " TEXT,"
            + enumStoreLogs.AuditDate.toString() + " TEXT,"
            + enumStoreLogs.AuditDesc.toString() + " TEXT,"
            + enumStoreLogs.StocksRef.toString() + " TEXT,"
            + enumStoreLogs.PurRef.toString() + " TEXT,"
            + enumStoreLogs.CustID.toString() + " TEXT,"
            + enumStoreLogs.IsUploaded.toString() + " TEXT" + ")";

    public static String createTableStoreStocks = "CREATE TABLE " + enumTables.StoreStocks.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStoreStocks.StoreID.toString() + " TEXT,"
            + enumStoreStocks.ItemID.toString() + " TEXT,"
            + enumStoreStocks.Quantity.toString() + " TEXT,"
            + enumStoreStocks.Remarks.toString() + " TEXT,"
            + enumStoreStocks.IsActive.toString() + " TEXT,"
            + enumStoreStocks.IsUploaded.toString() + " TEXT" + ")";

    public static String createTableStoreStocksReg = "CREATE TABLE " + enumTables.StoreStocksReg.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStoreStocksReg.StoreID.toString() + " TEXT,"
            + enumStoreStocksReg.StocksRef.toString() + " TEXT,"
            + enumStoreStocksReg.ItemID.toString() + " TEXT,"
            + enumStoreStocksReg.Quantity.toString() + " TEXT,"
            + enumStoreStocksReg.DateReg.toString() + " TEXT,"
            + enumStoreStocksReg.Remarks.toString() + " TEXT,"
            + enumStoreStocksReg.IsActive.toString() + " TEXT" + ")";

    public static String createTableStorePointsHistory = "CREATE TABLE " + enumTables.StorePointsHistory.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStorePointsHistory.StoreID.toString() + " TEXT,"
            + enumStorePointsHistory.PointsRef.toString() + " TEXT,"
            + enumStorePointsHistory.ModeOfPayment.toString() + " TEXT,"
            + enumStorePointsHistory.Amount.toString() + " TEXT,"
            + enumStorePointsHistory.Points.toString() + " TEXT,"
            + enumStorePointsHistory.DateDeposited.toString() + " TEXT,"
            + enumStorePointsHistory.ReceivedDate.toString() + " TEXT,"
            + enumStorePointsHistory.Remarks.toString() + " TEXT,"
            + enumStorePointsHistory.IsActive.toString() + " TEXT,"
            + enumStorePointsHistory.IsUploaded.toString() + " TEXT" + ")";

    public static String createTableStoreAccount= "CREATE TABLE " + enumTables.StoreAccount.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStoreAccount.StoreID.toString() + " TEXT,"
            + enumStoreAccount.TotalPoints.toString() + " TEXT,"
            + enumStoreAccount.RemainingPoints.toString() + " TEXT,"
            + enumStoreAccount.TotalWDPoints.toString() + " TEXT" + ")";

    public static String createTableStoreUser = "CREATE TABLE " + enumTables.StoreUser.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStoreUser.StoreID.toString() + " TEXT,"
            + enumStoreUser.UserName.toString() + " TEXT,"
            + enumStoreUser.Password.toString() + " TEXT,"
            + enumStoreUser.Fname.toString() + " TEXT,"
            + enumStoreUser.Mname.toString() + " TEXT,"
            + enumStoreUser.Lname.toString() + " TEXT,"
            + enumStoreUser.Level.toString() + " TEXT,"
            + enumStoreUser.RegBy.toString() + " TEXT,"
            + enumStoreUser.RegDate.toString() + " TEXT,"
            + enumStoreUser.Remarks.toString() + " TEXT,"
            + enumStoreUser.IsActive.toString() + " TEXT,"
            + enumStoreUser.IsUploaded.toString() + " TEXT" + ")";

    public static String createTableStore = "CREATE TABLE " + enumTables.Store.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumStore.StoreID.toString() + " TEXT,"
            + enumStore.FranName.toString() + " TEXT,"
            + enumStore.Street.toString() + " TEXT,"
            + enumStore.City.toString() + " TEXT,"
            + enumStore.Municipality.toString() + " TEXT,"
            + enumStore.Province.toString() + " TEXT,"
            + enumStore.ZipCode.toString() + " TEXT,"
            + enumStore.Longitude.toString() + " TEXT,"
            + enumStore.Latitude.toString() + " TEXT,"
            + enumStore.Email.toString() + " TEXT,"
            + enumStore.Mobile.toString() + " TEXT,"
            + enumStore.Phone.toString() + " TEXT,"
            + enumStore.Remarks.toString() + " TEXT,"
            + enumStore.IsActive.toString() + " TEXT,"
            + enumStore.IsUploaded.toString() + " TEXT" + ")";

    //Look Up tables
    public static String createTableProduct = "CREATE TABLE " + enumTables.Product.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumProduct.StoreID.toString() + " TEXT,"
            + enumProduct.ProductID.toString() + " TEXT,"
            + enumProduct.ProductDesc.toString() + " TEXT,"
            + enumProduct.SellingPrice.toString() + " TEXT,"
            + enumProduct.RebatePoints.toString() + " TEXT,"
            + enumProduct.SharePoints.toString() + " TEXT,"
            + enumProduct.Picture.toString() + " TEXT,"
            + enumProduct.IsActive.toString() + " TEXT" + ")";

    public static String createTableItem = "CREATE TABLE " + enumTables.Item.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumItem.StoreID.toString() + " TEXT,"
            + enumItem.ItemID.toString() + " TEXT,"
            + enumItem.ItemDesc.toString() + " TEXT,"
            + enumItem.QtyPerPack.toString() + " TEXT,"
            + enumItem.IsActive.toString() + " TEXT" + ")";

    public static String createTableProductItem = "CREATE TABLE " + enumTables.ProductItem.toString() + "("
            + recID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + enumProductItem.StoreID.toString() + " TEXT,"
            + enumProductItem.ProductID.toString() + " TEXT,"
            + enumProductItem.ItemID.toString() + " TEXT,"
            + enumProductItem.QtyPerServe.toString() + " TEXT,"
            + enumProductItem.IsActive.toString() + " TEXT" + ")";

    //For upgrade version of sqlite db
//    public static final String alterTableCategory = "ALTER TABLE " + enumTables.Category.toString() + " ADD COLUMN " + enumCategory.CatDesc.toString() + " TEXT";
}

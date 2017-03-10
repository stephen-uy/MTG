package com.android.stephen.mtgpos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Stephen Uy on 1/26/2017.
 */

public class SQLiteDBHandler extends SQLiteOpenHelper{
    private static String dbName = "MTGDB";
    private static int dbVersion = 1;

    public SQLiteDBHandler(Context context) {
        super(context, dbName, null, dbVersion);
    }

    public static SQLiteDBHandler getInstance(Context context){
        return new SQLiteDBHandler(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBModels.createTableProduct);
        sqLiteDatabase.execSQL(DBModels.createTableProductItem);
        sqLiteDatabase.execSQL(DBModels.createTableProductCategory);

        sqLiteDatabase.execSQL(DBModels.createTableItem);
        sqLiteDatabase.execSQL(DBModels.createTableItemTypeLookUp);

        sqLiteDatabase.execSQL(DBModels.createTableStocks);
        sqLiteDatabase.execSQL(DBModels.createTableStocksRegistration);

        sqLiteDatabase.execSQL(DBModels.createTableStore);
        sqLiteDatabase.execSQL(DBModels.createTableStoreUser);
        sqLiteDatabase.execSQL(DBModels.createTableStoreUPoints);
        sqLiteDatabase.execSQL(DBModels.createTableStoreUPointsHistory);
        sqLiteDatabase.execSQL(DBModels.createTablePurchased);
        sqLiteDatabase.execSQL(DBModels.createTablePurchasedProductDetails);
        sqLiteDatabase.execSQL(DBModels.createTablePurchasedItemDetails);

        sqLiteDatabase.execSQL(DBModels.createTableAuditLogs);

        sqLiteDatabase.execSQL(DBModels.createTableCustomer);
        sqLiteDatabase.execSQL(DBModels.createTableCustomerPicture);
//        sqLiteDatabase.execSQL(DBModels.createTableCustomerPictureHistory);
        sqLiteDatabase.execSQL(DBModels.createTableCustomerUPoints);
        sqLiteDatabase.execSQL(DBModels.createTableCustomerReceivedUPoints);
        sqLiteDatabase.execSQL(DBModels.createTableCustomerUpline);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("DBModel","OnUpgrade");
        Log.d("oldVersion",oldVersion+"");
        Log.d("newVersion",newVersion+"");
        if (newVersion == oldVersion + 1) {
//            sqLiteDatabase.execSQL(DBModels.alterTableCategory);
        }
    }
}

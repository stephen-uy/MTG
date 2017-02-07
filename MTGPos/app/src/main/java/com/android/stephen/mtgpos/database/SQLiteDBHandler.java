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
        sqLiteDatabase.execSQL(DBModels.createTableItem);
        sqLiteDatabase.execSQL(DBModels.createTableProductItem);

        sqLiteDatabase.execSQL(DBModels.createTableStore);
        sqLiteDatabase.execSQL(DBModels.createTableStoreUser);
        sqLiteDatabase.execSQL(DBModels.createTableStoreAccount);
        sqLiteDatabase.execSQL(DBModels.createTableStorePointsHistory);
        sqLiteDatabase.execSQL(DBModels.createTableStoreStocksReg);
        sqLiteDatabase.execSQL(DBModels.createTableStoreStocks);
        sqLiteDatabase.execSQL(DBModels.createTableStoreLogs);
        sqLiteDatabase.execSQL(DBModels.createTableStorePurchased);
        sqLiteDatabase.execSQL(DBModels.createTableStorePurchasedDetails);

        sqLiteDatabase.execSQL(DBModels.createTableCustomer);
        sqLiteDatabase.execSQL(DBModels.createTableCustomerPicture);
        sqLiteDatabase.execSQL(DBModels.createTableCustomerPictureHistory);
        sqLiteDatabase.execSQL(DBModels.createTableCustomerPoints);
        sqLiteDatabase.execSQL(DBModels.createTableCustomerBonusPoints);
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

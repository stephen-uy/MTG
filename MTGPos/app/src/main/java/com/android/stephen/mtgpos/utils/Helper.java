package com.android.stephen.mtgpos.utils;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.database.CustomerHandler;
import com.android.stephen.mtgpos.database.DBModels;
import com.android.stephen.mtgpos.database.StoreHandler;
import com.android.stephen.mtgpos.model.CustomerModel;
import com.android.stephen.mtgpos.model.StoreModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Stephen Uy on 1/11/2017.
 */

public class Helper {
    private static View layout = null;
    private static android.app.AlertDialog alertDialog;

    public static void addFragment(AppCompatActivity activity, Fragment fragment, @IdRes int id, String tag){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    public static void replaceFragment(AppCompatActivity activity, Fragment fragment, @IdRes int id, String tag){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    public static String formatDate(String format, String newFormat, String date) throws ParseException {
        String newDate;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        SimpleDateFormat newSdf = new SimpleDateFormat(newFormat, Locale.US);
        Date d1 = sdf.parse(date);
        newDate = newSdf.format(d1);
        Log.d("formatDate", newDate);
        return newDate;
    }

    public static void showDialog(final Context activity, String title, String message, View.OnClickListener onClickListener){
        // Get the layout inflater
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.custom_dialog, null);

        TextView tvMessage = (TextView) layout.findViewById(R.id.tvMessage);
        Button btnCancel = (Button) layout.findViewById(R.id.btnCancel);
        Button btnOK = (Button) layout.findViewById(R.id.btnOK);

        tvMessage.setText(message);
        btnCancel.setVisibility(View.GONE);
        btnOK.setOnClickListener(onClickListener);
//        btnOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.cancel();
//            }
//        });

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setView(layout);
        builder.create();
        builder.setCancelable(false);
        alertDialog = builder.show();
    }

    public static void showDialog(final Context activity, String title, String message){
        // Get the layout inflater
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.custom_dialog, null);

        TextView tvMessage = (TextView) layout.findViewById(R.id.tvMessage);
        Button btnCancel = (Button) layout.findViewById(R.id.btnCancel);
        Button btnOK = (Button) layout.findViewById(R.id.btnOK);

        tvMessage.setText(message);
        btnCancel.setVisibility(View.GONE);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setView(layout);
        builder.create();
        builder.setCancelable(false);
        alertDialog = builder.show();
    }

    public static void alertDialogCancel(){
        alertDialog.cancel();
    }

    public static String generateCustomerID(CustomerModel custModel) {
        String custID = "";
        try {
            custID = custModel.getFirstName().substring(0, 1) +
                    custModel.getMiddleName().substring(0, 1) +
                    custModel.getLastName().substring(0, 1) +
                    formatDate("MMM dd, yyyy", "MMddyy", custModel.getBirthDate()) +
                    custModel.getStoreID();
        } catch (ParseException e) {
            Log.d("generateCustomerID", e.getMessage());
        }
        Log.d("generateCustomerID", custID);
        return custID.toUpperCase();
    }

    public static void insertFranchiseeData(Context context) {
        StoreHandler storeHandler = new StoreHandler(context);
        StoreModel storeModel = new StoreModel();
        int rowCount = storeHandler.getRowCounts(DBModels.enumTables.Store.toString());
        if (rowCount == 0) {
            storeModel.setStoreID("2");
            storeModel.setFranchiseeName("MTG 2");
            storeModel.setStreet("Macapagal St.");
            storeModel.setCity("Las Pinas City");
            storeModel.setMunicipality("Las Pinas City");
            storeModel.setProvince("Metro Manila");
            storeModel.setZipCode("1747");
            storeModel.setLongitude("");
            storeModel.setLatitude("");
            storeModel.setEmailAddress("");
            storeModel.setMobileNumber("");
            storeModel.setPhone("");
            storeModel.setRemarks("");
            storeModel.setIsActive("Y");
            storeModel.setIsUploaded("N");
            storeHandler.addStore(storeModel);

            storeModel.setUserName("stephen");
            storeModel.setPassword("123456");
            storeModel.setFirstName("Stephen");
            storeModel.setMiddleName("Mallorca");
            storeModel.setLastName("Uy");
            storeModel.setLevel("Adm");
            storeModel.setRegBy("stephen");
            storeModel.setRegDate("02/01/2017 01:30:35");
            storeHandler.addStoreUser(storeModel);

//            storeModel.setUserName("sai");
//            storeModel.setPassword("123456");
//            storeModel.setFirstName("Sayre");
//            storeModel.setMiddleName("R");
//            storeModel.setLastName("Collado");
//            storeModel.setLevel("Adm");
//            storeModel.setRegBy("stephen");
//            storeModel.setRegDate("02/02/2017 01:40:35");
//            storeHandler.addStoreUser(storeModel);
        }
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
}

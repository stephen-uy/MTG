package com.android.stephen.mtgpos.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.database.CustomerHandler;
import com.android.stephen.mtgpos.database.DBModels;
import com.android.stephen.mtgpos.database.LookUpHandler;
import com.android.stephen.mtgpos.database.StoreHandler;
import com.android.stephen.mtgpos.model.CustomerModel;
import com.android.stephen.mtgpos.model.LookUpModel;
import com.android.stephen.mtgpos.model.StoreModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Stephen Uy on 1/11/2017.
 */

public class Helper {
    private static View layout = null;
    private static android.app.AlertDialog alertDialog;
    public static String storageDir = Environment.getExternalStorageDirectory() + "/MTGPhotos/";

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

    public static String getDateWithFormat(){
        Calendar calendar = Calendar.getInstance();
        String date;
        SimpleDateFormat sdf = new SimpleDateFormat(GlobalVariables.dateFormat, Locale.US);
        date = sdf.format(calendar.getTime());
        return date;
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

    public static SSLSocketFactory getSSLSocketFactory(Context context) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = context.getResources().getAssets().open(GlobalVariables.CERTIFICATE_PATH); // this cert file stored in \app\src\main\res\raw folder path

        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        return sslContext.getSocketFactory();
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

    public static String generateStocksRef(Context context) {
        String stocksRef;
        stocksRef = StoreHandler.getInstance(context).getStoreStocksRef();
        if (!TextUtils.isEmpty(stocksRef)) {
            stocksRef = String.valueOf(Integer.parseInt(stocksRef) + 1);
            Log.d("generateStocksRef", stocksRef);
        } else {
            stocksRef = "1";
        }
        return stocksRef;
    }

    public static void insertStoreData(Context context) {
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

            storeModel.setUserName("sai");
            storeModel.setPassword("123456");
            storeModel.setFirstName("Sayre");
            storeModel.setMiddleName("R");
            storeModel.setLastName("Collado");
            storeModel.setLevel("Adm");
            storeModel.setRegBy("stephen");
            storeModel.setRegDate("02/02/2017 01:40:35");
            storeHandler.addStoreUser(storeModel);
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

    public static void insertItemData(Context context){
        LinkedList<LookUpModel> lookUpModelLinkedList;
        LookUpHandler lookUpHandler = new LookUpHandler(context);
        lookUpModelLinkedList = setUpItemsData();
        int rowCount = lookUpHandler.getRowCounts(DBModels.enumTables.Item.toString());
        if (rowCount == 0) {
            for(LookUpModel model : lookUpModelLinkedList){
                lookUpHandler.addItem(model);
            }
        }
    }

    public static void insertProductData(Context context){
        LinkedList<LookUpModel> lookUpModelLinkedList;
        LookUpHandler lookUpHandler = new LookUpHandler(context);
        lookUpModelLinkedList = setUpProductsData();
        int rowCount = lookUpHandler.getRowCounts(DBModels.enumTables.Product.toString());
        if (rowCount == 0) {
            for(LookUpModel model : lookUpModelLinkedList){
                lookUpHandler.addProduct(model);
            }
        }
    }

    public static void insertProductItemData(Context context){
        LinkedList<LookUpModel> lookUpModelLinkedList;
        LookUpHandler lookUpHandler = new LookUpHandler(context);
        lookUpModelLinkedList = setUpProductItemData();
        int rowCount = lookUpHandler.getRowCounts(DBModels.enumTables.ProductItem.toString());
        if (rowCount == 0) {
            for(LookUpModel model : lookUpModelLinkedList){
                lookUpHandler.addProductItem(model);
            }
        }
    }

    public static void insertStoreStocksData(Context context){
        LinkedList<StoreModel> storeModelLinkedList;
        StoreHandler storeHandler = new StoreHandler(context);
        storeModelLinkedList = setUpStoreStocksData();
        int rowCount = storeHandler.getRowCounts(DBModels.enumTables.StoreStocks.toString());
        if (rowCount == 0) {
            for(StoreModel model : storeModelLinkedList){
                storeHandler.addStoreStocks(model);
            }
        }
    }

    public static void insertStoreStocksRegData(Context context){
        LinkedList<StoreModel> storeModelLinkedList;
        StoreHandler storeHandler = new StoreHandler(context);
        storeModelLinkedList = setUpStoreStocksRegData(context);
        int rowCount = storeHandler.getRowCounts(DBModels.enumTables.StoreStocksReg.toString());
        if (rowCount == 0) {
            for(StoreModel model : storeModelLinkedList){
                storeHandler.addStoreStocksReg(model);
            }
        }
    }

    private static LinkedList<LookUpModel> setUpItemsData() {
        LinkedList<LookUpModel> lookUpModelLinkedList = new LinkedList<>();
        //Items
        lookUpModelLinkedList.add(new LookUpModel("1","1","22oz with cup","1","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","2","16oz with lip","1","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","3","220cc solo","1","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","4","Choco Chips","45","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","5","Choco Mallows","14","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","6","Choco Sticks","160","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","7","Choco Coated Roll","90","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","8","Mini Choco Chips","120","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","9","Hiro Choco","45","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","10","Fries","1000","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","11","Pretzel","8","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","12","Siomai","1","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","13","Jolly Biscuit","45","Y"));

        Log.d("setUpItemsData-size","" + lookUpModelLinkedList.size());
        return lookUpModelLinkedList;
    }

    private static LinkedList<LookUpModel> setUpProductsData() {
        LinkedList<LookUpModel> lookUpModelLinkedList = new LinkedList<>();
        //Items
        lookUpModelLinkedList.add(new LookUpModel("1","1","Signature Combo","40","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","2","Juice","15","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","3","Fries Solo","18","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","4","Choco Chips Solo(6pcs)","15","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","5","Jolly Biscuit Solo(6pcs)","15","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","6","Choco Sticks Solo(15pcs)","15","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","7","Choco Coated Roll Solo(12pcs)","15","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","8","Fries with drinks","30","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","9","Choco Chip with drinks(6pcs)","25","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","10","Jolly Biscuit with drinks(6pcs)","25","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","11","Choco Sticks with drinks(15pcs)","25","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","12","Choco Coated Roll Solo(12pcs) with drinks","25","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","13","Combo 1 Solo(1 Mallows, 2 Choco chips, 5 Choco sticks)","15","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","14","Combo 2 Solo(1 Mallows, 3 Choco coated roll, 5 Mini chips)","15","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","15","Combo 3 Solo(1 Mallows, 2 Jolly Biscuit, 3 Hiro Choco)","15","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","16","Combo 1 with drinks(1 Mallows, 2 Choco chips, 5 Choco sticks)","25","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","17","Combo 2 with drinks(1 Mallows, 3 Choco coated roll, 5 Mini chips)","25","","","","Y"));
        lookUpModelLinkedList.add(new LookUpModel("1","18","Combo 3 with drinks(1 Mallows, 2 Jolly Biscuit, 3 Hiro Choco)","25","","","","Y"));

        Log.d("setUpProductsData-size","" + lookUpModelLinkedList.size());
        return lookUpModelLinkedList;
    }

    private static LinkedList<LookUpModel> setUpProductItemData() {
        LinkedList<LookUpModel> lookUpModelLinkedList = new LinkedList<>();
        //Items
        lookUpModelLinkedList.add(new LookUpModel("1","1","11","3","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","1","10","50","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","1","4","2","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","1","12","2","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","1","1","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","2","2","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","3","10","75","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","3","3","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","4","4","6","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","4","3","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","5","13","6","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","5","3","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","6","6","15","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","6","3","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","7","7","12","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","7","3","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","8","10","75","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","8","1","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","9","4","6","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","9","1","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","10","13","6","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","10","1","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","11","6","15","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","11","1","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","12","7","12","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","12","1","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","13","5","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","13","4","2","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","13","6","5","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","13","3","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","14","5","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","14","7","3","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","14","8","5","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","14","3","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","15","5","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","15","13","2","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","15","9","3","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","15","3","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","16","5","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","16","4","2","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","16","6","5","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","16","1","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","17","5","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","17","7","3","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","17","8","5","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","17","1","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","18","5","1","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","18","13","2","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","18","9","3","Y",""));
        lookUpModelLinkedList.add(new LookUpModel("1","18","1","1","Y",""));

        Log.d("setUpProductItem-size","" + lookUpModelLinkedList.size());
        return lookUpModelLinkedList;
    }

    private static LinkedList<StoreModel> setUpStoreStocksData() {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        //Items
        storeModelLinkedList.add(new StoreModel("1","1","100","","Y", "N"));
        storeModelLinkedList.add(new StoreModel("1","2","100","","Y", "N"));
        storeModelLinkedList.add(new StoreModel("1","3","100","","Y", "N"));
        storeModelLinkedList.add(new StoreModel("1","4","225","","Y", "N"));
        storeModelLinkedList.add(new StoreModel("1","5","70","","Y", "N"));
        storeModelLinkedList.add(new StoreModel("1","6","800","","Y", "N"));
        storeModelLinkedList.add(new StoreModel("1","7","450","","Y", "N"));
        storeModelLinkedList.add(new StoreModel("1","8","600","","Y", "N"));
        storeModelLinkedList.add(new StoreModel("1","9","225","","Y", "N"));
        storeModelLinkedList.add(new StoreModel("1","10","5000","","Y", "N"));
        storeModelLinkedList.add(new StoreModel("1","11","40","","Y", "N"));
        storeModelLinkedList.add(new StoreModel("1","12","5","","Y", "N"));
        storeModelLinkedList.add(new StoreModel("1","13","225","","Y", "N"));

        Log.d("setUpStoreStocks-size","" + storeModelLinkedList.size());
        return storeModelLinkedList;
    }

    private static LinkedList<StoreModel> setUpStoreStocksRegData(Context context) {
        LinkedList<StoreModel> storeModelLinkedList = new LinkedList<>();
        //Items
        storeModelLinkedList.add(new StoreModel("1", "1","1","100",getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "2","2","100",getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "3","3","100",getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "4","4","5",getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "5","5","5",getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "6","6","5",getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "7","7","5",getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "8","8","5",getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "9","9","5",getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "10","10","5",getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "11","11","5",getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "12","12","5",getDateWithFormat(),"", "Y"));
        storeModelLinkedList.add(new StoreModel("1", "13","13","5",getDateWithFormat(),"", "Y"));

        Log.d("setUpStocksReg-size","" + storeModelLinkedList.size());
        return storeModelLinkedList;
    }

    public static void captureImage(Activity activity) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takePictureIntent, GlobalVariables.ADD_PHOTO);
        }
    }

    public static File createImageFile(String fileName) throws IOException {
        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdir();

        File image = new File(storageDir + fileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        String mCurrentPhotoPath = image.getAbsolutePath();
        Log.i("createImageFile()", "photo path = " + mCurrentPhotoPath);
        return image;
    }

    public static void captureImageAndSave(Activity activity, File photoFile) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {

            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                activity.startActivityForResult(takePictureIntent, GlobalVariables.ADD_PHOTO);
            }
        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        quality = 100;
        image = Bitmap.createScaledBitmap(image, 100, 100, false);
        compressFormat = Bitmap.CompressFormat.PNG;
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        Bitmap bitmap;
        byte[] decodedBytes = Base64.decode(input, 0);
        bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        return bitmap;
    }

    public static Bitmap rotateImage(Bitmap bitmap, int orientation){
        Bitmap bm;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);
//            matrix.postRotate(90);
            bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return bm;
        } else {
            return bitmap;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 2;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    public static Bitmap setPic(String mCurrentPhotoPath, View mImageView) {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        return  bitmap;
    }
}

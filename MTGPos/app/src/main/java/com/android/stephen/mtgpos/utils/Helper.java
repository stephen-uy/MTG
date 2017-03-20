package com.android.stephen.mtgpos.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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
import java.net.NetworkInterface;
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
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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

    public static String getDateTimeWithFormat(){
        Calendar calendar = Calendar.getInstance();
        String date;
        SimpleDateFormat sdf = new SimpleDateFormat(GlobalVariables.dateTimeFormat, Locale.US);
        date = sdf.format(calendar.getTime());
        return date;
    }

    public static String getDateTimeWithFormat(String format){
        Calendar calendar = Calendar.getInstance();
        String date;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
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

    public static void showDialog(final Context activity, String title, String message,
                                  View.OnClickListener okClickListener, View.OnClickListener cancelClickListener){
        // Get the layout inflater
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.custom_dialog, null);

        TextView tvMessage = (TextView) layout.findViewById(R.id.tvMessage);
        Button btnCancel = (Button) layout.findViewById(R.id.btnCancel);
        Button btnOK = (Button) layout.findViewById(R.id.btnOK);

        tvMessage.setText(message);
        btnOK.setText(activity.getString(R.string.btn_yes));
        btnCancel.setText(activity.getString(R.string.btn_no));
        btnOK.setOnClickListener(okClickListener);
        btnCancel.setOnClickListener(cancelClickListener);

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
            custID = custModel.getFirstName().substring(0, 2) +
                    custModel.getMiddleName().substring(0, 2) +
                    custModel.getLastName().substring(0, 2) +
                    formatDate("MMM dd, yyyy", "MMddyy", custModel.getBirthDate());
        } catch (ParseException e) {
            Log.d("generateCustomerID", e.getMessage());
        }
        Log.d("generateCustomerID", custID);
        return custID.toUpperCase();
    }

    public static String generateStocksRef(Context context, String storeID) {
        String stocksRef = getDateTimeWithFormat("ddhhmmss") + storeID;
        Log.d("generateStocksRef", stocksRef);
        return stocksRef;
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

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
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

//    public static String getMacAddress(Context context){
//        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = manager.getConnectionInfo();
//        String address = info.getMacAddress().replace(":","");
//        return address;
//    }

    public static String getMacAddress(Context context) {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString().replace(":","");
            }
        } catch (Exception ex) {
        }
        return "020000000000";
    }

    public static ProgressDialog buildProgressSpinnerDialog(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static String computeAmount(String amount, String oldAmount, String qtyPerItemType){
        int sum = 0;
        try{
            int amt = Integer.parseInt(amount);
            int oldAmt = Integer.parseInt(oldAmount);
            int multiplier = Integer.parseInt(qtyPerItemType);
            sum = (amt * multiplier) + oldAmt;
        } catch (Exception e){

        }
        return String.valueOf(sum);
    }

    public static boolean checkResponse(Context context, LinkedHashMap<String, String> hashMap){
        if (!TextUtils.isEmpty(hashMap.get("response_code"))){
            if (hashMap.get("response_code").isEmpty())
                return true;
            else {
                String message = hashMap.get("response_message");
                Helper.showDialog(context, "", message);
                return false;
            }
        } else
            return true;
    }
}

package com.android.stephen.mtgpos.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.callback.VolleyCallback;
import com.android.stephen.mtgpos.database.CustomerHandler;
import com.android.stephen.mtgpos.database.DBModels;
import com.android.stephen.mtgpos.databinding.ActivityCustomerRegistrationBinding;
import com.android.stephen.mtgpos.model.CustomerModel;
import com.android.stephen.mtgpos.utils.APIHelper;
import com.android.stephen.mtgpos.utils.CustomerAPI;
import com.android.stephen.mtgpos.utils.GlobalVariables;
import com.android.stephen.mtgpos.utils.Helper;
import com.android.stephen.mtgpos.utils.Parameters;
import com.android.stephen.mtgpos.utils.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CustomerRegistration extends AppCompatActivity implements View.OnClickListener, VolleyCallback {

    ActivityCustomerRegistrationBinding activityCustomerRegistrationBinding;
    Calendar calendar;
    CustomerModel customerModel;
    CustomerModel customerUplineModel;
    CustomerHandler customerHandler;
    private String format = "MMM dd, yyyy";
    private String storeID = "";
    private String pictureByteString = "";
    private String picFileName = "";
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_WRITE_EXTERNAL = 2;
    private static final int REQUEST_PERMISSION_SETTING = 1;
    private CustomerAPI customerAPI;
    private ProgressDialog progressDialog;
    private String referralCode;
    private AlertDialog alertDialog;

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpData();
        setUpToolBar();
        setUpViews();
    }

    private void setUpData() {
        activityCustomerRegistrationBinding = DataBindingUtil.setContentView(this, R.layout.activity_customer_registration);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            storeID = bundle.getString("storeID");
        }
    }

    private void setUpToolBar() {
        activityCustomerRegistrationBinding.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        setSupportActionBar(activityCustomerRegistrationBinding.toolbar);
        activityCustomerRegistrationBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpViews() {
        activityCustomerRegistrationBinding.rlRegistrationForm.setEnabled(false);
        activityCustomerRegistrationBinding.btnDate.setOnClickListener(this);
        activityCustomerRegistrationBinding.imgPicture.setOnClickListener(this);

        mayRequestCamera();
        showReferralDialog();
    }

    private void showReferralDialog() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_referral_dialog, null);

        final EditText etReferralCode = (EditText) layout.findViewById(R.id.etReferralCode);
        Button btnCancel = (Button) layout.findViewById(R.id.btnCancel);
        Button btnOK = (Button) layout.findViewById(R.id.btnSubmit);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etReferralCode.getText().toString())){
                    getCustomerUpline(etReferralCode.getText().toString());
                } else {
                    Helper.showDialog(CustomerRegistration.this, "", getString(R.string.error_referral_code));
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setCancelable(false);
        builder.create();
        alertDialog = builder.show();
    }

    private void getCustomerUpline(String custUpID){
        alertDialog.cancel();
        progressDialog = Helper.buildProgressSpinnerDialog(this, getString(R.string.loading));
        progressDialog.show();
        referralCode = custUpID;
        customerAPI = new CustomerAPI(this);
        customerAPI.getUpline(this, custUpID);
    }

    private void generateCustomerID(String custTempID){
        progressDialog = Helper.buildProgressSpinnerDialog(this, getString(R.string.loading));
        progressDialog.show();
        customerAPI = new CustomerAPI(this);
        customerAPI.generateCustomerID(this, custTempID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item;
        item = menu.findItem(R.id.action_save);
        item.setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            save();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void save() {
        customerHandler = new CustomerHandler(this);
        customerModel = new CustomerModel();
        String message = "";

        if (!TextUtils.isEmpty(pictureByteString)){
            customerModel.setPicture(pictureByteString);
        } else {
            message += getResources().getString(R.string.error_picture);
        }

        if (!TextUtils.isEmpty(activityCustomerRegistrationBinding.etFirstName.getText())) {
            customerModel.setFirstName(activityCustomerRegistrationBinding.etFirstName.getText().toString());
        } else {
            message += getResources().getString(R.string.error_first_name);
        }

        if (!TextUtils.isEmpty(activityCustomerRegistrationBinding.etMiddleName.getText())) {
            customerModel.setMiddleName(activityCustomerRegistrationBinding.etMiddleName.getText().toString());
        } else {
            message += getResources().getString(R.string.error_middle_name);
        }

        if (!TextUtils.isEmpty(activityCustomerRegistrationBinding.etLastName.getText())) {
            customerModel.setLastName(activityCustomerRegistrationBinding.etLastName.getText().toString());
        } else {
            message += getResources().getString(R.string.error_last_name);
        }

//        if (!TextUtils.isEmpty(activityCustomerRegistrationBinding.etPassword.getText())) {
//            customerModel.setPassword(activityCustomerRegistrationBinding.etPassword.getText().toString());
//        } else {
//            message += getResources().getString(R.string.error_password);
//        }
        if (!TextUtils.isEmpty(activityCustomerRegistrationBinding.etEmailAddress.getText())) {
            customerModel.setEmailAddress(activityCustomerRegistrationBinding.etEmailAddress.getText().toString());
        } else {
            message += getResources().getString(R.string.error_email);
        }

        if (!TextUtils.isEmpty(activityCustomerRegistrationBinding.etBirthdate.getText())) {
            customerModel.setBirthDate(activityCustomerRegistrationBinding.etBirthdate.getText().toString());
        } else {
            message += getResources().getString(R.string.error_birth_date);
        }

        if (TextUtils.isEmpty(message)) {
            if (!TextUtils.isEmpty(activityCustomerRegistrationBinding.etReferral.getText())) {
//                if (checkIfValidUpCustID(activityCustomerRegistrationBinding.etReferral.getText().toString().toUpperCase()))
                    customerModel.setUpCustomerID(activityCustomerRegistrationBinding.etReferral.getText().toString().toUpperCase());
//                else
//                    Helper.showDialog(this, "", "Invalid referral code.");
//            } else {
//                customerModel.setUpCustomerID(getOwnerCustomerID());
            }
//            customerModel.setEmailAddress(activityCustomerRegistrationBinding.etEmailAddress.getText().toString());
            customerModel.setMobileNumber(activityCustomerRegistrationBinding.etMobileNumber.getText().toString());
            customerModel.setRemarks(activityCustomerRegistrationBinding.etRemarks.getText().toString());

            customerModel.setStoreID(storeID);
            customerModel.setIsStoreOwner("N");
            customerModel.setIsActive("");
            customerModel.setIsUploaded("N");
            generateCustomerID(Helper.generateCustomerID(customerModel));
        } else {
            Helper.showDialog(this, "", message);
        }
    }

    private void updateCustomerUpLine(CustomerModel custModel){
        CustomerModel custUpline = custModel;
        if (!TextUtils.isEmpty(custUpline.getCustomerUpID1())) {
            custUpline.setCustomerUpID1(custUpline.getCustomerUpID1());
        }
        if (!TextUtils.isEmpty(custUpline.getCustomerUpID2())) {
            custUpline.setCustomerUpID2(custUpline.getCustomerUpID2());
        }
        if (!TextUtils.isEmpty(custUpline.getCustomerUpID3())) {
            custUpline.setCustomerUpID3(custUpline.getCustomerUpID3());
        }
        if (!TextUtils.isEmpty(custUpline.getCustomerUpID3())) {
            custUpline.setCustomerUpID3(custUpline.getCustomerUpID3());
        }

        Log.d("customerUplines",custModel.getCustomerUpID1() + "," + custModel.getCustomerUpID2() + ","
                + custModel.getCustomerUpID3() + "," + custModel.getCustomerUpID4());
        CustomerHandler.getInstance(this).addCustomerUpline(custUpline);
    }

    private String getOwnerCustomerID() {
        return CustomerHandler.getInstance(this).getOwnerCustomerID();
    }

    private void clearData() {
        activityCustomerRegistrationBinding.etReferral.getText().clear();
        activityCustomerRegistrationBinding.etFirstName.getText().clear();
        activityCustomerRegistrationBinding.etMiddleName.getText().clear();
        activityCustomerRegistrationBinding.etLastName.getText().clear();
        activityCustomerRegistrationBinding.etBirthdate.getText().clear();
        activityCustomerRegistrationBinding.etEmailAddress.getText().clear();
        activityCustomerRegistrationBinding.etMobileNumber.getText().clear();
        activityCustomerRegistrationBinding.etRemarks.getText().clear();
        activityCustomerRegistrationBinding.tiReferral.requestFocus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnDate:
                showDate();
                break;
            case R.id.imgPicture:
                if (mayRequestCamera()) {
                    Helper.captureImage(this);
//                    File file = null;
//                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                    String imageFileName = "JPEG_" + timeStamp + "_";
//                    try {
//                        file = Helper.createImageFile(imageFileName);
//                        picFileName = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("/")+1);
//                        Helper.captureImageAndSave(this, file);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                } else
                    Helper.showDialog(this, "", getString(R.string.camera_permission, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        }
                    }));
                break;
            default:
                break;
        }
    }

    private void showDate() {
        calendar = Calendar.getInstance();
        new DatePickerDialog(this, onDateSetListener, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        activityCustomerRegistrationBinding.etBirthdate.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case GlobalVariables.ADD_PHOTO:
                if (resultCode == RESULT_OK){
//                    activityCustomerRegistrationBinding.imgPicture.setImageBitmap(Helper.setPic(picFileName, activityCustomerRegistrationBinding.imgPicture));
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        Uri selectedImage = data.getData();
                        String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
                        Cursor cur = getContentResolver().query(selectedImage, orientationColumn, null, null, null);
                        int orientation = -1;
                        if (cur != null && cur.moveToFirst()) {
                            orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
                        }
                        cur.close();
                        if (!Helper.isTablet(this))
                            imageBitmap = Helper.rotateImage(imageBitmap, orientation);
                    }
                    pictureByteString = Helper.encodeToBase64(imageBitmap, Bitmap.CompressFormat.PNG,100);
                    activityCustomerRegistrationBinding.imgPicture.setImageBitmap(Helper.decodeBase64(pictureByteString));
                }
                break;
        }
    }

    private boolean mayRequestCamera() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(CAMERA)) {
            Snackbar.make(activityCustomerRegistrationBinding.scrollCustomer, R.string.permission_rationale_camera, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
                        }
                    });
        } else {
            requestPermissions(new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                showReferralDialog();
            }
        }
    }

    private String setCustomerEnRegValues(CustomerModel customerModel) {
        JSONObject customerENReg = new JSONObject();
        try {
            customerENReg.put(DBModels.enumCustomer.StoreID.toString(), storeID);
            customerENReg.put(DBModels.enumCustomer.CustID.toString(), customerModel.getCustomerID());
            customerENReg.put(DBModels.enumCustomer.UpCustID.toString(), customerModel.getUpCustomerID());
            customerENReg.put(DBModels.enumCustomer.Fname.toString(), customerModel.getFirstName());
            customerENReg.put(DBModels.enumCustomer.Mname.toString(), customerModel.getMiddleName());
            customerENReg.put(DBModels.enumCustomer.Lname.toString(), customerModel.getLastName());
            customerENReg.put(DBModels.enumCustomer.BirthDate.toString(), customerModel.getBirthDate());
            customerENReg.put(DBModels.enumCustomer.Email.toString(), customerModel.getEmailAddress());
            customerENReg.put(DBModels.enumCustomer.IsActive.toString(), "");
            customerENReg.put(DBModels.enumCustomer.IsStoreOwner.toString(), "N");
            customerENReg.put(DBModels.enumCustomer.Mobile.toString(), customerModel.getMobileNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return customerENReg.toString();
    }

    private String setCustomerEnPicValues(CustomerModel customerModel) {
        JSONObject customerENPic = new JSONObject();
        try {
            customerENPic.put(DBModels.enumCustomerPicture.Picture.toString(), customerModel.getPicture());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return customerENPic.toString();
    }

    private String setCustomerEnUplineValues(CustomerModel customerModel) {
        JSONObject customerENUpline = new JSONObject();
        try {
            customerENUpline.put(DBModels.enumCustomerUpline.CustIDUp1.toString(), customerModel.getCustomerUpID1());
            customerENUpline.put(DBModels.enumCustomerUpline.CustIDUp2.toString(), customerModel.getCustomerUpID2());
            customerENUpline.put(DBModels.enumCustomerUpline.CustIDUp3.toString(), customerModel.getCustomerUpID3());
            customerENUpline.put(DBModels.enumCustomerUpline.CustIDUp4.toString(), customerModel.getCustomerUpID4());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return customerENUpline.toString();
    }

    private void saveOnlineCustomer() {
        progressDialog = Helper.buildProgressSpinnerDialog(this, getString(R.string.loading));
        progressDialog.show();
        customerAPI = new CustomerAPI(this);
        customerAPI.saveNewCustomer(this, setCustomerEnRegValues(customerModel)
                ,setCustomerEnPicValues(customerModel), setCustomerEnUplineValues(customerUplineModel));
    }

    private void saveOfflineCustomer(){
        if (customerHandler.addCustomer(customerModel)) {
            updateCustomerUpLine(customerUplineModel);
            customerModel.setDateCaptured(Helper.getDateTimeWithFormat());
            if (customerHandler.addCustomerPicture(customerModel)) {
//                    customerHandler.addCustomerPictureHistory(customerModel);
                Helper.showDialog(this, "", getResources().getString(R.string.success_add_customer)
                        + "\n\n Customer's Promo Code: " + customerModel.getCustomerID(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Helper.alertDialogCancel();
                        clearData();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        } else{
            Helper.showDialog(this, "", getResources().getString(R.string.failed_to_add));
        }
    }

    private void parseCustUpline(LinkedHashMap<String, String> hashMaps) {
        progressDialog.dismiss();
        if (Helper.checkResponse(this, hashMaps)) {
            customerUplineModel = APIHelper.setUpCustomerUplineData(hashMaps);

            if (!TextUtils.isEmpty(customerUplineModel.getCustomerUpID1()) && !customerUplineModel.getCustomerUpID1().equalsIgnoreCase("null")) {
                activityCustomerRegistrationBinding.rlRegistrationForm.setEnabled(true);
                activityCustomerRegistrationBinding.etReferral.setText(referralCode);
                activityCustomerRegistrationBinding.etReferral.setEnabled(false);
            } else {
                referralCode = "";
                Helper.showDialog(this, "", getString(R.string.error_referral_invalid), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Helper.alertDialogCancel();
                        activityCustomerRegistrationBinding.rlRegistrationForm.setEnabled(true);
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Helper.alertDialogCancel();
                        showReferralDialog();
                    }
                });
//                Toast.makeText(this, getString(R.string.error_referral_invalid), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseGenerateCustomerID(LinkedHashMap<String, String> response) {
        progressDialog.dismiss();
        if (Helper.checkResponse(this, response)) {
            if (!TextUtils.isEmpty(response.get(Parameters.response.getValue()))) {
                customerModel.setCustomerID(response.get(Parameters.response.getValue()));
//                saveOfflineCustomer();
                saveOnlineCustomer();
            }
        }
    }

    private void parseSaveNewCustomer(LinkedHashMap<String, String> response) {
        progressDialog.dismiss();
        if (Helper.checkResponse(this, response)) {
            if (response.get(Parameters.response.getValue()).equalsIgnoreCase("true")) {
                Helper.showDialog(this, "", getResources().getString(R.string.success_add_customer)
                        + "\n\n Customer's Promo Code: " + customerModel.getCustomerID(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Helper.alertDialogCancel();
                        clearData();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            } else {
                Helper.showDialog(this, "", getString(R.string.failed_to_add));
            }
        }
    }

    @Override
    public void onResponseReady(Task task, LinkedHashMap<String, String> response) {
        switch (task){
            case GET_UPLINE:
                parseCustUpline(response);
                break;
            case GENERATE_CUSTOMER_ID:
                parseGenerateCustomerID(response);
                break;
            case SAVE_NEW_CUSTOMER:
                parseSaveNewCustomer(response);
                break;
        }
    }

    @Override
    public void onResponseReady(Task task, LinkedList<LinkedHashMap<String, String>> response) {

    }
}

package com.android.stephen.mtgpos.activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.tool.reflection.SdkUtil;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.database.CustomerHandler;
import com.android.stephen.mtgpos.databinding.ActivityCustomerRegistrationBinding;
import com.android.stephen.mtgpos.model.CustomerModel;
import com.android.stephen.mtgpos.utils.GlobalVariables;
import com.android.stephen.mtgpos.utils.Helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CustomerRegistration extends AppCompatActivity implements View.OnClickListener {

    ActivityCustomerRegistrationBinding activityCustomerRegistrationBinding;
    Calendar calendar;
    CustomerModel customerModel;
    CustomerHandler customerHandler;
    private String format = "MMM dd, yyyy";
    private String storeID = "";
    private String pictureByteString = "";
    private String picFileName = "";
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_WRITE_EXTERNAL = 2;
    private static final int REQUEST_PERMISSION_SETTING = 1;

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
        activityCustomerRegistrationBinding.btnDate.setOnClickListener(this);
        activityCustomerRegistrationBinding.imgPicture.setOnClickListener(this);

        mayRequestCamera();
        showReferralDialog();
    }

    private void showReferralDialog() {

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

        if (!TextUtils.isEmpty(activityCustomerRegistrationBinding.etPassword.getText())) {
            customerModel.setPassword(activityCustomerRegistrationBinding.etPassword.getText().toString());
        } else {
            message += getResources().getString(R.string.error_password);
        }

        if (!TextUtils.isEmpty(activityCustomerRegistrationBinding.etBirthdate.getText())) {
            customerModel.setBirthDate(activityCustomerRegistrationBinding.etBirthdate.getText().toString());
        } else {
            message += getResources().getString(R.string.error_birth_date);
        }

        if (TextUtils.isEmpty(message)) {
            if (!TextUtils.isEmpty(activityCustomerRegistrationBinding.etReferral.getText())) {
                if (checkIfValidUpCustID(activityCustomerRegistrationBinding.etReferral.getText().toString().toUpperCase()))
                    customerModel.setUpCustomerID(activityCustomerRegistrationBinding.etReferral.getText().toString().toUpperCase());
//                else
//                    Helper.showDialog(this, "", "Invalid referral code.");
            } else {
                customerModel.setUpCustomerID(getOwnerCustomerID());
            }
            customerModel.setEmailAddress(activityCustomerRegistrationBinding.etEmailAddress.getText().toString());
            customerModel.setMobileNumber(activityCustomerRegistrationBinding.etMobileNumber.getText().toString());
            customerModel.setRemarks(activityCustomerRegistrationBinding.etRemarks.getText().toString());

            customerModel.setStoreID(storeID);
            customerModel.setIsStoreOwner("N");
            customerModel.setIsActive("");
            customerModel.setIsUploaded("N");
            customerModel.setCustomerID(Helper.generateCustomerID(customerModel));
            if (customerHandler.addCustomer(customerModel)) {
                updateCustomerUpLine(customerModel);
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
        } else {
            Helper.showDialog(this, "", message);
        }
    }

    private boolean checkIfValidUpCustID(String upCustID) {
        return true;
    }

    private void updateCustomerUpLine(CustomerModel custModel){
        CustomerModel custUpline;
        custModel.setCustomerUpID1(custModel.getUpCustomerID());
//        custUpline = CustomerHandler.getInstance(this).getCustomerUplines(custModel.getUpCustomerID(), custModel.getStoreID());
//        if (!TextUtils.isEmpty(custUpline.getCustomerUpID1())) {
//            custModel.setCustomerUpID2(custUpline.getCustomerUpID1());
//        }
//        if (!TextUtils.isEmpty(custUpline.getCustomerUpID2())) {
//            custModel.setCustomerUpID3(custUpline.getCustomerUpID2());
//        }
//        if (!TextUtils.isEmpty(custUpline.getCustomerUpID3())) {
//            custModel.setCustomerUpID4(custUpline.getCustomerUpID3());
//        }

        Log.d("customerUplines",custModel.getCustomerUpID1() + "," + custModel.getCustomerUpID2() + ","
                + custModel.getCustomerUpID3() + "," + custModel.getCustomerUpID4());
        CustomerHandler.getInstance(this).addCustomerUpline(custModel);
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

            }
        }
    }
}

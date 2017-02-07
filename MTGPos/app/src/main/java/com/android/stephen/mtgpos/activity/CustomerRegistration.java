package com.android.stephen.mtgpos.activity;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import com.android.stephen.mtgpos.utils.Helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CustomerRegistration extends AppCompatActivity implements View.OnClickListener {

    ActivityCustomerRegistrationBinding activityCustomerRegistrationBinding;
    Calendar calendar;
    CustomerModel customerModel;
    private String format = "MMM dd, yyyy";
    private String franchiseID = "";

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
            franchiseID = bundle.getString("franID");
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
        item = menu.findItem(R.id.action_add);
        item.setVisible(false);
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
        customerModel = new CustomerModel();
        String message = "";

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

        if (!TextUtils.isEmpty(activityCustomerRegistrationBinding.etBirthdate.getText())) {
            customerModel.setBirthDate(activityCustomerRegistrationBinding.etBirthdate.getText().toString());
        } else {
            message += getResources().getString(R.string.error_birth_date);
        }

        if (TextUtils.isEmpty(message)) {
            if (!TextUtils.isEmpty(activityCustomerRegistrationBinding.etReferral.getText())) {
                if (checkIfValidUpCustID(activityCustomerRegistrationBinding.etReferral.getText().toString().toUpperCase()))
                    customerModel.setUpCustomerID(activityCustomerRegistrationBinding.etReferral.getText().toString().toUpperCase());
                else
                    Helper.showDialog(this, "", "Invalid referral code.");
            } else {
                customerModel.setUpCustomerID(getOwnerCustomerID());
            }
            customerModel.setEmailAddress(activityCustomerRegistrationBinding.etEmailAddress.getText().toString());
            customerModel.setMobileNumber(activityCustomerRegistrationBinding.etMobileNumber.getText().toString());
            customerModel.setRemarks(activityCustomerRegistrationBinding.etRemarks.getText().toString());

            customerModel.setStoreID(franchiseID);
            customerModel.setIsStoreOwner("N");
            customerModel.setIsActive("N");
            customerModel.setIsUploaded("N");
            customerModel.setCustomerID(Helper.generateCustomerID(customerModel));
            if (CustomerHandler.getInstance(this).addCustomer(customerModel)) {
                updateCustomerUpLine(customerModel);
                Helper.showDialog(this, "", getResources().getString(R.string.success_add_customer), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Helper.alertDialogCancel();
                        clearData();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
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
        custUpline = CustomerHandler.getInstance(this).getCustomerUplines(custModel.getUpCustomerID(), custModel.getStoreID());
        if (!TextUtils.isEmpty(custUpline.getCustomerUpID1())) {
            custModel.setCustomerUpID2(custUpline.getCustomerUpID1());
        }
        if (!TextUtils.isEmpty(custUpline.getCustomerUpID2())) {
            custModel.setCustomerUpID3(custUpline.getCustomerUpID2());
        }
        if (!TextUtils.isEmpty(custUpline.getCustomerUpID3())) {
            custModel.setCustomerUpID4(custUpline.getCustomerUpID3());
        }

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
}

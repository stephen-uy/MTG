package com.android.stephen.mtgpos.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.database.StoreHandler;
import com.android.stephen.mtgpos.databinding.ActivityUserRegistrationBinding;
import com.android.stephen.mtgpos.model.StoreModel;
import com.android.stephen.mtgpos.utils.Helper;
import com.android.stephen.mtgpos.utils.LayoutSettings;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class UserRegistration extends AppCompatActivity {

    ActivityUserRegistrationBinding activityUserRegistrationBinding;
    private String storeID;
    private String userID;
    private String userRole;
    private boolean isAdd;
    StoreModel storeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpData();
        setUpToolBar();
        setUpViews();
    }

    private void setUpData() {
        activityUserRegistrationBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_registration);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            storeID = bundle.getString("storeID");
            userID = bundle.getString("userID");
            isAdd = bundle.getBoolean("isAdd");
            storeModel = new StoreModel();
            if (!isAdd)
                storeModel = (StoreModel) bundle.getSerializable("model");
        }
        if (!isAdd) {
            activityUserRegistrationBinding.setStoreModel(storeModel);
        }
    }

    private void setUpToolBar() {
        activityUserRegistrationBinding.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        setSupportActionBar(activityUserRegistrationBinding.toolbar);
        activityUserRegistrationBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpViews() {
        activityUserRegistrationBinding.spnrLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userRole = adapterView.getItemAtPosition(i).toString().substring(0,3);
                if (i <= 0)
                    userRole = "";

                Log.i("userRole", userRole);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        addSpinnerItems();
    }

    private void addSpinnerItems(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item,
                this.getResources().getStringArray(R.array.user_role_array));
        activityUserRegistrationBinding.spnrLevel.setAdapter(arrayAdapter);
        if (!isAdd)
            activityUserRegistrationBinding.spnrLevel.setSelection(getUserLevelID(storeModel.getLevel()));
        else
            activityUserRegistrationBinding.spnrLevel.setSelection(0);
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
        item = menu.findItem(R.id.action_search);
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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.US);
        String regDate = simpleDateFormat.format(calendar.getTime());
        String message = "";
        String content;

        if (!TextUtils.isEmpty(activityUserRegistrationBinding.etUsername.getText())) {
            storeModel.setUserName(activityUserRegistrationBinding.etUsername.getText().toString());
        } else {
            message += getResources().getString(R.string.error_user_name);
        }

        if (!TextUtils.isEmpty(activityUserRegistrationBinding.etPassword.getText())) {
            storeModel.setPassword(activityUserRegistrationBinding.etPassword.getText().toString());
        } else {
            message += getResources().getString(R.string.error_password);
        }

        if (!TextUtils.isEmpty(activityUserRegistrationBinding.etFirstName.getText())) {
            storeModel.setFirstName(activityUserRegistrationBinding.etFirstName.getText().toString());
        } else {
            message += getResources().getString(R.string.error_first_name);
        }

        if (!TextUtils.isEmpty(activityUserRegistrationBinding.etMiddleName.getText())) {
            storeModel.setMiddleName(activityUserRegistrationBinding.etMiddleName.getText().toString());
        } else {
            message += getResources().getString(R.string.error_middle_name);
        }

        if (!TextUtils.isEmpty(activityUserRegistrationBinding.etLastName.getText())) {
            storeModel.setLastName(activityUserRegistrationBinding.etLastName.getText().toString());
        } else {
            message += getResources().getString(R.string.error_last_name);
        }

        if (!TextUtils.isEmpty(userRole)){
            storeModel.setLevel(userRole);
        } else {
            message += getResources().getString(R.string.error_user_level);
        }

        if (TextUtils.isEmpty(message)){
            storeModel.setStoreID(storeID);
            storeModel.setRegBy(userID);
            storeModel.setRegDate(regDate);
            storeModel.setRemarks(activityUserRegistrationBinding.etRemarks.getText().toString());
            storeModel.setIsActive("N");
            storeModel.setIsUploaded("N");

            if (isAdd) {
                StoreHandler.getInstance(this).addStoreUser(storeModel);
                content = getResources().getString(R.string.success_add_user);
            } else {
                StoreHandler.getInstance(this).updateStoreUser(storeModel);
                content = getResources().getString(R.string.success_update_user);
            }
            Helper.showDialog(this, "", content, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Helper.alertDialogCancel();
                    clearData();
                    setResult(RESULT_OK);
                    finish();
                }
            });
        } else {
            Helper.showDialog(this, "", message);
        }
    }

    private int getUserLevelID(String level){
        return Arrays.asList(getResources().getStringArray(R.array.user_role_array)).indexOf(getUserLevelValue(level));
    }

    private String getUserLevelValue(String level){
        if (level.equalsIgnoreCase("Adm"))
            return "Admin";
        else
            return "Processor";
    }

    private void clearData() {
        activityUserRegistrationBinding.etUsername.getText().clear();
        activityUserRegistrationBinding.etFirstName.getText().clear();
        activityUserRegistrationBinding.etMiddleName.getText().clear();
        activityUserRegistrationBinding.etLastName.getText().clear();
        activityUserRegistrationBinding.etPassword.getText().clear();
        activityUserRegistrationBinding.etRemarks.getText().clear();
        activityUserRegistrationBinding.spnrLevel.setSelection(0);
        activityUserRegistrationBinding.tiUsername.requestFocus();
    }
}

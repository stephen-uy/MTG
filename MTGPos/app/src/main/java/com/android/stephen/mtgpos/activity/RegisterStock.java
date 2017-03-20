package com.android.stephen.mtgpos.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.callback.VolleyCallback;
import com.android.stephen.mtgpos.database.DBModels;
import com.android.stephen.mtgpos.databinding.ActivityRegisterStockBinding;
import com.android.stephen.mtgpos.model.StoreModel;
import com.android.stephen.mtgpos.utils.GlobalVariables;
import com.android.stephen.mtgpos.utils.Helper;
import com.android.stephen.mtgpos.utils.Parameters;
import com.android.stephen.mtgpos.utils.StoreAPI;
import com.android.stephen.mtgpos.utils.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

public class RegisterStock extends AppCompatActivity implements TextWatcher, View.OnClickListener, VolleyCallback {
    ActivityRegisterStockBinding activityRegisterStockBinding;
    StoreModel storeModel;
    private String dateDelivered;
    private String userName;
    String[] labels;
    private ProgressDialog progressDialog;
    Calendar calendar;

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
        setValues(storeModel.getItemType());
    }

    private void setUpData() {
        activityRegisterStockBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_stock);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            storeModel = (StoreModel) bundle.getSerializable("model");
            userName = bundle.getString("username");
        }
    }

    private void setUpToolBar() {
        activityRegisterStockBinding.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close));
        setSupportActionBar(activityRegisterStockBinding.toolbar);
        activityRegisterStockBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setValues(String itemType) {
        labels = new String[3];
        if (itemType.equalsIgnoreCase(getString(R.string.packs)))
            labels = getResources().getStringArray(R.array.Packs);
        else if (itemType.equalsIgnoreCase(getString(R.string.pcs)))
            labels = getResources().getStringArray(R.array.PCS);
        else if (itemType.equalsIgnoreCase(getString(R.string.box)))
            labels = getResources().getStringArray(R.array.Box);
        else if (itemType.equalsIgnoreCase(getString(R.string.kilo)))
            labels = getResources().getStringArray(R.array.Kilo);
        else if (itemType.equalsIgnoreCase(getString(R.string.grams)))
            labels = getResources().getStringArray(R.array.Grams);
        else if (itemType.equalsIgnoreCase(getString(R.string.liter)))
            labels = getResources().getStringArray(R.array.Liter);

        activityRegisterStockBinding.etInputQuantity.addTextChangedListener(this);
        activityRegisterStockBinding.btnSave.setOnClickListener(this);
        activityRegisterStockBinding.tvDateDelivered.setOnClickListener(this);

        activityRegisterStockBinding.tvItemDesc.setText(storeModel.getItemDesc().toString());
        activityRegisterStockBinding.tvInputQty.setText(labels[0].toString());
        activityRegisterStockBinding.tvQuantityPerItem.setText(labels[1].toString());
        activityRegisterStockBinding.tvTotalNewStock.setText(labels[2].toString());

        activityRegisterStockBinding.tvQuantityPerItemValue.setText(storeModel.getQtyPerItemType());
        activityRegisterStockBinding.tvRemainingQuantityValue.setText(storeModel.getQuantity());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() > 0){
            if (charSequence.length() == 1 && charSequence.toString().equalsIgnoreCase("0")){
                activityRegisterStockBinding.etInputQuantity.setText("");
                activityRegisterStockBinding.tvTotalNewStockValue.setText("");
            } else {
                activityRegisterStockBinding.tvTotalNewStockValue.setText(
                        Helper.computeAmount(activityRegisterStockBinding.etInputQuantity.getText().toString(),
                                activityRegisterStockBinding.tvRemainingQuantityValue.getText().toString(),
                                storeModel.getQtyPerItemType()));
            }
        } else
            activityRegisterStockBinding.tvTotalNewStockValue.setText("");
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSave:
                saveNewStock();
                break;
            case R.id.tvDateDelivered:
                showDate();
                break;
        }
    }

    private void saveNewStock() {
        progressDialog = Helper.buildProgressSpinnerDialog(this, getString(R.string.loading));
        progressDialog.show();
        if (!TextUtils.isEmpty(activityRegisterStockBinding.tvTotalNewStockValue.getText().toString())){
            if (!TextUtils.isEmpty(dateDelivered)) {
                StoreAPI storeAPI = new StoreAPI(this);
                storeAPI.saveNewStock(this, userName, setItemENvalues());
            } else {
                progressDialog.dismiss();
                Helper.showDialog(this, "", getString(R.string.error_date_delivered));
            }
        } else {
            progressDialog.dismiss();
            Helper.showDialog(this, "", getString(R.string.error_input_quantity) + " " + storeModel.getItemType().toLowerCase());
        }
    }

    private String setItemENvalues() {
        JSONObject itemENMap = new JSONObject();
        try {
            itemENMap.put(DBModels.enumStocks.StoreID.toString(), storeModel.getStoreID());
            itemENMap.put(Parameters.Stocks_Ref.getValue(), Helper.generateStocksRef(this, storeModel.getStoreID()));
            itemENMap.put(Parameters.Item_ID.getValue(), storeModel.getItemID());
            itemENMap.put(Parameters.Quantity.getValue(), activityRegisterStockBinding.etInputQuantity.getText().toString());
            itemENMap.put(Parameters.Old_Quantity.getValue(), storeModel.getQuantity());
            itemENMap.put(Parameters.Total_Quantity.getValue(), activityRegisterStockBinding.tvTotalNewStockValue.getText().toString());
            itemENMap.put(Parameters.Date_Delivered.getValue(), dateDelivered);
            itemENMap.put(Parameters.Reg_By.getValue(), userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return itemENMap.toString();
    }

    private void showDate() {
        calendar = Calendar.getInstance();
        new DatePickerDialog(this, onDateSetListener, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(GlobalVariables.dateFormat, Locale.US);
        activityRegisterStockBinding.tvDateDelivered.setText(sdf.format(calendar.getTime()));
        dateDelivered = activityRegisterStockBinding.tvDateDelivered.getText().toString();
    }

    @Override
    public void onResponseReady(Task task, LinkedHashMap<String, String> response) {
        progressDialog.dismiss();
        Log.d("RegisterStock","response" + response);
        if (response.size() > 0) {
            Intent output = new Intent();
            output.putExtra("itemID", storeModel.getItemID());
            output.putExtra("storeID", storeModel.getStoreID());
            setResult(RESULT_OK, output);
            Toast.makeText(this, getString(R.string.success_add_stock), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, getString(R.string.error_add_stock), Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public void onResponseReady(Task task, LinkedList<LinkedHashMap<String, String>> response) {

    }
}

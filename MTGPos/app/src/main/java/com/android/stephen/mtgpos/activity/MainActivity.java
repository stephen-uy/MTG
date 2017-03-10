package com.android.stephen.mtgpos.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.callback.FragmentCallback;
import com.android.stephen.mtgpos.callback.VolleyCallback;
import com.android.stephen.mtgpos.database.DBModels;
import com.android.stephen.mtgpos.database.StoreHandler;
import com.android.stephen.mtgpos.fragment.CustomerFragment;
import com.android.stephen.mtgpos.fragment.DashboardFragment;
import com.android.stephen.mtgpos.fragment.InventoryFragment;
import com.android.stephen.mtgpos.fragment.POSFragment;
import com.android.stephen.mtgpos.fragment.ProductsFragment;
import com.android.stephen.mtgpos.fragment.RegisteredStocksFragment;
import com.android.stephen.mtgpos.fragment.StocksFragment;
import com.android.stephen.mtgpos.fragment.TransactionFragment;
import com.android.stephen.mtgpos.fragment.UserFragment;
import com.android.stephen.mtgpos.model.CustomerModel;
import com.android.stephen.mtgpos.model.LookUpModel;
import com.android.stephen.mtgpos.utils.APIHelper;
import com.android.stephen.mtgpos.utils.FragmentTag;
import com.android.stephen.mtgpos.utils.GlobalVariables;
import com.android.stephen.mtgpos.utils.Helper;
import com.android.stephen.mtgpos.model.StoreModel;
import com.android.stephen.mtgpos.utils.StoreAPI;
import com.android.stephen.mtgpos.utils.Task;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback, DashboardFragment.OnDashboardFragmentInteractionListener,
        CustomerFragment.OnListCustomerFragmentInteractionListener, UserFragment.OnListUserFragmentInteractionListener,
        InventoryFragment.OnListInventoryFragmentInteractionListener, ProductsFragment.OnListProductsFragmentInteractionListener,
        TransactionFragment.OnListTransactionFragmentInteractionListener, StocksFragment.OnListStocksFragmentInteractionListener,
        RegisteredStocksFragment.OnListRegisteredStocksFragmentInteractionListener, VolleyCallback{

    private NavigationView navigationView;
    POSFragment posFragment;
    CustomerFragment customerFragment;
    UserFragment userFragment;
    InventoryFragment inventoryFragment;
    ProductsFragment productsFragment;
    TransactionFragment transactionFragment;
    StocksFragment stocksFragment;
    RegisteredStocksFragment registeredStocksFragment;
    DashboardFragment dashboardFragment;
    private String username;
    private String storeID;
    private String level;
    private String password;
    private String macaddress;
    StoreHandler storeHandler;
    StoreAPI storeAPI;
    StoreModel storeModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpData();
        setUpViews();
        setUpFragment();
    }

    private void setUpFragment() {
        dashboardFragment = new DashboardFragment();
        posFragment = new POSFragment();
        customerFragment = new CustomerFragment();
        userFragment = new UserFragment();
        inventoryFragment = new InventoryFragment();
        productsFragment = new ProductsFragment();
        transactionFragment = new TransactionFragment();
        stocksFragment = new StocksFragment();
        registeredStocksFragment = new RegisteredStocksFragment();
        Helper.addFragment(this, dashboardFragment, R.id.content_main, FragmentTag.DASHBOARD.toString());
        dashboardFragment.setValues(username, password, macaddress, storeID);
    }

    private void setUpData() {
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("user");
            storeID = bundle.getString("storeID");
            level = bundle.getString("level");
            password = bundle.getString("password");
            macaddress = bundle.getString("macaddress");
            storeModel = (StoreModel) bundle.getSerializable("storeModel");
        }
    }

    private void setUpViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu navMenu = navigationView.getMenu();
        if (!level.equalsIgnoreCase("Admin")){
            navMenu.findItem(R.id.nav_user).setVisible(false);
            navMenu.findItem(R.id.nav_inventory).setVisible(false);
            navMenu.findItem(R.id.nav_transactions).setVisible(false);
            navMenu.findItem(R.id.nav_products).setVisible(false);
            navMenu.findItem(R.id.nav_stocks).setVisible(false);
            navMenu.findItem(R.id.nav_register_stocks).setVisible(false);
            navMenu.findItem(R.id.nav_upoints_history).setVisible(false);
        } else{
            navMenu.findItem(R.id.nav_user).setVisible(true);
            navMenu.findItem(R.id.nav_inventory).setVisible(true);
            navMenu.findItem(R.id.nav_transactions).setVisible(true);
            navMenu.findItem(R.id.nav_products).setVisible(true);
            navMenu.findItem(R.id.nav_stocks).setVisible(true);
//            navMenu.findItem(R.id.nav_register_stocks).setVisible(true);
//            navMenu.findItem(R.id.nav_upoints_history).setVisible(true);
        }

        View view = navigationView.getHeaderView(0);
        TextView tvUsername = (TextView) view.findViewById(R.id.tvUsername);
        tvUsername.setText(username);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (dashboardFragment.isVisible()){
                finish();
            } else {
                super.onBackPressed();
                Helper.replaceFragment(this, dashboardFragment, R.id.content_main, FragmentTag.DASHBOARD.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            return true;
        } else if (id == R.id.action_add){
            if (customerFragment.isVisible()) {
                Intent i = new Intent(this, CustomerRegistration.class);
                i.putExtra("storeID", storeID);
                startActivityForResult(i, GlobalVariables.ADD_CUSTOMER);
            } else if (userFragment.isVisible()) {
                Intent i = new Intent(this, UserRegistration.class);
                i.putExtra("storeID", storeID);
                i.putExtra("userID", username);
                i.putExtra("isAdd", true);
                startActivityForResult(i, GlobalVariables.ADD_USER);
            }
        } else if (id == R.id.action_update){
            if (inventoryFragment.isVisible()){
                updateList(DBModels.enumTables.StoreItem.toString(), DBModels.createTableItem, getString(R.string.update_items));
            } else if (dashboardFragment.isVisible()){
                updateList(DBModels.enumTables.Store.toString(), DBModels.createTableStore, getString(R.string.update_dashboard));
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateList(final String table, final String query, String message){
        Helper.showDialog(MainActivity.this, "", message, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.alertDialogCancel();
                progressDialog = Helper.buildProgressSpinnerDialog(MainActivity.this, getString(R.string.loading));
                progressDialog.show();
                storeHandler = new StoreHandler(MainActivity.this);
                storeAPI = new StoreAPI(MainActivity.this);
                storeHandler.deleteTable(table);
                storeHandler.createTable(query);
                int row = storeHandler.getRowCounts(table);
                if (row == 0) {
                    if (table.equalsIgnoreCase(DBModels.enumTables.StoreItem.toString()))
                        storeAPI.getStoreItems(MainActivity.this, storeID);
                    else if (table.equalsIgnoreCase(DBModels.enumTables.Store.toString())) {
                        storeHandler.deleteTable(DBModels.enumTables.StoreUPoints.toString());
                        storeHandler.createTable(DBModels.createTableStoreUPoints);
                        storeAPI.getStoreDetails(MainActivity.this, username, password, macaddress);
                    }
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.alertDialogCancel();
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            Helper.replaceFragment(this, dashboardFragment, R.id.content_main, FragmentTag.DASHBOARD.toString());
            dashboardFragment.setValues(username, password, macaddress, storeID);
        } else if (id == R.id.nav_pos) {
            Helper.replaceFragment(this, posFragment, R.id.content_main, FragmentTag.POS.toString());
        } else if (id == R.id.nav_inventory) {
            Helper.replaceFragment(this, inventoryFragment, R.id.content_main, FragmentTag.INVENTORY.toString());
        } else if (id == R.id.nav_transactions) {
            Helper.replaceFragment(this, transactionFragment, R.id.content_main, FragmentTag.TRANSACTION.toString());
        } else if (id == R.id.nav_products) {
            Helper.replaceFragment(this, productsFragment, R.id.content_main, FragmentTag.PRODUCT.toString());
        } else if (id == R.id.nav_stocks) {
//            Helper.replaceFragment(this, stocksFragment, R.id.content_main, FragmentTag.STOCKS.toString());
            Helper.replaceFragment(this, registeredStocksFragment, R.id.content_main, FragmentTag.REGISTER_STOCKS.toString());
            registeredStocksFragment.storeID = storeID;
        } else if (id == R.id.nav_register_stocks) {
            Helper.replaceFragment(this, registeredStocksFragment, R.id.content_main, FragmentTag.REGISTER_STOCKS.toString());
            registeredStocksFragment.storeID = storeID;
        } else if (id == R.id.nav_customer) {
            Helper.replaceFragment(this, customerFragment, R.id.content_main, FragmentTag.CUSTOMER.toString());
        } else if (id == R.id.nav_user) {
            Helper.replaceFragment(this, userFragment, R.id.content_main, FragmentTag.USER_MAINTENANCE.toString());
        } else if (id == R.id.nav_upoints_history) {
            Helper.showDialog(this, "", "This feature is not available.");
        } else if (id == R.id.nav_logout) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentCallback() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case GlobalVariables.ADD_CUSTOMER:
                if (resultCode == RESULT_OK)
                    customerFragment.loadCustomer();
                break;
            case GlobalVariables.ADD_USER:
                if (resultCode == RESULT_OK)
                    userFragment.loadUser();
                break;
            case GlobalVariables.ADD_STOCK:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String storeID = bundle.getString("storeID");
                    String itemID = bundle.getString("itemID");
                    registeredStocksFragment.searchStockList(itemID, storeID);
                }
                break;
        }
    }

    public Dialog showUserListMenu(String title, final StoreModel storeModel) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setItems(R.array.dialog_menu, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        if (which == 0){
                            Intent i = new Intent(MainActivity.this, UserRegistration.class);
                            i.putExtra("storeID", storeID);
                            i.putExtra("userID", username);
                            i.putExtra("isAdd", false);
                            i.putExtra("model", storeModel);
                            startActivityForResult(i, GlobalVariables.ADD_USER);
                        } else {
                            Helper.showDialog(MainActivity.this, "", getString(R.string.delete_user), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Helper.alertDialogCancel();
                                    if (username.equalsIgnoreCase(storeModel.getUserName())){
                                        Helper.showDialog(MainActivity.this, "", "Cannot delete logged in user.");
                                    } else {
                                        StoreHandler.getInstance(MainActivity.this).deleteStoreUser(storeModel);
                                    }
                                    userFragment.loadUser();
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Helper.alertDialogCancel();
                                }
                            });
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void OnListUserFragmentInteractionListener(StoreModel item) {
//        Toast.makeText(this, item.getLevel() + "," + item.getUserName() + "," + item.getPass()
//                + "," + item.getRegBy() + "," + item.getRegDate(), Toast.LENGTH_SHORT).show();
        showUserListMenu(item.getUserName(), item).show();
    }

    @Override
    public void OnListInventoryFragmentInteractionListener(LookUpModel lookUpModel) {

    }

    @Override
    public void OnListCustomerFragmentInteractionListener(CustomerModel item) {
//        CustomerModel custUpline;
//        custUpline = CustomerHandler.getInstance(this).getCustomerUplines(item.getCustomerID(), item.getStoreID());
        Toast.makeText(this, "Customer's promo code: " + item.getCustomerID(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnListProductsFragmentInteractionListener(LookUpModel item) {
//        Intent i = new Intent(this, ProductItems.class);
//        i.putExtra("product", item.getProductDesc());
//        i.putExtra("id", item.getProductID());
//        startActivity(i);
    }

    @Override
    public void OnListTransactionFragmentInteractionListener(StoreModel item) {

    }

    @Override
    public void OnListStocksFragmentInteractionListener(StoreModel item) {

    }

    @Override
    public void OnListRegisteredStocksFragmentInteractionListener(StoreModel item) {
        Intent i = new Intent(this, RegisterStock.class);
        i.putExtra("model", item);
        i.putExtra("username", username);
        startActivityForResult(i, GlobalVariables.ADD_STOCK);
    }

    @Override
    public void onDashboardFragmentInteraction(Uri uri) {

    }

    @Override
    public void onResponseReady(Task task, LinkedHashMap<String, String> response) {
        Log.i("MainActivity_volley", "response : "+  task.getValue().toString() + response.toString());
        switch (task){
            case STORE_DETAILS:
                parseStoreDetails(response);
                break;
        }
    }

    @Override
    public void onResponseReady(Task task, LinkedList<LinkedHashMap<String, String>> hashMaps) {
        Log.i("MainActivity_volley", "response : "+  task.getValue().toString() + hashMaps.get(0).toString());
        switch (task) {
            case ITEMS:
                parseItems(hashMaps);
                break;
            case STOCKS_LIST:
                parseStocksList(hashMaps);
                break;
        }
    }

    private void parseStocksList(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        LinkedList<StoreModel> modelList = APIHelper.setUpStockList(hashMaps);
        registeredStocksFragment.setUpList(modelList);
    }

    private void parseStoreDetails(LinkedHashMap<String, String> hashMaps) {
        StoreModel model = APIHelper.setUpStoreDetails(hashMaps);
        if (!TextUtils.isEmpty(model.getStoreID())){
            model.setPass(password);
            APIHelper.insertStoreDetails(this, model);
            APIHelper.insertStoreUPoints(this, model);
            dashboardFragment.setUpDashboard();
            progressDialog.dismiss();
            Toast.makeText(this, getString(R.string.success_update_store), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, getString(R.string.failed_to_update_store), Toast.LENGTH_SHORT).show();
    }

    private void parseItems(LinkedList<LinkedHashMap<String, String>> hashMaps) {
        LinkedList<LookUpModel> lookUpModelLinkedList = APIHelper.setUpItemsData(hashMaps);
        if (lookUpModelLinkedList.size() > 0) {
            APIHelper.insertItemData(this, lookUpModelLinkedList);
            inventoryFragment.loadInventory();
            progressDialog.dismiss();
            Toast.makeText(this, getString(R.string.success_update_list), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, getString(R.string.failed_to_update), Toast.LENGTH_SHORT).show();
    }
}

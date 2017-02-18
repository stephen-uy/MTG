package com.android.stephen.mtgpos.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.android.stephen.mtgpos.database.CustomerHandler;
import com.android.stephen.mtgpos.database.StoreHandler;
import com.android.stephen.mtgpos.fragment.CustomerFragment;
import com.android.stephen.mtgpos.fragment.InventoryFragment;
import com.android.stephen.mtgpos.fragment.POSFragment;
import com.android.stephen.mtgpos.fragment.ProductsFragment;
import com.android.stephen.mtgpos.fragment.RegisteredStocksFragment;
import com.android.stephen.mtgpos.fragment.StocksFragment;
import com.android.stephen.mtgpos.fragment.TransactionFragment;
import com.android.stephen.mtgpos.fragment.UserFragment;
import com.android.stephen.mtgpos.model.CustomerModel;
import com.android.stephen.mtgpos.model.LookUpModel;
import com.android.stephen.mtgpos.utils.API;
import com.android.stephen.mtgpos.utils.FragmentTag;
import com.android.stephen.mtgpos.utils.GlobalVariables;
import com.android.stephen.mtgpos.utils.Helper;
import com.android.stephen.mtgpos.model.StoreModel;
import com.android.stephen.mtgpos.utils.HttpVolleyConnector;
import com.android.stephen.mtgpos.utils.Task;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback,
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
    private String username;
    private String storeID;
    private String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpData();
        setUpViews();
        setUpFragment();
    }

    private void setUpFragment() {
        posFragment = new POSFragment();
        customerFragment = new CustomerFragment();
        userFragment = new UserFragment();
        inventoryFragment = new InventoryFragment();
        productsFragment = new ProductsFragment();
        transactionFragment = new TransactionFragment();
        stocksFragment = new StocksFragment();
        registeredStocksFragment = new RegisteredStocksFragment();
        Helper.addFragment(this, posFragment, R.id.content_main, FragmentTag.POS.toString());
    }

    private void setUpData() {
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("user");
            storeID = bundle.getString("storeID");
            level = bundle.getString("level");
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
        if (level.equalsIgnoreCase("Pro")){
            navMenu.findItem(R.id.nav_user).setVisible(false);
            navMenu.findItem(R.id.nav_inventory).setVisible(false);
            navMenu.findItem(R.id.nav_transactions).setVisible(false);
            navMenu.findItem(R.id.nav_products).setVisible(false);
            navMenu.findItem(R.id.nav_stocks).setVisible(false);
            navMenu.findItem(R.id.nav_registered_stocks).setVisible(false);
            navMenu.findItem(R.id.nav_upload).setVisible(false);
            navMenu.findItem(R.id.nav_download).setVisible(false);
        } else{
            navMenu.findItem(R.id.nav_user).setVisible(true);
            navMenu.findItem(R.id.nav_inventory).setVisible(true);
            navMenu.findItem(R.id.nav_transactions).setVisible(true);
            navMenu.findItem(R.id.nav_products).setVisible(true);
            navMenu.findItem(R.id.nav_stocks).setVisible(true);
            navMenu.findItem(R.id.nav_registered_stocks).setVisible(true);
            navMenu.findItem(R.id.nav_upload).setVisible(true);
            navMenu.findItem(R.id.nav_download).setVisible(true);
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
            if (posFragment.isVisible()){
                finish();
            } else {
                super.onBackPressed();
                Helper.replaceFragment(this, posFragment, R.id.content_main, FragmentTag.POS.toString());
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
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pos) {
            Helper.replaceFragment(this, posFragment, R.id.content_main, FragmentTag.POS.toString());
        } else if (id == R.id.nav_inventory) {
            Helper.replaceFragment(this, inventoryFragment, R.id.content_main, FragmentTag.INVENTORY.toString());
        } else if (id == R.id.nav_transactions) {
            Helper.replaceFragment(this, transactionFragment, R.id.content_main, FragmentTag.TRANSACTION.toString());
        } else if (id == R.id.nav_products) {
            Helper.replaceFragment(this, productsFragment, R.id.content_main, FragmentTag.PRODUCT.toString());
        } else if (id == R.id.nav_stocks) {
            Helper.replaceFragment(this, stocksFragment, R.id.content_main, FragmentTag.STOCKS.toString());
        } else if (id == R.id.nav_registered_stocks) {
            Helper.replaceFragment(this, registeredStocksFragment, R.id.content_main, FragmentTag.REGISTER_STOCKS.toString());
        } else if (id == R.id.nav_customer) {
            Helper.replaceFragment(this, customerFragment, R.id.content_main, FragmentTag.CUSTOMER.toString());
        } else if (id == R.id.nav_user) {
            Helper.replaceFragment(this, userFragment, R.id.content_main, FragmentTag.USERMAINTENACE.toString());
        }  else if (id == R.id.nav_upload) {
            HttpVolleyConnector con = new HttpVolleyConnector();
            con.wGet(this, this, API.API, Task.ITEMS);
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
        }
    }

    @Override
    public void OnListUserFragmentInteractionListener(StoreModel item) {
//        Toast.makeText(this, item.getLevel() + "," + item.getUserName() + "," + item.getPassword()
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
        Intent i = new Intent(this, ProductItems.class);
        i.putExtra("product", item.getProductDesc());
        i.putExtra("id", item.getProductID());
        startActivity(i);
    }

    @Override
    public void OnListTransactionFragmentInteractionListener(StoreModel item) {

    }

    @Override
    public void onResponseReady(Task task, HashMap<String, String> response) {
        Log.i("volley", "response : "+  task.getValue().toString() + response.toString());
    }

    @Override
    public void onResponseReady(Task task, List<HashMap<String, String>> response) {
        Log.i("volley", "response : "+  task.getValue().toString() + response.get(0).toString());
    }

    @Override
    public void OnListStocksFragmentInteractionListener(StoreModel item) {

    }

    @Override
    public void OnListRegisteredStocksFragmentInteractionListener(StoreModel item) {

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
}

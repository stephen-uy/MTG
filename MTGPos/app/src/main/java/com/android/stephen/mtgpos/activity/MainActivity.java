package com.android.stephen.mtgpos.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.android.stephen.mtgpos.database.CustomerHandler;
import com.android.stephen.mtgpos.fragment.CustomerFragment;
import com.android.stephen.mtgpos.fragment.POSFragment;
import com.android.stephen.mtgpos.fragment.UserFragment;
import com.android.stephen.mtgpos.model.CustomerModel;
import com.android.stephen.mtgpos.utils.FragmentTag;
import com.android.stephen.mtgpos.utils.GlobalVariables;
import com.android.stephen.mtgpos.utils.Helper;
import com.android.stephen.mtgpos.model.StoreModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback,
        CustomerFragment.OnListFragmentInteractionListener, UserFragment.OnListFragmentInteractionListener {

    private NavigationView navigationView;
    POSFragment posFragment;
    CustomerFragment customerFragment;
    UserFragment userFragment;
    private String username;
    private String franID;
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
        Helper.addFragment(this, posFragment, R.id.content_main, FragmentTag.POS.toString());
    }

    private void setUpData() {
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("user");
            franID = bundle.getString("franID");
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
            navMenu.findItem(R.id.nav_upload).setVisible(false);
            navMenu.findItem(R.id.nav_inventory).setVisible(false);
        } else{
            navMenu.findItem(R.id.nav_user).setVisible(true);
            navMenu.findItem(R.id.nav_upload).setVisible(true);
            navMenu.findItem(R.id.nav_inventory).setVisible(true);
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
                i.putExtra("franID", franID);
                startActivityForResult(i, GlobalVariables.ADD_CUSTOMER);
            } else if (userFragment.isVisible()) {
                Intent i = new Intent(this, UserRegistration.class);
                i.putExtra("franID", franID);
                i.putExtra("userID", username);
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

        } else if (id == R.id.nav_purchases) {

        } else if (id == R.id.nav_customer) {
            Helper.replaceFragment(this, customerFragment, R.id.content_main, FragmentTag.CUSTOMER.toString());
        } else if (id == R.id.nav_user) {
            Helper.replaceFragment(this, userFragment, R.id.content_main, FragmentTag.USERMAINTENACE.toString());
        }  else if (id == R.id.nav_upload) {

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
    public void onListFragmentInteraction(CustomerModel item) {
        CustomerModel custUpline;
        custUpline = CustomerHandler.getInstance(this).getCustomerUplines(item.getCustomerID(), item.getStoreID());
        Toast.makeText(this, custUpline.getCustomerID() + "," + custUpline.getCustomerUpID1() + "," + custUpline.getCustomerUpID2()
                + "," + custUpline.getCustomerUpID3() + "," + custUpline.getCustomerUpID4(), Toast.LENGTH_SHORT).show();
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
    public void onListFragmentInteraction(StoreModel item) {
        Toast.makeText(this, item.getLevel() + "," + item.getUserName() + "," + item.getPassword()
                + "," + item.getRegBy() + "," + item.getRegDate(), Toast.LENGTH_SHORT).show();
    }
}

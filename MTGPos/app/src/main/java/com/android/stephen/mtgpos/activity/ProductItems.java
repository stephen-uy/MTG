package com.android.stephen.mtgpos.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.adapter.MyProductItemsRecyclerViewAdapter;
import com.android.stephen.mtgpos.database.LookUpHandler;
import com.android.stephen.mtgpos.databinding.ActivityProductItemsBinding;
import com.android.stephen.mtgpos.model.LookUpModel;

import java.util.LinkedList;

public class ProductItems extends AppCompatActivity {

    ActivityProductItemsBinding activityProductItemsBinding;
    private LinkedList<LookUpModel> lookUpModelLinkedList;
    private LinearLayoutManager mLayoutManager;
    private MyProductItemsRecyclerViewAdapter myProductItemsRecyclerViewAdapter;
    private String product;
    private String productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpData();
        setUpToolBar();
        setUpList();
    }

    private void setUpData() {
        activityProductItemsBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_items);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            product = bundle.getString("product");
            productID = bundle.getString("id");
        }
        activityProductItemsBinding.tvProduct.setText(product);
    }

    private void setUpList(){
        lookUpModelLinkedList = new LinkedList<>();
        lookUpModelLinkedList.addAll(LookUpHandler.getInstance(this).getProductItemDetails(productID));
        activityProductItemsBinding.listItems.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        activityProductItemsBinding.listItems.setLayoutManager(mLayoutManager);
        myProductItemsRecyclerViewAdapter = new MyProductItemsRecyclerViewAdapter(lookUpModelLinkedList);
        activityProductItemsBinding.listItems.setAdapter(myProductItemsRecyclerViewAdapter);
    }

    private void setUpToolBar() {
        activityProductItemsBinding.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close));
        setSupportActionBar(activityProductItemsBinding.toolbar);
        activityProductItemsBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}

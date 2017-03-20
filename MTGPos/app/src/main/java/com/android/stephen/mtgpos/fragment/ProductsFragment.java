package com.android.stephen.mtgpos.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.adapter.MyProductsRecyclerViewAdapter;
import com.android.stephen.mtgpos.callback.VolleyCallback;
import com.android.stephen.mtgpos.database.LookUpHandler;
import com.android.stephen.mtgpos.model.LookUpModel;
import com.android.stephen.mtgpos.utils.Helper;
import com.android.stephen.mtgpos.utils.StoreAPI;

import java.util.LinkedList;

public class ProductsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListProductsFragmentInteractionListener mListener;
    private MyProductsRecyclerViewAdapter myProductsRecyclerViewAdapter;
    private LinkedList<LookUpModel> lookUpModelLinkedList;
    private LinkedList<LookUpModel> productLinkedList;
    private LinkedList<LookUpModel> categoryLinkedList;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private String[] productIDArray;
    private String[] categoryIDArray;
    private Spinner spnrProduct;
    private Spinner spnrCategory;
    private String selectedProdID;
    private String selectedCatID;
    private ImageButton btnSearch;
    private ProgressDialog progressDialog;
    public static String storeID;

    public ProductsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProductsFragment newInstance(int columnCount) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.title_products));
        View view = inflater.inflate(R.layout.fragment_products_list, container, false);
        setUpList(view);
        return view;
    }

    private void setUpList(View view){
        lookUpModelLinkedList = new LinkedList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        myProductsRecyclerViewAdapter = new MyProductsRecyclerViewAdapter(lookUpModelLinkedList, mListener);
        recyclerView.setAdapter(myProductsRecyclerViewAdapter);
        recyclerView.setVisibility(View.GONE);
        spnrCategory = (Spinner) view.findViewById(R.id.spnrCategory);
        spnrProduct = (Spinner) view.findViewById(R.id.spnrProduct);
        btnSearch = (ImageButton) view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchProductList(selectedProdID, selectedCatID, storeID);
            }
        });
        spnrCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (i <= 0)
                    selectedCatID = "";
                else
                    selectedCatID = categoryIDArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnrProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (i <= 0)
                    selectedProdID = "";
                else
                    selectedProdID = productIDArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setSpinnerValues();
    }

    private void setSpinnerValues() {
        productLinkedList = new LinkedList<>();
        categoryLinkedList = new LinkedList<>();
        productLinkedList.addAll(LookUpHandler.getInstance(getActivity()).getAllProducts());
        ArrayAdapter<String> productAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                productArray(productLinkedList));
        spnrProduct.setAdapter(productAdapter);
        spnrProduct.setSelection(0);

        categoryLinkedList.addAll(LookUpHandler.getInstance(getActivity()).getAllProductCategory());
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                categoryArray(categoryLinkedList));
        spnrCategory.setAdapter(categoryAdapter);
        spnrCategory.setSelection(0);
    }

    private String[] productArray(LinkedList<LookUpModel> lookUpModels){
        String[] newProduct = new String[lookUpModels.size()+1];
        productIDArray = new String[lookUpModels.size()+1];
        newProduct[0] = getResources().getString(R.string.prod_select_all);
        productIDArray[0] = "0";
        for (int i = 0; i < lookUpModels.size(); i++){
            newProduct[i+1] = lookUpModels.get(i).getProductDesc();
            productIDArray[i+1] = lookUpModels.get(i).getProductID();
        }
        return newProduct;
    }

    private String[] categoryArray(LinkedList<LookUpModel> lookUpModels){
        String[] newCategory = new String[lookUpModels.size()+1];
        categoryIDArray = new String[lookUpModels.size()+1];
        newCategory[0] = getResources().getString(R.string.prod_select_one);
        categoryIDArray[0] = "0";
        for (int i = 0; i < lookUpModels.size(); i++){
            newCategory[i+1] = lookUpModels.get(i).getCatDesc();
            categoryIDArray[i+1] = lookUpModels.get(i).getCatID();
        }
        return newCategory;
    }

    public void searchProductList(String selectedProdID, String selectedCatID, String storeID) {
        if (TextUtils.isEmpty(selectedCatID)){
            Helper.showDialog(getActivity(), "", getString(R.string.error_category));
            return;
        }
        progressDialog = Helper.buildProgressSpinnerDialog(getActivity(), getString(R.string.loading));
        progressDialog.show();
        StoreAPI storeAPI = new StoreAPI(getActivity());
        storeAPI.getProductList((VolleyCallback) getActivity(), storeID, selectedProdID, selectedCatID);
    }

    public void setUpList(LinkedList<LookUpModel> lookUpList){
        progressDialog.dismiss();
        if (lookUpList.size() > 0) {
            lookUpModelLinkedList = getPicOfProduct(lookUpList);
            recyclerView.setVisibility(View.VISIBLE);
            loadTransaction();
        }
    }

    private LinkedList<LookUpModel> getPicOfProduct(LinkedList<LookUpModel> modelLinkedList){
        LinkedList<LookUpModel> newList = modelLinkedList;
        LinkedList<LookUpModel> newListPic;
        if (modelLinkedList.size() > 0){
            for (int i = 0; i < modelLinkedList.size(); i++){
                newListPic = LookUpHandler.getInstance(getActivity()).getAllProductsByProductID(modelLinkedList.get(i).getProductID());
                if (newListPic.size() > 0){
                    newList.get(i).setPicture(newListPic.get(0).getPicture());
                }
            }
        }

        return newList;
    }

    private boolean checkIfProductExist(String productID){
        boolean isExist = false;
        LinkedList<LookUpModel> listModel = LookUpHandler.getInstance(getActivity()).getAllProductsByProductID(productID);
        if(listModel.size() > 0)
            isExist = true;

        return isExist;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListProductsFragmentInteractionListener) {
            mListener = (OnListProductsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateProduct(LookUpModel productModel) {
        if (checkIfProductExist(productModel.getProductID())) {
            int result = LookUpHandler.getInstance(getActivity()).updateProductPicture(productModel);
            if (result <= 0) {
                Toast.makeText(getActivity(), getString(R.string.error_add_product_pic), Toast.LENGTH_SHORT).show();
            }
        } else {
            LookUpHandler.getInstance(getActivity()).addProduct(productModel);
        }
        Toast.makeText(getActivity(), getString(R.string.success_add_product_pic), Toast.LENGTH_SHORT).show();
        searchProductList(selectedProdID, selectedCatID, storeID);
    }

    public interface OnListProductsFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnListProductsFragmentInteractionListener(LookUpModel item);
    }

    public void loadTransaction(){
//        lookUpModelLinkedList = LookUpHandler.getInstance(getActivity()).getAllProducts();
        myProductsRecyclerViewAdapter.swap(lookUpModelLinkedList);
    }
}

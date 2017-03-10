package com.android.stephen.mtgpos.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.adapter.MyRegisteredStocksRecyclerViewAdapter;
import com.android.stephen.mtgpos.callback.VolleyCallback;
import com.android.stephen.mtgpos.database.LookUpHandler;
import com.android.stephen.mtgpos.database.StoreHandler;
import com.android.stephen.mtgpos.model.LookUpModel;
import com.android.stephen.mtgpos.model.StoreModel;
import com.android.stephen.mtgpos.utils.APIHelper;
import com.android.stephen.mtgpos.utils.Helper;
import com.android.stephen.mtgpos.utils.StoreAPI;
import com.android.stephen.mtgpos.utils.Task;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class RegisteredStocksFragment extends Fragment{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListRegisteredStocksFragmentInteractionListener mListener;
    private MyRegisteredStocksRecyclerViewAdapter myRegisteredStocksRecyclerViewAdapter;
    private LinkedList<StoreModel> storeModelLinkedList;
    private LinkedList<LookUpModel> lookUpModelLinkedList;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private Spinner spnrItems;
    private ImageButton btnSearch;
    private String selectedItemID;
    private String[] itemIDArray;
    public static String storeID;
    private ProgressDialog progressDialog;
    private LinearLayout llTitle;

    public RegisteredStocksFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RegisteredStocksFragment newInstance(int columnCount) {
        RegisteredStocksFragment fragment = new RegisteredStocksFragment();
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
//        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.title_register_stocks));
        View view = inflater.inflate(R.layout.fragment_registeredstocks_list, container, false);
        setUpViews(view);
//        setUpList(view);
        return view;
    }

    private void setUpViews(View view) {
        storeModelLinkedList = new LinkedList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        myRegisteredStocksRecyclerViewAdapter = new MyRegisteredStocksRecyclerViewAdapter(storeModelLinkedList, mListener);
        recyclerView.setAdapter(myRegisteredStocksRecyclerViewAdapter);
        recyclerView.setVisibility(View.GONE);
        llTitle = (LinearLayout) view.findViewById(R.id.llTitle);
        llTitle.setVisibility(View.GONE);
        spnrItems = (Spinner) view.findViewById(R.id.spnrItems);
        spnrItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i <= 0)
                    selectedItemID = "";
                else
                    selectedItemID = itemIDArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSearch = (ImageButton) view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchStockList(selectedItemID, storeID);
            }
        });
        setSpinnerValues();
    }

    public void searchStockList(String selectedItemID, String storeID) {
        progressDialog = Helper.buildProgressSpinnerDialog(getActivity(), getString(R.string.loading));
        progressDialog.show();
        StoreAPI storeAPI = new StoreAPI(getActivity());
        storeAPI.getStocksList((VolleyCallback) getActivity(), storeID, selectedItemID);
    }

    private void setSpinnerValues() {
        lookUpModelLinkedList = new LinkedList<>();
        lookUpModelLinkedList.addAll(LookUpHandler.getInstance(getActivity()).getAllItems());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                itemArray(lookUpModelLinkedList));
        spnrItems.setAdapter(arrayAdapter);
        spnrItems.setSelection(0);
    }

    private String[] itemArray(LinkedList<LookUpModel> lookUpModels){
        String[] newItems = new String[lookUpModels.size()+1];
        itemIDArray = new String[lookUpModels.size()+1];
        newItems[0] = getResources().getString(R.string.reg_stock_select_all);
        itemIDArray[0] = "0";
        for (int i = 0; i < lookUpModels.size(); i++){
            newItems[i+1] = lookUpModels.get(i).getItemDesc();
            itemIDArray[i+1] = lookUpModels.get(i).getItemID();
        }
        return newItems;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item;
        item = menu.findItem(R.id.action_save);
        item.setVisible(false);
        item = menu.findItem(R.id.action_add);
        item.setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void setUpList(LinkedList<StoreModel> storeModelsList){
        progressDialog.dismiss();
        if (storeModelsList.size() > 0) {
            storeModelLinkedList = storeModelsList;
            llTitle.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            loadRegisteredStocks();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListRegisteredStocksFragmentInteractionListener) {
            mListener = (OnListRegisteredStocksFragmentInteractionListener) context;
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

    public interface OnListRegisteredStocksFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnListRegisteredStocksFragmentInteractionListener(StoreModel item);
    }

    public void loadRegisteredStocks(){
        myRegisteredStocksRecyclerViewAdapter.swap(storeModelLinkedList);
    }
}

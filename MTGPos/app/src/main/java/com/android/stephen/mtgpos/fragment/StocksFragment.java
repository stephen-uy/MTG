package com.android.stephen.mtgpos.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.adapter.MyStocksRecyclerViewAdapter;
import com.android.stephen.mtgpos.database.StoreHandler;
import com.android.stephen.mtgpos.model.StoreModel;

import java.util.LinkedList;

public class StocksFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListStocksFragmentInteractionListener mListener;
    private MyStocksRecyclerViewAdapter myStocksRecyclerViewAdapter;
    private LinkedList<StoreModel> storeModelLinkedList;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    public StocksFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StocksFragment newInstance(int columnCount) {
        StocksFragment fragment = new StocksFragment();
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
        getActivity().setTitle(getResources().getString(R.string.title_stocks));
        View view = inflater.inflate(R.layout.fragment_stocks_list, container, false);
        setUpList(view);
        return view;
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

    private void setUpList(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        storeModelLinkedList = new LinkedList<>();
        storeModelLinkedList.addAll(StoreHandler.getInstance(getActivity()).getAllStoreStocksWithItems());
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        myStocksRecyclerViewAdapter = new MyStocksRecyclerViewAdapter(storeModelLinkedList, mListener);
        recyclerView.setAdapter(myStocksRecyclerViewAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListStocksFragmentInteractionListener) {
            mListener = (OnListStocksFragmentInteractionListener) context;
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

    public interface OnListStocksFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnListStocksFragmentInteractionListener(StoreModel item);
    }

    public void loadStocks(){
        storeModelLinkedList = StoreHandler.getInstance(getActivity()).getAllStoreStocksWithItems();
        myStocksRecyclerViewAdapter.swap(storeModelLinkedList);
    }
}

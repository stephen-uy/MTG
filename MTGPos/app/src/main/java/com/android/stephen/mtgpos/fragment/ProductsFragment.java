package com.android.stephen.mtgpos.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.adapter.MyProductsRecyclerViewAdapter;
import com.android.stephen.mtgpos.database.LookUpHandler;
import com.android.stephen.mtgpos.model.LookUpModel;

import java.util.LinkedList;

public class ProductsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListProductsFragmentInteractionListener mListener;
    private MyProductsRecyclerViewAdapter myProductsRecyclerViewAdapter;
    private LinkedList<LookUpModel> lookUpModelLinkedList;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
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
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        lookUpModelLinkedList = new LinkedList<>();
        lookUpModelLinkedList.addAll(LookUpHandler.getInstance(getActivity()).getAllProducts());
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        myProductsRecyclerViewAdapter = new MyProductsRecyclerViewAdapter(lookUpModelLinkedList, mListener);
        recyclerView.setAdapter(myProductsRecyclerViewAdapter);
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

    public interface OnListProductsFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnListProductsFragmentInteractionListener(LookUpModel item);
    }

    public void loadTransaction(){
        lookUpModelLinkedList = LookUpHandler.getInstance(getActivity()).getAllProducts();
        myProductsRecyclerViewAdapter.swap(lookUpModelLinkedList);
    }
}

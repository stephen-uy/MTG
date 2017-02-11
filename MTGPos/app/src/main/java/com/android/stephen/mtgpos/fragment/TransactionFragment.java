package com.android.stephen.mtgpos.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.adapter.MyTransactionRecyclerViewAdapter;
import com.android.stephen.mtgpos.database.StoreHandler;
import com.android.stephen.mtgpos.model.StoreModel;

import java.util.LinkedList;

public class TransactionFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListTransactionFragmentInteractionListener mListener;
    private MyTransactionRecyclerViewAdapter myTransactionRecyclerViewAdapter;
    private LinkedList<StoreModel> storeModelLinkedList;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TransactionFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TransactionFragment newInstance(int columnCount) {
        TransactionFragment fragment = new TransactionFragment();
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
        getActivity().setTitle(getResources().getString(R.string.title_transaction));
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        setUpList(view);
        return view;
    }

    private void setUpList(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        storeModelLinkedList = new LinkedList<>();
        storeModelLinkedList.addAll(StoreHandler.getInstance(getActivity()).getAllStorePurchased());
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        myTransactionRecyclerViewAdapter = new MyTransactionRecyclerViewAdapter(storeModelLinkedList, mListener);
        recyclerView.setAdapter(myTransactionRecyclerViewAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListTransactionFragmentInteractionListener) {
            mListener = (OnListTransactionFragmentInteractionListener) context;
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

    public interface OnListTransactionFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnListTransactionFragmentInteractionListener(StoreModel item);
    }

    public void loadTransaction(){
        storeModelLinkedList = StoreHandler.getInstance(getActivity()).getAllStorePurchased();
        myTransactionRecyclerViewAdapter.swap(storeModelLinkedList);
    }
}

package com.android.stephen.mtgpos.fragment;

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

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.adapter.MyInventoryRecyclerViewAdapter;
import com.android.stephen.mtgpos.database.LookUpHandler;
import com.android.stephen.mtgpos.model.LookUpModel;

import java.util.LinkedList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListInventoryFragmentInteractionListener}
 * interface.
 */
public class InventoryFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private MyInventoryRecyclerViewAdapter myInventoryRecyclerViewAdapter;
    private LinkedList<LookUpModel> lookUpModelLinkedList;
    private OnListInventoryFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public InventoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static InventoryFragment newInstance(int columnCount) {
        InventoryFragment fragment = new InventoryFragment();
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.title_items));
        View view = inflater.inflate(R.layout.fragment_inventory_list, container, false);
        setUpList(view);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item;
        item = menu.findItem(R.id.action_update);
        item.setVisible(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setUpList(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        lookUpModelLinkedList = new LinkedList<>();
        lookUpModelLinkedList.addAll(LookUpHandler.getInstance(getActivity()).getAllItems());
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        myInventoryRecyclerViewAdapter = new MyInventoryRecyclerViewAdapter(lookUpModelLinkedList, mListener);
        recyclerView.setAdapter(myInventoryRecyclerViewAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListInventoryFragmentInteractionListener) {
            mListener = (OnListInventoryFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListInventoryFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListInventoryFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnListInventoryFragmentInteractionListener(LookUpModel lookUpModel);
    }

    public void loadInventory(){
        lookUpModelLinkedList = LookUpHandler.getInstance(getActivity()).getAllItems();
        myInventoryRecyclerViewAdapter.swap(lookUpModelLinkedList);
    }
}

package com.android.stephen.mtgpos.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.adapter.MyUserRecyclerViewAdapter;
import com.android.stephen.mtgpos.database.StoreHandler;
import com.android.stephen.mtgpos.databinding.FragmentUserListBinding;
import com.android.stephen.mtgpos.model.StoreModel;

import java.util.LinkedList;

public class UserFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListUserFragmentInteractionListener mListener;
    private MyUserRecyclerViewAdapter myUserRecyclerViewAdapter;
    private LinkedList<StoreModel> userModelLinkedList;
    private LinearLayoutManager mLayoutManager;
    FragmentUserListBinding fragmentUserListBinding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UserFragment newInstance(int columnCount) {
        UserFragment fragment = new UserFragment();
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
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item;
        item = menu.findItem(R.id.action_add);
        item.setVisible(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.title_user));
        fragmentUserListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list,
                container, false);
        setUpList();
        return fragmentUserListBinding.getRoot();
    }

    private void setUpList(){
        userModelLinkedList = new LinkedList<>();
        userModelLinkedList.addAll(StoreHandler.getInstance(getActivity()).getAllStoreUser());
        if (userModelLinkedList.size() > 0) {
            fragmentUserListBinding.tvEmpty.setVisibility(View.GONE);
            fragmentUserListBinding.list.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity());
            fragmentUserListBinding.list.setLayoutManager(mLayoutManager);
            myUserRecyclerViewAdapter = new MyUserRecyclerViewAdapter(userModelLinkedList, mListener);
            fragmentUserListBinding.list.setAdapter(myUserRecyclerViewAdapter);
        } else {
            fragmentUserListBinding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListUserFragmentInteractionListener) {
            mListener = (OnListUserFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListUserFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnListUserFragmentInteractionListener(StoreModel item);
    }

    public void loadUser(){
        userModelLinkedList = StoreHandler.getInstance(getActivity()).getAllStoreUser();
        myUserRecyclerViewAdapter.swap(userModelLinkedList);
    }
}

package com.android.stephen.mtgpos.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.adapter.MyCustomerRecyclerViewAdapter;
import com.android.stephen.mtgpos.database.CustomerHandler;
import com.android.stephen.mtgpos.databinding.FragmentCustomerListBinding;
import com.android.stephen.mtgpos.model.CustomerModel;

import java.util.LinkedList;

public class CustomerFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListCustomerFragmentInteractionListener mListener;
    private MyCustomerRecyclerViewAdapter myCustomerRecyclerViewAdapter;
    LinkedList<CustomerModel> customerModelLinkedList;
    private LinearLayoutManager mLayoutManager;
    FragmentCustomerListBinding fragmentCustomerListBinding;

    public CustomerFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CustomerFragment newInstance(int columnCount) {
        CustomerFragment fragment = new CustomerFragment();
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
        item = menu.findItem(R.id.action_save);
        item.setVisible(false);
        item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.title_customer));
        fragmentCustomerListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer_list,
                container, false);
        setUpList();
        return fragmentCustomerListBinding.getRoot();
    }

    private void setUpList(){
        customerModelLinkedList = new LinkedList<>();
        customerModelLinkedList = CustomerHandler.getInstance(getActivity()).getAllCustomerWithPicture();
        if (customerModelLinkedList.size() > 0) {
            fragmentCustomerListBinding.tvEmpty.setVisibility(View.GONE);
            fragmentCustomerListBinding.list.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity());
            fragmentCustomerListBinding.list.setLayoutManager(mLayoutManager);
            myCustomerRecyclerViewAdapter = new MyCustomerRecyclerViewAdapter(customerModelLinkedList, mListener);
            fragmentCustomerListBinding.list.setAdapter(myCustomerRecyclerViewAdapter);
        } else {
            fragmentCustomerListBinding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListCustomerFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListCustomerFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnListCustomerFragmentInteractionListener(CustomerModel item);
    }

    public void loadCustomer(){
        customerModelLinkedList = CustomerHandler.getInstance(getActivity()).getAllCustomerWithPicture();
        if (customerModelLinkedList.size() <= 1){
            setUpList();
        } else {
            myCustomerRecyclerViewAdapter.swap(customerModelLinkedList);
        }
    }
}

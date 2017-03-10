package com.android.stephen.mtgpos.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.callback.VolleyCallback;
import com.android.stephen.mtgpos.database.StoreHandler;
import com.android.stephen.mtgpos.databinding.FragmentDashboardBinding;
import com.android.stephen.mtgpos.model.StoreModel;
import com.android.stephen.mtgpos.utils.GlobalVariables;
import com.android.stephen.mtgpos.utils.Helper;
import com.android.stephen.mtgpos.utils.StoreAPI;

import java.util.LinkedList;

public class DashboardFragment extends Fragment {
    FragmentDashboardBinding fragmentDashboardBinding;
    private String username;
    private String password;
    private String macaddress;
    private String storeID;
//    VolleyCallback volleyCallback;
    private OnDashboardFragmentInteractionListener mListener;
    LinkedList<StoreModel> storeModelLinkedList;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.title_dashboard));
        fragmentDashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard,
                container, false);
        setUpDashboard();
        return fragmentDashboardBinding.getRoot();
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

    public void setUpDashboard() {
        storeModelLinkedList = new LinkedList<>();
        storeModelLinkedList.addAll(StoreHandler.getInstance(getActivity()).getStore());
        if(storeModelLinkedList.size() > 0) {
            StoreModel userModel = StoreHandler.getInstance(getActivity()).getLoginDetailsByUsernamePassword(username, password);
            LinkedList<StoreModel> pointsModel = StoreHandler.getInstance(getActivity()).getAllStoreUPoints();
            StoreModel model = storeModelLinkedList.get(0);
            fragmentDashboardBinding.tvStoreName.setText(model.getStoreName());
            fragmentDashboardBinding.tvAppType.setText(model.getAppType());
            fragmentDashboardBinding.tvStoreName.setText(model.getStoreName());
            fragmentDashboardBinding.tvTotalUPoints.setText(pointsModel.get(0).getTotalPoints());
            fragmentDashboardBinding.tvTotalRemainingUPoints.setText(pointsModel.get(0).getRemainingPoints());
            fragmentDashboardBinding.tvTotalWDPoints.setText(pointsModel.get(0).getTotalWDPoints());
            fragmentDashboardBinding.tvStoreOwner.setText(userModel.getFirstName() + " "
                    + userModel.getMiddleName() + " " + userModel.getLastName());
            fragmentDashboardBinding.tvIsMTGStore.setText(setValue(model.getIsMTGStore()));
        }
    }

    private String setValue(String isMTGStore) {
        if (isMTGStore.equalsIgnoreCase("Y"))
            return "Yes";
        else
            return "No";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDashboardFragmentInteractionListener) {
            mListener = (OnDashboardFragmentInteractionListener) context;
//            volleyCallback = (VolleyCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setValues(String username, String password, String macaddress, String storeID) {
        this.username = username;
        this.password = password;
        this.macaddress = macaddress;
        this.storeID = storeID;
    }

    public interface OnDashboardFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDashboardFragmentInteraction(Uri uri);
    }
}

package com.android.stephen.mtgpos.fragment;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.adapter.MyUPointsHistoryRecyclerViewAdapter;
import com.android.stephen.mtgpos.callback.VolleyCallback;
import com.android.stephen.mtgpos.model.StoreModel;
import com.android.stephen.mtgpos.utils.GlobalVariables;
import com.android.stephen.mtgpos.utils.Helper;
import com.android.stephen.mtgpos.utils.StoreAPI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class UPointsHistoryFragment extends Fragment implements View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private LinkedList<StoreModel> storeModelLinkedList;
    private OnListUPointsFragmentInteractionListener mListener;
    private MyUPointsHistoryRecyclerViewAdapter myUPointsHistoryRecyclerViewAdapter;
    private Button btnSearch;
    private TextView tvDateFrom;
    private TextView tvDateFromLabel;
    private TextView tvDateTo;
    private TextView tvDateToLabel;
    private LinearLayout llTitle;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    Calendar calendar;
    private boolean isFrom;
    private String dateFrom;
    private String dateTo;
    public static String storeID;
    ProgressDialog progressDialog;
    SimpleDateFormat sdf = new SimpleDateFormat(GlobalVariables.dateFormat, Locale.US);

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            boolean isValidDate = checkDate();
            if (isValidDate)
                setDate();
        }
    };

    public UPointsHistoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UPointsHistoryFragment newInstance(int columnCount) {
        UPointsHistoryFragment fragment = new UPointsHistoryFragment();
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
        getActivity().setTitle(getResources().getString(R.string.title_upoints_history));
        View view = inflater.inflate(R.layout.fragment_upointshistory_list, container, false);
        setUpViews(view);
        return view;
    }

    private void setUpViews(View view) {
        storeModelLinkedList = new LinkedList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        myUPointsHistoryRecyclerViewAdapter = new MyUPointsHistoryRecyclerViewAdapter(storeModelLinkedList, mListener);
        recyclerView.setAdapter(myUPointsHistoryRecyclerViewAdapter);
        recyclerView.setVisibility(View.GONE);
        llTitle = (LinearLayout) view.findViewById(R.id.llTitle);
        llTitle.setVisibility(View.GONE);
        tvDateFrom = (TextView) view.findViewById(R.id.tvFrom);
        tvDateFromLabel = (TextView) view.findViewById(R.id.tvFromLabel);
        tvDateTo = (TextView) view.findViewById(R.id.tvTo);
        tvDateToLabel = (TextView) view.findViewById(R.id.tvToLabel);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        tvDateFrom.setOnClickListener(this);
        tvDateFromLabel.setOnClickListener(this);
        tvDateTo.setOnClickListener(this);
        tvDateToLabel.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    public void setUpList(LinkedList<StoreModel> storeModelsList){
        progressDialog.dismiss();
        if (storeModelsList.size() > 0) {
            storeModelLinkedList = storeModelsList;
            llTitle.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            loadUPointsHistory();
            dateFrom = "";
            dateTo = "";
        }
    }

    private void loadUPointsHistory() {
        myUPointsHistoryRecyclerViewAdapter.swap(storeModelLinkedList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListUPointsFragmentInteractionListener) {
            mListener = (OnListUPointsFragmentInteractionListener) context;
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSearch:
                searchUPointsHistory(storeID, dateFrom, dateTo);
                break;
            case R.id.tvFrom:
                isFrom = true;
                showDate();
                break;
            case R.id.tvFromLabel:
                isFrom = true;
                showDate();
                break;
            case R.id.tvTo:
                isFrom = false;
                showDate();
                break;
            case R.id.tvToLabel:
                isFrom = false;
                showDate();
                break;
        }
    }

    private boolean checkDate(){
        int compare;
        boolean isValidDate= true;
        if (isFrom){
            if (!TextUtils.isEmpty(dateTo)){
                try {
                    Date to = sdf.parse(dateTo);
                    Date from = calendar.getTime();
                    compare = from.compareTo(to);
                    if (compare > 0){
                        //selected from date is greater than to date
                        isValidDate = false;
                        Helper.showDialog(getActivity(), "", getString(R.string.error_date_from_greater_to));
                    }
                } catch (ParseException e) {
//                        e.printStackTrace();
                    isValidDate = false;
                }
            } else {
                compare = calendar.getTime().compareTo(Calendar.getInstance().getTime());
                if (compare > 0){
                    //selected date is greater than date today
                    isValidDate = false;
                    Helper.showDialog(getActivity(), "", getString(R.string.error_date_from));
                }
            }
        } else {
            if (!TextUtils.isEmpty(dateFrom)){
                try {
                    Date from = sdf.parse(dateFrom);
                    Date to = calendar.getTime();
                    compare = from.compareTo(to);
                    if (compare > 0){
                        //selected from date is greater than to date
                        isValidDate = false;
                        Helper.showDialog(getActivity(), "", getString(R.string.error_date_to_less_from));
                    }
                } catch (ParseException e) {
//                        e.printStackTrace();
                    isValidDate = false;
                }
            } else {
                compare = calendar.getTime().compareTo(Calendar.getInstance().getTime());
                if (compare > 0){
                    //selected date is greater than date today
                    isValidDate = false;
                    Helper.showDialog(getActivity(), "", getString(R.string.error_date_to));
                }
            }
        }

        return isValidDate;
    }

    private void searchUPointsHistory(String storeID, String from, String to){
        if (!TextUtils.isEmpty(from) && !TextUtils.isEmpty(to)) {
            progressDialog = Helper.buildProgressSpinnerDialog(getActivity(), getString(R.string.loading));
            progressDialog.show();
            StoreAPI storeAPI = new StoreAPI(getActivity());
            storeAPI.getUPointsHistory((VolleyCallback) getActivity(), storeID, from, to);
        } else {
            Helper.showDialog(getActivity(), "", getString(R.string.error_select_date));
        }
    }

    private void showDate() {
        calendar = Calendar.getInstance();
        new DatePickerDialog(getActivity(), onDateSetListener, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setDate() {
        if (isFrom) {
            tvDateFrom.setText(sdf.format(calendar.getTime()));
            dateFrom = tvDateFrom.getText().toString();
        } else {
            tvDateTo.setText(sdf.format(calendar.getTime()));
            dateTo = tvDateTo.getText().toString();
        }
    }

    public interface OnListUPointsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListUPointsFragmentInteraction(StoreModel item);
    }
}

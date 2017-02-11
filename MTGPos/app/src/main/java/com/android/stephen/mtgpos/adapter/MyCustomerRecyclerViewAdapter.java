package com.android.stephen.mtgpos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.fragment.CustomerFragment.OnListCustomerFragmentInteractionListener;
import com.android.stephen.mtgpos.model.CustomerModel;
import com.android.stephen.mtgpos.utils.Helper;

import java.util.LinkedList;

public class MyCustomerRecyclerViewAdapter extends RecyclerView.Adapter<MyCustomerRecyclerViewAdapter.ViewHolder> {

    private LinkedList<CustomerModel> mValues;
    private OnListCustomerFragmentInteractionListener mListener;

    public MyCustomerRecyclerViewAdapter(LinkedList<CustomerModel> items, OnListCustomerFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_customer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.custPic.setImageBitmap(Helper.decodeBase64(mValues.get(position).getPicture()));
        holder.custID.setText(mValues.get(position).getCustomerID());
        holder.custName.setText(mValues.get(position).getFirstName() + " " + mValues.get(position).getMiddleName() + " " + mValues.get(position).getLastName());
        holder.custBday.setText(mValues.get(position).getBirthDate());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnListCustomerFragmentInteractionListener(holder.mItem);
                }
            }
        });
    }

    public void swap(LinkedList<CustomerModel> list){
        if (mValues != null) {
            mValues.clear();
            mValues = list;
        }
        else {
            mValues = list;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView custID;
        public final TextView custName;
        public final TextView custBday;
        public final ImageView custPic;
        public CustomerModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            custID = (TextView) view.findViewById(R.id.customerIDValue);
            custName = (TextView) view.findViewById(R.id.customerNameValue);
            custBday = (TextView) view.findViewById(R.id.customerBirthDateValue);
            custPic = (ImageView) view.findViewById(R.id.imgPicture);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + custName.getText() + "'";
        }
    }
}

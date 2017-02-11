package com.android.stephen.mtgpos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.fragment.ProductsFragment.OnListProductsFragmentInteractionListener;
import com.android.stephen.mtgpos.model.LookUpModel;

import java.util.LinkedList;

public class MyProductsRecyclerViewAdapter extends RecyclerView.Adapter<MyProductsRecyclerViewAdapter.ViewHolder> {

    private LinkedList<LookUpModel> mValues;
    private OnListProductsFragmentInteractionListener mListener;

    public MyProductsRecyclerViewAdapter(LinkedList<LookUpModel> items, OnListProductsFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getProductID());
        holder.mContentView.setText(mValues.get(position).getProductDesc());
        holder.mPriceView.setText(mValues.get(position).getSellingPrice());
        holder.mRebateView.setText(mValues.get(position).getRebatePoints());
        holder.mShareView.setText(mValues.get(position).getSharePoints());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnListProductsFragmentInteractionListener(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void swap(LinkedList<LookUpModel> list) {
        if (mValues != null) {
            mValues.clear();
            mValues = list;
        }
        else {
            mValues = list;
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mPriceView;
        public final TextView mRebateView;
        public final TextView mShareView;
        public LookUpModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mPriceView = (TextView) view.findViewById(R.id.price);
            mRebateView = (TextView) view.findViewById(R.id.rebate_points);
            mShareView = (TextView) view.findViewById(R.id.share_points);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

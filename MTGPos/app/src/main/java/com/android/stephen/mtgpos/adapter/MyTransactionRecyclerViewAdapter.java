package com.android.stephen.mtgpos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.fragment.TransactionFragment.OnListTransactionFragmentInteractionListener;
import com.android.stephen.mtgpos.model.StoreModel;

import java.util.LinkedList;

public class MyTransactionRecyclerViewAdapter extends RecyclerView.Adapter<MyTransactionRecyclerViewAdapter.ViewHolder> {

    private LinkedList<StoreModel> mValues;
    private OnListTransactionFragmentInteractionListener mListener;

    public MyTransactionRecyclerViewAdapter(LinkedList<StoreModel> items, OnListTransactionFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getPurchaseRef());
        holder.mDateView.setText(mValues.get(position).getPurchaseDate());
        holder.mAmountView.setText(mValues.get(position).getTotalAmt());
        holder.mQuantityView.setText(mValues.get(position).getTotalQty());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnListTransactionFragmentInteractionListener(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void swap(LinkedList<StoreModel> list) {
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
        public final TextView mDateView;
        public final TextView mAmountView;
        public final TextView mQuantityView;
        public StoreModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mDateView = (TextView) view.findViewById(R.id.date);
            mAmountView = (TextView) view.findViewById(R.id.amount);
            mQuantityView = (TextView) view.findViewById(R.id.quantity);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDateView.getText() + "'";
        }
    }
}

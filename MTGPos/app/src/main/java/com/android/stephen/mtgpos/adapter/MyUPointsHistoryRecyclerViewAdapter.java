package com.android.stephen.mtgpos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.fragment.UPointsHistoryFragment.OnListUPointsFragmentInteractionListener;
import com.android.stephen.mtgpos.model.StoreModel;

import java.util.LinkedList;

public class MyUPointsHistoryRecyclerViewAdapter extends RecyclerView.Adapter<MyUPointsHistoryRecyclerViewAdapter.ViewHolder> {

    private LinkedList<StoreModel> mValues;
    private OnListUPointsFragmentInteractionListener mListener;

    public MyUPointsHistoryRecyclerViewAdapter(LinkedList<StoreModel> items, OnListUPointsFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_upointshistory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getPointsRef());
        holder.mContentView.setText(mValues.get(position).getAmount());
        holder.mUPointsView.setText(mValues.get(position).getuPoints());
        holder.mDateView.setText(mValues.get(position).getDateOfTransaction());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListUPointsFragmentInteraction(holder.mItem);
                }
            }
        });
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

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mUPointsView;
        public final TextView mDateView;
        public StoreModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.amount);
            mUPointsView = (TextView) view.findViewById(R.id.upoints);
            mDateView = (TextView) view.findViewById(R.id.date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

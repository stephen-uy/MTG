package com.android.stephen.mtgpos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.fragment.StocksFragment.OnListStocksFragmentInteractionListener;
import com.android.stephen.mtgpos.model.StoreModel;

import java.util.LinkedList;

public class MyStocksRecyclerViewAdapter extends RecyclerView.Adapter<MyStocksRecyclerViewAdapter.ViewHolder> {

    private LinkedList<StoreModel> mValues;
    private OnListStocksFragmentInteractionListener mListener;

    public MyStocksRecyclerViewAdapter(LinkedList<StoreModel> items, OnListStocksFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_stocks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getItemID());
        holder.mContentView.setText(mValues.get(position).getItemDesc());
        holder.mQuantityView.setText(mValues.get(position).getQuantity());
        holder.mTypeView.setText(mValues.get(position).getItemType());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnListStocksFragmentInteractionListener(holder.mItem);
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
        public final TextView mContentView;
        public final TextView mQuantityView;
        public final TextView mTypeView;
        public StoreModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mQuantityView = (TextView) view.findViewById(R.id.qty);
            mTypeView = (TextView) view.findViewById(R.id.type);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

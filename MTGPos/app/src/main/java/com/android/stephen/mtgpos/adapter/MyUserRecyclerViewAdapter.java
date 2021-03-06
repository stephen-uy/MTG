package com.android.stephen.mtgpos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.stephen.mtgpos.R;
import com.android.stephen.mtgpos.fragment.UserFragment.OnListUserFragmentInteractionListener;
import com.android.stephen.mtgpos.model.StoreModel;

import java.util.LinkedList;

public class MyUserRecyclerViewAdapter extends RecyclerView.Adapter<MyUserRecyclerViewAdapter.ViewHolder> {

    private LinkedList<StoreModel> mValues;
    private OnListUserFragmentInteractionListener mListener;

    public MyUserRecyclerViewAdapter(LinkedList<StoreModel> items, OnListUserFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mUserView.setText(mValues.get(position).getUserName());
        holder.mNameView.setText(mValues.get(position).getFirstName() + " " + mValues.get(position).getMiddleName()
                + " " + mValues.get(position).getLastName());
        holder.mLevelView.setText(mValues.get(position).getLevel());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnListUserFragmentInteractionListener(holder.mItem);
                }
            }
        });
    }

    public void swap(LinkedList<StoreModel> list){
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
        public final TextView mUserView;
        public final TextView mNameView;
        public final TextView mLevelView;
        public StoreModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUserView = (TextView) view.findViewById(R.id.username);
            mNameView = (TextView) view.findViewById(R.id.name);
            mLevelView = (TextView) view.findViewById(R.id.level);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}

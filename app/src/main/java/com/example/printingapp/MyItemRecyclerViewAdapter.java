package com.example.printingapp;

import androidx.recyclerview.widget.RecyclerView;

import android.app.LauncherActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link FileItem}.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
    public static final String NAME = "name";
    private final List<FileItem> mValues;
    private final ItemFragment.OnListFragmentInteractionListener mListener;
    public MyItemRecyclerViewAdapter(List<FileItem> items, ItemFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Intent detailIntent = new Intent(holder.mView.getContext(), AssetOnSDActivity.class);
                    detailIntent.putExtra(NAME, holder.mItem.name);
                    holder.mView.getContext().startActivity(detailIntent);
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mNameView;
        public FileItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.item_name_tv);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
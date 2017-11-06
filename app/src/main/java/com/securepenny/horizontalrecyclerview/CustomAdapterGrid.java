package com.securepenny.horizontalrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.securepenny.horizontalrecyclerview.listener.OnCustomerListChangedListener;
import com.securepenny.horizontalrecyclerview.listener.OnStartDragListener;
import com.securepenny.horizontalrecyclerview.utilities.ItemTouchHelperAdapter;
import com.securepenny.horizontalrecyclerview.utilities.ItemTouchHelperViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * Created by R041708040 on 11/6/2017.
 */

public class CustomAdapterGrid extends RecyclerView.Adapter<CustomAdapterGrid.ItemViewHolder> implements ItemTouchHelperAdapter {

    private List<CustomItem> mCustomItems;
    private Context mContext;
    private OnStartDragListener mDragStartListener;
    private OnCustomerListChangedListener mListChangedListener;

    public CustomAdapterGrid(List<CustomItem> customItems, Context context, OnStartDragListener dragLlistener, OnCustomerListChangedListener listChangedListener){
        mCustomItems = customItems;
        mContext = context;
        mDragStartListener = dragLlistener;
        mListChangedListener = listChangedListener;
    }

    @Override
    public CustomAdapterGrid.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_grid, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapterGrid.ItemViewHolder holder, int position) {
        final CustomItem selectedItem = mCustomItems.get(position);

        holder.customName.setText(selectedItem.getName());
        holder.customImage.setImageResource(selectedItem.getImageId());

    }

    @Override
    public int getItemCount() {
        return mCustomItems.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView customName;
        public final ImageView customImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            customName = (TextView)itemView.findViewById(R.id.tvName);
            customImage = (ImageView) itemView.findViewById(R.id.ivLogo);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mCustomItems, fromPosition, toPosition);
        mListChangedListener.onNoteListChanged(mCustomItems);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {

    }
}

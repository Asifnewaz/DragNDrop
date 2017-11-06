package com.securepenny.horizontalrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by R041708040 on 11/6/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ItemViewHolder> {

    private List<CustomItem> mCustomItems;
    private Context mContext;

    public CustomAdapter(List<CustomItem> customItems, Context context){
        mCustomItems = customItems;
        mContext = context;
    }

    @Override
    public CustomAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ItemViewHolder holder, int position) {
        final CustomItem selectedItem = mCustomItems.get(position);

        holder.customName.setText(selectedItem.getName());
        holder.customImage.setImageResource(selectedItem.getImageId());

    }

    @Override
    public int getItemCount() {
        return mCustomItems.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public final TextView customName;
        public final ImageView customImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            customName = (TextView)itemView.findViewById(R.id.tvName);
            customImage = (ImageView) itemView.findViewById(R.id.ivLogo);
        }
    }
}

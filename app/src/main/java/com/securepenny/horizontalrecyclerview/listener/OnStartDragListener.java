package com.securepenny.horizontalrecyclerview.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by R041708040 on 11/6/2017.
 */

public interface OnStartDragListener {
    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}

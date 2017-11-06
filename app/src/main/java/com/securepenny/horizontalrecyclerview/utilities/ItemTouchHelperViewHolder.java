package com.securepenny.horizontalrecyclerview.utilities;

/**
 * Created by R041708040 on 11/6/2017.
 */

public interface ItemTouchHelperViewHolder {
    /**
     * Implementations should update the item view to indicate it's active state.
     */
    void onItemSelected();


    /**
     * state should be cleared.
     */
    void onItemClear();
}

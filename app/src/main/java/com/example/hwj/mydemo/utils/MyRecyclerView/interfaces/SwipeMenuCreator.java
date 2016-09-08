package com.example.hwj.mydemo.utils.MyRecyclerView.interfaces;


import com.example.hwj.mydemo.utils.MyRecyclerView.swipe.SwipeMenu;

public interface SwipeMenuCreator {

    /**
     * Create menu for recyclerVie item.
     *
     * @param swipeLeftMenu  The menu on the left.
     * @param swipeRightMenu The menu on the right.
     * @param viewType       The view type of the new view.
     */
    void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType);

}

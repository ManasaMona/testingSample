package com.example.vinayg.tmdb.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by vinay.g.
 */


public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener, GestureDetector.OnDoubleTapListener {
    private GestureDetector gestureDetector;
    private ClickListener clickListener;
    private RecyclerView mRecyclerView;
    private View mChild;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
        this.clickListener = clickListener;
        this.mRecyclerView = recyclerView;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                mChild = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (mChild!= null && clickListener != null) {
                    clickListener.onLongClick(mChild, recyclerView.getChildAdapterPosition(mChild));
                }
            }
        });
        gestureDetector.setOnDoubleTapListener(this);


    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        mChild = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
        if (mChild!= null && clickListener != null) {
            clickListener.onDoubleClick(mChild,  mRecyclerView.getChildAdapterPosition(mChild));
        }

        return true;
    }
}
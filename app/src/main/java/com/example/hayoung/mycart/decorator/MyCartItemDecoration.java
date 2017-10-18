package com.example.hayoung.mycart.decorator;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hayoung on 2017. 9. 13..
 */

public class MyCartItemDecoration extends RecyclerView.ItemDecoration {

    private int halfSpace;

    public MyCartItemDecoration(int space) {
        this.halfSpace = space / 2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

//        if (parent.getPaddingLeft() != halfSpace) {
//            parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
//            parent.setClipToPadding(false);
//        }

        outRect.top = halfSpace;
        outRect.bottom = halfSpace;
        outRect.left = halfSpace;
        outRect.right = halfSpace;
    }
}


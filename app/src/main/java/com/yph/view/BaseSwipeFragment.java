package com.yph.view;

import android.support.v4.app.Fragment;
import android.view.View;

import java.io.Serializable;

/**
 * Created by _yph on 2016/1/27 0027.
 */

public abstract class BaseSwipeFragment extends Fragment {

    private SwipeBackLayout swipeBackLayout;
    private int model = SwipeBackLayout.SWIPE_RIGHT;

    protected View attachSwipe(View rootView){
        swipeBackLayout = new SwipeBackLayout(getActivity());
        swipeBackLayout.setOnSwipeFinishListener(new SwipeBackLayout.OnSwipeFinishListener() {
            @Override
            public void swipeFinish() {
            }
            @Override
            public void swipeStart() {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        swipeBackLayout.addView(rootView);
        swipeBackLayout.setModel(model);
        swipeBackLayout.setComputeScrollFinish(false);
        return swipeBackLayout;
    }

    protected Serializable getBundleObj() {
        return getArguments().getSerializable("obj");
    }

    protected void setSwipeModel(int model) {
        this.model = model;
    }

    protected void setSwipeEnable(boolean isEnable) {
        swipeBackLayout.setEnabled(isEnable);
    }
}

package com.yph.view;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import java.io.Serializable;

/**
 * Created by _yph on 2016/1/27 0027.
 */

public abstract class BaseSwipeFragment extends Fragment {

    private SwipeBackLayout swipeBackLayout;
    private Activity activity;

    protected View attachSwipe(View rootView){
        activity = getActivity();
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
        swipeBackLayout.setComputeScrollFinish(false);
        return swipeBackLayout;
    }

    protected Serializable getBundleObj() {
        return getArguments().getSerializable("obj");
    }

    protected void setSwipeModel(int model) {
        swipeBackLayout.setModel(model);
    }

    protected void setSwipeEnable(boolean isEnable) {
        swipeBackLayout.setEnabled(isEnable);
    }
}

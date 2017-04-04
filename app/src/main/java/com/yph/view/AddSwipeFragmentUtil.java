package com.yph.view;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.yph.swipebackfragment.R;

public class AddSwipeFragmentUtil {

	public static void addFragmentFromRight(FragmentActivity activity,BaseSwipeFragment fragment) {
		addFragment(activity,fragment,R.anim.slide_in_from_right,R.anim.slide_out_to_right);
		fragment.setSwipeModel(SwipeBackLayout.SWIPE_RIGHT);
	}
	public static void addFragmentFromLeft(FragmentActivity activity,BaseSwipeFragment fragment) {
		addFragment(activity,fragment,R.anim.slide_in_from_left,R.anim.slide_out_to_left);
		fragment.setSwipeModel(SwipeBackLayout.SWIPE_LEFT);
	}
	public static void addFragmentFrombBottom(FragmentActivity activity,BaseSwipeFragment fragment) {
		addFragment(activity,fragment,R.anim.slide_in_from_bottom,R.anim.slide_out_to_bottom);
		fragment.setSwipeModel(SwipeBackLayout.SWIPE_BOTTOM);
	}
	public static void addFragmentFromTop(FragmentActivity activity,BaseSwipeFragment fragment) {
		addFragment(activity,fragment,R.anim.slide_in_from_top,R.anim.slide_out_to_top);
		fragment.setSwipeModel(SwipeBackLayout.SWIPE_TOP);
	}
	private static void addFragment(FragmentActivity activity, BaseSwipeFragment fragment, int inID , int outID) {
		FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
		ft.addToBackStack(null);
		ft.setCustomAnimations(inID,outID,inID,outID);
		ft.add(R.id.slide_fragment, fragment);
		ft.commitAllowingStateLoss();
	}

}

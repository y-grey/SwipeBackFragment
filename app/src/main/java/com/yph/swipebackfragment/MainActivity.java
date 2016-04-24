package com.yph.swipebackfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void addFragmentClick(View v){
        Toast.makeText(this,"!!",Toast.LENGTH_SHORT).show();
        addFragment(new FragmentSlide(),R.anim.slide_in_from_right,R.anim.slide_out_to_right);
    }
    public void addFragment(Fragment fragment , int inID , int outID) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.setCustomAnimations(inID,outID,inID,outID);
        ft.add(R.id.slide_fragment, fragment);
        ft.commitAllowingStateLoss();
    }
}

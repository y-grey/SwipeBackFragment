package com.yph.swipebackfragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yph.view.AddSwipeFragmentUtil;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSwipeFragmentUtil.addFragmentFromRight(MainActivity.this,new FragmentSlide());
            }
        });
    }
}

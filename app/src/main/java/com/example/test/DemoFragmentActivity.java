package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.test.fragment.ProductListFragment;

public class DemoFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_fragment);
        ProductListFragment productListFragment = new ProductListFragment();
        ProductListFragment productListFragment2 = new ProductListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view_tag, productListFragment)
                .add(R.id.fragment_container_view_tag2, productListFragment2)
                .commit();
    }
}
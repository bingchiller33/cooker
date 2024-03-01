package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.adapter.ProductListAdapter;
import com.example.test.entity.ProductEntity;
import com.example.test.repository.ProductRepository;

public class CreateProductActivity extends AppCompatActivity {

    private ProductListAdapter productListAdapter;
    private EditText edtProductName, edtProductPrice;
    ProductRepository productRepository = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        edtProductName = findViewById(R.id.edt_product_name);
        edtProductPrice = findViewById(R.id.edt_product_price);
        productRepository = new ProductRepository(this);
        Button btSaveProductInfo = findViewById(R.id.bt_save_product_info);
        btSaveProductInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = edtProductName.getText().toString().trim();
                String productPriceText = edtProductPrice.getText().toString().trim();
                float productPrice = Float.parseFloat(productPriceText);
                ProductEntity product = new ProductEntity();
                product.setName(productName);
                product.setPrice(productPrice);
                productRepository.createProduct(product);
                Toast.makeText(CreateProductActivity.this, "Create product successfully",
                        Toast.LENGTH_SHORT).show();
                Log.d("CreateProductActivity", "Product added: " + product.getName() + " - " + product.getPrice());

                // Navigate
                setResult(2);
                finish();

            }
        });
    }
}
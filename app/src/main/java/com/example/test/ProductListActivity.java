package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.test.adapter.ProductListAdapter;
import com.example.test.bean.Product;
import com.example.test.entity.ProductEntity;
import com.example.test.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class ProductListActivity extends AppCompatActivity {

    private List<Product> productList = new ArrayList<>();
    private ProductRepository productRepository;

    private ProductListAdapter productListAdapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        getProductList();
//        getProductFromFile();

        Button navBtn = findViewById(R.id.bt_nav);
        productRepository = new ProductRepository(this);
//        ProductListAdapter productListAdapter = new ProductListAdapter(productList, this);
//        productListAdapter.notifyDataSetChanged();
        productListAdapter = new ProductListAdapter(productList, this);
//        convertProductEntity(productRepository.getALlProduct());

        RecyclerView recyclerView = findViewById(R.id.recycle_view_product_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productListAdapter);
        registerForContextMenu(recyclerView);

        navBtn.setOnClickListener(v -> {
            Toast.makeText(ProductListActivity.this, "Cum", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProductListActivity.this, CreateProductActivity.class);
            startActivityForResult(intent, 2);
        });
    }

    private void convertProductEntity(List<ProductEntity> productEntities) {
        if (productEntities == null) {
            return;
        }
        productList.clear();
        for (ProductEntity productEntity : productEntities) {
            Product product = new Product();
            product.setProductId("" + productEntity.getId());
            product.setName(productEntity.getName());
            product.setPrice(productEntity.getPrice());
            productList.add(product);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        productList = productRepository.getALlProduct().stream().map(product -> new Product("" + product.getId(), product.getName(), product.getPrice())).collect(Collectors.toList());
        convertProductEntity(productRepository.getALlProduct());
        productListAdapter.notifyDataSetChanged();

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_context_edit) {
            showEdit();
        } else {
            showDelete();
        }
        return super.onContextItemSelected(item);
    }

    private void showEdit() {
        Toast.makeText(this, "Edit menu selected", Toast.LENGTH_SHORT).show();
    }

    private void showDelete() {

        Toast.makeText(this, "Delete menu selected", Toast.LENGTH_SHORT).show();
    }
        private void getProductList() {
        Random random = new Random(50);
        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setProductId("0" + i);
            product.setName("Product" + i);
            product.setPrice((random.nextFloat()));
            productList.add(product);
        }
    }

//    private void getProductList() {
//
//    }


//    private void getProductFromFile() {
//        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.productlist));
//        while (scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//            StringTokenizer stringTokenizer = new StringTokenizer(line, ", ");
//            Product product = new Product("" + product.getProductId(), product.getName(), product.getPrice());
//            if (stringTokenizer.hasMoreTokens()) {
//                product.setProductId(stringTokenizer.nextToken());
//            }
//            if (stringTokenizer.hasMoreTokens()) {
//                product.setName(stringTokenizer.nextToken());
//            }
//            try {
//                if (stringTokenizer.hasMoreTokens()) {
//                    product.setPrice(Float.parseFloat(stringTokenizer.nextToken()));
//                }
//            } catch (Exception exception) {
//                Log.d(getClass().getSimpleName(), "Parse price is error" + exception.toString());
//            }
//            productList.add(product);
//        }
//    }
}
package com.example.test.repository;

import android.content.Context;

import com.example.test.dao.ProductDAO;
import com.example.test.dao.ProductRoomDatabase;
import com.example.test.entity.ProductEntity;

import java.util.List;

public class ProductRepository {
    private ProductDAO productDAO = null;
    public ProductRepository(Context context) {
        ProductRoomDatabase productRoomDatabase = ProductRoomDatabase.getInstance(context);
        productDAO = productRoomDatabase.productDAO();
    }

    public void createProduct(ProductEntity product) {
        productDAO.insert(product);
    }

    public ProductEntity getProduct(int id) {
        return productDAO.select(id);
    }

    public List<ProductEntity> getALlProduct() {
        return productDAO.selectAll();
    }

    public void deleteProduct(int id) {
        productDAO.delete(id);
    }
}

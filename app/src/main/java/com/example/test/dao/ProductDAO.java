package com.example.test.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.test.entity.ProductEntity;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductEntity product);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ProductEntity... productEntities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(ProductEntity product);

    @Delete
    void delete(ProductEntity product);

    @Query("DELETE FROM ProductEntity WHERE ProductEntity.Product_ID =:productId")
    void delete(int productId);

    @Query("SELECT * FROM ProductEntity P WHERE P.Product_ID =:id")
    ProductEntity select(int id);

    @Query("SELECT * FROM ProductEntity")
    List<ProductEntity> selectAll();


}

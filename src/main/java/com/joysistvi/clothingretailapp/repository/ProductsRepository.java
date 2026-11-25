package com.joysistvi.clothingretailapp.repository;

import com.joysistvi.clothingretailapp.model.Products;

import java.util.List;

public interface ProductsRepository {
    List<Products> findAllActive();
    List<Products> findAllArchived();
    List<Products> findActiveByCategory(int categoryId);
    int countActiveByCategory(int categoryId);
    boolean save(Products product);
    boolean update(Products product);
    boolean deleteById(int id);
    boolean archiveById(int id);
    boolean restoreById(int id);
    List<Products> searchByName(String keyword);

    Products findById(int id);
    //Products findById(int id);
}

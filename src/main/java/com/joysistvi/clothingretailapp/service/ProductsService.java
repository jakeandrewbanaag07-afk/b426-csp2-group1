package com.joysistvi.clothingretailapp.service;

import com.joysistvi.clothingretailapp.model.Products;

import java.util.List;

public interface ProductsService {
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
}

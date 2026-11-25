
package com.joysistvi.clothingretailapp.service;

import com.joysistvi.clothingretailapp.model.Products;
import com.joysistvi.clothingretailapp.repository.ProductsRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;

    // Constructor
    public ProductsServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    // ============================================================
    // RETRIEVE PRODUCTS
    // ============================================================

    @Override
    public List<Products> findAllActive() {
        List<Products> products = productsRepository.findAllActive();
        return products != null ? products : new ArrayList<>();
    }

    @Override
    public List<Products> findAllArchived() {
        List<Products> products = productsRepository.findAllArchived();
        return products != null ? products : new ArrayList<>();
    }

    @Override
    public List<Products> findActiveByCategory(int categoryId) {
        if (categoryId <= 0) {
            System.out.println("Validation Error: Category ID must be greater than 0.");
            return new ArrayList<>();
        }

        List<Products> products =
                productsRepository.findActiveByCategory(categoryId);

        return products != null ? products : new ArrayList<>();
    }

    // ============================================================
    // COUNT PRODUCTS
    // ============================================================

    @Override
    public int countActiveByCategory(int categoryId) {
        if (categoryId <= 0) {
            System.out.println("Validation Error: Category ID must be greater than 0.");
            return 0;
        }

        return productsRepository.countActiveByCategory(categoryId);
    }

    // ============================================================
    // PRODUCT OPERATIONS
    // ============================================================

    @Override
    public boolean save(Products product) {
        if (product == null) {
            System.out.println("Validation Error: Product cannot be null.");
            return false;
        }

        return productsRepository.save(product);
    }

    @Override
    public boolean update(Products product) {
        if (product == null) {
            System.out.println("Validation Error: Product cannot be null.");
            return false;
        }

        if (product.getId() <= 0) {
            System.out.println("Validation Error: Product ID must be greater than 0.");
            return false;
        }

        return productsRepository.update(product);
    }

    @Override
    public boolean deleteById(int id) {
        if (id <= 0) {
            System.out.println("Validation Error: Product ID must be greater than 0.");
            return false;
        }

        return productsRepository.deleteById(id);
    }

    // ============================================================
    // ARCHIVE OPERATIONS
    // ============================================================

    @Override
    public boolean archiveById(int id) {
        if (id <= 0) {
            System.out.println("Validation Error: Product ID must be greater than 0.");
            return false;
        }

        return productsRepository.archiveById(id);
    }

    @Override
    public boolean restoreById(int id) {
        if (id <= 0) {
            System.out.println("Validation Error: Product ID must be greater than 0.");
            return false;
        }

        return productsRepository.restoreById(id);
    }

    // ============================================================
    // SEARCH PRODUCTS
    // ============================================================

    @Override
    public List<Products> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Validation Error: Search keyword cannot be blank.");
            return new ArrayList<>();
        }

        List<Products> results =
                productsRepository.searchByName(keyword.trim());

        return results != null ? results : new ArrayList<>();
    }

    // ============================================================
    // FIND PRODUCT
    // ============================================================

    @Override
    public Products findById(int id) {
        if (id <= 0) {
            System.out.println("Validation Error: Product ID must be greater than 0.");
            return null;
        }

        return productsRepository.findById(id);
    }
}


package com.joysistvi.clothingretailapp.service;

import com.joysistvi.clothingretailapp.model.Categories;
import com.joysistvi.clothingretailapp.repository.CategoriesRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesRepository categoriesRepository;

    // Constructor
    public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    // ============================================================
    // RETRIEVE CATEGORIES
    // ============================================================

    @Override
    public List<Categories> findAllActive(int id) {
        return List.of();
    }

    @Override
    public List<Categories> findAllActive() {
        List<Categories> categories = categoriesRepository.findAllActive();
        return categories != null ? categories : new ArrayList<>();
    }

    @Override
    public List<Categories> findAllArchived() {
        List<Categories> categories = categoriesRepository.findAllArchived();
        return categories != null ? categories : new ArrayList<>();
    }

    // ============================================================
    // CATEGORY OPERATIONS
    // ============================================================

    @Override
    public boolean save(Categories category) {
        if (category == null) {
            System.out.println("Validation Error: Category cannot be null.");
            return false;
        }

        return categoriesRepository.save(category);
    }

    @Override
    public boolean update(Categories category) {
        if (category == null) {
            System.out.println("Validation Error: Category cannot be null.");
            return false;
        }

        return categoriesRepository.update(category);
    }

    @Override
    public boolean deleteById(int id) {
        if (id <= 0) {
            System.out.println("Validation Error: Category ID must be greater than 0.");
            return false;
        }

        return categoriesRepository.deleteById(id);
    }

    // ============================================================
    // ARCHIVE OPERATIONS
    // ============================================================

    @Override
    public boolean archiveById(int id) {
        if (id <= 0) {
            System.out.println("Validation Error: Category ID must be greater than 0.");
            return false;
        }

        return categoriesRepository.archiveById(id);
    }

    @Override
    public boolean restoreById(int id) {
        if (id <= 0) {
            System.out.println("Validation Error: Category ID must be greater than 0.");
            return false;
        }

        return categoriesRepository.restoreById(id);
    }

    // ============================================================
    // SEARCH CATEGORIES
    // ============================================================

    @Override
    public List<Categories> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Validation Error: Search keyword cannot be blank.");
            return new ArrayList<>();
        }

        List<Categories> results =
                categoriesRepository.searchByName(keyword.trim());

        return results != null ? results : new ArrayList<>();
    }

    // ============================================================
    // FIND CATEGORY
    // ============================================================

    @Override
    public Categories findById(int id) {
        if (id <= 0) {
            System.out.println("Validation Error: Category ID must be greater than 0.");
            return null;
        }

        return categoriesRepository.findById(id);
    }
}

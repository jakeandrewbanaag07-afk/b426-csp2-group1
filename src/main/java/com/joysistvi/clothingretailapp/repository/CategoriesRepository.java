package com.joysistvi.clothingretailapp.repository;

import com.joysistvi.clothingretailapp.model.Categories;

import java.util.List;

public interface CategoriesRepository {

    List<Categories> findAllActive(int id);

    List<Categories> findAllActive();

    List<Categories> findAllArchived();
    boolean save(Categories category);
    boolean update(Categories category);
    boolean deleteById(int id);
    boolean archiveById(int id);
    boolean restoreById(int id);
    List<Categories> searchByName(String keyword);
    Categories findById(int id);

}

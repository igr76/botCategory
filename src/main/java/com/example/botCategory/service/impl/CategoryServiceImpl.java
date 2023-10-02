package com.example.botCategory.service.impl;


import com.example.botCategory.model.Category;
import com.example.botCategory.repository.CategoryRepository;
import com.example.botCategory.service.CategoryService;
import org.springframework.stereotype.Service;


/**  Сервис Категорий  */
@Service
public class CategoryServiceImpl implements CategoryService {
    private Category category;
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(Category category, CategoryRepository categoryRepository) {
        this.category = category;
        this.categoryRepository = categoryRepository;
        CategoryRepository.u();
    }

    @Override
    public String getCategoryLevel(int level) {
        return String.join("\n", categoryRepository.findAllByParent(level));
    }

    @Override
    public String getCategoryPreviousLevel(int level) {
       return categoryRepository.findPreviousLevel(level);
    }

    @Override
    public String greatCategory(int level, String name) {
        int maxSeq = categoryRepository.findByParentAndMaxSeg(level);
        Category category1 = new Category();
        category1.setParent_node_id(level);
        category1.setSeq(maxSeq);
        category1.setName(name);
        categoryRepository.save(category1);
        return getCategoryLevel(level);
    }

    @Override
    public String greatNewCategory(int id, String name) {
        Category category1 = new Category();
        category1.setSeq(1);
        category1.setName(name);
        category1.setParent_node_id(id);
        return getCategoryLevel(id);
    }

    @Override
    public void deleteCategory(int id, int level) {
        Category category1 = categoryRepository.findByParentAndSeg(level, id);
        if (category1 != null) {
            categoryRepository.delete(category1);
        }

    }

    @Override
    public int newLevel(Integer level, int value) {
        Category category1 = categoryRepository.findByParentAndSeg(level,value);
        category1.setName(category1.getName() + " >");
        categoryRepository.save(category1);
        return category1.getId();
    }
}

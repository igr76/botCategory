package com.example.botCategory.service.impl;


import com.example.botCategory.exception.ElemNotFound;
import com.example.botCategory.model.Category;
import com.example.botCategory.repository.CategoryRepository;
import com.example.botCategory.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;


/**  Сервис Категорий  */

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;


    @Override
    public String getCategoryLevel(int level) {
        return String.join("\n", categoryRepository.findAllByParent(level));
    }

    @Override
    public String getCategoryPreviousLevel(int level) {
       return "";//categoryRepository.findPreviousLevel(level);
    }

    @Override
    public String greatCategory(int level, String name) {
        int maxSeq =1;
        try {
             maxSeq = categoryRepository.findByParentAndMaxSeg(level).orElseThrow(ElemNotFound::new);
        } catch (ElemNotFound e) {
        }

        Category category1 = new Category();
        category1.setParent(level);
        category1.setSeq(maxSeq++);
        category1.setName(name);
        categoryRepository.save(category1);
        return getCategoryLevel(level);
    }

    @Override
    public String greatNewCategory(int id, String name) {
        Category category1 = new Category();
        category1.setSeq(1);
        category1.setName(name);
        category1.setParent(id);
        return getCategoryLevel(id);
    }

    @Override
    public void deleteCategory(int id, int level) {
        Category category1 = categoryRepository.findByParentAndSeg(level, id).orElseThrow(ElemNotFound::new);
        if (category1 != null) {
            categoryRepository.delete(category1);
        }

    }

    @Override
    public int newLevel(Integer level, int value) {
        Category category1 = categoryRepository.findByParentAndSeg(level,value).orElseThrow(ElemNotFound::new);
        category1.setName(category1.getName() + " >");
        categoryRepository.save(category1);
        return category1.getId();
    }
}

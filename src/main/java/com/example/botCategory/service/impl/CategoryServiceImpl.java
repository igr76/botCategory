package com.example.botCategory.service.impl;


import com.example.botCategory.exception.ElemNotFound;
import com.example.botCategory.model.Category;
import com.example.botCategory.repository.CategoryRepository;
import com.example.botCategory.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**  Сервис Категорий  */

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private Set<Integer> u= new HashSet<>();
    private char ch = ' ';
    private List<String> categoryStringList = new ArrayList<>();
    private List<Category> categoryListTime = new ArrayList<>();

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String getCategory(int level) {
        return String.join("\n", categoryRepository.findAllByParent(level));
    }

    @Override
    public String getCategoryPreviousLevel(int level) {
       return String.join("\n",categoryRepository.findPreviousLevel(level));
    }

//    @Override
//    public String greatCategory(int level, String name) {
//        int maxSeq =1;
//        try {
//             maxSeq = categoryRepository.findByParentAndMaxSeg(level).orElseThrow(ElemNotFound::new);
//        } catch (ElemNotFound e) {
//        }
//
//        Category category1 = new Category();
//        category1.setParent(level);
//        category1.setSeq(maxSeq++);
//        category1.setName(name);
//        categoryRepository.save(category1);
//        return getCategoryLevel(level);
//    }
//
//    @Override
//    public String greatNewCategory(int id, String name) {
//        Category category1 = new Category();
//        category1.setSeq(1);
//        category1.setName(name);
//        category1.setParent(id);
//        return getCategoryLevel(id);
//    }

    @Override
    public void deleteCategory(String textl) {
        try {
            Category category1 = categoryRepository.findByNname(textl).orElseThrow(ElemNotFound::new);
            if (categoryRepository.existsByParent(category1.getId()) == 0) {
                categoryRepository.delete(category1);
            }else {
                List<Category> categoryList = categoryRepository.findAll();
                categoryListTime.clear();
                treeCategory(categoryList,category1.getId());
                for (Category e:categoryListTime) {
                    categoryRepository.delete(e);
                }
            }
        }catch (ElemNotFound e){}


    }

    @Override
    public int newLevel(Integer level, int value) {
        Category category1 = categoryRepository.findByParentAndSeg(level,value).orElseThrow(ElemNotFound::new);
        category1.setName(category1.getName() + " >");
        categoryRepository.save(category1);
        return category1.getId();
    }

    @Override
    public String viewTree() {
        categoryStringList.clear();
        int l=0;
        List<Category> categoryList = categoryRepository.findAll();
        for (int i = 0; i < categoryList.size()-1; i++) {
           if (1==0){tree(categoryList,l);}

           if (u.contains(i)) { break; }else tree(categoryList,i);
        }
        return String.join("\n",categoryStringList);
    }

    @Override
    public void addTwo( String fatherCategory, String childrenCategory) {
        Category fatherCategory1 = categoryRepository.findByNname(fatherCategory).orElseThrow(ElemNotFound::new);
        int maxSeg = categoryRepository.findByParentAndMaxSeg(fatherCategory1.getId()).orElseThrow(ElemNotFound::new);
        Category category = new Category();
        category.setName(childrenCategory);
        category.setParent(fatherCategory1.getParent());
        category.setSeq(maxSeg+1);
        categoryRepository.save(category);
    }

    @Override
    public void addOne( String textCommand) {
        Category category = new Category();
        try {
            Category last = categoryRepository.findLastElement().orElseThrow(ElemNotFound::new);
            category.setName(textCommand);
            category.setParent(last.getParent());
            category.setSeq(last.getSeq()+1);
        } catch (ElemNotFound e) {
            category.setName(textCommand);
            category.setParent(0);
            category.setSeq(0);
        }
       categoryRepository.save(category);
    }

    void tree(List<Category> e,int l) {
        for (int m = 0; m <e.size()-1; m++) {
            if (e.get(m).getParent() == e.get(l).getId()) {
                l=e.get(m).getId();
                ch +=ch;
               categoryStringList.add(ch+e.get(l).getName());
                u.add(l);
            }

        }
        backTree(e,l);
    }

    void backTree(List<Category> e,int l) {
        while (l <= 1) {
            for (int i = 0; i < e.size()-1; i++) {
                if (e.get(i).getParent() == l) {
                    categoryStringList.add(ch+e.get(l).getName());
                    u.add(l);
                }
            }
            ch-=ch; l=e.get(l).getParent();
        }
    }
    void treeCategory(List<Category> e,int l) {
        for (int m = 0; m <e.size()-1; m++) {
            if (e.get(m).getParent() == e.get(l).getId()) {
                l=e.get(m).getId();
                categoryListTime.add(e.get(l));
            }

        }
        backTree(e,l);
    }

    void backTreeCategory(List<Category> e,int l) {
        while (l <= 1) {
            for (int i = 0; i < e.size()-1; i++) {
                if (e.get(i).getParent() == l) {
                    categoryListTime.add(e.get(l));
                }
            }
             l=e.get(l).getParent();
        }
    }
}

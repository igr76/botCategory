package com.example.botCategory.service.impl;


import com.example.botCategory.exception.ElemNotFound;
import com.example.botCategory.model.Category;
import com.example.botCategory.repository.CategoryRepository;
import com.example.botCategory.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**  Сервис Категорий  */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
  //  private Set<Integer> u= new HashSet<>(); // учет пройденных сущностей
    private String ch = "|";
    private List<String> categoryStringList = new ArrayList<>();
    private List<Category> categoryListTime = new ArrayList<>();
    private List<Category> categoryListDelete = new ArrayList<>();
    private List<Category> listChildren = new ArrayList<>();

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

    @Override
    public void deleteCategory(String textl) {
        try {
            Category category1 = categoryRepository.findByNname(textl).orElseThrow(ElemNotFound::new);
            if (categoryRepository.existsByParent(category1.getId()) == 0) {
                categoryRepository.delete(category1);
            }else {
                List<Category> categoryList = categoryRepository.findAll();
                categoryListTime.clear();
                treeCategory(category1);
                for (Category e:categoryListDelete) {
                    categoryRepository.delete(e);
                }
            }
        }catch (ElemNotFound e){}


    }

    @Override
    public String viewTree() {
        categoryStringList.clear();
        int l=0;
        categoryListTime = categoryRepository.findAll();
        log.info("public String viewTree");

        getTree(categoryListTime.get(0));
        return String.join("\n",categoryStringList);
    }

    @Override
    public void addTwo( String fatherCategory, String childrenCategory) {
        int maxSeg;
        Category fatherCategory1 = categoryRepository.findByNname(fatherCategory).orElseThrow(ElemNotFound::new);
        log.info(String.valueOf(fatherCategory1)+ "addTwo fatherCategory1");
        try {
             maxSeg = categoryRepository.findByParentAndMaxSeg(fatherCategory1.getId()).orElseThrow(ElemNotFound::new);
        } catch (ElemNotFound elemNotFound) {
             maxSeg =0;
        }
        Category last = categoryRepository.findLastElement().orElseThrow(ElemNotFound::new);
        Category category = new Category();
        category.setId(last.getId()+1);
        category.setName(childrenCategory);
        category.setParent(fatherCategory1.getId());
        category.setSeq(maxSeg+1);
        log.info(String.valueOf(category)+ "addTwo");
        categoryRepository.save(category);
    }

    @Override
    public void addOne( String textCommand) {
        log.info("void addOne");
        Category category = new Category();
        try {
            Category last = categoryRepository.findLastElement().orElseThrow(ElemNotFound::new);
            category.setId(last.getId()+1);
            category.setName(textCommand);
            if (last.getParent() == 0) {category.setParent(1);
                log.info(String.valueOf(category.getParent()));
            }else category.setParent(last.getParent());
            category.setSeq(last.getSeq()+1);
            log.info(String.valueOf(category));
        } catch (ElemNotFound e) {
            category.setId(1);
            category.setName(textCommand);
            category.setParent(0);
            category.setSeq(0);
        }
       categoryRepository.save(category);
        log.info("addOne  save");
    }

    void getTree(Category category) {
        categoryStringList.add(ch+category.getName());
        for (Category e :
                categoryListTime) {
            if (category.getId() == e.getParent()) {listChildren.add(e);}
        }
        if (listChildren == null) {return;}
        log.info("listChildren est");
        for (Category e :
                listChildren) {
            log.info(e.getName());
            if (checkChildren(e.getId())) {
                listChildren.clear();ch +=ch;
                log.info("recursive");
                getTree(e);
            }
            categoryStringList.add(ch+e.getName());
        }
    }

    boolean checkChildren(int id) {
        for (Category e :
                categoryListTime) {
            if (e.getParent() == id) { log.info("true");return true;}
        }
        log.info("false");
        return false;
    }
    void treeCategory(Category category) {
        categoryListDelete.add(category);
        for (Category e :
                categoryListTime) {
            if (category.getId() == e.getParent()) {listChildren.add(e);}
        }
        if (listChildren == null) {return;}
        log.info("listChildren est");
        for (Category e :
                listChildren) {
            log.info(e.getName());
            if (checkChildren(e.getId())) {
                listChildren.clear();ch +=ch;
                log.info("recursive");
                treeCategory(e);
            }
            categoryListDelete.add(e);
        }
    }

}

package com.example.botCategory.repository;

import com.example.botCategory.model.Category;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryRepository {
    static List<Category> categoryMap = new ArrayList<>();
    public static void u() {

        Category category = new Category(0,1,1,"TV","string");
        categoryMap.add(0,category);
        categoryMap.add(new Category(1,1,2,"monitor",""));
        categoryMap.add(new Category(4,2,1,"mouse",""));

    }
    List<String>  getCat = new ArrayList<>();


    public List<String> findAllByParent(int level) {
        getCat.clear();
        for (int i = 0; i < categoryMap.size(); i++) {
            if (level == categoryMap.get(i).getParent_node_id()) {
                getCat.add(categoryMap.get(i).getSeq() + categoryMap.get(i).getName());
            }
        }
        System.out.println(getCat);
        return getCat;
    }

    public int findByParentAndMaxSeg(int level) {
        int max =1;
        for (int i = 0; i < categoryMap.size(); i++) {
            if (categoryMap.get(i).getParent_node_id()==level) {
                if (max <categoryMap.get(i).getSeq()){
                    max = categoryMap.get(i).getSeq();}
            }
        }
        return 3;
    }

    public void save(Category category1) {
        for (int i = 0; i < categoryMap.size(); i++) {
            if (categoryMap.get(i).getName() == category1.getName()) {
                categoryMap.add(i,category1);
            }
        } categoryMap.add(category1);
    }

    public String findPreviousLevel(int level) {
        for (int i = 0; i < categoryMap.size(); i++) {
            if (level == categoryMap.get(i).getId()) {
                level = categoryMap.get(i).getSeq();
                break;
            }
        }
        return  String.join("\n",findAllByParent(level));
    }

    public Category findByParentAndSeg(Integer level, int id) {
        for (int i = 0; i < categoryMap.size(); i++) {
            if (categoryMap.get(i).getParent_node_id() == level && categoryMap.get(i).getSeq() == id) {
               return categoryMap.get(i);
            }
        }
        return null;
    }

    public void delete(Category category1) {
        categoryMap.remove(category1.getId());
    }
}

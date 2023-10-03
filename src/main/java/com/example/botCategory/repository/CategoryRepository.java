package com.example.botCategory.repository;

import com.example.botCategory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(nativeQuery = true, value = "SELECT name FROM сategoryes WHERE parent = (SELECT DISTINCT parent FROM сategoryes WHERE id= :level )")
    CharSequence findAllByParent(int level);

    @Query(nativeQuery = true, value = "SELECT parent FROM сategoryes WHERE id = :level")
    Optional<String> findPreviousLevel(int level);
    @Query(nativeQuery = true, value = "SELECT seq FROM сategoryes WHERE parent = :level AND MAX(seq)")
    Optional<Integer> findByParentAndMaxSeg(int level);
    @Query(nativeQuery = true, value = "SELECT * FROM сategoryes WHERE parent = :level AND seq = :id")
    Category findByParentAndSeg(int level, int id);

//    static List<Category> categoryMap = new ArrayList<>();
//    public static void u() {
//
//        Category category = new Category(0,1,1,"TV","string");
//        categoryMap.add(0,category);
//        categoryMap.add(new Category(1,1,2,"monitor",""));
//        categoryMap.add(new Category(4,2,1,"mouse",""));
//
//    }
//    List<String>  getCat = new ArrayList<>();
//
//
//    public List<String> findAllByParent(int level) {
//        getCat.clear();
//        for (int i = 0; i < categoryMap.size(); i++) {
//            if (level == categoryMap.get(i).getParent_node_id()) {
//                getCat.add(categoryMap.get(i).getSeq() + categoryMap.get(i).getName());
//            }
//        }
//        System.out.println(getCat);
//        return getCat;
//    }
//
//    public int findByParentAndMaxSeg(int level) {
//        int max =1;
//        for (int i = 0; i < categoryMap.size(); i++) {
//            if (categoryMap.get(i).getParent_node_id()==level) {
//                if (max <categoryMap.get(i).getSeq()){
//                    max = categoryMap.get(i).getSeq();}
//            }
//        }
//        return max;
//    }
//
//    public void save(Category category1) {
//        for (int i = 0; i < categoryMap.size(); i++) {
//            if (categoryMap.get(i).getName() == category1.getName()) {
//                categoryMap.add(i,category1);
//            }
//        } categoryMap.add(category1);
//    }
//
//    public String findPreviousLevel(int level) {
//        for (int i = 0; i < categoryMap.size(); i++) {
//            if (level == categoryMap.get(i).getId()) {
//                level = categoryMap.get(i).getSeq();
//                break;
//            }
//        }
//        return  String.join("\n",findAllByParent(level));
//    }
//
//    public Category findByParentAndSeg(Integer level, int id) {
//        for (int i = 0; i < categoryMap.size(); i++) {
//            if (categoryMap.get(i).getParent_node_id() == level && categoryMap.get(i).getSeq() == id) {
//               return categoryMap.get(i);
//            }
//        }
//        return null;
//    }
//
//    public void delete(Category category1) {
//        categoryMap.remove(category1.getId());
//    }
}

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
    @Query(nativeQuery = true, value = "SELECT MAX(seq) FROM сategoryes WHERE parent = :level ")
    Optional<Integer> findByParentAndMaxSeg(int level);
    @Query(nativeQuery = true, value = "SELECT * FROM сategoryes WHERE parent = :level AND seq = :id")
    Optional<Category> findByParentAndSeg(int level, int id);

}

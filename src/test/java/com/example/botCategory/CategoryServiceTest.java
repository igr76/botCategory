package com.example.botCategory;

import com.example.botCategory.model.Category;
import com.example.botCategory.repository.CategoryRepository;
import com.example.botCategory.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    CategoryService categoryService;
    @Mock
    CategoryRepository categoryRepository;
    @Test
    void viewTreeTest() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(getCategory());
        System.out.println(categoryList.toString());
        when(categoryRepository.findAll()).thenReturn(categoryList);
        assertThat(categoryService.viewTree()).isEqualTo("TV");
        verify(categoryRepository, times(1)).findAllById(any());
    }
    @Test
    void addTwoTest() {
        Category category1 = getCategory();
    when(categoryRepository.findByNname(anyString())).thenReturn(Optional.of(category1));
    when(categoryRepository.findByParentAndMaxSeg(anyInt())).thenReturn(Optional.of(1));
    when(categoryRepository.findAllByParent(anyInt())).thenReturn("TV");
    when(categoryRepository.save(any(Category.class))).thenReturn(any(Category.class));
       // when(categoryRepository.save(any(Category.class))).thenReturn(category1);
     //   doNothing().when(categoryRepository).save(any(Category.class));
   // assertThat(categoryService.addOne("TV")).isEqualTo("TV");
    verify(categoryRepository, times(3)).findAllById(any());
    }
    @Test
    void addOneTest() {
        Category category1 = getCategory();
        when(categoryRepository.findLastElement()).thenReturn(Optional.of(category1));
        when(categoryRepository.save(any(Category.class))).thenReturn(any(Category.class));
        verify(categoryRepository, times(1)).findAllById(any());
    }

    @Test
    void deleteCategoryTest() {
        Category category1 = getCategory();
        when(categoryRepository.findByParentAndSeg(anyInt(),anyInt())).thenReturn(Optional.of(category1));
        when(categoryRepository.findAllByParent(anyInt())).thenReturn("TV");
        doNothing().when(categoryRepository).deleteById(anyInt());
       verify(categoryRepository, times(3)).findAllById(any());
    }
    Category  getCategory() {
        Category category1 = new Category();
        category1.setSeq(1);
        category1.setName("TV");
        category1.setParent(1);
        return category1;
    }

}

package com.example.botCategory.service;

/**  Сервис Категорий  */
public interface CategoryService {
      public String getCategory(int id);
    public String  getCategoryPreviousLevel(int level);

    public void deleteCategory(String text);


    String viewTree();

    void addTwo( String fatherCategory, String childrenCategory);

    void addOne( String textCommand);

}

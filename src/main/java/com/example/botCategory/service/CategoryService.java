package com.example.botCategory.service;

/**  Сервис Категорий  */
public interface CategoryService {
    public String getCategory(int id);
    public String  getCategoryPreviousLevel(int level);
//    public String  greatCategory(int level,String name);
//    public String  greatNewCategory(int id,String name);
    public void deleteCategory(String text);

    public int newLevel(Integer level, int value);

    String viewTree();

    void addTwo( String fatherCategory, String childrenCategory);

    void addOne( String textCommand);

}

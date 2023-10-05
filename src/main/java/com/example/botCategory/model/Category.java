package com.example.botCategory.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;



/**    Сущность дерева категорий     */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "сategoryes")
public class Category {
    /**    Идентификатор узла     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    /**    номер родительского узла     */
    @Column(name = "parent")
    private int parent;
    /**    номер наследника     */
    @Column(name = "seq")
    private int seq;
    /**    Имя узла     */
    @Column(name = "name")
    private String name;
    /**    Содержание узла     */
    @Column(name = "filling")
    private String filling;
}

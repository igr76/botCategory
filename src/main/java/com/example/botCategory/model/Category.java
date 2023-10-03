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
    private Integer id;
    /**    номер родительского узла     */

    private int parent;
    /**    номер наследника     */

    private int seq;
    /**    Имя узла     */

    private String name;
    /**    Содержание узла     */

    private String filling;
}

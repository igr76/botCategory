package com.example.botCategory.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**    Сущность дерева категорий     */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Component
public class Category {
    /**    Идентификатор узла     */

    private Integer id;
    /**    номер родительского узла     */

    private int parent_node_id;
    /**    номер наследника     */

    private int seq;
    /**    Имя узла     */

    private String name;
    /**    Содержание узла     */

    private String filling;
}

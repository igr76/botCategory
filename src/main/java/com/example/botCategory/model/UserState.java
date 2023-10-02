package com.example.botCategory.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
/**    Сущность состояния пользователей     */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Component
public class UserState {
    /**    ChatId пользователя     */

    private long id;
    /**    уровень меню пользователя     */

    private int level;
    /**    последние действия пользователя     */

    private String lastAction;
}

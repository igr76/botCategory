package com.example.botCategory.controller;



import com.example.botCategory.repository.CategoryRepository;
import com.example.botCategory.service.CategoryService;
import com.example.botCategory.service.UpdateProducer;
import com.example.botCategory.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.botCategory.controller.AllText.*;


/**  Контроллер Категорий  */
@Slf4j
@Component
public class UpdateController {
    private  TelegramBot telegramBot;
    private MessageUtils messageUtils;
    private CategoryService categoryService;
    private CategoryRepository categoryRepository;
    private UpdateProducer updateProducer;

    public UpdateController(@Lazy TelegramBot telegramBot, MessageUtils messageUtils,
                            CategoryService categoryService) {
        this.telegramBot = telegramBot;
        this.messageUtils = messageUtils;
        this.categoryService = categoryService;
    }


//    private void setUnssupportedMessageTipeView(Update update) {
//        var sendMessage = messageUtils.generateSendMessageWithText(update,
//                "Неподдерживаемый тип сообщения");
//                setView(sendMessage);
//    }



    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Сообщение пустое");
            messageUtils.generateSendMessageWithText(update,
                    "Сообщение пустое");

            return;
        }
        if (update.getMessage() != null) {
            distributeMessagesByType(update);
        } else {log.error("Получено сообщение неподдерживаемого типа" + update);}
    }

    private void distributeMessagesByType(Update update) {
        log.info("distributeMessagesByType");
        var message = update.getMessage();
        if (message.hasText()) {
            processTextMessage(update);
        } else if (message.hasDocument()) {
            processDocMessage(update);
        } else if (message.hasPhoto()) {
            processPhotoMessage(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }
    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения!");
        setView(sendMessage);
    }
    private void setView(SendMessage sendMessage) {
      telegramBot.sendAnswerMessage(sendMessage);
    }

    private void processPhotoMessage(Update update) {
        updateProducer.produce(PHOTO_MESSAGE_UPDATE,update);
        setFileIsReceivedView(update);
    }
    private void processDocMessage(Update update) {
        updateProducer.produce(DDC_MESSAGE_UPDATE,update);
        setFileIsReceivedView(update);
    }
    private void processTextMessage(Update update) {
        String message = update.getMessage().getText();
        }


    private void setFileIsReceivedView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Файл получен! Обрабатывается...");
        setView(sendMessage);
    }
}

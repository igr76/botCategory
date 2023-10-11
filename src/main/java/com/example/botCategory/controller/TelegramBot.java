package com.example.botCategory.controller;

import com.example.botCategory.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static com.example.botCategory.controller.AllText.*;
@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private  UpdateController updateController;
    private CategoryService categoryService;

    public TelegramBot(@Lazy UpdateController updateController, CategoryService categoryService) {
        this.updateController = updateController;
        this.categoryService = categoryService;
    }

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
    public    String LAST_ACTION ;
    public    Integer level =1;
 //   private  String[] textArray = new  String[5] ;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String messageTextAfter = update.getMessage().getText();
            String[] textArray = messageTextAfter.split(" ");
            String messageText = textArray[0];
            log.info(messageText );
            long chatId = update.getMessage().getChatId();
            switch (messageText) {
                case START -> {
                    String userName = update.getMessage().getChat().getUserName();
                    startCommand(chatId, userName);
                }
                case ADD -> greatCommand(textArray);
       //         case GET -> getCommand(chatId);
                case DELETE -> deleteCommand(chatId,textArray[1]);
                case HELP -> helpCommand(chatId);
                case VIEW_TREE -> viewTreeCommand(chatId);
                default -> unknownCommand(update);
            }
        }
    }


    private void viewTreeCommand(long chatId) {log.info("viewTree");
        sendMessage(chatId, categoryService.viewTree());
    }


    private void unknownCommand(Update update) {
        log.info("unknownCommand");
        updateController.processUpdate(update);

           }


    private void helpCommand(long chatId) {
        var text = """
                Справочная информация по боту
                
               Здесь Вы сможете создавать и удалять категории товаров.
                
                Для этого воспользуйтесь командами:
                /viewTree - получить список категорий
                /addElement <название категории> - создать категорию
                /addElement <родительский элемент> <дочерний элемент> - создать категорию в категории
                (писать без пробелов категории)
                /removeElement <название категории>  - удалить категорию
             
                """;
        sendMessage(chatId, text);
    }

    private void deleteCommand(long chatId,String text) {
        if (text == null) {    sendMessage(chatId, "введите удаляемый элемент");    }
        categoryService.deleteCategory(text);
    }

    private void greatTwoCommand(String fatherCategory,String childrenCategory) {
        categoryService.addTwo(fatherCategory,childrenCategory);
    }

    private void greatCommand(String[] text) {
       // String textCommand = text.substring(12);
        log.info("greatCommand" +text[1]);
        if (text.length > 2) {greatTwoCommand(text[1],text[2]);
        }else   categoryService.addOne(text[1]);

    }


    private void startCommand(long chatId, String userName) {
        var text = """
                Добро пожаловать в бот, %s!
                
                Здесь Вы сможете создавать и удалять категории товаров.
                
                Для этого воспользуйтесь минимальными командами:
                /viewTree - получить список категорий
                /addElement <название категории> - создать категорию
                /removeElement <название элемента> - удалить категорию
                
                Дополнительные команды:
                /help - получение справки обо всех командах
                """;
        var formattedText = String.format(text, userName);
        sendMessage(chatId, formattedText);
    }

    @Override
    public String getBotToken() {
        return  botToken;
    }
    @Override
    public String getBotUsername() {
        return botName;
    }
    private void sendMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }
    public void sendAnswerTextMessage(Long chatId,String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendAnswerMessage(sendMessage);
    }
    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                log.error(String.valueOf(e));
            }
        }
    }
}

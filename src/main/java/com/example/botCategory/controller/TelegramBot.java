package com.example.botCategory.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
    @Override
    public void onUpdateReceived(Update update) {
        var o = update.getMessage();
        System.out.println(o.getText());
    }
    @Override
    public String getBotToken() {
        return  botToken;
    }
    @Override
    public String getBotUsername() {
        return botName;
    }
}

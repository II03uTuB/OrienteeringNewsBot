package ru.medvedev.bot;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import static ru.medvedev.bot.Messages.*;


public class Bot extends TelegramLongPollingBot {
    private static final Logger LOGGER = Logger.getLogger(Bot.class);
    final int RECONNECT_PAUSE = 10000;

    @Setter
    @Getter
    String userName;

    @Setter
    @Getter
    String token;

    public Bot(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }
    private void message(String typeOfMessage, Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        //message.setText(infoMessage());
        message.setText(typeOfMessage);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        LOGGER.debug("Receive new Update. updateID: " + update.getUpdateId());

        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText();
        if (inputText.startsWith("/start")) {
            message(startString,chatId);
        }
        if (inputText.startsWith("/help")) {
            message(helpString, chatId);
        }
        if (inputText.startsWith("/admin")) {
            message(adminString, chatId);
        }
        if (inputText.startsWith("/week")) {
            message(weekString, chatId);
        }
        if (inputText.startsWith("/links")) {
            message(linksString, chatId);
        }

    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
    public void botConnect() {
        try{
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            telegramBotsApi.registerBot(this);
            LOGGER.info("TelegramAPI started. Look for messages");
        } catch (TelegramApiRequestException e) {
            LOGGER.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 +
            "sec and try again. Error: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }
}

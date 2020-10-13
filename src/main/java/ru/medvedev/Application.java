package ru.medvedev;

import org.telegram.telegrambots.ApiContextInitializer;
import ru.medvedev.bot.Bot;

import java.util.logging.Logger;

public class Application {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(Application.class));

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Bot news_bot = new Bot("orienteering_news_bot", "1228849064:AAG3H1x6tCM5lOki1FNrPDtmRTwKAGbUbB4");
        news_bot.botConnect();
    }

}

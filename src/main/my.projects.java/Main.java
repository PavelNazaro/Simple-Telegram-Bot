package main.my.projects.java;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static final String CONFIG_PROPERTIES = "config.properties";
    public static void main(String[] args) {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            if (properties.isEmpty()) {
                System.out.println("Main Error 1: Properties is EMPTY!");
                return;
            }

            String botToken = properties.getProperty("botToken");
            String botUsername = properties.getProperty("botUsername");

            if (botToken.isEmpty() || botUsername.isEmpty()) {
                System.out.println("Main Error 2: Any of properties is EMPTY!");
                return;
            }

            TgBot bot = new TgBot(botToken, botUsername);

            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
            System.out.println("Bot started successfully!");
        } catch (IOException | TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

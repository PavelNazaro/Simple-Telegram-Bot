package my.projects.java;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.*;
import java.util.Properties;

public class Main {
    public static final String CONFIG_PROPERTIES = "config.properties";
    public static void main(String[] args) {
        File configFile = new File(CONFIG_PROPERTIES);
        if (!configFile.exists()){
            System.out.println(String.format("Error: File %s not found", CONFIG_PROPERTIES));
            return;
        }

        try(InputStream configStream = new FileInputStream(configFile)){
            Properties properties = new Properties();
            properties.load(configStream);
            if (properties.isEmpty()) {
                System.out.println("Error: Properties is EMPTY!");
                return;
            }

            String botToken = properties.getProperty("botToken");
            String botUsername = properties.getProperty("botUsername");

            if (botToken.isEmpty() || botUsername.isEmpty()) {
                System.out.println("Error: Any of properties is EMPTY!");
                return;
            }

            TgBot bot = new TgBot(botToken, botUsername);

            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
            System.out.println(String.format("Bot %s started successfully!", botUsername));
        } catch (IOException | TelegramApiException e){
            System.out.println(String.format("Error: Error reading %s: %s", CONFIG_PROPERTIES, e.getMessage()));
        }
    }
}

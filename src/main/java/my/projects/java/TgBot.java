package my.projects.java;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TgBot extends TelegramLongPollingBot {
    private final String botToken;
    private final String botUsername;
    private long chatId;

    public TgBot(String botToken, String botUsername) {
        this.botToken = botToken;
        this.botUsername = botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            if (!message.hasText()) {
                System.out.println("MyBot Error 1: Message is EMPTY!");
                return;
            }

            chatId = message.getChatId();
            String text = message.getText();

            if (text.isEmpty()) {
                System.out.println("MyBot Error 2: Message is EMPTY!");
                return;
            }

            System.out.println("Message received: " + text);

            sendMessageToBot(text);
        }
    }

    protected void sendMessageToBot(String response) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(response);
        sendMessage.setChatId(chatId);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("MyBot Error 3: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}

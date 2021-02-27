import org.apache.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Properties;

public class AikamBot extends TelegramLongPollingBot {

    private final Logger logger = Logger.getLogger(AikamBot.class);

    private final String token;
    private final String bot_name;

    public AikamBot() throws IOException {
        Properties properties = Helper.getProperties();
        this.token = properties.getProperty("TOKEN");
        this.bot_name = properties.getProperty("BOT_NAME");
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if(message != null && message.hasText()) {
            String[] text = Helper.getTextToAnswer(message.getText());
            try {
                for(String tx : text) {
                    if(Helper.needToAnswer(tx)) {
                        SendMessage sendMessage = new SendMessage();
                        //в какой чат отвечать
                        sendMessage.setChatId(message.getChatId().toString());
                        //на какое сообщение отвечать
                        //sendMessage.setReplyToMessageId(message.getMessageId());
                        sendMess(sendMessage, tx);
                    }
                }
            } catch (TelegramApiException e) {
                logger.error(String
                        .format("ERROR: cant sending message from user: %s, text: \"%s\"",
                                message.getFrom(),
                                message.getText()), e);
            } catch (RuntimeException e) {
                logger.error("ERROR getting issue by id", e);
            } catch (IOException e) {
                logger.error("cant load Jira URL from props.properties");
            }
        }
    }

    private void sendMess(SendMessage sendMessage, String text) throws TelegramApiException, IOException {
        sendMessage.enableHtml(true);
        String answer = Helper.makeAnswer(text);
        sendMessage.setText(answer);
        execute(sendMessage);
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    @Override
    public String getBotUsername() {
        return this.bot_name;
    }
}

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class Start {

    private static final Logger logger = Logger.getLogger(Start.class);

    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new AikamBot());
            System.out.println("Aikam-bot is running");
        } catch (TelegramApiException | IOException e) {
            logger.error("ERROR: error work bot", e);
        }
    }
}

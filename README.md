# telegram-bot
Присылаешь боту id запроса Jira/ссылку на запрос, телеграмм бот отвечает гиперссылкой + краткое описание запроса Jira.


#сборка
Из корня проекта в консоли вызываем коменду  mvn clean package

#запуск
1. Из созданной после сборки директории target копируем jar архив t-bot-aikam-jar-with-dependencies.jar в необходимую Вам директорию

2. В ту же директорию копируем .properties файлы из \src\main\resources

3. Заполняем необходимыми данными файл props.properties

TOKEN = "токен телеграмм бота"
BOT_NAME = "имя бота"
URI_JIRA = uri вида https://jira...ru:port/ (url домена вашей Jira)
JIRA_LOGIN = "логин от аккаунта Jira"
JIRA_PASSWORD = "логин от аккаунта Jira"
URL = https://jira...ru:port/browse/ (url домена вашей Jira + /brouse)

4. Запускаем бота из консоли из директории, где лежит .jar командой   java -jar t-bot-aikam-jar-with-dependencies.jar

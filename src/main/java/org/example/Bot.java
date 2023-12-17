package org.example;

import org.example.question.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;

public class Bot extends TelegramLongPollingBot {

    private HashMap<Long, UserData> users;
    private ArrayList<AbstractQuestion> questions;

    //Создать обьект класса - конструктор для записи пользователей и номеров их вопросов
    public Bot() {
        users = new HashMap<>();
        questions = new ArrayList<>();
        questions.add(new JavaQuestion());
        questions.add(new SQL_Question());
        questions.add(new HttpQuestion());
        questions.add(new GitQuestion());
    }

    // Регистрация бота
    @Override
    public String getBotUsername() {
        return "JavaKiwiBot";
    }

    @Override
    public String getBotToken() {
        return "add token";
    }

    //отправка сообщения пользователю
    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage(); //получение сообщений
        String text = message.getText();
        //получение id пользователя
        long userId = message.getFrom().getId();

        if (text.equals("/start")) {
            sendText(userId, "Привет! Это тест навыков по Java.");
            users.put(userId, new UserData()); // Создаются новые данные для пользователя
            String question = questions.get(0).getQuestion(); // 1 вопрос
            sendText(userId, question);
        } else if (users.get(userId).getQuestionNumber() == questions.size()) {
            sendText(userId, "Тест завершён. Для перезапуска бота используйте команду /start");
        } else {
            UserData userData = (users.get(userId));
            int questionNumber = userData.getQuestionNumber();
            boolean result = questions.get(questionNumber).checkAnswer(text);
            int score = userData.getScore();
            int nextQuestion = questionNumber + 1;
            userData.setScore(score + (result ? 1 : 0));
            userData.setQuestionNumber(nextQuestion);

            if (nextQuestion == questions.size()) { // Если вопрос последний, то выводится рейтинг
                sendText(userId, "Ваш рейтинг: " + users.get(userId).getScore()
                        + " из " + questions.size());
            } else {
                String question = questions.get(nextQuestion).getQuestion();
                sendText(userId, question);
            }
        }
    }
}







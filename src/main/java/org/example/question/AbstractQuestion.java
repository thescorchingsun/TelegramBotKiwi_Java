package org.example.question;

public abstract class AbstractQuestion {
    //abstract класс, содержит хотя бы один абстрактный метод
    private String question;

    // Конструктор для хранения вопроса
    public AbstractQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public abstract boolean checkAnswer(String answer);
    // abstract метод, который не содержит никакой реализации
}

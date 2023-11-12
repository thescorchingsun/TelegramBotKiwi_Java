package org.example.question;

public class JavaQuestion extends AbstractQuestion {

    public JavaQuestion() {
        super("Сколько примитивов в языке программирования Java?");
    }

    @Override
    public boolean checkAnswer(String answer) {
        return answer.equals("8");
    }


}

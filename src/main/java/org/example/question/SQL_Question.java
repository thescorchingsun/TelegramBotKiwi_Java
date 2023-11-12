package org.example.question;

public class SQL_Question extends AbstractQuestion {

    public SQL_Question() {
        super("Сколько в реляционных (SQL) базах данных существует типов связей между таблицами?");
    }

    @Override
    public boolean checkAnswer(String answer) {
    return answer.equals("3");
}
}

package parser;

import parser.OnCompleted;

public class Completed implements OnCompleted {

    @Override
    public void onCompleted(Object sender) {
        System.out.println("Загрузка закончена");
    }
}
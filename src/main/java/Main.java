import habr.*;
import model.Article;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        int start = 1;
        int end = 10;

        ParserWorker<ArrayList<Article>> parser = new ParserWorker<>(new HabrParser(), new HabrSettings(start, end));

        parser.onCompletedList.add(new Completed());
        parser.onNewDataList.add(new NewData());

        parser.start();
        Thread.sleep(10000);
        parser.abort();
    }
}
package parser;

import model.Article;
import parser.OnNewDataHandler;

import java.util.ArrayList;

public class NewData implements OnNewDataHandler<ArrayList<Article>> {

    @Override
    public void onNewData(Object sender, ArrayList<Article> args) {
        for (Article article : args) {
            System.out.println(article);
        }
    }
}
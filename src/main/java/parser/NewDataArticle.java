package parser;

import model.Article;

import java.util.ArrayList;

public class NewDataArticle implements OnNewDataHandler<ArrayList<Article>> {

    @Override
    public void onNewData(Object sender, ArrayList<Article> args) {
        for (Article article : args) {
            System.out.println(article);
        }
    }
}
package parser;

import model.Article;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NewDataArticle implements OnNewDataHandler<ArrayList<Article>> {

    @Override
    public void onNewData(Object sender, @NotNull ArrayList<Article> args) {
        for (Article article : args) {
            System.out.println(article);
        }
    }
}
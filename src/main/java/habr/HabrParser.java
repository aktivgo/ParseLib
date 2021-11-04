package habr;

import model.Article;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.Parser;

import java.io.IOException;
import java.util.ArrayList;

public class HabrParser implements Parser<ArrayList<Article>> {

    @Override
    public ArrayList<Article> Parse(Document document) throws IOException {

        ArrayList<Article> articles = new ArrayList<>();
        Elements articlesEl = document.getElementsByTag("article");

        for (Element article : articlesEl) {
            String title = article.getElementsByClass("tm-article-snippet__title-link").text();
            String text = article.getElementsByClass("article-formatted-body").text();
            String image = article.getElementsByClass("tm-article-snippet__lead-image").attr("src");
            articles.add(new Article(title, text, image));
        }

        return articles;
    }
}
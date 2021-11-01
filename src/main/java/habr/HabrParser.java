package habr;

import model.Article;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.Parser;

import java.util.ArrayList;

public class HabrParser implements Parser<ArrayList<String>> {

    @Override
    public ArrayList<Article> Parse(Document document) {

        ArrayList<Article> articles = new ArrayList<>();
        Elements articlesEl = document.getElementsByTag("article");

        for (Element article : articlesEl) {
            String title = article.getElementsByClass("tm-article-snippet__title-link").text();
            String text = article.getElementsByClass("article-formatted-body article-formatted-body_version-2").text();
            String image = article.getElementsByClass("tm-article-snippet__lead-image").attr("src");
            articles.add(new Article(title, text, image));
        }

        return articles;
    }
}
package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HtmlLoader {

    String url;

    public HtmlLoader() {
        url = ParserSettings.BASE_URL + "/" + ParserSettings.PREFIX;
    }

    public Document GetSourceByPageId(int id) throws IOException {
        String currentUrl = url.replace("{CurrentId}", Integer.toString(id));
        return Jsoup.connect(currentUrl).get();
    }

}
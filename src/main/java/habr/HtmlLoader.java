package habr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import parser.ParserSettings;

import java.io.IOException;

public class HtmlLoader {

    String url;

    public HtmlLoader(ParserSettings settings) {
        url = HabrSettings.BASE_URL + "/" + HabrSettings.PREFIX;
    }

    public Document GetSourceByPageId(int id) throws IOException {
        String currentUrl = url.replace("{CurrentId}", Integer.toString(id));
        return Jsoup.connect(currentUrl).get();
    }

}
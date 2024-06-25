package demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class OpenGraphTagGetter {

    public static String getTitle(String html) {
        String title = MetaTagContentGetter.getMetaTagContent(html, "og:title");
        if (title == null || title.isEmpty()) {
            Document doc = Jsoup.parse(html);
            Element titleTag = doc.select("title").first();
            title = titleTag != null ? titleTag.text() : "";
        }
        return title;
    }

    public static String getDescription(String html) {
        String description = MetaTagContentGetter.getMetaTagContent(html, "og:description");
        if (description == null || description.isEmpty()) {
            description = MetaTagContentGetter.getMetaTagContent(html, "description", "name");
        }
        return description;
    }

    public static String getImageUrl(String html) {
        return MetaTagContentGetter.getMetaTagContent(html, "og:image");
    }
    
    public static String getImageUrlLast(String html) {
        return MetaTagContentGetter.getLastMetaTagContent(html, "og:image");
    }
}
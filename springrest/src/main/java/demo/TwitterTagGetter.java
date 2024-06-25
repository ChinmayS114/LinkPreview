package demo;

public class TwitterTagGetter {

    public static String getTitle(String doc) {
        String title = MetaTagContentGetter.getMetaTagContent(doc.toString(), "twitter:title");
        return title;
    }

    public static String getDescription(String doc) {
        String description = MetaTagContentGetter.getMetaTagContent(doc.toString(), "twitter:description");
        return description;
    }

    public static String getImageUrl(String doc) {
        String imageUrl = MetaTagContentGetter.getMetaTagContent(doc.toString(), "name", "twitter:image");
        if (imageUrl == null || imageUrl.isEmpty()) {
            imageUrl = MetaTagContentGetter.getMetaTagContent(doc.toString(), "name", "twitter:image:src");
        }
        return imageUrl;
    }

    public static String getCard(String doc) {
        String card = MetaTagContentGetter.getMetaTagContent(doc.toString(), "name", "twitter:card");
        return card;
    }
}
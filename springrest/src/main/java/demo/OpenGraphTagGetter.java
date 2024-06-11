package demo;


public class OpenGraphTagGetter {

    public static String getTitle(String doc) {
        String title = MetaTagContentGetter.getMetaTagContent(doc.toString(), "og:title");
        if (title == null || title.isEmpty()) {
            title = MetaTagContentGetter.getMetaTagContent(doc.toString(), "title", "name");
        }
        return title;
    }

    public static String getDescription(String doc) {
        String description = MetaTagContentGetter.getMetaTagContent(doc.toString(), "og:description");
        if (description == null || description.isEmpty()) {
            description = MetaTagContentGetter.getMetaTagContent(doc.toString(), "description", "name");
        }
        return description;
    }

    public static String getImageUrl(String doc) {
        String imageUrl = MetaTagContentGetter.getMetaTagContent(doc.toString(), "og:image");
        return imageUrl;
    }
}

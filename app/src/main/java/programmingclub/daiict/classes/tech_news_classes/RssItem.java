package programmingclub.daiict.classes.tech_news_classes;

/**
 * Created by omkar13 on 9/26/2015.
 */
public class RssItem {
    private final String title;
    private final String link;

    public RssItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

}

package ftn.com.trippleaproject.domain.articlepart;

public class ImageNewsArticlePart extends NewsArticlePart {

    private String url;

    public ImageNewsArticlePart(int id, String url) {
        super(id);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ImageNewsArticlePartDb{" +
                "url='" + url + '\'' +
                "} " + super.toString();
    }
}

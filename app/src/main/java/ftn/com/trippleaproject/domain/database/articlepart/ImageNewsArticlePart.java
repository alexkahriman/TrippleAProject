package ftn.com.trippleaproject.domain.database.articlepart;


public class ImageNewsArticlePart extends NewsArticlePart {

    private String url;

    public ImageNewsArticlePart(String url) {
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
        return "ImageNewsArticlePart{" +
                "url='" + url + '\'' +
                "} " + super.toString();
    }
}

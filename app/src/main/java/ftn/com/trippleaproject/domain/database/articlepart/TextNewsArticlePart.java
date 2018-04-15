package ftn.com.trippleaproject.domain.database.articlepart;


public class TextNewsArticlePart extends NewsArticlePart {

    private String text;

    public TextNewsArticlePart(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TextNewsArticlePart{" +
                "text='" + text + '\'' +
                "} " + super.toString();
    }
}

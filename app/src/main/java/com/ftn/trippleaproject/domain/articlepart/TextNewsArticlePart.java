package com.ftn.trippleaproject.domain.articlepart;

public class TextNewsArticlePart extends NewsArticlePart {

    private String text;

    public TextNewsArticlePart(int id, String text) {
        super(id);
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
        return "TextNewsArticlePartDb{" +
                "text='" + text + '\'' +
                "} " + super.toString();
    }
}

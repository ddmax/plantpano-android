package net.ddmax.plantpano.ui.custom.viewpagercard;


public class CardItem {

    private String textString;
    private String titleString;

    public CardItem(String title, String text) {
        titleString = title;
        textString = text;
    }

    public String getText() {
        return textString;
    }

    public String getTitle() {
        return titleString;
    }
}

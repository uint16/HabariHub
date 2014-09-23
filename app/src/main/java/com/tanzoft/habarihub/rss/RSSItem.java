package com.tanzoft.habarihub.rss;

/**
 * Created by Damas on 9/22/14.
 */
public class RSSItem {

    private String title;
    private String link;

    public void setTitle(String _title){
        this.title = _title;
    }

    public void setLink(String _link) {
        this.link = _link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}

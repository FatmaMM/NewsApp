package com.example.android.newsapp;


public class News {
    private String section_name;
    private String webPublicationDate;
    private String webTitle;
    private String webUrl;

    public News(String section_name, String webPublicationDate, String webTitle, String webUrl) {
        this.section_name = section_name;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
    }


    public String getSection_name() {
        return section_name;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

}

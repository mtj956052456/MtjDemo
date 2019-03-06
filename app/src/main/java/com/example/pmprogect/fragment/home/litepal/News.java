package com.example.pmprogect.fragment.home.litepal;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * @author mtj
 * @time 2018/10/31 2018 10
 * @des
 */

public class News extends LitePalSupport{

    private int id;

    private int page;

    private String title;

    private String content;

    @Column(ignore = true)
    public String heitory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

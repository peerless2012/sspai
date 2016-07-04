package com.peerless2012.sspai.domain;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/3 18:06
 * @Version V1.0
 * @Description :
 */
public class Articles{

    private int count;

    private List<Article> articles;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "Articles{" +
                "count=" + count +
                ", articles=" + articles +
                '}';
    }
}

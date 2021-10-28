package com.example.yingbang.entity;

import javax.persistence.*;

@Entity
public class Movie {
    private Integer id;    //电影ID
    private double rate;    //评分
    private String title;   //电影名称
    private String director;    //导演
    private String screenwriter;  //编剧
    private String protagonist; //主演
    private String type; //类型
    private String country; //国家
    private String language; //语言
    private String initialReleaseDate;  //上映日期
    private String dateTime;    //电影时长
    private String alias;    //别名


    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", rate=" + rate +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", screenwriter='" + screenwriter + '\'' +
                ", protagonist='" + protagonist + '\'' +
                ", type='" + type + '\'' +
                ", country='" + country + '\'' +
                ", language='" + language + '\'' +
                ", initialReleaseDate='" + initialReleaseDate + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Column(length=2000)
    public String getScreenwriter() {
        return screenwriter;
    }

    public void setScreenwriter(String screenwriter) {
        this.screenwriter = screenwriter;
    }

    @Column(length=2000)
    public String getProtagonist() {
        return protagonist;
    }

    public void setProtagonist(String protagonist) {
        this.protagonist = protagonist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getInitialReleaseDate() { return initialReleaseDate;
    }

    public void setInitialReleaseDate(String initialReleaseDate) {
        this.initialReleaseDate = initialReleaseDate;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}

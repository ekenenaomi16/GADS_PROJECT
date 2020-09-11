package com.example.gadsleaderboard.models;

public class Learner {
    private String name;
    private int hours, score;
    private String country;
    private String badgeUrl;


    public Learner() {
    }

    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

    public int getScore() {
        return score;
    }

    public String getCountry() {
        return country;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }
}

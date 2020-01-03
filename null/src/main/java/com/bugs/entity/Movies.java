package com.bugs.entity;

public class Movies {
    private String movieId;
    private String movieImg;
    private String movieName;
    private String movieUrl;
    private String movieInfo;

    @Override
    public String toString() {
        return "Movies{" +
                "movieId='" + movieId + '\'' +
                ", movieImg='" + movieImg + '\'' +
                ", movieName='" + movieName + '\'' +
                ", movieUrl='" + movieUrl + '\'' +
                ", movieInfo='" + movieInfo + '\'' +
                '}';
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieImg() {
        return movieImg;
    }

    public void setMovieImg(String movieImg) {
        this.movieImg = movieImg;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getMovieInfo() {
        return movieInfo;
    }

    public void setMovieInfo(String movieInfo) {
        this.movieInfo = movieInfo;
    }

    public Movies() {
    }

    public Movies(String movieId, String movieImg, String movieName, String movieUrl, String movieInfo) {
        this.movieId = movieId;
        this.movieImg = movieImg;
        this.movieName = movieName;
        this.movieUrl = movieUrl;
        this.movieInfo = movieInfo;
    }
}

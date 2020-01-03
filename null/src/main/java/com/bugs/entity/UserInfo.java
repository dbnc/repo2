package com.bugs.entity;

public class UserInfo {
    private String email;
    private String movieUrl;
    private Integer code;

    @Override
    public String toString() {
        return "UserInfo{" +
                "email='" + email + '\'' +
                ", movieUrl='" + movieUrl + '\'' +
                ", code=" + code +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public UserInfo() {
    }

    public UserInfo(String email, String movieUrl, Integer code) {
        this.email = email;
        this.movieUrl = movieUrl;
        this.code = code;
    }
}

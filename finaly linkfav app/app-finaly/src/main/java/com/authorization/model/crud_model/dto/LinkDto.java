package com.authorization.model.crud_model.dto;

public class LinkDto {

    private String username;
    private String describe;
    private String link;

    public LinkDto(Builder builder) {
        this.username = builder.username;
        this.link = builder.link;
        this.describe = builder.describe;
    }

    public static class Builder {
        private String username;
        private String describe;
        private String link;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder describe(String describe) {
            this.describe = describe;
            return this;
        }

        public Builder link(String link) {
            this.link = link;
            return this;
        }

        public LinkDto builder() {
            return new LinkDto(this);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getDescribe() {
        return describe;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "LinkDto{" +
                "username='" + username + '\'' +
                ", describe='" + describe + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}

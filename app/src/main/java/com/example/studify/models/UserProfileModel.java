package com.example.studify.models;

public class UserProfileModel {

    private String name;

    private String email;

    private String password;

    private String img;

    public UserProfileModel(final Builder builder) {
        name = builder.name;
        email = builder.email;
        password = builder.password;
        img = builder.img;
    }

    public static class Builder {
        private String name;
        private String email;
        private String password;
        private String img;

        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(final String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(final String password) {
            this.password = password;
            return this;
        }

        public Builder setImg(String img) {
            this.img = img;
            return this;
        }

        public UserProfileModel build() {
            return new UserProfileModel(this);
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public UserProfileModel() {
    }

    public UserProfileModel(String name, String email, String password, String img) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.img = img;
    }
}

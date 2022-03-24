package com.example.studify.models;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class UserProfile {

    private String name;

    private String email;

    private String password;

    private Uri img;

    public UserProfile(final Builder builder) {
        name = builder.name;
        email = builder.email;
        password = builder.password;
        img = builder.img;
    }

    public static class Builder {
        private String name;
        private String email;
        private String password;
        private Uri img;

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

        public Builder setImg(Uri img) {
            this.img = img;
            return this;
        }

        public UserProfile build() {
            return new UserProfile(this);
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

    public Uri getImg() {
        return img;
    }

    public void setImg(Uri img) {
        this.img = img;
    }

    public UserProfile() {
    }

    public UserProfile(String name, String email, String password, Uri img) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.img = img;
    }
}

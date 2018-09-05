package com.sanjaya.mobileassignment.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserDataModel {

    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private UserData data;

    public UserDataModel(boolean status, String message, UserData data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public class UserData{

        @SerializedName("users")
        private ArrayList<User> users;

        @SerializedName("has_more")
        private boolean hasMore;

        public UserData(ArrayList<User> users, boolean hasMore) {
            this.users = users;
            this.hasMore = hasMore;
        }

        public ArrayList<User> getUsers() {
            return users;
        }

        public void setUsers(ArrayList<User> users) {
            this.users = users;
        }

        public boolean hasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public class User {

            @SerializedName("name")
            private String name;
            @SerializedName("image")
            private String image;
            @SerializedName("items")
            private ArrayList<String> items;

            public User(String name, String image, ArrayList<String> items) {
                this.name = name;
                this.image = image;
                this.items = items;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public ArrayList<String> getItems() {
                return items;
            }

            public void setItems(ArrayList<String> items) {
                this.items = items;
            }

        }
    }

}

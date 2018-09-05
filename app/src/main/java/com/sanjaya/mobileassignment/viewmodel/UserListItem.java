package com.sanjaya.mobileassignment.viewmodel;

import java.util.ArrayList;

public class UserListItem {

    private final String name;
    private final String image;
    private boolean isEven = true;
    private final ArrayList<String> items;

    public UserListItem() {
        name = image = "";
        items = new ArrayList<>();
    }

    public UserListItem(String name, String image, boolean isEven, ArrayList<String> items) {
        this.name = name;
        this.image = image;
        this.isEven = isEven;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public boolean isEven() {
        return isEven;
    }

    public ArrayList<String> getItems() {
        return items;
    }

}

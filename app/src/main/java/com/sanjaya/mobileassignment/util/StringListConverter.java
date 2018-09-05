package com.sanjaya.mobileassignment.util;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class StringListConverter {

    @TypeConverter
    public List<String> fromString(String items){
        List<String> list = new ArrayList<>();
        String[] array = items.split(",");

        for(String item: array){
            list.add(item);
        }
        return list;
    }

    @TypeConverter
    public String fromArrayList(ArrayList<String> list){
        String items = "";
        for(String item: list){
            items += "," + item;
        }
        return items;
    }

}

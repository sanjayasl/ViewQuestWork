package com.sanjaya.mobileassignment.util;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;
/**
 * The ViewQuest created for demonstration purpose
 * don't copy/edit this code without author acknowledgement.
 *
 * @author  Sanjaya Ratnayake
 * @version 1.0
 * @since   2018-09-06
 */
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

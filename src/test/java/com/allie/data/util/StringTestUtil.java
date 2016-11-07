package com.allie.data.util;

/**
 * Created by andrew.larsen on 11/7/2016.
 */
public class StringTestUtil {

    public static String getStringOfLength(int length){
        String oneCharString = "1";
        String returnString = "";
        while(returnString.length() < length){
            returnString += oneCharString;
        }

        return returnString;
    }
}

package com.lapin.common.utility;

public class RemoveLastChar {
    public static String remove(String str){
        return (str == null || str.length() == 0) ? null : (str.substring(0, str.length() - 1));
    }
    public static String remove(String str, int num){
        return (str == null || str.length() == 0) ? null : (str.substring(0, str.length() - num));
    }
}

package com.junyoung.searchwheretogoapi.util;

public class NamingUtil {
    public static String eraseBoldTags(String name) {
        return name.replaceAll("(<b>|</b>)", "");
    }
}

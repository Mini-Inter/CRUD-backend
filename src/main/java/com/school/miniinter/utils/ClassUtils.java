package com.school.miniinter.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ClassUtils {

    public static boolean verifySeries(String series){
        List<String> accept = new LinkedList<>();
        accept.add("6");
        accept.add("7");
        accept.add("8");
        accept.add("9");
        accept.add("1");
        accept.add("2");
        accept.add("3");
        return accept.contains(series);
    }

    public static boolean verifyClassroom(String classroom){
        Character c = Character.toUpperCase(classroom.charAt(0));
        return c >='A' && c<='Z';
    }
}

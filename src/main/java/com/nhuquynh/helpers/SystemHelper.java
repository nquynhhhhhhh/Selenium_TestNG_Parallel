package com.nhuquynh.helpers;

import com.nhuquynh.utils.LogUtils;

import java.io.File;
import java.text.Normalizer;

public class SystemHelper {
    public static String removeSpecialCharacters(String str){
        // Normalize the string to decompose diacritical marks
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);

        // Remove diacritical marks by replacing non-ASCII characters
        String result = normalized.replaceAll("\\p{M}", "");

        LogUtils.info("Result: " + result);

        return result;
    }

    public static String getCurrentDir() {
        String current = System.getProperty("user.dir") + File.separator;
        return current;
    }
}

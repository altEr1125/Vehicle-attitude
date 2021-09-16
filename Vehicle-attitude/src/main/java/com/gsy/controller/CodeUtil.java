package com.gsy.controller;

import java.util.Arrays;
import java.util.Base64;

public class CodeUtil {

    public static String encodeToString(byte[] byteArray) {
        return Base64.getUrlEncoder().encodeToString(byteArray);
    }

    public static byte[] decodeStringToByte(String str) {
        return Base64.getUrlDecoder().decode(str);
    }

    public static void confuse(byte[] byteArray) {
        byte tmp = byteArray[0];
        System.arraycopy(byteArray, 1, byteArray, 0, byteArray.length - 1);
        byteArray[byteArray.length - 1] = tmp;
    }

    public static void deconfuse(byte[] byteArray) {
        byte tmp = byteArray[byteArray.length - 1];
        System.arraycopy(byteArray, 0, byteArray, 1, byteArray.length - 1);
        byteArray[0] = tmp;
    }
}
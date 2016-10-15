package com.cdkeyesdwe.myapplication;

import java.util.ArrayList;

/**
 * Created by asen on 08/10/2016.
 */
public class Alphabet {

    public static ArrayList<String> getBulgarianAlphabet(){
        ArrayList<String> alphabet= new ArrayList<String>();
        alphabet.add("а");alphabet.add("б");alphabet.add("в");alphabet.add("г");alphabet.add("д");alphabet.add("е");
        alphabet.add("ж");alphabet.add("з");alphabet.add("и");alphabet.add("й");alphabet.add("к");alphabet.add("л");
        alphabet.add("м");alphabet.add("н");alphabet.add("о");alphabet.add("п");alphabet.add("р");alphabet.add("с");
        alphabet.add("т");alphabet.add("у");alphabet.add("ф");alphabet.add("х");alphabet.add("ц");alphabet.add("ч");alphabet.add("ш");
        alphabet.add("щ");alphabet.add("ъ");alphabet.add("ь");alphabet.add("ю");alphabet.add("я");

        return alphabet;
    }

    public static ArrayList<String> getEnglishAlphabet(){
        ArrayList<String> alphabet= new ArrayList<String>();
        alphabet.add("a");alphabet.add("b");alphabet.add("c");alphabet.add("d");alphabet.add("e");alphabet.add("f");
        alphabet.add("g");alphabet.add("h");alphabet.add("i");alphabet.add("j");alphabet.add("k");alphabet.add("l");
        alphabet.add("m");alphabet.add("n");alphabet.add("o");alphabet.add("p");alphabet.add("q");alphabet.add("r");
        alphabet.add("s");alphabet.add("t");alphabet.add("v");alphabet.add("w");alphabet.add("x");alphabet.add("y");
        alphabet.add("z");


        return alphabet;
    }
}

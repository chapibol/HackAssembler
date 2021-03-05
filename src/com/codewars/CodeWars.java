package com.codewars;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Some numbers have funny properties. For example:
 *
 * 89 --> 8¹ + 9² = 89 * 1
 *
 * 695 --> 6² + 9³ + 5⁴= 1390 = 695 * 2
 *
 * 46288 --> 4³ + 6⁴+ 2⁵ + 8⁶ + 8⁷ = 2360688 = 46288 * 51
 *
 * Given a positive integer n written as abcd... (a, b, c, d... being digits) and a positive integer p
 *
 * we want to find a positive integer k, if it exists, such as the sum of the digits of n taken to the successive powers of p is equal to k * n.
 * In other words:
 *
 * Is there an integer k such as : (a ^ p + b ^ (p+1) + c ^(p+2) + d ^ (p+3) + ...) = n * k
 *
 * If it is the case we will return k, if not return -1.
 *
 * Note: n and p will always be given as strictly positive integers.
 */
public class CodeWars {

    public static void main(String[] args){
        System.out.println(scramble("scriptjavx", "javascript"));
    }


    public static long digPow(int n, int p) {
        List<String> digitStrList = Arrays.asList(("" + n).split(""));
        List<Integer> digits = digitStrList.stream().map(Integer::valueOf).collect(Collectors.toList());
        List<Integer> digitSums = new ArrayList<>();

        System.out.println("p = " + p);

        for(Integer num: digits){
            Integer powNum = (int)Math.pow(num, p++);
            digitSums.add(powNum);
        }

        Integer powSum = digitSums.stream().reduce(0, Integer::sum);
        System.out.println("powSum = " + powSum);
        System.out.println("n = " + n);

        // solving for k. Initial equation is powSum = n * k
        int kMod = powSum % n;
        int kDiv = powSum / n;


        return kMod == 0 ? kDiv : -1;
    }

    public static boolean scramble(String str1, String str2) {
       System.out.println("str1 = " + str1);
       System.out.println("str2 = " + str2);
       StringBuilder str1Builder = new StringBuilder(str1);
       for(char str: str2.toCharArray()){
           int foundIndex = str1Builder.indexOf("" + str);
           if(foundIndex == - 1)
               return false;
           else
               str1Builder.deleteCharAt(foundIndex);// remove found char
       }
       return true;
    }
}

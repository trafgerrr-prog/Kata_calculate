import com.sun.source.tree.IfTree;

import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static String calc(String input) throws IOException {
        Converter converter = new Converter();

        String[] actions = {"+", "-", "/", "*"};
        String[] shActions = {"\\+", "-", "/", "\\*"};


        int aInd = -1;

        for (int i = 0; i < actions.length; i++){
            if (input.contains(actions[i])) {
                aInd = i;
                break;
            }
        }

        if (aInd == -1) {
            throw new IOException();
        }

        String[] nums = input.split(shActions[aInd]);
        if (nums.length > 2) {
            throw new IOException();
        }

        if (converter.isRom(nums[0]) == converter.isRom(nums[1])){

            if (!converter.isRom(nums[0])){
                numbers a = new numbers();
                a.number = Integer.parseInt(nums[0]);

                int b;
                b = Integer.parseInt(nums[1]);

                if (a.number > 0 & a.number <= 10 & b > 0 & b <= 10){
                    switch (actions[aInd]){
                        case "+":
                            return Integer.toString(a.sum(b));
                        case "-":
                            return Integer.toString(a.sub(b));
                        case "/":
                            return Integer.toString(a.div(b));
                        case "*":
                            return Integer.toString(a.mul(b));
                    }
                } else {
                    throw new IOException();
                }
            } else{
                numbers a = new numbers();
                int b;

                a.number = converter.converterToAr(nums[0]);
                b = converter.converterToAr(nums[1]);

                if (a.number > 0 & a.number <= 10 & b > 0 & b <= 10){
                    switch (actions[aInd]){
                        case "+":
                            return converter.converterToInt(a.sum(b));
                        case "-":
                            if (a.sub(b) > 0){
                                return converter.converterToInt(a.sub(b));
                            } else {
                                throw new IOException();
                            }
                        case "/":
                            return converter.converterToInt(a.div(b));
                        case "*":
                            return converter.converterToInt(a.mul(b));
                    }
                } else {
                    throw new IOException();
                }
            }

        } else {
            throw new IOException();
        }

        throw new IOException();
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String str = scn.nextLine();
        str = str.replaceAll("\\s", "");


        try {
            String output = calc(str);
            System.out.println(output);
        } catch (IOException e) {
            System.out.println("throw Exception");
        }

    }
}

class numbers{
    int number;

    int sum(int num2){
        return number + num2;
    }
    int sub(int num2){
        return number - num2;
    }
    int div(int num2){
        return number / num2;
    }
    int mul(int num2){
        return number * num2;
    }

}

class Converter{
    TreeMap<Character, Integer> mapRoman = new TreeMap<>();
    TreeMap<Integer, String> mapArab = new TreeMap<>();
    Converter(){
        mapRoman.put('I', 1);
        mapRoman.put('V', 5);
        mapRoman.put('X', 10);
        mapRoman.put('L', 50);
        mapRoman.put('C', 100);

        mapArab.put(1,"I");
        mapArab.put(4,"IV");
        mapArab.put(5,"V");
        mapArab.put(9,"IX");
        mapArab.put(10,"X");
        mapArab.put(40,"XL");
        mapArab.put(50,"L");
        mapArab.put(90,"XC");
        mapArab.put(100,"C");
    }
    int converterToAr(String s){

        int end = s.length() - 1;
        char[] arr = s.toCharArray();

        int ar;
        int res = mapRoman.get(arr[end]);

        for (int i = end - 1; i >= 0; i--){
            ar = mapRoman.get(arr[i]);

            if (ar < mapRoman.get(arr[i + 1])) res -= ar;
            else res +=ar;
        }

        return res;
    }
    String converterToInt(int num){
        String rom = "";
        int arKey;

        do{
            arKey = mapArab.floorKey(num);
            rom += mapArab.get(arKey);
            num -= arKey;
        } while (num != 0);

        return rom;
    }
    boolean isRom(String num){
        return mapRoman.containsKey(num.charAt(0));
    }
}
package com.epam.rd.autotasks.arrays;

import java.util.Arrays;

public class LocalMaximaRemove {

    public static void main(String[] args) {
        int[] array = new int[]{18, 1, 3, 6, 7, -5};

        System.out.println(Arrays.toString(removeLocalMaxima(array)));
    }

    public static int[] removeLocalMaxima(int[] array){

        int l = array.length-1;
        int counter = 0;
        int[] outputArray = new int[array.length];
        for (int i = 0; i < array.length-1; i++) {
            if ((i == 0) && (array[i] > array[i + 1])) {
                counter--;
            }
            else if (i != 0 && (array[i] > array[i - 1]) && (array[i] > array[i + 1])) {
                counter--;
            }
            else
                outputArray[counter] = array[i];
                counter ++;
            }
            if (array[l] > array[l-1]){
                counter--;
            }
            else
            outputArray[counter] = array[l];
        counter = counter +1;
        int[] arrayToReturn = Arrays.copyOf(outputArray,counter);
        return arrayToReturn;
    }
}

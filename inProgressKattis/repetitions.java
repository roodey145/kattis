package inProgressKattis;

import java.util.Scanner;

public class repetitions{
    private static Scanner _scanner = new Scanner(System.in);
    public static void main(String[] args) {

        // System.out.println(calcUpperLimit(2));
        // System.out.println(calcUpperLimit(3));
        // System.out.println(calcUpperLimit(4));
        // System.out.println(calcUpperLimit(5));
        // if(true) return;
        int k = Integer.parseInt( _scanner.nextLine() );
        int n = Integer.parseInt( _scanner.nextLine() );

        
        // Number of characters needed to reach continues repetition
        // int initialValue = k*k + k;
        // int nrToContinuesRe = k * (initialValue + 1); // Upper limit
        int nrToContinuesRe = calcUpperLimit(k);
        System.out.println(nrToContinuesRe);

        int result = calcNrOfOnes(k, k, nrToContinuesRe < n ? nrToContinuesRe : n, nrToContinuesRe);
        int extra = result / k;
        if(extra <= 1) extra = 0;

        System.out.println("Results Before: " + (result));
        System.out.println("Results After: " + (result + result / k));
        result += extra + (n > nrToContinuesRe ? n - nrToContinuesRe : 0);

        System.out.println( result );
    }

    public static int calcUpperLimit(int k)
    {
        int upperLimit = k * k + k;
        
        for(int i = 1; i < k - 1; i++)
        {
            upperLimit = upperLimit * k + k;
        }

        return upperLimit;
    }


    public static int calcNrOfOnes(int initialK, int k, int n, int upperLimit)
    {
        int newK = k + 1; // Because we need (k + 1) characters to find one repetition, i.e. one number 1
        int initialValue = initialK*k + initialK;


        if(n <= initialValue)
        {
            return n / newK;
        } else{
            return initialK * calcNrOfOnes(initialK, initialValue, n, upperLimit);
        }


        // if(initialValue > upperLimit)
        // {
        //     int repetition = upperLimit / newK;
        //     int extraValueToAdd = 0;
        //     if(n > upperLimit)
        //         extraValueToAdd += n - upperLimit;
        //     return repetition + extraValueToAdd;
        // }
        // else if(n <= initialValue)
        // {
        //     return n / newK;
        // }

        // // There is k number of repetitions
        // int result = calcNrOfOnes(initialK, initialValue, n, upperLimit);
        // return initialK + initialK * result;
    }
}
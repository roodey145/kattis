package inProgressKattis;

import java.util.Scanner;

public class repetitions {
    private static Scanner _scanner = new Scanner(System.in);
    private static int usedNumbers = 0;

    public static void main(String[] args) {

        // System.out.println(calcUpperLimit(2));
        // System.out.println(calcUpperLimit(3));
        // System.out.println(calcUpperLimit(4));
        // System.out.println(calcUpperLimit(5));
        // if(true) return;
        int n = _scanner.nextInt();
        int k = _scanner.nextInt();
        _scanner.nextLine();

        // Number of characters needed to reach continues repetition
        // int initialValue = k*k + k;
        // int nrToContinuesRe = k * (initialValue + 1); // Upper limit
        int nrToContinuesRe = calcUpperLimit(k);
        // System.out.println("Upper Limit: " + nrToContinuesRe);

        int result = calcNrOfOnes(k, k, nrToContinuesRe < n ? nrToContinuesRe : n, nrToContinuesRe);
        int extra = result / k;
        if (extra <= 1)
            extra = 0;

        // This reminder do not take into consideration that the reminder is bigger than
        // (k * k + k)
        int reminder = n - usedNumbers;
        int firstLayerLength = k * k + k;
        if (n < nrToContinuesRe) {
            while (reminder > firstLayerLength) {
                int subResult = calcNrOfOnes(k, k, nrToContinuesRe < reminder ? nrToContinuesRe : reminder,
                        nrToContinuesRe);
                int subExtra = subResult / k;
                result += subResult;
                extra += subExtra;
                reminder -= usedNumbers;
            }
        }
        extra += (n - usedNumbers) / (k + 1);

        // System.out.println("Results Before: " + (result));
        // System.out.println("Results After: " + (result + result / k));
        // System.out.println("Used Numbers: " + (usedNumbers));
        result += extra + (n > nrToContinuesRe ? n - nrToContinuesRe : 0);

        System.out.println(result);
    }

    public static int calcUpperLimit(int k) {
        int upperLimit = k * k + k;

        for (int i = 1; i < k - 1; i++) {
            upperLimit = upperLimit * k + k;
        }

        return upperLimit;
    }

    public static int calcNrOfOnes(int initialK, int k, int n, int upperLimit) {
        int newK = k + 1; // Because we need (k + 1) characters to find one repetition, i.e. one number 1
        int initialValue = initialK * k + initialK;

        if (n <= initialValue) {
            int result = n / newK;
            usedNumbers = result * newK;
            return result;
        } else {
            return initialK * calcNrOfOnes(initialK, initialValue, n, upperLimit);
        }

        // if(initialValue > upperLimit)
        // {
        // int repetition = upperLimit / newK;
        // int extraValueToAdd = 0;
        // if(n > upperLimit)
        // extraValueToAdd += n - upperLimit;
        // return repetition + extraValueToAdd;
        // }
        // else if(n <= initialValue)
        // {
        // return n / newK;
        // }

        // // There is k number of repetitions
        // int result = calcNrOfOnes(initialK, initialValue, n, upperLimit);
        // return initialK + initialK * result;
    }
}
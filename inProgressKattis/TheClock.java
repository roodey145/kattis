
package inProgressKattis;

import java.util.Scanner;

public class TheClock {
    private static Scanner _scanner = new Scanner(System.in);
    public static int input = 0;

    public static void main(String[] args) {
        input = _scanner.nextInt();
        _scanner.nextLine();
        // int count = 0;
        for (int hour = 0; hour < 12; hour++) {
            for (int minute = 0; minute < 60; minute++) {
                if (Math.abs(input - calcAngle(hour, minute)) == 0) {
                    String hourStr = hour < 10 ? "0" + hour : hour + "";
                    String minuteStr = minute < 10 ? "0" + minute : minute + "";
                    System.out.println(hourStr + ":" + minuteStr);
                }
                // if (hour == 3 && minute == 8)
                // System.out.println("Diff: " + Math.abs(input - calcAngle(hour, minute)));
                // System.out.println("Angle: " + calcAngle(hour, minute) + "\n\n");
                // count++;
                // if (count > 100)
                // return;
            }
        }
    }

    private static int calcAngle(int hour, int minute) {
        double angle = 0;
        double hourF = (hour + (minute / 60.0)) / 12 * Math.PI * 2;
        double minuteF = (minute / 60.0) * Math.PI * 2;

        double hX = Math.sin(hourF);
        double hY = Math.cos(hourF);

        double mX = Math.sin(minuteF);
        double mY = Math.cos(minuteF);

        double ang = Math.acos(hX * mX + hY * mY) / Math.PI * 180;

        // double diff = minuteF - hourF;

        // if (diff < 0)
        // angle = (Math.PI * 2 - diff) / Math.PI * 180;
        // else
        // angle = diff / Math.PI * 180;

        if (minuteF < hourF)
            angle = 360 - ang;
        else
            angle = ang;

        // if (hour == 3 && minute == 8) {
        // System.out.println("Hour: " + hourF);
        // System.out.println("Minute: " + minuteF);
        // // System.out.println("Diff: " + diff);
        // System.out.println("Angle: " + (angle * 10));
        // System.out.println("New Angle: " + (int) ((360 - (ang / Math.PI * 180)) *
        // 10));
        // }
        return (int) (angle * 10);
    }
}
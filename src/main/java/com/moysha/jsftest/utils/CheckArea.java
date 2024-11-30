package com.moysha.jsftest.utils;


public class CheckArea {

    public static boolean checkRectangle(double x, double y, double r) {
        return ((-r <= x)&&(x <= 0) && ((-r <= y) && (y<=0)));
    }
    public static boolean checkTriangle(double x, double y, double r) {
        return ((0 <= x && x <= r) && (-r/2 <= y && y <=0) && y >= (x / 2) - r / 2);
    }
    public static boolean checkCircle(double x, double y, double r) {
        return ((-r<=x && x<=0) && (0<=y && y<=r) && Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2));
    }
    public static boolean checkArea(double x, double y, double r){
        return checkRectangle(x, y, r) || checkTriangle(x, y, r) || checkCircle(x, y, r);
    }
}

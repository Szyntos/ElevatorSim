package org.example;

public class Color {
    int r;
    int g;
    int b;
    public Color(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void setHSL(double h, double s, double l){
        int[] rgb = hslToRgb(h, s, l);
        this.r = rgb[0];
        this.g = rgb[1];
        this.b = rgb[2];
    }

    private static int[] hslToRgb(double h, double s, double l) {
        double r, g, b;

        if (s == 0) {
            r = g = b = l; // achromatic
        } else {
            double q = l < 0.5 ? l * (1 + s) : l + s - l * s;
            double p = 2 * l - q;
            r = hueToRgb(p, q, h + 1/(double)3);
            g = hueToRgb(p, q, h);
            b = hueToRgb(p, q, h - 1/(double)3);
        }

        return new int[]{round(r * 255), round(g * 255), round(b * 255)};
    }

    private static double hueToRgb(double p, double q, double t) {
        if (t < 0) t += 1;
        if (t > 1) t -= 1;
        if (t < 1/(double)6) return p + (q - p) * 6 * t;
        if (t < 1/(double)2) return q;
        if (t < 2/(double)3) return p + (q - p) * (2/(double)3 - t) * 6;
        return p;
    }

    private static int round(double value) {
        return (int) Math.round(value);
    }

    public void setToFloor(int floorID, int floorCount){
        this.setHSL(1/(double)(floorCount+1) * floorID,1, 0.5);
    }

    public void printRGB(){
        System.out.println("R = " + r + ", G = " + g + ",B = " + b);
    }


    public int[] toArray(){
        return new int[]{r, g, b};
    }
}

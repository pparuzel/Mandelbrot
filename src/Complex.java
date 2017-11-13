package mandelbrot;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.Math;

/**
 * <h1>Complex</h1>
 * Klasa reprezentująca liczby zespolone. Operacje arytmetyczne na nich
 * oraz obliczanie ich właściwości takich jak argument liczby czy moduł.
 * @author Paweł Paruzel
 */

public class Complex
{
    private double r, i;
    public Complex() { this.r = 0.0; this.i = 0.0; }
    public Complex(double r) { this.r = r; this.i = 0.0; }
    public Complex(double r, double i) { this.r = r; this.i = i; }
    public Complex(Complex x) { r = x.r; i = x.i; }
    public Complex(String s) { Complex x = valueOf(s); r = x.r; i = x.i; }

    /**
     * Statyczna metoda sumująca dwie liczby zespolone.<br>
     * Przykład użycia: <code>Complex.add(x, y)</code>
     * @param x lewy składnik sumy
     * @param y prawy składnik sumy
     * @return zwraca liczbę zespoloną
     */
    public static Complex add(Complex x, Complex y)
    {
        return new Complex(x.r+y.r, x.i+y.i);
    }

    /**
     * Statyczna metoda odejmująca dwie liczby zespolone.<br>
     * Przykład użycia: <code>Complex.sub(x, y)</code>
     * @param x lewy składnik różnicy
     * @param y prawy składnik różnicy
     * @return zwraca liczbę zespoloną
     */
    public static Complex sub(Complex x, Complex y)
    {
        return new Complex(x.r-y.r, x.i-y.i);
    }

    /**
     * Statyczna metoda mnożąca dwie liczby zespolone.<br>
     * Przykład użycia: <code>Complex.mul(x, y)</code>
     * @param x lewy składnik iloczynu
     * @param y prawy składnik iloczynu
     * @return zwraca liczbę zespoloną
     */
    public static Complex mul(Complex x, Complex y)
    {
        return new Complex(x.r*y.r-x.i*y.i, x.r*y.i+x.i*y.r);
    }

    /**
     * Statyczna metoda dzieląca dwie liczby zespolone.<br>
     * Przykład użycia: <code>Complex.div(x, y)</code>
     * @param x lewy składnik ilorazu
     * @param y prawy składnik ilorazu
     * @return zwraca liczbę zespoloną
     * @throws DivideByZeroException wyrzuca wyjątek przy dzieleniu przez zero.
     */
    public static Complex div(Complex x, Complex y) throws DivideByZeroException
    {
        double p = 1.0;
        double q = 1.0;
        p = (x.r*y.r+x.i*y.i)/(y.r*y.r+y.i*y.i);
        q = (x.i*y.r-x.r*y.i)/(y.r*y.r+y.i*y.i);
        if( (y.r*y.r+y.i*y.i) == 0.0 )
            throw new DivideByZeroException("0.0", "0.0");
        return new Complex(p, q);
    }

    /**
     * Statyczna metoda wartości bezwzględnej.<br>
     * Przykład użycia: <code>Complex.abs(x)</code>
     * @param x składnik do metody abs
     * @return zwraca <code>double</code>
     */
    public static double abs(Complex x)
    {
        return Math.sqrt(x.r*x.r + x.i*x.i);
    }

    /**
     * Statyczna metoda wartości argumentu liczby zespolonej.<br>
     * Przykład użycia: <code>Complex.phase(x)</code>
     * @param x składnik do metody phase
     * @return zwraca <code>double</code>
     */
    public static double phase(Complex x)
    {
        if(x.r > 0)
            return Math.atan(x.i/x.r);
        else if(x.r < 0 && x.i >= 0)
            return Math.atan(x.i/x.r)+Math.PI;
        else if(x.r < 0 && x.i < 0)
            return Math.atan(x.i/x.r)-Math.PI;
        else if(x.r == 0 && x.i > 0)
            return Math.PI/2;
        else if(x.r == 0 && x.i < 0)
            return -Math.PI/2;
        else
            return 0.0;
    }

    /**
     * Statyczna metoda wartości bezwzględnej podniesionej do kwadratu.<br>
     * Przykład użycia: <code>Complex.sqrAbs(x)</code>
     * @param x składnik do metody sqrAbs
     * @return zwraca <code>double</code>
     */
    public static double sqrAbs(Complex x)
    {
        return x.r*x.r + x.i*x.i;
    }
    /* Zwraca kwadrat modułu liczby zespolonej */
    public static double re(Complex x)
    {
        return x.r;
    }
    /* Zwraca część rzeczywistą liczby zespolonej */
    public static double im(Complex x)
    {
        return x.i;
    }
    /* Zwraca część urojoną liczby zespolonej */

    //

    public Complex add(Complex x)
    {
        /*
        double real      = r + x.r;
        double imaginary = i + x.i;
        return new Complex(real, imaginary);*/
        r = r + x.r;
        i = i + x.i;
        return this;
    }
    public Complex sub(Complex x)
    {
        Complex z = this;
        double real      = r - x.r;
        double imaginary = i - x.i;
        return new Complex(real, imaginary);
    }
    public Complex mul(Complex x)
    {/*
        double real      = r*x.r-i*x.i;
        double imaginary = r*x.i+i*x.r;
        return new Complex(real, imaginary); */
        double rr = r;
        double ii = i;
        r = r*x.r-i*x.i;
        i = rr*x.i+ii*x.r;
        return this;
    }
    public Complex div(Complex x) throws DivideByZeroException
    {
        double real      = (r*x.r + i*x.i)/(x.r*x.r + x.i*x.i);
        double imaginary = (i*x.r - r*x.i)/(x.r*x.r + x.i*x.i);
        if( (x.r*x.r+x.i*x.i) == 0.0 )
            throw new DivideByZeroException("0.0", "0.0");
        return new Complex(real, imaginary);
    }
    public double abs()
    {
        return Math.sqrt(r*r + i*i);
    }
    public double sqrAbs()
    {
        return r*r + i*i;
    }
    double re()
    {
        return r;
    }
    double im()
    {
        return i;
    }

    @Override
    public String toString()
    {
        String symbol = "+";
        if(i < 0)
            symbol = "";
        if(r != 0 && i != 0)
            return r+symbol+i+"i";
        else if(r != 0 && i == 0)
            return r+"";
        else if(r == 0 && i != 0)
            return i+"i";
        else
            return "0.0";
    }

    public String toString(boolean b)
    {
        return "["+r+","+i+"]";
    }

    public static Complex valueOf(String s)
    {
        Pattern p1 = Pattern
                .compile("([\\+||\\-][0-9]+?\\.[0-9]+?)([\\+||\\-][0-9]+?\\.[0-9]+?)i");
        Pattern p2 = Pattern
                .compile("([0-9]+?\\.[0-9]+?)([\\+||\\-][0-9]+?\\.[0-9]+?)i");
        Pattern p;

        if     (p1.matcher(s).matches()) { p = p1; }
        else if(p2.matcher(s).matches()) { p = p2; }
        else return null;

        Matcher m = p.matcher(s);
        double re=0.0, im=0.0;
        while (m.find())
        {
            re = Double.parseDouble(m.group(1));
            im = Double.parseDouble(m.group(2));
        }
        return new Complex(re,im);
    }

    void setRe(double r)
    {
        this.r = r;
    }

    void setIm(double i)
    {
        this.i = i;
    }

    void setVal(Complex x)
    {
        r = x.r;
        i = x.i;
    }

    void setVal(double r, double i)
    {
        this.r = r;
        this.i = i;
    }
}
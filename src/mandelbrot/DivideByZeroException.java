package mandelbrot;

import java.lang.ArithmeticException;

public class DivideByZeroException extends ArithmeticException
{
    private String errormsg;

    public DivideByZeroException()
    {
        super();
        errormsg = "Undefined number! Division by zero.";
    }
    public DivideByZeroException(String s)
    {
        super(s);
        errormsg = s;
    }
    public DivideByZeroException(String a, String b)
    {
        errormsg = a+"/"+b+" is undefined! Division by zero.";
    }
    @Override
    public String getMessage()
    {
        return errormsg;
    }
}
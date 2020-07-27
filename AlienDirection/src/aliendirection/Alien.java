package aliendirection;

public class Alien 
{
    private Integer txtNum;
    private boolean eaten = false;
    private double xValue, yValue;
    
    public Alien (Integer txtNum)
    {
        this.txtNum = txtNum;
    }
    
    public void setEaten()
    {
        eaten = true;
    }
    
    public void setXY (int xValue, int yValue)
    {
        this.xValue = xValue;
        this.yValue = yValue;
    }
    
    public Integer get_txtNum()
    {
        return txtNum;
    }
    
    public double get_xValue()
    {
        return xValue;
    }
    
    public double get_yValue()
    {
        return yValue;
    }
    
    public boolean isEaten()
    {
        return eaten;
    }
    
}

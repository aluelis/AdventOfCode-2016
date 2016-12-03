/**
 * Created by Svetlin Tanyi on 03/12/16.
 */
public class Triangle {

    private int a = 0, b = 0, c = 0;

    public Triangle(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setC(int c) {
        this.c = c;
    }

    public boolean isValid(){
        return a + b > c && a + c > b && b + c > a;
    }

}

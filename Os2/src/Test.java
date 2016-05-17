/**
 * Created by hongjiayong on 16/5/16.
 */
public class Test {
    public static void main(String args[]){
        firstAdapter first = new firstAdapter();
        first.putIn(100, 1);
        first.putIn(200, 2);
        first.putIn(800, 3);

        first.releaseMem(1);
        first.putIn(80, 4);
        first.releaseMem(2);
    }
}

/**
 * Created by hongjiayong on 16/5/17.
 */
public class commamd {
    // true->apply
    private boolean state;
    private int lenth;
    private int tag;

    public commamd(boolean state, int lenth, int tag){
        this.state = state;
        this.lenth = lenth;
        this.tag = tag;
    }

    public int getLenth() {
        return lenth;
    }

    public boolean getState(){
        return state;
    }

    public int getTag() {
        return tag;
    }
}

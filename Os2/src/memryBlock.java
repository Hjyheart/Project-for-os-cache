/**
 * Created by hongjiayong on 16/5/16.
 */
public class memryBlock {
    private boolean memState;
    private int start;
    private int end;
    private int lenth;
    private int tag;

    public memryBlock(){
        memState = false;
        start = -1;
        end = -1;
        lenth = 0;
        tag = 0;
    }

    public memryBlock(int start, int end, int lenth, int tag){
        memState = true;
        this.start = start;
        this.end = end;
        this.lenth = lenth;
        this.tag = tag;
    }

    public void setMemState(boolean state){
        memState = state;
    }

    public boolean getMemState(){
        return memState;
    }

    public void setStart(int start){
        this.start = start;
    }

    public int getStart() {
        return start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getEnd() {
        return end;
    }

    public void setLenth(int lenth) {
        this.lenth = lenth;
    }

    public int getLenth() {
        return lenth;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }
}

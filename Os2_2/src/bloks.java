/**
 * Created by hongjiayong on 16/5/18.
 */
public class bloks {
    private int name;
    private int inTime;
    private int useTime;

    public bloks(int name, int inTime){
        this.name = name;
        this.inTime = inTime;
        useTime = 0;
    }

    public int getInTime() {
        return inTime;
    }

    public void setInTime(int inTime) {
        this.inTime = inTime;
    }

    public int getUseTime() {
        return useTime;
    }

    public int getName() {
        return name;
    }

    public void setUseTime() {
        useTime = 0;
    }

    public void useTimeAdd(){
        useTime++;
    }

}

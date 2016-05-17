import java.util.ArrayList;

/**
 * Created by hongjiayong on 16/5/16.
 */
public class firstAdapter {
    public ArrayList<memryBlock> table;
    public ArrayList<Integer> tags;

    public firstAdapter(){
        table = new ArrayList<memryBlock>();
        tags = new ArrayList<Integer>();
        memryBlock blank = new memryBlock(0, 640, 640, 0);
        blank.setMemState(false);
        tags.add(0);
        table.add(blank);
    }

    public void putIn(int lenth, int tag){
        for (int i = 0; i < table.size(); i++){
            // enable
            if(!table.get(i).getMemState() && table.get(i).getLenth() >= lenth){
                memryBlock temp = table.get(i);
                memryBlock newMem = new memryBlock(temp.getStart(), temp.getStart() + lenth, lenth, tag);
                temp.setStart(temp.getStart() + lenth);
                temp.setLenth(temp.getLenth() - lenth);
                table.add(i, newMem);
                tags.add(i);
                ui.board.append("Put in successful!\n");
                break;
            }
            // unable
            if (i == table.size() - 1){
                ui.board.append("Put in fail! short of memery!\n");
            }
        }
    }

    public void releaseMem(int tag){
        try {
            if(tags.get(tag) == -1){
                ui.board.append("作业" + tag + ": Has been released!\n");
                return;
            }
            tags.set(tag, -1);
        }catch (RuntimeException e){
            ui.board.append("No such memery block!");
            return;
        }
        for (int i = 0; i < table.size(); i++){
            if (table.get(i).getTag() == tag){
                memryBlock temp = table.get(i);
                // left and right both are true
                if((i == 0 && table.get(1).getMemState()) || (i == table.size() - 1 && table.get(i - 1).getMemState())
                        || (i > 0 && table.get(i - 1).getMemState() && table.get(i).getMemState())){
                    temp.setMemState(false);
                    ui.board.append("作业" + tag + ": Released successful!\n");
                    break;
                }else{
                    // left is false
                    if(i > 0 && !table.get(i - 1).getMemState()){
                        temp.setStart(table.get(i - 1).getStart());
                        temp.setLenth(temp.getLenth() + table.get(i - 1).getLenth());
                        temp.setMemState(false);
                        ui.board.append("作业" + tag + ": Released successful!\n");
                        table.remove(i - 1);
                        i--;
                    }
                    // right is false
                    if(i < table.size() - 1 && !table.get(i + 1).getMemState()){
                        temp.setEnd(table.get(i + 1).getEnd());
                        temp.setLenth(temp.getLenth() + table.get(i + 1).getLenth());
                        temp.setMemState(false);
                        ui.board.append("作业" + tag + ": Released successful!\n");
                        table.remove(i + 1);
                    }
                }
                break;
            }
        }
    }
}

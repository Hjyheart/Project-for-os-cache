import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created by hongjiayong on 16/5/18.
 */
public class ui extends JFrame{

    private static JComboBox algorChose = new JComboBox();
    private static JButton start = new JButton("start");
    private static TextArea logArea = new TextArea();
    private static boolean [] commands = new boolean[320];
    private static bloks[] blok = new bloks[4];
    private static ArrayList<bloks> pages = new ArrayList<bloks>();


    public static boolean judge(){
        for (int i = 0; i < 320; i++){
            if (commands[i])
                return true;
        }
        return false;
    }

    public ui(){
        // Jframe init
        setTitle("页式调度");
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        add(algorChose);
        add(start);
        add(logArea);

        GridBagConstraints s = new GridBagConstraints();

        // algorChose init
        s.fill = GridBagConstraints.BOTH;
        s.gridheight = 1;
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 1;
        algorChose.addItem("FIFO");
        algorChose.addItem("LRU");
        algorChose.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ItemEvent.SELECTED == e.getStateChange()){
                    // FIFO
                    if (e.getItem().toString().equals("FIFO")){
                        logArea.setText("");
                        logArea.append("You choose: FIFO\n");
                    }
                    // LRU
                    if (e.getItem().toString().equals("LRU")){
                        logArea.setText("");
                        logArea.append("You choose: LRU\n");
                    }
                }
            }
        });
        layout.setConstraints(algorChose, s);

        // start init
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread(){
                    public void run(){
                        int lackTime = 0;
                        int comandCount = 0;
                        int comandGet = 0;
                        int comandId = (int) Math.round(Math.random() * 320);
                        while (judge()){
                            if (commands[comandId]) {
                                // FIFO
                                if (algorChose.getSelectedIndex() == 0){
                                    boolean flag1 = true;
                                    boolean flag2 = true;
                                    int pageId = comandId / 10;
                                    // search
                                    for (int i = 0; i < 4; i++){
                                        if (blok[i].getName() == pageId){
                                            logArea.append(comandGet + ": Page " + pageId
                                                    + " has in blok " + i + " to do command " + comandId
                                                    + "\n");
                                            flag1 = false;
                                            break;
                                        }
                                    }
                                    // need to turn in
                                    if (flag1) {
                                        for (int i = 0; i < 4; i++) {
                                            // empty
                                            if (blok[i].getName() == -1) {
                                                bloks newBlok = pages.get(pageId);
                                                newBlok.setInTime(comandGet);
                                                logArea.append(comandGet + ": Page " + pageId
                                                        + " turns in blok " + i
                                                        + " to do command " + comandId
                                                        + "\n");
                                                blok[i] = newBlok;
                                                flag2 = false;
                                                break;
                                            }
                                        }
                                    }
                                    // not empty
                                    if (flag2 && flag1){
                                        lackTime++;
                                        int min = 10000;
                                        int choose = -1;
                                        for (int i = 0; i < 4; i++){
                                            if (blok[i].getInTime() < min){
                                                min = blok[i].getInTime();
                                                choose = i;
                                            }
                                        }
                                        bloks newBlok = pages.get(pageId);
                                        newBlok.setInTime(comandGet);
                                        logArea.append(comandGet + ": Page " + pageId
                                                + " turns in blok " + choose + " replace page " + blok[choose].getName()
                                                + " to do command " + comandId
                                                + "\n");
                                        blok[choose] = newBlok;
                                    }
                                }

                                // LRU
                                if (algorChose.getSelectedIndex() == 1){
                                    boolean flag1 = true;
                                    boolean flag2 = true;
                                    int pageId = comandId / 10;
                                    // search
                                    for (int i = 0; i < 4; i++){
                                        if (blok[i].getName() == pageId){
                                            logArea.append(comandGet + ": Page " + pageId
                                                    + " has in blok " + i + " to do command " + comandId
                                                    + "\n");
                                            flag1 = false;
                                            break;
                                        }
                                    }
                                    // need to turn in
                                    if (flag1) {
                                        for (int i = 0; i < 4; i++) {
                                            // empty
                                            if (blok[i].getName() == -1) {
                                                bloks newBlok = pages.get(pageId);
                                                newBlok.setUseTime();
                                                logArea.append(comandGet + ": Page " + pageId
                                                        + " turns in blok " + i
                                                        + " to do command " + comandId
                                                        + "\n");
                                                blok[i] = newBlok;
                                                flag2 = false;
                                                break;
                                            }
                                        }
                                    }
                                    // not empty
                                    if (flag2 && flag1){
                                        lackTime++;
                                        int max = -1;
                                        int choose = -1;
                                        for (int i = 0; i < 4; i++){
                                            if (blok[i].getUseTime() > max){
                                                max = blok[i].getUseTime();
                                                choose = i;
                                            }
                                        }
                                        bloks newBlok = pages.get(pageId);
                                        newBlok.setUseTime();
                                        logArea.append(comandGet + ": Page " + pageId
                                                + " turns in blok " + choose + " replace page " + blok[choose].getName()
                                                + " to do command " + comandId
                                                + "\n");
                                        blok[choose] = newBlok;
                                    }
                                }
                                comandGet++;
                                for (int i = 0; i < 4; i++){
                                    blok[i].useTimeAdd();
                                }
                            }
                            commands[comandId] = false;

                            if (comandCount % 4 == 0){;
                                int temp = comandId;
                                if (!judge())
                                    break;
                                if (temp < 319) {
                                    temp++;
                                    while (!commands[temp]){
                                        if (temp < 319){
                                            temp++;
                                        }
                                        if (temp == 319) {
                                            break;
                                        }
                                    }
                                }
                                comandId = temp;
                            }
                            if (comandCount % 4 == 1){
                                if (!judge())
                                    break;
                                int temp = comandId;
                                temp = (int) Math.round(Math.random() * comandId);
                                int count = 0;
                                while (!commands[temp]){
                                    temp = (int) Math.round(Math.random() * comandId);
                                    count++;
                                    if (count == 10000)
                                        break;
                                }
                                comandId = temp;
                            }
                            if (comandCount % 4 == 2){
                                if (!judge())
                                    break;
                                int temp = comandId;
                                if (temp < 319) {
                                    temp++;
                                    while (!commands[temp]){
                                        if (temp < 319){
                                            temp++;
                                        }
                                        if (temp == 319) {
                                            break;
                                        }
                                    }
                                }
                                comandId = temp;
                            }
                            if (comandCount % 4 == 3){
                                if (!judge())
                                    break;
                                int temp = comandId;
                                temp = (int) Math.round(Math.random() * (319 - comandId) + comandId);
                                int count = 0;
                                while (!commands[temp]){
                                    temp = (int) Math.round(Math.random() * (319 - comandId) + comandId);
                                    count++;
                                    if (count == 100000)
                                        break;
                                }
                                comandId = temp;
                            }
                            comandCount++;
                        }
                        comandCount = 0;

                        logArea.append("缺页率: " + 0.1 * lackTime / 320 * 100 + "%\n");

                        for (int i = 0; i < 320; i++)
                            commands[i] = true;
                    }
                };
                t.start();
            }
        });
        layout.setConstraints(start, s);

        // LogArea init
        s.gridheight = 4;
        logArea.setFont(new Font("黑体",Font.BOLD,32));
        layout.setConstraints(logArea, s);
        logArea.append("You choose: FIFO\n");

        setSize(1000, 800);
        setVisible(true);
    }

    public static void main(String args[]){
        // blocks init
        blok[0] = new bloks(-1, -1);
        blok[1] = new bloks(-1, -1);
        blok[2] = new bloks(-1, -1);
        blok[3] = new bloks(-1, -1);

        // pages init
        for (int i = 0; i < 32; i++){
            pages.add(new bloks(i, -1));
        }

        // commands init
        for (int i = 0; i < 320; i++)
            commands[i] = true;

        new ui();
    }
}

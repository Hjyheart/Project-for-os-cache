import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created by hongjiayong on 16/5/15.
 */
public class ui extends JFrame{
    private static Container con = new Container();
    private static JComboBox modeChose = new JComboBox();
    private static JButton onebyoneDisplay = new JButton("逐步演示");
    private static JButton autoDisplay = new JButton("自动演示");
    private static JButton reset = new JButton("复位");
    public static TextArea board = new TextArea();

    public static firstAdapter first = new firstAdapter();
    public static bestAdapter best = new bestAdapter();

    public static ArrayList<commamd> commamds;

    public static int commandCount = 0;

    private static String helpMessage =
                    "<html>" +
                    "<body>" +
                    "<h1>动态内存调度</h1>" +
                    "<h2>基本算法</h2>" +
                    "<h3>最先适配算法</h3>" +
                    "<ul> <li>从头开始找到最先满足要求的内存块</li> <li>释放内存时合并空白内存块</li> </ul>" +
                    "<h3>最佳适配算法</h3>" +
                    "<ul> <li>遍历所有可用内存块，找出满足要求的最小内存块</li> <li>释放内存时合并空白内存块</li> </ul>" +
                    "<h2>操作说明</h2>" +
                    "<ul> <li>顶端多选按钮可以选择模式</li> <li>中心文本框用于显示操作指令和操作结果</li> " +
                    "<li>复位按钮可以将操作初始化</li> <li>左侧自动演示按钮可以自动演示所有操作</li>" +
                    "<li>右侧单步操作可以逐步运行指令</li>" +
                    "<li>最下方空白处将显示内存块当前状况</li> </ul>" +
                    "<h2>特别说明</h2>" +
                    "<ul> <li>白色部分为空白内存块</li> </ul>" +
                    "</body>" +
                    "</html>";

    public ui(){

        setTitle("动态分配内存");
        Container container = new Container();
        container.setLayout(new GridLayout(2, 1));
        Container settings = new Container();
        settings.setLayout(new BorderLayout());

        // commamds init
        commamds = new ArrayList<commamd>();
        commamds.add(new commamd(true, 130, 1));
        commamds.add(new commamd(true, 60, 2));
        commamds.add(new commamd(true, 100, 3));
        commamds.add(new commamd(false, 60, 2));
        commamds.add(new commamd(true, 200, 4));
        commamds.add(new commamd(false, 100, 3));
        commamds.add(new commamd(false, 130, 1));
        commamds.add(new commamd(true, 140, 5));
        commamds.add(new commamd(true, 60, 6));
        commamds.add(new commamd(true, 50, 7));
        commamds.add(new commamd(false, 60, 6));

        // auto init
        autoDisplay.setOpaque(true);
        autoDisplay.setBackground(Color.ORANGE);
        autoDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.append("自动演示开始\n");
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        first = new firstAdapter();
                        best = new bestAdapter();
                        for (int i = 0; i <= commamds.size(); i++) {
                            onebyoneDisplay.doClick();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                };
                t.start();

            }
        });
        settings.add(autoDisplay, BorderLayout.WEST);

        // modeChooser init
        modeChose.setOpaque(true);
        modeChose.setBackground(Color.CYAN);
        modeChose.addItem("最早适用");
        modeChose.addItem("最佳适用");
        modeChose.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ItemEvent.SELECTED == e.getStateChange()){
                    // select first
                    if(e.getItem().toString().equals("最早适用")){
                        commandCount = 0;
                        board.append("You choose: " + e.getItem().toString() + "\n");
                        first = new firstAdapter();
                    }
                    // select best
                    if(e.getItem().toString().equals("最佳适用")){
                        commandCount = 0;
                        board.append("You choose: " + e.getItem().toString() + "\n");
                        best = new bestAdapter();
                    }
                    con.invalidate();
                    con.removeAll();
                    con.validate();
                }
            }
        });
        settings.add(modeChose, BorderLayout.NORTH);

        // display init
        onebyoneDisplay.setOpaque(true);
        onebyoneDisplay.setBackground(Color.green);
        onebyoneDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                con.invalidate();
                con.removeAll();
                // first
                if(modeChose.getSelectedIndex() == 0) {
                    try {
                        commamd current = commamds.get(commandCount);
                        String operation = "";
                        if (current.getState()){
                            operation = "申请";
                        }else{
                            operation = "释放";
                        }
                        board.append("作业" + current.getTag() + ": " + operation + " " + current.getLenth() + "内存\n");
                        commandCount++;
                        if (current.getState()) {
                            first.putIn(current.getLenth(), current.getTag());
                        } else {
                            first.releaseMem(current.getTag());
                        }
                        for (int i = 0; i < first.table.size(); i++) {
                            if (!first.table.get(i).getMemState()) {
                                myCanvas cache = new myCanvas(Color.white, first.table.get(i).getStart(), first.table.get(i).getLenth());
                                con.add(cache);
                            } else {
                                int A = (int) Math.round(Math.random() * 255);
                                int B = (int) Math.round(Math.random() * 255);
                                int C = (int) Math.round(Math.random() * 255);
                                myCanvas cache = new myCanvas(new Color(A, B, C), first.table.get(i).getStart(), first.table.get(i).getLenth());
                                con.add(cache);
                            }
                        }
                    }catch (RuntimeException E){
                        board.append("模拟结束!\n");
                        first = new firstAdapter();
                        commandCount = 0;
                    }
                }
                // best
                if(modeChose.getSelectedIndex() == 1){
                    try {
                        commamd current = commamds.get(commandCount);
                        String operation = "";
                        if (current.getState()){
                            operation = "申请";
                        }else{
                            operation = "释放";
                        }
                        board.append("作业" + current.getTag() + ": " + operation + " " + current.getLenth() + "内存\n");
                        commandCount++;
                        if (current.getState()) {
                            best.putIn(current.getLenth(), current.getTag());
                        } else {
                            best.releaseMem(current.getTag());
                        }
                        for (int i = 0; i < best.table.size(); i++) {
                            if (!best.table.get(i).getMemState()) {
                                myCanvas cache = new myCanvas(Color.white, best.table.get(i).getStart(), best.table.get(i).getLenth());
                                con.add(cache);
                            } else {
                                int A = (int) Math.round(Math.random() * 255);
                                int B = (int) Math.round(Math.random() * 255);
                                int C = (int) Math.round(Math.random() * 255);
                                myCanvas cache = new myCanvas(new Color(A, B, C), best.table.get(i).getStart(), best.table.get(i).getLenth());
                                con.add(cache);
                            }
                        }
                    }catch (RuntimeException E){
                        board.append("模拟结束!\n");
                        best = new bestAdapter();
                        commandCount = 0;
                    }
                }
                con.validate();
            }
        });
        settings.add(onebyoneDisplay, BorderLayout.EAST);

        // board init
        board.setEditable(false);
        board.setFont(new Font("黑体",Font.BOLD,32));
        board.append("You choose: 最早适配\n");
        settings.add(board, BorderLayout.CENTER);

        // reset init
        reset.setOpaque(true);
        reset.setBackground(Color.pink);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                con.removeAll();
                commandCount = 0;
                first = new firstAdapter();
                best = new bestAdapter();
                board.append("已进行重置!\n");
            }
        });
        settings.add(reset, BorderLayout.SOUTH);

        container.add(settings);

        con.setSize(1000, 400);
        con.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        container.add(con);


        setContentPane(container);
        setSize(1000, 700);
        setVisible(true);

        // help init
        JOptionPane.showMessageDialog(null,
                helpMessage,
                "动态内存调度",
                JOptionPane.DEFAULT_OPTION);
    }

    public static void main(String args[]) throws InterruptedException {

        new ui();

    }
}

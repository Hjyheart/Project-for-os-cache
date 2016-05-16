import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by hongjiayong on 16/5/15.
 */
public class ui extends JFrame{
    private static Container con = new Container();
    private static JLabel title = new JLabel("最早适用");
    private static JComboBox modeChose = new JComboBox();
    private static JButton display = new JButton("开始演示");
    private static TextArea board = new TextArea();

    public static firstAdapter first = new firstAdapter();
    public static bestAdapter best = new bestAdapter();

    public ui(){

        setTitle("动态分配内存");
        Container container = new Container();
        container.setLayout(new GridLayout(2, 1));
        Container settings = new Container();
        settings.setLayout(new BorderLayout());
        // title init
        title.setOpaque(true);
        title.setBackground(Color.ORANGE);
        settings.add(title, BorderLayout.WEST);
        // modeChooser init
        modeChose.setOpaque(true);
        modeChose.setBackground(Color.CYAN);
        modeChose.addItem("最早适用");
        modeChose.addItem("最佳适用");
        modeChose.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ItemEvent.SELECTED == e.getStateChange()){
                    title.setText(modeChose.getItemAt(ItemEvent.SELECTED).toString());
                    // select first
                    if(ItemEvent.SELECTED == 0){
                        first = new firstAdapter();
                    }
                    // select best
                    if(ItemEvent.SELECTED == 1){
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
        display.setOpaque(true);
        display.setBackground(Color.green);
        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                con.invalidate();
                // first
                if(modeChose.getSelectedIndex() == 0) {
                    for (int i = 0; i < first.table.size(); i++) {
                        if (!first.table.get(i).getMemState()) {
                            myCanvas cache = new myCanvas(Color.white, first.table.get(i).getStart(), first.table.get(i).getLenth());
                            con.add(cache);
                        } else {
                            myCanvas cache = new myCanvas(Color.blue, first.table.get(i).getStart(), first.table.get(i).getLenth());
                            con.add(cache);
                        }
                    }
                }
                // best
                if(modeChose.getSelectedIndex() == 1){

                }
                con.validate();
            }
        });
        settings.add(display, BorderLayout.EAST);
        // board init
        board.setEditable(false);
        board.setFont(new Font("黑体",Font.BOLD,32));
        JScrollPane pane = new JScrollPane(board);
        settings.add(pane, BorderLayout.CENTER);

        container.add(settings);

        con.setSize(1000, 850);
        con.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        container.add(con);


        setContentPane(container);
        setSize(1000, 1500);
        setVisible(true);
    }

    public static void main(String args[]){
        first.putIn(100, 1);
        first.putIn(200, 2);
        first.releaseMem(1);
        first.putIn(80, 4);
        first.releaseMem(2);
        new ui();
    }
}

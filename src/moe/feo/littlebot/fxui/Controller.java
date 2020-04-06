package moe.feo.littlebot.fxui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import moe.feo.littlebot.Group;
import moe.feo.littlebot.Group.GroupMannager;
import javafx.scene.input.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {
    private Group group = GroupMannager.getInstance().list.get(0);

    @FXML
    TabPane tabPane;
    @FXML
    TextArea historyTextArea;
    @FXML
    TextArea inputTextArea;
    @FXML
    Button sendButton;

    public void init(){
        for (Group group : GroupMannager.getInstance().list) {
            Tab tab = new Tab(group.name);
            tabPane.getTabs().add(tab);
        }
    }

    private void recordMessage(String str) {// 将消息记录至历史区域
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String message = new String("[" + ft.format(date) + "]: " + str + System.lineSeparator());
        historyTextArea.appendText(message);
    }

    @FXML
    public void send() {//发送, 将输入框的文字发送
        String msg = inputTextArea.getText();
        inputTextArea.clear();
        recordMessage(msg);

        group.hook.send(msg);
    }

    @FXML
    public void keyPressed(KeyEvent event) {// 按下键
        if (event.isControlDown() && event.getCode()== KeyCode.ENTER) {// ctrl+enter
            inputTextArea.appendText(System.lineSeparator());// 向输入区写入一个回车
        } else if (event.getCode() == KeyCode.ENTER) {// enter
            send();// 发送消息
        }
    }

    @FXML
    public void keyReleased(KeyEvent event) {// 松开键
        if (!event.isControlDown() && event.getCode()==KeyCode.ENTER) {// enter被松开且没按住ctrl
            inputTextArea.setText("");// 视为发送了消息, 清空刚刚输入的回车
        }
    }

    @FXML
    public void stateChanged() {
        if (group != null) {// 把历史记录和输入框的内容保存至group对象
            group.history = historyTextArea.getText();
            group.sending = inputTextArea.getText();
        }
        int index = tabPane.getSelectionModel().getSelectedIndex();// 这个index和list中的list应该是一样的
        group = GroupMannager.getInstance().list.get(index);// 更换group对象
        // 恢复group对象中的历史记录和输入框
        historyTextArea.setText(group.history);
        inputTextArea.setText(group.sending);
    }

}

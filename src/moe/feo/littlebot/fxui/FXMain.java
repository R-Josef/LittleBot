package moe.feo.littlebot.fxui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import moe.feo.littlebot.Config;
import moe.feo.littlebot.Group.GroupMannager;

import javax.swing.*;

public class FXMain extends Application {
    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        loader = new FXMLLoader();
        
        if (Config.getInstance().key.isEmpty()) {
            JOptionPane.showMessageDialog(null, "请在当前目录下的Key.txt文件中写入Key\n格式为: [群名称]=[key]");
            Platform.exit();
            return;
        }
        GroupMannager.getInstance().load(Config.getInstance().key);

        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.init();

        primaryStage.setTitle("LittleBot");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("icon.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
    }
}

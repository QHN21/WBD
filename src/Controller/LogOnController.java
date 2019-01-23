package Controller;

import Controller.Admin.AdminMenuController;
import Controller.User.UserMenuController;
import Model.Connection.JDBC_conn;
import Model.User;
import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class LogOnController implements Initializable{
    private JDBC_conn connection;
    @FXML
    private StackPane rootPane;
    @FXML
    private StackPane dialogStack;
    @FXML
    private JFXTextField logField;
    @FXML
    private JFXPasswordField passField;

    private LinkedList<User> userData = new LinkedList<>();

    public void pressButtonConnect(ActionEvent evt){
        boolean connSucess = false;
        int userNumber;
        try {
             for(userNumber = 0; userNumber < userData.size();userNumber++){
                 if(logField.getText().equals(userData.get(userNumber).getLogin()) && passField.getText().equals(userData.get(userNumber).getPassword())) {
                     connSucess = true;
                     connection.getConnection("kbednars","kbednars");
                     break;
                 }
             }
             if(connSucess == false){
                 logDialog();

             }else{
                 FXMLLoader loader = new FXMLLoader();
                 Parent Menu;
                 if(userData.get(userNumber).isAdminAccess()){
                     loader.setLocation(getClass().getResource("/View/Admin/adminMenu.fxml"));
                     Menu = loader.load();
                     AdminMenuController adminMenuController = loader.getController();
                     adminMenuController.setConnection(connection);
                     adminMenuController.wyswietlKlient();
                 }else {
                     loader.setLocation(getClass().getResource("/View/User/userMenu.fxml"));
                     Menu = loader.load();
                     UserMenuController userMenuController = loader.getController();
                     userMenuController.setConnection(connection);
                     userMenuController.getUserData();
                 }


                 Scene MenuScene = new Scene(Menu, 640, 480);
                 Stage window = (Stage)((Node)evt.getSource()).getScene().getWindow();

                 window.setTitle("Centrum RMA");
                 window.setScene(MenuScene);
                 window.show();
             }
             } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void logDialog(){
        rootPane.setEffect(new BoxBlur(4,4,3));
        rootPane.setDisable(true);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton button = new JFXButton("OK");
        button.setStyle("-fx-background-color: #00b2ff");
        button.setPrefSize(50,20);
        JFXDialog dialog = new JFXDialog(dialogStack,dialogLayout, JFXDialog.DialogTransition.CENTER);
        dialog.setOverlayClose(false);

        button.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,(MouseEvent mouseEvent) -> dialog.close());

        dialog.setOnDialogClosed((JFXDialogEvent event)->{
            rootPane.setEffect(null);
            rootPane.setDisable(false);
        });
        Text text = new Text("Nieprawidłowe hasło lub login użytkownika.");
        text.setFont(Font.font(16));
        dialogLayout.setBody(text);
        dialogLayout.setActions(button);
        dialog.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = new JDBC_conn();
        userData.add(new User("User","User",false));
        userData.add(new User("admin","admin",true));
    }
}

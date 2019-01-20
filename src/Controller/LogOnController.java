package Controller;

import Model.Connection.JDBC_conn;
import Model.user;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class LogOnController implements Initializable{
    private JDBC_conn connection;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private StackPane dialogStack;
    @FXML
    private TextField logField;
    @FXML
    private TextField passField;

    private LinkedList<user> userData = new LinkedList<>();

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
                     loader.setLocation(getClass().getResource("/View/adminMenu.fxml"));
                     Menu = loader.load();
                     AdminMenuController adminMenuController = loader.getController();
                     adminMenuController.setConnection(connection);
                 }else {
                     loader.setLocation(getClass().getResource("/View/clientMenu.fxml"));
                     Menu = loader.load();
                     ClientMenuController clientMenuController = loader.getController();
                     clientMenuController.setConnection(connection);
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
        BoxBlur blur = new BoxBlur(4,4,4);
        rootPane.setEffect(blur);
        rootPane.setDisable(true);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton button = new JFXButton("OK");
        button.setStyle("-fx-background-color: #00b2ff");
        button.setPrefSize(50,20);
        JFXDialog dialog = new JFXDialog(dialogStack,dialogLayout, JFXDialog.DialogTransition.CENTER);

        button.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,(MouseEvent mouseEvent) -> {
            dialog.close();
        });

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
        userData.add(new user("klient","klient",false));
        userData.add(new user("admin","admin",true));
    }
}

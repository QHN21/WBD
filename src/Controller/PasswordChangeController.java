package Controller;

import Controller.User.UserMenuController;
import Model.Connection.JDBC_conn;
import Model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
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
import java.util.ResourceBundle;

public class PasswordChangeController implements Initializable
{
    private User user;

    @FXML
    private StackPane rootPane;
    @FXML
    private StackPane dialogStack;
    @FXML
    private JFXPasswordField passFieldOriginal;
    @FXML
    private JFXPasswordField passFieldNew;
    @FXML
    private JFXPasswordField passFieldNewRepeated;

    private JDBC_conn connection;

    public void setConnection(JDBC_conn connection)
    {
        this.connection = connection;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void pressButtonPotwierdz(ActionEvent evt) throws IOException{
        if(passFieldNew.getText().equals(passFieldNewRepeated.getText()) && passFieldOriginal.getText().equals(user.getPassword())){
            String sqlUpdate = "UPDATE users SET password = \'" + passFieldNew.getText() + "\' WHERE username = \'" + user.getLogin() + "\'";
            try
            {
                connection.sendQuery(sqlUpdate);
                user.setPassword(passFieldNew.getText());
                showGoodDialog();
            } catch (SQLException e)
            {
                e.printStackTrace();
                showErrorDialog();
            }
        }
        else{
            showErrorDialog();
        }
    }
    public void pressButtonCofnij(ActionEvent evt) throws IOException {
        goBack();
    }
    public void goBack(){
        rootPane.getScene().getWindow().hide();
    }

    public void showErrorDialog(){
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
            passFieldOriginal.setText("");
            passFieldNew.setText("");
            passFieldNewRepeated.setText("");
        });
        Text text = new Text("BLAD!!! Nieprawidłowe dane.");
        text.setFont(Font.font(16));
        dialogLayout.setBody(text);
        dialogLayout.setActions(button);
        dialog.show();
    }
    public void showGoodDialog(){
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
            goBack();
        });
        Text text = new Text("Pomyślna zmiana hasła");
        text.setFont(Font.font(16));
        dialogLayout.setBody(text);
        dialogLayout.setActions(button);
        dialog.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}

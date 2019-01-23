package Controller;

import Controller.User.UserMenuController;
import Model.Connection.JDBC_conn;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PasswordChangeController implements Initializable
{
    @FXML
    private JFXPasswordField passFieldOriginal;
    @FXML
    private JFXPasswordField passFieldNew;
    @FXML
    private JFXPasswordField passFieldNewRepeated;

    private JDBC_conn connection;
    private String previousResource;
    public void setConnection(JDBC_conn connection)
    {
        this.connection = connection;
    }

    public void setPreviousResource(String previousResource)
    {
        this.previousResource = previousResource;
    }

    public void goBack(ActionEvent evt) throws IOException {
        Stage hide = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        hide.hide();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}

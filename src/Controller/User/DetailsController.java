package Controller.User;

import Model.Connection.JDBC_conn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable
{
    private JDBC_conn connection;

    @FXML
    private StackPane rootPane;

    public void setConnection(JDBC_conn connection)
    {
        this.connection = connection;
    }

    public void pressButtonCofnij(ActionEvent evt) throws IOException
    {
        goBack();
    }

    public void goBack(){
        rootPane.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}

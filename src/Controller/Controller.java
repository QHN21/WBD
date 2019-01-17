package Controller;

import Model.Connection.JDBC_conn;
import Model.Entities.komponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class Controller implements Initializable{
    private JDBC_conn connection;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField logField;
    @FXML
    private TextField passField;
    @FXML
    private AnchorPane logDialog;


    public void pressButtonConnect(ActionEvent evt){
        boolean connSucess;

        try {
             connSucess = connection.getConnection(logField.getText(),passField.getText());
             if(connSucess == false){
                 BoxBlur blur = new BoxBlur(4,4,4);
                 rootPane.setEffect(blur);
                 rootPane.setDisable(true);
                 logDialog.setVisible(true);


             }else{
                 FXMLLoader loader = new FXMLLoader();
                 loader.setLocation(getClass().getResource("../View/clientMenu.fxml"));
                 Parent clientMenu = loader.load();


                 Scene clientMenuScene = new Scene(clientMenu, 640, 480);

                 ClientMenuController clientMenuController = loader.getController();
                 clientMenuController.setConnection(connection);

                 Stage window = (Stage)((Node)evt.getSource()).getScene().getWindow();

                 window.setTitle("Centrum RMA");
                 window.setScene(clientMenuScene);
                 window.show();
             }
             } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void pressButtonLogOk(ActionEvent evt){
        logDialog.setVisible(false);
        rootPane.setDisable(false);
        rootPane.setEffect(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = new JDBC_conn();
    }
}

package sample;

import connection.JDBC_conn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class Controller implements Initializable{
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<komponent> pa;
    @FXML
    public TableColumn<komponent, String> id;
    @FXML
    public TableColumn<komponent, String> nazwa;
    @FXML
    private TextField logField;
    @FXML
    private TextField passField;
    @FXML
    private AnchorPane logDialog;

    private LinkedList<String> ll;
    private ObservableList <komponent> ol = FXCollections.observableArrayList();


    public void pressButtonConnect(ActionEvent evt){
        JDBC_conn connection = new JDBC_conn();
        boolean connSucess;
        try {
             connSucess = connection.getConnection(logField.getText(),passField.getText());
             if(connSucess == false){
                 BoxBlur blur = new BoxBlur(4,4,4);
                 rootPane.setEffect(blur);
                 rootPane.setDisable(true);
                 logDialog.setVisible(true);


             }
             ll = connection.getData();
             ol.clear();
             pa.getColumns().clear();
             for(int i = 0; i<ll.size();){
                 ol.add(new komponent(ll.get(i++),ll.get(i++)));
             }
             pa.getColumns().addAll(id,nazwa);
             } catch (SQLException e) {
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
        id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nazwa.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        pa.setItems(ol);
    }
}

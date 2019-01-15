package Controller;

import Model.Connection.JDBC_conn;
import Model.Entities.komponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;

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
        boolean connSucess;

        try {
             connSucess = connection.getConnection(logField.getText(),passField.getText());
             if(connSucess == false){
                 BoxBlur blur = new BoxBlur(4,4,4);
                 rootPane.setEffect(blur);
                 rootPane.setDisable(true);
                 logDialog.setVisible(true);


             }
             } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void pressButtonSelect(ActionEvent evt) throws SQLException {
        String sqlSelect = "SELECT * FROM \"Komponenty\"";
        getData(sqlSelect);
    }

    public void getData(String sqlSelect) throws SQLException {
        ResultSet rs;
        rs = connection.getDataServer(sqlSelect);
        ol.clear();
        while (rs.next()) {
            ol.add(new komponent(rs.getString("Nazwa") ,rs.getString(3)));
        }
        pa.getColumns().clear();
        pa.getColumns().addAll(id,nazwa);
    }

    public void pressButtonLogOk(ActionEvent evt){
        logDialog.setVisible(false);
        rootPane.setDisable(false);
        rootPane.setEffect(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = new JDBC_conn();
        id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nazwa.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        pa.setItems(ol);
    }
}

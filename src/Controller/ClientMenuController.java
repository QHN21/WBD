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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable{
    private JDBC_conn connection;
    @FXML
    private TableView<komponent> komponent_table;
    @FXML
    public TableColumn<komponent, String> nazwa;
    @FXML
    public TableColumn<komponent, Integer> nr_seryjny;
    @FXML
    public TableColumn<komponent, String> rodzajKomponentu;

    private ObservableList <komponent> ol = FXCollections.observableArrayList();

    public void setConnection(JDBC_conn connection)
    {
        this.connection = connection;
    }

    public void pressButtonSelect(ActionEvent evt) throws SQLException {
        String sqlSelect = "SELECT * FROM Komponenty";
        getData(sqlSelect);
    }

    public void pressButtonWyloguj(ActionEvent evt) throws SQLException, IOException {
        connection.getCon().close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/logON.fxml"));
        Parent logOn = loader.load();
        Scene logScene = new Scene(logOn, 640, 480);
        Stage window = (Stage)((Node)evt.getSource()).getScene().getWindow();
        window.setTitle("Centrum RMA");
        window.setScene(logScene);
        window.show();
    }

    public void getData(String sqlSelect) throws SQLException {
        ResultSet rs;
        rs = connection.sendQuery(sqlSelect);
        ol.clear();
        while (rs.next()) {
            ol.add(new komponent(rs.getString(3) ,rs.getInt(2),rs.getString(4)));
        }
        komponent_table.getColumns().clear();
        komponent_table.getColumns().addAll(nazwa,nr_seryjny,rodzajKomponentu);
    }

    public void pressButtonDodaj(ActionEvent evt) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/addKomponent.fxml"));
        Parent addKomponent = loader.load();

        Scene addKomponentScene = new Scene(addKomponent, 640, 480);

        AddKomponentController AddKompponentController = loader.getController();
        AddKompponentController.setConnection(connection);

        Stage window = (Stage)((Node)evt.getSource()).getScene().getWindow();

        window.setTitle("Centrum RMA");
        window.setScene(addKomponentScene);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nazwa.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        nr_seryjny.setCellValueFactory(new PropertyValueFactory<>("Nr_seryjny"));
        rodzajKomponentu.setCellValueFactory(new PropertyValueFactory<>("Typ"));
        komponent_table.setItems(ol);
    }
}

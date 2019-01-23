package Controller.Admin;

import Model.Connection.JDBC_conn;
import Model.Entities.Firma;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    private JDBC_conn connection;

    @FXML
    private StackPane rootPane;
    @FXML
    private StackPane dialogPane;

    @FXML
    private TableView <Firma> klientTable;
    @FXML
    private TableColumn<Firma,String> nazwa;
    @FXML
    private TableColumn <Firma, Integer> telefon;
    @FXML
    private TableColumn <Firma, String> email;
    @FXML
    private TableColumn <Firma, String> adres;

    private ObservableList<Firma> ol = FXCollections.observableArrayList();

    public void setConnection(JDBC_conn connection) {
        this.connection = connection;
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

    public void pressButtonHaslo(ActionEvent evt) throws SQLException, IOException {

    }

    public void pressButtonPolaczRMA(ActionEvent evt) throws SQLException, IOException {

    }

    public void pressButtonDodajKlienta(ActionEvent evt) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Admin/dodajKlient.fxml"));
        Parent dodKlient = loader.load();
        Scene klientScene = new Scene(dodKlient, 640, 480);
        DodajKlientController dodajKlientController = loader.getController();
        dodajKlientController.setConnection(connection);
        Stage window = (Stage)((Node)evt.getSource()).getScene().getWindow();
        window.setTitle("Centrum RMA");
        window.setScene(klientScene);
        window.show();
    }

    public void pressButtonUsunKlienta(ActionEvent evt) throws SQLException, IOException {
        klientTable.getSelectionModel().getSelectedItem().getNazwa();
    }

    public void pressButtonProdukty(ActionEvent evt) throws SQLException, IOException {

    }

    public void pressButtonKomponenty(ActionEvent evt) throws SQLException, IOException {

    }

    public void pressButtonRMA(ActionEvent evt) throws SQLException, IOException {

    }

    public void wyswietlKlient() throws SQLException {
        String sqlSelect = "SELECT * FROM Firmy JOIN Klienci ON klienci.firma_id=firmy.firma_id";
        ResultSet rs;
        rs = connection.sendQuery(sqlSelect);
        ol.clear();
        String numerBud;
        while (rs.next()) {
            if(rs.getString(8)==null)
                numerBud = rs.getString(7);
            else
                numerBud = rs.getString(7)+"/"+rs.getString(8);
            ol.add(new Firma(rs.getString(2) ,rs.getInt(3),rs.getString(4),rs.getString(5)+", "+
                    rs.getString(6)+" "+numerBud+", "+rs.getString(9)));
        }
        klientTable.getColumns().clear();
        klientTable.getColumns().addAll(nazwa,telefon,email,adres);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nazwa.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        telefon.setCellValueFactory(new PropertyValueFactory<>("Telefon"));
        email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        adres.setCellValueFactory(new PropertyValueFactory<>("Adres"));
        klientTable.setItems(ol);
    }
}

package Controller.Admin;

import Model.Connection.JDBC_conn;
import Model.Entities.Firma;
import Model.Entities.Produkt;
import com.jfoenix.controls.JFXTextField;
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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProduktyController implements Initializable {
    private JDBC_conn connection;

    @FXML
    private TableView<Produkt>produktyTable;
    @FXML
    private TableColumn<Produkt,String>nazwa;
    @FXML
    private TableColumn<Produkt,Integer>numerSeryjny;
    @FXML
    private TableColumn<Produkt,String>typ;
    @FXML
    private TableColumn<Produkt,Date>dataProdukcji;
    @FXML
    private TableColumn<Produkt,Date>dataAwarii;
    @FXML
    private JFXTextField szukajField;

    private ObservableList<Produkt> ol = FXCollections.observableArrayList();

    public void setConnection(JDBC_conn connection) {
        this.connection = connection;
    }

    public void pressButtonCofnij(ActionEvent evt) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Admin/adminMenu.fxml"));
        Parent admin = loader.load();
        Scene adminScene = new Scene(admin, 640, 480);
        AdminMenuController adminMenuController = loader.getController();
        adminMenuController.setConnection(connection);
        adminMenuController.wyswietlKlient("");
        Stage window = (Stage)((Node)evt.getSource()).getScene().getWindow();
        window.setTitle("Centrum RMA");
        window.setScene(adminScene);
        window.show();
    }

    public void pressButtonSzukaj(ActionEvent evt) throws SQLException {
        wyswietlProdukt(szukajField.getText());
    }

    public void wyswietlProdukt(String szukaj) throws SQLException {
        String sqlSelect;
        if(szukaj != "")
            sqlSelect = "SELECT * FROM Produkty WHERE LOWER(nazwa_produktu) LIKE LOWER('"+szukaj+"%')";
        else
            sqlSelect = "SELECT * FROM Produkty";
        ResultSet rs;
        rs = connection.sendQuery(sqlSelect);
        ol.clear();
        while (rs.next()) {
            ol.add(new Produkt(rs.getInt(2),rs.getInt(3),rs.getString(4),
                    rs.getString(5),rs.getDate(6),rs.getDate(7),rs.getDate(8)
                    ,rs.getDate(9),"brak"));
        }
        produktyTable.getColumns().clear();
        produktyTable.getColumns().addAll(nazwa,numerSeryjny,typ,dataProdukcji,dataAwarii);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        numerSeryjny.setCellValueFactory(new PropertyValueFactory<>("numerSeryjny"));
        typ.setCellValueFactory(new PropertyValueFactory<>("typ"));
        dataProdukcji.setCellValueFactory(new PropertyValueFactory<>("dataProdukcji"));
        dataAwarii.setCellValueFactory(new PropertyValueFactory<>("dataAwarii"));
        produktyTable.setItems(ol);
    }
}

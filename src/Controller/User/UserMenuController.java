package Controller.User;

import Controller.AddKomponentController;
import Model.Connection.JDBC_conn;
import Model.Entities.RMA;
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
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable{
    private JDBC_conn connection;
    @FXML
    private TableView<RMA> rmaTable;
    @FXML
    public TableColumn<RMA, String> status;
    @FXML
    public TableColumn<RMA, Date> dataUtworzenia;
    @FXML
    public TableColumn<RMA, Date> dataZakonczenia;
    @FXML
    public TableColumn<RMA, String> nazwaProduktu;

    private ObservableList <RMA> ol = FXCollections.observableArrayList();

    public void setConnection(JDBC_conn connection)
    {
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
    public void pressButtonSzczegoly(ActionEvent evt){

    }
    public void getUserData(){
        String sqlSelect = "SELECT * FROM RMA JOIN PRODUKTY ON RMA.RMA_ID = PRODUKTY.RMA_ID WHERE PRODUKTY.KLIENT_ID = 2";
        try
        {
            getData(sqlSelect);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void getData(String sqlSelect) throws SQLException {
        ResultSet rs;
        rs = connection.sendQuery(sqlSelect);
        ol.clear();
        while (rs.next()) {
            ol.add(new RMA(rs.getString(4) ,rs.getDate(2),rs.getDate(3),rs.getString(9)));
        }
        rmaTable.getColumns().clear();
        rmaTable.getColumns().addAll(status, dataUtworzenia, dataZakonczenia, nazwaProduktu);
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
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        dataUtworzenia.setCellValueFactory(new PropertyValueFactory<>("dataUtworzenia"));
        dataZakonczenia.setCellValueFactory(new PropertyValueFactory<>("dataZakonczenia"));
        nazwaProduktu.setCellValueFactory(new PropertyValueFactory<>("nazwaProduktu"));
        rmaTable.setItems(ol);
    }
}

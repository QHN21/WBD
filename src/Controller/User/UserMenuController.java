package Controller.User;

import Controller.AddKomponentController;
import Controller.PasswordChangeController;
import Model.Connection.JDBC_conn;
import Model.Entities.RMA;
import Model.User;
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

import javax.swing.text.View;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable{
    private JDBC_conn connection;
    private User user;

    @FXML
    private JFXTextField companyName;

    @FXML
    private JFXTextField phoneNumber;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField adres;

    @FXML
    private TableView<RMA> rmaTable;
    @FXML
    public TableColumn<RMA, Integer> rmaID;
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

    public void setUser(User user)
    {
        this.user = user;
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
    /*public void pressButtonSzczegoly(ActionEvent evt)throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/User/details.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent, 640, 480);

        DetailsController detailsController = loader.getController();
        detailsController.setConnection(connection);

        Stage window = new Stage();
        window.initOwner((Stage)((Node)evt.getSource()).getScene().getWindow());
        window.setTitle("Centrum RMA");
        window.setScene(scene);
        window.showAndWait();
    }*/

    public void pressButtonEdytuj(ActionEvent evt)throws  IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/User/editUserData.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent, 640, 480);

        EditUserDataController editUserDataController = loader.getController();
        editUserDataController.setConnection(connection);
        editUserDataController.setUser(user);
        editUserDataController.getUserData();

        Stage window = new Stage();
        window.initOwner((Stage)((Node)evt.getSource()).getScene().getWindow());
        window.setTitle("Centrum RMA");
        window.setScene(scene);
        window.showAndWait();
        getUserData();
    }

    public void getUserData(){
        String sqlSelect = "SELECT * FROM RMA JOIN PRODUKTY ON RMA.RMA_ID = PRODUKTY.RMA_ID WHERE PRODUKTY.KLIENT_ID = " + user.getFirma_id();
        try
        {
            getData(sqlSelect);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        sqlSelect = "SELECT * FROM Firmy JOIN Klienci ON klienci.firma_id=firmy.firma_id WHERE firmy.firma_id = \'" + user.getFirma_id() + "\'";
        ResultSet rs;
        try
        {
            rs = connection.sendQuery(sqlSelect);
            if(rs.next())
            {
                companyName.setText(rs.getString(2));
                phoneNumber.setText(rs.getString(3));
                email.setText(rs.getString(4));
                adres.setText(rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + "/" + rs.getString(8) + " " + rs.getString(9));
            }
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
            ol.add(new RMA(rs.getInt(1),rs.getString(4) ,rs.getDate(2),rs.getDate(3),rs.getString(9)));
        }
        rmaTable.getColumns().clear();
        rmaTable.getColumns().addAll(rmaID, status, dataUtworzenia, dataZakonczenia, nazwaProduktu);
    }

    public void pressButtonZmienHaslo(ActionEvent evt) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/passwordChange.fxml"));
        Parent passwordChange = loader.load();

        Scene passwordChangeScene = new Scene(passwordChange, 640, 480);

        PasswordChangeController passwordChangeController = loader.getController();
        passwordChangeController.setConnection(connection);
        passwordChangeController.setUser(this.user);
        Stage window = new Stage();
        window.initOwner((Stage)((Node)evt.getSource()).getScene().getWindow());
        window.setTitle("Centrum RMA");
        window.setScene(passwordChangeScene);
        window.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rmaID.setCellValueFactory(new PropertyValueFactory<>("rmaID"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        dataUtworzenia.setCellValueFactory(new PropertyValueFactory<>("dataUtworzenia"));
        dataZakonczenia.setCellValueFactory(new PropertyValueFactory<>("dataZakonczenia"));
        nazwaProduktu.setCellValueFactory(new PropertyValueFactory<>("nazwaProduktu"));
        rmaTable.setItems(ol);
    }
}

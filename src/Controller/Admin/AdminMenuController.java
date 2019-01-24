package Controller.Admin;

import Controller.PasswordChangeController;
import Model.Connection.JDBC_conn;
import Model.Entities.Firma;
import Model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
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
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    private JDBC_conn connection;
    private User user;

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
    @FXML
    private JFXTextField szukajField;

    private ObservableList<Firma> ol = FXCollections.observableArrayList();

    public void setConnection(JDBC_conn connection) { this.connection = connection; }
    public void setUser(User user) { this.user = user; }

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

    public void pressButtonSzukaj(ActionEvent evt) throws SQLException, IOException {
        wyswietlKlient(szukajField.getText());
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
       usunDialog();
    }

    public void pressButtonProdukty(ActionEvent evt) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Admin/Produkty.fxml"));
        Parent dodKlient = loader.load();
        Scene klientScene = new Scene(dodKlient, 640, 480);
        ProduktyController produktyController = loader.getController();
        produktyController.setConnection(connection);
        produktyController.wyswietlProdukt("");
        Stage window = (Stage)((Node)evt.getSource()).getScene().getWindow();
        window.setTitle("Centrum RMA");
        window.setScene(klientScene);
        window.show();
    }

    public void pressButtonRMA(ActionEvent evt) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent Menu;
        loader.setLocation(getClass().getResource("/View/Admin/RMA.fxml"));
        Menu = loader.load();
        RMAController rmaController = loader.getController();
        rmaController.setConnection(connection);
        rmaController.wyswietlRMA();

        Scene MenuScene = new Scene(Menu, 640, 480);
        Stage window = (Stage)((Node)evt.getSource()).getScene().getWindow();

        window.setTitle("Centrum RMA");
        window.setScene(MenuScene);
        window.show();
    }

    public void wyswietlKlient(String szukaj) throws SQLException {
        String sqlSelect;
        if(szukaj != "")
            sqlSelect = "SELECT * FROM Firmy JOIN Klienci ON klienci.firma_id=firmy.firma_id WHERE LOWER(nazwa_firmy) " +
                    "LIKE LOWER('"+szukaj+"%')";
        else
            sqlSelect = "SELECT * FROM Firmy JOIN Klienci ON klienci.firma_id=firmy.firma_id";
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
                    rs.getString(6)+" "+numerBud+", "+rs.getString(9), rs.getInt(1)));
        }
        klientTable.getColumns().clear();
        klientTable.getColumns().addAll(nazwa,telefon,email,adres);
    }

    private void usunDialog(){
        rootPane.setEffect(new BoxBlur(2,2,3));
        rootPane.setDisable(true);
        JFXDialogLayout dialogLayout = new JFXDialogLayout();

        JFXButton buttonTak = new JFXButton("Tak");
        buttonTak.setStyle("-fx-background-color: #00b2ff");
        buttonTak.setPrefSize(50,20);

        JFXButton buttonNie = new JFXButton("Nie");
        buttonNie.setStyle("-fx-background-color: #00b2ff");
        buttonNie.setPrefSize(50,20);

        JFXDialog dialog = new JFXDialog(dialogPane,dialogLayout, JFXDialog.DialogTransition.CENTER);
        dialog.setOverlayClose(false);

        buttonTak.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent mouseEvent) -> {
            int idUsun = klientTable.getSelectionModel().getSelectedItem().getID();
            try {
                String sqlDelete = "DELETE FROM Firmy WHERE firma_id="+idUsun;
                connection.sendQuery(sqlDelete);
                sqlDelete = "DELETE FROM Klienci WHERE firma_id=" + idUsun;
                connection.sendQuery(sqlDelete);
                wyswietlKlient("");
            }catch (SQLException e) {
                e.printStackTrace();
            }
            dialog.close();
        });

        buttonNie.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent mouseEvent)-> {
            dialog.close();
        });

        dialog.setOnDialogClosed((JFXDialogEvent event)->{
            rootPane.setEffect(null);
            rootPane.setDisable(false);
        });

        Text text = new Text("Czy potwierdzasz usuwanie rekordu klienta?");
        text.setFont(Font.font(16));
        dialogLayout.setBody(text);
        dialogLayout.setActions(buttonTak, buttonNie);
        dialog.show();
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

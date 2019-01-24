package Controller.Admin;

import Model.Connection.JDBC_conn;
import Model.Entities.RMA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RMAController implements Initializable {

    private JDBC_conn connection;

    @FXML
    private StackPane rootPane;
    @FXML
    private TableView<RMA> RMATable;
    @FXML
    private TableColumn<RMA, Integer> rmaID;
    @FXML
    private TableColumn<RMA, String> status;
    @FXML
    private TableColumn<RMA, Date> dataUtworzenia;
    @FXML
    private TableColumn<RMA, Date> dataZakonczenia;
    @FXML
    private TableColumn<RMA, String> nazwaProduktu;
    @FXML
    private DatePicker dataOd;
    @FXML
    private DatePicker dataDo;

    private ObservableList<RMA> ol = FXCollections.observableArrayList();

    public void setConnection(JDBC_conn connection) { this.connection = connection; }

    public void pressButtonCofnij(ActionEvent evt) throws SQLException, IOException {
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

    public void pressButtonSzukaj(ActionEvent evt) throws SQLException, IOException {
        Boolean odChoosed = true;
        Boolean doChoosed = true;

        try {
            Date.valueOf(dataOd.getValue());
        }catch (NullPointerException e){
            odChoosed = false;
        }
        try {
            Date.valueOf(dataDo.getValue());
        }catch (NullPointerException e){
            doChoosed = false;
        }

        if(odChoosed && doChoosed){
            wyswietlRMA(Date.valueOf(dataOd.getValue()), Date.valueOf(dataDo.getValue()));
        }else if(odChoosed && !doChoosed){
            wyswietlRMA(Date.valueOf(dataOd.getValue()), Date.valueOf(LocalDate.now()));
        }else if(!odChoosed && doChoosed){
            wyswietlRMA(Date.valueOf(LocalDate.of(2000,1,1)), Date.valueOf(dataDo.getValue()));
        }else{
            wyswietlRMA(Date.valueOf(LocalDate.of(2000,1,1)), Date.valueOf(LocalDate.now()));
        }
    }

    public void pressButtonZmienStatus(ActionEvent evt) throws SQLException, IOException {

    }

    public void pressButtonStworzRMA(ActionEvent evt) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Admin/stworzRMA.fxml"));
        Parent dodKlient = loader.load();
        Scene klientScene = new Scene(dodKlient, 640, 480);
        DodajKlientController dodajKlientController = loader.getController();
        dodajKlientController.setConnection(connection);
        Stage window = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        window.setTitle("Centrum RMA");
        window.setScene(klientScene);
        window.show();
    }

    public void wyswietlRMA() throws SQLException {
        String sqlSelect = "SELECT * FROM RMA JOIN produkty ON rma.rma_id=produkty.rma_id";
        ResultSet rs;
        rs = connection.sendQuery(sqlSelect);
        ol.clear();
        while (rs.next()) {
            ol.add(new RMA(rs.getInt(1), rs.getString(4), rs.getDate(2), rs.getDate(3), rs.getString(9)));
        }
        RMATable.getColumns().clear();
        RMATable.getColumns().addAll(rmaID, status, dataUtworzenia, dataZakonczenia, nazwaProduktu);
    }

    public void wyswietlRMA(Date dataOd, Date dataDo) throws SQLException {
        String sqlSelect = "SELECT * FROM RMA JOIN produkty ON rma.rma_id=produkty.rma_id " +
                "WHERE data_utworzenia>='"+dataOd+"' AND (data_zakonczenia<='"+dataDo+"' OR data_zakonczenia IS NULL)";
        ResultSet rs;
        rs = connection.sendQuery(sqlSelect);
        ol.clear();
        while (rs.next()) {
            ol.add(new RMA(rs.getInt(1), rs.getString(4), rs.getDate(2), rs.getDate(3), rs.getString(9)));
        }
        RMATable.getColumns().clear();
        RMATable.getColumns().addAll(rmaID, status, dataUtworzenia, dataZakonczenia, nazwaProduktu);
    }

    /*private void usunDialog() {
        rootPane.setEffect(new BoxBlur(2, 2, 3));
        rootPane.setDisable(true);
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton buttonTak = new JFXButton("Tak");
        buttonTak.setStyle("-fx-background-color: #00b2ff");
        buttonTak.setPrefSize(50, 20);

        JFXButton buttonNie = new JFXButton("Nie");
        buttonNie.setStyle("-fx-background-color: #00b2ff");
        buttonNie.setPrefSize(50, 20);

        JFXDialog dialog = new JFXDialog(dialogPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        dialog.setOverlayClose(false);

        buttonTak.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            int idUsun = klientTable.getSelectionModel().getSelectedItem().getID();
            String sqlDelete = "DELETE FROM Firmy WHERE firma_id=" + idUsun;
            try {
                connection.sendQuery(sqlDelete);
                sqlDelete = "DELETE FROM Klienci WHERE firma_id=" + idUsun;
                connection.sendQuery(sqlDelete);
                wyswietlKlient("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dialog.close();
        });

        buttonNie.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            dialog.close();
        });

        dialog.setOnDialogClosed((JFXDialogEvent event) -> {
            rootPane.setEffect(null);
            rootPane.setDisable(false);
        });

        Text text = new Text("Czy potwierdzasz usuwanie rekordu klienta?");
        text.setFont(Font.font(16));
        dialogLayout.setBody(text);
        dialogLayout.setActions(buttonTak, buttonNie);
        dialog.show();
    }
*/
   // }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rmaID.setCellValueFactory(new PropertyValueFactory<>("rmaID"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        dataUtworzenia.setCellValueFactory(new PropertyValueFactory<>("dataUtworzenia"));
        dataZakonczenia.setCellValueFactory(new PropertyValueFactory<>("dataZakonczenia"));
        nazwaProduktu.setCellValueFactory(new PropertyValueFactory<>("nazwaProduktu"));
        RMATable.setItems(ol);
    }
}

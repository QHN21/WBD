package Controller.Admin;

import Model.Connection.JDBC_conn;
import Model.Entities.Firma;
import Model.Entities.RMA;
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
import javafx.scene.control.DatePicker;
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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StworzRMAController implements Initializable{
    private JDBC_conn connection;

    @FXML
    private StackPane rootPane;
    @FXML
    private StackPane dialogPane;
    @FXML
    private JFXTextField nazwaProduktuField;
    @FXML
    private JFXTextField numerSeryjnyField;
    @FXML
    private JFXTextField numerWersjiField;
    @FXML
    private JFXTextField typProduktuField;
    @FXML
    private JFXTextField nazwaFirmyField;
    @FXML
    private DatePicker dataProdukcji;
    @FXML
    private DatePicker dataSprzedazy;
    @FXML
    private DatePicker dataAktualizacji;
    @FXML
    private DatePicker dataAwarii;

    @FXML
    private TableView<Firma> klientTable;
    @FXML
    private TableColumn<Firma,String> nazwaColumn;
    private ObservableList<Firma> ol = FXCollections.observableArrayList();


    public void setConnection(JDBC_conn connection) {
        this.connection = connection;
    }

    public void buttonCofnij(ActionEvent evt) throws IOException, SQLException {
        goBack();
    }

    public void goBack(){
        rootPane.getScene().getWindow().hide();
    }

    public void buttonStworzRMA(ActionEvent evt){
        String sqlQuery = "SELECT MAX(RMA_ID) FROM RMA";
        ResultSet rs;
        Boolean error = false;
        String komunikat;
        try {
            rs = connection.sendQuery(sqlQuery);
            rs.next();
            int RMANumber = rs.getInt(1) + 1;

            String sqlInsert = "Insert into RMA (RMA_ID,Data_utworzenia,Status,Centrum_id) values ('"
                    + RMANumber + "','"+ Date.valueOf(LocalDate.now())+"','Wpis','1')";
            connection.sendQuery(sqlInsert);

            sqlQuery = "SELECT MAX(Produkt_id) FROM Produkty";
            rs = connection.sendQuery(sqlQuery);
            rs.next();
            int produktNumber = rs.getInt(1) + 1;

            sqlInsert = "Insert into Produkty (Produkt_id,Numer_seryjny,numer_wersji,nazwa_produktu," +
                    "typ_produktu,data_produkcji,data_sprzedazy,data_aktualizacji,data_wystapienia_awarii,rma_id,klient_id) values " +
                    "('"+produktNumber+"','"+numerSeryjnyField.getText()+"','"+numerWersjiField.getText()+"','"+nazwaProduktuField.getText()+"','"+typProduktuField.getText()+
                    "','"+dataProdukcji.getValue()+"','"+dataSprzedazy.getValue()+"','"+dataAktualizacji.getValue()+"','"
                    +dataAwarii.getValue()+"','"+RMANumber+"','"+klientTable.getSelectionModel().getSelectedItem().getID()+"')";
            connection.sendQuery(sqlInsert);
        } catch (SQLException e) {
            e.printStackTrace();
            error = true;
        }
        if(error){
            komunikat = "Nie udało się stworzyć nowego RMA.";
            dialogDodaj(komunikat);
        }else{
            komunikat = "Tworzenie nowego RMA zakończone powodzeniem.";
            dialogDodaj(komunikat);
        }
    }

    public void buttonSzukaj(ActionEvent evt) throws SQLException {
        wyswietlKlient(nazwaFirmyField.getText());
    }

    public void dialogDodaj(String komunikat){
        rootPane.setEffect(new BoxBlur(4,4,3));
        rootPane.setDisable(true);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton button = new JFXButton("OK");
        button.setStyle("-fx-background-color: #00b2ff");
        button.setPrefSize(50,20);
        JFXDialog dialog = new JFXDialog(dialogPane,dialogLayout, JFXDialog.DialogTransition.CENTER);
        dialog.setOverlayClose(false);

        button.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,(MouseEvent mouseEvent) -> dialog.close());

        dialog.setOnDialogClosed((JFXDialogEvent event)->{
            rootPane.setEffect(null);
            rootPane.setDisable(false);
        });
        Text text = new Text(komunikat);
        text.setFont(Font.font(16));
        dialogLayout.setBody(text);
        dialogLayout.setActions(button);
        dialog.show();
    }

    public void wyswietlKlient(String szukaj) throws SQLException {
        String sqlSelect;
        if(szukaj != "")
            sqlSelect = "SELECT * FROM Firmy JOIN Klienci ON klienci.firma_id=firmy.firma_id WHERE LOWER(nazwa_firmy) " +
                    "LIKE ('"+szukaj+"%') OR UPPER(nazwa_firmy) LIKE('"+szukaj+"%')";
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
        klientTable.getColumns().addAll(nazwaColumn);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nazwaColumn.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        klientTable.setItems(ol);
    }
}

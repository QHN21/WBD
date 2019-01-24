package Controller.Admin;

import Model.Connection.JDBC_conn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class DodajKlientController implements Initializable {
    private JDBC_conn connection;

    @FXML
    private StackPane rootPane;
    @FXML
    private StackPane dialogPane;
    @FXML
    private JFXTextField nazwaField;
    @FXML
    private JFXTextField telefonField;
    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXTextField miastoField;
    @FXML
    private JFXTextField ulicaField;
    @FXML
    private JFXTextField numerBudynkuField;
    @FXML
    private JFXTextField numerLokaluField;
    @FXML
    private JFXTextField kodPocztowyField;

    public void setConnection(JDBC_conn connection) {
        this.connection = connection;
    }

    public void buttonCofnij(ActionEvent evt) throws IOException, SQLException {
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

    public void buttonDodajKlient(ActionEvent evt){
        String sqlQuery = "SELECT MAX(Firma_ID) FROM Firmy";
        ResultSet rs;
        Boolean error = false;
        String komunikat;
        try {
            rs = connection.sendQuery(sqlQuery);
            rs.next();
            int firmaNumber = rs.getInt(1) + 1;

            String sqlInsert = "Insert into Firmy (Firma_ID,nazwa_firmy,Telefon,email,miasto,ulica,numer_budynku,numer_lokalu,kod_pocztowy,centrum_id) values ('"
                    + firmaNumber + "','" + nazwaField.getText() +
                    "','" + telefonField.getText() + "','" + emailField.getText() + "','" + miastoField.getText() + "', '" + ulicaField.getText() + "', '" + numerBudynkuField.getText()
                    + "', '" + numerLokaluField.getText() + "', '" + kodPocztowyField.getText() + "', '1')";
            rs = connection.sendQuery(sqlInsert);

            sqlQuery = "SELECT MAX(Klient_ID) FROM Klienci";
            rs = connection.sendQuery(sqlQuery);
            rs.next();
            int klientNumber = rs.getInt(1) + 1;

            sqlInsert = "Insert into Klienci (Klient_ID,preferowany_odbior,firma_id) values ('" + klientNumber + "', 'osobisty', '" + firmaNumber + "')";
            connection.sendQuery(sqlInsert);
        } catch (SQLException e) {
            error = true;
        }
        if(error){
            komunikat = "Nie udało się dodać nowego rekordu klient";
            dialogDodaj(komunikat);
        }else{
            komunikat = "Dodawanie nowego rekordu klient zakończone powodzeniem";
            dialogDodaj(komunikat);
        }
        System.out.println("basd");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

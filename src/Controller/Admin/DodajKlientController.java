package Controller.Admin;

import Model.Connection.JDBC_conn;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DodajKlientController implements Initializable {
    private JDBC_conn connection;

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
        adminMenuController.wyswietlKlient();
        Stage window = (Stage)((Node)evt.getSource()).getScene().getWindow();
        window.setTitle("Centrum RMA");
        window.setScene(adminScene);
        window.show();
    }

    public void buttonDodajKlient(ActionEvent evt){
        String sqlQuery = "SELECT MAX(Firma_ID) FROM Firmy";
        ResultSet rs;
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
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

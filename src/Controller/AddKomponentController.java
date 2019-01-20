package Controller;

import Model.Connection.JDBC_conn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddKomponentController  implements Initializable {

    private JDBC_conn connection;

    @FXML
    private TextField nazwaField;
    @FXML
    private TextField numerSeryjnyField;
    @FXML
    private TextField rodzajKomponentuField;


    public void setConnection(JDBC_conn connection)
    {
        this.connection = connection;
    }

    public void dodajKomponent(ActionEvent evt) throws SQLException {
        String sqlQuery = "SELECT COUNT(*) FROM Komponenty";
        ResultSet rs;
        rs = connection.sendQuery(sqlQuery);
        rs.next();
        int recordsNumber = rs.getInt(1)+1;

        sqlQuery = "Insert into Komponenty (Komponent_ID,Numer_seryjny,Nazwa,Rodzaj_komponentu) values ('"+recordsNumber+"','"+numerSeryjnyField.getText()+
                "','"+nazwaField.getText()+"','"+rodzajKomponentuField.getText()+"')";
        connection.sendQuery(sqlQuery);
    }

    public void zakonczDodawanie(ActionEvent evt) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/clientMenu.fxml"));
        Parent clientMenu = loader.load();

        Scene clientMenuScene = new Scene(clientMenu, 640, 480);

        ClientMenuController clientMenuController = loader.getController();
        clientMenuController.setConnection(connection);

        Stage window = (Stage)((Node)evt.getSource()).getScene().getWindow();

        window.setTitle("Centrum RMA");
        window.setScene(clientMenuScene);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}

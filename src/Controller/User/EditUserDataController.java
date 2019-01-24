package Controller.User;

import Model.Connection.JDBC_conn;
import Model.Entities.RMA;
import Model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditUserDataController implements Initializable
{
    private JDBC_conn connection;
    private User user;
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

    public void setConnection(JDBC_conn connection)
    {
        this.connection = connection;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void pressButtonCofnij(ActionEvent evt) throws IOException
    {
        goBack();
    }
    public void goBack(){
        rootPane.getScene().getWindow().hide();
    }

    public void pressButtonZatwierdz(ActionEvent evt){
        String sqlQuery = "SELECT MAX(Firma_ID) FROM Firmy";
        ResultSet rs;
        Boolean error = false;
        String komunikat;
        try {
            rs = connection.sendQuery(sqlQuery);
            rs.next();


            String sqlUpdate = "UPDATE Firmy SET nazwa_firmy =\'"+ nazwaField.getText() + "\' , telefon = \'" + telefonField.getText() + "\' , email = \'" + emailField.getText() + "\' , miasto = \'" + miastoField.getText() + "\', ulica = \'" +
                    ulicaField.getText() + "\', numer_budynku = " + numerBudynkuField.getText() + ", numer_lokalu = " + numerLokaluField.getText() + ", kod_pocztowy = \'" + kodPocztowyField.getText()+ "\' WHERE firma_id = \'" + user.getFirma_id() + "\'";

            connection.sendQuery(sqlUpdate);

        } catch (SQLException e) {
            error = true;
        }
        if(error){
            komunikat = "Edycja danych nie powiodła się. Błędne dane.";
            getUserData();
            dialogDodaj(komunikat);
        }else{
            komunikat = "Edycja zakończona powodzeniem";
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

    public void getUserData()
    {
        String sqlSelect = "SELECT * FROM Firmy JOIN Klienci ON klienci.firma_id=firmy.firma_id WHERE firmy.firma_id = \'" + user.getFirma_id() + "\'";
        ResultSet rs;
        try
        {
            rs = connection.sendQuery(sqlSelect);
            if (rs.next())
            {
                nazwaField.setText(rs.getString(2));
                telefonField.setText(rs.getString(3));
                emailField.setText(rs.getString(4));
                miastoField.setText(rs.getString(5));
                ulicaField.setText(rs.getString(6));
                numerBudynkuField.setText(rs.getString(7));
                numerLokaluField.setText(rs.getString(8));
                kodPocztowyField.setText(rs.getString(9));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}

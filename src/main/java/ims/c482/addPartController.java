package ims.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


/** Controller class for adding parts functionality.*/

public class addPartController implements Initializable{
/**Radio buttons for inHouse and outsourced parts. */
    @FXML private RadioButton inHouse;
    @FXML private RadioButton outsource;

    /**Label and text fields of the FXML. */
    @FXML private Label switchLabel;
    @FXML private TextField name;
    @FXML private TextField stock;
    @FXML private TextField price;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TextField switchField;

    @Override public void initialize(URL url, ResourceBundle resourceBundle){

    }

    /** Method to go back to the main screen when cancel button is clicked.
     * Cancels the progress made to the new part. */

    @FXML public void cancelBtn(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /** Methods for each radio button, changes specific fields and texts. */
    @FXML public void inHouseClick(){
        switchLabel.setText("Machine ID");
    }
    @FXML public void outsourceClick(){
        switchLabel.setText("Company Name");
    }

    /** Methods that takes text values and returns number values (Int, double). */

    public static boolean isInteger(String s){
        try{
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
    public static boolean isDouble(String s){
        try{
            Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    /** Creating the method to save the part.
     * Verifies correct formats are being inputted to the fields.
     * Verifies all fields are filled out before saving.
     * Verifies inventory is within the Max and Min values, also verifies Min value is not greater than Max value.
     * Saves the part to the appropriate location whether InHouse or Outsourced. */

    public void saveBtn(ActionEvent actionEvent) throws IOException {
        if ((name.getText().isEmpty() || stock.getText().isEmpty() || price.getText().isEmpty() || max.getText().isEmpty() || min.getText().isEmpty() || switchField.getText().isEmpty())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing information");
            alert.setHeaderText("Error");
            alert.setContentText("All fields must be filled");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (!isInteger(stock.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect format");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter a number");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (!isInteger(max.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect format");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter a number");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (!isInteger(min.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect format");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter a number");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if (!isDouble(price.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect format");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter correct price format");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Random random = new Random();
        int randID = random.nextInt(9000) + 1000;

        if (inHouse.isSelected()) {
            if (!isInteger(switchField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect format");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a number");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                return;
            }

            String prtName = name.getText();
            int prtStock = Integer.parseInt(stock.getText());
            double prtPrice = Double.parseDouble(price.getText());
            int prtMax = Integer.parseInt(max.getText());
            int prtMin = Integer.parseInt(min.getText());
            int machID = Integer.parseInt(switchField.getText());

            if (prtMin > prtMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect format");
                alert.setHeaderText("Error");
                alert.setContentText("Min can not be larger than Max");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                return;
            }
            if ((prtMin > prtStock) || (prtMax < prtStock)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory number error");
                alert.setHeaderText("Error");
                alert.setContentText("Inventory cannot be bigger or smaller that the Max and Min values");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                return;
            }

            Inventory.addPart(new Inhouse(randID, prtName, prtStock, prtPrice, prtMax, prtMin, machID));
            Parent parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (outsource.isSelected()) {

            if ((name.getText().isEmpty() || stock.getText().isEmpty() || price.getText().isEmpty() || max.getText().isEmpty() || min.getText().isEmpty() || switchField.getText().isEmpty())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing information");
                alert.setHeaderText("Error");
                alert.setContentText("All fields must be filled");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                return;
            }

            String outPrtName = name.getText();
            int outPrtStock = Integer.parseInt(stock.getText());
            double outPrtPrice = Double.parseDouble(price.getText());
            int otuPrtMax = Integer.parseInt(max.getText());
            int outPrtMin = Integer.parseInt(min.getText());
            String companyName = switchField.getText();

            if (outPrtMin > otuPrtMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect format");
                alert.setHeaderText("Error");
                alert.setContentText("Min can not be larger than Max");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                return;
            }
            if ((outPrtMin > outPrtStock) || (otuPrtMax < otuPrtMax)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory number error");
                alert.setHeaderText("Error");
                alert.setContentText("Inventory cannot be bigger or smaller that the Max and Min values");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                return;
            }

            Inventory.addPart(new Outsourced(randID, outPrtName, outPrtStock, outPrtPrice, otuPrtMax, outPrtMin, companyName));
            Parent parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
    }




}

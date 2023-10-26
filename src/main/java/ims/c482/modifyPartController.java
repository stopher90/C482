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
import java.util.Objects;
import java.util.ResourceBundle;

/**Creating the modifyPartController class, this is where parts will be modified and updated.
 * @author Christopher Dupont*/

public class modifyPartController implements Initializable {

    /** Radio buttons for an InHouse or Outsourced part. */
    @FXML public RadioButton inHouse;
    @FXML public RadioButton outsource;

    /**Toggle group that will assist with changing the text and label fields of machine ID and Company
     * depending on the active radioButton. */
    @FXML
    static ToggleGroup inOut = new ToggleGroup();

    /** TextFields and label for switching between Machine ID and Company name. */
    @FXML private TextField modPrtID;
    @FXML private TextField modPrtName;
    @FXML private TextField modPrtStock;
    @FXML private TextField modPrtPrice;
    @FXML private TextField modPrtMax;
    @FXML private TextField modPrtMin;
    @FXML private TextField modSwitchField;
    @FXML private Label modSwitchLabel;

    /** Part object to be initialized with the selected part, index of part. */
     Part modifyPart;
     int index = 0;

     /**This initializes the Modify Part page. */
    @Override public void initialize(URL url, ResourceBundle resourceBundle){}

    /** Method to load the data into correct fields for easier view. */
    public void loadPartData(int partIndex, Part selectedPart) {
        modifyPart = selectedPart;
        index = partIndex;
//        inHouse.setToggleGroup(inOut);
//        outsource.setToggleGroup(inOut);

        if (selectedPart instanceof  Outsourced)
            outsource.setSelected(true);
        else
            inHouse.setSelected(true);
        int partID = modifyPart.getID();
        String partName = modifyPart.getName();
        int partStock = modifyPart.getStock();
        double partPrice = modifyPart.getPrice();
        int partMax = modifyPart.getMax();
        int partMin = modifyPart.getMin();
        //int machID = modifyPart.getMachID();

        modPrtID.setText(Integer.toString(partID));
        modPrtName.setText(partName);
        modPrtStock.setText(Integer.toString(partStock));
        modPrtPrice.setText(Double.toString(partPrice));
        modPrtMax.setText(Integer.toString(partMax));
        modPrtMin.setText(Integer.toString(partMin));
        //modSwitchField.setText(Integer.toString(machID));

        if (selectedPart instanceof Inhouse inHousePrt) {
            inHouse.setSelected(true);

            inOut.selectToggle(inHouse);
            modSwitchField.setText(Integer.toString(((Inhouse) selectedPart).getMachID()));
            modSwitchField.setPromptText("Machine ID");
            modSwitchLabel.setText("Machine ID");


        }
        if (selectedPart instanceof Outsourced) {
            outsource.setSelected(true);
            modSwitchLabel.setText("Company Name");

            inOut.selectToggle(outsource);
            modSwitchField.setText(((Outsourced)selectedPart).getCompanyName());
            modSwitchField.setPromptText("Company Name");
            modSwitchLabel.setText("Company Name");
        }
    }

        /** Parses the text to check if the input is a number. */

        public static boolean isInteger(String s) {
            boolean result;
            try {
                Integer.parseInt(s);
                result = true;
            } catch (NumberFormatException e) {
                result = false;
            }
            return result;
        }
    /** Parses the text to check if the input is a number. */
        public static boolean isDouble(String s){
            boolean result;
            try{
                Double.parseDouble(s);
                result = true;
            }
            catch (NumberFormatException e){
                result = false;
            }
            return result;
        }

        /** method to save the modified part and updates the inventory.
         * verifies all fields are filled.
         * verifes the correct input.
         * Checks if the inventory number is within the range of the Max and Min values.
         * Checks if the min value is larger than the max. */

        public void modSaveBtn(ActionEvent actionEvent) throws IOException {
            if ((modPrtName.getText().isEmpty() || modPrtStock.getText().isEmpty() || modPrtPrice.getText().isEmpty() || modPrtMax.getText().isEmpty() || modPrtMin.getText().isEmpty() || modSwitchField.getText().isEmpty())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing information");
                alert.setHeaderText("Error");
                alert.setContentText("All fields must be filled");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                return;
            }
            if (!isInteger(modPrtStock.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect format");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a number");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                return;
            }
            if (!isInteger(modPrtMax.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect format");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a number");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                return;
            }
            if(!isInteger(modPrtMin.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect format");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a number");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                return;
            }
            if (!isDouble(modPrtPrice.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect format");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter correct price format");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                return;
            }

            int newPrtID = Integer.parseInt(modPrtID.getText());
            String newPrtName = modPrtName.getText();
            int newPrtStock = Integer.parseInt(modPrtStock.getText());
            double newPrtPrice = Double.parseDouble(modPrtPrice.getText());
            int newPrtMax = Integer.parseInt(modPrtMax.getText());
            int newPrtMin = Integer.parseInt(modPrtMin.getText());

            if (newPrtMin > newPrtMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect format");
                alert.setHeaderText("Error");
                alert.setContentText("Min can not be larger than Max");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                return;
            }
            if ((newPrtMin > newPrtStock) || (newPrtMax < newPrtStock)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory number error");
                alert.setHeaderText("Error");
                alert.setContentText("Inventory cannot be bigger or smaller that the Max and Min values");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                return;
            }



            if (inHouse.isSelected()){

                int newMachID = Integer.parseInt(modSwitchField.getText());

                Inhouse modifyIn = new Inhouse(newPrtID, newPrtName, newPrtStock, newPrtPrice, newPrtMax, newPrtMin, newMachID);
                Inventory.updatePart(index, modifyIn);
                System.out.println(Inventory.getAllParts());

            }
            if (outsource.isSelected()) {

                String companyName = modSwitchField.getText();

                Outsourced modifyOut = new Outsourced(newPrtID, newPrtName, newPrtStock, newPrtPrice, newPrtMax, newPrtMin, companyName);
                Inventory.updatePart(index, modifyOut);
                System.out.println(Inventory.getAllParts());

            }
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();


        }

        /** This method changes the label field to Machine ID when the InHouse RB is selected. */
        public void inHouseClick(){
            //if (this.inOut.getSelectedToggle().equals(inHouse)){
                modSwitchLabel.setText("Machine ID");
            //}
        }
    /** This method changes the label field to Company Name when the Outsourced RB is selected. */
        public void outsourceClick(){
           // if (this.inOut.getSelectedToggle().equals(outsource)){
                modSwitchLabel.setText("Company Name");
            //}
        }
/**This method cancels all changes to the part and returns back to the Main Page. */
        public void modCancelBtn(ActionEvent actionEvent) throws IOException {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }



}

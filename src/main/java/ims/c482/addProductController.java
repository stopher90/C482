package ims.c482;


import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import javafx.collections.ObservableList;

import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**Creating the add product class for functionality. */
public class addProductController implements Initializable {
    private final ObservableList<Part> assocPrtList = FXCollections.observableArrayList();

    /** assigning the table views and columns. */
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn prtListID;
    @FXML private TableColumn prtListName;
    @FXML private TableColumn prtListStock;
    @FXML private TableColumn prtListPrice;

    @FXML private TableView<Part> assocPrtTable;
    @FXML private TableColumn assocPrtID;
    @FXML private TableColumn assocPrtName;
    @FXML private TableColumn assocPrtStock;
    @FXML private TableColumn assocPrtPrice;

    /** Text field assignments. */
    @FXML public TextField prodID;
    @FXML public TextField prodName;
    @FXML public TextField prodStock;
    @FXML public TextField prodPrice;
    @FXML public TextField prodMax;
    @FXML public TextField prodMin;

    @FXML public TextField search;

    /**Variables for products. */
    int randProdID;
    String productName;
    int productStock;
    double productPrice;
    int productMax;
    int productMin;

    /** Method that initializes the parts that will be used to add to the product. */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        prtListID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("ID"));
        prtListName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        prtListStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        prtListPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }
/** Parses the text to verify the correct values are being inputted. */
    public static boolean isInteger(String text){
        try{
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
    /** Parses the text to verify the correct values are being inputted. */
    public static boolean isDouble(String text){
        try{
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /** cancel button method.
     * This takes the user back to the Main form page. */
    @FXML public void cancelBtnClick(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /** Creating the save button to save the product.
     * Verifies All fields are filled out.
     * Verifies Correct input values.*/
    public void sveBtnClick(ActionEvent actionEvent) throws IOException {
        Random random = new Random();
        randProdID = random.nextInt(9000) + 1000;

        if ((prodName.getText().isEmpty() || prodStock.getText().isEmpty() || prodPrice.getText().isEmpty() || prodMax.getText().isEmpty() || prodMin.getText().isEmpty())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing information");
            alert.setHeaderText("Error");
            alert.setContentText("All fields must be filled");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if(!isInteger(prodMax.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Field needs to be a number");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
        }
        if (!isInteger(prodMin.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Field needs to be a number");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
        }
        if (!isInteger(prodStock.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Field needs to be a number");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
        }
        if (!isDouble(prodPrice.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Field needs to be a number");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
        }

        productName = prodName.getText();
        productStock = Integer.parseInt(prodStock.getText());
        productPrice = Double.parseDouble(prodPrice.getText());
        productMax = Integer.parseInt(prodMax.getText());
        productMin = Integer.parseInt(prodMin.getText());

        if (productMin > productMax){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect format");
            alert.setHeaderText("Error");
            alert.setContentText("Min can not be larger than Max");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if((productMax < productStock) || (productMin > productStock)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory number error");
            alert.setHeaderText("Error");
            alert.setContentText("Inventory cannot be bigger or smaller that the Max and Min values");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Product currProd = new Product(randProdID, productName, productStock,productPrice, productMax, productMin);
        for (Part part : assocPrtList){
            currProd.addAssocPart(part);
        }
        Inventory.addProduct(currProd);
        Parent parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /** This method will add a part from the parts table to the associated parts table to create a product. */
    public void addBtnClick(){
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        assocPrtList.add(selectedPart);
        assocPrtTable.setItems(assocPrtList);
        assocPrtID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        assocPrtName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPrtStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPrtPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** This method removes the associated part from a product.
     * This is to prepare for a new part or to prepare for updated or deletion of a product. */
    public void removeAssocPrt(ActionEvent actionEvent){
        Part selectedPart = assocPrtTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Part Selected");
            alert.setHeaderText("Warning");
            alert.setContentText("Select a part to remove");
            alert.getButtonTypes().setAll(ButtonType.OK);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove part");
        alert.setHeaderText("Confirm");
        alert.setContentText("Remove this part?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES){
            assocPrtList.remove(selectedPart);
        }

    }

    /** method for the search field.
     * This returns a part that is being searched for or produces an error. */
    public void searchField(){
        String a = search.getText();
        ObservableList<Part> parts = Inventory.lookupPart(a);

        if(!parts.isEmpty()){
            partsTable.setItems(parts);
        }

        if (parts.isEmpty()){
            try{
                int partID = Integer.parseInt(a);
                Part part = (Part) Inventory.lookupPart(partID);
                if (part != null){
                    parts.add(part);
                    partsTable.setItems(parts);
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Part not found");
                    alert.setHeaderText("Error");
                    alert.setContentText("Part not found");
                    alert.getButtonTypes().setAll(ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Part not found");
                alert.setHeaderText("Error");
                alert.setContentText("Part not found");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
            }
        }
    }



}

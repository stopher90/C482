package ims.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static ims.c482.addProductController.isDouble;
import static ims.c482.addProductController.isInteger;

/**Creating the modify product class.*/
public class modifyProductController implements Initializable {

    private ObservableList<Part> assocPrtList = FXCollections.observableArrayList();

    /** assigning Tableviews, columns, and TextFields. */
    @FXML public TableView<Part> partsTable;
    @FXML public TableColumn<Part, Integer> partID;
    @FXML public TableColumn<Part, String > partName;
    @FXML public TableColumn<Part, Integer> partStock;
    @FXML public TableColumn<Part, Double> partPrice;
    @FXML public TableView<Part> assocPrtTable;
    @FXML public TableColumn<Part, Integer> assocPrtID;
    @FXML public TableColumn<Part, String > assocPrtName;
    @FXML public TableColumn<Part, Integer> assocPrtStock;
    @FXML public TableColumn<Part, Double> assocPrtPrice;
    @FXML public TextField search;
    @FXML public TextField prodID;
    @FXML public TextField prodName;
    @FXML public TextField prodStock;
    @FXML public TextField prodPrice;
    @FXML public TextField prodMax;
    @FXML public TextField prodMin;

    /** Variable for the index. */
     int prodIndex;

     Product modifyProd;
    public int productID;

    /** Method to populate tables. */

    public void loadTables(int index, Product selectedProduct){
        prodIndex = index;
        modifyProd = selectedProduct;

        assocPrtList.addAll(modifyProd.getAllAssocParts());

        productID = modifyProd.getID();
        String productName = modifyProd.getName();
        int productStock = modifyProd.getStock();
        double productPrice = modifyProd.getPrice();
        int productMax = modifyProd.getMax();
        int productMin = modifyProd.getMin();

        prodID.setText(Integer.toString(productID));
        prodName.setText(productName);
        prodStock.setText(Integer.toString(productStock));
        prodPrice.setText(Double.toString(productPrice));
        prodMax.setText(Integer.toString(productMax));
        prodMin.setText(Integer.toString(productMin));


        assocPrtTable.setItems(assocPrtList);
        assocPrtID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        assocPrtName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPrtStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPrtPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

/** this will be used to load the data into Parts table. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /** cancel button and return to main page. */
    public void cancelBtn(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /** Method for adding a part to the product. */
    public void addBtnClick(){
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        assocPrtList.add(selectedPart);

        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.setContentText("Select a part.");

            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }

        assocPrtTable.setItems(assocPrtList);
        assocPrtID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        assocPrtName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPrtStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPrtPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** Method for removal. */
    public void removeAssoc(){
        Part selectedPart = assocPrtTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm");
        alert.setContentText("Do you want to remove this part?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES){
            assocPrtList.remove(selectedPart);
            assocPrtTable.setItems(assocPrtList);
        }
    }

    /** This method saves an updated product.
     * Verifies all fields are filled.
     * Verifies inputs are in correct format.
     * Checks if the inventory is within the range of max and min values or returns an error.
     * Checks if the min value is larger than the max value, if so it returns an error.
     * Updates the product and returns to the main screen. */
    public void saveBtnClick(ActionEvent actionEvent) throws IOException {
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
        String newProdName = prodName.getText();
        int newProdStock = Integer.parseInt(prodStock.getText());
        double newProdPrice = Double.parseDouble(prodPrice.getText());
        int newProdMax = Integer.parseInt(prodMax.getText());
        int newProdMin = Integer.parseInt(prodMin.getText());
        if (newProdMin > newProdMax){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect format");
            alert.setHeaderText("Error");
            alert.setContentText("Min can not be larger than Max");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if((newProdMax < newProdStock) || (newProdMin > newProdStock)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory number error");
            alert.setHeaderText("Error");
            alert.setContentText("Inventory cannot be bigger or smaller that the Max and Min values");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Product modifiedProduct = new Product(productID, newProdName, newProdStock, newProdPrice, newProdMax, newProdMin);

        for (Part part : assocPrtList){
            modifiedProduct.addAssocPart(part);
        }
        Inventory.updateProduct(prodIndex, modifiedProduct);

        Parent parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
/** RUNTIME ERROR:
 * This gave me a runtime error due to not updating the name of partsearchTable to partsTable.
 * searches the parts table and returns part or produces an error. */
    public void searchField(){
        String a = search.getText();
        ObservableList<Part> parts = Inventory.lookupPart(a);

        if(!parts.isEmpty()){
            partsTable.setItems(parts);
        }

        if (parts.isEmpty()){
            try{
                int partID = Integer.parseInt(a);
                Part part = Inventory.lookupPart(partID);
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
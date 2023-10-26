package ims.c482;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

/**Main controller Class, this will be where the parts and products are displayed.
 * This will guide the user to the corresponding pages and this is where the main deletion of items will be. */

public class MainController implements Initializable {

    /**Calling to the FXML to assign FXID's for the tableviews of parts and products. */
    @FXML public TableView<Part> partTable;
    @FXML public TableColumn<Part, Integer> partID;
    @FXML public TableColumn<Part, String> partName;
    @FXML public TableColumn<Part, Integer> partStock;
    @FXML public TableColumn<Part, Double> partPrice;

    @FXML public TableView<Product> productTable;
    @FXML public TableColumn<Product, Integer> productID;
    @FXML public TableColumn<Product, String> productName;
    @FXML public TableColumn<Product, Integer> productStock;
    @FXML public TableColumn<Product, Double> productPrice;

    /**Assigning Search Fields to each table. */
    @FXML public TextField partSearch;
    @FXML public TextField productSearch;

    /**Exit Button. */
    @FXML public Button exitBtn;
    @FXML public Button prtModBtn;

    /**Setting a boolean to check if this is the first time the Main page was looked at. */
    private static boolean fTime = true;

/**This method is creating the tables for the data to be viewed. */
    private void createTables(){
        if (!fTime){
            return;
        }
        fTime = false;
        //adding in test data values for part and product
        Part prt1 = Inventory.addPart(new Inhouse(101, "Tire", 100, 15.00, 150,20, 6926));
        Inventory.addPart(new Inhouse(201, "Chain", 60, 10.99, 70, 7, 7845));
        Part prt2 = Inventory.addPart(new Outsourced(301, "Brake Pads", 45, 5.99, 200, 15, "Bob's Brakes"));

        Product prod1 = Inventory.addProduct(new Product(1001, "Mountain bike", 5, 300.99, 15, 5));
        prod1.addAssocPart(prt1);
        prod1.addAssocPart(prt2);
        Product prod2 = Inventory.addProduct(new Product(2001, "Custom bike", 5, 500.99, 10, 1));
        prod2.addAssocPart(prt1);
        prod2.addAssocPart(prt2);
        Inventory.addProduct(new Product(3001, "Bike Wagon", 14, 49.99, 15, 1));
    }



/**This initializes the data and populates the data to the tables. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTables();

        partTable.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        partStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));

        productTable.setItems(Inventory.getAllProducts());
        productID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        productStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));


    }



    /**here will take the user to the add parts screen to input new part data. */

    @FXML public void addPrtScrn(ActionEvent actionEvent) throws IOException {
        Parent addPart = FXMLLoader.load(getClass().getResource("addPart.fxml"));
        Scene scene = new Scene(addPart);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
/**This will take the user to the modify screen to modify an existing part.
 * This also verifies there is a part selected before proceeding. */
    @FXML public void partModify(ActionEvent actionEvent) throws IOException{
        Part selectedModPart = partTable.getSelectionModel().getSelectedItem();
        int index = partTable.getSelectionModel().getSelectedIndex();

        if (selectedModPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nothing Selected");
            alert.setHeaderText("Warning");
            alert.setContentText("Please select a part to modify");
            alert.getButtonTypes().setAll(ButtonType.OK);

            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifyPart.fxml"));
        Parent modPrt = loader.load();
        modifyPartController modPartController  = loader.getController();


        modPartController.loadPartData(index, selectedModPart);
        Scene scene = new Scene(modPrt);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();


    }
/**This will delete the selected part after the user confirms that it is the correct part to be deleted. */
    @FXML public void delPrt(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm part delete");
        alert.setHeaderText("Confirm");
        alert.setContentText("Do you want to delete this part?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (Inventory.deletePart(partTable.getSelectionModel().getSelectedItem())){
        }
    }

    /** part search button, used to search through the list of parts return part or produce an error. */
    @FXML public  void prtSearch(){
        String a = partSearch.getText();
        ObservableList<Part> parts = Inventory.lookupPart(a);

        if(!parts.isEmpty()){
            partTable.setItems(parts);
        }
        if (parts.isEmpty()){
            try{
                int partID = Integer.parseInt(a);
                Part part = Inventory.lookupPart(partID);
                if (part != null){
                    parts.add(part);
                    partTable.setItems(parts);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Part not found");
                    alert.setHeaderText("Error");
                    alert.setContentText("Part not found");
                    alert.getButtonTypes().setAll(ButtonType.OK);
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Part not found");
                alert.setHeaderText("Error");
                alert.setContentText("Part not found");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
/**This method takes the user to the add product screen. */
    @FXML public void prodAdd(ActionEvent actionEvent) throws IOException {
        Parent addprod = FXMLLoader.load(getClass().getResource("addProduct.fxml"));
        Scene scene = new Scene(addprod);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
/**This method takes the user to the modify product screen.
 * Verifies there is a product selected before proceeding to the next screen. */
    @FXML public void prodMod(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        int prodIndex = productTable.getSelectionModel().getSelectedIndex();

        if (selectedProduct == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No product selected");
            alert.setHeaderText("Warning");
            alert.setContentText("Please select a product");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyProduct.fxml"));
        Parent modifyProduct = loader.load();
        modifyProductController modProdController = loader.getController();
        modProdController.loadTables(prodIndex,selectedProduct);
        Scene scene = new Scene(modifyProduct);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }
/**This method will delete a selected product AFTER all associated parts have been removed.
 * If no associated parts have been removed from the product first, it will produce an error. */
    @FXML public void delProd(ActionEvent actionEvent){
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct.getAllAssocParts().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm");
            alert.setContentText("Do you want to delete this item?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES){
                Inventory.deleteProduct(selectedProduct);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Could not delete product");
            alert.setHeaderText("Error");
            alert.setContentText("Remove associated parts first");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
        }
    }

    /** product search , used to search through the list of products and returns the product or produce an error. */
    @FXML public  void prodSearch(ActionEvent actionEvent){
        String a = productSearch.getText();
        ObservableList<Product> products = Inventory.lookupProduct(a);

        if(!products.isEmpty()){
            productTable.setItems(products);
        }
        if (products.isEmpty()){
            try{
                int productID = Integer.parseInt(a);
                Product product = (Product) Inventory.lookupProduct(productID);
                if (product != null){
                    products.add(product);
                    productTable.setItems(products);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Product not found");
                    alert.setHeaderText("Error");
                    alert.setContentText("Product not found");
                    alert.getButtonTypes().setAll(ButtonType.OK);
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Product not found");
                alert.setHeaderText("Error");
                alert.setContentText("Product not found");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
/**This handles the Exit button and closes the application*/
    @FXML public void exit(){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
}
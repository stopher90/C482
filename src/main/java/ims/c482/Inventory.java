package ims.c482;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/** This class will handle most of the interactions of the application.
 * Contains Observable lists that will be used to store Parts or Products in the tableviews. */

public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Methods to add parts or products to the respective Observable lists.
     */

    public static Part addPart(Part newPart){
        allParts.add(newPart);
        return newPart;
    }

    public static Product addProduct(Product newProduct){
        allProducts.add(newProduct);
        return newProduct;
    }

    /** Method to find a part or product by ID or produces a null. */
    public static Part lookupPart(int partID){
        for (int b = 0; b < allParts.size();  b++){
            Part i = allParts.get(b);
            if (i.getID() == partID){
                return i;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productID){
        for (int b = 0; b < allProducts.size(); b++){
            Product i = allProducts.get(b);
            if (i.getID() == productID){
                return i;
            }
        }
        return null;
    }

    /** Methods to find a part or product by name or produces a null. */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part>searchedPart = FXCollections.observableArrayList();

        for(Part searchPrt : allParts){
            if(searchPrt.getName().toLowerCase().contains(partName.toLowerCase())){
                searchedPart.add(searchPrt);
            }
        }
        return searchedPart;
    }

    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> searchedProduct = FXCollections.observableArrayList();

        for (Product searchProd : allProducts){
            if(searchProd.getName().toLowerCase().contains(productName.toLowerCase())){
                searchedProduct.add(searchProd);
            }
        }
        return searchedProduct;
    }

    /** Methods for updating parts and products. */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    public static void updateProduct(int index, Product selectedProd){
        allProducts.set(index, selectedProd);
    }

    /** Methods to delete parts and products. */
    public static boolean deletePart(Part selectedPart){
        if(allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }else{
            return false;
        }
    }
    public static boolean deleteProduct(Product selectedProd){
        if (allProducts.contains(selectedProd)){
            allProducts.remove(selectedProd);
            return true;
        }else{
            return false;
        }
    }
/**@return allParts*/
    public static ObservableList<Part> getAllParts(){return allParts;}

    /**@return allProducts*/
    public static ObservableList<Product> getAllProducts(){return allProducts;}


}

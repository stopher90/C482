package ims.c482;


import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**Creating the product class with a constructor, setters and getters. */
public class Product {

    /**Observable list for the associated Parts. */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private int stock;
    private double price;
    private int max;
    private int min;

    /** Constructor for the Product with the required parameters to create the product. */
    public Product(int id, String name, int stock, double price, int max, int min){
        setID(id);
        setName(name);
        setStock(stock);
        setPrice(price);
        setMax(max);
        setMin(min);
    }

    /** Setters and Getters.
     * These set the necessary information, then retrieves that information when called.  */
    public void setID(int id){
        this.id = id;
    }
    public int getID(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setStock(int stock){
        this.stock = stock;
    }
    public int getStock(){
        return stock;
    }

    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return price;
    }

    public void setMax(int max){
        this.max = max;
    }
    public int getMax(){
        return max;
    }

    public void setMin(int min){
        this.min = min;
    }
    public int getMin(){
        return min;
    }

    /** Method that takes a part and adds to Associated Parts to create the product. */
    public  void addAssocPart(Part part){
        assocParts.add(part);
    }

    /** Method that removes the part from Associated Parts. */
    public boolean removeAssoc(Part selectedAssocPart){
        if (assocParts.contains(selectedAssocPart)){
            assocParts.remove(selectedAssocPart);
            return true;
        }
        return false;
    }

    /**Returns the associate parts when selecting an existing product.*/
    public ObservableList<Part>getAllAssocParts(){
        return assocParts;
    }

}

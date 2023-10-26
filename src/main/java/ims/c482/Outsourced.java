package ims.c482;

/**Class is going to contain the constructor, setters, and getters for Outsourced parts.
 * Setter and getter retrives and returns the needed information. */

public class Outsourced extends Part{

    /**used to hold the Outsourced company's name for the part. */
    private String companyName;

    /** constructor for outsourced parts with the required parameters. */
    public Outsourced(int id, String name, int stock, double price, int max, int min, String companyName){
        super();

        setID(id);
        setName(name);
        setStock(stock);
        setPrice(price);
        setMax(max);
        setMin(min);
        setCompanyName(companyName);
    }
/**Sets the Company's Name. */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**Returns the Company's Name. */
    public String getCompanyName(){
        return companyName;
    }

}

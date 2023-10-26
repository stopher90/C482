package ims.c482;

/**Class is going to contain the constructor, setters, and getters for the in-house parts.*/
public class Inhouse extends Part{
    private int machID;

    /** Inhouse constructor with the required parameters. */
    public Inhouse(int id, String name, int stock, double price, int max, int min, int machID){
        super();
        setID(id);
        setName(name);
        setStock(stock);
        setPrice(price);
        setMax(max);
        setMin(min);
        setMachID(machID);
    }

    /** Setter that sets the machine ID.
     * Getter that returns the machine ID. */
    public void setMachID(int machID){
        this.machID = machID;
    }
    public int getMachID(){return machID;}
}

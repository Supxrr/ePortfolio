//super class for stock and mutualfund
//Evan Bucholski
//ebuchols, 1226299
//Due November 8th, 2024
package ePortfolio;

public abstract class Investment {

    protected String symbol;
    protected String name;
    protected double price;
    protected int quantity;
    protected double bookValue;
    

    /**
     * Constructor for the Investment class.
     * 
     * @param symbol The investment symbol.
     * @param name The investment name.
     * @param price The investment price per unit.
     * @param quantity The number of investment units.
     */
    public Investment(String symbol, String name, double price, int quantity) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.bookValue = price * quantity;
    }

    //calculate the initial book value, can be overrided if needed.

    /**
     * Calculate the initial book value of the investment.
     * @return The initial book value of the investment.
     */
    protected double calculateInitialBookValue() {
        return price * quantity;
    }

    //getters
    /**
     * Get the symbol of the investment.
     * @return The symbol of the investment.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Get the name of the investment.
     * @return The name of the investment.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the price of the investment.
     * @return The price of the investment.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Get the quantity of the investment.
     * @return The quantity of the investment.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Get the book value of the investment.
     * @return The book value of the investment.
     */
    public double getBookValue() {
        return bookValue;
    }

    /**
    * Set the quantity of the investment.
    */
    public void setPrice(double price) {
        this.price = price;
    }

    
    //abstract methods, implemented in subclasses to override.

    public abstract double calculateGain();
    public abstract double sell(int quantityToSell, double sellPrice);
    public abstract void buy(int addtionalQuantity, double newPrice);


    /**
     * method toString to return the investment details.
     * @return The investment details.
     */
    public String toString() {
        return "Symbol: " + symbol + "\nName: " + name + "\nPrice: " + price + "\nQuantity: " + quantity + "\nBook Value: " + bookValue;
    }




    
}

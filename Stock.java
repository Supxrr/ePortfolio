//Evan Bucholski
//ebuchols, 1226299
//Due November 8th, 2024
package ePortfolio;

public class Stock extends Investment {
    public static final double COMMISSION = 9.99;

    /**
     * Constructor for the Stock class.
     * 
     * @param symbol The stock symbol.
     * @param name The stock name.
     * @param price The stock price per unit.
     * @param quantity The number of stock units.
     */
    public Stock(String symbol, String name, double price, int quantity, double bookValue) {
        super(symbol, name, price, quantity);
        this.bookValue = bookValue;
    }

    /**
     * Calculate the gain of the stock.
     * 
     * @return The gain of the stock.
     */

     //again, these methods will override the superclass methods.
    @Override
    public double calculateGain() {
        return (price * quantity - COMMISSION) - bookValue;
    }

    /**
     * Buy more stock units.
     * 
     * @param additionalQuantity The number of additional stock units to buy.
     * @param newPrice The price of the new stock units.
     */
    @Override
    public void buy(int additionalQuantity, double newPrice) {
        double additionalBookValue = (additionalQuantity * newPrice) + COMMISSION;
        bookValue += additionalBookValue;
        quantity += additionalQuantity;
        price = newPrice;
    }

    /**
     * Sell stock units.
     * @param quantityToSell The number of stock units to sell.
     * @param sellPrice The price to sell the stock units at.
     * @return The gain from selling the stock units.
     */
    @Override
    public double sell(int quantityToSell, double sellPrice) {
        if(quantityToSell > quantity) {
            System.out.println("Cannot sell more than owned quantity");
            return -1.0;
        }

        double payment = (sellPrice * quantityToSell) - COMMISSION;

        double bookValueSold = bookValue * ((double) quantityToSell / quantity);

        quantity -= quantityToSell;
        bookValue -= bookValueSold;

        return payment - bookValueSold;
        
    }
}
//Evan Bucholski
//ebuchols, 1226299
//Due November 8th, 2024
package ePortfolio;

public class MutualFund extends Investment {
    private static final double REDEMPTION = 45.00;

    /**
     * Constructor for the MutualFund class.
     * 
     * @param symbol The mutual fund symbol.
     * @param name The mutual fund name.
     * @param price The mutual fund price per unit.
     * @param quantity The number of mutual fund units.
     */
    public MutualFund(String symbol, String name, double price, int quantity, double bookValue) {
        super(symbol, name, price, quantity);
        this.bookValue = bookValue;
    }

    /**
     * Calculate the gain of the mutual fund.
     * 
     * @return The gain of the mutual fund.
     */
    //the ovveride is used to override methods of the superclass.
    @Override
    public double calculateGain() {
        return (price * quantity) - bookValue;
    }

    /**
     * Buy more mutual fund units.
     * 
     * @param additionalQuantity The number of additional mutual fund units to buy.
     * @param newPrice The price of the new mutual fund units.
     */
    @Override
    public void buy(int additionalQuantity, double newPrice) {
        double additionalBookValue = additionalQuantity * newPrice;
        bookValue += additionalBookValue;
        quantity += additionalQuantity;
        price = newPrice;
    }

    /**
     * Sell mutual fund units.
     * 
     * @param quantityToSell The number of mutual fund units to sell.
     * @param sellPrice The price to sell the mutual fund units at.
     * @return The gain from selling the mutual fund units.
     */
    @Override
    public double sell(int quantityToSell, double sellPrice) {
        if(quantityToSell > quantity) {
            System.out.println("Cannot sell more than owned quantity");
            return -1.0;
        }

        double payment = (sellPrice * quantityToSell) - REDEMPTION;

        double bookValueSold = bookValue * ((double) quantityToSell / quantity);

        quantity -= quantityToSell;
        bookValue -= bookValueSold;
        

        return payment - bookValueSold;

        
    }
}
//Evan Bucholski
//ebuchols, 1226299
//Due November 8th, 2024
package ePortfolio;
import java.io.*;
import java.util.*;

public class Portfolio {

    //only one arraylist is needed to store all investments now instead of 2
    /**
     * Arraylist for Investments.
     */
    private ArrayList<Investment> investments;

    //a hashmap is used to store the keywords and their corresponding indices
    /**
     * A hashmap to store the keywords and their corresponding indices.
     */
    private HashMap<String, ArrayList<Integer>> keywordIndex;

    //constructor
    public Portfolio() {
        investments = new ArrayList<>();
        keywordIndex = new HashMap<>();
    }

    /**
     * Load investments from a file.
     * @param filename
     */
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            Investment investment = null;
            String type = "", symbol = "", name = "";
            int quantity = 0;
            double price = 0.0;
            double bookValue = 0.0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("type")) {
                    type = line.split("=")[1].trim().replaceAll("\"", "");
                } else if (line.startsWith("symbol")) {
                    symbol = line.split("=")[1].trim().replaceAll("\"", "");
                } else if (line.startsWith("name")) {
                    name = line.split("=")[1].trim().replaceAll("\"", "");
                } else if (line.startsWith("quantity")) {
                    quantity = Integer.parseInt(line.split("=")[1].trim());
                } else if (line.startsWith("price")) {
                    price = Double.parseDouble(line.split("=")[1].trim());
                } else if (line.startsWith("bookValue")) {
                    bookValue = Double.parseDouble(line.split("=")[1].trim());
                }

                if (!type.isEmpty() && !symbol.isEmpty() && !name.isEmpty() && quantity > 0 && price > 0) {
                    if (type.equalsIgnoreCase("stock")) {
                        investment = new Stock(symbol, name, price, quantity, bookValue);
                    } else if (type.equalsIgnoreCase("mutualfund")) {
                        investment = new MutualFund(symbol, name, price, quantity, bookValue);
                    }

                    if (investment != null) {
                        investments.add(investment);
                        updateKeyWordIndex(investments.size() - 1);
                    }
                    type = symbol = name = "";
                    quantity = 0;
                    price = bookValue = 0.0;
                }
            }
            System.out.println("Investments loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
        }
    }

    /**
     * Save investments to a file.
     * @param filename
     */
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Investment investment : investments) {
                writer.println("type = \"" + investment.getClass().getSimpleName().toLowerCase() + "\"");
                writer.println("symbol = \"" + investment.getSymbol() + "\"");
                writer.println("name = \"" + investment.getName() + "\"");
                writer.println("quantity = " + investment.getQuantity());
                writer.println("price = " + investment.getPrice());
                writer.println("bookValue = " + investment.getBookValue());
                writer.println();
            }
            System.out.println("Investments saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing file: " + filename);
        }
    }

    /**
     * Buy an investment.
     * @param investmentType stock or mutualfund
     * @param symbol the symbol of the investment
     * @param name the name of the investment
     * @param quantity the quantity of the investment
     * @param price the price of the investment
     */
    public void buy(String investmentType, String symbol, String name, int quantity, double price) {
        //made this a lot simpler than before, now instead of asking for additional quantity and price, it instead automatically
        //adds it knowing the symbol matches one prior.
        //also uses the ternary operator to determine if the investment is a stock or mutual fund, much simpler than before.
        Investment existingInvestment = null;

        for(Investment investment : investments) {
            if(investment.getSymbol().equalsIgnoreCase(symbol)) {
                existingInvestment = investment;
                break;
            }
        }

        if(existingInvestment != null) {

            if(existingInvestment instanceof Stock) {
                double additionalBookValue = (quantity * price) + Stock.COMMISSION;
                existingInvestment.bookValue += additionalBookValue;
                existingInvestment.quantity += quantity;
                existingInvestment.price = price;
            } else if(existingInvestment instanceof MutualFund) {
                double additionalBookValue = quantity * price;
                existingInvestment.bookValue += additionalBookValue;
                existingInvestment.quantity += quantity;
                existingInvestment.price = price;
            }
            System.out.println("Investment already exists. Added " + quantity + " units to " + symbol);
        } else {
            Investment newInvestment = investmentType.equalsIgnoreCase("stock")
                    ? new Stock(symbol, name, price, quantity, price * quantity + Stock.COMMISSION)
                    : new MutualFund(symbol, name, price, quantity, price * quantity);
            investments.add(newInvestment);
            updateKeyWordIndex(investments.size() - 1);
            System.out.println("Added new " + investmentType + ": " + newInvestment);
        }
    }


    /**
     * Sell an investment.
     * @param symbol the symbol of the investment
     * @param quantity the quantity of the investment
     * @param price the price of the investment
     */
    public void sell(String investmentType, String symbol, int quantity, double price) {
        //sell was also made a lot simpler, can now loop through all investments and check if the symbol matches the one given
        //if it does, it will check if the investment type matches the one given, if it does, it will sell the investment.
        boolean found = false;


        for(Investment investment : investments) {
            if(investment.getSymbol().equalsIgnoreCase(symbol)) {
                if((investmentType.equalsIgnoreCase("stock") && investment instanceof Stock) ||
                    (investmentType.equalsIgnoreCase("mutualfund") && investment instanceof MutualFund)) {
                    

                    if(investment.getQuantity() < quantity) {
                        System.out.println("Cannot sell more than owned quantity");
                        return;
                    }

                    double gain = investment.sell(quantity, price);
                    System.out.println("Sold " + quantity + " units of " + symbol + " for " + price + " each. Gain: " + gain);

                    if(investment.getQuantity() == 0) {
                        investments.remove(investment);
                        rebuildKeywordIndex();
                        System.out.println("Investment " + symbol + " removed from portfolio.");
                    }

                    found = true;
                    break;
                } else {
                    System.out.println("Investment type does not match.");
                    return;
                }
            }
        }

        if(!found) {
            System.out.println("Investment not found.");
        }
    }

    /**
     * Update the price of investments.
     */
    public void update() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to update a stock or a mutualfund? ");
        String investmentType = scanner.nextLine().trim().toLowerCase();

        boolean found = false;

        if(investmentType.equalsIgnoreCase("stock")) {
            for(Investment investment : investments) {
                if (investment instanceof Stock) {
                    found = true;
                    System.out.print("Enter new price for " + investment.getSymbol() + ": ");
                    double newPrice = scanner.nextDouble();
                    investment.setPrice(newPrice);
                    System.out.println("Price updated for " + investment.getSymbol());
                }
            }
            if(!found) {
                System.out.println("No stocks found to update.");
            }
        } else if(investmentType.equalsIgnoreCase("mutualfund")) {
            for(Investment investment : investments) {
                if (investment instanceof MutualFund) {
                    found = true;
                    System.out.print("Enter new price for " + investment.getSymbol() + ": ");
                    double newPrice = scanner.nextDouble();
                    investment.setPrice(newPrice);
                    System.out.println("Price updated for " + investment.getSymbol());
                }
            }
            if(!found) {
                System.out.println("No mutual funds found to update.");
            }
        } else {
            System.out.println("Invalid investment type.");
        }
    }

    /**
     * Get the total gain of the portfolio.
     */
    public double getGain() {
        double totalGain = 0;
        for (Investment investment : investments) {
            totalGain += investment.calculateGain();
        }
        return totalGain;
    }

    /**
     * Search for investments.
     * @param symbol the symbol of the investment
     * @param nameKeywords the keywords in the name of the investment
     * @param priceRange the price range of the investment
     */
    public List<Investment> search(String symbol, String nameKeywords, String priceRange) {
        //search uses hashmaps to store the keywords and their corresponding indices, then it will loop through the investments
        List<Investment> results = new ArrayList<>();
        List<Integer> matchedIndices = new ArrayList<>();
        if (!nameKeywords.isEmpty()) {
            String[] keywords = nameKeywords.toLowerCase().split(" ");
            for (String keyword : keywords) {
                List<Integer> keywordMatches = keywordIndex.getOrDefault(keyword, new ArrayList<>());
                if (matchedIndices.isEmpty()) {
                    matchedIndices.addAll(keywordMatches);
                } else {
                    matchedIndices.retainAll(keywordMatches);
                }
                if (matchedIndices.isEmpty()) {
                    return results;
                }
            }
        } else {
            for (int i = 0; i < investments.size(); i++) {
                matchedIndices.add(i);
            }
        }

        for (int index : matchedIndices) {
            Investment investment = investments.get(index);
            if (!symbol.isEmpty() && !investment.getSymbol().equalsIgnoreCase(symbol)) {
                continue;
            }
            if (!priceRange.isEmpty() && !isPriceInRange(investment.getPrice(), priceRange)) {
                continue;
            }
            results.add(investment);
        }
        return results;
    }

    /**
     * Check if the price of an investment is within a given range.
     * @param price the price of the investment
     * @param priceRange the price range
     * @return  true if the price is within the range, false otherwise
     */
    private boolean isPriceInRange(double price, String priceRange) {
        //this remained the exact same.
        String[] rangeParts = priceRange.split("-");
        double minPrice = Double.MIN_VALUE;
        double maxPrice = Double.MAX_VALUE;
        try {
            if (rangeParts.length == 1) {
                minPrice = Double.parseDouble(rangeParts[0]);
            } else if (rangeParts.length == 2) {
                minPrice = Double.parseDouble(rangeParts[0]);
                maxPrice = Double.parseDouble(rangeParts[1]);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price range format. Skipping price range filter.");
            return true;
        }
        return price >= minPrice && price <= maxPrice;
    }


    /**
     * Update the keyword index for an investment.
     * @param index the index of the investment
     */
    private void updateKeyWordIndex(int index) {

        //will update the keyword index for the given index
        Investment investment = investments.get(index);
        String[] nameKeywords = investment.getName().toLowerCase().split(" ");
        for (String keyword : nameKeywords) {
            keywordIndex.computeIfAbsent(keyword, k -> new ArrayList<>()).add(index);
        }
    }

    /**
     * Rebuild the keyword index for all investments.
     */
    private void rebuildKeywordIndex() {
        //will rebuild the keyword index for all investments
        keywordIndex.clear();
        for (int i = 0; i < investments.size(); i++) {
            updateKeyWordIndex(i);
        }
    }

    /**
     * Get the list of investments as a copy.
     * @return the list of investments
     */
    public ArrayList<Investment> getInvestments() {
        return investments;
    }

    /**
     * Main method to run the portfolio program.
     */
    public static void main(String[] args) {
        //main remained pretty much the same as before, just a few minor changes to the input prompts.
        //also added a check to see if a file name was provided as an argument, if it was, it will load the file.
        Portfolio portfolio = new Portfolio();
        Scanner scanner = new Scanner(System.in);
        String choice;

        // check if a file name is provided as an argument
        if (args.length > 0) {
            portfolio.loadFromFile(args[0]);
        } else {
            System.out.println("No file name provided. Starting with an empty portfolio.");
        }

        while (true) {
            System.out.println("\nEnter command (buy, sell, update, getGain, search, quit):");
            choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "b":
                case "buy":
                    System.out.print("Enter investment type (stock/mutualfund): ");
                    String investmentType = scanner.nextLine().trim();

                    if (!investmentType.equalsIgnoreCase("stock") && !investmentType.equalsIgnoreCase("mutualfund")) {
                        System.out.println("Invalid investment type.");
                        break;
                    }

                    System.out.print("Enter symbol: ");
                    String symbol = scanner.nextLine().trim();

                    System.out.print("Enter name: ");
                    String name = scanner.nextLine().trim();

                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    if (quantity <= 0) {
                        System.out.println("Invalid quantity.");
                        break;
                    }

                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    if (price <= 0) {
                        System.out.println("Invalid price.");
                        break;
                    }

                    portfolio.buy(investmentType, symbol, name, quantity, price);
                    scanner.nextLine();
                    break;

                case "sell":
                    System.out.print("Enter investment type (stock/mutualfund): ");
                    investmentType = scanner.nextLine().trim();

                    if (!investmentType.equalsIgnoreCase("stock") && !investmentType.equalsIgnoreCase("mutualfund")) {
                        System.out.println("Invalid investment type.");
                        break;
                    }

                    System.out.print("Enter symbol: ");
                    symbol = scanner.nextLine().trim();

                    System.out.print("Enter quantity: ");
                    quantity = scanner.nextInt();
                    if(quantity <= 0) {
                        System.out.println("Invalid quantity.");
                        break;
                    }

                    System.out.print("Enter price: ");
                    price = scanner.nextDouble();
                    if(price <= 0) {
                        System.out.println("Invalid price.");
                        break;
                    }

                    portfolio.sell(investmentType, symbol, quantity, price);
                    scanner.nextLine();    
                    break;  
                case "u":
                case "update":
                    portfolio.update();
                    break;
                case "g":
                case "getgain":
                    portfolio.getGain();
                    break;

                case "search":
                    //swapped search inputs to be done here instead of in the search method
                    System.out.print("Enter symbol (or press enter to skip): ");
                    symbol = scanner.nextLine().trim();
                    System.out.print("Enter name keywords (or press enter to skip): ");
                    String nameKeywords = scanner.nextLine().trim();
                    System.out.print("Enter price range (or press enter to skip): ");
                    String priceRange = scanner.nextLine().trim();
                    portfolio.search(symbol, nameKeywords, priceRange);
                    break;
                case "q":
                case "quit":
                    System.out.println("Program ended.");
                    portfolio.saveToFile("portfolio.txt");
                    return;

                default:
                    System.out.println("Invalid choice.");
                    continue;
            }
        }
    }
}

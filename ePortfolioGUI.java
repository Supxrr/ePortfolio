package ePortfolio;

import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.Port;
import javax.swing.*;

import ePortfolio.Portfolio;

import java.util.List;


public class ePortfolioGUI extends JFrame {

    //instance variables for the GUI
    private Portfolio ePortfolio;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private int currentIndex = 0;

    /*
     * Constructor for the GUI
     * Creates the main frame and sets the title and close operation
     * Creates a card layout for switching between panels
     * Creates the main panel and adds the intro panel to it
     * Creates the menu bar and adds it to the frame
     */
    public ePortfolioGUI() { 

        ePortfolio = new Portfolio();
        //set title and close operation
        setTitle("ePortfolio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);


        //create card layout for switching between panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        //create panels
        JPanel introPanel = createIntroPanel();
        JPanel buyPanel = createBuyPanel();
        JPanel sellPanel = createSellPanel();
        JPanel updatePanel = createUpdatePanel();
        JPanel getGainPanel = createGetGainPanel();
        JPanel searchPanel = createSearchPanel();
        

        //add panels to main panel
        mainPanel.add(introPanel, "Intro");
        mainPanel.add(buyPanel, "Buy");
        mainPanel.add(sellPanel, "Sell");
        mainPanel.add(updatePanel, "Update");
        mainPanel.add(getGainPanel, "Get Gain");
        mainPanel.add(searchPanel, "Search");
        

        //menu bar
        setJMenuBar(createMenuBar());

        //add the panels to the main panel frame
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    /*
     * Creates the menu bar for the GUI
     * Creates a menu bar and a menu
     * Creates menu items for buy, sell, update, get gain, search, and quit
     * Adds action listeners to the menu items to switch between panels
     * Returns the menu bar
     */
    private JMenuBar createMenuBar(){

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");

        //create menu items
        JMenuItem buyItem = new JMenuItem("Buy");
        buyItem.addActionListener(e -> cardLayout.show(mainPanel, "Buy"));

        JMenuItem sellItem = new JMenuItem("Sell");
        sellItem.addActionListener(e -> cardLayout.show(mainPanel, "Sell"));

        JMenuItem updateItem = new JMenuItem("Update");
        updateItem.addActionListener(e -> cardLayout.show(mainPanel, "Update"));

        JMenuItem getGainItem = new JMenuItem("Get Gain");
        getGainItem.addActionListener(e -> cardLayout.show(mainPanel, "Get Gain"));

        JMenuItem searchItem = new JMenuItem("Search");
        searchItem.addActionListener(e -> cardLayout.show(mainPanel, "Search"));


        //save to file on quit
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(e -> {
            ePortfolio.saveToFile("portfolio.txt");
            System.exit(0);
        });


        //add menu items to menu
        menu.add(buyItem);
        menu.add(sellItem);
        menu.add(updateItem);
        menu.add(getGainItem);
        menu.add(searchItem);
        menu.add(quitItem);


        //add menu to menu bar
        menuBar.add(menu);

        return menuBar;
    }

    /*
     * Creates the intro panel for the GUI
     * Creates a panel with a border layout
     * Creates a welcome label and an instruction label
     * Adds the labels to the panel
     * Returns the panel
     */
    private JPanel createIntroPanel(){
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        //title label
        JLabel welcomeLabel = new JLabel("Welcome to ePortfolio", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        //instruction label, makes it so its on the screen fully
        JTextArea instructionArea = new JTextArea("Choose a command from the Commands menu to buy or sell an investment, update prices for all investments, get the total gain, or search for relevant investments, or quit the program.");
        instructionArea.setEditable(false);
        instructionArea.setWrapStyleWord(true);
        instructionArea.setLineWrap(true);
        instructionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(instructionArea, BorderLayout.CENTER);

        return panel;

    }

    /*
     * Creates the buy panel for the GUI
     * Creates a panel with a grid layout
     * Creates a drop down menu for type, text fields for symbol, name, quantity, and price
     * Creates a message area for displaying messages
     * Creates a reset button for clearing the text fields
     * Creates a buy button for buying an investment
     * Adds the components to the panel
     * Returns the panel
     */
    private JPanel createBuyPanel(){

        JPanel mainPanel = new JPanel(new BorderLayout(10,10));

        //adding title
        JLabel titleLabel = new JLabel("Buying an investment", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);


        //content panel
        JPanel contentPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        //drop down menu for type then add to panel
        JLabel typeLabel = new JLabel("Type");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Stock", "Mutual Fund"});
        contentPanel.add(typeLabel);
        contentPanel.add(typeComboBox);

        
        //create text fields and labels
        JLabel symbolLabel = new JLabel("Symbol");
        JTextField symbolField = new JTextField();

        JLabel nameLabel = new JLabel("Name");
        JTextField nameField = new JTextField();

        JLabel quantityLabel = new JLabel("Quantity");
        JTextField quantityField = new JTextField();

        JLabel priceLabel = new JLabel("Price");
        JTextField priceField = new JTextField();

        //message area
        JLabel messageLabel = new JLabel("Messages");
        JTextArea messageArea = new JTextArea(5,30);
        messageArea.setEditable(false);
        JScrollPane messageScroll = new JScrollPane(messageArea);


        //reset button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            symbolField.setText("");
            nameField.setText("");
            quantityField.setText("");
            priceField.setText("");
            messageArea.setText("");
        });



        //buy button implementation using try catch blocks to catch exceptions
        JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(e -> {
            String type = (String) typeComboBox.getSelectedItem();
            String symbol = symbolField.getText().trim();
            String name = nameField.getText().trim();
            String quantity = quantityField.getText().trim();
            String price = priceField.getText().trim();

            if(symbol.isEmpty() || name.isEmpty() || quantity.isEmpty() || price.isEmpty()){
                messageArea.append("All fields must be filled\n");
                return;
            }

            try {
                int quantityInt = Integer.parseInt(quantity);
                double priceDouble = Double.parseDouble(price);
                ePortfolio.buy(type, symbol, name, quantityInt, priceDouble);
                messageArea.append("Bought " + quantityInt + " of " + symbol + " at " + priceDouble + "\n");
            } catch(NumberFormatException x){
                messageArea.append("Quantity and price must be numbers\n");
            } catch(IllegalArgumentException x){
                messageArea.append(x.getMessage() + "\n");
            }
        });

        //add components to panel
        contentPanel.add(typeLabel);
        contentPanel.add(typeComboBox);
        contentPanel.add(symbolLabel);
        contentPanel.add(symbolField);
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(quantityLabel);
        contentPanel.add(quantityField);
        contentPanel.add(priceLabel);
        contentPanel.add(priceField);
        contentPanel.add(messageLabel);
        contentPanel.add(messageScroll);
        contentPanel.add(resetButton);
        contentPanel.add(buyButton);


        mainPanel.add(contentPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    /*
     * Creates the sell panel for the GUI
     * Creates a panel with a grid layout
     * Creates a drop down menu for type, text fields for symbol, quantity, and price
     * Creates a message area for displaying messages
     * Creates a reset button for clearing the text fields
     * Creates a sell button for selling an investment
     * Adds the components to the panel
     * Returns the panel
     */
    private JPanel createSellPanel(){
        //title panel
        JPanel mainPanel = new JPanel(new BorderLayout(10,10));

        //adding title
        JLabel titleLabel = new JLabel("Selling an investment", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);



        JPanel contentPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //create text fields and labels
        JLabel typeLabel = new JLabel("Type");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Stock", "Mutual Fund"});

        JLabel symbolLabel = new JLabel("Symbol");
        JTextField symbolField = new JTextField();

        JLabel quantityLabel = new JLabel("Quantity");
        JTextField quantityField = new JTextField();

        JLabel priceLabel = new JLabel("Price");
        JTextField priceField = new JTextField();

        JLabel messageLabel = new JLabel("Messages");
        JTextArea messageArea = new JTextArea(5,30);
        messageArea.setEditable(false);
        JScrollPane messageScroll = new JScrollPane(messageArea);


        //reset button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            symbolField.setText("");
            quantityField.setText("");
            priceField.setText("");
            messageArea.setText("");
        });


        //sell button implementation using try catch blocks to catch exceptions
        JButton sellButton = new JButton("Sell");
        sellButton.addActionListener(e -> {
            String type = (String) typeComboBox.getSelectedItem();
            String symbol = symbolField.getText().trim();
            String quantity = quantityField.getText().trim();
            String price = priceField.getText().trim();

            if(symbol.isEmpty() || quantity.isEmpty() || price.isEmpty()){
                messageArea.append("All fields must be filled\n");
                return;
            }


            try {
                int quantityInt = Integer.parseInt(quantity);
                double priceDouble = Double.parseDouble(price);
                ePortfolio.sell(type, symbol, quantityInt, priceDouble);
                messageArea.append("Sold " + quantityInt + " of " + symbol + " at " + priceDouble + "\n");
            } catch(NumberFormatException x){
                messageArea.append("Quantity and price must be numbers\n");
            } catch(IllegalArgumentException x){
                messageArea.append(x.getMessage() + "\n");
            }
        });
    

        //add components to panel
        contentPanel.add(typeLabel);
        contentPanel.add(typeComboBox);
        contentPanel.add(symbolLabel);
        contentPanel.add(symbolField);
        contentPanel.add(quantityLabel);
        contentPanel.add(quantityField);
        contentPanel.add(priceLabel);
        contentPanel.add(priceField);
        contentPanel.add(messageLabel);
        contentPanel.add(messageScroll);
        contentPanel.add(resetButton);
        contentPanel.add(sellButton);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    /*
     * Creates the update panel for the GUI
     * Creates a panel with a grid layout
     * Creates text fields for symbol, name, and price
     * Creates a message area for displaying messages
     * Creates prev and next buttons for navigating between investments
     * Creates a save button for updating the price of an investment
     * Adds the components to the panel
     * Returns the panel
     */
    private JPanel createUpdatePanel(){

        //title panel
        JPanel mainPanel = new JPanel(new BorderLayout(10,10));

        //adding title
        JLabel titleLabel = new JLabel("Updating investments", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        //content panel
        JPanel contentPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //create text fields and labels
        JLabel symbolLabel = new JLabel("Symbol");
        JTextField symbolField = new JTextField();
        symbolField.setEditable(false);

        JLabel nameLabel = new JLabel("Name");
        JTextField nameField = new JTextField();
        nameField.setEditable(false);

        JLabel priceLabel = new JLabel("Price");
        JTextField priceField = new JTextField();


        //message area
        JLabel messageLabel = new JLabel("Messages");
        JTextArea messageArea = new JTextArea(5,30);
        messageArea.setEditable(false);
        JScrollPane messageScroll = new JScrollPane(messageArea);

        //buttons for updating
        JButton prevButton = new JButton("Prev");
        JButton nextButton = new JButton("Next");
        JButton saveButton = new JButton("Save");

        //button implementations which call the navigateInvestments method, using 1 or -1 as the step to navigate to the next or previous investment
        prevButton.addActionListener(e -> navigateInvestments(-1, symbolField, nameField, priceField, prevButton, nextButton));
        nextButton.addActionListener(e -> navigateInvestments(1, symbolField, nameField, priceField, prevButton, nextButton));

        saveButton.addActionListener(e -> {
            try {
                double price = Double.parseDouble(priceField.getText().trim());
                ePortfolio.getInvestments().get(currentIndex).setPrice(price);
                messageArea.append("Updated price for " + symbolField.getText() + "\n");
            } catch(NumberFormatException x){
                messageArea.append("Price must be a number\n");
            }
        });

        
        //add components to panel
        contentPanel.add(symbolLabel);
        contentPanel.add(symbolField);

        contentPanel.add(nameLabel);
        contentPanel.add(nameField);

        contentPanel.add(priceLabel);
        contentPanel.add(priceField);

        contentPanel.add(messageLabel);
        contentPanel.add(messageScroll);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(saveButton);
        contentPanel.add(buttonPanel);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);

    
        return mainPanel;
    }

    /*
     * Creates the get gain panel for the GUI
     * Creates a panel with a grid layout
     * Creates a text field for total gain and a text area for individual gains
     * Creates a calculate button for calculating the total gain
     * Adds the components to the panel
     * Returns the panel
     */
    private JPanel createGetGainPanel(){

         //title panel
         JPanel mainPanel = new JPanel(new BorderLayout(10,10));

         //adding title
         JLabel titleLabel = new JLabel("Getting total gain", JLabel.CENTER);
         titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
         mainPanel.add(titleLabel, BorderLayout.NORTH);



        JPanel contentPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //create text fields and labels
        JLabel totalGainLabel = new JLabel("Total Gain");
        JTextField totalGainField = new JTextField();
        totalGainField.setEditable(false);


        JTextArea individualGainsArea = new JTextArea(5,30);
        individualGainsArea.setEditable(false);
        JScrollPane individualGainsScroll = new JScrollPane(individualGainsArea);

        //calculate gain button using the getGain method from the portfolio class
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> {
            double totalGain = ePortfolio.getGain();
            totalGainField.setText(String.format("%.2f", totalGain));
            individualGainsArea.setText("");

            for(Investment investment : ePortfolio.getInvestments()){
                individualGainsArea.append(investment.getSymbol() + ": " + investment.calculateGain() + "\n");
            }

        });

        //add components to panel
        contentPanel.add(totalGainLabel);
        contentPanel.add(totalGainField);
        contentPanel.add(new JLabel("Individual Gains"));
        contentPanel.add(individualGainsScroll);
        contentPanel.add(calculateButton);

        mainPanel.add(contentPanel, BorderLayout.CENTER);


        return mainPanel;
    }
    
    /*
     * Creates the search panel for the GUI
     * Creates a panel with a grid layout
     * Creates text fields for symbol, name keywords, low price, and high price
     * Creates a text area for search results
     * Creates a search button for searching for investments
     * Adds the components to the panel
     * Returns the panel
     */
    private JPanel createSearchPanel(){


        //title panel
        JPanel mainPanel = new JPanel(new BorderLayout(10,10));

        //adding title
        JLabel titleLabel = new JLabel("Searching investments", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);



        JPanel contentPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //create text fields and labels
        JLabel symbolLabel = new JLabel("Symbol");
        JTextField symbolField = new JTextField();

        JLabel nameKeywordLabel = new JLabel("Name keywords");
        JTextField nameKeywordField = new JTextField();

        JLabel lowPriceLabel = new JLabel("Low price");
        JTextField lowPriceField = new JTextField();

        JLabel highPriceLabel = new JLabel("High price");
        JTextField highPriceField = new JTextField();

        //search results area
        JLabel searchResultsLabel = new JLabel("Search Results");
        JTextArea searchResultsArea = new JTextArea(5,30);
        searchResultsArea.setEditable(false);
        JScrollPane searchResultsScroll = new JScrollPane(searchResultsArea);

        //search button implementation
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {

            String symbol = symbolField.getText().trim();
            String nameKeywords = nameKeywordField.getText().trim();
            String lowPrice = lowPriceField.getText().trim();
            String highPrice = highPriceField.getText().trim();

            String priceRange = "";
            if(!lowPrice.isEmpty() || !highPrice.isEmpty()){
                priceRange = (lowPrice.isEmpty() ? "0" : lowPrice) + "-" + (highPrice.isEmpty() ? Double.MAX_VALUE : highPrice);
            }

            try {

                List<Investment> results = ePortfolio.search(symbol, nameKeywords, priceRange);
                searchResultsArea.setText("");

                if(results.isEmpty()){
                    searchResultsArea.append("No results found\n");
                } else {
                    for(Investment investment : results){
                        searchResultsArea.append(investment.toString() + "\n");
                    }
                }

            } catch(NumberFormatException x){
                searchResultsArea.setText("Invalid price values\n");
            }
        });
        
        contentPanel.add(symbolLabel);
        contentPanel.add(symbolField);
        contentPanel.add(nameKeywordLabel);
        contentPanel.add(nameKeywordField);
        contentPanel.add(lowPriceLabel);
        contentPanel.add(lowPriceField);
        contentPanel.add(highPriceLabel);
        contentPanel.add(highPriceField);
        contentPanel.add(searchResultsLabel);
        contentPanel.add(searchResultsScroll);
        contentPanel.add(searchButton);

        
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    /*
     * Navigates between investments in the update panel
     * Increments or decrements the current index by the step
     * Sets the text fields to the values of the current investment
     * Disables the prev button if the current index is 0
     * Disables the next button if the current index is the last index
     * @param step the amount to increment or decrement the current index
     * @param symbolField the text field for the symbol
     * @param nameField the text field for the name
     * @param priceField the text field for the price
     * @param prevButton the button for navigating to the previous investment
     * @param nextButton the button for navigating to the next investment
     */
    private void navigateInvestments(int step, JTextField symbolField, JTextField nameField, JTextField priceField, JButton prevButton, JButton nextButton){
        currentIndex += step;

        if(currentIndex < 0){
            currentIndex = 0;
        } else if(currentIndex >= ePortfolio.getInvestments().size()){
            currentIndex = ePortfolio.getInvestments().size() - 1;
        }

        Investment currentInvestment = ePortfolio.getInvestments().get(currentIndex);
        symbolField.setText(currentInvestment.getSymbol());
        nameField.setText(currentInvestment.getName());
        priceField.setText(String.format("%.2f", currentInvestment.getPrice()));

        prevButton.setEnabled(currentIndex > 0);
        nextButton.setEnabled(currentIndex < ePortfolio.getInvestments().size() - 1);
    }

    //main method for calling the GUI
    public static void main(String[] args) {
        new ePortfolioGUI();
    }
    

}
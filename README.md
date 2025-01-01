***ePortfolio README FILE***
Created By: Evan Bucholski
Username: ebuchols
StudentID: 1226299
Due Date: November 29th, 2024

***HOW TO RUN THE PROGRAM***

TO CREATE THE .CLASS FILES: javac ePortfolio/*.java

TO RUN THE PROGRAM AFTER COMPILED: java ePortfolio.Portfolio

THIS TIME YOU CAN RUN IT THE SAME AS ABOVE OR

CLASS FILES REMAIN THE SAME

java ePortfolio.Portfolio "FILENAME"

SO YOU COULD DO portfolio.txt, IF IT DOESNT EXIST IT WILL CREATE IT AT THE END WHEN SAVED WITH THE FILENAME USED AS THE ARG.


***HOW TO RUN THE PROGRAM GUI***
TO CREATE THE .CLASS FILE FOR THE GUI: javac ePortfolio/*.java

TO RUN THE GUI WINDOW: java ePortfolio.ePortfolioGUI


***BRIEF OVERVIEW OF PROGRAM (THE PROBLEM BEING SOLVED)***

This is an ePortfolio that can be used to add both stocks and mutualfunds in order to keep track of
the earnings/sellings, and value of ones portfolio. The commands currently availble on this portfolio are
buying, selling, updating, gettingGain, and searching. The problem being solved is the usual issue of tracking money and investments. This provides an organized and easy way to track both the stocks and mutualfunds someone has invested and can be updated on the fly to account to different sales and buying.


***ASSUMPTIONS AND LIMITATIONS***

There are certain limitations with this implementation as it is still within the early stages and cannot do everything a portfolio should be able to do yet. One really cool thing that could be implemented eventually is real time updates on the stock/mutualfund prices so the user doesn't have to do any manual changes at all. So right now we are assuming the user has manually changed and updated every price/ put in every price properly.


***ADDITIONS FOR ASSIGNMENT 2***

 - Investment super class that is the parent class of all investments and the stock and mutualfund classes now extend investment, helps majorly for organization aspects

 - Allows for file handling now, user can input the file to be read in at the start and it will automatically fill the portfolio with the investments. Then at the end it will save the values added during the running of the program to the file.

 - Added Hashmap for index keyword searches.

 - In general, a lot of the functions and processes were made a lot easier when it was not needed to iterate through both stock and mutualfunds. Now I could just iterate through the super class so it made a lot of the functionality easier.


 ***ADDITIONS FOR ASSIGNMENT 3***
  
  - Implemented the GUI for the project itself. This includes all the commands such as the menu interface that can be dropped down to show the options menu where the user can choose between Buy, Sell, Update, Getgain and Search features of the portfolio.

  - With this addition, I also implememted the numerous safety and error checking features into the GUI to make sure the user is not doing things they should not be doing such as trying to sell Stock they don't have.

  - Also made changes to the implementations of search and other functions within Portfolio so that they would return values instead of printing to the console so I could use them properly in my GUI windows.


***TEST PLAN***

ERROR HANDLING: 

First the program uses multiple error checking when the user is inputting certain values for prices etc. Within the program there is built in checkers that will ensure the value inputted is not a negative number. If inputted wrong the user will be asked to enter the correct values allowed.

The program will also check if an incorrect investment type is entered and will once again redirect the user to the menu to correct themselves and write the expected value.



INPUT VALIDATION IN CHOICE:

The program will also ensure that the correct command is entered when using the main menu, for example if none of the available options are entered then it will reask the menu question. And for all the choices it allows for the lowercase and uppercase options regardless as long as the word remains the same. Then for the "Quit" option, it will allow for any variation of quit as well as "q" and "Q".


OVERALL TESTING:

My overall testing includes running through every possible scenario in order to ensure the program runs accordingly.


TESTING FOR BUY:

 - Checks to make sure mutualfund or stock was inputted, otherwise reasks
 - Checks to see if symbol matches another stock and if it does, it will instead ask for new price and quantity
 - Checks for correct price and quantity values

TESTING FOR SELL:

 - Checks to make sure mutualfund or stock was inputted, otherwise reasks
 - Checks to make sure the symbol inputted by the user is a real symbol and is within the list, otherwise lets them know.
 - Checks for correct price and quantity values, also makes sure the user is not trying to sell more of the stock/mutualfund then what they have.
 - If the quantity gets down to 0, automatically removes the stock/mutualfund from the list.

TESTING FOR UPDATE:

 - Before update was not checking to ask what type of investment they want to update, now it will ask what type of investment they want to update then go through that list accordingly, asking them to update each price.
 - Also has the same error checking with the invalid inputs and will reask the user to enter again. Same as buy and sell.


TESTING FOR GETGAIN:

 - Not really any user input detections because this just calculates the total portfolio gain.


TESTING FOR SEARCH:

 - Search uses try catch blocks in order to catch if the user is implementing the wrong inputs and will let the user know.
 - Search also allows for single number price range inputs and that will act as the minimum value.


TESTING FOR FILE INPUT/OUTPUT

 - Checks to see the amount of arguements the user inputted when running, if it was less than the expected it will create the file that will be used to save the information to, otherwise it will use the file given. No matter what though a file will be saved after the program is excited.




TESTING FOR GUI:


The testing for GUI was basically running through all the test cases as stated above but instead this time obviously using the GUI to do them and make sure the appropriate error message was then printed out in the message area.


***POSSBILE IMPROVEMENTS***

One major improvement that came to mind is to generally expand the portfolio to allow for more investment types. The biggest one and the one that should be done first is with crypto. Crypto is ever growing and has become one of the biggest means of investing espesially with the blowup of bitcoin and eth.

Like mentioned before, this could be made way more complex by implementing stock market APIs that could update the portfolio in real time so the user would never have to update the prices of things manually. Pretty much everything in this portfolio right now is very baseline and the user has to do everything manually. In real life use this portfolio would get no use as it provides no real practicality.


A2 IMPROVEMENTS

This could still be made a lot easier in terms of the entire process by making a GUI where the user can click the responsive buttons instead of having to interact with the terminal of the program.


A3 IMPROVEMENTS

I would say the biggest issue with this assignment for me was the time crunch espesially at the end of the year. I think if we had more time for this there could have been many improvement to the GUI to make it fancier and more user friendly.






                                VIRTUAL TRADING APPLICATION SETUP
*********************************************************************************************************************


*********************************************************************************************************************
how to run our program to create a portfolio with 3 different stocks, a second portfolio with 2 different stocks and query their value on a specific date.
*********************************************************************************************************************
Run the jar
1) UI: "Enter 1: To create a Portfolio 2: To query the portfolio details 3: To load a Portfolio"
   User input: 1
2) UI: "Enter the name of the Portfolio"
   User input: p1
3) UI: "Enter the name of the company to be added to portfolio"
   User input: AAPL
4) UI: "Enter the quantity of the stocks"
   User input: 10
5) UI: "Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio."
   User input: 1
6) UI: "Enter the name of the company to be added to portfolio"
   User input: AMZN
7) UI: "Enter the quantity of the stocks"
   User input: 20
8) UI: "Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio."
   User input: 1
9) UI: "Enter the name of the company to be added to portfolio"
   User input: TWTR
10) UI: "Enter the quantity of the stocks"
    User input: 50
11) UI: "Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio."
    User input: 2
12) UI: "Enter 1: To continue trading further. 2: To exit from this session."
    User input: 1
13) UI: "Enter 1: To create a Portfolio 2: To query the portfolio details 3: To load a Portfolio"
    User input: 1
14) UI: "Enter the name of the Portfolio"
    User input: p2
15) UI: "Enter the name of the company to be added to portfolio"
    User input: dash
16) UI: "Enter the quantity of the stocks"
    User input: 11
17) UI: "Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio."
    User input: 1
18) UI: "Enter the name of the company to be added to portfolio"
    User input: NVDA
19) UI: "Enter the quantity of the stocks"
    User input: 30
20) UI: "Enter 1: To continue trading further. 2: To exit from this session."
    User input: 1
21) UI: "Enter 1: To continue trading further. 2: To exit from this session."
    User input: 1
22) UI: "Enter 1: To create a Portfolio 2: To query the portfolio details 3: To load a Portfolio"
    User input: 2
23) UI: "Enter 1: To view composition  2: To get TotalValue of portfolio"
    User input: 2
24) UI: "Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)"
    User input: "2022-10-10"
25) UI: "Enter 1: To continue querying loaded portfolio  2: To perform actions on other portfolios"
    User input: 2
26) UI: "Enter 1: To continue trading further. 2: To exit from this session."
    User input: 1
27) UI: "Enter 1: To create a Portfolio 2: To query the portfolio details 3: To load a Portfolio"
    User input: 2
28) UI: "Enter 1: To create a Portfolio 2: To query the portfolio details 3: To load a Portfolio"
    User input: 2
29) UI: "Enter the name of the Portfolio"
    User input: p8
30) UI: "Enter 1: To view composition  2: To get TotalValue of portfolio"
    User input: 2
31) UI: "Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)"
    User input: 2022-04-05
32) UI: "Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)"
    User input: 2022-09-09
33) UI: "Enter 1: To continue querying loaded portfolio  2: To perform actions on other portfolios"
    User input: 2
34) UI: "Enter 1: To continue trading further. 2: To exit from this session."
    User input: 2





*********************************************************************************************************************
generic steps for the user to follow:
*********************************************************************************************************************
Run the jar file.

creating a portfolio:
---------------------
1) Text displayed on Text UI after running jar file:
   "Enter 1: To create a Portfolio 2: To query the portfolio details 3: To load a Portfolio"
2) Type 1 and press enter to create a new portfolio.
3) Text displayed after pressing enter:
    "Enter the name of the Portfolio"
4) Type the name that you want to give to this portfolio and press enter.
5) Text displayed after pressing enter:
    "Enter the name of the company to be added to portfolio"
6) Type the company ticker symbol and press enter.
    (Note: only 25 ticker symbols are supported. Please enter one among these. ( AAPL, ABNB, AMZN, CCL, COIN, DASH, DKNG, F, GT, OBM, INTC, META, NU, NVDA, NXPL, ORCL, SHOP, SOFI, T, TLRY, TREX, TSLA, TSP, TWTR, UBER ))
    (Note: we support case-insensitive ticker symbols )
7) Text displayed after pressing enter:
    "Enter the quantity of the stocks"
8) Type the quantity of stocks of above entered company that you want to buy. Press enter.
    (Note: please enter positive whole numbers)
9) Text displayed after pressing enter:
   "Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio."
    a) Type 1 and press enter to keep adding stocks of multiple companies. Steps 5 to 9 will repeat for each stock that you add.
    b) Once you are done adding all your stocks to the current portfolio that you created, type 2 and press enter.
    (Note: If you press 2 to exit from current portfolio, your portfolio will be persisted as a csv.)
10) If 2 is pressed to exit from current portfolio, then text displayed after pressing enter:
    "Enter 1: To continue trading further. 2: To exit from this session"
    a) Type 2 and press enter to exit the program completely.
    b) If you dont want to exit but continue exploring further in this session then enter 1 and press enter.
13) If 1 is pressed then text displayed after pressing enter:
    "Enter 1: To create a Portfolio 2: To query the portfolio details 3: To load a Portfolio"
    a) type 1 and press enter to create a new portfolio. steps 1  to 13 will repeat.
    b) Steps to query the existing portfolio details and steps to load a portfolio by user are explained in further sections below.


Query the portfolio details:
----------------------------
14) In step 13, if you type 2 and press enter then text displayed is:
    "Enter the name of the Portfolio"
15) Type the name of portfolio name that you want to view and press enter.
    ( Note: portfolio name is case sensitive.
      Note: Please enter the name of the portfolio which you already created)
16) Text displayed after pressing enter:
    "Enter 1: To view composition  2: To get TotalValue of portfolio"
17) Type 1 and press enter to view composition.
18) Composition is diplayed as below:
    CompanyName Quantity PriceBought DatePurchase TotalValueWhenPurchased
19) After displaying composition, text displayed on the UI:
    "Enter 1: To continue querying loaded portfolio   2: To perform actions on other portfolios"
20) If you want to get the total value of the portfolio as well then type 1 and press enter to continue querying the portfolio which you just loaded before this step.
21) text displayed after pressing enter:
    "Enter 1: To view composition  2: To get TotalValue of portfolio"
22) since you already tried view compostion option for this portfolio, you can now type 2 and press enter to get total value of this portfolio.
23) Text displayed after pressing enter.
    "Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)"
24) Type date in given format and press enter.
    (Note: stock prices are available only for a certain date range ( today's date to some past date)
25) total valuation is displayed after entering the correct date.
26) Text displayed after displaying total valuation is:
     "Enter 1: To continue querying loaded portfolio   2: To perform actions on other portfolios"
27) since you have explored the option 1 completely, try option 2.
    type 2 and press enter.
28) text displayed after pressing enter:
    "Enter 1: To continue trading further. 2: To exit from this session."
    a) type 1 and press enter if you want to try loading a csv file with stocks info( comapany name and quantity)
        into the application.
    b) type 2 and press enter if you want to close the application.

loading a portfolio:
---------------------
29) In step 28, if you typed 1 and pressed enter, text displayed is:
    "Enter the path of File to load Portfolio"
30) Enter the absolute path of the file stored in your local and press enter.
31) After pressing enter, text displayed is:
    "Enter 1: To view details of this portfolio.  2: To exit and continue further trading."
    a) Type 1 and press enter to view results computed using this loaded file.
    b) Type 2 and press enter to just load the file and not do anything further with this loaded file.
32) If you typed 1 and pressed enter then text displayed is:
    "Enter 1: To view composition  2: To get TotalValue of portfolio"
33) steps 16 to 28 will be repeated based on whether you enter 1 or 2.


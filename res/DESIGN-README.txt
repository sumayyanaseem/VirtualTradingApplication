                                          VIRTUAL TRADING APPLICATION
                                          ***************************


******************************************************************************************************************************************************************
CURRENT ASSUMPTIONS:
******************************************************************************************************************************************************************
1. We are currently offering only a selected set of 25 companies to trade from. Given below are the Ticker symbols for these companies.
   ( AAPL, ABNB, AMZN, CCL, COIN, DASH, DKNG, F, GT, OBM, INTC, META, NU, NVDA, NXPL, ORCL, SHOP, SOFI, T, TLRY, TREX, TSLA, TSP, TWTR, UBER )
2. Stock prices for the above companies are available only for a specific range of dates ( From 2022-11-03 to 2022-06-15 ).
   Hence the user will be able to get any kind of values from his portfolio only within this range.
   ( for example: Stock prices for AAPL are availble from 2022-11-03 date to 2022-06-09 )
3. We are currently using CSV format to save the files. Reading/writing in done from csv formatted files.
4. Once the user is done inputting the stocks related data for the current portfolio he is trading in, we are persisting the portfolio as a csv file in local.
   ( sample portfolio path from project root directory : userPortfolios/portfolio_name.csv )
5. We are currently using downloaded csv files as cache to get stock info instead of AlphaVantage API due to restriction of number of API calls that can be made per minute;
6. We are not using any other external dependencies apart from the ones available in JDK.


******************************************************************************************************************************************************************
CURRENT DESIGN:
******************************************************************************************************************************************************************
TradingMVC.java is the starting point of application.

Objects of model, view and controller are created as described below:
View object is used to display output messages to user. It uses System.out stream.
Controller takes user input and uses view Object to display messages to user. Hence, controller Object is created using System.in stream and view Object.
Controller has model object as one of the parameters(Here we are using the composition technique).

---------------------------------------------------------------------------------------------------------------------------------------------------------------
PortfolioController.java is Controller interface

Role of Controller:
Takes user input and delegates the actions to Model and View accordingly.
One controller object will create multiple instances of Portfolio(when creating more than one portfolio).

Methods in Controller Class:
start:  a)start method which takes a new model object to trigger the start of our application.

        b)We pass a new model object to start method instead of passing it as constructor arguments while creating controller object because we might need to call start method
        internally again with a different new model object when the user wants to create a new portfolio.

        c)start method internally makes calls to methods in model and view depending on user inputs taken by controller.

(Note:Controller has a validation to stop the user entering fractional stocks.)

----------------------------------------------------------------------------------------------------------------------------------------------------------------
PortfolioView.java is view Interface.

Role of View:
We have below 3 categories for methods of view.
a)Displays the output messages to the user on text based UI, for the user to enter the input as number/word text accordingly.
b)Displays certain results queried by user.
c)Also, displays error messages to user.

Methods in View Class:
category 'a':
1) callToViewToChooseCreateOrView
2) askUserOnHowToCreatePortfolio
3) getFilePath
4) getPortfolioName
5) getBuyOrSellChoiceFromUser
6) getQuantity
7) getCompanyTicker
8) askUserIfHeWantsToContinueTradingInCurrentPortfolio
9) checkIfUserWantsToExitCompletely
10) createOrUpdateExistingPortfolio
11) checkIfUserWantsToViewCompositionOrTotalValue
12) getDateForValuation
13) callExitFromLoad
14) askUserIfheWantsTOContinueViewing

category 'b':
15) displayTotalValue
16) displayComposition

category 'c':
17) displayErrorMessage
--------------------------------------------------------------------------------------------------------------------------------------
PortfolioModel.java is model interface
One Portfolio object holds the info of only one Portfolio.

Role of Model:
We have below 2 categories for methods of view.
a) Model performs actions based on what controller asks it for. After processing, model returns the generated outputs to its client which is controller.
b) Model also performs certain validations on the inputs provided from its client ( i.e controller )

Methods in Model class:
category 'a':
1) buyStocks
2) getTotalValueOfPortfolioOnCertainDate
3) createPortfolioUsingFilePath
4) viewCompositionOfCurrentPortfolio
5) createPortfolioIfCreatedManually
6) getInstance();

category 'b':
7) validateIfCompanyExists
8) validateIfPortfolioAlreadyExists(String portfolioName);
9) validateIfPortfolioDoesntExists(String name);
10)validateQuantity(Quantity) (Note:Model will accept fractional Quantity of stocks as well).
******************************************************************************************************************************************************************
CUSTOM CLASSES
******************************************************************************************************************************************************************

We have created 2 custom classes to support certain Model functionalities.
1) APICustomClass
2) CustomCSVParser

APICustomClass description : This class provides methods to fetch the stock price
a) fetchLatestStockPriceOfThisCompany
b) getStockPriceAsOfCertainDate

CustomCSVParser description : This class provides all the methods (i.e read/write/helper etc) for parsing of CSV files.
a) readFromCSV
b) readFromPathProvidedByUser
c) readFromFileHelper
d) writeTOCSV
e) convertToCSV


******************************************************************************************************************************************************************
Future Enhancements:
******************************************************************************************************************************************************************

1. Provide functionality to Sell the stocks.
2. Determine the Profit or Loss of any particular stock or of the total portfolio.
3. Provide option to Update already created portfolios in the past.
4. Determine the top performing stocks in a portfolio (or) top performing Portfolios among all the portfolios owned by user.
5. Support all the companies listed on stock exchange.
6. Fetch the stock prices from API instead of using cached files.
******************************************************************************************************************************************************************

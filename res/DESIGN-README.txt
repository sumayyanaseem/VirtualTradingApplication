                                          VIRTUAL TRADING APPLICATION
                                          ***************************

******************************************************************************************************************************************************************
DESIGN CHANGES:
******************************************************************************************************************************************************************
1) Initially we had "Portfolio.java" interface which had union of all the methods that can be supported by flexible and inflexible portfolios.
    Hence, FlexiblePortfolioImpl and InFlexiblePortfolioImpl had to implement each of these methods.
    Some methods like sell stocks etc which were not supported by inflexible, we were just throwing an exception saying unsopperted action for inflexible.

    In current design, in "Portfolio.java" interface, we are keeping only those functions which are commonly supported by both flexible and inflexible.
    For any additional methods that a flexible portfolio needs, we have introduced a new interface IFlexible. ( we made this interface extend the Portfolio interface which all other has common methods )
    Hence FlexiblePortfolioImpl.java just implements Portfolio.java interface.
    InflexiblePortfolioImpl.java implements IFlexible.java interface.

2) Earlier we had only "PortfolioView.java" interface to support text based view actions.
    In current design, We added a new view interface ( "PortfolioGUIView.java") to support GUI related view actions.
    We added a common interface "IViewInterface.java" which is extended by both "PortfolioView.java" and PortfolioGUIView.java and has a common method.

3) In current design, we are using command design pattern.

******************************************************************************************************************************************************************
CURRENT ASSUMPTIONS:
******************************************************************************************************************************************************************
1. We are supported all stocks present in Alphavantage API.
2. Stock prices for the above companies are available only for a specific range of dates ( From today's date to Stock's IPO date ).
   Hence the user will be able to get any kind of values from his portfolio only within this range.
3. We are currently using JSON format to save the files. Reading/writing in done from JSON formatted files.
4. Once the user is done inputting the stocks related data for the current portfolio he is trading in, we are persisting the portfolio as a JSON file in local.
   ( sample portfolio path from project root directory : userPortfolios/portfolio_name.json )
5. We are currently using AlphaVantage API to get the stock values and caching these values to avoid multiple calls to AlphaVantage API.
6. Sell orders should be entered in chronological order of dates ( i.e if you sell on 2022-11-13, then your next sell cant be 2022-11-11)
7. We are not using any other external dependencies apart from the ones available in JDK.


******************************************************************************************************************************************************************
CURRENT DESIGN:(Using MVC Pattern)
******************************************************************************************************************************************************************
TradingMVC.java is the starting point of application.

Text based UI's View object - displays output messages to user. It uses System.out stream.
GUI based view object - creating frames, generates panels, displays pop ups.
Controller takes user input and uses view Object to display messages to user. Hence, controller Object is created using System.in stream and view Object.
Controller has model object as one of the parameters(Here we are using the composition technique).

---------------------------------------------------------------------------------------------------------------------------------------------------------------
CONTROLLER:
----------
PortfolioController.java is Controller interface

Role of Controller:
Takes user input and delegates the actions to Model and View accordingly.
One controller object will create multiple instances of Portfolio(when creating more than one portfolio). It can be either flexible portfolio or inflexible portfolio.

Methods in Controller Class:
start:  a)start method which takes a new model object to trigger the start of our application.

        b)We pass a new model object to start method instead of passing it as constructor arguments while creating controller object because we might need to call start method
        internally again with a different new model object when the user wants to create a new portfolio.

        c)start method internally makes calls to methods in model and view depending on user inputs taken by controller.

        d)controller makes calls to model by passing either flexible portfolio object or inflexible portfolio object.

        e)controller supplies AlphaVantage API to model.
(Note:Controller has a validation to stop the user entering fractional stocks.)

----------------------------------------------------------------------------------------------------------------------------------------------------------------
VIEW:
----
PortfolioView.java is view Interface.

Role of View:
We have below 3 categories for methods of view.
a)Displays the output messages to the user on text based UI, for the user to enter the input as number/word text accordingly.
b)Displays certain results queried by user.
c)Also, displays error messages to user.


--------------------------------------------------------------------------------------------------------------------------------------
MODEL:
-----

One Portfolio object holds the info of only one Portfolio which can either be a flexible or inflexible portfolio.
We have below 2 categories for methods of MODEL.
a) Model performs actions based on what controller asks it for. After processing, model returns the generated outputs to its client which is controller.
b) Model also performs certain validations on the inputs provided from its client ( i.e controller )


Role of PortfolioModel.java: It's an interface with all the methods that a model needs to have. Every method signature has an object of portfolio as one of the arguments.
Role of PortfolioModelImpl.java: This class implements all methods in PortfolioModel.java for delegating the calls from controller to either FlexiblePortfolioImpl.java or InflexiblePortfolioImple.java based on the portfolio object passed as argument to the methods.
Role of Portfolio.java: It's an interface which is implemented by FlexiblePortfolioImpl.java and InflexiblePortfolioImpl.java. It has same methods as PortfolioModel.java but only difference is that it doesnt take an object of portfolio as an argument.
Role of FlexiblePortfolioImpl.java: This class implements all methods in Portfolio.java which are supported for a Flexible Portfolio.
Role of InFlexiblePortfolioImpl.java: This class implements all methods in Portfolio.java which are supported for a InFlexible Portfolio.
Role of AbstractPortfolio.java: This Abstract class has the common functionality of both FlexiblePortfolioImpl.java and InFlexiblePortfolioImpl.java portfolio classes.


Methods supported by both Flexible and Inflexible portfolios class:
1)getTotalValueOfPortfolioOnCertainDate
2)loadPortfolioUsingFilePath
3)viewCompositionOfCurrentPortfolio
4)createPortfolioIfCreatedManually
5)validateIfCompanyExists
7)validateIfPortfolioAlreadyExists
8)validateIfPortfolioDoesntExists
9)buyStocks

Additional methods supported by Flexible portfolios class:
10)sellStocks
11)updatePortfolio
12)updatePortfolioUsingFilePath
13)getTotalMoneyInvestedOnCertainDate
14)getPortfolioPerformanceOvertime


******************************************************************************************************************************************************************
CUSTOM CLASSES
******************************************************************************************************************************************************************

We have created 2 custom classes to support certain Model functionalities.
1) APICustomInterface and APICustomClass
2) CustomParser and JsonParserImplementation
3) CompanyTickerSymbol
4) LocalCacheForAPI

APICustomClass.java description : This class provides methods to fetch the stock price
a) fetchLatestStockPriceOfThisCompany
b) getStockPriceAsOfCertainDate

JsonParserImplementation.java description : This class provides all the methods (i.e read/write/helper etc) for parsing of json files.
a) readFromPathProvidedByUser
b) readFromFile
c) readFromJson
d) writeTOCSV
e) convertToCSV
f) writeIntoFile
g) appendIntoFile
h) appendIntoFileUsingFilePath
i) getTypeOfFile
j) getTypeOfLoadedFile


CompanyTickerSymbol.java description: It's an enum with all the supported company names and their respective IPO dates.

LocalCacheForAPI.java description: It is used to cache the values after querying Alphavantage API.

******************************************************************************************************************************************************************
Possible Future Enhancements:
******************************************************************************************************************************************************************

1. Determine the Profit or Loss of any particular stock or of the total portfolio.
2. Determine the top performing stocks in a portfolio (or) top performing Portfolios among all the portfolios owned by user.
3. Support all the companies listed on stock exchange.
******************************************************************************************************************************************************************

                                            WORKING FEATURES
**********************************************************************************************************************************
1) Allows user to create one or more portfolios.
2) Allows the user to input stocks info(companyName and Quantity) using text based UI to create a inflexible portfolio.
   Allows the user to input stocks info(companyName and Quantity and date) using text based UI to create a flexible portfolio with buy/sell options.
3) Allows the user to load a json file consisting of stocks info into this application.
   (Only one file per for loading single portfolio is supported.Multiple portfolio's info in single file is not supported)
4) Allows user to add stocks to the inflexible portfolio ( only add option is supported )
    Allows user to add buy stock and sell stock to the inflexible portfolio.
5) Inflexible Portfolio once created , User cant modify that portfolio.
   Flexible Portfolio once created , can be updated.
6) Allows user to transact only stocks of 26 companies supported by our application.
7) Allows user to view the composition of his portfolios as of the date that he wishes to view.
8) Allows user to view the total value of any portfolios created by him earlier that he wishes to view.(Dates between given range is only supported).
9) Allows user to view the total cost basis of flexible portfolios created by him earlier that he wishes to view.(Dates between given range is only supported).
10) The json files can be saved and retrieved and are of human-readable format.
11) Allows user to create a json file outside of the program using a text editor and our program will load it in.
12) Allows user to view the composition of the portfolio he just loaded.
13) Allows user to view the total value of the portfolios he loaded.
14) Allows user to view the total cost basis of the portfolio he loaded if that portfolio is a flexible portfolio.
15) Allows user to update the loaded portfolio if that portfolio is a flexible portfolio.
16) Our program doesn't crash when user enters invalid inputs. Validations are done at every point and error is displayed to user.
    Our program keeps asking user to enter inputs until he enters a valid input format.
17)when the date provided by the users falls on the weekend,we are pickingup the stock value from the last nearest working day from the given date.
18) Allows user to view the portfolio's performance over a range of time period. Currently we are supporting daily, weekly, monthly, quarterly
    and yearly time frames.

Working features of GUI:
------------------------
We are supporting below functionalities for a flexible portfolio from GUI:
1) creating a portfolio
2) buy/sell stocks on newly created portfolios in that current session or on the portfolios created in the past
3) Dollar cost and fixed cost strategies on newly created portfolios in that current session or on the portfolios created in the past
4) View the details of newly created portfolios in that current session or on the portfolios created in the past.
   view composition/ view total value/ view total cost basis are supported.
5) Exit the application.


Note:
1. We are supporting all stocks available in alphavantage API.
2. Dates acceptable(From current date to IPO date).

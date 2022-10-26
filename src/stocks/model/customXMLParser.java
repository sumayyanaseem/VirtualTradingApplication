package stocks.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class customXMLParser {

  /**
   * 1.get the document builder and get document.
   * 2.normalize the structure
   * 3.Get all elements by Tag Name.
   * @param args
   */
  public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document document =  documentBuilder.parse(new File("src/stocks/InputFile.xml"));
    NodeList portfolioList = document.getElementsByTagName("Portfolio");
    for(int i = 0; i <portfolioList.getLength(); i++) {
      Node portfolio = portfolioList.item(i);
      if(portfolio.getNodeType() == Node.ELEMENT_NODE) {
        Element portfolioElement = (Element) portfolio;
        System.out.println("Portfolio Name: " + portfolioElement.getAttribute("name"));
        NodeList companyList = portfolioElement.getElementsByTagName("Company");
        NodeList laptopDetails =  portfolioElement.getChildNodes();
        System.out.println("Company list: " + companyList.getLength() +" cb  " +laptopDetails.getLength());
        for(int j = 0; j <laptopDetails.getLength(); j++) {
          Node company = laptopDetails.item(j);
         // System.out.println(company.getAttributes());
          if(company.getNodeType() == Node.ELEMENT_NODE) {
           Element companyElement = (Element) company;
          System.out.println("Company Name: " + companyElement.getAttribute("name"));
          NodeList companyDetails
            company.getChildNodes();
          System.out.println(companyDetails.getLength());
          for(int k=0;k<companyDetails.getLength();k++){

          }
          }
        }
      //  NodeList laptopDetails =  portfolio.getChildNodes();
       /* for(int j = 0; j < laptopDetails.getLength(); j++){
          Node detail = laptopDetails.item(j);
          if(detail.getNodeType() == Node.ELEMENT_NODE) {
            Element detailElement = (Element) detail;
           // System.out.println("     " + detailElement.getTagName() + ": " + detailElement.getAttribute("value"));
          }

        }*/

      }
    }








  /*  Document document =  documentBuilder.parse(new File("src/main/java/laptops.xml"));
    NodeList laptopList = document.getElementsByTagName("laptop");
    for(int i = 0; i <laptopList.getLength(); i++) {
      Node laptop = laptopList.item(i);
      if(laptop.getNodeType() == Node.ELEMENT_NODE) {

        Element laptopElement = (Element) laptop;
        System.out.println("Laptop Name: " + laptopElement.getAttribute("name"));

        NodeList laptopDetails =  laptop.getChildNodes();
        for(int j = 0; j < laptopDetails.getLength(); j++){
          Node detail = laptopDetails.item(j);
          if(detail.getNodeType() == Node.ELEMENT_NODE) {
            Element detailElement = (Element) detail;
            System.out.println("     " + detailElement.getTagName() + ": " + detailElement.getAttribute("value"));
          }

        }

      }*/

  }
}

package stocks.customparser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import stocks.model.Stock;


/**
 * This class represents a JSON Parser.
 */
public class JsonParserImplementation implements CustomParser {

  @Override
  public Map<String, List<Stock>> readFromPathProvidedByUser(String path) {
    Map<String, List<Stock>> res = new HashMap<>();
    try {
      res = readFromJson(path);
    } catch (IOException | ParseException e) {
      System.out.println(e.getMessage());
    }
    return res;

  }


  @Override
  public Map<String, List<Stock>> readFromFile(String portFolioName) {
    String path = "userPortfolios/" + portFolioName + "_output.json";
    Map<String, List<Stock>> res = new HashMap<>();
    try {
      res = readFromJson(path);
    } catch (IOException | ParseException e) {
      System.out.println(e.getMessage());
    }
    return res;
  }

  private Map<String, List<Stock>> readFromJson(String path) throws IOException, ParseException {
    Map<String, List<Stock>> res = new HashMap<>();
    List<Stock> list = new ArrayList<>();
    String cName = null;
    Object obj = new JSONParser().parse(new FileReader(path));
    JSONObject jsonObject = (JSONObject) obj;
    JSONArray portfolios = (JSONArray) jsonObject.get("Companies");
    Iterator itr = portfolios.iterator();
    while (itr.hasNext()) {
      Iterator<Map.Entry> itr1 = ((Map) itr.next()).entrySet().iterator();
      while (itr1.hasNext()) {
        Map.Entry pair = itr1.next();
        if (pair.getKey().equals("CompanyName")) {
          // System.out.println(pair.getKey() + " : " + pair.getValue());
          if (res.containsKey(pair.getValue())) {
            cName = (String) pair.getValue();
            list = res.get(pair.getValue());
          } else {
            cName = (String) pair.getValue();
            list = new ArrayList<>();
          }
        } else {
          // System.out.println(pair.getKey() + " : " + pair.getValue());
          JSONArray ja = (JSONArray) pair.getValue();
          Iterator itr2 = ja.iterator();
          while (itr2.hasNext()) {
            Iterator<Map.Entry> itr3 = ((Map) itr2.next()).entrySet().iterator();
            String date = null;
            String quantity = null;
            String action = null;
            while (itr3.hasNext()) {
              pair = itr3.next();
              //System.out.println(pair.getKey() + " : " + pair.getValue());
              if (pair.getKey().equals("date")) {
                date = String.valueOf(pair.getValue());
              } else if (pair.getKey().equals("Quantity")) {
                quantity = (String) pair.getValue();
              } else if (pair.getKey().equals("action")) {
                action = String.valueOf(pair.getValue());
              }

            }
            Stock s = new Stock(
                    cName, Double.parseDouble(quantity),
                    0.0, action, 0.0, date);
            list.add(s);
          }
          res.put(cName, list);
        }
      }
    }
    return res;
  }


  @Override
  public void writeIntoFile(String portFolioName, Map<String, List<Stock>> map, String type) {
    //used for firstTime creating a file
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("type", type);
    JSONArray outerJson = new JSONArray();
    for (Map.Entry<String, List<Stock>> entry : map.entrySet()) {
      //System.out.println(entry.getKey()+" "+entry.getValue());
      String cName = entry.getKey();
      List<Stock> list = entry.getValue();
      Map outerMap = new HashMap<>(2);
      outerMap.put("CompanyName", cName);
      JSONArray innerJson = new JSONArray();
      for (Stock s : list) {
        Map<String, String> innerMap = new HashMap<>();
        innerMap.put("date", s.getDateOfAction());
        innerMap.put("Quantity", String.valueOf(s.getQty()));
        innerMap.put("action", s.getAction());
        innerJson.add(innerMap);
      }
      outerMap.put("Actions", innerJson);
      outerJson.add(outerMap);
    }
    jsonObject.put("Companies", outerJson);

    String path = "userPortfolios/" + portFolioName + "_output.json";
    try {
      FileWriter file = new FileWriter(path);
      file.write(jsonObject.toJSONString());
      file.flush();
      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private void appendFileHelper(String companyName,
                                String quantity, String action, String date, String path) {
    try {
      Object obj = new JSONParser().parse(new FileReader(path));
      JSONObject jsonObject = (JSONObject) obj;
      JSONArray portfolios = (JSONArray) jsonObject.get("Companies");
      Iterator itr = portfolios.iterator();
      boolean found = false;
      while (itr.hasNext()) {
        Iterator<Map.Entry> itr1 = ((Map) itr.next()).entrySet().iterator();
        while (itr1.hasNext()) {
          Map.Entry pair = itr1.next();
          if (pair.getValue().equals(companyName)) {
            pair = itr1.next();
            if (pair.getKey().equals("Actions")) {
              JSONArray ja = (JSONArray) pair.getValue();
              Map<String, String> m = new LinkedHashMap<>(3);
              m.put("date", date);
              m.put("Quantity", quantity);
              m.put("action", action);
              ja.add(m);
              found = true;
            }
          }
        }
      }
      if (!found) {
        JSONArray ja = new JSONArray();
        Map<String, String> m = new LinkedHashMap<>(3);
        m.put("date", date);
        m.put("Quantity", quantity);
        m.put("action", action);
        ja.add(m);
        Map outerMap = new HashMap<>(2);
        outerMap.put("CompanyName", companyName);
        outerMap.put("Actions", ja);
        portfolios.add(outerMap);
      }
      FileWriter file = new FileWriter(path);
      file.write(jsonObject.toJSONString());
      file.flush();
      file.close();

    } catch (ParseException | IOException e) {
      System.out.println(e.getMessage());
    }
  }


  @Override
  public void appendIntoFile(String portfolioName, String companyName,
                             String quantity, String action, String date) {
    String path = "userPortfolios/" + portfolioName + "_output.json";
    appendFileHelper(companyName, quantity, action, date, path);
  }

  @Override
  public void appendIntoFileUsingFilePath(String path,
                                          String portfolioName, String companyName,
                                          String quantity, String action, String date) {
    appendFileHelper(companyName, quantity, action, date, path);
  }

  @Override
  public String getTypeOfFile(String portFolioName) {
    String path = "userPortfolios/" + portFolioName + "_output.json";
    return getTypeHelper(path);
  }

  @Override
  public String getTypeOfLoadedFile(String path) {
    return getTypeHelper(path);
  }

  private String getTypeHelper(String path) {
    String type = null;
    try {
      Object obj = new JSONParser().parse(new FileReader(path));
      JSONObject jsonObject = (JSONObject) obj;
      type = (String) jsonObject.get("type");
    } catch (IOException | ParseException e) {
      System.out.println(e.getMessage());
    }
    return type;
  }
}

package stocks.customParser;

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
import java.util.List;
import java.util.Map;

public class JsonParserImplementation implements Parser {


  @Override
  public List<List<String>> readFromPathProvidedByUser(String path) {
    List<List<String>> res = new ArrayList<>();
    try {
      res =  readFromJson(path);
    } catch(IOException | ParseException e){
      System.out.println(e.getMessage());
    }
    return res;

  }

  @Override
  public List<List<String>> readFromFile(String portFolioName) {
    String path = portFolioName;
    List<List<String>> res = new ArrayList<>();
    try {
      res =  readFromJson(path);
    } catch(IOException | ParseException e){
      System.out.println(e.getMessage());
    }
    return res;
  }

  private List<List<String>> readFromJson(String path) throws IOException, ParseException {
    List<List<String>> res =  new ArrayList<>();
    Object obj = new JSONParser().parse(new FileReader(path));
    JSONObject jsonObject = (JSONObject) obj;
    String type = (String) jsonObject.get("type");
    System.out.println(type);
    JSONArray portfolios = (JSONArray) jsonObject.get("Companies");
    Iterator itr = portfolios.iterator();
    while (itr.hasNext()) {
      Iterator<Map.Entry> itr1 = ((Map) itr.next()).entrySet().iterator();
      while (itr1.hasNext()) {
        Map.Entry pair = itr1.next();
        if (pair.getKey().equals("CompanyName")) {
          System.out.println(pair.getKey() + " : " + pair.getValue());
        } else {
          // System.out.println(pair.getKey() + " : " + pair.getValue());
          JSONArray ja = (JSONArray) pair.getValue();
          Iterator itr2 = ja.iterator();
          while (itr2.hasNext()) {
            Iterator<Map.Entry> itr3 = ((Map) itr2.next()).entrySet().iterator();
            while (itr3.hasNext()) {
              pair = itr3.next();
              System.out.println(pair.getKey() + " : " + pair.getValue());
            }
          }
        }
      }
    }
    return res;
  }


  public void writeIntoFile(String portFolioName,String companyName,String quantity,String action,String date,String type) {

    //used for firstTime creating a file

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("type",type);

    JSONArray innerJson = new JSONArray();

    Map innerMap = new HashMap();
    innerMap.put("date",date);
    innerMap.put("Quantity",quantity);
    innerMap.put("action",action);
    innerJson.add(innerMap);

    JSONArray outerJson = new JSONArray();

    Map map = new HashMap(2);
    map.put("CompanyName",companyName);
    map.put("Actions",innerJson);
    outerJson.add(map);

    jsonObject.put("Companies",outerJson);
    String path =portFolioName;
    try{
      FileWriter file = new FileWriter(path);
      file.write(jsonObject.toJSONString());
      file.close();
    } catch(IOException e ){
      e.printStackTrace();
    }
  }


  public void appendIntoFile(String portfolioName,String companyName,String quantity,String action,String date,String type){
    String path =portfolioName;
    try {
      Object obj = new JSONParser().parse(new FileReader(path));
      JSONObject jsonObject = (JSONObject) obj;
      jsonObject.put("type",type);

      JSONArray innerJson = new JSONArray();

      Map innerMap = new HashMap();
      innerMap.put("date",date);
      innerMap.put("Quantity",quantity);
      innerMap.put("action",action);
      innerJson.add(innerMap);

      JSONArray outerJson = new JSONArray();

      Map map = new HashMap(2);
      map.put("CompanyName",companyName);
      map.put("Actions",innerJson);
      outerJson.add(map);

      jsonObject.put("Companies",outerJson);
      FileWriter file = new FileWriter(path);
      file.write(jsonObject.toJSONString());
      file.close();
    } catch(ParseException | IOException e){
      System.out.println(e.getMessage());
    }
  }

  public String getTypeOfFile(String portFolioName) {
    String path = portFolioName;
    String type = null;
    try {
      Object obj = new JSONParser().parse(new FileReader("src/stocks/customParser/sampleJson.json"));
      JSONObject jsonObject = (JSONObject) obj;
      type = (String) jsonObject.get("type");
    } catch (IOException | ParseException e) {
      System.out.println(e.getMessage());
    }
    return type;
  }
}

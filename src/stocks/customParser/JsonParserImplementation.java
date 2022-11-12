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

import stocks.model.Stock;

public class JsonParserImplementation  {



  public Map<String,List<Stock>> readFromPathProvidedByUser(String path) {
    Map<String,List<Stock>> res = new HashMap<>();
    try {
      res =  readFromJson(path);
    } catch(IOException | ParseException e){
      System.out.println(e.getMessage());
    }
    return res;

  }


  public Map<String,List<Stock>> readFromFile(String portFolioName) {
    String path = portFolioName;
    Map<String, List<Stock>> res = new HashMap<>();
    try {
      res =  readFromJson(path);
    } catch(IOException | ParseException e){
      System.out.println(e.getMessage());
    }
    return res;
  }

  private Map<String, List<Stock>> readFromJson(String path) throws IOException, ParseException {
    Map<String, List<Stock>> res =  new HashMap<>();
    List<Stock> list = new ArrayList<>();
    String cName=null;
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
          if(res.containsKey(pair.getValue())){
            cName=(String) pair.getValue();
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
            String date=null;
            double quantity=0.0;
            String action=null;
            while (itr3.hasNext()) {
              pair = itr3.next();
              System.out.println(pair.getKey() + " : " + pair.getValue());
              if(pair.getKey().equals("date")){
                date=String.valueOf(pair.getValue());
              } else if(pair.getKey().equals("Quantity")){
                quantity = (double) pair.getValue();
              } else if(pair.getKey().equals("action")){
                action = String.valueOf(pair.getValue());
              }
             Stock s = new Stock(cName,quantity,0.0,action,0.0,date);
              list.add(s);
            }
            res.put(cName,list)  ;
          }
        }
      }
    }
    return res;
  }


  public void writeIntoFile(String portFolioName,Map<String,List<Stock>> map,String type) {

    //used for firstTime creating a file

    JSONObject jsonObject = new JSONObject();

    jsonObject.put("type",type);

    for (Map.Entry<String, List<Stock>> entry : map.entrySet()) {
      System.out.println(entry.getKey()+" "+entry.getValue());
      String cName = entry.getKey();
      List<Stock> list = entry.getValue();
      for(int i=0;i<list.size();i++) {
        Stock s = list.get(i);
        writeIntoFileHelper(jsonObject, cName, s.getDateOfAction(), String.valueOf(s.getQty()), s.getAction());
      }
    }
    /*JSONArray innerJson = new JSONArray();

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
    }*/

    String path =portFolioName;
    try{
      FileWriter file = new FileWriter(path);
      file.write(jsonObject.toJSONString());
      file.close();
    } catch(IOException e ){
      e.printStackTrace();
    }
  }

  private void writeIntoFileHelper(JSONObject jsonObject,String companyName,String date,String quantity,String action){
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
  }


  public void appendIntoFile(String portfolioName,String companyName,String quantity,String action,String date){
    String path =portfolioName;
    try {
      Object obj = new JSONParser().parse(new FileReader(path));
      JSONObject jsonObject = (JSONObject) obj;

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

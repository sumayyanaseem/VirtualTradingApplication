package stocks.customParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class parserImpl {

  public static void main(String[] args) throws IOException, ParseException {

      Object obj = new JSONParser().parse(new FileReader("src/main/java/sample.json"));
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
      writeIntoJson();
    }

    public static void writeIntoJson() throws IOException, ParseException {
      //Inserting key-value pairs into the json object
      Object obj = new JSONParser().parse(new FileReader("src/main/java/sample.json"));
      JSONObject jsonObject = (JSONObject) obj;
      String firstName = (String) jsonObject.get("type");
      System.out.println(firstName);
      JSONArray portfolios = (JSONArray) jsonObject.get("Companies");
      Iterator itr = portfolios.iterator();
      boolean found =false;
      while (itr.hasNext()) {
        Iterator<Map.Entry> itr1 = ((Map) itr.next()).entrySet().iterator();
        while (itr1.hasNext()) {
          Map.Entry pair = itr1.next();
          // System.out.println(pair.getKey() + " : " + pair.getValue());
          if (pair.getValue().equals("P1")) {
            pair = itr1.next();
            if (pair.getKey().equals("Actions")) {
              JSONArray ja = (JSONArray) pair.getValue();

              Map m = new LinkedHashMap(3);
              m.put("date","2022-11-12");
              m.put("Quantity","100");
              m.put("action","buy");
              ja.add(m);
            }
          }
        }
      }
      try {
        FileWriter file = new FileWriter("src/main/java/sample.json");
        file.write(jsonObject.toJSONString());
        file.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    public static void writeFirstTime(){

    }


}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class InputProcess{

//using mapping to hold value..
 // GalacticRomanValueMapping : {pish=X, tegj=L, prok=V, glob=I}
 static Map<String, String> GalacticRomanValueMapping = new HashMap<String, String>();
 // galacticIntegerValue :{pish=10.0, tegj=50.0, prok=5.0, glob=1.0}
 static Map<String, Float> galacticIntegerValue = new HashMap<String, Float>(); 
 // questionAsk :{how much is pish tegj glob glob ?, how many Credits is glob prok Iron ?
 static Map<String, String> questionAsk = new HashMap<String, String>();  
 // Raremetaltransaction:[glob glob Silver is 34 Credits, glob prok Gold is 57800 Credits, pish pish Iron is 3910 Credits]
 static ArrayList<String> Raremetaltransaction= new ArrayList<String>(); 
 //raremetalValueList :{Gold=14450.0, Iron=195.5, Silver=17.0} 
 static Map<String, Float> raremetalValueList = new HashMap<String, Float>(); 
 
 /*
  * Each line is picked up and served to processreadLine() for processing.
  */
public static void ReadFile(String filePath) throws IOException {
  BufferedReader bufferedReader = null;
  if (filePath == null){
   InputStream in = InputProcess.class.getResourceAsStream("input2.txt");
   bufferedReader =new BufferedReader(new InputStreamReader(in));
  }
  else{
   FileReader fileReader = new FileReader(filePath);
   bufferedReader = new BufferedReader(fileReader);
  }
  String line = null;
  while ((line = bufferedReader.readLine()) != null) {
   ProcessReadLine(line);
  }
  bufferedReader.close();
 }

 /*
  * processreadline will processed the input.txt and adds the input to various maps<K,T> 
  * based on different conditions.
  */
 public static void ProcessReadLine(String line){
  String arr[] = line.split("((?<=:)|(?=:))|( )");

  if (line.endsWith("?")){
   questionAsk.put(line,"");
  }
  else if (arr.length == 3 && arr[1].equalsIgnoreCase("is")){
   GalacticRomanValueMapping.put(arr[0], arr[arr.length-1]);
  }
  else if(line.toLowerCase().endsWith("credits")){
  Raremetaltransaction.add(line);
  }
 }

 /**
  * Read input.txt and read using processreadline, thus galactic currency expression 
   assign to Roman equivalent  
  * {pish=X, tegj=L, prok=V, glob=I}
  */
 public static void GalacticcurrencytoIntegerValue(){

  Iterator it = GalacticRomanValueMapping.entrySet().iterator();
  while (it.hasNext()) {
   Map.Entry token = (Map.Entry)it.next();
   float integerValue = new RomanToDecimal().romanToDecimal(token.getValue().toString());
   galacticIntegerValue.put(token.getKey().toString(), integerValue);
  }
  mapElementsEntities();
 }

 /**
  * Finds the value of elements by decoding queries like [glob glob Silver is 34 Credits]
  */
 private static void mapElementsEntities(){
  for (int i = 0; i < Raremetaltransaction.size(); i++) {
   deCodingQuery(Raremetaltransaction.get(i));
  }
 }
 /**
  * Calculates the values of various elements and appends the same to map raremetalValueList.
  * raremetalValueList :{Gold=14450.0, Iron=195.5, Silver=17.0}
  */
 private static void deCodingQuery(String query){
  String array[] = query.split("((?<=:)|(?=:))|( )");
  int splitIndex = 0;
  int creditValue = 0; String element= null; String[] valueofRaremetal = null;
  for (int i = 0; i < array.length; i++) {
   if(array[i].toLowerCase().equals("credits")){
    creditValue = Integer.parseInt(array[i-1]);
   }
   if(array[i].toLowerCase().equals("is")){
    splitIndex = i-1;
    element = array[i-1];
   }
   valueofRaremetal = java.util.Arrays.copyOfRange(array, 0, splitIndex);
  }

  StringBuilder stringBuilder = new StringBuilder();
  for (int j = 0; j < valueofRaremetal.length; j++) {
   stringBuilder.append(GalacticRomanValueMapping.get(valueofRaremetal[j]));
  }
  float valueOfRaremetalInDecimal = new RomanToDecimal().romanToDecimal(stringBuilder.toString());
  raremetalValueList.put(element, creditValue/valueOfRaremetalInDecimal);
 }
}
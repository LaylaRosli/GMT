import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OutputProcess extends InputProcess{

 /*
  * processReplyForQuestionask() itertates over the questionAsk map that contain all the valid queries as keys.
  * It further invokes processReply() on each key for processing the response.
  */
 public static void processReplyForQuestionask(){
  Map<String, String> map = questionAsk;
  for (Map.Entry<String, String> entry : map.entrySet()) {
   processReply(entry.getKey());
  }
 }
 private static void processReply(String query){
  if (query.toLowerCase().startsWith("how much")){
   findValueOfGalacticRoman(query);
  }
  else if (query.toLowerCase().startsWith("how many")){
   findCreditsValueofElements(query);
  }
 }
 /*
  * findValueOfGalacticRoman() processes those queries seeking the decimal equivalent of any
  RomanNumeral and prints the result.
  */
 public static void findValueOfGalacticRoman(String query){
  if (isValidinput(query)== true){
   ArrayList<String> GalacticValueToRoman = new ArrayList<String>();
   ArrayList<String> GalacticValue = splitQuery(query);
   for (int i = 0; i < GalacticValue.size(); i++) {
    GalacticValueToRoman.add(GalacticRomanValueMapping.get(GalacticValue.get(i)));
   }
   float value = new RomanToDecimal().romanToDecimal(GalacticValueToRoman.toString());
   GalacticValue.add("is");GalacticValue.add(Float.toString(value));
   System.out.println(/*query+*/" "+outputFormatter(GalacticValue));
  }
  else{
   System.err.println(/*query+*/" I have no idea what you are talking about");
  }
 }
 /*
  * findCreditsValueOfElements() processes those queries seeking the Credit value of any 
  quantity of raremetal and prints the result.
  */
 private static void findCreditsValueofElements(String query){
  if (isValidinput(query) == true){
   ArrayList<String> tokenValue = splitQuery(query);
   ArrayList<String> tokenValueToRoman = new ArrayList<String>();
   String element = null;
   for (int i = 0; i < tokenValue.size(); i++) {
    if(GalacticRomanValueMapping.get(tokenValue.get(i)) != null){
     tokenValueToRoman.add(GalacticRomanValueMapping.get(tokenValue.get(i)));
    }
    else if (raremetalValueList.get(tokenValue.get(i)) != null){
     element = tokenValue.get(i);
    }
    else{
     System.err.println(/*query+*/"  I have no idea what you are talking about");
    }
   }
   float elementValue = (new RomanToDecimal().romanToDecimal(tokenValueToRoman.toString()) * raremetalValueList.get(element));
   tokenValue.add("is");tokenValue.add(Float.toString(elementValue));tokenValue.add("Credits");
   System.out.println(/*query+*/" "+outputFormatter(tokenValue));
  }
  else{
   System.err.println(/*query+*/"  I have no idea what you are talking about");
  }
 }

 /*
  * Formats the response to a query and displays it in readable format
  */
 private static String outputFormatter(ArrayList<String> output){
  return output.toString().replace(",", "").replace("[", "").replace("]", "");
 }

 /*
  * Applies regex on each input in the file to figure out the valid ones.
  */
 private static boolean isValidinput(String query){
  Pattern regex = Pattern.compile("[$&+,:;=@#|]");
  Matcher matcher = regex.matcher(query);
  if (matcher.find()){
   return false;
  }
  else{
   return true;
  }

 }
 /*
  * Splits the query and returns an ArrayList containing only Roman numerals and raremetals
  */
 private static ArrayList<String> splitQuery(String query){
  ArrayList<String> queryArray = new ArrayList<String>(Arrays.asList(query.split("((?<=:)|(?=:))|( )")));
  int startIndex = 0, endIndex = 0;
  for (int i = 0; i < queryArray.size(); i++) {
   if(queryArray.get(i).toLowerCase().equals("is")){
    startIndex = i+1;
   }
   else if(queryArray.get(i).toLowerCase().equals("?")){
    endIndex = i;

   }
  }
  String[] array = queryArray.toArray(new String[queryArray.size()]);
  return new ArrayList<String>(Arrays.asList(java.util.Arrays.copyOfRange(array, startIndex, endIndex)));

 }

}
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Conversion {

 private static final Character[] NonRepeatingRomanNumerals = {'D', 'L', 'V'};
 private static final Character[] RepeatingRomanNumerals = {'I','V','X','M'};
 private static Map<Character,Integer> RepeatableSymbolsCount = getRepeatableSymbolsCount();

 private static Map<Character,Integer> getRepeatableSymbolsCount(){
  Map<Character,Integer>map = new HashMap<Character,Integer>() {
   {
    put('I', 0);
    put('X', 0);
    put('C', 0);
    put('M', 0);
   }
  };
  return map;
 }

 private static Map<Character,Integer> NonRepeatableSymbolsCount = getNonRepeatableSymbolsCount();

 private static Map<Character,Integer> getNonRepeatableSymbolsCount(){
  Map<Character,Integer>map = new HashMap<Character,Integer>() {
   {
    put('V', 0);
    put('L', 0);
    put('D', 0);
   }
  };
  return map;
 }

 private static Map<Integer, Integer[]> ROMAN_SUBTRACTABLE_MAPPING = Collections.unmodifiableMap(
   new HashMap<Integer, Integer[]>() {
    {
     put(1, new Integer[] {5, 10});
     put(5, new Integer[] {});
     put(10, new Integer[] {50,100});
     put(50, new Integer[] {});
     put(100, new Integer[] {100,1000});
     put(500, new Integer[] {});
     put(1000, new Integer[] {});
    }
   });

 private static Map<Character, Integer> ROMAN_TO_NUMERIC_MAPPING = Collections.unmodifiableMap(
   new HashMap<Character, Integer>() {
    {
     put('I', 1);
     put('V', 5);
     put('X', 10);
     put('L', 50);
     put('C', 100);
     put('D', 500);
     put('M', 1000);
    }
   });

 /**
  * checkSymbolCountValidity() method keeps the count of all repeatable and non repeatable Symbols.
  */
 public static void checkSymbolCountValidity(Character CurrentSymbol){
  if(checkIfSymbolPresent(NonRepeatingRomanNumerals, CurrentSymbol)){
   NonRepeatableSymbolsCount.put(CurrentSymbol, NonRepeatableSymbolsCount.get(CurrentSymbol) + 1);
   if(NonRepeatableSymbolsCount.containsValue(3)){
    System.err.println("Error : Roman Numeral V,L,D cannot be repeated."); 
    System.exit(0);
   }
  }
  else if(checkIfSymbolPresent(RepeatingRomanNumerals, CurrentSymbol)){
   Character keyForValueContainingThree = getKeyForValueContainingThree();
   if(keyForValueContainingThree != '\0'){
    if (CurrentSymbol.equals(keyForValueContainingThree)){
     System.err.println("Error : Roman Numeral "+CurrentSymbol+" cannot repeat 4 times successively");
     System.exit(0);
    }
    else if(CurrentSymbolSmallerThanPrevious(CurrentSymbol, keyForValueContainingThree)) {
     RepeatableSymbolsCount.put(CurrentSymbol, RepeatableSymbolsCount.get(CurrentSymbol) +1);
     RepeatableSymbolsCount.put(keyForValueContainingThree, 0);
    }
   }
   else{
    RepeatableSymbolsCount.put(CurrentSymbol, RepeatableSymbolsCount.get(CurrentSymbol) +1);
   }
  }
 }

 /**
  * getKeyForValueContainingThree() method returns the key corresponding to value 3.
  * This is used while checking the count of Symbols
  * that cannot be repeated more than 3 times.
  * 
  */
 private static char getKeyForValueContainingThree(){
  char key = '\0';
  Iterator<Map.Entry<Character,Integer>> iter = RepeatableSymbolsCount.entrySet().iterator();
  while (iter.hasNext()) {
   Map.Entry<Character,Integer> entry = iter.next();
   if (entry.getValue().equals(3)) {
    return Character.valueOf(entry.getKey());
   }
  }
  return key;
 }

 /**
  * checks if currentSymbol is smaller than the previous one. This rule is applied when repeatable Symbols can
  * occur 4 times only if the 3rd and 4th occurance has a smaller value between them.
  */
 private static boolean CurrentSymbolSmallerThanPrevious(char CurrentSymbol, char keyForValueContainingThree){
  if (ROMAN_TO_NUMERIC_MAPPING.get(CurrentSymbol)> ROMAN_TO_NUMERIC_MAPPING.get(keyForValueContainingThree)){
   System.err.println("Error : Should have been a lesser Roman Numeral next instead of "+CurrentSymbol);
   System.exit(0);
   return false;
  }
  else{
   return true;
  }
 }
 /**
  * Applies the subtaction logic and checks if the symbol is subtractable by the other or not.
  */
 public static float subtractionLogic(Float lastDecimal, Float decimal, Float lastNumber){
  if(Arrays.asList(ROMAN_SUBTRACTABLE_MAPPING.get(Math.round(decimal))).contains(Math.round(lastNumber))){
   return lastDecimal - decimal;
  }
  else
   return lastDecimal + decimal;
 }

 /**
  * Checks if a symbol is present in an array
  */
 public static boolean checkIfSymbolPresent(Character[] array, Character literal){
  boolean result = false;
  for (int i = 0; i < array.length; i++) {
   if(array[i].equals(literal))
    result =  true;
  }
  return result;
 }

 public static void resetSymbolsCounter(){
  RepeatableSymbolsCount = getRepeatableSymbolsCount();
  NonRepeatableSymbolsCount = getNonRepeatableSymbolsCount();

}
}
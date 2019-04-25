//converting Roman into Decimal

public class RomanToDecimal extends Conversion{

 /**
  * Method to convert romannumeric to it's equivalent decimal
  */
 public float romanToDecimal(java.lang.String romanNumber) {

  float decimal = 0;
  float lastNum = 0;
  char[] romanNum = romanNumber.toUpperCase().toCharArray();

  /*
 syntax checker if input is in upper case or lower-- with both, operation can still be performed
 */
  for (int i = romanNum.length- 1; i >= 0 ; i--) {
   Character convertToDecimal = romanNum[i];

   switch (convertToDecimal) {
   case 'M':
    super.checkSymbolCountValidity(convertToDecimal);
    decimal =  processintoDecimal(1000, lastNum, decimal);
    lastNum = 1000;
    break;

   case 'D':
    super.checkSymbolCountValidity(convertToDecimal);
    decimal = processintoDecimal(500, lastNum, decimal);
    lastNum = 500;
    break;

   case 'C':
    super.checkSymbolCountValidity(convertToDecimal);
    decimal =  processintoDecimal(100, lastNum, decimal);
    lastNum = 100;
    break;

   case 'L':
    super.checkSymbolCountValidity(convertToDecimal);
    decimal =  processintoDecimal(50, lastNum, decimal);
    lastNum = 50;
    break;

   case 'X':
    super.checkSymbolCountValidity(convertToDecimal);
    decimal =  processintoDecimal(10, lastNum, decimal);
    lastNum = 10;
    break;

   case 'V':
    super.checkSymbolCountValidity(convertToDecimal);
    decimal =  processintoDecimal(5, lastNum, decimal);
    lastNum= 5;
    break;

   case 'I':
    super.checkSymbolCountValidity(convertToDecimal);
    decimal =   processintoDecimal(1, lastNum, decimal);
    lastNum= 1;
    break;
   }
  }
  super.resetSymbolsCounter();
  return decimal;
 }

 /* 
 processintoDecimal() will called subrationslogic() method and returns the result
  */
 public float processintoDecimal(float decimal, float lastNum, float lastDecimal) {
  if (lastNum > decimal) {
   return super.subtractionLogic(lastDecimal, decimal, lastNum);
  }
  else {
   return lastDecimal + decimal;
  }
 }
}
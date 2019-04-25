public class Galaxymerchanttransactions{
  
 public static void main(String[] args) {
  String filePath = null;
  if (args.length != 0)
   filePath = args[0];
  try{
   InputProcess.ReadFile(filePath); //calling processFile method to read file "input.txt" from input process 
   InputProcess.GalacticcurrencytoIntegerValue(); 
   OutputProcess.processReplyForQuestionask();
  }
  catch(Exception e){
   System.out.println(" File Not Found ");
  
  }
 }
}
 
 


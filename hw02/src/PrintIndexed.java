public class PrintIndexed {
   /**
     * Prints each character of a given string followed by the reverse of its index.
     * Example: printIndexed("hello") -> h4e3l2l1o0
     */
   public static void printIndexed(String s) {
      int last = s.length() - 1;
      for (int i = 0; i < s.length(); i++) {
         IO.print(s.charAt(i));
         IO.print(last - i);
      }
      IO.println();
   }

   public static void main(String[] args) {
      printIndexed("hello");
      printIndexed("cat"); // should print c2a1t0
   }
}
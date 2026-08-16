public class StarTriangleN {
   /**
     * Prints a right-aligned triangle of stars ('*') with N lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle(int N) {
      for (int i = 1; i <= N; i++) {
         for (int j = 1; j <= N - i; j++) {
            IO.print(" ");
         }
         for (int j = 1; j <= i; j++) {
            IO.print("*");
         }
         IO.println();
      }
   }
   
   public static void main(String[] args) {
      starTriangle(7);
   }
}
public class Test {
  static int all = 0, passed = 0;

  static boolean test(boolean applicable, Operator... operators) {
    Allapot allapot = new Allapot();
    //    System.out.println(allapot);

    for (int i = 0; i < operators.length; i++) {
      if (i < operators.length - 1) {
        if (!operators[i].isAlkalmazhato(allapot)) return false;
        allapot = operators[i].alkalmaz(allapot);
      } else {
        return applicable == operators[i].isAlkalmazhato(allapot);
      }
    }
    return true;
  }

  static void test(String condition, boolean applicable, Operator... operators) {
    StringBuilder builder = new StringBuilder();
    all++;
    boolean t = test(applicable, operators);
    if (t) passed++;
    builder.append(t ? "[PASS]" : "[FAIL]").append(" ");
    builder.append(condition);
    System.out.println(builder.toString());
  }

  public static void main(String[] args) {
    
    System.out.println(passed * 100 / all);
  }
}

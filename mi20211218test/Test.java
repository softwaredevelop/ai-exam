package mi20211218test;

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
    test("1. Az s nap nem lehet foglalt", true, new Operator(1, Kikuldetes.ARAD));
    test(
        "1. Az s . nap még nem foglalt",
        false,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(2, Kikuldetes.CSORNA));
    test(
        "2. Az s . napot megelozo napok már foglaltak",
        true,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(8, Kikuldetes.CSORNA));
    test(
        "Az s . napot megelozo napok már foglaltak",
        false,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(12, Kikuldetes.CSORNA));
    test(
        "Az n . helyszínt még nem érintettük",
        true,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(8, Kikuldetes.CSORNA));
    test(
        "Az n . helyszínt még nem érintettük",
        false,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(8, Kikuldetes.ARAD));
    test(
        "Ha Gyort vesszük fel a programba, akkor Csornának már szerepelnie kellett",
        true,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(8, Kikuldetes.CSORNA),
        new Operator(12, Kikuldetes.GYOR));
    test(
        "Ha Gyort vesszük fel a programba, akkor Csornának már szerepelnie kellett",
        false,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(8, Kikuldetes.DOROG),
        new Operator(11, Kikuldetes.GYOR));
    test(
        "Ha Fonyódot vesszük fel a programba, az nem érintheti január 10-ét",
        false,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(8, Kikuldetes.FONYOD));
    test(
        "Ha Fonyódot vesszük fel a programba, akkor annak január 12-ét is érintenie kell",
        true,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(8, Kikuldetes.CSORNA),
        new Operator(12, Kikuldetes.FONYOD));
    test(
        "Ha Fonyódot vesszük fel a programba, akkor annak január 12-ét is érintenie kell",
        false,
        new Operator(1, Kikuldetes.FONYOD));
    test(
        "Ha Egert vesszük fel a programba, akkor annak január 20-át is érintenie kell",
        true,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(8, Kikuldetes.CSORNA),
        new Operator(12, Kikuldetes.GYOR),
        new Operator(14, Kikuldetes.DOROG),
        new Operator(17, Kikuldetes.EGER));
    test(
        "Ha Egert vesszük fel a programba, akkor annak január 20-át is érintenie kell",
        false,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(8, Kikuldetes.CSORNA),
        new Operator(12, Kikuldetes.GYOR),
        new Operator(14, Kikuldetes.EGER));
    test("Dorog nem lehet az elso", false, new Operator(1, Kikuldetes.DOROG));
    test(
        "Ha Bécset vesszük fel a programba, akkor közvetlenül elotte Egernek kell szerepelnie",
        true,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(8, Kikuldetes.CSORNA),
        new Operator(12, Kikuldetes.GYOR),
        new Operator(14, Kikuldetes.DOROG),
        new Operator(17, Kikuldetes.EGER),
        new Operator(22, Kikuldetes.BECS));
    test(
        "Ha Bécset vesszük fel a programba, akkor közvetlenül elotte Egernek kell szerepelnie",
        false,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(8, Kikuldetes.CSORNA),
        new Operator(12, Kikuldetes.GYOR),
        new Operator(14, Kikuldetes.DOROG),
        new Operator(17, Kikuldetes.BECS));
    test(
        "Ha Hevest vesszük fel a programba akkor annak január 9 és 15 közé kell esnie",
        true,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(8, Kikuldetes.CSORNA),
        new Operator(12, Kikuldetes.GYOR),
        new Operator(14, Kikuldetes.HEVES));
    test(
        "Ha Hevest vesszük fel a programba akkor annak január 9 és 15 közé kell esnie",
        false,
        new Operator(1, Kikuldetes.ARAD),
        new Operator(8, Kikuldetes.CSORNA),
        new Operator(12, Kikuldetes.GYOR),
        new Operator(14, Kikuldetes.DOROG),
        new Operator(17, Kikuldetes.HEVES));
    //    test(
    //        "Arad nem lehet az utolsó",
    //        false,
    //        new Operator(1, Kikuldetes.BECS),
    //        new Operator(5, Kikuldetes.CSORNA),
    //        new Operator(9, Kikuldetes.DOROG),
    //        new Operator(12, Kikuldetes.EGER),
    //        new Operator(17, Kikuldetes.FONYOD),
    //        new Operator(22, Kikuldetes.GYOR),
    //        new Operator(24, Kikuldetes.HEVES),
    //        new Operator(25, Kikuldetes.ARAD));
    System.out.println(passed * 100 / all);
  }
}

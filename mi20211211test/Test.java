package mi20211211test;

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
    //    test("az s . sor i . széke még szabad", true, new Operator(1, 1, Nezoter.BALAZS));
    test("az s . sor i . széke még szabad", true, new Operator(3, 1, Nezoter.BALAZS));
    test("az s . sor i . széke még szabad", false, new Operator(1, 2, Nezoter.BALAZS));
    //    test(
    //        "az s . sor i . székét sorfolytonosan követo helyekre még nem ültettünk senkit",
    //        true,
    //        new Operator(2, 4, Nezoter.GABOR),
    //        new Operator(2, 5, Nezoter.BALAZS));
    test(
        "az s . sor i . székét sorfolytonosan követo helyekre még nem ültettünk senkit",
        false,
        new Operator(2, 4, Nezoter.GABOR),
        new Operator(2, 3, Nezoter.BALAZS));
    test(
        "az s . sor i . székét sorfolytonosan követo helyekre még nem ültettünk senkit",
        false,
        new Operator(2, 4, Nezoter.GABOR),
        new Operator(1, 4, Nezoter.BALAZS));
    test(
        "az n nézönek még nem találtunk ülöhelyet",
        false,
        new Operator(2, 4, Nezoter.ANNA),
        new Operator(2, 5, Nezoter.ANNA));
    test(
        "ha tudjuk hol ül Balázs, akkor Annát nem ültetjük vele egy sorba",
        true,
        new Operator(2, 4, Nezoter.ANNA),
        new Operator(3, 1, Nezoter.BALAZS));
    test(
        "ha tudjuk hol ül Balázs, akkor Annát nem ültetjük vele egy sorba",
        false,
        new Operator(2, 3, Nezoter.BALAZS),
        new Operator(2, 4, Nezoter.ANNA));
    test(
        "ha tudjuk hol ül Anna, akkor Balázst nem ültetjük vele egy sorba",
        true,
        new Operator(2, 4, Nezoter.ANNA),
        new Operator(3, 1, Nezoter.BALAZS));
    test(
        "ha tudjuk hol ül Anna, akkor Balázst nem ültetjük vele egy sorba",
        false,
        new Operator(2, 3, Nezoter.ANNA),
        new Operator(2, 4, Nezoter.BALAZS));
    //    test(
    //        "ha tudjuk, hol ül Anna, akkor Csanád csak mellé kerülhet",
    //        true,
    //        new Operator(2, 2, Nezoter.ANNA),
    //        new Operator(2, 3, Nezoter.CSANAD));
    test(
        "ha tudjuk, hol ül Anna, akkor Csanád csak mellé kerülhet",
        false,
        new Operator(2, 5, Nezoter.ANNA),
        new Operator(3, 1, Nezoter.CSANAD));
    test(
        "ha tudjuk, hol ül Eszter, akkor Csanád csak mellé kerülhet",
        true,
        new Operator(2, 3, Nezoter.CSANAD),
        new Operator(2, 4, Nezoter.ESZTER));
    test(
        "ha tudjuk, hol ül Eszter, akkor Csanád csak mellé kerülhet",
        false,
        new Operator(2, 4, Nezoter.ESZTER),
        new Operator(2, 5, Nezoter.GABOR),
        new Operator(3, 1, Nezoter.CSANAD));
    test("Anna nem ülhet a harmadik sorba", false, new Operator(3, 1, Nezoter.ANNA));
    //    test(
    //        "ha tudjuk, hogy hol ül Eszter akkor Ferencet csak elé ültethetjük",
    //        true,
    //        new Operator(2, 4, Nezoter.ESZTER),
    //        new Operator(3, 4, Nezoter.FERENC));
    test("Dezso csak az elso sor szélére ültetheto", true, new Operator(1, 1, Nezoter.DEZSO));
    test("Dezso csak az elso sor szélére ültetheto", true, new Operator(1, 5, Nezoter.DEZSO));
    test("Dezso csak az elso sor szélére ültetheto", false, new Operator(2, 1, Nezoter.DEZSO));
    test("Dezso csak az elso sor szélére ültetheto", false, new Operator(2, 5, Nezoter.DEZSO));
    System.out.println(passed * 100 / all);
  }
}

package mi20211204test;

public class Test {

  static int all = 0, passed = 0;

  static boolean test(boolean applicable, Operator... operators) {
    Allapot allapot = new Allapot();
    System.out.println(allapot);

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
    test(
        "Nem mehet két kurzus ugyanazon időpontra.",
        false,
        new Operator(2, 3, Kurzus.ADATBAZISRENDSZEREK_LABOR),
        new Operator(2, 3, Kurzus.ADATBAZISRENDSZEREK_ELOADAS));
    test(
        "Nem mehet két kurzus ugyanazon időpontra.",
        true,
        new Operator(2, 3, Kurzus.ADATBAZISRENDSZEREK_LABOR),
        new Operator(2, 4, Kurzus.ADATBAZISRENDSZEREK_ELOADAS));

    test(
        "Sorfolytonos a kitöltés",
        false,
        new Operator(2, 3, Kurzus.ADATBAZISRENDSZEREK_LABOR),
        new Operator(1, 3, Kurzus.ADATBAZISRENDSZEREK_ELOADAS));
    test(
        "Sorfolytonos a kitöltés",
        false,
        new Operator(2, 3, Kurzus.ADATBAZISRENDSZEREK_LABOR),
        new Operator(2, 2, Kurzus.ADATBAZISRENDSZEREK_ELOADAS));
    test(
        "Sorfolytonos a kitöltés",
        true,
        new Operator(2, 3, Kurzus.ADATBAZISRENDSZEREK_LABOR),
        new Operator(3, 1, Kurzus.ADATBAZISRENDSZEREK_ELOADAS));
    test(
        "Sorfolytonos a kitöltés",
        true,
        new Operator(2, 3, Kurzus.ADATBAZISRENDSZEREK_LABOR),
        new Operator(2, 4, Kurzus.ADATBAZISRENDSZEREK_ELOADAS));

    test(
        "Egy kurzus csak egyszer szerepelhet",
        false,
        new Operator(2, 3, Kurzus.ADATBAZISRENDSZEREK_LABOR),
        new Operator(2, 4, Kurzus.ADATBAZISRENDSZEREK_LABOR));
    test(
        "Egy kurzus csak egyszer szerepelhet",
        false,
        new Operator(2, 3, Kurzus.ADATBAZISRENDSZEREK_LABOR),
        new Operator(3, 3, Kurzus.ADATBAZISRENDSZEREK_LABOR));

    test(
        "A számítástudomány előadás előzze meg a gyakorlatot",
        true,
        new Operator(2, 1, Kurzus.SZAMITASTUDOMANY_ELOADAS),
        new Operator(2, 4, Kurzus.SZAMITASTUDOMANY_GYAKORLAT));
    test(
        "A számítástudomány előadás előzze meg a gyakorlatot",
        true,
        new Operator(2, 4, Kurzus.SZAMITASTUDOMANY_ELOADAS),
        new Operator(3, 4, Kurzus.SZAMITASTUDOMANY_GYAKORLAT));
    test(
        "A számítástudomány előadás előzze meg a gyakorlatot",
        false,
        new Operator(2, 2, Kurzus.SZAMITASTUDOMANY_GYAKORLAT));

    test(
        "Ha délelőtt van a programozás előadás, legyen délelőtt az adatabázis gyakorlat is",
        true,
        new Operator(1, 1, Kurzus.SZAMITASTUDOMANY_ELOADAS),
        new Operator(2, 1, Kurzus.SZAMITASTUDOMANY_GYAKORLAT),
        new Operator(2, 2, Kurzus.PROGRAMOZAS_ELOADAS),
        new Operator(3, 1, Kurzus.ADATBAZISRENDSZEREK_LABOR));
    test(
        "Ha délelőtt van a programozás előadás, legyen délelőtt az adatabázis gyakorlat is",
        true,
        new Operator(1, 1, Kurzus.SZAMITASTUDOMANY_ELOADAS),
        new Operator(2, 1, Kurzus.SZAMITASTUDOMANY_GYAKORLAT),
        new Operator(2, 5, Kurzus.PROGRAMOZAS_ELOADAS),
        new Operator(3, 1, Kurzus.ADATBAZISRENDSZEREK_LABOR));
    test(
        "Ha délelőtt van a programozás előadás, legyen délelőtt az adatabázis gyakorlat is",
        true,
        new Operator(1, 1, Kurzus.SZAMITASTUDOMANY_ELOADAS),
        new Operator(2, 1, Kurzus.SZAMITASTUDOMANY_GYAKORLAT),
        new Operator(2, 5, Kurzus.PROGRAMOZAS_ELOADAS),
        new Operator(3, 5, Kurzus.ADATBAZISRENDSZEREK_LABOR));
    test(
        "Ha délelőtt van a programozás előadás, legyen délelőtt az adatabázis gyakorlat is",
        false,
        new Operator(1, 1, Kurzus.SZAMITASTUDOMANY_ELOADAS),
        new Operator(2, 1, Kurzus.SZAMITASTUDOMANY_GYAKORLAT),
        new Operator(2, 2, Kurzus.PROGRAMOZAS_ELOADAS),
        new Operator(3, 5, Kurzus.ADATBAZISRENDSZEREK_LABOR));
    test(
        "Ha délelőtt van a programozás előadás, legyen délelőtt az adatabázis gyakorlat is",
        true,
        new Operator(1, 1, Kurzus.SZAMITASTUDOMANY_ELOADAS),
        new Operator(2, 1, Kurzus.ADATBAZISRENDSZEREK_LABOR),
        new Operator(2, 2, Kurzus.PROGRAMOZAS_ELOADAS));
    test(
        "Ha délelőtt van a programozás előadás, legyen délelőtt az adatabázis gyakorlat is",
        true,
        new Operator(1, 1, Kurzus.SZAMITASTUDOMANY_ELOADAS),
        new Operator(2, 1, Kurzus.ADATBAZISRENDSZEREK_LABOR),
        new Operator(2, 5, Kurzus.PROGRAMOZAS_ELOADAS));

    test(
        "A számítástudomány és a programozás ekőadás nem eshet egy napra",
        false,
        new Operator(2, 1, Kurzus.SZAMITASTUDOMANY_ELOADAS),
        new Operator(2, 2, Kurzus.SZAMITASTUDOMANY_GYAKORLAT),
        new Operator(2, 3, Kurzus.PROGRAMOZAS_ELOADAS));
    test(
        "A számítástudomány és a programozás előadás nem eshet egy napra",
        false,
        new Operator(2, 1, Kurzus.SZAMITASTUDOMANY_ELOADAS),
        new Operator(2, 2, Kurzus.SZAMITASTUDOMANY_GYAKORLAT),
        new Operator(2, 3, Kurzus.PROGRAMOZAS_ELOADAS));

    test(
        "A számítástudomány előadás megelőzi a gyakorlatot",
        true,
        new Operator(2, 1, Kurzus.SZAMITASTUDOMANY_ELOADAS),
        new Operator(2, 2, Kurzus.SZAMITASTUDOMANY_GYAKORLAT));
    test(
        "A számítástudomány előadás megelőzi a gyakorlatot",
        false,
        new Operator(2, 1, Kurzus.SZAMITASTUDOMANY_GYAKORLAT));

    test(
        "Legfeljebb egy nap kezdődhet az óra délben",
        false,
        new Operator(2, 3, Kurzus.SZAMITASTUDOMANY_ELOADAS),
        new Operator(3, 3, Kurzus.SZAMITASTUDOMANY_GYAKORLAT));

    test(
        "Hétfőn a 8-as vagy a 16-os időpont szabad",
        false,
        new Operator(1, 1, Kurzus.SZAMITASTUDOMANY_ELOADAS),
        new Operator(1, 5, Kurzus.SZAMITASTUDOMANY_GYAKORLAT));

    test(
        "Programozás labor után közvetlenül óra nem kezdődhet",
        true,
        new Operator(1, 1, Kurzus.PROGRAMOZAS_LABOR),
        new Operator(1, 3, Kurzus.ADATBAZISRENDSZEREK_ELOADAS));
    test(
        "Programozás labor után közvetlenül óra nem kezdődhet",
        false,
        new Operator(1, 1, Kurzus.PROGRAMOZAS_LABOR),
        new Operator(1, 2, Kurzus.ADATBAZISRENDSZEREK_ELOADAS));

    test(
        "Programozás labor után közvetlenül óra nem kezdődhet",
        false,
        new Operator(1, 2, Kurzus.PROGRAMOZAS_ELOADAS));
    test(
        "Programozás labor után közvetlenül óra nem kezdődhet",
        true,
        new Operator(1, 1, Kurzus.ADATBAZISRENDSZEREK_ELOADAS),
        new Operator(1, 2, Kurzus.PROGRAMOZAS_ELOADAS));
    System.out.println(passed * 100 / all);
  }
}

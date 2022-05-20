package mi20211204test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

enum Kurzus {
  SZAMITASTUDOMANY_ELOADAS,

  SZAMITASTUDOMANY_GYAKORLAT,

  PROGRAMOZAS_ELOADAS,

  PROGRAMOZAS_LABOR,

  ADATBAZISRENDSZEREK_ELOADAS,

  ADATBAZISRENDSZEREK_LABOR
}

class Allapot {

  Kurzus[][] a = new Kurzus[6][6];

  public boolean isCel() {
    /* célfeltétel */
    int count = 0;
    for (int p : Operator.ANY) {
      for (int q : Operator.ANY) {
        if (a[p][q] != null) {
          count++;
        }
      }
    }
    return count == Kurzus.values().length;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    for (int i = 1; i <= 5; i++) {
      for (int n = 1; n <= 5; n++) {
        String kurzus = a[n][i] == null ? "-" : a[n][i].name();
        while (kurzus.length() < 30) kurzus = kurzus + " ";
        builder.append(kurzus).append("   ");
      }
      builder.append("\n");
    }

    return builder.toString();
  }
}

class Operator {

  public static final int[] ANY = new int[] {1, 2, 3, 4, 5};
  final int n;
  final int i;
  final Kurzus k;

  Operator(int n, int i, Kurzus k) {
    this.n = n;
    this.i = i;
    this.k = k;
  }

  public boolean isAlkalmazhato(Allapot allapot) {
    final Kurzus[][] a = allapot.a;
    //      az idopontokat - 8, 10, 12, 14 és 16 óra - rendre 1-tol 5-ig számozzuk
    // 1. az n . nap i . idopontjában még nem vettünk fel kurzust
    boolean feladat1 = a[n][i] == null;

    // 2. az n . nap i . idopontjánál késobb még nem vettünk fel kurzust (sorfolytonos a kitöltés)
    boolean feladat2 = true;
    for (int p : ANY) {
      for (int q : ANY) {
        feladat2 &= !(p > n || (p == n && q > i)) || a[p][q] == null;
        //        feladat2 &= (p > n || (p == n && q > i)) || a[p][q] == null;
        //        feladat2 &= (p <= n && (p != n && q <= i)) || a[p][q] == null;
      }
    }

    // 3. a k kurzus még nem szerepl az órarendben
    boolean feladat3 = true;
    for (int p : ANY) {
      for (int q : ANY) {
        feladat3 &= a[p][q] != k;
      }
    }

    // 4. a számítástudomány eloadásnak korábbi idopontra kell kerülnie mint a számítástudomány
    // gyakorlat
    boolean feladat4b = false;
    for (int p : ANY) {
      for (int q : ANY) {
        feladat4b |= a[p][q] == Kurzus.SZAMITASTUDOMANY_ELOADAS;
      }
    }
    boolean feladat4 = !(k == Kurzus.SZAMITASTUDOMANY_GYAKORLAT) || feladat4b;

    // 5. ha a programozás eloadás délel®ttre esik, akkor az adatbázis gyakorlatnak is valamelyik
    // nap délelottjén kell lennie
    boolean feladat5b_1 = true;
    for (int p : ANY) {
      for (int q : ANY) {
        feladat5b_1 &= !(a[p][q] == Kurzus.ADATBAZISRENDSZEREK_LABOR) || q < 3;
      }
    }
    boolean feladat5_1 = !(k == Kurzus.SZAMITASTUDOMANY_ELOADAS && i < 3) || feladat5b_1;
    boolean feladat5b_2 = true;
    for (int p : ANY) {
      for (int q : ANY) {
        feladat5b_2 &= !(a[p][q] == Kurzus.PROGRAMOZAS_ELOADAS && q < 3) || i < 3;
      }
    }
    boolean feladat5_2 = !(k == Kurzus.ADATBAZISRENDSZEREK_LABOR) || feladat5b_2;
    boolean feladat5 = feladat5_1 && feladat5_2;

    // 7. egyik nap sem lehet négynél több tanóra
    boolean feladat7 = false;
    for (int q : ANY) {
      feladat7 |= a[n][q] == null && i != q;
    }

    // 8. a számítástudomány és a programozás tárgyak eloadásai nem eshetnek egy napra
    boolean feladat8b_1 = true;
    for (int q : ANY) {
      feladat8b_1 &= a[n][q] != Kurzus.PROGRAMOZAS_ELOADAS;
    }
    boolean feladat8_1 = !(k == Kurzus.SZAMITASTUDOMANY_ELOADAS) || feladat8b_1;
    boolean feladat8b_2 = true;
    for (int q : ANY) {
      feladat8b_2 &= a[n][q] != Kurzus.SZAMITASTUDOMANY_ELOADAS;
    }
    boolean feladat8_2 = !(k == Kurzus.PROGRAMOZAS_ELOADAS) || feladat8b_2;
    boolean feladat8 = feladat8_1 && feladat8_2;

    // 9. legfeljebb egy nap kezdodhet óra délben
    boolean feladat9b = true;
    for (int p : ANY) {
      feladat9b &= a[p][3] == null;
    }
    boolean feladat9 = !(i == 3) || feladat9b;

    // 10. hétfon reggel 8 és délután 16-os idopontok legalább egyike szabad legyen
    boolean feladat10 = !(n == 1 && i == 5) || a[1][1] == null;

    // 11. a programozás labor után közvetlenül másik óra nem kezdodhet
    boolean feladat11 = true;
    for (int q : ANY) {
      feladat11 &= !(q + 1 == i) || a[n][q] != Kurzus.PROGRAMOZAS_LABOR;
    }

    //    12. a programozás eloadás elott (aznap) kell még legyen tanóra
    boolean feladat12b = false;
    for (int q : ANY) {
      feladat12b |= q < i && a[n][q] != null;
    }
    boolean feladat12 = !(k == Kurzus.PROGRAMOZAS_ELOADAS) || feladat12b;

    return feladat1 && feladat2 && feladat3 && feladat4 && feladat5 && feladat7 && feladat8
        && feladat9 && feladat10 && feladat11 && feladat12;
  }

  public Allapot alkalmaz(Allapot allapot) {
    Allapot newAllapot = new Allapot();
    final Kurzus[][] a = allapot.a;
    final Kurzus[][] b = newAllapot.a;
    for (int n : ANY) for (int i : ANY) b[n][i] = n == this.n && i == this.i ? k : a[n][i];
    return newAllapot;
  }
}

class SzelessegiKereso {

  private final Allapot start;

  private final List<Operator> operatorok;

  public SzelessegiKereso(Allapot start, List<Operator> operatorok) {
    this.start = start;
    this.operatorok = operatorok;
  }

  public SzelessegiKereso.Megoldas kereses() {
    LinkedList<Csomopont> nyiltak = new LinkedList<>();

    nyiltak.add(new Csomopont(start, null, null));
    while (true) {
      if (nyiltak.isEmpty()) return null;
      Csomopont kivalasztott = nyiltak.removeFirst();
      if (kivalasztott.allapot.isCel())
        return new SzelessegiKereso.Megoldas(kivalasztott.allapot, kivalasztott.eleres());
      else nyiltak.addAll(kivalasztott.kovetok());
    }
  }

  public static class Megoldas {
    Allapot allapot;
    List<Operator> access;

    Megoldas(Allapot allapot, List<Operator> access) {
      this.allapot = allapot;
      this.access = access;
    }
  }

  private class Csomopont {

    final Allapot allapot;

    final Csomopont szulo;

    final Operator eloallito;

    Csomopont(Allapot allapot, Csomopont szulo, Operator eloallito) {
      this.allapot = allapot;
      this.szulo = szulo;
      this.eloallito = eloallito;
    }

    List<Csomopont> kovetok() {
      List<Csomopont> kovetok = new ArrayList<>();
      for (Operator operator : operatorok) {
        if (operator.isAlkalmazhato(allapot)) {
          kovetok.add(new Csomopont(operator.alkalmaz(allapot), this, operator));
        }
      }
      return kovetok;
    }

    List<Operator> eleres() {
      LinkedList<Operator> eleres = new LinkedList<>();
      for (Csomopont node = this; node.szulo != null; node = node.szulo)
        eleres.addFirst(node.eloallito);
      return eleres;
    }
  }
}

public class Problem {

  public static void main(String[] args) {
    List<Operator> operatorok = new ArrayList<>();

    operatorok.add(new Operator(2, 1, Kurzus.ADATBAZISRENDSZEREK_ELOADAS));
    operatorok.add(new Operator(3, 2, Kurzus.PROGRAMOZAS_ELOADAS));
    operatorok.add(new Operator(3, 1, Kurzus.SZAMITASTUDOMANY_ELOADAS));

    operatorok.add(new Operator(4, 2, Kurzus.ADATBAZISRENDSZEREK_LABOR));
    operatorok.add(new Operator(4, 3, Kurzus.ADATBAZISRENDSZEREK_LABOR));

    operatorok.add(new Operator(2, 1, Kurzus.PROGRAMOZAS_LABOR));
    operatorok.add(new Operator(2, 2, Kurzus.PROGRAMOZAS_LABOR));

    operatorok.add(new Operator(4, 1, Kurzus.SZAMITASTUDOMANY_GYAKORLAT));
    operatorok.add(new Operator(4, 2, Kurzus.SZAMITASTUDOMANY_GYAKORLAT));

    SzelessegiKereso.Megoldas megoldas = new SzelessegiKereso(new Allapot(), operatorok).kereses();
    if (megoldas != null) System.out.println(megoldas.allapot);
    else System.out.println("Nincs megoldás");
  }
}

package mi20211211test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

enum Nezoter {
  ANNA,

  BALAZS,

  CSANAD,

  DEZSO,

  ESZTER,

  FERENC,

  GABOR,

  FOGLALT
}

class Allapot {
  Nezoter[][] a = new Nezoter[4][6];

  Allapot() {
    this.a[1][2] = Nezoter.FOGLALT;
    this.a[1][3] = Nezoter.FOGLALT;
    this.a[2][1] = Nezoter.FOGLALT;
  }

  public boolean isCel() {
    int c = 0;
    for (int p : Operator.ROW) {
      for (int q : Operator.COL) {
        if (a[p][q] != null || a[p][q] != Nezoter.FOGLALT) {
          c++;
        }
      }
    }
    return c - 1 == Nezoter.values().length;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    // for (int s = 1; s <= 3; s++) {
    // for (int o = 1; o <= 5; s++) {
    for (int s : Operator.ROW) {
      for (int o : Operator.COL) {
        String szek = a[s][o] == null ? "-" : a[s][o].name();
        while (szek.length() < 60) szek = szek + " ";
        builder.append(szek).append("   ");
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}

class Operator {

  public static final int[] ROW = new int[] {1, 2, 3};
  public static final int[] COL = new int[] {1, 2, 3, 4, 5};
  final int s;
  final int i;
  final Nezoter n;

  Operator(int s, int i, Nezoter n) {
    this.s = s;
    this.i = i;
    this.n = n;
  }

  public boolean isAlkalmazhato(Allapot allapot) {
    final Nezoter[][] a = allapot.a;

    // 1. az s . sor i . széke még szabad
    boolean feladat1 = a[s][i] == null;

    // 2. az s . sor i . székét sorfolytonosan követo helyekre még nem ültettünk senkit
    boolean feladat2 = true;
    for (int p : ROW) {
      for (int q : COL) {
        feladat2 &= !(p > s || (p == s && q > i)) || a[p][q] == Nezoter.FOGLALT || a[p][q] == null;
      }
    }

    // 3. az n nézönek még nem találtunk ülöhelyet
    boolean feladat3 = true;
    for (int p : ROW) {
      for (int q : COL) {
        feladat3 &= a[p][q] != n;
      }
    }

    // 4. ha tudjuk hol ül Balázs, akkor Annát nem ültetjük vele egy sorba
    boolean feladat4 = true;
    for (int p : ROW) {
      for (int q : COL) {
        feladat4 &= !(a[p][q] == Nezoter.BALAZS && n == Nezoter.ANNA) || p != s;
      }
    }

    // 5. ha tudjuk hol ül Anna, akkor Balázst nem ültetjük vele egy sorba
    boolean feladat5 = true;
    for (int p : ROW) {
      for (int q : COL) {
        feladat5 &= !(a[p][q] == Nezoter.ANNA && n == Nezoter.BALAZS) || p != s;
      }
    }

    // 6. ha tudjuk, hol ül Anna, akkor Csanád csak mellé kerülhet
    boolean feladat6 = true;
    for (int p : ROW) {
      for (int q : COL) {
        feladat6 &=
            !(a[p][q] == Nezoter.ANNA && n == Nezoter.CSANAD) || p == s && Math.abs(q - i) == 1;
      }
    }

    // 7. ha tudjuk, hol ül Csanád, akkor Anna csak mellé kerülhet
    boolean feladat7 = true;
    for (int p : ROW) {
      for (int q : COL) {
        feladat7 &=
            !(a[p][q] == Nezoter.CSANAD && n == Nezoter.ANNA) || p == s && Math.abs(q - i) == 1;
      }
    }

    // 8. ha tudjuk, hol ül Eszter, akkor Csanád csak mellé kerülhet
    boolean feladat8 = true;
    for (int p : ROW) {
      for (int q : COL) {
        feladat8 &=
            !(a[p][q] == Nezoter.ESZTER && n == Nezoter.CSANAD) || p == s && Math.abs(q - i) == 1;
      }
    }

    // 9. ha tudjuk, hol ül Csanád, akkor Eszter csak mellé kerülhet
    boolean feladat9 = true;
    for (int p : ROW) {
      for (int q : COL) {
        feladat9 &=
            !(a[p][q] == Nezoter.CSANAD && n == Nezoter.ESZTER) || p == s && Math.abs(q - i) == 1;
      }
    }

    // 10. Anna nem ülhet a harmadik sorba
    boolean feladat10 = !(n == Nezoter.ANNA) || s != 3;

    // 11. ha tudjuk, hogy hol ül Eszter akkor Ferencet csak elé ültethetjük
    boolean feladat11 = true;
    for (int p : ROW) {
      for (int q : COL) {
        feladat11 &= !(a[p][q] == Nezoter.ESZTER && n == Nezoter.FERENC) || q == i && p == s + 1;
      }
    }

    // 12. ha tudjuk, hogy hol ül Fernec akkor Esztert csak mögé ültethetjük
    boolean feladat12 = true;
    for (int p : ROW) {
      for (int q : COL) {
        feladat12 &= !(a[p][q] == Nezoter.FERENC && n == Nezoter.ESZTER) || q == i && p == s - 1;
      }
    }

    // 13. ha tudjuk, hogy hol ül Balázs akkor Gábort csak elé ültethetjük
    boolean feladat13 = true;
    for (int p : ROW) {
      for (int q : COL) {
        feladat13 &= !(a[p][q] == Nezoter.BALAZS && n == Nezoter.GABOR) || q == i && p == s + 1;
      }
    }

    // 14. ha tudjuk, hogy hol ül Gábor akkor Balázst csak mögé ültethetjük
    boolean feladat14 = true;
    for (int p : ROW) {
      for (int q : COL) {
        feladat14 &= !(a[p][q] == Nezoter.GABOR && n == Nezoter.BALAZS) || q == i && p == s - 1;
      }
    }

    // 15. Dezso csak az elso sor szélére ültetheto
    boolean feladat15 = !(n == Nezoter.DEZSO) || s == 1 && (i == 1 || i == 5);

    // 16. a harmadik sor székére csak akkor ültethetünk valakit, ha elötte legalább egy szék üresen
    // maradt
    boolean feladat16b = false;
    for (int j : ROW) {
      feladat16b |= j < i && a[j][i] == null;
    }
    boolean feladat16 = s == 3 || feladat16b;

    return feladat1 && feladat2 && feladat3 && feladat4 && feladat5 && feladat6 && feladat7
        && feladat8 && feladat9 && feladat10 && feladat11 && feladat12 && feladat13 && feladat14
        && feladat15 && feladat16;
  }

  public Allapot alkalmaz(Allapot allapot) {
    Allapot ujAllapot = new Allapot();
    final Nezoter[][] a = allapot.a;
    final Nezoter[][] ujA = ujAllapot.a;
    for (int s : ROW) {
      for (int i : COL) {
        ujA[s][i] = s == this.s && i == this.i ? n : a[s][i];
      }
    }
    return ujAllapot;
  }
}

class SzellessegiKereso {
  private final Allapot start;
  private final List<Operator> operatorok;

  public SzellessegiKereso(Allapot start, List<Operator> operatorok) {
    this.start = start;
    this.operatorok = operatorok;
  }

  public SzellessegiKereso.Megoldas kereses() {
    LinkedList<Csomopont> nyiltak = new LinkedList<>();
    nyiltak.add(new Csomopont(start, null, null));
    while (true) {
      if (nyiltak.isEmpty()) {
        return null;
      }
      Csomopont kivalasztott = nyiltak.removeFirst();
      if (kivalasztott.allapot.isCel()) {
        return new SzellessegiKereso.Megoldas(kivalasztott.allapot, kivalasztott.eleres());
      } else {
        nyiltak.addAll(kivalasztott.kovetok());
      }
    }
  }

  public static class Megoldas {
    Allapot allapot;
    List<Operator> accesss;

    Megoldas(Allapot allapot, List<Operator> accesss) {
      this.allapot = allapot;
      this.accesss = accesss;
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
      for (Csomopont csomopont = this; csomopont != null; csomopont = csomopont.szulo) {
        eleres.addFirst(csomopont.eloallito);
      }
      return eleres;
    }
  }
}

public class Problem {

  public static void main(String[] args) {
    List<Operator> operatorok = new ArrayList<>();

    operatorok.add(new Operator(2, 4, Nezoter.GABOR));
    operatorok.add(new Operator(2, 5, Nezoter.BALAZS));
    operatorok.add(new Operator(2, 4, Nezoter.ANNA));
    operatorok.add(new Operator(3, 1, Nezoter.BALAZS));

    SzellessegiKereso.Megoldas megoldas =
        new SzellessegiKereso(new Allapot(), operatorok).kereses();
    if (megoldas != null) {
      System.out.println(megoldas.allapot);
      System.out.println(megoldas.accesss);
    } else {
      System.out.println("Nincs megoldas");
    }
  }
}

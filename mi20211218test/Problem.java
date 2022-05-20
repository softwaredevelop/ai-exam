package mi20211218test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

enum Kikuldetes {
  ARAD(7),
  BECS(4),
  CSORNA(4),
  DOROG(3),
  EGER(5),
  FONYOD(5),
  GYOR(2),
  HEVES(1);

  final int hossz;

  Kikuldetes(int hossz) {
    this.hossz = hossz;
  }
}

class Allapot {

  Kikuldetes[] a = new Kikuldetes[32];

  Allapot() {}

  public boolean isCel() {
    /* célfeltétel */
    int c = 0;
    for (int i : Operator.ANY) {
      if (a[i] != null) {
        c++;
      }
    }
    return c == Kikuldetes.values().length;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = 1; i <= 31; i++) builder.append(i).append(".\t").append(a[i]).append("\n");
    return builder.toString();
  }
}

class Operator {

  public static final int[] ANY =
      new int[] {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
        26, 27, 28, 29, 30, 31
      };
  final int s;
  final Kikuldetes n;

  Operator(int s, Kikuldetes n) {
    this.s = s;
    this.n = n;
  }

  public static int h(Kikuldetes kikuldetes) {
    return kikuldetes.hossz;
  }

  public boolean isAlkalmazhato(Allapot allapot) {
    final Kikuldetes[] a = allapot.a;
    /* operátoralkalmazási előfeltétel */

    // 1. Az s . nap még nem foglalt.
    boolean feladat1 = a[s] == null;

    // 2. Az s . napot megelozo napok már foglaltak (sorfolytonos kitöltés).
    boolean feladat2 = true;
    for (int i : ANY) {
      feladat2 &= !(i < s) || a[i] != null;
    }

    // 3. Az n . helyszínt még nem érintettük.
    boolean feladat3 = true;
    for (int i : ANY) {
      feladat3 &= a[i] != n;
    }

    // 4. Ha Gyort vesszük fel a programba, akkor Csornának már szerepelnie kellett.
    boolean feladat4b = false;
    for (int i : ANY) {
      feladat4b |= i < s && a[i] == Kikuldetes.CSORNA;
    }
    boolean feladat4 = !(n == Kikuldetes.GYOR) || feladat4b;

    // 5. Ha Fonyódot vesszük fel a programba, az nem érintheti január 10-ét
    boolean feladat5b = s > 10 || 10 >= s + h(n);
    boolean feladat5 = !(n == Kikuldetes.FONYOD) || feladat5b;

    // 6. Ha Fonyódot vesszük fel a programba, akkor annak január 12-ét is érintenie kell
    boolean feladat6b = s <= 12 && 12 < s + h(n);
    boolean feladat6 = !(n == Kikuldetes.FONYOD) || feladat6b;

    // 7. Ha Egert vesszük fel a programba, akkor annak január 20-át is érintenie kell
    boolean feladat7b = s <= 20 && 20 < s + h(n);
    boolean feladat7 = !(n == Kikuldetes.EGER) || feladat7b;

    // 8. Dorog nem lehet az elso
    boolean feladat8 = !(n == Kikuldetes.DOROG) || s != 1;

    // 9. Ha Bécset vesszük fel a programba, akkor közvetlenül elotte Egernek kell szerepelnie
    boolean feladat9b = false;
    for (int i : ANY) {
      feladat9b |= i == s - 1 && a[i] == Kikuldetes.EGER;
    }
    boolean feladat9 = !(n == Kikuldetes.BECS) || feladat9b;

    // 10. Ha Hevest vesszük fel a programba akkor annak január 9 és 15 közé kell esnie
    boolean feladat10b = s == 9 || s == 10 || s == 11 || s == 12 || s == 13 || s == 14 || s == 15;
    boolean feladat10 = !(n == Kikuldetes.HEVES) || feladat10b;

    // 11. Arad nem lehet az utolsó
    boolean feladat11b = s + h(n) < 32;
    boolean feladat11 = !(n == Kikuldetes.ARAD) || feladat11b;

    return feladat1 && feladat2 && feladat3 && feladat4 && feladat5 && feladat6 && feladat7
        && feladat8 && feladat9 && feladat10 && feladat11;
  }

  public Allapot alkalmaz(Allapot allapot) {
    Allapot uj = new Allapot();
    final Kikuldetes[] a = allapot.a;
    final Kikuldetes[] b = uj.a;
    for (int i = 1; i <= 31; i++) b[i] = s <= i && i < s + h(n) ? this.n : a[i];
    return uj;
  }
}

class MelysegiKereso {

  private final Allapot start;

  private final List<Operator> operatorok;

  public MelysegiKereso(Allapot start, List<Operator> operatorok) {
    this.start = start;
    this.operatorok = operatorok;
  }

  public MelysegiKereso.Megoldas kereses() {
    LinkedList<Csomopont> nyiltak = new LinkedList<>();

    nyiltak.add(new Csomopont(start, null, null));
    while (true) {
      if (nyiltak.isEmpty()) return null;
      Csomopont kivalasztott = nyiltak.removeLast();
      if (kivalasztott.allapot.isCel()) {
        return new MelysegiKereso.Megoldas(kivalasztott.allapot, kivalasztott.eleres());
      } else {
        nyiltak.addAll(kivalasztott.kovetok());
      }
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

    for (int i = 1; i <= 31; i++)
      for (Kikuldetes kikuldetes : Kikuldetes.values()) operatorok.add(new Operator(i, kikuldetes));

    MelysegiKereso.Megoldas megoldas = new MelysegiKereso(new Allapot(), operatorok).kereses();
    if (megoldas != null) System.out.println(megoldas.allapot);
    else System.out.println("Nincs megoldás");
  }
}

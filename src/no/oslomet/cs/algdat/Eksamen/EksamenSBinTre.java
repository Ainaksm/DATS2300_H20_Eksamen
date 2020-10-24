package no.oslomet.cs.algdat.Eksamen;


import java.util.*;

public class EksamenSBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    /*
    Basert på Programkode 5.2.3 a) i kompendie, slik oppgave 1. ber om.
     */
    public boolean leggInn(T verdi) {

        Objects.requireNonNull(verdi, "Nullverdier er ulovlig!");

        Node<T> p = rot;    // p starter i roten
        Node<T> q = null;
        int cmp = 0;    // hjelpevariabel

        while (p != null) {     // Fortsetter til p er ute av treet
            q = p;      // q er forelder til p
            cmp = comp.compare(verdi, p.verdi);     // Bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;      // Flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<>(verdi, q);      // Oppretter ny node

        if (q == null) {
            rot = p;        // p bilr rotnode
        }
        else if (cmp < 0) {
            q.venstre = p;      // venstre barn til q
        }
        else {
            q.høyre = p;        // høyre barn til q
        }

        antall++;       // én verdi mer i treet
        return true;        // vellykket innlegging

    }

    /*
    Basert på Programkode 5.2.8 fra kompendie, slik oppgave 6 forelår
     */
    public boolean fjern(T verdi) {
        /*
        Gjør de endringene som trengs for at pekeren forelder får korrekt verdi i alle noder etter en fjerning.
         */

        if (verdi == null) {        // Treet har ingen null verdier
            return false;
        }

        // q skal være forelder til p
        Node<T> p = rot;
        Node<T> q = null;

        while (p != null) {     // leter etter verdi
            int cmp = comp.compare(verdi, p.verdi);     // sammenlikner
            if (cmp < 0) {
                q = p;
                p = p.venstre;      // går til venstre
            }
            else if (cmp > 0) {
                q = p;
                p = p.høyre;        // går til høyre
            }
            else {
                break;      // den søkte verdien ligger i p
            }
        }

        if (p == null) {
            return false;       // finner ikke verdi
        }


        if (p.venstre == null || p.høyre == null) {     // Tilfelle 1) og 2)
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;        // b for barn
            if (p == rot) {
                rot = b;

                if (b != null) {
                    b.forelder = null;
                }
            }
            else if (p == q.venstre) {
                q.venstre = b;

                if (b != null) {
                    b.forelder = q;
                }
            }
            else {
                q.høyre = b;

                if (b != null) {
                    b.forelder = q;
                }
            }
        }
        else {      // Tilfelle 3)
            Node<T> s = p;
            Node<T> r = p.høyre;        // finner neste i inorden

            while (r.venstre != null) {
                s = r;      // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;      // kopierer verdien i r til p

            if (s != p) {
                s.venstre = r.høyre;

                if (r.høyre != null) {
                    r.høyre.forelder = s;
                }
            }
            else {
                s.høyre = r.høyre;

                if (r.høyre != null) {
                    r.høyre.forelder = s;
                }
            }
        }

        antall--;       // Det er nå én node mindre i treet
        return true;
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        /*
        Den skal fjerne alle forekomstene av verdi i treet.
        Husk at duplikater er tillatt. Dermed kan en og samme verdi ligge flere steder i treet.
        Metoden skal returnere antallet som ble fjernet.
        Hvis treet er tomt, skal 0 returneres.
         */

        int teller = 0;

        while (fjern(verdi)) {
            teller++;
        }

        return teller;
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {

        // Verdi er ikke i treet
        if (verdi == null) {
            return 0;
        }

        int antall = 0;     // Oppstartsverdi

        Node<T> p = rot;    // p starter i roten

        while (p != null) {

            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) {
                p = p.venstre;      // går til venstre
            }
            else if (cmp > 0) {
                p = p.høyre;        // går til høyre
            }
            else {
                antall++;
                p = p.høyre;
            }

        }

        return antall;

        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void nullstill() {
        /*
        Den skal traversere (rekursivt eller iterativt) treet i en eller annen rekkefølge
        og sørge for at samtlige pekere og nodeverdier i treet blir nullet.
        Det er med andre ord ikke tilstrekkelig å sette rot til null og antall til 0.
         */

        // nullstiller med hjelpemetode
        // tømmer treet
        rot = null;
        antall = 0;
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    // Privat hjelpemetode for nullstill() for rekutrsiv traversering
    private void nullstillRekursivt(Node<T> p) {

        // går mot venstre
        if (p.venstre != null) {
            // venstre
            // setter perker til null
        }

        // går mot høyre
        if (p.høyre != null) {
            // høyre
            // setter peker til null
        }

        // nuller verdien
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {

        //p = rot;
        while (true) {
            if (p.venstre != null) {
                p = p.venstre;
            }
            else if (p.høyre != null) {
                p = p.høyre;
            }
            else {
                return p;
            }
        }
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {

        // Hvis p ikke har en forelder (p er rotnoden), så er p den siste i postorden

        // Hvis p er høyre barn til sin forelder f, er forlederen f den neste.

        // Hvis p er venstre barn til sin forelder f

            //Hvis p er enebarn (f.høyre er null), er forelderen f den neste

            //Hvis p ikker er enebarn (dvs. f.høyre er ikke null),
            // så er den neste den noden som kommer først i postorden i subtreet med f.høyre som rot

        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postorden(Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {

        if (p.venstre != null) {
            postordenRecursive(p.venstre, oppgave);
        }

        if (p.høyre != null) {
            postordenRecursive(p.høyre, oppgave);
        }

        oppgave.utførOppgave(p.verdi);
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre

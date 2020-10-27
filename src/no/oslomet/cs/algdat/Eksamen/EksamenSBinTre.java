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
            }
            else {
                s.høyre = r.høyre;
            }

            if (r.høyre != null) {
                r.høyre.forelder = s;
            }
        }

        antall--;       // Det er nå én node mindre i treet
        return true;

    }

    public int fjernAlle(T verdi) {

        int teller = 0;

        while (fjern(verdi)) {
            teller++;
        }

        return teller;

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

    }

    public void nullstill() {

        // nullstiller med hjelpemetode
        if (!tom()) {
            nullstillRekursivt(rot);
        }

        // Treet er tomt
        rot = null;
        antall = 0;

    }

    // Privat hjelpemetode for nullstill() for rekutrsiv traversering
    private void nullstillRekursivt(Node<T> p) {

        // går mot venstre
        if (p.venstre != null) {
            // går rekursivt gjennom venstre subtre
            nullstillRekursivt(p.venstre);
            // setter perker til null
            p.venstre = null;
        }

        // går mot høyre
        if (p.høyre != null) {
            // går rekursivt gjennom høyre subtre
            nullstillRekursivt(p.høyre);
            // setter peker til null
            p.høyre = null;
        }

        // nuller verdien
        p.verdi = null;
    }

    /*
    Basert på Programkode 5.1.7 h).
     */
    private static <T> Node<T> førstePostorden(Node<T> p) {

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

    }

    private static <T> Node<T> nestePostorden(Node<T> p) {

        Node<T> f = p.forelder;   // f er forelder til p

        // Hvis p ikke har en forelder (p er rotnoden), så er p den siste i postorden. Skal returenre null.
        if (f == null) {
            return null;
        }

        // Hvis p er enebarn eller p er høyrebarn til sin forelder f, er forelderen f den neste.
        if (f.høyre == null || p == f.høyre) {
            p = f;
        }
        // hvis ikke så er den neste den noden som kommer først i postoden i subtreet med f.høyre som rot.
        else {
            p = f.høyre;
            while (true) {
                if (p.venstre != null) {
                    p = p.venstre;
                }
                else if (p.høyre != null) {
                    p = p.høyre;
                }
                else break;
            }
        }

        return p;

    }

    public void postorden(Oppgave<? super T> oppgave) {

        // treet er tomt
        if (tom()) {
            return;
        }

        Node<T> p = rot;

        // finner den første noden i postoden
        while (true) {
            if (p.venstre != null) {
                p = p.venstre;
            }
            else if (p.høyre != null) {
                p = p.høyre;
            }
            else {
                break;
            }
        }

        oppgave.utførOppgave(p.verdi);

        // bruker nestePostoden fra oppg. 3, til å traversere treet
        while (true) {
            if (p != null) {
                p = nestePostorden(p);
            }
            else {
                break;
            }

            if (p != null) {
                oppgave.utførOppgave(p.verdi);
            }
        }

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

    }

    /*
    Selve metoden serialize skal være iterativ og må bruke en kø til å traversere treet i nivå orden.
    Arrayet som returneres av serialize skal inneholde verdiene i alle nodene i nivå orden.
     */
    public ArrayList<T> serialize() {

        // Lager liste
        ArrayList<T> liste = new ArrayList<>();

        // tom liste
        if (rot == null) {
            return liste;
        }

        // Lager kø
        ArrayDeque<Node<T>> deque = new ArrayDeque<>();

        // Legger til rotnoden
        deque.addLast(rot);

        while (!deque.isEmpty()) {
            // Ta ut første fra køen
            Node<T> current = deque.removeFirst();

            // Legg til currents to barn
            if (current.venstre != null) {
                deque.addLast(current.venstre);
            }
            if (current.høyre != null) {
                deque.addLast(current.høyre);
            }

        }



        return liste;
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    /*
    Deserialize skal da ta dette arrayet,
    og legge inn alle verdiene (igjen i nivå orden),
    og dermed gjenskape treet.
     */
    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {

        // Ta array fra serialize()

        // legge inn i tre i nivå orden
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre

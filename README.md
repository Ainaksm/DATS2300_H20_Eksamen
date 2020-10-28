# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

# Krav til innlevering

Se oblig-tekst for alle krav, og husk spesielt på følgende:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* git bundle er levert inn
* Hovedklassen ligger i denne path'en i git: src/no/oslomet/cs/algdat/Eksamen/EksamenSBinTre.java
* Ingen debug-utskrifter
* Alle testene i test-programmet kjører og gir null feil (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet


# Beskrivelse av oppgaveløsning (4-8 linjer/setninger per oppgave)

Jeg har brukt git til å dokumentere arbeidet mitt. Jeg har 29 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

Jeg startet med å lagre den opprinnelige utleverte kildekoden i git.

* __Oppgave 1:__ Løste ved å studere Programkode 5.2.3 fra kompendiet, som anbefalt i oppgaveteksten.
For å forstå hvordan den peker på barn og foreldre, tegnet jeg det.
Her ble det viktig å holde styr på hvordan pekerne, _p_ og _q_, flytter seg.
_p_ begynner i roten (toppen av treet), og flyttes nedover. Den flyttes til venstre hvis verdien vi vil legge inn er mindre enn nodens verdi og ellers legges den til høyre.
_q_ skal ligge et nivå over _p_ (den er forelder).
Når _p_ blir null, er _q_ den siste noden som ble passert. Da skal _verdi_ legges inn som barn av _q_ - venstre barn om den er mindre enn _q_ sin verdi og høyre ellers.
* __Oppgave 2:__ Begynte med å lage sjekk for om _verdi_ ikke er i treet.
Instansierte _antall_ og satte _p_ til rot.
Bruker while-løkke for å finne hvor mange ganger en verdi forekommer.
Bruker komparator for å sammenlikne _verdi_ og nodens verdi. 
Går til venstre hvis verdien er mindre enn nodeverdien og høyre ellers.
* __Oppgave 3:__ I `førstePostorden(Node<T> p)` tok jeg utgangspunkt i kompendiet. Målet var å komme seg til noden som er lengst nederst til venstre i treet.
`nestePostorden(Node<T> p)` var en utfordring å se for seg hvordan pekerne skulle flyttes, så denne metoden involverte mye tegning.
Tok utgangspunkt i beskrivelsen i kompendiet, og tok den fra hverandre for å gjøre logikken så enkel å forstå som mulig.
Hvis _p_ er enebarn eller _p_ er høyrebarn til sin forelder _f_, er forelderen _f_ neste node.
Hvis ikke så er den neste noden den noden som kommer først i postoden i subtreet med _f.høyre_ som _rot_. For å finne denne brukes samme logikk som for å finne første postorden.
* __Oppgave 4:__ I `postorden(Oppgave<? super T> oppgave)`, begynner jeg med å sjekke om treet er tomt. 
Deretter finner jeg første noden i postoden, slik jeg har gjort i oppgave 3, og utfører oppgave.
For å gå gjennom resten av treet bruker jeg en while-løkke som lopper over metoden `nestePostorden(Node<T> p)` fra oppgave 3.
Utfører oppgave til slutt i løkka, slik at det blir riktig for traversering av postoden.
I `postordenRecursive(Node<T> p, Oppgave<? super T> oppgave)` går rekursivt til venstre og høre. 
Som nevnt, er det viktig at utførelsen av oppgaven kommer til slutt i postorden. (Huskeregegel for postorden: venstre, høyre, node).
* __Oppgave 5:__ I `serialize()` begynner jeg med å opprette en liste og sjekker om lista er tom.
Oppretter deretter en kø og legger til rotnoden sist i køen.
Bruker en while-løkke til å traversere så lenge køen ikke er tom. Legger treets høyre og venstre barn bak i køen. Til slutt i løkka legges verdi inn i arrayet.
Så returneres lista i nivå orden. 
I `deserialize(ArrayList<K> data, Comparator<? super K> c)` tok jeg utgangspunkt i Programkode 5.2.3 c) fra kompendiet. 
I metoden opprettes det et binært tre, hvor en eksplisitt komparator c for typen K inngår. 
Treet bygges opp ved hjelp av forEach-løkke, der verdiene i arrayet blir lagt inn i treet ved hjelp av leggInn-metoden fra oppgave 1. 
(Operatoren :: brukes som referanse til metoder og kan forenkle kode.)
* __Oppgave 6:__ `fjern(T verdi)` er basert på Programkode 5.2.8 fra kompendiet, slik oppgave 6 foreslår.
Sjekker først at treet ikke har nullverdier.
Søker gjennom treet slik som i oppgave 1, og returnerer _false_ om verdien ikke finnes i treet.
Deretter tar metoden først for seg tilfellene: Hvis (1) Noden _p_ har ingen barn (_p_ er bladnode) eller (2) _p_ har nøyaktig ett barn (venstre eller høyre). 
I denne sjekken sjekkes det først for hvis _p_ er rotnoden og "nuller" _p_ ved å flytte rotreferansen til _b_ og sette forelderen til _b_ til _null_.
Så sjekkes det for venstre eller høyre barn. Hvis _p_ er lik det venstre barnet til _q_, forelderen, flyttes pekeren på venstrebarnet til _b_ og foreldrepekeren til _b_ til _q_. Da vil barn og forledrepekere følge hverandre. For høyrebarn er det speilvendt av høyre. 
Tilfelle (3) har _p_ to barn. Det er _r_ som kommer etter _p_ i inorden og _s_ er forelder til _r_.
Metoden kopierer først verdien i _r_ over i _p_. Så skal _r_ fjernes ved at _s.venstre_ settes lik _r.høyre_. Foreldrepeker kommer med ved at hvis _r.høyre_ ikke er null, så er forelderen dens _s_.
`fjernAlle(T verdi)` bruker en teller for å telle opp verdiene som er fjernet. For å få med tilfeller der det er duplikatverdier brukes en whileløkke. Altså så lenge verdien er i treet vil den fjernes og hver gang den fjernes øker telleren.
I `nullstill()` har jeg valgt rekursiv traversering av treet ved hjelp av en privat hjelpemetode jeg har laget: `nullstillRekursivt(Node<T> p)`. Denne hjelpemetoden traverserer først til venstre og setter pekerne til null, så gjør den det samme til høyre. Så nulles verdi.
I `nullstll()` kalles den rekursive hjelpemetoden hvis treet ikke allerede er tomt. Til slutt settes _rot_ til _null_ og _antall_ til _0_.

# Merknader

* Warnings: Ved et par tilfeller er det brukt norske bokstaver (øæå) i navn og det reagerer InelliJ på, men siden de er laget sånn fra foreleser lar jeg det være. 
I LeggInn(T verdi) brukes aldri returverdien, den kunne vært void, men siden jeg er bedt om å lage metoden slik lar jeg den være. 
_endreinger_ er aldri brukt, siden det ikke er noen iterator. Siden den var ferdigkodet har jeg ikke fjernet den. 
Jeg har ikke brukt metoden _inneholder(T verdi)_, da jeg ikke har funnet behov for den.
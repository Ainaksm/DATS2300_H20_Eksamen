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

Jeg har brukt git til å dokumentere arbeidet mitt. Jeg har 16 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

Jeg startet med å lagre den opprinnelige utleverte kildekoden i git.

* Oppgave 1: Løste ved å studere Programkode 5.2.3 fra kompendiet, som anbefalt i oppgaveteksten.
For å forså hvordan den peker på barn og foreldre, tegnet jeg det.
Her ble det viktig å holde styr på hvordan pekerne, _p_ og _q_, flytter seg.
_p_ begynner i roten (toppen av treet), og flyttes nedeover. Den flyttes til venstre hvis verdien vi vil legge inn er mindre enn nodens verdi og ellers legges den til høyre.
_q_ skal ligge et nivå over _p_ (den er forelder).
Når _p_ blir null, er _q_ den siste noden som ble passert. Da skal _verdi_ legges inn dom barn av _q_, venstre barn om den er mindre enn _qs_ verdi og høyre ellers.
* Oppgave 2: Begynte med å lage sjekk for om _verdi_ ikke er i treet.
Instansierte _antall_ og satte _p_ til rot.
Bruker while-løkke for å finne hvor mange ganger en verdi forekommer.
Bruker komparator for å sammenlikne _verdi_ og nodens verdi. 
Går til venstre hvis verdien er mindre og høyre ellers.
* Oppgave 3: I _førstePostorden(Node<T> p)_ tok jeg utgangspunkt i kompendiet. Målet var å komme seg til noden som er lengst nederst til venstre i treet.
_nestePostorden(Node<T> p)_ var en utfordring å se for seg hvordan perkerne skulle flyttes, så denne metoden innvloverte mye tegning.
Tok utgangspunkt i beskrivelsen i kompendiet, og tok den fra hverandre for å gjøre logikken så enkel å forstå som mulig.
Hvis _p_ er enebarn eller _p_ er høyrebarn til sin forelder _f_, er forelderen _f_ neste node.
Hvis ikke så er den neste noden den noden som kommer først i postoden i subtreet med _f.høyre_ som _rot_.
* Oppgave 4: I _postorden(Oppgave<? super T> oppgave)_, begynner jeg med å sjekke om treet er tomt. 
Deretter finner jeg første noden i postoden, slik jeg har gjort i oppgave 3, og utfører oppgave.
For å gå gjennom resten av treet bruker jeg en while-løkke metoden _nestePostorden(Node<T> p)_ fra oppgave 3.
Utøfer oppgave til slutt i løkka, slik at det blir riktig for traversering av postoden.
I _postordenRecursive(Node<T> p, Oppgave<? super T> oppgave)_ går rekrustivt til venstre og høre. 
Som nevnt, er det viktig at utførelsen av oppgaven kommer til slutt i postorden.
* Oppgave 5:
* Oppgave 6: _fjern(T verdi)_ er basert på Programkode 5.2.8 fra kompendie, slik oppgave 6 forelår.
Sjekker først at treet ikke har nullverdier.
Søker gjennom treet slik som i oppave 1, og returnerer _false_ om verdien ikke finnes i treet.
Først tar metoden for seg tilfellene: Hvis (1) Noden _p_ har ingen barn (_p_ er bladnode) eller (2) _p_ har nøyaktig ett barn (venstre eller høyre). 
I denne sjekken sjekkes det først for hvis _p_ er rotnoden og "nuller" _p_ ved å flytte rotreferansen til _b_ og sette forelderen til _b_ til null.
Så sjekkes det for venstre eller høyre barn. Hvis _p_ er lik det venstre barnet til _q_, forelderen, flyttes pekeren på venstrebarnet itl _b_ og foreldrepekeren til _b_ til _q_. Da vil barn og forledrepekere følge hverandre. For høyrebarn er det speilvendt av høyre. 
Tilfelle (3) har _p_ to barn. Det er _r_ som kommer etter _p_ i inorden og _s_ er forelder til _r_.
Metoden kopierer først verdien i _r_ over i _p_. Så skal _r_ fjernes ved at _s.venstre_ settes lik _r.høyre_. Foreldrepeker kommer med ved at hvis _r.høyre_ ikke er null, så er forelderen dens _s_.
_fjernAlle(T verdi)_ bruker en teller for å telle opp verdiene som er fjernet. For å få med tilfeller der det er duplikatverdier brukes en whileløkke. Altså så lenge verdien er i treet vil den fjernes og hver gang den fjernes øker telleren.
I _nullstill()_ har jeg valgt rekursiv traversering av treet ved hjelp av en privat hjelpemetode jeg har laget: _nullstillRekursivt(Node<T> p)_. Denne hjelpemetoden traverserer først til venstre og setter perkerne til null, så gjør den det samme til høyre. Så nulles verdi.
i _nullstll()_ kalles den rekursive hjelpemtoden hvis treet ikke allerede er tomt. Til slutt settes _rot_ til _null_ og _antall_ til _0_.
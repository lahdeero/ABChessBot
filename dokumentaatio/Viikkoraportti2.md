Viikkoraportti 2 - ABChessbot
========

### Mitä olet tehnyt tällä viikolla

Oon Conffannu gradlea, netbeansia ja junittia. Päässyt onneksi vähän koodaamaankin ja tekemään testejä positioiden generoitinti pitäisi olla pikkuhiljaa kunnossa. Myös alustavat rungot
Evaluator ja Minimax luokista, tarkotus oli tehdä pidemmällekkin mutta positioiden generointi vei oletettua enemmän aikaa.

### Miten ohjelma on edistynyt?

Uusia positioita voi generoida suht luotettavasti, jokunen testi ehkä vielä lisää. Evaluator ja Minimax luokissa rungot, periaatteessa voi käyttää jo muttei mihinkään hyödylliseen, eli ne eivät kerro seuraavaa "oikeaa" siirtoa vielä.

### Mitä opin tällä viikolla / tänään?

Opin paljon debuggausta.
Opin myös että jos teet ikuisen silmukan jossa on System.out.println("") joudut usemman minuutin yrittää taskin pysäyttämistä Netbeansissa.. Tuntuu gradle+netbeans spesifiltä jutulta, en ainakaan muista että ennen olisi ollut näin hankalaa.
Uudestaan opittua: Älä oleta että koodi toimii jollet ole testannut sitä, älä välttämättä siltikään..

### Mikä jäi epäselväksi tai tuottanut vaikeuksia? Vastaa tähän kohtaan rehellisesti, koska saat tarvittaessa apua tämän kohdan perusteella.

En muistanut miten vaikeeta testien debuggaus on gradlen kanssa.

### Mitä teen seuraavaksi?

Liikkumiset generoinnit valmiiksi, minimaxii ja alphabetaa.
Ja tietty testejä edellämainituille.

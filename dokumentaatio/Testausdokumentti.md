Testausdokumentti
========

## Mitä on testattu, miten tämä tehtiin

Käyttöliittymää ja tiedostojen lukemista lukuunottamatta olen koittanut testata kaiken kirjoittamani koodin. Yleensä kirjoitan perustapauksille yksinkertaiset testit, sitten simuloin erilaisia tilanteita ja yritän löytää epäjohdonmukaisuuksia. Onnistuessani(!) kirjoitan uudet testit löydetyille epäjohdonmukaisuuksille/bugeille, yleensä logiikalla alkutilanne -> suorita jokin toiminto -> tarkista että lopputulos on halutunlainen. Aikavaatimuksia on testattu empiirisesti ja
koneelliseti(graafit lopussa), käytännössä viiden siirron päähän laskee ihan vikkelästi ja kuusi on vielä pelattava, tosin simulointiin ja testaukseen hieman hidas.

## Minkälaisilla syötteillä testaus tehtiin 

Algoritmia on testattu pelaamalla sitä vastaan, luomalla arvioilta ongelmallisia positioita, peluuttamalla sitä itseään vastaan ja generoimalla satunnaisia pelitilanteita ja ihmetelty miten se selvytyy niistä. Huomasin projektin edetessä että paras tapa testata on luoda suuria määriä satunnaisia tilanteita ja verrata algoritmin muunnelmilla ja minimaxilla saatuja tuloksia. Näin löytyi hyvin toimintalogiikan bugeja sekä tilanteita joissa algoritmin oli "mahdoton toimia oikein",
kuten shakkimatti tilanteessa. Muutenkin pidin satunnaispositioiden luomista ja niistä laskemista erittäin hyvänä testausmenetelmänä, koska sillä on suuri etu itsekirjoitettujen positioiden tehtyihin teseihin verrattuna. Huomataan että kirjoittamani jUnit testit testaa vain tilanteet jotka olen itse arvioinut ongelmallisiksi, tämä siis kertoo enemmän minusta kun algoritmin toiminnasta eikä näinollen varsinaisesti mittaa algoritmin toimintaa muissa, kun niissä tilanteissa
joihin olen törmännyt.

## Miten testit voidaan toistaa

Itse käytän netbeansia niin 'Run -> Test Project' varmaan helpoin tapa, komentoriviltä 'gradle test'. Edellämainittuihin random testeihin olen luonut Generator-luokkaan metodin generateRandomPosition(int maxPieces) jolla algoritmia voi testata toiseen algoritmiin, käytännössä siis suorittamalla esim seuraava koodi Main -luokassa:

```
       Generator generator = new Generator();
       Position randomPosition = generator.generateRandomPosition(16);
       AlphaBeta ab = new AlphaBeta();
       Position nextPosition = ab.calculateNextPosition(randomPosition, 5, true);
       nextPosition.print();
       System.out.println(ab.value);
```

HUOM: kyseisellä random-generaattorilla ei ole hirveästi tekemistä satunnaisuuden kanssa ja abcb.util.Randomizer on syytä päivittää mieleisekseen.

Paras testaamistapa on silti pelata bottia vastaan.
 
## Ohjelman toiminnan empiirisen testauksen tulosten esittäminen graafisessa muodossa.

Seuraavat graafit olen luonut satunnaisista tilanteista simuloimalla ja laskemalla keskiarvoja.

Suuntaa-antavia mittauksia hakupuun syvyyden suhteen:

[Syvyys/aika suhde](https://github.com/lahdeero/ABChessBot/blob/master/dokumentaatio/depthtimes.png)

Suuntaa-antavia mittauksia lehtisolmujen lukumäärästä syvyyden suhteen:

[Syvyys/lehtisolmu suhde](https://github.com/lahdeero/ABChessBot/blob/master/dokumentaatio/depthtimes.png)




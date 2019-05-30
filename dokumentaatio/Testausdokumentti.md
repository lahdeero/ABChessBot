Testausdokumentti
========

## Mitä on testattu, miten tämä tehtiin

Käyttöliittymää ja tiedostojen lukemista lukuunottamatta olen koittanut testata kaiken kirjoittamani koodin. Yleensä
kirjoitan perustapauksille yksinkertaiset testit, sitten simuloin erilaisia tilanteita ja yritän löytää epäjohdonmukaisuuksia. Onnistuessani(!) kirjoitan uudet testit löydetyille epäjohdonmukaisuuksille/bugeille, yleensä logiikalla alkutilanne -> suorita jokin toiminto -> tarkista että lopputulos on halutunlainen.

## Minkälaisilla syötteillä testaus tehtiin 

Yleensä loin erilaisia Positio -olioita, eli shakkilaudan tiloja. Näitä Positioita voi lukea myös tekstitiedostosta niinkuin muutama testi tekeekin.

## Miten testit voidaan toistaa

Itse käytän netbeansia niin 'Run -> Test Project' varmaan helpoin tapa, komentoriviltä 'gradle test'.

## Ohjelman toiminnan empiirisen testauksen tulosten esittäminen graafisessa muodossa.

<Insert some jacoco picture here>



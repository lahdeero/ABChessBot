Toteutusdokumentti
=========

## Ohjelman yleisrakenne

Aloitustilanteessa pyritään hakemaan siirtoa openings.txt:stä, mikäli mahdollisia siirtoja on useita niin arvotaan niistä joku. Muutaman siirron päästä(kun openings.txt :stä ei löydy enää avaukselle jatkoa) kutsutaan käyttöliittymästä tai komentoriviltä AlphaBeta.java :ssa sijaitsevaa algoritmia. Algoritmi generoi Generator-luokan avulla hakupuun Position-luokan ilmentyminä, jotka lisätään itsetehtyyn MyReplay-tietorakenteeseen(muistuttaa java.util.ArrayListiä). MyReplaystä saadaan näin positiot, joista voidaan laskea alpha- ja beta-arvoja. AB-Agoritmi pyrkii palauttamaan edullisimman tilanteen niin valkoiselle kun mustalle pelaajalle, valkoista pelaajaa kuvataan max-pelaajana joka pyrkii valitsemaan korkeimman saadun luvun(valuen), kun taas musta pelaaja pyrkii mahdollisemman pieneen lukuun. Huomattavin parannus Alpha-Beta algoritmissa Minimaxiin on se ettei läheskään aina tarvitse evaluoida kaikkia mahdollisia positioita, vaan tilanteessa jossa esim min pelaaja voi valita paremman siirron kun jo aiemmin lasketussa haarassa, on kyseisen haaran eteenpäin laskeminen turhaa.

Lisähuomiona että tämän kurssin osuus sisältää vain abcb -paketin alla olevia osia ohjelmasta, shakkibeli -paketin(graafinen käyttöliittymä ja liikkeiden tarkistus) alla olevat osat on pääosin toteutettu aiemmalla kurssilla. Käytännöllisyyden nimissä on kuitenkin parempi pitää nämä samassa projektissa, koska shakkibottia vastaan pelaaminen komentoriviltä on "hieman" kankeaa. 

## Saavutetut aika- ja tilavaativuudet (m.m. O-analyysit pseudokoodista)

Aika- ja tilavaativuudet nousevat eksponentiaalisesti hakupuun syventyessä, jokaisesta solmusta kun lähtee mahdollisten siirtojen verran lapsisolmuja. Alpha-Beta karsinnan avulla voidaan usein karsia puusta "oksia", joita on turha laskea mutta tästäkin huolimatta pahimmassa tapauksessa joudumme käymään kaikki permutaatiot läpi jolloin aikavaatimus on O(leveys^syvyys), eli moneenko siirtoon kustakin pelitilanteesta pääsee potenssiin kuinka monta siirtoa eteenpäin halutaan laskea. 

## Työn mahdolliset puutteet ja parannusehdotukset

Shakkibottia on mahdollista kehittää lähes loputtomiin, suurimpia puutteita lienee botin kyvyttömyys erikoissiirtoihin kuten tornitukseen ja ohisyöntiin. Tämän lisäksi usein törmää tilanteisiin jossa tulee ns. "turha siirto", eli kun selkeää voittavaa linjaa ei löydy jodutaan tyytymään johonkin nollahyöty/-haitta siirtoon. Tämä ongelma yleisesti ratkaistaan nappulakohtaisilla matriiseilla eli heurestiikalla, näin kannustetaan algoritmia pyrkimään tietyn nappulan kohdalla arviolta suotuisempaan positioon laudalla. Esimerkiksi sotilaan kohdalla voitaisiin määritellä että on hieamn suotuisempaa kokonaistilanteen kannalta, mitä lähempänä vastustajan päätyä sotilas on.

## Lähteet

https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
https://www.chessprogramming.org/

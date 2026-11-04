-- 1. Mostrare tutti gli autori ordinati per cognome e poi per nome.
-- 2. Mostrare titolo e anno dei libri pubblicati dopo il 2000.
-- 3. Trovare gli autori italiani.
-- 4. Trovare i libri il cui titolo contiene la parola "x" (senza distinguere maiuscole e minuscole).
-- 5. Elencare le case editrici presenti, senza duplicati.
-- 6. Mostrare i 5 libri più recenti.
-- 7. Mostrare ogni libro con il nome del suo genere.
-- 8. Mostrare ogni libro con nome e cognome dei suoi autori (passando da libro_autore).
-- 9. Trovare tutti i libri scritti da un autore dato il suo cognome.
-- 10. Trovare i libri di genere "Fantasy" che hanno almeno una copia disponibile.
-- 11. Mostrare gli autori che hanno scritto almeno un libro di un certo genere, senza duplicati.

-- 1.
SELECT Autori.cognome,Autori.nome
FROM Autori
ORDER BY cognome,nome;

-- 2.
SELECT Libri.titolo, Libri.anno
FROM Libri
WHERE anno >= "2000-01-01";

-- 3. L'Italia corrisponde all'id 1.
SELECT *
FROM Autori
WHERE fk_nazionalita = 1;

-- 4. 
SELECT *
FROM Libri
WHERE LOWER(Libri.titolo) LIKE LOWER('%a%');

-- 5. 
SELECT DISTINCT nome
FROM CasaEditrice;

-- 6.
SELECT *
FROM Libri
ORDER BY anno DESC
LIMIT 5	;

-- 7.
SELECT Libri.titolo, Generi.nome
FROM Libri
JOIN Generi ON Libri.fk_genere = Generi.idGenere;

-- 8.
SELECT Libri.titolo, Autori.nome,Autori.cognome
FROM Libri
JOIN Libri_Autori ON Libri.isbn = Libri_Autori.fk_isbn
JOIN Autori ON Libri_Autori.fk_autore = Autori.idAutore;

-- 9.
SELECT Libri.titolo, Autori.nome, Autori.cognome
FROM Libri
JOIN Libri_Autori ON Libri.isbn = Libri_Autori.fk_isbn
JOIN Autori ON Libri_Autori.fk_autore = Autori.idAutore
WHERE Autori.cognome LIKE ("Calvino");



-- 10.
SELECT Libri.titolo,Generi.nome Genere
FROM Copie
JOIN Libri ON Copie.fk_isbn = Libri.isbn
JOIN Generi ON Libri.fk_genere = Generi.idGenere
WHERE LOWER(Generi.nome) LIKE LOWER('Fantasy') 
AND numero_copie>=1
GROUP BY numero_copie ASC;

-- 11.
SELECT DISTINCT Libri.titolo, Autori.nome, Autori.cognome, Generi.nome Genere
FROM Autori
JOIN Libri_Autori ON Autori.idAutore = Libri_Autori.fk_autore
JOIN Libri ON Libri_Autori.fk_isbn = Libri.isbn
JOIN Generi ON Libri.fk_genere = Generi.idGenere 
WHERE LOWER(Generi.nome) LIKE LOWER('Fantasy') ;
 
-- Bonus
-- Tutti i libri disponibili.
SELECT Libri.titolo,Copie.numero_copie
FROM Copie
JOIN Libri ON Copie.fk_isbn = Libri.isbn
WHERE numero_copie>=1
ORDER BY numero_copie ASC;

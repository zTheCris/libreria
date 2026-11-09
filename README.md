# Libreria

Applicazione REST sviluppata con Spring Boot per la gestione del catalogo di una biblioteca. Il progetto gestisce libri, autori, copie e generi attraverso API REST, Spring Data JPA e Hibernate.

Le tabelle di supporto, come \`Nazionalita\` e \`CasaEditrice\`, vengono utilizzate per mantenere i dati normalizzati e per associare valori già presenti alle entità principali. Non tutte le tabelle del database richiedono quindi un controller dedicato.

## Funzionalità implementate

- Consultazione, inserimento, modifica ed eliminazione dei libri.
- Ricerca dei libri tramite ISBN e titolo, sia esatta sia parziale.
- Consultazione, inserimento, modifica ed eliminazione degli autori.
- Associazione di uno o più autori a un libro.
- Consultazione e inserimento delle copie di un libro.
- Ricerca delle copie per quantità e casa editrice.
- Consultazione delle copie disponibili e di quelle esaurite.
- Consultazione e gestione dei generi tramite un controller dedicato.
- Verifica dell'esistenza di generi, autori, nazionalità, libri e case editrici prima di creare le relative associazioni.

> La documentazione descrive il comportamento del codice presente nel branch \`master\`. La sezione [Limitazioni e comportamenti da correggere](#limitazioni-e-comportamenti-da-correggere) segnala alcuni endpoint che sono presenti nel codice ma che non funzionano ancora come suggerisce il loro nome.

## Tecnologie utilizzate

- **Java 23**
- **Spring Boot 4.1.2**
- **Spring Web MVC**, per le API REST
- **Spring Data JPA** e **Hibernate**, per l'accesso al database
- **MySQL**, database relazionale
- **Maven**, gestione del progetto e delle dipendenze
- **SpringDoc OpenAPI 3.2.0**, documentazione OpenAPI e Swagger UI
- **Spring Boot DevTools**, strumenti per lo sviluppo
- **Spring Boot Starter Validation**, dipendenza per la validazione

## Struttura del progetto

Il codice applicativo si trova in \`src/main/java/com/example/libreria\`.

\`\`\`text
com.example.libreria
├── LibreriaApplication.java
├── controller/
│   ├── AutoreController.java
│   ├── CopiaController.java
│   ├── GenereController.java
│   └── LibroController.java
├── dto/
│   ├── AutoreDTO.java
│   ├── CasaEditriceDTO.java
│   ├── CopiaDTO.java
│   ├── GenereDTO.java
│   ├── LibroDTO.java
│   └── NazionalitaDTO.java
├── exception/
│   ├── AutoreNotFoundException.java
│   ├── CasaEditriceNotFound.java
│   ├── CopiaNotFoundException.java
│   ├── GenereNotFoundException.java
│   ├── LibroNotFoundException.java
│   └── NazionalitaNotFoundException.java
├── model/
│   ├── Autore.java
│   ├── CasaEditrice.java
│   ├── Copia.java
│   ├── Genere.java
│   ├── Libro.java
│   └── Nazionalita.java
├── repository/
│   ├── AutoreRepository.java
│   ├── CasaEditriceRepository.java
│   ├── CopiaRepository.java
│   ├── GenereRepository.java
│   ├── LibroRepository.java
│   └── NazionalitaRepository.java
├── service/
│   ├── AutoreService.java
│   ├── CopiaService.java
│   ├── GenereService.java
│   └── LibroService.java
└── utility/
    └── Mapper.java
\`\`\`

### Responsabilità dei componenti

- **Controller**: ricevono le richieste HTTP, richiamano i service e costruiscono le risposte.
- **Service**: contengono la logica applicativa, verificano l'esistenza dei dati collegati e coordinano le operazioni sui repository.
- **Repository**: interfacce Spring Data JPA che consentono di interrogare e modificare il database.
- **Model/Entity**: classi JPA che rappresentano le tabelle mappate dall'applicazione.
- **DTO**: oggetti utilizzati nelle risposte per trasferire i dati senza esporre direttamente le entity.
- **Mapper**: converte le entity nei rispettivi DTO e viceversa.

## Modello applicativo e relazioni

### Entità mappate da JPA

| Entità | Tabella | Relazioni e utilizzo |
|---|---|---|
| \`Libro\` | \`Libri\` | Appartiene a un genere e può essere associato a più autori. |
| \`Autore\` | \`Autori\` | Appartiene a una nazionalità e può essere associato a più libri. |
| \`Genere\` | \`Generi\` | Può essere associato a più libri. |
| \`Nazionalita\` | \`Nazionalita\` | Può essere associata a più autori. |
| \`Copia\` | \`Copie\` | Appartiene a un libro e a una casa editrice. |
| \`CasaEditrice\` | \`CasaEditrice\` | Può essere associata a più copie. |

### Relazioni JPA

- **Libro e Genere**: relazione \`ManyToOne\`, tramite \`Libri.fk_genere\`.
- **Autore e Nazionalita**: relazione \`ManyToOne\`, tramite \`Autori.fk_nazionalita\`.
- **Libro e Autore**: relazione \`ManyToMany\`, tramite la tabella ponte \`Libri_Autori\`, con le colonne \`fk_isbn\` e \`fk_autore\`.
- **Copia e Libro**: relazione \`ManyToOne\`, tramite \`Copie.fk_isbn\`.
- **Copia e CasaEditrice**: relazione \`ManyToOne\`, tramite \`Copie.fk_casaeditrice\`.

### Ruolo delle tabelle di supporto

\`Nazionalita\` e \`CasaEditrice\` vengono recuperate dai rispettivi repository quando si crea o modifica un autore o una copia. Non hanno un controller REST dedicato perché, nell'attuale logica applicativa, servono soprattutto a riutilizzare valori già presenti nel database.

\`Genere\`, invece, dispone anche di \`GenereService\` e \`GenereController\`. Il service viene inoltre utilizzato da \`LibroService\` per verificare e associare il genere durante l'inserimento e la modifica di un libro.

### DTO e struttura delle risposte

Il mapper costruisce le risposte annidando i dati collegati:

- \`LibroDTO\` contiene il genere e l'insieme degli autori.
- \`AutoreDTO\` contiene la nazionalità, ma non la lista dei libri.
- \`CopiaDTO\` contiene il libro e la casa editrice.

Questa scelta evita di ripetere la relazione inversa nei DTO e riduce il rischio di ricorsione durante la serializzazione JSON.

Esempio indicativo della struttura di un \`LibroDTO\`:

\`\`\`json
{
  "isbn": 100000001,
  "titolo": "Il Signore degli Anelli",
  "anno": "1954-07-29",
  "prezzo": 25,
  "genere": {
    "idGenere": 1,
    "nome": "Fantasy"
  },
  "autori": [
    {
      "idAutore": 1,
      "nome": "J.R.R.",
      "cognome": "Tolkien",
      "data": "1892-01-03",
      "nazionalita": {
        "idNazionalita": 2,
        "nome": "Inglese"
      }
    }
  ]
}
\`\`\`

## API REST

La base URL locale è \`http://localhost:8080\`. Gli endpoint che ricevono parametri semplici non annotati esplicitamente con \`@RequestParam\` sono documentati come parametri della query, secondo il binding utilizzato da Spring MVC.

### Autori

| Metodo | Endpoint | Descrizione |
|---|---|---|
| GET | \`/autori/all\` | Restituisce tutti gli autori come lista di \`AutoreDTO\`. |
| POST | \`/autori/addAutore\` | Inserisce un autore. |
| PUT | \`/autori/{idAutore}\` | Modifica un autore esistente. |
| DELETE | \`/autori/{idAutore}\` | Elimina un autore. |

Per inserire un autore sono richiesti i parametri \`nome\`, \`cognome\`, \`data\` e \`nazionalita\`. La data deve rispettare il formato ISO \`yyyy-MM-dd\`. La nazionalità deve già esistere nel database.

Esempio:

\`\`\`bash
curl -X POST "http://localhost:8080/autori/addAutore?nome=Primo&cognome=Levi&data=1919-07-31&nazionalita=Italiana"
\`\`\`

Per modificare un autore si passano gli stessi parametri insieme all'identificativo:

\`\`\`bash
curl -X PUT "http://localhost:8080/autori/11?nome=Primo&cognome=Levi&data=1919-07-31&nazionalita=Italiana"
\`\`\`

Il service verifica che l'autore da modificare e la nazionalità indicata esistano. Gli endpoint di scrittura restituiscono attualmente un messaggio testuale.

Non è ancora presente un endpoint per recuperare un singolo autore tramite ID o per cercare gli autori tramite nome e cognome.

### Generi

| Metodo | Endpoint | Descrizione |
|---|---|---|
| GET | \`/generi/all\` | Restituisce la lista dei generi come \`GenereDTO\`. |
| GET | \`/generi/{id}\` | Recupera un genere tramite ID. |
| POST | \`/generi\` | Inserisce un genere. |
| PUT | \`/generi/id\` | Mapping presente nel codice, ma aggiornamento da correggere. |

Per inserire un genere si passa il parametro \`nome\`:

\`\`\`bash
curl -X POST "http://localhost:8080/generi?nome=Fantascienza"
\`\`\`

Il service controlla che il nome non sia vuoto. Non è attualmente presente un endpoint DELETE per i generi.

**Nota:** l'endpoint PUT è definito con \`@PutMapping("/id")\`, mentre il metodo richiede un parametro \`@PathVariable Integer id\`. Il percorso non contiene quindi il segnaposto \`{id}\` necessario. Inoltre, il metodo \`getById\` del service lancia un'eccezione di tipo \`AutoreNotFoundException\` quando il genere non esiste, mentre il controller intercetta \`GenereNotFoundException\`. Questi due punti devono essere corretti.

### Libri

| Metodo | Endpoint | Descrizione |
|---|---|---|
| GET | \`/libri/all\` | Restituisce tutti i libri come lista di \`LibroDTO\`. |
| GET | \`/libri/{isbn}\` | Recupera un libro tramite ISBN. |
| GET | \`/libri/getallbytitle/{titolo}\` | Cerca i libri con titolo contenente il testo specificato, senza distinzione tra maiuscole e minuscole. |
| GET | \`/libri/getbytitle/{titolo}\` | Cerca un libro con titolo esatto. |
| POST | \`/libri/addLibro\` | Inserisce un libro e associa gli autori indicati. |
| PUT | \`/libri/{isbn}\` | Modifica un libro esistente. |
| DELETE | \`/libri/{isbn}\` | Elimina un libro. |

Per l'inserimento sono richiesti i parametri \`isbn\`, \`titolo\`, \`data\`, \`prezzo\` e \`genere\`. Il body JSON contiene un array di ID autore. Il valore \`data\` viene utilizzato per il campo \`anno\` dell'entità \`Libro\`.

Esempio di inserimento:

\`\`\`bash
curl -X POST "http://localhost:8080/libri/addLibro?isbn=100000021&titolo=Nuovo%20libro&data=2026-01-15&prezzo=18&genere=Romanzo" \
  -H "Content-Type: application/json" \
  -d "[7,8]"
\`\`\`

Il service verifica che tutti gli ID autore ricevuti esistano e recupera il genere tramite il nome. Se una delle associazioni richieste non è valida, viene sollevata la relativa eccezione.

La modifica richiede l'ISBN del libro già presente. Il service verifica l'esistenza del libro, recupera il genere, verifica tutti gli autori e salva il libro con i nuovi dati e le nuove associazioni.

L'eliminazione è gestita con una transazione \`@Transactional\`. Prima di eliminare il libro, il service rimuove i riferimenti dalla tabella \`Libri_Autori\` e i record associati in \`Copie\`; infine elimina il libro. In questo modo le operazioni previste dal service vengono eseguite nella stessa transazione.

### Copie

| Metodo | Endpoint | Descrizione |
|---|---|---|
| GET | \`/copie/all\` | Restituisce tutte le copie come lista di \`CopiaDTO\`. |
| GET | \`/copie/allbynumcopie/{num_copie}\` | Cerca i record con un numero di copie esatto. |
| GET | \`/copie/allbycasaeditrice/{id}\` | Cerca i record associati alla casa editrice indicata. |
| GET | \`/copie/getbyisbn/{isbn}\` | Cerca il record di copia associato al libro indicato. |
| GET | \`/copie/disponibili\` | Restituisce i record con \`numeroCopie > 0\`. |
| GET | \`/copie/esaurite\` | Restituisce i record con \`numeroCopie = 0\`. |
| POST | \`/copie\` | Inserisce un nuovo record di copia. |
| PUT | \`/copie/{isbn}\` | Mapping presente, ma al momento richiama la logica di inserimento. |
| DELETE | \`/copie/{isbn}\` | Elimina un record di copia; il valore ricevuto viene usato come ID della copia, non come ISBN. |

Per inserire una copia sono richiesti i parametri \`isbn\`, \`idCasaEditrice\` e \`numeroCopie\`. Il service controlla che il libro e la casa editrice esistano prima di creare il record.

Esempio:

\`\`\`bash
curl -X POST "http://localhost:8080/copie?isbn=100000021&idCasaEditrice=1&numeroCopie=5"
\`\`\`

Il DTO di una copia contiene \`idCopia\`, \`libro\`, \`casaEditrice\` e \`numeroCopie\`. Il libro annidato è un \`LibroDTO\`, quindi include a sua volta genere e autori.

### Esempi dei criteri di ricerca

- \`GET /libri/getallbytitle/signore\`: ricerca parziale del titolo.
- \`GET /libri/getbytitle/Il%20Signore%20degli%20Anelli\`: ricerca esatta del titolo.
- \`GET /copie/allbynumcopie/5\`: record con esattamente cinque copie.
- \`GET /copie/disponibili\`: record con almeno una copia.
- \`GET /copie/esaurite\`: record senza copie disponibili.

## Logica applicativa rilevante

### Associazione dei dati esistenti

I service non creano nuove righe di nazionalità, case editrici o generi quando ricevono i rispettivi nomi o identificativi. Recuperano invece i record già presenti e li associano all'entità principale:

1. Alla creazione o modifica di un autore, viene cercata la nazionalità tramite nome.
2. Alla creazione o modifica di un libro, viene cercato il genere tramite nome e vengono recuperati tutti gli autori tramite i loro ID.
3. Alla creazione di una copia, vengono recuperati il libro tramite ISBN e la casa editrice tramite ID.

Questo approccio evita di duplicare stringhe e record di riferimento.

### Disponibilità delle copie

La disponibilità viene determinata dal campo \`numeroCopie\`:

- valore maggiore di zero: copie disponibili;
- valore uguale a zero: copie esaurite.

Attualmente il filtro delle copie disponibili viene eseguito dal service dopo aver recuperato i record dal database.

### Eliminazione di un libro

\`LibroService.deleteLibro\` è annotato con \`@Transactional\`. La sequenza attuale è:

1. verifica dell'esistenza del libro;
2. eliminazione dei collegamenti in \`Libri_Autori\`;
3. eliminazione dei record in \`Copie\` associati all'ISBN;
4. eliminazione del libro.

Le query di eliminazione diretta nei repository sono annotate con \`@Modifying\`.

## Database e avvio

La configurazione predefinita si trova in \`src/main/resources/application.properties\`:

- database: \`Biblioteca\`;
- host: \`localhost\`;
- porta MySQL: \`3306\`;
- driver: \`com.mysql.cj.jdbc.Driver\`;
- \`spring.jpa.hibernate.ddl-auto=validate\`.

Con \`ddl-auto=validate\`, Hibernate verifica la compatibilità tra entity e schema esistente, senza creare o modificare automaticamente le tabelle. Il database deve quindi essere predisposto prima di avviare l'applicazione.

Il dump SQL si trova in \`sql/Dump20261104.sql\`; lo schema grafico è disponibile in \`sql/db_schema.png\`.

Per avviare l'applicazione con Maven:

\`\`\`bash
mvn spring-boot:run
\`\`\`

Con SpringDoc è disponibile la documentazione OpenAPI e l'interfaccia Swagger UI agli indirizzi:

- \`http://localhost:8080/v3/api-docs\`
- \`http://localhost:8080/swagger-ui/index.html\`

## Tabelle presenti nel dump ma non ancora gestite da entity e API

Il dump SQL contiene anche le tabelle \`Condizione\`, \`Prestiti\`, \`Utenti\`, \`Dipendenti\` e \`Vendite\`. Al momento non risultano implementate le corrispondenti entity, repository, service e controller Java.

Le relazioni SQL presenti nel dump comprendono inoltre:

\`\`\`text
Nazionalita  1 ─── N Autori
Generi       1 ─── N Libri
Autori       N ─── N Libri       tramite Libri_Autori
Libri        1 ─── N Copie
CasaEditrice 1 ─── N Copie
Libri        1 ─── N Prestiti
Utenti       1 ─── N Prestiti
Condizione   1 ─── N Prestiti
Utenti       1 ─── N Vendite
Dipendenti   1 ─── N Vendite
\`\`\`

Le ultime relazioni appartengono allo schema SQL, ma non sono ancora rappresentate dal modello JPA dell'applicazione.

## Limitazioni e comportamenti da correggere

Questa sezione distingue le funzionalità effettivamente implementate dai punti per cui il comportamento attuale non corrisponde ancora all'endpoint o al nome del metodo.

1. **Modifica di una copia**: \`CopiaController.modificaCopia\` richiama \`addCopia\`, che crea una nuova entity \`Copia\` senza valorizzare \`idCopia\`. La chiamata PUT inserisce quindi un nuovo record invece di aggiornare quello esistente.
2. **Eliminazione di una copia**: il percorso è dichiarato come \`/copie/{isbn}\`, ma il valore viene passato a \`deleteCopia(Integer idCopia)\`. Il parametro rappresenta quindi l'ID della copia e non l'ISBN del libro.
3. **Ricerca delle copie tramite ISBN**: \`CopiaRepository.findAllByLibro\` restituisce una singola \`Copia\`, non una lista. La ricerca può essere ambigua se lo stesso libro è associato a più case editrici.
4. **Modifica di un genere**: \`GenereController\` usa \`@PutMapping("/id")\` ma dichiara un parametro \`@PathVariable id\`. Il mapping dovrebbe includere il segnaposto, ad esempio \`@PutMapping("/{id}")\`.
5. **Ricerca di un genere per ID**: se il genere non esiste, \`GenereService.getById\` solleva \`AutoreNotFoundException\` invece di \`GenereNotFoundException\`. Il controller intercetta soltanto quest'ultima.
6. **Eliminazione di un autore associato a libri**: \`AutoreService\` elimina direttamente l'autore senza rimuovere prima eventuali collegamenti in \`Libri_Autori\`. Se sono presenti riferimenti, l'eliminazione può essere impedita dai vincoli del database.
7. **Prestiti**: la cancellazione di un libro non gestisce record in \`Prestiti\`. Se esistono prestiti che fanno riferimento al libro e il vincolo SQL non consente la cancellazione, l'operazione può fallire.
8. **Parametri delle richieste**: diversi metodi ricevono parametri semplici senza annotazioni esplicite e, per l'inserimento o la modifica di un libro, gli ID degli autori sono invece nel body JSON. La documentazione degli esempi riflette questa struttura attuale; in futuro si potrebbe uniformare il contratto usando DTO di request e \`@RequestBody\`.

Questi punti non implicano che siano necessari controller CRUD per tutte le tabelle di supporto. È sufficiente esporre API dedicate quando esiste una vera operazione applicativa da rendere disponibile al client.

# ListGame

Description (Italian)

Esercizio

Si realizzi in Java, utilizzando le librerie Thread e Socket un server di rete che gestisce un gioco elettronico distribuito, a cui possono giocare più utenti contemporaneamente. 

Il gioco consiste nel leggere rapidamente una sequenza di numeri e nel riscriverla correttamente.

Una volta lanciato il client chiede all’utente di inserire username e password e le invia al server dopo aver inviato il messaggio “login”. Se l’utente inserisce un username mai usato da nessuno nel sistema, il server lo registra associandolo con la password inserita. Se invece l’utente inserisce uno username già registrato il server verifica che la password ricevuta sia corretta. La procedura viene ripetuta fino a che il processo di login termina correttamente. Se il processo va a buon fine il server invia la stringa “true”, altrimenti invia la stringa “false”.

Una volta loggato l’utente può decidere di iniziare una partita o di richiedere la lista dei record personali di tutti gli utenti.

Nel primo caso il client invia la stringa “partita”. Il server invia al client la prima sequenza da ricordare. Il client la mostra per qualche istante all’utente, poi chiede all’utente di ridigitarla, e la invia al server. Il server controlla essa sia uguale a quella inviata, se essa non è corretta invia la stringa “false” e termina la partita, altrimenti risponde con la stringa “true” e invia un’altra sequenza di numeri. 

Se invece l’utente sceglie di vedere i record dei giocatori, il client invia al server la stringa “record” e questi risponde con una stringa rappresentante il numero dei giocatori seguita da una stringa per ognuno dei giocatori contenente username e punteggio.

Quando il giocatore sceglie di uscire dal programma, il client invia al server la stringa “fine”, e il server chiude la connessione col client.

Si chiede di realizzare il solo server.
Viene fornito in allegato un client, completo di sorgente, per testare il programma.  La procedura di esecuzione deve essere la seguente: si manda in esecuzione dapprima un file server.bat e poi il file client.bat.

Per ogni eventuale dubbio sul funzionamento atteso del server, utilizzare il codice sorgente del client come “specifica”.




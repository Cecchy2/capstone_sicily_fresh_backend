# capstone_sicily_fresh

# Sicily Fresh

**Sicily Fresh** è un progetto sviluppato da **Dario Cecchinato** come capstone finale per il diploma FullStack presso **Epicode**.

## Descrizione del Progetto

Sicily Fresh consente agli utenti di registrarsi come **Clienti** e di contattare l'amministratore per richiedere l'accesso come **Fornitori**. Una volta registrati, i Clienti possono completare un ordine e ricevere una notifica via email. Attualmente, il sistema di email utilizza **Mailgun** in modalità di test, pertanto le email vengono inviate solo a indirizzi verificati.

- I **Fornitori** possono caricare **ricette tipiche siciliane** e vendere i relativi ingredienti, ottenendo un guadagno fisso per ogni ricetta venduta.
- I **Clienti** possono sottoscrivere abbonamenti per aggiungere ricette al carrello e ordinare gli ingredienti necessari per prepararle a casa.
- Gli utenti possono gestire il proprio profilo, aggiornare le informazioni personali e caricare un'immagine di profilo.

## Pubblicazione

Il progetto è stato pubblicato online:

- **Frontend**: [Netlify]
- **Backend**: [Koyeb]

Puoi visitare il progetto a questo indirizzo: https://sicilyfresh.netlify.app

## Tecnologie Utilizzate

### Back-end

- **Java** con **Spring Boot**
- **Hibernate** per la gestione delle entità e delle relazioni
- **PostgreSQL** come database relazionale
- Schema **ERD** (Entity Relationship Diagram)
- **Spring Security** per l'autenticazione e l'autorizzazione (ruoli: Clienti e Fornitori)

### Front-end

- **JavaScript** con **React**
- **Redux** per la gestione dello stato globale
- Chiamate **API REST**, testate con **Postman**

L'interfaccia utente è **intuitiva** e **responsive**, consentendo agli utenti di navigare facilmente tra le ricette, aggiungerle al carrello e completare gli ordini da qualsiasi dispositivo.

---

### Come Eseguire il Progetto Localmente

1. Clona questo repository:

   ```bash
   git clone https://github.com/Cecchy2/capstone_sicily_fresh_front_end.git (FrontEnd)

   git clone https://github.com/Cecchy2/capstone_sicily_fresh.git (BackEnd)
   ```

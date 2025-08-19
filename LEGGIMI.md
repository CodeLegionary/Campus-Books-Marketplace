# LEGGI_ME: 📚 Negozio di Libri Accademico in Docker - Demo Online

## 🧭 Introduzione

Questa è una web application full-stack containerizzata e deployata tramite Docker. Simula un marketplace universitario dove gli utenti, sia affiliati all'università che non, possono comprare e vendere libri di seconda mano. Le consegne sono gestite internamente all'università, senza l'uso di servizi logistici di terze parti. L'applicazione è puramente una demo, progettata per mostrare le competenze di sviluppo in un contesto simile.

## 🏗️ Architettura

Il progetto è composto da:

- **Front-End**: Si trova nella cartella `Vite-jsx`, realizzato con React e Vite.
- **Back-End**: Si trova nella cartella `demo`, realizzato con Java 17 e Spring. Include anche form statici e script per la registrazione e l'autenticazione, integrati tramite Thymeleaf.

L'intera applicazione è containerizzata e orchestrata utilizzando Docker. Ci sono 2 container/servizi e un database PSQL.

## ⚙️ Istruzioni

Per avviare il progetto in locale, usa Docker Compose:

```bash
docker-compose up
```

Oppure, a seconda della tua versione di Docker:
```bash
docker compose up
```

Puoi modificare il file docker-compose.yml per adattarlo al tuo ambiente. Questo progetto specifico è progettato per funzionare online (ovvero in modalità produzione).

## 🚀 Deployment

- **CI** è gestita tramite GitHub Actions con predisposizione per implementare **CD**.
- Le **immagini dei container** sono ospitate su GitHub Container Registry (GHCR).
- Il **deployment** è ospitato su [Northflank](https://northflank.com), una PaaS ottimizzata per i flussi di lavoro Docker. Il link dell'app è: [Books-Store](https://p01--frontend--ls828smnbk6x.code.run/).

### Livelli di Accesso

- **Utenti non autenticati**: (Un visitatore) può sfogliare una sorta di anteprima completa del marketplace.
- **Utenti autenticati**: Hanno pieno accesso a tutte le funzionalità, inclusa la messa in vendita e l'acquisto di libri.

## ⚠️ Avvertenze

Questa è un'applicazione demo. Qualsiasi contenuto o funzionalità è illustrativo e non rappresenta una transazione commerciale reale, anche se il modello di base può evolvere in un prodotto finito. Per gestire transazioni reali, sarebbe necessario integrare servizi di terze parti come RazorPay, Stripe o simili.
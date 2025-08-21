# README: 📚 Academic Dockerized Books-Store - Online Demo

## 🧭 Introduction

This is a containerized full-stack web application deployed via Docker. It simulates a university marketplace where users - whether affiliated with the university or not - can buy and sell second-hand books. Deliveries are handled internally within the university, with no third-party logistics involved. The app is purely a demo, designed to showcase development skills in a similar context.

## 🏗️ Architecture

The project consists of:

- **Front-End**: Located in the `Vite-jsx` folder, built with React and Vite.
- **Back-End**: Located in the `demo` folder, built with Java 17 and Spring. This also includes static forms and scripts for registration and authentication, integrated via Thymeleaf.

The entire application is containerized and orchestrated using Docker. There are 2 containers/services and one database PSQL.

## ⚙️ Instructions

To start the project locally, first make sure all URLs point to the correct containers on localhost (typically 5432, 8080, 5173). For some forms, you may need to manually reset these values. Then, use Docker Compose to launch the environment.

```bash
docker-compose up
```

Or, depending on your Docker version:

```bash
docker compose up
```

You can adjust the docker-compose.yml file to suit your environment. This specific project is designed to run online (i.e. production mode).

## 🚀 Deployment

- **CI** is handled via GitHub Actions, with the potential to implement **CD** through the host.
- **Container images** are hosted on GitHub Container Registry (GHCR).
- **Deployment** is hosted on [Northflank](https://northflank.com), a PaaS optimized for Docker workflows. Live app link: [Books-Store](https://p01--frontend--ls828smnbk6x.code.run/).

### Access Levels

- **Unauthenticated users**: (A visitor) can browse a sort of full preview of the marketplace.
- **Authenticated users**: Have full access to all features, including listing and purchasing books.

### Roles & Permissions

The application features role differentiation ('USER', 'ADMIN'), though only the base role is currently active. The architecture was designed to support future development, allowing for the addition of other roles like 'ADMIN', which would be useful for managing a larger site with a dedicated administrative team. As this is a demo, administrative and monitoring activities can still be performed directly through the database.

## ⚠️ Disclaimer

This is a demo application. Any content or functionality is for illustrative purposes and do not represent real commercial transactions, even though the model is scalable and can be updated into a finished product. To handle actual transactions, third-party services (e.g., RazorPay, Stripe, etc.) would be required.

Therefore, you can simply avoid entering any sensitive data or real payment credentials. The same applies to authentication data. While certain protective measures, such as encryption, are in place to demonstrate best practices, the site is fully functional with fictitious information.
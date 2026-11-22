# API de Mensajería y Logística (Courier & Logistics API)

[![CI](https://github.com/WilfredoAM22/courier-logistics-api/actions/workflows/ci.yml/badge.svg)](https://github.com/WilfredoAM22/courier-logistics-api/actions/workflows/ci.yml)
![Java 21](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot&logoColor=white)

API RESTful en Java 21 y Spring Boot para cotización aduanal (DGA) y rastreo logístico en la ruta **Miami (MIA) -> República Dominicana (SDQ/STI)**.

## Características

* Cotización aduanal para paquetes exentos ($\le 200$ USD FOB) y gravados en cascada (Arancel, ISC e ITBIS).
* Registro de paquetes en Miami y consulta por número de tracking.
* Actualización de estados logísticos del envío.

## Stack

* **Lenguaje:** Java 21 | **Framework:** Spring Boot 4.1.2 | **Persistencia:** Hibernate 7, H2 | **Pruebas:** JUnit 5

## Requisitos previos

* Java 21 instalado (Maven no hace falta: el proyecto incluye `mvnw`).

## Instalación y Ejecución

```bash
git clone https://github.com/WilfredoAM22/courier-logistics-api.git
cd courier-logistics-api
./mvnw clean compile
./mvnw test
export DB_PASSWORD=tu_contraseña_aquí
./mvnw spring-boot:run
```

## Endpoints Principales

* **Cotizador Aduanal (`POST /api/v1/quotes`):** Soporta cálculo para paquetes exentos ($\le 200$ USD FOB) y gravados en cascada (Arancel, ISC e ITBIS).
* **Gestión de Envíos (`/api/v1/shipments`):** Registro de paquetes en Miami, consulta por número de tracking y actualización de estados logísticos.

## Evidencias

**Compilación**

![Compilación exitosa](docs/images/build-success.png)

**Pruebas (JUnit 5)**

![Resultado de las pruebas](docs/images/junit-tests.png)
# API de Mensajería y Logística (Courier & Logistics API)

API RESTful en Java 21 y Spring Boot para cotización aduanal (DGA) y rastreo logístico en la ruta **Miami (MIA) -> República Dominicana (SDQ/STI)**.

## Stack
* **Lenguaje:** Java 21 | **Framework:** Spring Boot 4.1.2 | **Persistencia:** Hibernate 7, H2 | **Pruebas:** JUnit 5

## Instalación y Ejecución
```bash
git clone https://github.com
cd courier-logistics-api
./mvnw clean compile
./mvnw test
export DB_PASSWORD=BUTTERCOOKIES
./mvnw spring-boot:run
```

## Endpoints Principales (`curl`)
* **Cotizador Aduanal (`POST /api/v1/quotes`):** Soporta cálculo para paquetes exentos ($\le 200$ USD FOB) y gravados en cascada (Arancel, ISC e ITBIS).
* **Gestión de Envíos (`/api/v1/shipments`):** Registro de paquetes en Miami, consulta por número de tracking y actualización de estados logísticos.

## Evidencias
* **Compilación:** `docs/images/build-success.png`
* **Pruebas:** `docs/images/junit-tests.png`

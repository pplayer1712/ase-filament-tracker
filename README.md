# Filament Tracker

## Zweck des Projekts
Der Filament Tracker ist eine Backend-Anwendung zur Verwaltung von 3D-Drucker-Filamenten. Sie ermöglicht es, Filamentspulen mit Details wie Material, Farbe und Gewicht zu erfassen, konkrete Druckaufträge (PrintJobs) abzuwickeln und den daraus resultierenden Filament-Verbrauch zu berechnen. Zudem können Verbrauchsstatistiken ermittelt und Nachbestell-Schwellenwerte (Reorder Thresholds) überwacht werden.

Das Projekt ist nach den Ideen des **Domain-Driven Design (DDD)** und als **Hexagonale Architektur (Ports and Adapters)** aufgebaut. Die Geschäftslogik ist strikt von technischen Details wie Datenbanken oder Web-Schnittstellen getrennt, was die Anwendung testbar und wartbar macht.

## Technologien
* **Programmiersprache:** Kotlin
* **Framework:** Spring Boot 4
* **Datenbank:** SQLite (mit Spring Data JPA / Hibernate)
* **Build-Tool:** Gradle (Kotlin DSL)
* **Java-Version:** Java 21

## Projekt aufsetzen und starten

### Voraussetzungen
* **Java 21** muss installiert sein (oder Sie lassen Gradle Toolchain dies automatisch beziehen).
* Das Projekt bringt einen eigenen Gradle-Wrapper mit, es ist also keine manuelle Gradle-Installation notwendig.

### Anwendung starten
Öffnen Sie ein Terminal im Hauptverzeichnis des Projekts und führen Sie den folgenden Befehl aus:

**Auf Windows:**
```powershell
.\gradlew.bat bootRun
```

**Auf Linux / macOS:**
```bash
./gradlew bootRun
```

Die Anwendung startet standardmäßig auf Port `8080`. 

> **Hinweis zur Datenbank:** Die lokale SQLite-Datenbankdatei wird im Git-Repository ignoriert und nicht mitgeführt. Beim ersten Start der Anwendung generiert Spring Boot (bzw. Hibernate/SQLite) automatisch eine frische, leere Datenbankdatei, sodass das Projekt direkt einsatzbereit ist.

Die interaktive **API-Dokumentation (Swagger UI)** rufen Sie nach dem Start typischerweise im Browser unter folgender Adresse auf:
`http://localhost:8080/swagger-ui.html`

### Tests ausführen
Um die Unit- und Integrationstests (geschrieben mit JUnit 5 und MockK) des Projekts auszuführen, nutzen Sie den `test`-Task:

**Auf Windows:**
```powershell
.\gradlew.bat test
```

**Auf Linux / macOS:**
```bash
./gradlew test
```
Testberichte im HTML-Format finden Sie nach dem Durchlauf unter `build/reports/tests/test/index.html`.

## Architekturübersicht
Das Projekt ist den DDD-Konzepten folgend in drei maßgebliche Schichten (Packages) geteilt:
1. **`domain`:** Der absolut technik-unabhängige Kern. Enthält Aggregates (`Filament`, `PrintJob`), Value Objects (`Weight`, `Diameter`) und Domain Services.
2. **`application`:** Orchestriert Fach-Abläufe in Use-Cases (Application Services) und definiert die Ein- und Ausgangs-Ports der Anwendung.
3. **`adapter`:** Technische Implementierung der Inbound-Ports (z. B. REST-Controller) und Outbound-Ports (Datenbank-Repositories zur Persistierung auf SQLite).

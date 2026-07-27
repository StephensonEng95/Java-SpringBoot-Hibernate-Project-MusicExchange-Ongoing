# Artist & Fan Management System 🎤

Hi, i am Stephenson, a Backend-focused Engineer leaning to Full Stack. I build scalable and maintanabl Spring Boot applications following clean architecture, spring design patterns and standard software development guidelines. Currently building a Spring Web app modeling a real artist/fan music platform — artists publish
songs, fans follow artists and get notified when they do. I built it to go deep on backend architecture
and event-driven design, while also learning Full Stack in depth, not just to ship a CRUD app.

## Stack

- [**Java 17**](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [**Spring Boot 3.4**](https://spring.io/projects/spring-boot)
- [**Spring Data JPA / Hibernate**](https://spring.io/projects/spring-data-jpa)
- [**Spring Security**](https://spring.io/projects/spring-security) — authentication, with a custom Enum-based role check gating access
- [**Apache Kafka**](https://kafka.apache.org/) — async events for follows and new song releases
- [**Flyway**](https://flywaydb.org/) — versioned schema migrations
- [**MySQL**](https://www.mysql.com/)
- [**Maven**](https://maven.apache.org/)

## Why I built this 

The idea came out of a conversation with a producer friend of mine — I'm an artist myself, and we kept
running into the same gap: no clean way for fans to follow the artists they care about and actually get
notified when something new drops, without it living inside a general-purpose social platform that wasn't
built for that relationship specifically. My friend had the product instinct for what artists and fans
actually need; I'm the developer, so I took it on to build.

That's also why the architecture leans the way it does — a fan following an artist and an artist
publishing a song are the two core actions the whole system is designed around, which is why they're the
first two events in the Kafka pipeline rather than an afterthought bolted on later.

Screnshot below showing working fan dashboard page, with table entries and user controller

![](https://github.com/user-attachments/assets/d90388c4-effd-4402-aa11-6191f18ef936)

## What's actually built

- **Core domain** — Artist, Fan, Song entities with proper relationship mapping (`@ManyToMany` for
  fan-follows-artist via a join table, `@ManyToOne`/`@OneToMany` for song-belongs-to-artist).
- **Flyway migrations** — schema changes are versioned, reviewable SQL scripts, not Hibernate auto-DDL
  guesswork.
- **Kafka event pipeline** — `ArtistAddedSongEvent` and `FanFollowedArtistEvent` are published as
  lightweight records (not entities, to avoid coupling the message schema to the DB schema and dodge
  lazy-loading issues on serialization) and consumed asynchronously.
- **Dedicated `kafka` package** — keeps all messaging concerns (producers, consumers, event records,
  and the dead-letter error-handler config) in one place, separate from the domain/business logic in
  `service`. A clean, self-contained unit to eventually fold into domain packages once the modular
  monolith refactor happens.
- **Dead-letter handling** — a message that fails processing gets retried twice, then routed to a
  dead-letter topic instead of blocking every message behind it. `acks=all` and producer idempotence are
  configured to avoid data loss and duplicate delivery.
- **Custom Enum-based RBAC** — dashboard and endpoint access is gated by role today via a manual check
  against a `Role` enum, sitting on top of Spring Security authentication. Migrating this to Spring
  Security's `@PreAuthorize` / `hasRole()` is a near-term cleanup item — the access logic is correct, I
  want the enforcement living in the framework layer instead of hand-rolled.

## Architecture

Currently structured as a layered application (`controller` / `service` / `repository` / `model` /
`kafka` packages). Laying the foundation for a **modular monolith** — regrouping by domain (`artist`,
`fan`, `song`, each owning its own controller/service/repository/entity, with the relevant Kafka
producer/consumer folded in) instead of by technical role. Layered was the right starting point to get
the domain model right; modular boundaries matter more once the app has several interacting domains and
event flows, which is where this project is now.

## Project Layout

    ## Project Layout
     ''' text
    src/main/java/com/musicexchange/
    ├── controller/    # Handles web requests
    ├── service/       # Business logic
    ├── repository/    # Data access layer
    ├── model/         # Entity classes (Artist, Fan, Song)
    ├── dto/           # Data Transfer Objects for API requests and responses
    ├── config/        # Application configurations (Security, Kafka, etc.)
    ├── exception/     # Custom exception handlers and error definitions
    └── kafka/         # Producers, consumers, event records, and error-handling config
                       # for the async event pipeline (song-added, artist-followed)

    src/main/resources/
    ├── db/migration/  # Flyway versioned SQL schema migration scripts
    └── templates/     # Thymeleaf HTML view templates

## Milesstones
- [ ] Migrated role checks to Spring Security `@PreAuthorize`
- [ ] Wired the Thymeleaf views to the backend (HTML's build)
- [ ] Added following logic

## In progress

- [ ] Modular monolith package restructuring
- [ ] Docker containerisation
- [ ] Expanding JUnit 5 / Mockito test coverage across the service layer


## Git workflow

Feature branches per unit of work (e.g. `kafka-events-and-error-handling`, `flyway-schema-migration`),
merged into `main` via reviewed pull requests, branch deleted after merge. Solo project, so I review my
own diffs before merging — same discipline I'd want from anyone else on a team.

## Background

MEng in Electrical & Electronics Engineering before moving into software — the systematic-debugging habit
from that background carries over more than I expected. This project is my main portfolio piece; happy to
walk through any of the architectural decisions above in more detail.

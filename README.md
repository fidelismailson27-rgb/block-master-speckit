# Block Master

Tetris-like game skeleton for Android (Kotlin).

Setup
- This repository contains a lightweight skeleton and domain logic for Block Master.
- To build a full Android app, create an Android Studio project and place the `app/src/main/kotlin/com/blockmaster/game` sources accordingly.

Running tests
- Unit tests are under app/src/test/kotlin. Configure Gradle and run `./gradlew test` after adding Android/Gradle scaffolding.

Design
- MVVM architecture. Core game rules live in `domain/` and are testable without Android.

See .specify/specs/block_master_spec.md and .specify/plans/block_master_plan.md for specification and plan.


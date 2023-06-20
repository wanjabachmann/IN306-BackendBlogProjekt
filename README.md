# Backend Blog IN306 Verteilte Systeme

As part of the course we will implement a backend for a blog.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/blog-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

# Changelog
- style: :truck: Moves all files one folder up to the main folder and move the services into the control package
- docs: :memo: Updates the Changelog within  the README.md file
- Merge pull request #1 from wanjabachmann/add_db_access
- feat: :lipstick: Change start page from hello to blog
- feat: Add the service files for the entities blog and author
- feat: add classes author and blog
- test: :white_check_mark: add the junit tests for the entities author and blog
- feat: :card_file_box: Add repository which implements PanacheRepository
- build: :heavy_plus_sign: Add the depenencies lombok and hibernate panache
- refactor: :fire: Removes unused files from previous tests and the default setup.
- feat: Creates the Quarkus project and a first customization from the Projekt-Setup task in moodle
- Initial commit
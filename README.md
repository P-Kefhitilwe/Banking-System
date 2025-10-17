# Banking System (OOAD)

Quick notes to build and run the project locally (console mode).

Windows (PowerShell):

1. Ensure Java 21 is on PATH: `java -version` should show Java 21.
2. Run the build script:

```powershell
.\build.ps1
```

This script compiles the non-UI sources, packages a simple JAR at `target\banking-system.jar`, and runs the console `banking.Main`.

To enable the JavaFX UI:

1. Add OpenJFX dependencies to `pom.xml` (javafx-controls and javafx-fxml with platform classifier, e.g. `win-x64`).
2. Move UI Java files from `src/main/resources/banking/ui/java/` back to `src/main/java/banking/ui/`.
3. Install Maven (or add Maven Wrapper) and run with `mvn -DskipTests=true exec:java -Dexec.mainClass="banking.ui.BankingApp"`.

Using Maven (recommended):

- Package the headless runnable jar (default):

```powershell
mvn -DskipTests package
java -jar target/banking-system.jar
```

- Build the UI-enabled jar (Windows):

```powershell
mvn -DskipTests -Pui package
java -jar target/banking-system.jar
```

- To add a Maven Wrapper to the repo (so others don't need Maven installed):

```powershell
# run once on your machine that has Maven installed
mvn -N io.takari:maven:wrapper
# commit the generated mvnw files and the .mvn folder
```

Note: I added `mvnw`, `mvnw.cmd` and `.mvn/wrapper/maven-wrapper.properties` to the repo. The actual `maven-wrapper.jar` will be downloaded automatically the first time you run `.\\mvnw.cmd` or `./mvnw`.


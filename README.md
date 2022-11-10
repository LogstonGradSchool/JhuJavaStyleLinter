# StyleLinter

## Build

```
mvn package
```

## Usage

```
# linter.jar = target/style-linter-1.0-SNAPSHOT-jar-with-dependencies.jar
java -jar linter.jar "path/to/file.java"
```

For a whole directory:

```
find . -name "*.java" | xargs -I{} /bin/bash -c "echo -n {},; java -jar linter.jar {};"
```

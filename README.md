# StyleLinter

[![Java CI with Maven](https://github.com/LogstonGradSchool/JhuJavaStyleLinter/actions/workflows/ci.yml/badge.svg)](https://github.com/LogstonGradSchool/JhuJavaStyleLinter/actions/workflows/ci.yml)

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

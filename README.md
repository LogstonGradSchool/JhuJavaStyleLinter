# StyleLinter

[![Java CI with Maven](https://github.com/LogstonGradSchool/JhuJavaStyleLinter/actions/workflows/ci.yml/badge.svg)](https://github.com/LogstonGradSchool/JhuJavaStyleLinter/actions/workflows/ci.yml)

## Analysis

Check out the [Analysis.ipynb
JupyterNotebook](https://github.com/LogstonGradSchool/JhuJavaStyleLinter/blob/main/examples/Analysis.ipynb)
for details on common repos and how they compare in terms of visibility ordering.

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
find . -name "*.java" | xargs -I{} java -jar linter.jar {}
```

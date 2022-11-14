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

Download JAR from the [releases page](https://github.com/LogstonGradSchool/JhuJavaStyleLinter/releases). Then ...

```
java -jar java-style-linter-0.1.1-jar-with-dependencies.jar "path/to/file.java"
```

For a whole directory:

```
find . -name "*.java" | xargs -I{} java -jar  java-style-linter-0.1.1-jar-with-dependencies.jar  {}
```

#!/bin/bash

cp ../target/style-linter-1.0-SNAPSHOT-jar-with-dependencies.jar linter.jar

rm -rf results.csv

while read line; do
   url=$(echo $line | cut -d, -f1)
   if [[ "$url" == "url" ]]; then
      continue
   fi

   echo "$url"
   rm -rf repo
   git clone $url repo

   trap break INT
      for file in $(find repo/ -name "*.java")
      do
         echo -n $line,$file, >> results.csv
         java -jar linter.jar $file >> results.csv
      done
   trap - INT
done < "top100.csv"

#!/bin/bash
mkdir temp
cp -r src temp/
mkdir temp/database
cp database/*.sql temp/database/
mkdir temp/lib
touch temp/lib/put-sqlitejdbc-v056.jar-here.txt
cp .project temp/
cp .classpath temp/
rm temp/src/testing/*
cp src/testing/ExpectedResult.java temp/src/testing/ExpectedResult.java
zip -r new-src.zip temp
rm -rf temp
git checkout gh-pages
rm src.zip
mv new-src.zip src.zip
git add src.zip
git commit -m "Updating source"
git push
git checkout master

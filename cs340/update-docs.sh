#!/bin/bash
git checkout master
doxygen
git checkout gh-pages
rm -rf documentation
mv docs documentation
git add documentation
git commit -m "Updating documentation"
git push
git checkout master

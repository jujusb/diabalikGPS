#!/bin/sh

name1="prenom-NOM"
name2="prenom-NOM"
release="D1"-$name1-$name2

rm -r $release 2> /dev/null
mkdir $release
cd ..
zip -r diaballik-bundle/$release/diaballik-D1-$name1-$name2.zip diaballik-doc

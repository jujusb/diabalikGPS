#!/bin/sh

name1="Julio-SANTILARIO_BERTHILIER"
name2="Guillaume-SONNET"
name3="Pierre_Yves-Le_Roland_Romer"
release="D1"-$name1-$name2-$name3

rm -r $release 2> /dev/null
mkdir $release
cd ..
zip -r diaballik-bundle/$release/diaballik-D1-$name1-$name2-$name3.zip diaballik-doc
#!/bin/sh

name1="prenom-NOM"
name2="prenom-NOM"
release="D2"-$name1-$name2

# cleaning the release files
rm -r $release 2> /dev/null
mkdir $release
# compiling and testing the back-end
cd ../diaballik-backend
mvn clean package
cd ..
zip -r diaballik-bundle/$release/diaballik-D2-$name1-$name2-sources.zip diaballik-doc diaballik-backend -x diaballik-backend/target/\* \*.idea/\* \*.iml

## building Docker containers
## Docker container of the back-end
#cd diaballik-backend
#docker build -t diaballik-backend .
#cd ../diaballik-bundle
#docker save diaballik-backend | xz > $release/diaballik-$name1-$name2-backend-docker.tar.xz

# docker stop diaballik-backend-run
# docker rm diaballik-backend-run
# docker run -it --name diaballik-backend-run -d -p 4444:4444 diaballik-backend

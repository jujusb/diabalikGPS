#!/bin/sh

name1="prenom-NOM"
name2="prenom-NOM"
release="FINAL"-$name1-$name2

# cleaning the release files
rm -r $release 2> /dev/null
# compiling and testing the back-end
cd ../diaballik-backend
mvn clean package
# building the frontend
cd ../diaballik-frontend
ng build --prod --build-optimizer
# creating release files
cd ../diaballik-bundle
mkdir $release
cd ..
zip -r diaballik-bundle/$release/diaballik-$name1-$name2-sources.zip diaballik-doc diaballik-backend diaballik-frontend/ -x diaballik-backend/target/\* lib-algo/target/\* diaballik-frontend/dist/\* diaballik-frontend/node_modules/\* \*.idea/\* \*.iml
cd diaballik-bundle/

# building Docker containers
## Docker container of the back-end
#cd ../diaballik-backend
#docker build -t diaballik-backend .
# docker run -it --name diaballik-backend-run -d -p 4444:4444 diaballik-backend
## Docker container of the front-end
#cd ../diaballik-frontend
#docker build -t diaballik-frontend .
#cd ../diaballik-bundle
#docker save diaballik-backend | xz > $release/diaballik-$name1-$name2-backend.tar.xz
#docker save diaballik-frontend | xz > $release/diaballik-$name1-$name2-frontend.tar.xz

# docker stop diaballik-frontend-run
# docker rm diaballik-frontend-run
# docker run -it --name diaballik-frontend-run -d -p 8080:8080 diaballik-frontend

# Docker stop/remove all the containers
# docker stop $(docker ps -a -q)
# docker rm $(docker ps -a -q)

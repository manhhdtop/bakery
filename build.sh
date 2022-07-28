git pull
mvn clean install
docker build -t bakery .
docker container rm bakery
docker run -it -d --name=bakery bakery
git pull
mvn clean install
docker build -t bakery .
docker container rm bakery
docker run -p 8080:80800 -it -d --name=bakery bakery
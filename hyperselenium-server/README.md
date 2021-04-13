## Build

    mvn clean install

## Docker Build

    docker build -t hyperselenium-server .

## Docker Start

    docker run -d -p 80:8080 --restart always hyperselenium-server

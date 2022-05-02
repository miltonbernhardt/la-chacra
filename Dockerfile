FROM openjdk:11-jre-slim-buster
ENV TZ=America/Argentina/Buenos_Aires
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ARG VERSION
ADD target/la-chacra-$VERSION.jar /la-chacra-app.jar
CMD java -jar /la-chacra-app.jar
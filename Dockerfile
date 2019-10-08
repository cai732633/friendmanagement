FROM adoptopenjdk/openjdk11:jdk-11.0.3_7

VOLUME /tmp
#VOLUME /etc/resources

ARG jar_file=friend-management-1.0-SNAPSHOT.jar
ENV jar_file=${jar_file}
ENV home=/friend

RUN mkdir $home
RUN mkdir $home/resources

ADD src/main/resources/application.yml $home/resources/application.yml

COPY ./target/${jar_file} $home/${jar_file}

EXPOSE 9001

ENTRYPOINT java -Dspring.config.location=$home/resources/application.yml -jar $home/${jar_file}

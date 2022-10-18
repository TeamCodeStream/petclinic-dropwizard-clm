# syntax=docker/dockerfile:1
FROM amazoncorretto:18 as base

RUN yum install -y jq && yum clean all

WORKDIR /app
expose 4080
expose 4081

FROM amazoncorretto:18 as build
WORKDIR /src
COPY src ./src
COPY .mvn ./.mvn
COPY mvnw pom.xml lombok.config ./
RUN --mount=type=cache,target=/root/.m2 ./mvnw package -DskipTests
#RUN echo $HOME
#RUN ls -alh /root/.m2
#RUN ls -alh /src/

FROM base AS final
WORKDIR /app
COPY app-config.yml ./
COPY --from=build /src/target/petclinic-dropwizard-1.0-SNAPSHOT.jar ./libs/
COPY --from=build /src/target/dependency ./libs/
COPY ["newrelic/", "./newrelic"]

COPY --chmod=0755 entrypoint.sh /
COPY --chmod=0755 tester.sh /app

#RUN ls -alh /app/libs

# Set env vars
# ENV NEW_RELIC_CONFIG_FILE=/app/newrelic/newrelic.yml

ENTRYPOINT ["/entrypoint.sh"]

#COPY app-config.yml ./
#COPY target/petclinic-dropwizard-1.0-SNAPSHOT.jar ./libs/
#COPY target/dependency ./libs/
#
#CMD ["java", "-cp", "libs/*", "io.baris.petclinic.dropwizard.PetclinicApplication", "server", "app-config.yml"]

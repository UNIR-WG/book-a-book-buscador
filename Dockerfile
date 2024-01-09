# Use the UBI minimal base image for the compilation stage
FROM openjdk:17-oracle as build

# Set the working directory
WORKDIR /app

# Install necessary dependencies for compilation
RUN microdnf install --nodocs -y java-17-openjdk-headless maven && \
    microdnf clean all

# Copy the source code to the container
COPY . /app

# Apply permissions
RUN chown -R 1001:1001 /app

# Set the user to run the application
USER 1001

# Compile project skipping testing goals (compilation, resources and run of tests)
RUN JAVA_HOME= ./mvnw clean package spring-boot:repackage -Dmaven.test.skip=true


# Use the UBI minimal base image for the run stage
FROM openjdk:17-oracle

# Set the working directory
WORKDIR /app

# Install necessary dependencies for running
RUN microdnf install --nodocs -y java-17-openjdk-headless && \
    microdnf clean all

# Copy the directory created in the first stage into the run container
RUN mkdir -p /app/target
COPY --from=build /app/target/bookabook.war /app

# Cambia la propiedad del directorio '/app/target' al usuario con id 1001
RUN chown 1001:1001 /app/bookabook.war

# Cambia el usuario que va a ejecutar los siguientes comandos al usuario con id 1001
USER 1001

# Set the application profile in order to change the config of DB location
ENV spring_profiles_active=prod

ENTRYPOINT ["java", \
    "-jar", "bookabook.war"]
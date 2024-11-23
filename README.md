# Logistics Api

## Getting started

Ensure you have your computer setup correctly for development by installing the following

- Java 21

## Setup Instructions

### 1. Clone the Repository

First, clone the repository to your local machine using Git.

```sh
git clone https://github.com/Adewale66/logistics.git
cd logistics
```

### 2. Install Dependencies

You can install the dependencies by running the following command in your root folder.

```sh
# For Unix based systems
./mvnw dependency:resolve

# For Windows based systems
mvnw.cmd dependency:resolve
```

### 3. Set Environment Variables for database

```sh
export H2_USERNAME=USERNAME
export H2_PASSWORD=PASSWORD

```

### 3. Run the Development Server

You can run the development server by running the following command in your root folder.

```sh
# For Unix based systems
./mvnw spring-boot:run

# For Windows based systems
mvnw.cmd spring-boot:run
```

## API Documentation

The API documentation can be found at `/docs`

## Testing

You can run the tests by running the following command in your root folder.

```sh
# For Unix based systems
./mvnw test

# For Windows based systems
mvnw.cmd test
```

## Build
You can build the project into a jar file by running the following command in your root folder.

```sh
# For Unix based systems
./mvnw clean package

# For Windows based systems
mvnw.cmd clean package
```

And run it using the following command in your root folder with the appropriate environment variables.

```sh
 H2_USERNAME=USERNAME H2_PASSWORD=PASSWORD java -jar target/{app-name}.jar
```
# EDH Pod Generator - API

## Project Setup

```
mvn clean install
```

### Compiles and hot-reloads for development

```
mvn spring-boot:run
```

### Run your unit tests

```
mvn clean verify
```

## Architecture

- Java/SpringBoot
- PostGreSQL

## Testing Strategy

### Unit Testing

I used MockMvc to unit test the controller level while mocking services as to remove any dependencies needed from the service level. I then tested the services separately to test the actual logic not related to just hitting the database, this primarily was focused on the actual pod creation process. 

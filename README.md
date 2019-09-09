Exposed EntityID reference example
===

A test for unexpected behavior [JetBrains' Exposed library](https://github.com/JetBrains/Exposed) when creating records where columns reference an `EntityId`.


Setup
---

```
docker run \
       --name exposed-entityid-example \
       --publish 54321:5432 \
       --rm \
       --detach \
       --env POSTGRES_PASSWORD=example \
       postgres:9.6-alpine
docker run \
       --interactive \
       --rm \
       --link exposed-entityid-example:postgres \
       --env PGPASSWORD=example \
       postgres:9.6-alpine \
       psql --host=postgres \
            --username=postgres <<EOF
CREATE ROLE example NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT LOGIN PASSWORD 'example';
CREATE DATABASE example OWNER example ENCODING 'UTF8';
EOF
```


Run
---

```
./gradlew test
```


Cleanup
---

```
docker stop exposed-entityid-example
```

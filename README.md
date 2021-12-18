## Redis-Cache-Demo

Demo application for using MariaDB and Redis caching

## Highlights

MariaDB is used for the persistence.

master-slave connection-string is used to write data via master and read via slaves.
`jdbc:mariadb:replication://localhost:33306,localhost:33307/TechieNotes`

https://mariadb.com/kb/en/failover-and-high-availability-with-mariadb-connector-j/

* Docker command to run MariaDB in master-slave mode:
    * For master
      `docker run -dti -p 33306:3306 --name mariadb-master -e MARIADB_ROOT_PASSWORD=masterghost -e MARIADB_REPLICATION_MODE=master -e MARIADB_REPLICATION_USER=suke -e MARIADB_REPLICATION_PASSWORD=duke -e MARIADB_USER=suke -e MARIADB_PASSWORD=duke -e MARIADB_DATABASE=TechieNotes bitnami/mariadb:latest`
    * For Slave
      `docker run -dti -p 33307:3306 --name mariadb-slave --link mariadb-master:master -e MARIADB_REPLICATION_MODE=slave -e MARIADB_REPLICATION_USER=suke -e MARIADB_REPLICATION_PASSWORD=duke -e MARIADB_MASTER_HOST=master -e MARIADB_MASTER_ROOT_PASSWORD=masterghost bitnami/mariadb:latest`
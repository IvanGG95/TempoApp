# TempoApp

Uses:
- Java 11
- Maven 3.6.3
- Spring-boot
- MySQL

For the application to work it is necessary to add a database with the following data:

CREATE DATABASE tempodb;
CREATE USER 'tempoUser'@'%' IDENTIFIED BY 'passwd';
GRANT ALL PRIVILEGES ON tempodb.* TO 'tempoUser'@'%';

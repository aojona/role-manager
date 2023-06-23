# Role manager

SOAP-сервис для управления ролями пользователей

## Стек используемых технологий

* [Spring](https://spring.io/) — основной фреймворк для разработки на Java
* [Gradle](https://gradle.org) — инструмент для автоматизации сборки проекта
* [Lombok](https://projectlombok.org/) — библиотека для генерации boilerplate кода
* [PostgreSQL](https://postgresql.org) — реляционная база данных
* [Apache CXF](https://cxf.apache.org/docs/springboot.html) – фреймворк для разработки SOAP сервиса
* [ModelMapper](https://modelmapper.org) – библиотека для автоматического преобразования объектов
* [Liquibase](https://liquibase.org) – система управления версиями базы данных

## Запуск

1. Настроить подключение к PostgreSQL или запустить локально
```shell
docker run --name hotel-database -e POSTGRES_PASSWORD=pass -p 5432:5432 -d postgres:15.3-alpine
```
2. Скомпилировать и запустить приложение
```shell
./gradlew bootRun
```
3. Для автоматической генерации запросов импортировать в SoapUI wsdl-схемы, 
сгенерированные по пути: `localhost:8080/soap`

## База данных

![diagram](https://github.com/aojona/role-manager/blob/main/database.svg)
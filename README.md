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

## Эндпоинты

```http
POST /soap/roles
```

#### Добавление роли

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.rolemanager.kirill.ru/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:createRole>
         <role>
            <name>MENTOR</name>
         </role>
      </ser:createRole>
   </soapenv:Body>
</soapenv:Envelope>
```

____

```http
POST /soap/users
```

#### Создание нового пользователя

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.rolemanager.kirill.ru/">
    <soapenv:Header/>
    <soapenv:Body>
        <ser:create>
            <user>
                <password>Miki87</password>
                <roles>
                    <name>MENTOR</name>
                </roles>
                <roles>
                    <name>SENIOR_DEVELOPER</name>
                </roles>
                <username>Mouse</username>
            </user>
        </ser:create>
    </soapenv:Body>
</soapenv:Envelope>
```

#### Поиск пользователя по id

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.rolemanager.kirill.ru/">
    <soapenv:Header/>
    <soapenv:Body>
        <ser:get>
            <id>1</id>
        </ser:get>
    </soapenv:Body>
</soapenv:Envelope>
```

#### Поиск всех пользователей

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.rolemanager.kirill.ru/">
    <soapenv:Header/>
    <soapenv:Body>
        <ser:getAll/>
    </soapenv:Body>
</soapenv:Envelope>
```

#### Обновление данных пользователя

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.rolemanager.kirill.ru/">
    <soapenv:Header/>
    <soapenv:Body>
        <ser:update>
            <id>1</id>
            <user>
                <password>L1</password>
                <roles>
                    <name>HERO</name>
                </roles>
                <username>KamenRider</username>
            </user>
        </ser:update>
    </soapenv:Body>
</soapenv:Envelope>
```

#### Обновление ролей пользователя

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.rolemanager.kirill.ru/">
    <soapenv:Header/>
    <soapenv:Body>
        <ser:updateWithRoles>
            <id>1</id>
            <role>
                <name>ACTOR</name>
            </role>
            <role>
                <name>PRODUCER</name>
            </role>
        </ser:updateWithRoles>
    </soapenv:Body>
</soapenv:Envelope>
```

#### Удаление пользователя

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.rolemanager.kirill.ru/">
    <soapenv:Header/>
    <soapenv:Body>
        <ser:delete>
            <id>1</id>
        </ser:delete>
    </soapenv:Body>
</soapenv:Envelope>
```

## Возможные ошибки

#### Пользователь не найден

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    <soap:Body>
        <soap:Fault>
            <faultcode>soap:Server</faultcode>
            <faultstring>User is not found</faultstring>
            <detail>
                <ns1:UserNotFoundException xmlns:ns1="http://service.rolemanager.kirill.ru/"/>
            </detail>
        </soap:Fault>
    </soap:Body>
</soap:Envelope>
```

#### Ошибка валидации

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    <soap:Body>
        <soap:Fault>
            <faultcode>soap:Server</faultcode>
            <faultstring>Название не может быть пустым; Пароль должен начианться с заглавной буквы и содержать хотя бы одну цифру</faultstring>
            <detail>
                <ns1:ValidationException xmlns:ns1="http://service.rolemanager.kirill.ru/"/>
            </detail>
        </soap:Fault>
    </soap:Body>
</soap:Envelope>
```
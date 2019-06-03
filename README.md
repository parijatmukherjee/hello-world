[![Build Status](https://dev.azure.com/parijatmukherjee/Open%20Source/_apis/build/status/parijatmukherjee.hello-world?branchName=master)](https://dev.azure.com/parijatmukherjee/Open%20Source/_build/latest?definitionId=3&branchName=master)
&#160;
![GitHub](https://img.shields.io/github/license/parijatmukherjee/hello-world.svg)

# Getting Started
hello-world is a free open source configurable spring boot 2 application that enables you to send birthday and anniversary mails to your employees. It automatically checks and triggers mails everyday at 12:00 AM. (JavaDocs will be updated soon)

# Requirements
- CentOS Linux
- MariaDB
- JDK 1.8
- JavaMail (can also work with your own smtp mail server)

# Features
- Database driven configurations
- Both Email templates are configurable starting from the image, subject line, company logo, content and much more.
- Added Freemarker support. Now you can use freemarker expressions to count years of work or age. Supported freemarker expressions are - ${name}, ${email}, ${birthDate}, ${anniversaryDate}, ${age}, ${workYears}.
- Added multiple configuration support. Set a number of mail configurations (specially, the mail content) in the database and hello-world will randomly choose one for sending the mail.

# Installation Instructions
- Download Source Code
- Build with Maven - ```$ mvn clean package```
- Run the jar in background - ```$ nohup java -jar /home/centos/hello-world-0.0.1-SNAPSHOT.jar > /var/log/hello-world/hello-world.log &```
- Check log - ```tail -f /var/log/hello-world/hello-world.log```

# Configuration Instructions

## Before you start
- Install MariaDB server and create database "HELLO_WORLD". (Change database related configuration in "src/main/resources/application.properties before building the jar.)

## Create Tables
```mysql
CREATE TABLE `EMPLOYEE` (
  `AUTO_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ANNIVERSARY_DAY` datetime DEFAULT NULL,
  `BIRTH_DAY` datetime DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`AUTO_ID`)
);
```
```mysql
CREATE TABLE `BIRTHDAY_MAIL_CONFIG` (
  `AUTO_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CC_RECIPIENTS` longtext,
  `COMPANY_LOGO_LINK` varchar(255) DEFAULT NULL,
  `FROM_EMAIL` varchar(255) DEFAULT NULL,
  `FROM_NAME` varchar(255) DEFAULT NULL,
  `CONTENT_HEAD_LINE` varchar(255) DEFAULT NULL,
  `MAIL_IMAGE_LINK` varchar(255) DEFAULT NULL,
  `MAIL_INTRO_CONTENT` longtext,
  `MAIL_MAIN_CONTENT` longtext,
  `SUBJECT_LINE` varchar(255) DEFAULT NULL,
  `REGARDS_FROM` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`AUTO_ID`)
);
```
```mysql
CREATE TABLE `ANNIVERSARY_MAIL_CONFIG` (
  `AUTO_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CC_RECIPIENTS` longtext,
  `COMPANY_LOGO_LINK` varchar(255) DEFAULT NULL,
  `FROM_EMAIL` varchar(255) DEFAULT NULL,
  `FROM_NAME` varchar(255) DEFAULT NULL,
  `CONTENT_HEAD_LINE` varchar(255) DEFAULT NULL,
  `MAIL_IMAGE_LINK` varchar(255) DEFAULT NULL,
  `MAIL_INTRO_CONTENT` longtext,
  ` ` longtext,
  `SUBJECT_LINE` varchar(255) DEFAULT NULL,
  `REGARDS_FROM` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`AUTO_ID`)
);
```
## Customize your application

### EMPLOYEE Table
- AUTO_ID : Auto incremented value
- ANNIVERSARY_DAY : Work Anniversary date of the employee (yyyy-MM-dd 00:00:00)
- BIRTH_DAY : Birth date of the employee (yyyy-MM-dd 00:00:00)
- EMAIL : Email Id of the emoloyee (the birthday and anniversary greetings will be sent to this mail id)
- NAME : Name of the employee (you can use short names too. This name is used in the mail content)

### BIRTHDAY_MAIL_CONFIG Table
- AUTO_ID : Auto incremented value
- CC_RECIPIENTS : The email Ids you want to keep in cc for all the birthday greetings. (Use comma seperated values without spaces)
- COMPANY_LOGO_LINK : The company logo link to be placed in every email (If any)
- FROM_EMAIL : The email id from which the greetings should be sent
- FROM_NAME : Personalized name for the "FROM_EMAIL" Id
- CONTENT_HEAD_LINE : Headline of the mail. (It will be displayed as 'h2' tag in the mail)
- MAIL_IMAGE_LINK : Link of the image you want to display in the mail. (*Width should be less that 650px*)
- MAIL_INTRO_CONTENT : First paragraph of the greetings mail.
- MAIL_MAIN_CONTENT : Second paragraph of the mail.
- SUBJECT_LINE : Subject of the mail.
- REGARDS_FROM : Your name or your company's name.

Configuration Example:
```mysql
INSERT INTO `BIRTHDAY_MAIL_CONFIG` (`AUTO_ID`, `CC_RECIPIENTS`, `COMPANY_LOGO_LINK`, `FROM_EMAIL`, `FROM_NAME`, `CONTENT_HEAD_LINE`, `MAIL_IMAGE_LINK`, `MAIL_INTRO_CONTENT`, `MAIL_MAIN_CONTENT`, `SUBJECT_LINE`, `REGARDS_FROM`)
VALUES
	(1, 'abc@def.com', 'https://drive.google.com/uc?export=view&id=<id>', 'hr@abc.com', 'Company', '', 'http://res.cloudinary.com/dhkcpbx2w/image/upload/c_scale,w_300/v1498078911/Happy_Birthday_uwz5vs.png', 'Happy Birthday! May your coming year surprise you with the happiness of smiles and the feeling of love. We hope you have made plenty of sweet memories to cherish forever over the last year.', 'Thank you for everything you do to make this company a great place to work and Happy Birthday, once again!', 'Happy Birthday to ${name}', 'Company');
```

Mail Content Example :
<br />
![Mail Example of Hello-world Application - https://github.com/parijatmukherjee/hello-world](https://github.com/parijatmukherjee/hello-world/blob/master/hello-world-application.png)

### ANNIVERSARY_MAIL_CONFIG is same as the above one


## APPLICATION PROPERTIES

```
logging.level.org.springframework=INFO
logging.level.se.seamless.sfo=DEBUG
logging.file=/var/log/hello-world/hello-world.log
logging.pattern.file= %d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%


# Spring DataSource properties
spring.datasource.url=jdbc:mysql://0.0.0.0:3306/HELLO_WORLD?createDatabaseIfNotExist=true
spring.datasource.username=refill
spring.datasource.password=refill
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true


############# MAIL CONFIGURATION ##############
#spring.mail.host=smtp.gmail.com
#spring.mail.port=587
#spring.mail.username=hr.india@seamless.se
#spring.mail.password=###########
#spring.mail.properties.mail.smtp.auth=true
#spring.mail.properties.mail.smtp.starttls.enable=true
```

To use your own application properties, please customize the above properties and save it somewhere.
To use your customized properties file run the jar like below -

```
$ java -jar hello-world.jar --spring.config.location=<link to your customized properties file>

```

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
Will be updated soon...



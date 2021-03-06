# Getting Tutor

### The Experiment Purpose
* Learning about web system framework 
* Preliminary understanding of how to develop systems in real production environments
* To develop a small system for graduates to choose tutors

### System Introduction
* For use by BO
* Student rankings are automatically calculated according to the grade/intention weight set by the teacher
* The student list is automatically selected according to the number of students the tutor can guide
* The remaining number that can be selected changes automatically with the selected number
* waiting for writing···

### Develop Insight
* Start by designing entity class carefully
* To understand how to use OneToMany and ManyToMany correctly
* Learning how to use mysql on the command line
* Map sets are difficult to use but powerful
* Use the common primary key to contact the user and tutor and student entities instead of inheritance relationships.
* Sensitive information such as passwords and identities need to be encrypted for transmission and should be ignored when serialized.
* Instead of traditional authentication, headers in HTTP carry encrypted permission information.
 

### Development Environments
Development environment/framework and version:

* Intellij IDEA 2019.3.3
* OpenJDK 11.0.5
* Springboot 2.2.4
* VS Code 1.42
* Node.js 12.16
* Vue 2.2.11
* VueX 3.1.2
* Axios 0.19.2
* Git 2.24
* MySQL 8.0.17

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)


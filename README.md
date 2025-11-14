# Artist & Fan Management System 🎤

Hey there! I'm building this Spring Boot app to manage artists and fans because I love music and wanted to create something that combines my passions. It's been a great way to learn real-world backend development.

## What I'm Using

- [**Java (JDK 17)**](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - My main programming language
- [**Spring Boot**](https://spring.io/projects/spring-boot) - Makes building web apps way easier
- [**Spring Data JPA**](https://spring.io/projects/spring-data-jpa) - For talking to the database
- [**Spring Security**](https://spring.io/projects/spring-security) - Handling login and permissions (still learning this!)
- [**Hibernate**](https://hibernate.org/) - Maps my Java objects to database tables
- [**MySQL**](https://www.mysql.com/) - Database where all the data lives
- [**Maven**](https://maven.apache.org/) - Manages all my dependencies
- [**Git**](https://git-scm.com/) - So I don't break everything when I experiment 

## About Me

I actually studied electronics engineering first, which taught me how to think through problems systematically. But I fell in love with coding and decided to switch to software development. Now I'm building projects like this to grow my skills!

## Why I Built This

I wanted to create something I'd actually use as a music lover, but the technical stuff I'm learning - like handling different user types and secure logins - applies to pretty much any app. It's been challenging but really fun to figure out.

## What Works So Far

✅ **Repositories** - My data layer is set up and working  
✅ **Services** - Business logic is separated out  
🔄 **User Controller** - Working on one controller that handles both artists and fans  
🔄 **Security** - Still setting up Spring Security (it's tricky!)

## How I'm Building It

I'm trying to follow good practices I've been learning:
- Keeping my code organized in layers
- Using feature branches so I don't mess up the main code
- Writing clear commit messages
- Learning as I go!

## Project Layout
src/main/java/com/musicexchange/
├── repository/ # Where I talk to the database
├── service/ # Business rules live here
├── model/ # My data structures
└── controller/ # Handles web requests


## The Tricky Parts

The hardest thing so far has been setting up one login system that works for both artists and fans but gives them different permissions. I'm learning a lot about:
- How Spring Security works
- Managing user roles
- Building APIs that can grow

## What's Next

I'm still working on:
- [ ] Getting Spring Security properly configured  
- [ ] Adding tests (I know I should as they are the most important part in software development lifecycle!)
- [ ] Better error handling and validation
- [ ] Connecting my Thymeleaf frontend (I already have HTML pages set up!) to the backend controllers

## My Development Approach

I've been practicing good Git workflow by using feature branches:
- Created `repositoriesUpdates` branch for all my repository layer improvements
- Created `servicesUpdates` branch for service layer enhancements  
- This lets me work on features without breaking the main codebase
- I'm learning how to manage multiple branches and merge changes safely

---

This project is my way of learning Spring Boot properly. Every time I hit a wall, I learn something new - and that's what I love about coding! Thanks for checking out my work 😊

![Logo](documents/logo2.png)

<br/>

---

<br/>
<br/>

#  TodayIGotIt (TiGi)
_status: in progress_

:mortar_board: TodayIGotIt is a project that implement online learning website (inspired from [**Freecodecamp**](https://github.com/freeCodeCamp/freeCodeCamp) and [**Udemy**](https://www.udemy.com/))

Technology in use: 
- Building Tool : Maven
- Front-End : Angular, Bootstrap
- Back-end : Spring Framework (Spring Boot, Spring MVC, Spring Data JPA, Spring Security, Hibernate)
- Database : H2 Database, MySQL


<br/>
<br/>


---

## How to run
**For Developer**
> - Open project in IntelliJ (or what ever IDE that support Spring and Maven)
> - Install the Tigi-Business module to local machine
> ![img1](documents/images/img1.png)
> - Execute the Tigi-Restapi Application
> ![img2](documents/images/img2.png)

**For User**
> - Start Localhost Server to deploy Spring Boot Application that implement RESTFul API. Open the terminal and navigate to ```tigiproject/``` and run the following command:
> ```
> ./mvnw spring-boot:run
> ```
> - If you encounter with the running problem, please check for the error logn, and run the following command to update the maven wrapper : 
> ```
> ./mvnw -N io.takari:maven:wrapper
> ```

**Optional** 

_(for use who has Maven installed in your machine)_
> ```
> mvn spring-boot:run
> ```


<br/>
<br/>


---


## How to develop
**For API Consumer**
> - Visit Api Documentation [Swagger UI](http://localhost:8080/TigiProject/swagger-ui.html#/) - [API Docs in Json](http://localhost:8080/TigiProject/v2/api-docs)
> ![img3](documents/images/img3.png)

**For Admnin**
> - Using Spring Actuator to manage RESTFul API [Actuator in HAL Browser](http://localhost:8080/TigiProject/browser/index.html#/TigiProject/actuator)
> ![img4](documents/images/img4.png)









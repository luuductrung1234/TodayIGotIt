- Front-End just for data testing



# Application Logic:
When deploying the app to Spring Container
>- the Spring Boot will be run first (by execute the ```SpringmvcApplication```)
>- the Spring Boot load all its default bean (include some beans were added by Maven)
>- the ```application.properties``` will be read and used by Spring Boot
>- the the ```WebConfiguration``` class will be loaded (liked ```web.xml```)
>- the ```SpringDataBaseBootstrap``` class will be loaded to init the data

When the request come to the Spring Application
>- the **dispatcherServlet** will navigate the request to the **Controller**
>- the **Controller** handle something and call the correct **Service**
>- the **Service** handle some business logic and then delegate the data management to the **Repository**
>- the **Repository** use the SpringDataJPA framework's API (in ```ldt.springframework.springmvc.data``` package) to talk to **Database**

When the data show to UI or when user input the form
>- the Command Object (in ```ldt.springframework.springmvc.commands```) work likes a temple interface that contain the data and show to UI
>- Use the Command Object help us hide the actual Entity object's data from user interact directly


The Security in this app implement
>- Authentication xác định danh tính người dùng
>- Authorization là quyền và khả năng
 

















# Iniciar el proyecto

1. Clonar el repositorio
2. Configurar las propiedades sobre la conexión a la base de datos en `application.properties`
```
    spring.application.name=project-planner
    spring.datasource.url=jdbc:mysql://localhost:3306/nombre_base_datos
    spring.datasource.username=usuario
    spring.datasource.password=contraseña
    spring.datasource.drive-class-name=com.mysql.jdbc.Driver
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=update
    server.port=8081
```
3. Compilar y ejecutar el proyecto
4. Al momento de ejecutar el proyecto en su puerto asignado en las variables de entorno para realizar las pruebas de las peticiones a los servicios con el siguiente Link base
```
    http://localhost:8081/api/project/save
    http://localhost:8081/api/task/save
```
5. El proyecto tiene 2 endpoints de task y projects para las solicitudes

# Documentación
1. En este link se encuentra los endpoints para poder hacer el servicio de las solicitudes

```
    https://documenter.getpostman.com/view/18900720/2sAXqnfQdL
```
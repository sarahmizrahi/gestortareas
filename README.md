# Gestor de Tareas

Esta es una aplicación de gestión de tareas desarrollada con un frontend en Android Studio utilizando Jetpack Compose en Kotlin y un backend en Spring Boot con Kotlin y MySQL como base de datos.

## Comenzando 🚀
Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas.

### Requisitos previos
Asegúrate de tener instalados los siguientes programas antes de continuar:

* [Java 17+](https://adoptopenjdk.net/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [MySQL](https://www.mysql.com/downloads/)
* [Android Studio](https://developer.android.com/studio)
* [IntelliJ IDEA](https://www.jetbrains.com/idea/) 

### Organización del repositorio

El proyecto está estructurado en un solo repositorio con dos carpetas principales:
```
/ProyectoConjunto_SMR
│-- backend/   # Proyecto Spring Boot para el backend
│-- frontend/  # Proyecto Android Studio para el frontend
```

### Instalación y configuración

Clona este repositorio y ejecuta cada proyecto por separado:
```
git clone https://github.com/sarahmizrahi/gestortareas.git
```

### Configurar la base de datos en IntelliJ IDEA

Asegúrate de que MySQL está corriendo y accesible
```
mysql -u tu_usuario -p
```
Luego ingresa el password 4490 y verifica que la base de datos gestortareas existe
```
SHOW DATABASES;
```
Abre la pestaña Database en IntelliJ IDEA:
  1. Ve a View → Tool Windows → Database.
  2. Haz clic en el botón + y selecciona Data Source → MySQL.
  3. Configura la conexión:
```
     Host: localhost
     Port: 3306
     User: root
     Password: 4490
     Database: gestortareas
     URL de conexión: jdbc:mysql://localhost:3306/gestortareas
```
  4. Haz clic en Test Connection. Si todo está bien, verás un mensaje de éxito.
  5. Luego, haz clic en Apply y OK.

### Scripts SQL y ejecución en IntelliJ

1. Abre IntelliJ IDEA y accede a la pestaña Database.
2. Haz doble clic en la base de datos gestortareas para expandir sus tablas.
3. Haz clic derecho en gestortareas y selecciona New + Query Console.
4. Escribe el script que deseas ejecutar y presiona Play/Run:
5. 
Listar todas las tareas:
```
SELECT * FROM tareas;
```
Insertar tareas:
```
INSERT INTO tareas (titulo, descripcion, completado) VALUES
('Comprar café', 'En CoffeeShops', 0),
('Llamar veterinario', 'Pedir cita', 1);
```
Actualizar una tarea por ID (ejemplo con ID 66):
```
UPDATE tareas
SET titulo = 'actualizando pedir cita',
    descripcion = '',
    completado = false
WHERE id = 66;
```
Eliminar una tarea por ID (ejemplo con ID 65):
```
DELETE FROM tareas WHERE id = 65;
```
Los resultados se mostrarán en la pestaña de salida en la parte inferior.

## Ejecución del proyecto
### Iniciar el backend en Spring Boot, IntelliJ IDEA
  1. Abre IntelliJ IDEA, importa la carpeta backend, sincroniza las dependencias de Gradle y ejecuta la aplicación.
  2. El backend estará disponible en http://localhost:8080/tareas
     
### Iniciar el frontend en Android Studio
  1. Abre Android Studio y selecciona "Open" para importar la carpeta frontend.
  2. Ejecuta el emulador de Android Studio.
  3. Verás la interfaz de la aplicación donde podrás interactuar con ella.

## Prueba la sincronización entre frontend y backend
  1. Abre el navegador y accede a:
```
http://localhost:8080/tareas
```
  2. Esto te permitirá ver la lista de tareas del backend, donde tambíen podrás realizar CRUD.
  3. Alterna entre el frontend en Android y el navegador, agregando, editando o eliminando tareas.
  4. Refresca Database en Intellij para ver que los datos se actualizan correctamente.

| **Método** | **Endpoint** | **Descripción** |
|------------|--------------|-----------------|
| GET        |  /tareas     | Listar tareas   |
| POST       | /tareas/crear|Crear nueva tarea|
| PUT        | /tareas/editar/{id} |Actualizar tarea |
| DELETE     | /tareas/{id} | Eliminar tarea  |

🎉 ¡Listo! Ahora tienes todo para ejecutar la aplicación 🎉
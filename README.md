# MedicalApp

MedicalApp is an application designed to manage medical visits, doctors, and patients. This repository contains the source code and configurations for setting up the app.

## Configuration

To configure the application, you need to add the following details in the respective property files:

### **application.properties** (Located in `src/main/resources/`)

The `application.properties` file contains the application's core configuration settings such as database connection details.

```properties
# Database URL
spring.datasource.url=your-database-url

# Database username
spring.datasource.username=your-username

# Database password
spring.datasource.password=your-password

# Database driver
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Other Spring Boot configurations...
```

## API Endpoints

| **Endpoint**     | **Method** | **Description**                                                            | **Request Body**                                                                                                                         | **Response**                               |
|------------------|------------|----------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------|
| `/visits`        | `POST`     | Create a new visit for a doctor (validated by date, doctor & patient IDs). | `{ "doctorId": 1, "patientId": 2, "startDateTime": "2025-02-28T11:00:00" - after now, "endDateTime": "2025-02-28T11:00:00" - after now}` | 201 Created with visit details.            |
| `/visits/search` | `GET`      | Search for visits based on patient name and doctor ID.                     | `search=John&doctorId=1&page=0&size=20&sort=id` or<br/>`search=John Smith&doctorId=1,2,3&page=0&size=5`                                  | List of visits based on search parameters. |

### Swagger UI

You can interact with these endpoints and test them using the Swagger UI at:  
### http://localhost:8080/swagger-ui/index.html#/



# Doctors, Patients and Visits
The poctors, patients and visits are added by default when the application starts.

### Doctors table

| ID  | First Name | Last Name  | Time Zone                      |
|-----|------------|------------|--------------------------------|
| 1   | John       | Smith      | America/New_York               |
| 2   | Emily      | Johnson    | Europe/London                  |
| 3   | Michael    | Brown      | Asia/Tokyo                     |
| 4   | Sarah      | Davis      | America/Argentina/Buenos_Aires |
| 5   | James      | Miller     | Asia/Dubai                     |

### Patients table

| ID  | First Name | Last Name  |
|-----|------------|------------|
| 1   | Alice      | Brown      |
| 2   | Bob        | Smith      |
| 3   | Carol      | Johnson    |
| 4   | David      | Williams   |
| 5   | Emma       | Davis      |

### Visits table

| id  | start_date_time       | end_date_time         | doctor_id | patient_id |
|-----|-----------------------|-----------------------|-----------|------------|
| 1   | 2023-12-01 09:00:00   | 2023-12-01 10:00:00   | 1         | 1          |
| 2   | 2023-12-02 10:00:00   | 2023-12-02 11:00:00   | 2         | 2          |
| 3   | 2023-12-03 11:00:00   | 2023-12-03 12:00:00   | 3         | 3          |
| 4   | 2023-12-04 14:00:00   | 2023-12-04 15:00:00   | 4         | 4          |
| 5   | 2023-12-05 15:00:00   | 2023-12-05 16:00:00   | 5         | 5          |
| 6   | 2024-01-01 09:00:00   | 2024-01-01 10:00:00   | 1         | 2          |
| 7   | 2024-01-02 10:00:00   | 2024-01-02 11:00:00   | 2         | 3          |
| 8   | 2024-01-03 11:00:00   | 2024-01-03 12:00:00   | 3         | 4          |
| 9   | 2024-01-04 14:00:00   | 2024-01-04 15:00:00   | 4         | 5          |
| 10  | 2024-01-05 15:00:00   | 2024-01-05 16:00:00   | 5         | 1          |
| 11  | 2024-02-01 09:00:00   | 2024-02-01 10:00:00   | 1         | 3          |
| 12  | 2024-02-02 10:00:00   | 2024-02-02 11:00:00   | 2         | 4          |
| 13  | 2024-02-03 11:00:00   | 2024-02-03 12:00:00   | 3         | 5          |
| 14  | 2024-02-04 14:00:00   | 2024-02-04 15:00:00   | 4         | 1          |
| 15  | 2024-02-05 15:00:00   | 2024-02-05 16:00:00   | 5         | 2          |




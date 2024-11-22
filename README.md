UML CLASS DIAGRAM LINK : [Click Here](https://excalidraw.com/#json=Qnno1zps2YDBTsLIIUlSU,iSZn7hp4gkzK0140cX1C1A)

# Citronix Farm Management System

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![License](https://img.shields.io/badge/license-MIT-blue)

A Spring Boot application for managing citrus farms, tracking harvests, and handling sales.

## 🚀 Quick Start

```bash
# Clone the repository
git clone https://github.com/yourusername/citronix.git

# Navigate to project directory
cd citronix

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

## 🌟 Features

- Farm & field management with area validation
- Tree lifecycle tracking with productivity metrics
- Seasonal harvest recording
- Sales and revenue management
- Comprehensive validation rules
- RESTful API endpoints

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Jakarta Validation
- Lombok
- MapStruct
- PostgreSQL

## 📚 Documentation

### API Endpoints

#### Farms (`/api/v1/farms`)
```
POST   /create         # Create farm
PUT    /update        # Update farm
DELETE /delete/{id}    # Delete farm
GET    /details/{id}   # Get farm details
POST   /search        # Search farms
```

#### Fields (`/api/v1/fields`)
```
POST   /save          # Create field
GET    /find/{id}     # Get field
GET    /farm/{farmId} # List fields
PUT    /update        # Update field
DELETE /delete/{id}   # Delete field
```

See [API Documentation](docs/API.md) for complete endpoint details.

## 🔧 Configuration

Create `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/citronix
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🔍 Business Rules

### Farm
- Minimum area: 0.2 hectare
- Required: name, location, area, establishment date

### Field
- Minimum area: 0.1 hectare
- Maximum: 10 fields per farm
- Area limit: 50% of farm area

### Tree
- Planting: March-May only
- Density: 1 tree per 100 m²

### Harvest
- One harvest per season/year
- No duplicate tree harvests per season

## ✨ Acknowledgments

- Thanks to all contributors
- Inspired by modern farming management systems


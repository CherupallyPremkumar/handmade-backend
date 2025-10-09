# Homebase Admin Suite Backend

Spring Boot backend for the multi-tenant admin dashboard with JWT authentication, role-based access control, and comprehensive REST APIs.

## 🚀 Features

- **Multi-Tenant Architecture**: Complete tenant isolation with X-Tenant-ID header support
- **JWT Authentication**: Secure token-based authentication with role-based access control
- **Role-Based Authorization**: Three roles (SUPER_ADMIN, EDITOR, VIEWER) with granular permissions
- **RESTful APIs**: Complete CRUD operations for products, orders, categories, customers
- **2FA Support**: Two-factor authentication infrastructure ready
- **Spring Security**: Production-ready security configuration with CORS support
- **JPA/Hibernate**: Clean entity relationships with auditing
- **H2 Database**: Development database (easily switch to PostgreSQL)
- **Password Encryption**: BCrypt password hashing
- **Data Initialization**: Auto-creates sample data for testing

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🛠️ Installation & Setup

### 1. Clone and Navigate

```bash
cd backend
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The backend will start at `http://localhost:8080`

## 🔐 Test Credentials

The application auto-initializes with test users for three tenants: `default`, `tenant1`, `tenant2`

### Super Admin (Full Access)
- **Email**: `admin@homedecor.com`
- **Password**: `admin123`
- **Role**: `SUPER_ADMIN`
- **2FA**: Disabled

### Editor (Products & Categories)
- **Email**: `editor@homedecor.com`
- **Password**: `editor123`
- **Role**: `EDITOR`
- **2FA**: Enabled (code: `123456`)

### Viewer (Read-Only)
- **Email**: `viewer@homedecor.com`
- **Password**: `viewer123`
- **Role**: `VIEWER`
- **2FA**: Disabled

## 📡 API Endpoints

### Authentication
```
POST   /api/admin/login                 - Login with email/password
POST   /api/admin/verify-2fa            - Verify 2FA code
GET    /api/admin/me                    - Get current user profile
POST   /api/admin/logout                - Logout
POST   /api/admin/password-reset        - Request password reset
POST   /api/admin/verify-session        - Verify session validity
```

### Products
```
GET    /api/products                    - Get all products (tenant-aware)
GET    /api/products/{id}               - Get product by ID
POST   /api/products                    - Create product (SUPER_ADMIN, EDITOR)
PUT    /api/products/{id}               - Update product (SUPER_ADMIN, EDITOR)
DELETE /api/products/{id}               - Delete product (SUPER_ADMIN, EDITOR)
```

### Orders
```
GET    /api/orders                      - Get all orders
GET    /api/orders/{id}                 - Get order by ID
PATCH  /api/orders/{id}/status          - Update order status
```

### Categories
```
GET    /api/categories                  - Get all categories
POST   /api/categories                  - Create category (SUPER_ADMIN, EDITOR)
PUT    /api/categories/{id}             - Update category (SUPER_ADMIN, EDITOR)
DELETE /api/categories/{id}             - Delete category (SUPER_ADMIN, EDITOR)
```

### Dashboard
```
GET    /api/dashboard/stats             - Get dashboard statistics
```

## 🔑 Authentication Flow

### 1. Login Request
```bash
curl -X POST http://localhost:8080/api/admin/login?tenantId=default \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@homedecor.com",
    "password": "admin123",
    "rememberMe": true
  }'
```

### 2. Response
```json
{
  "user": {
    "id": "1",
    "email": "admin@homedecor.com",
    "name": "Admin User",
    "role": "super_admin",
    "tenantId": "default"
  },
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "requiresTwoFactor": false,
  "tenantConfig": {
    "id": "default",
    "name": "Home Decor Admin"
  }
}
```

### 3. Use Token in Subsequent Requests
```bash
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "X-Tenant-ID: default"
```

## 🏗️ Project Structure

```
backend/
├── src/main/java/com/homebase/admin/
│   ├── HomebaseAdminApplication.java    # Main application entry point
│   ├── controller/                      # REST controllers
│   │   ├── AuthController.java
│   │   ├── ProductController.java
│   │   ├── OrderController.java
│   │   ├── CategoryController.java
│   │   └── DashboardController.java
│   ├── service/                         # Business logic layer
│   │   ├── AuthService.java
│   │   ├── ProductService.java
│   │   ├── OrderService.java
│   │   ├── CategoryService.java
│   │   └── DashboardService.java
│   ├── repository/                      # Data access layer
│   │   ├── AdminUserRepository.java
│   │   ├── ProductRepository.java
│   │   ├── OrderRepository.java
│   │   ├── CategoryRepository.java
│   │   └── CustomerRepository.java
│   ├── entity/                          # JPA entities
│   │   ├── AdminUser.java
│   │   ├── Product.java
│   │   ├── Order.java
│   │   ├── Category.java
│   │   ├── Customer.java
│   │   ├── BaseEntity.java
│   │   └── TenantContext.java
│   ├── dto/                             # Data Transfer Objects
│   │   ├── LoginRequest.java
│   │   ├── LoginResponse.java
│   │   ├── ProductDTO.java
│   │   ├── OrderDTO.java
│   │   └── ...
│   ├── security/                        # Security configuration
│   │   ├── JwtUtil.java
│   │   ├── JwtRequestFilter.java
│   │   └── SecurityConfig.java
│   └── config/                          # Application configuration
│       └── DataInitializer.java
└── src/main/resources/
    └── application.yml                  # Application properties
```

## 🔧 Configuration

### Switch to PostgreSQL

1. Update `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/homebase_admin
    username: your_username
    password: your_password
    driver-class-name: org.postgresql.Driver
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

2. Comment out H2 dependency in `pom.xml`

### Change JWT Secret
Update in `application.yml`:
```yaml
jwt:
  secret: your-super-secure-secret-key-at-least-256-bits
  expiration: 86400000  # 24 hours
```

### Configure CORS
Update in `application.yml`:
```yaml
cors:
  allowed-origins: http://localhost:5173,https://your-frontend-domain.com
```

## 🧪 Testing

### Access H2 Console (Development)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:homebase_admin`
- Username: `sa`
- Password: _(leave empty)_

### Run Tests
```bash
mvn test
```

## 🌐 Multi-Tenant Usage

All API calls should include the `X-Tenant-ID` header:

```bash
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer TOKEN" \
  -H "X-Tenant-ID: tenant1"
```

Available tenants:
- `default` - Default tenant
- `tenant1` - Tenant 1 Store
- `tenant2` - Tenant 2 Shop

## 📦 Building for Production

```bash
# Build JAR file
mvn clean package

# Run JAR
java -jar target/admin-suite-backend-1.0.0.jar
```

## 🔒 Security Best Practices

1. **Change JWT Secret**: Use a strong, random secret in production
2. **Use PostgreSQL**: Switch from H2 to PostgreSQL for production
3. **Enable HTTPS**: Always use HTTPS in production
4. **Environment Variables**: Store sensitive config in environment variables
5. **Rate Limiting**: Add rate limiting for authentication endpoints
6. **Audit Logging**: Enable comprehensive audit logs
7. **Database Backup**: Implement regular database backups

## 🐛 Troubleshooting

### Port Already in Use
```bash
# Change port in application.yml
server:
  port: 8081
```

### Database Connection Issues
- Verify database is running
- Check credentials in `application.yml`
- Ensure correct JDBC driver is included

### CORS Issues
- Add your frontend URL to `cors.allowed-origins`
- Check browser console for specific CORS errors

## 📚 API Documentation

Once running, access Swagger UI (if configured):
- URL: `http://localhost:8080/swagger-ui.html`

## 🤝 Integration with Frontend

1. Set frontend environment variables:
```env
VITE_USE_MOCK_DATA=false
VITE_API_URL=http://localhost:8080/api
```

2. Restart frontend:
```bash
npm run dev
```

3. Login with test credentials
4. All API calls will now use the Spring Boot backend

## 📄 License

This project is part of the Homebase Admin Suite.

## 🆘 Support

For issues or questions:
- Check existing documentation
- Review API Integration guide in frontend
- Verify JWT token is valid and not expired
- Ensure X-Tenant-ID header is included in requests

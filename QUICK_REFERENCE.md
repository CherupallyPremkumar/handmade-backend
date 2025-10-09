# Backend Quick Reference

## 🚀 Start Backend
```bash
cd backend
mvn spring-boot:run
# OR
./start.sh
```

## 🔗 URLs
- Backend API: `http://localhost:8080/api`
- H2 Console: `http://localhost:8080/h2-console`

## 🔐 Test Users (All Tenants)
```
Super Admin: admin@homedecor.com / admin123
Editor:      editor@homedecor.com / editor123 (2FA: 123456)
Viewer:      viewer@homedecor.com / viewer123
```

## 📡 Key Endpoints

### Auth
```bash
POST /api/admin/login?tenantId=default
GET  /api/admin/me
POST /api/admin/logout
```

### Products
```bash
GET    /api/products
POST   /api/products          # ADMIN, EDITOR
PUT    /api/products/{id}     # ADMIN, EDITOR
DELETE /api/products/{id}     # ADMIN, EDITOR
```

### Orders
```bash
GET   /api/orders
PATCH /api/orders/{id}/status
```

### Categories
```bash
GET    /api/categories
POST   /api/categories        # ADMIN, EDITOR
PUT    /api/categories/{id}   # ADMIN, EDITOR
DELETE /api/categories/{id}   # ADMIN, EDITOR
```

### Tags
```bash
GET    /api/tags
POST   /api/tags              # ADMIN, EDITOR
DELETE /api/tags/{id}         # ADMIN, EDITOR
```

### Other
```bash
GET /api/customers
GET /api/dashboard/stats
```

## 📋 Request Headers
```
Authorization: Bearer {JWT_TOKEN}
X-Tenant-ID: {TENANT_ID}
Content-Type: application/json
```

## 🗄️ Database (H2 Console)
- URL: `jdbc:h2:mem:homebase_admin`
- User: `sa`
- Pass: _(empty)_

## 🔧 Configuration Files
- `src/main/resources/application.yml` - Main config
- `pom.xml` - Dependencies

## 🏗️ Code Structure
```
com.homebase.admin/
├── controller/     # REST endpoints
├── service/        # Business logic
├── repository/     # Data access
├── entity/         # Database models
├── dto/            # API request/response
├── security/       # JWT & Auth
└── config/         # App setup
```

## 🌐 Multi-Tenant
Available tenants: `default`, `tenant1`, `tenant2`

Each tenant has isolated:
- Users, Products, Orders, Categories, Customers

## 🛠️ Common Commands

### Build
```bash
mvn clean install
```

### Run
```bash
mvn spring-boot:run
```

### Package
```bash
mvn clean package
# Creates: target/admin-suite-backend-1.0.0.jar
```

### Test
```bash
mvn test
```

## 🔍 Debugging

### View Logs
Check terminal where backend is running

### Database Queries
```sql
-- H2 Console
SELECT * FROM admin_users;
SELECT * FROM products WHERE tenant_id = 'default';
SELECT * FROM orders;
```

### Test Login
```bash
curl -X POST http://localhost:8080/api/admin/login?tenantId=default \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@homedecor.com","password":"admin123"}'
```

## ⚠️ Troubleshooting

**Port 8080 in use?**
```bash
lsof -ti:8080 | xargs kill -9
```

**Build fails?**
```bash
mvn clean install -U
```

**Database issues?**
- Restart backend (data auto-reinitializes)
- Check H2 console

## 📚 More Info
- Full Backend Docs: `README.md`
- Integration Guide: `../BACKEND_INTEGRATION.md`
- Getting Started: `../GETTING_STARTED.md`

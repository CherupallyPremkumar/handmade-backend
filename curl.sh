curl -X POST "https://handmade-backend-981536694150.asia-south1.run.app/api/admin/login?tenantId=default" \
     -H "Content-Type: application/json" \
     -d '{
           "email": "admin@homedecor.com",
           "password": "admin123"
         }'
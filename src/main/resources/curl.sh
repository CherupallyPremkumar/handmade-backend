curl -X POST http://localhost:8081/tenant \
  -H "Content-Type: application/json" \
  -H "x-chenile-tenant-id: tenant0" \
  -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJXTmpVdlVjNW9fRjQ2N0tQbFpPVjdidlNiMmhUeHlZS2NlLXZUR3pwOXh3In0.eyJleHAiOjE3NjAzNzQxMDUsImlhdCI6MTc2MDM3MzgwNSwianRpIjoiYTA4MTAxMzYtNDkwZi00NzFmLWFmZjctMzgwMDI4ZDliNzA5IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgyL3JlYWxtcy90ZW5hbnQwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYWNjb3VudC1jb25zb2xlIiwic2lkIjoiNTU1OTViNzAtOWI5Ny00MjJlLTkwYWEtYzJmOTJjMzA0YTIxIiwic2NvcGUiOiJvcGVuaWQifQ.TQrrcCtOFPLP5N_BGGrBb3AmJg2fjfFThrIAriyMWDFZojsLBXxssez0KkYWYkYLeKzELcGhul6zcjZEvvDrUhJGBtaM39xMc8zXNhBvh7v8sH7n5gabMMXnMPI-DEWLqnOkQ9hWy8p1k_rTn5i_EVqrBujvLeSYixslB-WC-ssxRgOZrdCnw5CQbk7MfNnCAbFhCy2FgXdPRx8v1_sSlkWcxilNEMF4LkWPIiwD21s_9u9Vm0vI7BaSb4VtUd_Fp8I8Pe9O8P4xQgOK1HUtmi5CbizkFz-7m5znfCBJWdWQWGvUrHT2kvmbOaimq0hEcYJU27kH6p3Jtj8jj1yHvg" \
  -d '{
    "tenantCode": "tenant1",
    "displayName": "My Store",
    "domain": "mystore.example.com",
    "logoUrl": "https://example.com/logo.png",
    "supportEmail": "support@mystore.com",
    "supportPhone": "+1-555-0123",
    "theme": {
      "primaryColor": "#3B82F6",
      "secondaryColor": "#10B981",
      "logoUrl": "https://example.com/logo.png"
    }
  }'
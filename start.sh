#!/bin/bash

echo "======================================"
echo "Homebase Admin Suite Backend"
echo "======================================"
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null
then
    echo "❌ Maven is not installed. Please install Maven first."
    echo "   Visit: https://maven.apache.org/install.html"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null
then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    echo "   Visit: https://www.oracle.com/java/technologies/downloads/"
    exit 1
fi

echo "✅ Maven and Java are installed"
echo ""

# Clean and build
echo "🔨 Building the project..."
mvn clean install -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Build successful!"
    echo ""
    echo "🚀 Starting the application..."
    echo "   Backend will be available at: http://localhost:8080"
    echo "   H2 Console: http://localhost:8080/h2-console"
    echo ""
    echo "📋 Test Credentials:"
    echo "   Super Admin: admin@homedecor.com / admin123"
    echo "   Editor: editor@homedecor.com / editor123"
    echo "   Viewer: viewer@homedecor.com / viewer123"
    echo ""
    mvn spring-boot:run
else
    echo ""
    echo "❌ Build failed. Please check the errors above."
    exit 1
fi

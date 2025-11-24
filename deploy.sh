#!/bin/bash

# Hospital Management System Deployment Script

echo "🏥 Hospital Management System Deployment"

# Build Java Backend
echo "🔨 Building Java Backend..."
cd backend
mvn clean package

if [ $? -eq 0 ]; then
    echo "✅ Backend build successful"
else
    echo "❌ Backend build failed"
    exit 1
fi

# Copy WAR file to Tomcat
echo "🚀 Deploying to Tomcat..."
cp target/hospital-management-system.war /opt/tomcat/webapps/

# Deploy Frontend
echo "🎨 Deploying Frontend..."
cd ../frontend
cp -r * /var/www/html/

echo "✅ Deployment completed successfully!"
echo "🌐 Application available at: http://localhost:8080/hospital-management-system"

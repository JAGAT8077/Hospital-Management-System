<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hospital Management System - Live Demo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <nav class="navbar">
        <div class="nav-container">
            <div class="nav-logo">
                <i class="fas fa-hospital-alt"></i>
                <span>Hospital Management System - Live Demo</span>
            </div>
            <ul class="nav-menu">
                <li><a href="${pageContext.request.contextPath}/" class="nav-link">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/demo" class="nav-link active">Demo</a></li>
                <li><a href="${pageContext.request.contextPath}/api/patients" class="nav-link">API</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <h1>Live Java Demo</h1>
        
        <div class="demo-stats">
            <div class="stat-card">
                <h3>Total Patients</h3>
                <p id="totalPatients">0</p>
            </div>
            <div class="stat-card">
                <h3>Admitted Patients</h3>
                <p id="admittedPatients">0</p>
            </div>
            <div class="stat-card">
                <h3>Doctors</h3>
                <p id="totalDoctors">0</p>
            </div>
        </div>

        <div class="demo-actions">
            <h2>Patient Management</h2>
            <form id="patientForm" class="demo-form">
                <input type="text" name="name" placeholder="Patient Name" required class="form-input">
                <input type="email" name="email" placeholder="Email" required class="form-input">
                <input type="text" name="phone" placeholder="Phone" required class="form-input">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-user-plus"></i> Add Patient
                </button>
            </form>
            
            <button onclick="loadStats()" class="btn btn-secondary">
                <i class="fas fa-sync"></i> Refresh Stats
            </button>
        </div>

        <div class="demo-output">
            <h3>System Output</h3>
            <div id="output" class="output-console">
                <p>Welcome to Hospital Management System Demo</p>
                <p>System initialized with Java Servlets and JSP</p>
            </div>
        </div>

        <div class="java-code">
            <h3>Java Servlet Code Example</h3>
            <pre><code class="java">
@WebServlet("/api/patients/*")
public class PatientServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) {
        // Handle GET requests
        List<Patient> patients = patientService.getAllPatients();
        // Return JSON response
    }
    
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response) {
        // Handle POST requests
        Patient patient = parsePatientFromRequest(request);
        patientService.addPatient(patient);
        // Return success response
    }
}
            </code></pre>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/demo.js"></script>
    <script>
        // Initialize demo
        document.getElementById('patientForm').addEventListener('submit', function(e) {
            e.preventDefault();
            addPatient();
        });

        loadStats();
    </script>
</body>
</html>

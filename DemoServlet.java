package com.hospital.servlets;

import com.hospital.model.*;
import com.hospital.services.HospitalService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/demo")
public class DemoServlet extends HttpServlet {
    private HospitalService hospitalService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        this.hospitalService = new HospitalService();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set demo data
        setupDemoData();
        
        // Forward to JSP page
        request.getRequestDispatcher("/jsp/demo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        Map<String, Object> result = new HashMap<>();
        
        try {
            switch (action) {
                case "addPatient":
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    String phone = request.getParameter("phone");
                    
                    Patient patient = new Patient(
                        "P" + System.currentTimeMillis(),
                        name,
                        email,
                        phone,
                        LocalDate.now().minusYears(30),
                        "O+"
                    );
                    
                    hospitalService.admitPatient(patient);
                    result.put("success", true);
                    result.put("message", "Patient added successfully");
                    result.put("patient", patient);
                    break;
                    
                case "getStats":
                    result.put("totalPatients", hospitalService.getTotalPatients());
                    result.put("admittedPatients", hospitalService.getAdmittedPatients().size());
                    result.put("totalDoctors", hospitalService.getAllDoctors().size());
                    result.put("success", true);
                    break;
                    
                default:
                    result.put("success", false);
                    result.put("message", "Unknown action");
            }
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    private void setupDemoData() {
        // Setup sample data for demo
        try {
            // Add sample doctors
            Doctor doctor1 = new Doctor(
                "D001", 
                "Dr. Sarah Wilson", 
                "sarah.wilson@hospital.com", 
                "555-0101",
                "Cardiology", 
                15, 
                "MED12345"
            );
            
            Doctor doctor2 = new Doctor(
                "D002", 
                "Dr. Robert Brown", 
                "robert.brown@hospital.com", 
                "555-0102",
                "Pediatrics", 
                10, 
                "MED12346"
            );
            
            // These would be added to hospital service in real implementation
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

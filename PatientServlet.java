package com.hospital.servlets;

import com.hospital.model.Patient;
import com.hospital.services.PatientService;
import com.hospital.exceptions.PatientNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/patients/*")
public class PatientServlet extends HttpServlet {
    private PatientService patientService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        this.patientService = new PatientService();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = request.getPathInfo();
            
            if (pathInfo == null || pathInfo.equals("/")) {
                // Get all patients
                List<Patient> patients = patientService.getAllPatients();
                String jsonResponse = objectMapper.writeValueAsString(patients);
                response.getWriter().write(jsonResponse);
            } else {
                // Get patient by ID
                String patientId = pathInfo.substring(1); // Remove leading slash
                Patient patient = patientService.getPatientById(patientId);
                String jsonResponse = objectMapper.writeValueAsString(patient);
                response.getWriter().write(jsonResponse);
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
            
        } catch (PatientNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Patient not found\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Internal server error\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            Patient patient = objectMapper.readValue(request.getReader(), Patient.class);
            patientService.addPatient(patient);
            
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\": \"Patient created successfully\"}");
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid patient data\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Patient ID required\"}");
                return;
            }
            
            String patientId = pathInfo.substring(1);
            Patient patient = objectMapper.readValue(request.getReader(), Patient.class);
            patient.setId(patientId); // Ensure ID matches URL
            
            patientService.updatePatient(patient);
            
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Patient updated successfully\"}");
            
        } catch (PatientNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Patient not found\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid patient data\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Patient ID required\"}");
                return;
            }
            
            String patientId = pathInfo.substring(1);
            patientService.deletePatient(patientId);
            
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Patient deleted successfully\"}");
            
        } catch (PatientNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Patient not found\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Internal server error\"}");
        }
    }
}

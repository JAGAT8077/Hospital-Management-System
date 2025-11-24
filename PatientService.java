package com.hospital.services;

import com.hospital.model.Patient;
import com.hospital.dao.PatientDAO;
import com.hospital.exceptions.PatientNotFoundException;
import java.util.List;

public class PatientService {
    private PatientDAO patientDAO;

    public PatientService() {
        this.patientDAO = new PatientDAO();
    }

    public List<Patient> getAllPatients() {
        try {
            return patientDAO.getAllPatients();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving patients", e);
        }
    }

    public Patient getPatientById(String id) throws PatientNotFoundException {
        try {
            return patientDAO.getPatient(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving patient", e);
        }
    }

    public void addPatient(Patient patient) {
        try {
            patientDAO.addPatient(patient);
        } catch (Exception e) {
            throw new RuntimeException("Error adding patient", e);
        }
    }

    public void updatePatient(Patient patient) throws PatientNotFoundException {
        try {
            patientDAO.updatePatient(patient);
        } catch (Exception e) {
            throw new RuntimeException("Error updating patient", e);
        }
    }

    public void deletePatient(String id) throws PatientNotFoundException {
        try {
            patientDAO.deletePatient(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting patient", e);
        }
    }

    public List<Patient> searchPatients(String query) {
        try {
            return patientDAO.searchPatients(query);
        } catch (Exception e) {
            throw new RuntimeException("Error searching patients", e);
        }
    }
}

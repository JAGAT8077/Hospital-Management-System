package com.hospital.services;

import com.hospital.model.*;
import com.hospital.collections.HospitalRegistry;
import com.hospital.exceptions.HospitalOperationException;
import java.util.List;
import java.util.ArrayList;

public class HospitalService {
    private HospitalRegistry<Patient> patientRegistry;
    private List<Doctor> doctors;
    private List<Nurse> nurses;

    public HospitalService() {
        this.patientRegistry = new HospitalRegistry<>();
        this.doctors = new ArrayList<>();
        this.nurses = new ArrayList<>();
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Initialize with sample data
        try {
            // Sample doctors
            doctors.add(new Doctor("D001", "Dr. Sarah Wilson", "sarah@hospital.com", 
                                 "555-0101", "Cardiology", 15, "MED12345"));
            doctors.add(new Doctor("D002", "Dr. Robert Brown", "robert@hospital.com", 
                                 "555-0102", "Pediatrics", 10, "MED12346"));
            
            // Sample nurses
            nurses.add(new Nurse("N001", "Nurse Amanda Clark", "amanda@hospital.com", 
                               "555-0103", "Emergency", 1));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void admitPatient(Patient patient) throws HospitalOperationException {
        try {
            patientRegistry.registerPatient(patient);
            patientRegistry.admitPatient(patient.getId());
        } catch (Exception e) {
            throw new HospitalOperationException("Failed to admit patient: " + e.getMessage());
        }
    }

    public void dischargePatient(String patientId) throws HospitalOperationException {
        try {
            patientRegistry.dischargePatient(patientId);
        } catch (Exception e) {
            throw new HospitalOperationException("Failed to discharge patient: " + e.getMessage());
        }
    }

    public List<Patient> getAdmittedPatients() {
        return patientRegistry.getAdmittedPatients();
    }

    public int getTotalPatients() {
        return patientRegistry.getTotalPatients();
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors);
    }

    public List<Nurse> getAllNurses() {
        return new ArrayList<>(nurses);
    }

    public HospitalRegistry<Patient> getPatientRegistry() {
        return patientRegistry;
    }
}

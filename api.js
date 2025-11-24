// API Integration with Java Backend
class HospitalAPI {
    constructor(baseUrl = '') {
        this.baseUrl = baseUrl;
    }

    async getPatients() {
        try {
            const response = await fetch(`${this.baseUrl}/api/patients`);
            if (!response.ok) throw new Error('Failed to fetch patients');
            return await response.json();
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    }

    async addPatient(patientData) {
        try {
            const response = await fetch(`${this.baseUrl}/api/patients`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(patientData)
            });
            
            if (!response.ok) throw new Error('Failed to add patient');
            return await response.json();
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    }

    async getPatient(id) {
        try {
            const response = await fetch(`${this.baseUrl}/api/patients/${id}`);
            if (!response.ok) throw new Error('Failed to fetch patient');
            return await response.json();
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    }

    async updatePatient(id, patientData) {
        try {
            const response = await fetch(`${this.baseUrl}/api/patients/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(patientData)
            });
            
            if (!response.ok) throw new Error('Failed to update patient');
            return await response.json();
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    }

    async deletePatient(id) {
        try {
            const response = await fetch(`${this.baseUrl}/api/patients/${id}`, {
                method: 'DELETE'
            });
            
            if (!response.ok) throw new Error('Failed to delete patient');
            return await response.json();
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    }
}

// Global API instance
const hospitalAPI = new HospitalAPI();

// Demo functions using Java API
async function addPatient() {
    const name = document.getElementById('patientName').value;
    const email = document.getElementById('patientEmail').value;
    const phone = document.getElementById('patientPhone').value;

    if (!name || !email || !phone) {
        showOutput('patientOutput', '❌ Please fill in all fields');
        return;
    }

    try {
        const patientData = {
            name: name,
            email: email,
            phone: phone,
            dateOfBirth: '1990-01-01',
            bloodGroup: 'O+'
        };

        const result = await hospitalAPI.addPatient(patientData);
        showOutput('patientOutput', `✅ Patient added successfully!\n${JSON.stringify(result, null, 2)}`);
        
        // Clear form
        document.getElementById('patientName').value = '';
        document.getElementById('patientEmail').value = '';
        document.getElementById('patientPhone').value = '';
        
        // Reload patients list
        loadPatients();
        
    } catch (error) {
        showOutput('patientOutput', `❌ Error adding patient: ${error.message}`);
    }
}

async function loadPatients() {
    try {
        const patients = await hospitalAPI.getPatients();
        displayPatients(patients);
    } catch (error) {
        showOutput('patientOutput', `❌ Error loading patients: ${error.message}`);
    }
}

function displayPatients(patients) {
    const container = document.getElementById('patientsList');
    
    if (!patients || patients.length === 0) {
        container.innerHTML = '<p>No patients found</p>';
        return;
    }

    const table = `
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                ${patients.map(patient => `
                    <tr>
                        <td>${patient.id || 'N/A'}</td>
                        <td>${patient.name}</td>
                        <td>${patient.email}</td>
                        <td>${patient.phone}</td>
                        <td>
                            <button onclick="viewPatient('${patient.id}')" class="btn btn-sm">View</button>
                        </td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;
    
    container.innerHTML = table;
}

async function viewPatient(patientId) {
    try {
        const patient = await hospitalAPI.getPatient(patientId);
        showOutput('patientOutput', `👤 Patient Details:\n${JSON.stringify(patient, null, 2)}`);
    } catch (error) {
        showOutput('patientOutput', `❌ Error fetching patient: ${error.message}`);
    }
}

async function testAPI() {
    try {
        const output = document.getElementById('apiOutput');
        output.innerHTML = 'Testing API endpoints...\n';
        
        // Test GET /api/patients
        output.innerHTML += '🔍 Testing GET /api/patients...\n';
        const patients = await hospitalAPI.getPatients();
        output.innerHTML += `✅ Success! Found ${patients.length} patients\n`;
        
        // Test POST /api/patients
        output.innerHTML += '📝 Testing POST /api/patients...\n';
        const testPatient = {
            name: 'Test Patient',
            email: 'test@hospital.com',
            phone: '555-9999',
            dateOfBirth: '1985-05-15',
            bloodGroup: 'A+'
        };
        
        const newPatient = await hospitalAPI.addPatient(testPatient);
        output.innerHTML += '✅ Patient created successfully!\n';
        
        output.innerHTML += '\n🎉 All API tests passed!';
        
    } catch (error) {
        const output = document.getElementById('apiOutput');
        output.innerHTML += `❌ API Test Failed: ${error.message}\n`;
    }
}

function showOutput(elementId, message) {
    const outputElement = document.getElementById(elementId);
    if (outputElement) {
        outputElement.innerHTML = `<pre>${message}</pre>`;
        outputElement.scrollTop = outputElement.scrollHeight;
    }
}

// Initialize when page loads
document.addEventListener('DOMContentLoaded', function() {
    console.log('Hospital Management System API initialized');
    
    // Load initial data
    loadPatients().catch(console.error);
});

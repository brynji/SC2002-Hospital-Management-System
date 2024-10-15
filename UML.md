


User (Abstract)
----------------
- userID: String
- password: String
- name: String
- email: String
- contactNumber: String 

+ login(userID: String, password: String): boolean
+ changePassword(newPassword: String)
+ updateContactInfo(newEmail: String, newContactNumber: String)



Patient
----------
- patientID: String
- bloodType: String
- medicalRecord: MedicalRecord
- appointments: List<Appointment>

+ viewMedicalRecord()
+ scheduleAppointment(newAppointment: Appointment)
+ rescheduleAppointment(appointment: Appointment, newDate: String, newTime: String)
+ cancelAppointment(appointment: Appointment)
+ viewPastAppointments()
+ displayUserMenu()



Doctor
----------
- doctorID: String
- specialization: String
- availability: Schedule

+ viewPatientRecord()
+ updatePatientRecord()
+ setAvailability()
+ acceptAppointment()
+ declineAppointment()
+ recordAppointmentOutcome(appointmentID: String, serviceType: String, medications: List<Prescription>, consultationNotes: String)
+ displayUserMenu()



Pharmacist
-----------
- pharmacistID: String

+ viewAppointmentOutcome(recordID: String)
+ updatePrescriptionStatus(recordID: String, medicationName: String, newStatus: String)
+ viewInventory()
+ requestReplenishment(medicationName: String, quantity: int)
+ displayUserMenu()



Administrator
----------------
- adminID: String

+ manageStaff(action: String, staffDetails: Map)
+ manageAppointments()
+ viewAppointmentDetails(recordID: String)
+ manageInventory(action: String, medicationName: String, quantity: int)
+ approveReplenishment(requestID: String)
+ displayUserMenu()



Prescription
--------------
- prescriptionID: String
- medicationName: String
- status: String
- quantity: int

+ updateStatus(newStatus: String)
+ getDetails(): String



Inventory
--------------
- medicationName: String
- stockLevel: int
- lowStockAlert: int

+ updateStockLevel(newLevel: int)
+ isLowStock(): boolean
+ getDetails(): String



Appointment
--------------
- appointmentID: String
- patient: Patient
- doctor: Doctor
- appointmentDate: String
- appointmentTime: String
- status: String
- outcomeRecord: AppointmentOutcomeRecord

+ recordOutcome(serviceType: String, medications: List<Prescription>, consultationNotes: String)
+ getDetails(): String



AppointmentOutcomeRecord
---------------------------
- recordID: String
- appointmentDate: String
- serviceType: String
- medications: List<Prescription>
- consultationNotes: String
- status: String (default "pending")

 + getDetails(): String



MedicalRecord
---------------
- patientID: String
- name: String
- dateOfBirth: String
- gender: String
- contactInfo: String
- bloodType: String
- pastRecords: List<AppointmentOutcomeRecord>

+ getDetails(): String
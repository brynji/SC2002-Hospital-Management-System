package Data;

import Users.Patient;

public class PatientRepository extends BaseRepository<Patient> {

    @Override
    public Patient FindById(String userId) {
        for(var patient : patients){
            if(patient.getPatientID().equals(userId)){
                return patient;
            }
        }
        return null;
    }
}

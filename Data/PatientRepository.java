package Data;

import Users.Patient;

public class PatientRepository extends BaseRepository {
    public Patient FindPatientById(String id){
        for(var patient : patients){
            if(patient.getPatientID().equals(id)){
                return patient;
            }
        }
        return null;
    }

}

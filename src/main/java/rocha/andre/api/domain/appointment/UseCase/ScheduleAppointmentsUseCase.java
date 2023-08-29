package rocha.andre.api.domain.appointment.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.Appointment;
import rocha.andre.api.domain.appointment.AppointmentDetaillingData;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.appointment.AppointmentRepository;
import rocha.andre.api.domain.appointment.validations.ValidatorScheduleAppointments;
import rocha.andre.api.domain.doctor.Doctor;
import rocha.andre.api.domain.doctor.DoctorRepository;
import rocha.andre.api.domain.patient.PatientRepository;

import java.util.List;

@Service
public class ScheduleAppointmentsUseCase {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<ValidatorScheduleAppointments> validators;

    public AppointmentDetaillingData schedule(AppointmentDto data) {
        boolean patientExists = patientRepository.existsById(data.patient_id());

        if (!patientExists) {
            throw new ValidationException("patient Id not found in our database.");
        }
        if(data.doctor_id() != null && !doctorRepository.existsById(data.doctor_id())) {
            throw new ValidationException("doctor Id not found in our database.");
        }

        validators.forEach(validator -> validator.validate(data));

        var patient =  patientRepository.getReferenceById(data.patient_id());
        var doctor = chooseDoctor(data);

        if (doctor == null) {
            throw new ValidationException("No doctors are available for the requested date.");
        }

        var appointment = new Appointment(null, doctor, patient, data.date(), null);

        appointmentRepository.save(appointment);

        return new AppointmentDetaillingData(appointment);
    }

    private Doctor chooseDoctor(AppointmentDto data) {
        if (data.doctor_id() != null) {
            var doctor = doctorRepository.getReferenceById(data.doctor_id());
            return doctor;
        }

        if (data.specialty() == null) {
            throw new ValidationException("Specialty not informed and no doctor was chosen.");
        }

        return doctorRepository.chooseRandomDoctorAvailableAtTheDate(data.specialty(), data.date());
    }
}

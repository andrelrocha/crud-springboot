package rocha.andre.api.domain.appointment.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.Appointment;
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

    public void schedule(AppointmentDto data) {
        boolean doctorExists = doctorRepository.existsById(data.doctorId());
        boolean patientExists = patientRepository.existsById(data.patientId());

        if (!patientExists) {
            throw new ValidationException("patient Id not found in our database.");
        }
        if(data.doctorId() != null && !doctorExists) {
            throw new ValidationException("doctor Id not found in our database.");
        }

        validators.forEach(validator -> validator.validate(data));

        var patient =  patientRepository.getReferenceById(data.patientId());
        var doctor = chooseDoctor(data);

        var appointment = new Appointment(null, doctor, patient, data.date(), null);

        appointmentRepository.save(appointment);
    }

    private Doctor chooseDoctor(AppointmentDto data) {
        if (data.doctorId() != null) {
            var doctor = doctorRepository.getReferenceById(data.doctorId());
            return doctor;
        }

        if (data.specialty() == null) {
            throw new ValidationException("Specialty not informed and no doctor was chosen.");
        }

        return doctorRepository.chooseRandomDoctorAvailableAtTheDate(data.specialty(), data.date());

    }
}

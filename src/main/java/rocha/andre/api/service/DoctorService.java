package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;
import rocha.andre.api.domain.doctor.DataDoctor;
import rocha.andre.api.domain.doctor.DataListDoctor;
import rocha.andre.api.domain.doctor.DataUpdateDoctor;
import rocha.andre.api.domain.doctor.Doctor;

public interface DoctorService {
    public Page<DataListDoctor> getAllDoctors(Pageable pagination);
    public Doctor getDoctorById(Long id);
    public DataListDoctor createDoctor(DataDoctor data, UriComponentsBuilder uriBuilder);
    public Doctor updateDoctor(DataUpdateDoctor data);
    public void deleteDoctor(Long id);
}

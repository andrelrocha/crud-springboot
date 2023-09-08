package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.doctor.DoctorDTO;
import rocha.andre.api.domain.doctor.DoctorReturnDTO;
import rocha.andre.api.domain.doctor.DoctorUpdateDTO;

public interface DoctorService {
    public Page<DoctorReturnDTO> getAllDoctors(Pageable pagination);
    public DoctorReturnDTO getDoctorById(Long id);
    public DoctorReturnDTO createDoctor(DoctorDTO data);
    public DoctorReturnDTO updateDoctor(DoctorUpdateDTO data);
    public void deleteDoctor(Long id);
}

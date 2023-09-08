package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.doctor.DoctorDTO;
import rocha.andre.api.domain.doctor.DoctorReturnDTO;
import rocha.andre.api.domain.doctor.DoctorUpdateDTO;
import rocha.andre.api.domain.doctor.UseCase.*;
import rocha.andre.api.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private ListDoctorUseCase listDoctorUseCase;
    @Autowired
    private ListDoctorByIdUseCase listDoctorByIdUseCase;
    @Autowired
    private CreateDoctorUseCase createDoctorUseCase;
    @Autowired
    private DeleteDoctorUseCase deleteDoctorUseCase;
    @Autowired
    private UpdateDoctorUseCase updateDoctorUseCase;

    @Override
    public Page<DoctorReturnDTO> getAllDoctors(Pageable pagination) {
        var vendors = listDoctorUseCase.listDoctor(pagination);
        return vendors;
    }

    @Override
    public DoctorReturnDTO getDoctorById(Long id) {
        var doctorById = listDoctorByIdUseCase.listDoctorById(id);
        return doctorById;
    }

    @Override
    public DoctorReturnDTO createDoctor(DoctorDTO data) {
        var newDoctor = createDoctorUseCase.createDoctor(data);
        return newDoctor;
    }

    @Override
    public DoctorReturnDTO updateDoctor(DoctorUpdateDTO data) {
        var doctorUpdated = updateDoctorUseCase.updateDoctor(data);
        return doctorUpdated;
    }

    @Override
    public void deleteDoctor(Long id) {
        deleteDoctorUseCase.deleteDoctor(id);
    }
}

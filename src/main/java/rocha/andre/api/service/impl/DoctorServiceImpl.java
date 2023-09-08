package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import rocha.andre.api.domain.doctor.DataDoctor;
import rocha.andre.api.domain.doctor.DataListDoctor;
import rocha.andre.api.domain.doctor.DataUpdateDoctor;
import rocha.andre.api.domain.doctor.Doctor;
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
    public Page<DataListDoctor> getAllDoctors(Pageable pagination) {
        var vendors = listDoctorUseCase.listDoctor(pagination);
        return vendors;
    }

    @Override
    public Doctor getDoctorById(Long id) {
        var doctorById = listDoctorByIdUseCase.listDoctorById(id);
        return doctorById;
    }

    @Override
    public DataListDoctor createDoctor(DataDoctor data, UriComponentsBuilder uriBuilder) {
        return null;
    }

    @Override
    public Doctor updateDoctor(DataUpdateDoctor data) {
        return null;
    }

    @Override
    public void deleteDoctor(Long id) {

    }
}

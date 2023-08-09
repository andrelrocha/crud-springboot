package rocha.andre.api.doctor.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.doctor.DataListDoctor;
import rocha.andre.api.doctor.DoctorRepository;

import java.util.List;

@Component
public class ListDoctorUseCase {
    private DoctorRepository repository;

    public ListDoctorUseCase(DoctorRepository repository) {
        this.repository = repository;
    }

    public List<DataListDoctor> listDoctor() {
        // O método map() é uma operação do Stream que permite transformar cada elemento da sequência
        // em outro objeto usando uma função de mapeamento.

        return repository.findAll().stream().map(DataListDoctor::new).toList();
    }
}

package rocha.andre.api.doctor;

import jakarta.persistence.*;
import lombok.*;

import rocha.andre.api.address.Address;

@Table(name = "doctors")
@Entity(name = "Doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String telephone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    //nao cria relação, na table de doctor ficará salva a classe address
    @Embedded
    private Address address;

    public Doctor (DataDoctor data) {
        this.name = data.name();
        this.email = data.email();
        this.telephone = data.telephone();
        this.crm = data.crm();
        this.specialty = data.specialty();
        this.address = new Address(data.address());
    }

    public void updateData(DataUpdateDoctor data) {
        if (data.name() != null) {
            this.name = data.name();
        }

        if (data.telephone() != null) {
            this.telephone = data.telephone();
        }

        if (data.address() != null) {
            this.address.updateData(data.address());
        }
    }
}

package rocha.andre.api.address;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private String district;
    private String zip;
    private String city;
    private String state;
    private String number;
    private String complement;

    public Address(DataAddress data) {
        this.street = data.street();
        this.district = data.district();
        this.zip = data.zipCode();
        this.city = data.city();
        this.state = data.state();
        this.number = data.number();
        this.complement = data.complement();
    }
}

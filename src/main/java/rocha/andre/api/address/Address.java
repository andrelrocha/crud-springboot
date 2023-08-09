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

    public void updateData(DataAddress data) {
        if (data.street() != null) {
            this.street = data.street();
        }

        if (data.district() != null) {
            this.district = data.district();
        }

        if (data.zipCode() != null) {
            this.zip = data.zipCode();
        }

        if (data.state() != null) {
            this.state = data.state();
        }

        if (data.number() != null) {
            this.number = data.number();
        }

        if (data.complement() != null) {
            this.complement = data.complement();
        }
    }
}

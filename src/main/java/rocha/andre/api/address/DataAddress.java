package rocha.andre.api.address;

public record DataAddress(String street,
                          String district,
                          String zipCode,
                          String city,
                          String state,
                          String number,
                          String complement) {
}

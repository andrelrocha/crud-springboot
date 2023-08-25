create table appointments(
    id bigint not null auto_increment,
    doctorId bigint not null,
    patientId bigint not null,
    date datetime not null,

    primary key(id),
    constraint fk_appointments_doctorId foreign key(doctorId) references doctors(id),
    constraint fk_appointments_patientId foreign key(patientId) references patients(id)

)
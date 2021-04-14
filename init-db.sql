CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE  TABLE IF NOT EXISTS bookings
(
    id         uuid default uuid_generate_v4() not null
    constraint bookings_pk
    primary key,
    card_id    uuid                            not null,
    start_date timestamp                       not null,
    end_date   timestamp                       not null,
    status     varchar(255)                    not null
);

alter table bookings
    owner to hsedjame;

create unique index bookings_id_uindex
    on bookings (id);

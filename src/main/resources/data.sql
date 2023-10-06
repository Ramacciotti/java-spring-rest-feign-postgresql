create table address
(
    id        uuid not null,
    city      varchar(255),
    street    varchar(255),
    suite     varchar(255),
    zipcode   varchar(255),
    person_id uuid,
    primary key (id)
)

create table company
(
    id           uuid not null,
    bs           varchar(255),
    catch_phrase varchar(255),
    name         varchar(255),
    person_id    uuid,
    primary key (id)
)

create table geo
(
    id         uuid not null,
    lat        varchar(255),
    lng        varchar(255),
    address_id uuid,
    primary key (id)
)

create table person
(
    id       uuid not null,
    email    varchar(255),
    name     varchar(255),
    phone    varchar(255),
    username varchar(255),
    website  varchar(255),
    primary key (id)
)

alter table if exists address
    add constraint FK81ihijcn1kdfwffke0c0sjqeb
    foreign key (person_id)
    references person

alter table if exists company
    add constraint FK29qmftcirjgc06i9n5clkxlk8
    foreign key (person_id)
    references person

alter table if exists geo
    add constraint FK5wrqffc1r4lh16tibwcotrdhi
    foreign key (address_id)
    references address
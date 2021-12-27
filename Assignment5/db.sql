-- Create table Guest
CREATE TABLE guest
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    name    VARCHAR(255)          NOT NULL,
    surname VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_guest PRIMARY KEY (id)
);

ALTER TABLE guest
    ADD CONSTRAINT uc_guest_id UNIQUE (id);

-- Create table Accommodation
CREATE TABLE accommodation
(
    id    BIGINT AUTO_INCREMENT NOT NULL,
    name  VARCHAR(255)          NOT NULL,
    price INT                   NOT NULL,
    CONSTRAINT pk_accommodation PRIMARY KEY (id)
);

-- Create table Hotel, parents Accommodation
CREATE TABLE hotel
(
    id               BIGINT NOT NULL,
    extra_half_board INT    NOT NULL,
    stars            INT    NOT NULL,
    places           INT    NOT NULL,
    CONSTRAINT pk_hotel PRIMARY KEY (id)
);

ALTER TABLE hotel
    ADD CONSTRAINT FK_HOTEL_ON_ID FOREIGN KEY (id) REFERENCES accommodation (id);

-- Create table Apartment, parents Accommodation
CREATE TABLE apartment
(
    id             BIGINT NOT NULL,
    final_cleaning INT    NOT NULL,
    max_n_persons  INT    NOT NULL,
    CONSTRAINT pk_apartment PRIMARY KEY (id)
);

ALTER TABLE apartment
    ADD CONSTRAINT FK_APARTMENT_ON_ID FOREIGN KEY (id) REFERENCES accommodation (id);

-- Create table Reservation, linked to Guest and Accommodation
CREATE TABLE reservation
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    guest_id           BIGINT                NULL,
    accommodation_id   BIGINT                NULL,
    persons_number     INT                   NOT NULL,
    credit_card_number INT                   NOT NULL,
    `from`             datetime              NOT NULL,
    `to`               datetime              NOT NULL,
    half_board         BIT(1)                NULL,
    CONSTRAINT pk_reservation PRIMARY KEY (id)
);

ALTER TABLE reservation
    ADD CONSTRAINT FK_RESERVATION_ON_ACCOMMODATION FOREIGN KEY (accommodation_id) REFERENCES accommodation (id);

ALTER TABLE reservation
    ADD CONSTRAINT FK_RESERVATION_ON_GUEST FOREIGN KEY (guest_id) REFERENCES guest (id);

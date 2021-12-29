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
    id             BIGINT NOT NULL,
    extraHalfBoard INT    NOT NULL,
    stars          INT    NOT NULL,
    places         INT    NOT NULL,
    CONSTRAINT pk_hotel PRIMARY KEY (id)
);

ALTER TABLE hotel
    ADD CONSTRAINT FK_HOTEL_ON_ID FOREIGN KEY (id) REFERENCES accommodation (id);

-- Create table Apartment, parents Accommodation
CREATE TABLE apartment
(
    id            BIGINT NOT NULL,
    finalCleaning INT    NOT NULL,
    maxPersons    INT    NOT NULL,
    CONSTRAINT pk_apartment PRIMARY KEY (id)
);

ALTER TABLE apartment
    ADD CONSTRAINT FK_APARTMENT_ON_ID FOREIGN KEY (id) REFERENCES accommodation (id);

-- Create table Reservation, linked to Guest and Accommodation
CREATE TABLE reservation
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    guest_id         BIGINT                NULL,
    accommodation_id BIGINT                NULL,
    nPersons         INT                   NOT NULL,
    creditCardNumber VARCHAR(255)          NOT NULL,
    dateFrom         datetime              NOT NULL,
    dateTo           datetime              NOT NULL,
    halfBoard        VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_reservation PRIMARY KEY (id)
);

ALTER TABLE reservation
    ADD CONSTRAINT FK_RESERVATION_ON_ACCOMMODATION FOREIGN KEY (accommodation_id) REFERENCES accommodation (id);

ALTER TABLE reservation
    ADD CONSTRAINT FK_RESERVATION_ON_GUEST FOREIGN KEY (guest_id) REFERENCES guest (id);

-- Insert of apartments and hotel
INSERT INTO accommodation (name, price) VALUES ('Artemide', 100);
INSERT INTO accommodation (name, price) VALUES ('Majestic', 65);
INSERT INTO accommodation (name, price) VALUES ('Palace', 200);
INSERT INTO accommodation (name, price) VALUES ('Zenith', 70);
INSERT INTO hotel (id, extraHalfBoard, stars, places) VALUES (1, 20, 4, 60);
INSERT INTO hotel (id, extraHalfBoard, stars, places) VALUES (2, 15, 3, 50);
INSERT INTO hotel (id, extraHalfBoard, stars, places) VALUES (3, 30, 5, 25);
INSERT INTO hotel (id, extraHalfBoard, stars, places) VALUES (4, 18, 3, 40);

INSERT INTO accommodation (name, price) VALUES ('Pietra Bianca', 40);
INSERT INTO accommodation (name, price) VALUES ('Sapore Di Sale', 80);
INSERT INTO accommodation (name, price) VALUES ('Tenuta Di Artimino', 60);
INSERT INTO apartment (id, finalCleaning, nPersons) VALUES (5, 15, 4);
INSERT INTO apartment (id, finalCleaning, nPersons) VALUES (6, 20, 8);
INSERT INTO apartment (id, finalCleaning, nPersons) VALUES (7, 12, 6);

insert into reservation (guest_id, accommodation_id, nPersons, creditCardNumber, dateFrom, dateTo, halfBoard)  values (1, 1, 2, '055', '20220210', '20220214', 'Yes');

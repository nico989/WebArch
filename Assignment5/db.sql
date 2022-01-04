-- Create table Guest
CREATE TABLE webarch.guest
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    name    VARCHAR(255)          NOT NULL,
    surname VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_guest PRIMARY KEY (id)
);

-- Create table Accommodation
CREATE TABLE webarch.accommodation
(
    id    BIGINT AUTO_INCREMENT NOT NULL,
    name  VARCHAR(255)          NOT NULL,
    price INT                   NOT NULL,
    CONSTRAINT pk_accommodation PRIMARY KEY (id)
);

-- Create table Hotel, parents Accommodation
CREATE TABLE webarch.hotel
(
    id             BIGINT NOT NULL,
    extraHalfBoard INT    NOT NULL,
    stars          INT    NOT NULL,
    places         INT    NOT NULL,
    CONSTRAINT pk_hotel PRIMARY KEY (id)
);

ALTER TABLE webarch.hotel
    ADD CONSTRAINT FK_HOTEL_ON_ID FOREIGN KEY (id) REFERENCES webarch.accommodation (id);

-- Create table Apartment, parents Accommodation
CREATE TABLE webarch.apartment
(
    id            BIGINT NOT NULL,
    finalCleaning INT    NOT NULL,
    maxPersons    INT    NOT NULL,
    CONSTRAINT pk_apartment PRIMARY KEY (id)
);

ALTER TABLE webarch.apartment
    ADD CONSTRAINT FK_APARTMENT_ON_ID FOREIGN KEY (id) REFERENCES webarch.accommodation (id);

-- Create table Reservation/ReservationHotel, linked to Guest and Accommodation
CREATE TABLE webarch.reservation
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    TYPE             VARCHAR(31)           NULL,
    guestId          BIGINT                NOT NULL,
    accommodationId  BIGINT                NOT NULL,
    nPersons         INT                   NOT NULL,
    creditCardNumber VARCHAR(255)          NOT NULL,
    dateFrom         datetime              NOT NULL,
    dateTo           datetime              NOT NULL,
    halfBoard        BIT(1)                NULL,
    CONSTRAINT pk_reservation PRIMARY KEY (id)
);

ALTER TABLE webarch.reservation
    ADD CONSTRAINT FK_RESERVATION_ON_ACCOMMODATIONID FOREIGN KEY (accommodationId) REFERENCES webarch.accommodation (id);

ALTER TABLE webarch.reservation
    ADD CONSTRAINT FK_RESERVATION_ON_GUESTID FOREIGN KEY (guestId) REFERENCES webarch.guest (id);

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
INSERT INTO apartment (id, finalCleaning, maxPersons) VALUES (5, 15, 4);
INSERT INTO apartment (id, finalCleaning, maxPersons) VALUES (6, 20, 8);
INSERT INTO apartment (id, finalCleaning, maxPersons) VALUES (7, 12, 6);

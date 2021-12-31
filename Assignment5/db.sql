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
    guestId          BIGINT                NOT NULL,
    accommodationId  BIGINT                NOT NULL,
    nPersons         INT                   NOT NULL,
    creditCardNumber VARCHAR(255)          NOT NULL,
    dateFrom         datetime              NOT NULL,
    dateTo           datetime              NOT NULL,
    halfBoard        VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_reservation PRIMARY KEY (id)
);

ALTER TABLE reservation
    ADD CONSTRAINT FK_RESERVATION_ON_ACCOMMODATIONID FOREIGN KEY (accommodationId) REFERENCES accommodation (id);

ALTER TABLE reservation
    ADD CONSTRAINT FK_RESERVATION_ON_GUESTID FOREIGN KEY (guestId) REFERENCES guest (id);

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

-- Return all apartments/hotel
select * from accommodation inner join apartment on accommodation.id=apartment.id;
select * from accommodation inner join apartment on accommodation.id=apartment.id where apartment.id=5;
select * from accommodation inner join hotel on accommodation.id=hotel.id;
select * from accommodation inner join hotel on accommodation.id=hotel.id where apartment.id=5;

-- Check apartment availability
select * from reservation where dateFrom >= '20220212' and  dateTo <= '20220213';
select * from reservation where dateFrom between '20220212' and '20220213';
select * from reservation where dateTo between '20220212' and '20220213';

select * from apartment where id not in (select accommodation_id  from reservation where dateFrom >= '20220212' and  dateTo <= '20220213');
select * from apartment where id not in (select accommodation_id  from reservation where dateFrom between '20220212' and '20220213');
select * from apartment where id not in (select accommodation_id  from reservation where dateTo between '20220212' and '20220213');

select * from reservation where (dateFrom >= '20220212' and  dateTo <= '20220213') or (dateFrom between '20220212' and '20220213') or (dateTo between '20220212' and '20220213');

select * from accommodation as acc join apartment as ap on acc.id=ap.id where ap.id not in 
(select accommodation_id from reservation as r join apartment as ap2 on r.accommodation_id=ap2.id
   where (r.dateFrom<='20220211' and r.dateTo>='20220216') or (r.dateFrom>='20220211' and r.dateTo>='20220216') 
    or (r.dateFrom<='20220211' and r.dateTo<='20220216' and r.dateTo>='20220211') or (r.dateFrom>='20220211' and r.dateTo<='20220216'));

select * from accommodation join apartment on accommodation.id=apartment.id where apartment.id in (select reservation.accommodationId from reservation where dateFrom > '20220208' or dateTo < '20220207');

-- Check hotel availability
select * from accommodation as acc join hotel as h on acc.id=h.id left join 
(select r.accommodation_id, sum(npersons) as occupied from reservation as r
   where (r.dateFrom<='20220211' and r.dateTo>='20220216') or (r.dateFrom>='20220211' and r.dateTo>='20220216') 
    or (r.dateFrom<='20220211' and r.dateTo<='20220216' and r.dateTo>='20220211') or (r.dateFrom>='20220211' and r.dateTo<='20220216')
   group by r.accommodation_id) as tmp on tmp.accommodation_id=h.id
where h.places>tmp.occupied+9 or tmp.occupied is null;

select accommodationId, sum(nPersons) as occ from reservation where dateFrom > '20220208' or dateTo < '20220207' group by accommodationId;

select * from accommodation join hotel on accommodation.id=hotel.id join 
(select accommodationId, sum(nPersons) as occ from reservation where dateFrom > '20220208' or dateTo < '20220207' group by accommodationId) on accommodationId=hotel.id
where places>occ+20;

-- Check hotel/apartments availability
select * from reservation where dateFrom > inputDateTo or dateTo < inputDateFrom;
select * from reservation where dateFrom > '20220208' or dateTo < '20220207';

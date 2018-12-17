CREATE KEYSPACE IF NOT EXISTS HRS with replication={'class': 'SimpleStrategy', 'replication_factor': 1 };
USE HRS;

CREATE TYPE address (street text, city text, state text, zip text, country text);

CREATE TABLE center (id text PRIMARY KEY, name text, street text, city text, state text, zip text, balance double);

CREATE TABLE hotel (h_id text PRIMARY KEY, id int, name text, street text, city text, state text, zip text, phone text, url text, rating double, tax double, balance double, min_price double, image_id text, description text, cancelPolicy text, no_rooms int);

CREATE TABLE features_by_hotel(h_id text, ft_name text, status text, PRIMARY KEY((h_id), ft_name));
CREATE TABLE facilities_by_hotel(h_id text, fc_name text, status text, PRIMARY KEY((h_id), fc_name));
CREATE TABLE policies_by_hotel(h_id text, pl_name text, status text, PRIMARY KEY((h_id), pl_name));
CREATE TABLE reviews_by_hotel(h_id text, review_id text, reviewTitle text, reviewText text, reviewRating double, hotelName text, occupation text, gender text, usernName text, userEmail text, age int, PRIMARY KEY((h_id), review_id));

CREATE TABLE room_type (h_id text, name text, max_people int, no_rooms int, no_av_rooms int, price double, data text, PRIMARY KEY (h_id, name));

CREATE TABLE room (h_id text, id int, rt_name text, hotelName text, hotelCity text, hotelState text, hotelZip text, hotelStreet text, floor int, price double, status text, image text, data text, PRIMARY KEY(h_id, id));

CREATE TABLE amenities (h_id text, r_id int, name text, info text, status text, PRIMARY KEY((h_id, r_id), name));

CREATE TABLE room_availabilities (h_id text, r_id int, startDate bigint, endDate bigint, status bigint, PRIMARY KEY((h_id, r_id), startDate, endDate));

CREATE TABLE guest (id UUID, full_name text, phone text, type text, email text, password text, occupation text, gender text, age int, since text, discount double, credit_st text, credit_lm int, payment_id text, balance double, ytd_pm double, PRIMARY KEY((id),email));

CREATE TABLE guest_member (g_id UUID, name text, age int, email text, phone text, PRIMARY KEY((g_id), email));

CREATE TABLE reservation (g_email text, g_id UUID, h_id text, startDate text, endDate text, cf_no text, bookingDate text, name text, PRIMARY KEY((g_email, g_id, h_id), cf_no));

CREATE TABLE reservation_item (g_email text, g_id UUID, h_id text, r_id int, rt_name text, start_date date, nights int, no_rooms int, price double, data text, PRIMARY KEY((g_email, g_id, h_id, r_id), rt_name));

CREATE TABLE facility_usage (g_id UUID, h_id text, fc_name text, used_date date, info text, amount double, amt_paid double, status text, data text, PRIMARY KEY(g_id, h_id, fc_name));

CREATE TABLE payment_bill (g_id UUID, i_id text, pm_date date, info text, amount double, data text, PRIMARY KEY((g_id, i_id), pm_date));
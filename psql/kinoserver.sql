--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: favorites; Type: TABLE; Schema: public; Owner: product; Tablespace: 
--

CREATE TABLE favorites (
    user_id integer NOT NULL,
    music_id integer NOT NULL,
    date_time timestamp without time zone
);


ALTER TABLE public.favorites OWNER TO product;

--
-- Name: film; Type: TABLE; Schema: public; Owner: product; Tablespace: 
--

CREATE TABLE film (
    id integer NOT NULL,
    name character varying(255),
    year integer,
    img integer,
    rating double precision
);


ALTER TABLE public.film OWNER TO product;

--
-- Name: film_history; Type: TABLE; Schema: public; Owner: product; Tablespace: 
--

CREATE TABLE film_history (
    id integer NOT NULL,
    film_id integer NOT NULL,
    method character varying(45),
    date_time timestamp without time zone
);


ALTER TABLE public.film_history OWNER TO product;

--
-- Name: film_music; Type: TABLE; Schema: public; Owner: product; Tablespace: 
--

CREATE TABLE film_music (
    film_id integer NOT NULL,
    music_id integer NOT NULL
);


ALTER TABLE public.film_music OWNER TO product;

--
-- Name: music; Type: TABLE; Schema: public; Owner: product; Tablespace: 
--

CREATE TABLE music (
    id integer NOT NULL,
    name character varying(45),
    performer_id integer,
    rating double precision
);


ALTER TABLE public.music OWNER TO product;

--
-- Name: music_rating; Type: TABLE; Schema: public; Owner: product; Tablespace: 
--

CREATE TABLE music_rating (
    music_id integer NOT NULL,
    user_id integer NOT NULL,
    value integer,
    date_time timestamp without time zone
);


ALTER TABLE public.music_rating OWNER TO product;

--
-- Name: performer; Type: TABLE; Schema: public; Owner: product; Tablespace: 
--

CREATE TABLE performer (
    id integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.performer OWNER TO product;

--
-- Name: user; Type: TABLE; Schema: public; Owner: product; Tablespace: 
--

CREATE TABLE "user" (
    id integer NOT NULL,
    name character varying(45)
);


ALTER TABLE public."user" OWNER TO product;

--
-- Data for Name: favorites; Type: TABLE DATA; Schema: public; Owner: product
--

COPY favorites (user_id, music_id, date_time) FROM stdin;
\.


--
-- Data for Name: film; Type: TABLE DATA; Schema: public; Owner: product
--

COPY film (id, name, year, img, rating) FROM stdin;
\.


--
-- Data for Name: film_history; Type: TABLE DATA; Schema: public; Owner: product
--

COPY film_history (id, film_id, method, date_time) FROM stdin;
\.


--
-- Data for Name: film_music; Type: TABLE DATA; Schema: public; Owner: product
--

COPY film_music (film_id, music_id) FROM stdin;
\.


--
-- Data for Name: music; Type: TABLE DATA; Schema: public; Owner: product
--

COPY music (id, name, performer_id, rating) FROM stdin;
\.


--
-- Data for Name: music_rating; Type: TABLE DATA; Schema: public; Owner: product
--

COPY music_rating (music_id, user_id, value, date_time) FROM stdin;
\.


--
-- Data for Name: performer; Type: TABLE DATA; Schema: public; Owner: product
--

COPY performer (id, name) FROM stdin;
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: product
--

COPY "user" (id, name) FROM stdin;
\.


--
-- Name: favorites_user_id_music_id_pkey; Type: CONSTRAINT; Schema: public; Owner: product; Tablespace: 
--

ALTER TABLE ONLY favorites
    ADD CONSTRAINT favorites_user_id_music_id_pkey PRIMARY KEY (user_id, music_id);


--
-- Name: film_history_id_pkey; Type: CONSTRAINT; Schema: public; Owner: product; Tablespace: 
--

ALTER TABLE ONLY film_history
    ADD CONSTRAINT film_history_id_pkey PRIMARY KEY (id);


--
-- Name: film_id_pkey; Type: CONSTRAINT; Schema: public; Owner: product; Tablespace: 
--

ALTER TABLE ONLY film
    ADD CONSTRAINT film_id_pkey PRIMARY KEY (id);


--
-- Name: film_music_film_id_music_id_pkey; Type: CONSTRAINT; Schema: public; Owner: product; Tablespace: 
--

ALTER TABLE ONLY film_music
    ADD CONSTRAINT film_music_film_id_music_id_pkey PRIMARY KEY (film_id, music_id);


--
-- Name: music_id_pkey; Type: CONSTRAINT; Schema: public; Owner: product; Tablespace: 
--

ALTER TABLE ONLY music
    ADD CONSTRAINT music_id_pkey PRIMARY KEY (id);


--
-- Name: music_rating_music_id_user_id_pkey; Type: CONSTRAINT; Schema: public; Owner: product; Tablespace: 
--

ALTER TABLE ONLY music_rating
    ADD CONSTRAINT music_rating_music_id_user_id_pkey PRIMARY KEY (music_id, user_id);


--
-- Name: performer_id_pkey; Type: CONSTRAINT; Schema: public; Owner: product; Tablespace: 
--

ALTER TABLE ONLY performer
    ADD CONSTRAINT performer_id_pkey PRIMARY KEY (id);


--
-- Name: user_id_pkey; Type: CONSTRAINT; Schema: public; Owner: product; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_id_pkey PRIMARY KEY (id);


--
-- Name: favorites_music_id; Type: INDEX; Schema: public; Owner: product; Tablespace: 
--

CREATE INDEX favorites_music_id ON favorites USING btree (music_id);


--
-- Name: favorites_user_id; Type: INDEX; Schema: public; Owner: product; Tablespace: 
--

CREATE INDEX favorites_user_id ON favorites USING btree (user_id);


--
-- Name: film_history_film_id; Type: INDEX; Schema: public; Owner: product; Tablespace: 
--

CREATE INDEX film_history_film_id ON film_history USING btree (film_id);


--
-- Name: film_music_film_id; Type: INDEX; Schema: public; Owner: product; Tablespace: 
--

CREATE INDEX film_music_film_id ON film_music USING btree (film_id);


--
-- Name: film_music_music_id; Type: INDEX; Schema: public; Owner: product; Tablespace: 
--

CREATE INDEX film_music_music_id ON film_music USING btree (music_id);


--
-- Name: film_name; Type: INDEX; Schema: public; Owner: product; Tablespace: 
--

CREATE INDEX film_name ON film USING btree (name);


--
-- Name: film_year; Type: INDEX; Schema: public; Owner: product; Tablespace: 
--

CREATE INDEX film_year ON film USING btree (year);


--
-- Name: music_name; Type: INDEX; Schema: public; Owner: product; Tablespace: 
--

CREATE INDEX music_name ON music USING btree (name);


--
-- Name: music_performer_id; Type: INDEX; Schema: public; Owner: product; Tablespace: 
--

CREATE INDEX music_performer_id ON music USING btree (performer_id);


--
-- Name: music_rating_music_id; Type: INDEX; Schema: public; Owner: product; Tablespace: 
--

CREATE INDEX music_rating_music_id ON music_rating USING btree (music_id);


--
-- Name: music_rating_user_id; Type: INDEX; Schema: public; Owner: product; Tablespace: 
--

CREATE INDEX music_rating_user_id ON music_rating USING btree (user_id);


--
-- Name: performer_name; Type: INDEX; Schema: public; Owner: product; Tablespace: 
--

CREATE INDEX performer_name ON performer USING btree (name);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--


--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.3.5
-- Started on 2015-05-25 14:57:19

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 1941 (class 1262 OID 53235)
-- Name: Large_Systems_MiniProject; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "Large_Systems_MiniProject" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Danish_Denmark.1252' LC_CTYPE = 'Danish_Denmark.1252';


ALTER DATABASE "Large_Systems_MiniProject" OWNER TO postgres;

\connect "Large_Systems_MiniProject"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 172 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1944 (class 0 OID 0)
-- Dependencies: 172
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 53261)
-- Name: countries; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE countries (
  id integer NOT NULL,
  alpha2code character varying(2),
  alpha3code character varying(3),
  currency character varying(3),
  name character varying(255) NOT NULL
);


ALTER TABLE public.countries OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 53259)
-- Name: countries_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE countries_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE public.countries_id_seq OWNER TO postgres;

--
-- TOC entry 1945 (class 0 OID 0)
-- Dependencies: 170
-- Name: countries_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE countries_id_seq OWNED BY countries.id;


--
-- TOC entry 1823 (class 2604 OID 53264)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY countries ALTER COLUMN id SET DEFAULT nextval('countries_id_seq'::regclass);


--
-- TOC entry 1936 (class 0 OID 53261)
-- Dependencies: 171
-- Data for Name: countries; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY countries (id, alpha2code, alpha3code, currency, name) FROM stdin;
\.


--
-- TOC entry 1946 (class 0 OID 0)
-- Dependencies: 170
-- Name: countries_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('countries_id_seq', 1, false);


--
-- TOC entry 1825 (class 2606 OID 53266)
-- Name: countries_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY countries
ADD CONSTRAINT countries_pkey PRIMARY KEY (id);


--
-- TOC entry 1827 (class 2606 OID 53268)
-- Name: uk_4wy0jt9nm7f9bfviwksp4v5j2; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY countries
ADD CONSTRAINT uk_4wy0jt9nm7f9bfviwksp4v5j2 UNIQUE (name);


--
-- TOC entry 1943 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-05-25 14:57:19

--
-- PostgreSQL database dump complete
--


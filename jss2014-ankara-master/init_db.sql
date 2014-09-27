--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.4
-- Dumped by pg_dump version 9.3.4
-- Started on 2014-07-11 10:22:05 EEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 174 (class 3079 OID 11791)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1992 (class 0 OID 0)
-- Dependencies: 174
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 16580)
-- Name: halls; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE halls (
    id integer NOT NULL,
    rows integer,
    cols integer,
    name text
);


ALTER TABLE public.halls OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 16578)
-- Name: halls_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE halls_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.halls_id_seq OWNER TO postgres;

--
-- TOC entry 1993 (class 0 OID 0)
-- Dependencies: 170
-- Name: halls_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE halls_id_seq OWNED BY halls.id;


--
-- TOC entry 172 (class 1259 OID 16589)
-- Name: tickets; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tickets (
    hour integer,
    "hallId" integer,
    "row" integer,
    col integer,
    id integer NOT NULL
);


ALTER TABLE public.tickets OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 16600)
-- Name: tickets_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tickets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tickets_id_seq OWNER TO postgres;

--
-- TOC entry 1994 (class 0 OID 0)
-- Dependencies: 173
-- Name: tickets_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tickets_id_seq OWNED BY tickets.id;


--
-- TOC entry 1870 (class 2604 OID 16583)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY halls ALTER COLUMN id SET DEFAULT nextval('halls_id_seq'::regclass);


--
-- TOC entry 1871 (class 2604 OID 16602)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tickets ALTER COLUMN id SET DEFAULT nextval('tickets_id_seq'::regclass);


--
-- TOC entry 1873 (class 2606 OID 16588)
-- Name: pk_halls; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY halls
    ADD CONSTRAINT pk_halls PRIMARY KEY (id);


--
-- TOC entry 1876 (class 2606 OID 16608)
-- Name: pk_tickets; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tickets
    ADD CONSTRAINT pk_tickets PRIMARY KEY (id);


--
-- TOC entry 1874 (class 1259 OID 16599)
-- Name: fki_ticket_hall; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_ticket_hall ON tickets USING btree ("hallId");


--
-- TOC entry 1877 (class 2606 OID 16594)
-- Name: fk_ticket_hall; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tickets
    ADD CONSTRAINT fk_ticket_hall FOREIGN KEY ("hallId") REFERENCES halls(id);


--
-- TOC entry 1991 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-07-11 10:22:05 EEST

--
-- PostgreSQL database dump complete
--


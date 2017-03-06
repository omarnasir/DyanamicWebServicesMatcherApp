--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 9.6.2

-- Started on 2017-03-06 14:44:31

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12387)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2151 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 185 (class 1259 OID 16418)
-- Name: Element; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Element" (
    "ElementID" integer NOT NULL,
    "OperationID" integer NOT NULL,
    "ElementName" text,
    "Annotation" text
);


ALTER TABLE "Element" OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 16424)
-- Name: Operation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Operation" (
    "OperationID" integer NOT NULL,
    "ServiceID" integer NOT NULL,
    "OperationName" text,
    "MessageName" text,
    "OperationType" integer
);


ALTER TABLE "Operation" OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 16430)
-- Name: Service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Service" (
    "ServiceID" integer NOT NULL,
    "ServiceName" text NOT NULL,
    "WSDLName" text
);


ALTER TABLE "Service" OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 16436)
-- Name: seqElement; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "seqElement"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 9999999
    CACHE 1;


ALTER TABLE "seqElement" OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 16438)
-- Name: seqOperation; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "seqOperation"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 99999999
    CACHE 1;


ALTER TABLE "seqOperation" OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 16440)
-- Name: seqService; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "seqService"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1;


ALTER TABLE "seqService" OWNER TO postgres;


--
-- TOC entry 2152 (class 0 OID 0)
-- Dependencies: 188
-- Name: seqElement; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"seqElement"', 1, true);


--
-- TOC entry 2153 (class 0 OID 0)
-- Dependencies: 189
-- Name: seqOperation; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"seqOperation"', 1, true);


--
-- TOC entry 2154 (class 0 OID 0)
-- Dependencies: 190
-- Name: seqService; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"seqService"', 1, true);


--
-- TOC entry 2017 (class 2606 OID 16443)
-- Name: Element Element_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Element"
    ADD CONSTRAINT "Element_pkey" PRIMARY KEY ("ElementID");


--
-- TOC entry 2019 (class 2606 OID 16445)
-- Name: Operation OutputOperation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Operation"
    ADD CONSTRAINT "OutputOperation_pkey" PRIMARY KEY ("OperationID");


--
-- TOC entry 2021 (class 2606 OID 16447)
-- Name: Service Service_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Service"
    ADD CONSTRAINT "Service_pkey" PRIMARY KEY ("ServiceID");


-- Completed on 2017-03-06 14:44:31

--
-- PostgreSQL database dump complete
--


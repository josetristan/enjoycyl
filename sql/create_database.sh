#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL

	CREATE DATABASE enjoycyl
          WITH
          OWNER = postgres
          ENCODING = 'UTF8'
          --LC_COLLATE = 'Spanish_Spain.1252'
          --LC_CTYPE = 'Spanish_Spain.1252'
          TABLESPACE = pg_default
          CONNECTION LIMIT = -1;

  \connect enjoycyl


  -- SEQUENCE: public.hibernate_sequence

  -- DROP SEQUENCE IF EXISTS public.hibernate_sequence;

  CREATE SEQUENCE IF NOT EXISTS public.hibernate_sequence
      INCREMENT 1
      START 1
      MINVALUE 1
      MAXVALUE 9223372036854775807
      CACHE 1;

  ALTER SEQUENCE public.hibernate_sequence
      OWNER TO postgres;

  CREATE TABLE IF NOT EXISTS public.statistic
  (
      id bigint NOT NULL,
      version bigint,
      region character varying(255) COLLATE pg_catalog."default",
      event_id character varying(255) COLLATE pg_catalog."default",
      locality character varying(255) COLLATE pg_catalog."default",
      celebration_place character varying(255) COLLATE pg_catalog."default",
      end_date date,
      start_date date,
      title character varying(255) COLLATE pg_catalog."default",
      is_library_event boolean,
      likes integer,
      category character varying(255) COLLATE pg_catalog."default",
      attends integer,
      period character(6) COLLATE pg_catalog."default",
      CONSTRAINT statistic_pkey PRIMARY KEY (id)
  )

      TABLESPACE pg_default;

  ALTER TABLE IF EXISTS public.statistic
      OWNER to postgres;
  -- Index: category_idx

  -- DROP INDEX IF EXISTS public.category_idx;

  CREATE INDEX IF NOT EXISTS category_idx
      ON public.statistic USING btree
          (category COLLATE pg_catalog."default" ASC NULLS LAST)
      TABLESPACE pg_default;
  -- Index: locality_idx

  -- DROP INDEX IF EXISTS public.locality_idx;

  CREATE INDEX IF NOT EXISTS locality_idx
      ON public.statistic USING btree
          (locality COLLATE pg_catalog."default" ASC NULLS LAST)
      TABLESPACE pg_default;
  -- Index: period_idx

  -- DROP INDEX IF EXISTS public.period_idx;

  CREATE INDEX IF NOT EXISTS period_idx
      ON public.statistic USING btree
          (period COLLATE pg_catalog."default" ASC NULLS LAST)
      TABLESPACE pg_default;


  -- Table: public.attendant

  --DROP TABLE IF EXISTS public.attendant;

  CREATE TABLE IF NOT EXISTS public.attendant
  (
      id bigint NOT NULL,
      version bigint,
      region character varying(255) COLLATE pg_catalog."default",
      event_id bigint,
      time_rated integer,
      event_rated integer,
      locality character varying(255) COLLATE pg_catalog."default",
      adults integer,
      statistic_id bigint,
      place_rated integer,
      children integer,
      CONSTRAINT attendant_pkey PRIMARY KEY (id),
      CONSTRAINT fkjowogr5ganksor964t2y4at11 FOREIGN KEY (statistic_id)
      REFERENCES public.statistic (id) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION
      )

      TABLESPACE pg_default;

  ALTER TABLE IF EXISTS public.attendant
      OWNER to postgres;

  -- Table: public.people

  -- DROP TABLE IF EXISTS public.people;

  CREATE TABLE IF NOT EXISTS public.people
  (
      id bigint NOT NULL,
      version bigint,
      password_expired boolean,
      username character varying(255) COLLATE pg_catalog."default" NOT NULL,
      account_locked boolean,
      password character varying(255) COLLATE pg_catalog."default" NOT NULL,
      account_expired boolean,
      enabled boolean,
      region character varying(255) COLLATE pg_catalog."default",
      locality character varying(255) COLLATE pg_catalog."default",
      CONSTRAINT people_pkey PRIMARY KEY (id),
      CONSTRAINT uk_tnekt3qb2ipe5fxfdbjqxce3o UNIQUE (username)
      )

      TABLESPACE pg_default;

  ALTER TABLE IF EXISTS public.people
      OWNER to postgres;

  -- Table: public.rated

  -- DROP TABLE IF EXISTS public.rated;

  CREATE TABLE IF NOT EXISTS public.rated
  (
      id bigint NOT NULL,
      version bigint,
      region character varying(255) COLLATE pg_catalog."default",
      event_id bigint,
      time_rated integer,
      event_rated integer,
      locality character varying(255) COLLATE pg_catalog."default",
      adults integer,
      statistic_id bigint,
      place_rated integer,
      children integer,
      CONSTRAINT rated_pkey PRIMARY KEY (id),
      CONSTRAINT fkes62swjwr04xh7ueeihl5xtg6 FOREIGN KEY (statistic_id)
      REFERENCES public.statistic (id) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION
      )

      TABLESPACE pg_default;

  ALTER TABLE IF EXISTS public.rated
      OWNER to postgres;

  -- Table: public.role

  -- DROP TABLE IF EXISTS public.role;

  CREATE TABLE IF NOT EXISTS public.role
  (
      id bigint NOT NULL,
      version bigint,
      authority character varying(255) COLLATE pg_catalog."default" NOT NULL,
      CONSTRAINT role_pkey PRIMARY KEY (id),
      CONSTRAINT uk_irsamgnera6angm0prq1kemt2 UNIQUE (authority)
      )

      TABLESPACE pg_default;

  ALTER TABLE IF EXISTS public.role
      OWNER to postgres;


  -- Table: public.user_role

  -- DROP TABLE IF EXISTS public.user_role;

  CREATE TABLE IF NOT EXISTS public.user_role
  (
      user_id bigint NOT NULL,
      role_id bigint NOT NULL,
      CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id),
      CONSTRAINT fka68196081fvovjhkek5m97n3y FOREIGN KEY (role_id)
      REFERENCES public.role (id) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION,
      CONSTRAINT fks2dyr0xkt53pnq8lrqk4harmw FOREIGN KEY (user_id)
      REFERENCES public.people (id) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION
      )

      TABLESPACE pg_default;

  ALTER TABLE IF EXISTS public.user_role
      OWNER to postgres;






  ------INSERT INTO---------
  INSERT INTO public.people(
      id, version, password_expired, username, account_locked, password, account_expired, enabled, region, locality)
  VALUES (1, 0, false, 'admin', false, '{bcrypt}$2a$10$K7kZy5LhQ4jR1YhzZjNcFeTVQBpYSIgm6nQ16x9eKbU8GNpbYzxHO', false, true, null, 'PALENCIA');


  --///password9874*
  INSERT INTO public.people(
  	id, version, password_expired, username, account_locked, password, account_expired, enabled, region, locality)
  	VALUES (2, 0, false, 'admin1', false, '{bcrypt}$2a$10$8YFTg7mFftyfTD4EvTcmNu957BnPvOPAMx0URV7lfdnw/16Rcu.Wq', false, true, null, 'PALENCIA');

  INSERT INTO public.role(
      id, version, authority)
  VALUES (1, 0, 'ROLE_ADMIN');


  INSERT INTO public.role(
      id, version, authority)
  VALUES (2, 0, 'ROLE_MANAGEMENT_USER');


  INSERT INTO public.user_role(
      user_id, role_id)
  VALUES (1, 1);

  INSERT INTO public.user_role(
      user_id, role_id)
  VALUES (1, 2);

  INSERT INTO public.user_role(
      user_id, role_id)
  VALUES (2, 1);

  INSERT INTO public.user_role(
      user_id, role_id)
  VALUES (2, 2);

EOSQL
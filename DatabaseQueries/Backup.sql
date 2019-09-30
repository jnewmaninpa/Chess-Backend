SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

CREATE DATABASE chess WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE chess OWNER TO justin;

\connect chess

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE public.games (
    id bigint NOT NULL,
    fen text DEFAULT 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1'::text NOT NULL,
    creation_datetime timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);

ALTER TABLE public.games OWNER TO justin;

CREATE SEQUENCE public.game_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.game_id_seq OWNER TO justin;

ALTER SEQUENCE public.game_id_seq OWNED BY public.games.id;

CREATE TABLE public.moves (
    move_time timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    game_id bigint NOT NULL,
    move jsonb NOT NULL
);


ALTER TABLE public.moves OWNER TO justin;

CREATE TABLE public.users (
    username text NOT NULL,
    password text NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT users_username_check CHECK ((username ~ '^[a-zA-Z0-9.@_\-]+$'::text))
);


ALTER TABLE public.users OWNER TO justin;

CREATE SEQUENCE public.users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_user_id_seq OWNER TO justin;

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;

ALTER TABLE ONLY public.games ALTER COLUMN id SET DEFAULT nextval('public.game_id_seq'::regclass);

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);

ALTER TABLE ONLY public.games
    ADD CONSTRAINT game_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.moves
    ADD CONSTRAINT moves_pkey PRIMARY KEY (move_time);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT unique_username UNIQUE (username);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (user_id);

ALTER TABLE ONLY public.moves
    ADD CONSTRAINT "Games_pkey" FOREIGN KEY (game_id) REFERENCES public.games(id);

REVOKE ALL ON DATABASE chess FROM justin;
GRANT ALL ON DATABASE chess TO "justin.newman";

GRANT ALL ON SCHEMA public TO PUBLIC;

GRANT ALL ON LANGUAGE plpgsql TO justin;

GRANT SELECT,INSERT,UPDATE ON TABLE public.games TO chess;

GRANT USAGE ON SEQUENCE public.game_id_seq TO chess;

GRANT SELECT,INSERT,UPDATE ON TABLE public.moves TO chess;

GRANT SELECT,INSERT,UPDATE ON TABLE public.users TO chess;

GRANT USAGE ON SEQUENCE public.users_user_id_seq TO chess;

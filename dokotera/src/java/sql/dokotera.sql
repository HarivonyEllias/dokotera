CREATE DATABASE dokotera

CREATE TABLE aretina(
    id serial primary key,
    nom varchar(100)
);

CREATE TABLE params(
    id serial primary key,
    nom varchar(100)
);

CREATE TABLE fanafody(
    id serial primary key,
    nom varchar(100),
    prix double precision
);

CREATE TABLE defs( -- definition intervalle parametre 
    id serial primary key,
    idparams int references params(id),
    minim double precision,
    maxim double precision
);

create table aretina_params(
    id serial primary key,
    idaretina int references aretina(id),
    iddefs int references defs(id)
);

CREATE TABLE fanafody_params(
    id serial primary key,
    idfanafody int references fanafody(id),
    idparams int references params(id),
    effet double precision
);
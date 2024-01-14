insert into aretina(nom) values ('grippe'),('fievre'),('fatigue');

insert into params(nom) values ('loha'),('lelo'),('taona');

insert into fanafody(nom,prix) values ('f1',5000),('f2',7000),('f3',1000);


-- definition params ana aretina
insert into defs(idparams,minim,maxim) values (1,5,7),(2,6,8),(3,0,12);
insert into defs(idparams,minim,maxim) values (1,6,9),(2,5,7),(3,0,12); 


insert into aretina_params(idaretina,iddefs) values (2,4),(2,5),(2,6);
-- atreto

insert into fanafody_params(idfanafody,idparams,effet) values 
    (1,1,4),(1,2,5),(1,3,7),
    (2,1,5),(2,2,6),(2,3,8),
    (3,1,2),(3,2,5),(3,3,6)
;

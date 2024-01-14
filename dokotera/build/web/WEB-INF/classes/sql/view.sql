CREATE VIEW v_aretina_params AS
SELECT
    a.id AS aretina_id,
    a.nom AS aretina_nom,
    p.nom AS params_nom,
    d.minim,
    d.maxim
FROM
    aretina a
JOIN aretina_params ap ON a.id = ap.idaretina
JOIN params p ON ap.iddefs = p.id
JOIN defs d ON ap.iddefs = d.id;



CREATE VIEW v_fanafody_params AS
SELECT
    f.id AS fanafody_id,
    fp.id AS fanafody_params_id,
    f.nom AS fanafody_nom,
    f.prix AS fanafody_prix,
    p.id AS params_id,
    p.nom AS params_nom,
    fp.effet
FROM
    fanafody_params fp
JOIN fanafody f ON fp.idfanafody = f.id
JOIN params p ON fp.idparams = p.id;



SELECT
    ap.id AS aretina_params_id,
    a.id AS aretina_id,
    a.nom AS aretina_nom,
    d.id AS defs_id,
    p.id AS params_id,
    p.nom AS params_nom,
    d.minim,
    d.maxim
FROM
    aretina a
JOIN aretina_params ap ON a.id = ap.idaretina
JOIN defs d ON ap.iddefs = d.id
JOIN params p ON d.idparams = p.id
WHERE
    p.id IN (1, 2, 3)
    AND (
        (5 BETWEEN d.minim AND d.maxim)
        OR
        (6 BETWEEN d.minim AND d.maxim)
        OR
        (12 BETWEEN d.minim AND d.maxim)
    );
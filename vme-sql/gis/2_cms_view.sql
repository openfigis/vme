-- CREATE THE VIEW 'VME_GIS_CLOSURE' from the VME-db graph
-- =======================================================
-- Comment Erik: Run as FIGIS_GIS 
CREATE OR REPLACE VIEW FIGIS_GIS.VME_GIS_CLOSURE AS
-- Sub query 3 to provide surface field
-- (with casts to align with the 2 other global vme types)
SELECT
  qout.THE_GEOM,
  CAST(qout.VME_ID AS VARCHAR2(254 BYTE)) AS VME_ID,
  CAST(qout.LOCAL_NAME AS VARCHAR2(254 BYTE)) AS LOCAL_NAME,
  CAST(qout.YEAR AS VARCHAR2(254 BYTE)) AS YEAR,
  CAST(qout.END_YEAR AS VARCHAR2(254 BYTE)) AS END_YEAR,
  CAST(qout.OWNER AS VARCHAR2(254 BYTE)) AS OWNER,
  CAST(qout.VME_AREA_TIME AS VARCHAR2(254 BYTE)) AS VME_AREA_TIME,
  CAST(qout.GLOB_TYPE AS VARCHAR2(50 BYTE)) AS GLOB_TYPE,
  CAST(qout.GLOB_NAME AS VARCHAR2(50 BYTE)) AS GLOB_NAME,
  CAST(qout.REG_TYPE AS VARCHAR2(50 BYTE)) AS REG_TYPE,
  CAST(qout.REG_NAME AS VARCHAR2(50 BYTE)) AS REG_NAME,
  CAST(SDO_GEOM.SDO_AREA(THE_GEOM, 0.001, 'unit=SQ_M') AS FLOAT) AS SURFACE
FROM
-- Sub query 2 to convert wkt geometry
(SELECT
  VME.WKT_TO_GEOMETRY(res.WKT_GEOM) AS THE_GEOM,
  res.VME_ID,
  res.LOCAL_NAME,
  res.YEAR,
  res.END_YEAR,
  res.OWNER,
  res.VME_AREA_TIME,
  res.GLOB_TYPE,
  res.GLOB_NAME,
  res.REG_TYPE,
  res.REG_NAME
FROM

-- Sub query 1 to prepare attribute content
(SELECT
  geo.WKT_GEOM,
  vme.INVENTORYIDENTIFIER AS VME_ID,
  TO_CHAR(i18n_map.STRINGMAP) AS LOCAL_NAME,
  geo.YEAR,
  geotime.END_YEAR AS END_YEAR,
  vme.RFMO_ID AS OWNER,
  geo.GEOGRAPHICFEATUREID AS VME_AREA_TIME,
  'VME' AS GLOB_TYPE, -- required as target GIS table will also include other types (BFA, OTHER)
  'VME Closed Areas' AS GLOB_NAME, -- required as target GIS table will also include other types (BFA, OTHER)
  vt.CODE AS REG_TYPE,
  vt.NAME AS REG_NAME
FROM
  VME.VME vme,
  VME.GEOREF geo,
  
  -- for inheriting the geometry end year
  (
  WITH seq AS
    (SELECT
      VME_ID,
      YEAR,
      LEAD(YEAR,1) OVER (PARTITION BY VME_ID ORDER BY YEAR) -1 END_YEAR
     FROM VME.GEOREF
     ORDER BY VME_ID
     )
     -- all past records with end year calculated by looking to next record
    SELECT seq.* FROM seq WHERE END_YEAR IS NOT NULL
    UNION
    -- last record enriched with last measure end year
    SELECT seq.VME_ID, seq.YEAR, lastmeasure.YEAR
    FROM seq seq,
    (
    SELECT VME_ID, EXTRACT(YEAR FROM MAX(ENDDATE)) YEAR
    FROM VME.SPECIFIC_MEASURE GROUP BY VME_ID) lastmeasure
    WHERE END_YEAR IS NULL AND seq.VME_ID = lastmeasure.VME_ID
  ) geotime,
  
  VME.VME_TYPE vt,
  VME.MULTILINGUAL_STRING i18n,
  VME.MULTILINGUALSTRING_STRINGMAP i18n_map
  
WHERE
  vme.ID = geo.VME_ID AND
  geo.VME_ID = geotime.VME_ID AND
  geo.YEAR = geotime.YEAR AND
  vme.NAME_ID = i18n.ID AND
  i18n.ID = i18n_map.MULTILINGUALSTRING_ID AND
  vme.AREATYPE = vt.ID AND
  geo.VME_ID IS NOT NULL AND
  geo.WKT_GEOM IS NOT NULL
  ORDER BY VME_ID, YEAR
) res
) qout;
-- Report Erik: view created in fis-gis-dev
-- Report Erik: view created in figis-gis-qa

-- Need to insert GIS metadata
INSERT INTO
  MDSYS.USER_SDO_GEOM_METADATA
VALUES (
  'VME_GIS_CLOSURE',
  'THE_GEOM',
  MDSYS.SDO_DIM_ARRAY( MDSYS.SDO_DIM_ELEMENT('X',-180,180,0.001), MDSYS.SDO_DIM_ELEMENT('Y',-90,90,0.001) ),
  4326
);
COMMIT;
-- Report Erik: done in figis-gis-dev
-- Report Erik: done in figis-gis-qa


--Comment Erik run as figis-gis
CREATE OR REPLACE VIEW FIGIS_GIS.VMEMEASURE_TIME AS
SELECT
  THE_GEOM,
  VME_ID,
  OWNER,
  TO_DATE((YEAR || '01-01'),'YYYY-MM-DD') AS START_YEAR,
  TO_DATE((END_YEAR || '12-31'), 'YYYY-MM-DD') AS END_YEAR,
  LOCAL_NAME,
  GLOB_TYPE,
  GLOB_NAME,
  REG_TYPE,
  REG_NAME,
  SURFACE
FROM
  FIGIS_GIS.VMEMEASURE;
-- Report Erik: done in figis-gis-dev
-- Report Erik: done in figis-gis-qa
  
-- Need to insert GIS metadata (first try delete in case existing record)
DELETE FROM "MDSYS"."USER_SDO_GEOM_METADATA" WHERE TABLE_NAME = 'VMEMEASURE_TIME';
INSERT INTO
  MDSYS.USER_SDO_GEOM_METADATA
VALUES (
  'VMEMEASURE_TIME',
  'THE_GEOM',
  MDSYS.SDO_DIM_ARRAY( MDSYS.SDO_DIM_ELEMENT('X',-180,180,0.005), MDSYS.SDO_DIM_ELEMENT('Y',-90,90,0.005) ),
  4326
);
commit;
-- Report Erik: done in figis-gis-dev
-- Report Erik: done in figis-gis-qa

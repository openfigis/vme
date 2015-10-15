-- Draft implementation of indicator 4(b)
-- ======================================
-- Done in SQL as part of the indicators exploratory approach (SQL is being considered only for relatively "simple"
-- indicators and for the sake of illustrating the indicator needs. This indicator later should be computed along 
-- with the other statistical indicators using a single statistical framework. For the latter, the current candidate
-- technology is the R/Java Statistical Engine developed in the TCP/BHA/3501 project.
--
-- Indicator 4(b) computes statistics by (OWNER)
--

CREATE OR REPLACE VIEW FIGIS_GIS.VME_INDICATOR_4B_DRAFT AS
SELECT
  OWNER,
  MIN(SURFACE) AS MIN,
  MAX(SURFACE) AS MAX,
  AVG(SURFACE) AS AVG,
  STDDEV(SURFACE) AS SD,
  COUNT(*) AS COUNT
FROM
(WITH LEVELS AS(
SELECT 2000 + LEVEL AS YEARLEVEL
  FROM DUAL
  CONNECT BY LEVEL <= ( SELECT MAX(END_YEAR) - MIN(YEAR) FROM FIGIS_GIS.VME_GIS_CLOSURE )
)
SELECT
  vme.OWNER,
  vme.VME_ID,
  lev.YEARLEVEL AS YEAR,
  vme.SURFACE
FROM
  FIGIS_GIS.VME_GIS_CLOSURE vme,
  LEVELS lev
WHERE
  lev.YEARLEVEL >= vme.YEAR AND lev.YEARLEVEL <= vme.END_YEAR 
ORDER BY
  OWNER, VME_ID, YEARLEVEL
)
WHERE YEAR < = EXTRACT(YEAR FROM sysdate)
GROUP BY OWNER
ORDER BY OWNER;

delete fs_observation_xml
where cd_observation in 
(select cd_observation from fs_vme_observation);

delete fs_vme_observation;

delete fs_observation
where cd_observation in 
(select cd_observation from fs_vme_observation);

delete ref_vme

delete REF_WATER_AREA
where cd_water_area >= 6000
and cd_water_area <= 8000


select count(*) from REF_WATER_AREA
where cd_water_area >= 6000
and cd_water_area <= 8000



select count(*) from ref_vme
--98
select count(*) from fs_vme_observation
--186



select x.xml from fs_vme_observation oo, fs_observation o, fs_observation_xml x
where
o.cd_observation = oo.cd_observation 
and
o.cd_observation = x.cd_observation 







select count(*) from fs_vme_observation oo, fs_observation o, fs_observation_xml x
where
o.cd_observation = oo.cd_observation 
and
o.cd_observation = x.cd_observation 
-- 376


select * from fs_vme_observation oo, fs_observation o
where
o.cd_observation = oo.cd_observation 


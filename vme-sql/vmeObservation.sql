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
-- 0, correct, they were already loaded, only new ones will be ported. 
-- total should be 105


select * from ref_vme
select count(inventory_id) from ref_vme
select count(name_e) from ref_vme
select count(*) from ref_vme
--99, is correct





select * from fs_vme_observation
select count(*) from fs_vme_observation
select count(cd_observation) from fs_vme_observation
select count(cd_vme) from fs_vme_observation
--210



select count(*) from fs_vme_observation oo, fs_observation o, fs_observation_xml x
where
o.cd_observation = oo.cd_observation 
and
o.cd_observation = x.cd_observation 
-- 210



select * from fs_vme_observation oo, fs_observation o
where
o.cd_observation = oo.cd_observation 

select count (*) from fs_vme_observation oo, fs_observation o, fs_observation_xml x
select oo.reporting_year, o.* from fs_vme_observation oo, fs_observation o, fs_observation_xml x
where
o.cd_observation = oo.cd_observation 
and
o.cd_observation = x.cd_observation 
and oo.cd_vme = 1344

select * from fs_vme_observation





delete fs_observation_xml
where cd_observation in 
(select cd_observation from fs_vme_observation);

delete fs_vme_observation;

delete fs_observation
where cd_observation in 
(select cd_observation from fs_vme_observation);




select x.xml from fs_vme_observation oo, fs_observation o, fs_observation_xml x
where
o.cd_observation = oo.cd_observation 
and
o.cd_observation = x.cd_observation 


select * from ref_vme
where cd_vme = 2000




delete ref_vme
where cd_vme < 10
or cd_vme > 10




select count(*) from fs_vme_observation oo, fs_observation o, fs_observation_xml x
where
o.cd_observation = oo.cd_observation 
and
o.cd_observation = x.cd_observation 



select * from fs_vme_observation oo, fs_observation o
where
o.cd_observation = oo.cd_observation 


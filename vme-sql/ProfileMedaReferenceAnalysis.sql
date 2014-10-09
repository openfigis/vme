select count(*) from vme v, Profile p
where v.id = p.vme_id
-qa 166 prod 173


select count(*) from  Profile p
-- qa 186 prod 193

select count(*) from  Profile p
where vme_id is null
-- qa 20 prod 20

select count(*) from vme v, MediaReference m
where v.id = m.vme_id
-19

select count(*) from  MediaReference 
--19

select count(*) from  MediaReference 
where vme_id is null
--0



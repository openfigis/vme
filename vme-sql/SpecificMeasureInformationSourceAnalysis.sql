
select * from specific_measure
select count(*) from specific_measure
-- qa 359
-- prod 342


select count(*) from information_source
-- qa 171
-- prod 171

select count(*) from is_sm
-- qa 223
-- prod 3

select count(*) from specific_measure 
where vme_id is null
--  prod 34

select * from is_sm


select j.* from specific_measure s, is_sm j
where s.vme_id is null
and  j.specificmeasurelist_id = s.id
-- qa 22
-- prod 0
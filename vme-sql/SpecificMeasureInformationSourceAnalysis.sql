
select * from specific_measure
select count(*) from specific_measure
-- qa 352
-- prod 342


select count(*) from information_source
-- qa 171
-- prod 171

select count(*) from is_sm
-- qa 223
-- prod 3


select * from vme
where id = 31851
--qa 225
--prod 332

select count(*) from specific_measure
where vme_id is null
-- qa 29
-- prod 34




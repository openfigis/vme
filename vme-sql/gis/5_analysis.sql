
select count(*) from georef
-- prod 200
-- qa 195


select count(*) from georef
where wkt_geom is null
-- prod 10 out of 200
-- qa 8 out of 195

select * from georef


 



-- cleaning georef, mediareference, profile


select count(*) from georef
where vme_id is null
-- devel 99

delete georef
where vme_id is null

select count(*) from mediareference
where vme_id is null
-- devel 0


select count(*) from profile
where vme_id is null
-- devel 24

delete profile
where vme_id is null











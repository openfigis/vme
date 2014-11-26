Geneselect infs.* , m.*, s.* 
from information_source infs, multilingual_string m , multilingualstring_stringmap s 
where m.id = s.multilingualstring_id 
and infs.citation_id = m.id 


select count(*)
from information_source infs
-- qa 170
-- prod 171

select count(*)
from information_source infs, multilingual_string m , multilingualstring_stringmap s 
where m.id = s.multilingualstring_id 
and infs.citation_id = m.id 
-- qa 170
-- 0

select count(*)
from information_source infs, multilingual_string m
where infs.citation_id = m.id 
--170 have a multilingualstring id
--prod 170 have a multilingualstring id

select count(*)
from information_source infs, multilingual_string m , multilingualstring_stringmap s 
where infs.citation_id = m.id 
and m.id = s.multilingualstring_id (+)
and s.multilingualstring_id is null
-- the prove that 170 have lost their name


select distinct stringmap_key from multilingualstring_stringmap
-- only 1, this is correct, it means it is only using english. 

select count(*) from  multilingual_string m 
-- qa there are 6132 registered strings
-- prod there are 7147 registered strings
-- devel 6073 registered strings

select count(*)
from multilingual_string m , multilingualstring_stringmap s 
where m.id = s.multilingualstring_id
-- qa from the 6132 registered strings, there are 6130 with an actual string value. That mean that 2 registered strings have  lost their value. 
-- prod from the 7147 registered strings, there are 6101 with an actual string value. That mean that 1046 registered strings have  lost their value. 
-- prod from the 6073 registered strings, there are 6073 with an actual string value. That mean that 0 registered strings have  lost their value. 

select count(*)
from multilingual_string m , multilingualstring_stringmap s 
where m.id = s.multilingualstring_id (+)
and s.multilingualstring_id is null
-- qa the proof that indeed 2 registered strings have lost their value. 
-- prod the proof that indeed 1046 registered strings have lost their value. 
-- devel the proof that indeed 0 registered strings have lost their value. 

select count(*) from multilingual_string m 
-- qa so 6132 registered strings in total
-- prod so 7147 registered strings in total

select count(*) from multilingualstring_stringmap m 
-- and 6130 values. 
-- prod 6101
-- devel 6073


select multilingualstring_id, stringmap from multilingualstring_stringmap
select multilingualstring_id, stringmap from multilingualstring_stringmap






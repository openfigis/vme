select v.* , m.*, s.* 
from vme v, multilingual_string m , multilingualstring_stringmap s 
where m.id = s.multilingualstring_id 
and v.name_id = m.id 


select count(*)
from vme v
-- qa 187

select count(*)
from vme v, multilingual_string m , multilingualstring_stringmap s 
where m.id = s.multilingualstring_id 
and v.name_id = m.id 
-- 106, so 81 vmes have lost their name

select count(*)
from vme v, multilingual_string m
where v.name_id = m.id 
--187 have a multilingualstring id

select count(*)
from vme v, multilingual_string m , multilingualstring_stringmap s 
where v.name_id = m.id 
and m.id = s.multilingualstring_id (+)
and s.multilingualstring_id is null
-- the prove that 81 have lost their name
-- this problem might be bigger than only the vme names, it could appply to all vmes. 

select distinct stringmap_key from multilingualstring_stringmap
-- only 1, this is correct, it means it is only using english. 

select count(*) from  multilingual_string m 
-- there are 5090 registered strings

select count(*)
from multilingual_string m , multilingualstring_stringmap s 
where m.id = s.multilingualstring_id
-- from the 5090 registered strings, there are 4476 with an actual string value. That mean that 614 registered strings have  lost their value. 

select count(*)
from multilingual_string m , multilingualstring_stringmap s 
where m.id = s.multilingualstring_id (+)
and s.multilingualstring_id is null
-- the proof that indeed 614 registered strings have lost their value. 

select count(*) from multilingual_string m 
-- so 5090 registered strings in total

select count(*) from multilingualstring_stringmap m 
-- and 4476 values. 


select multilingualstring_id, stringmap from multilingualstring_stringmap



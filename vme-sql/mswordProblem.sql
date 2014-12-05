select * from vme v, Rfmo r, fishery_areas_history f
where  v.rfmo_id = r.id 
and r.id = v.rfmo_id
and r.id = f.rfmo_id
and v.inventoryidentifier = 'VME_NPFC_Regulatory_1'

select * from vme v, Rfmo r, fishery_areas_history f ,   multilingual_string m , multilingualstring_stringmap s 
where  v.rfmo_id = r.id 
and r.id = v.rfmo_id
and r.id = f.rfmo_id
and v.inventoryidentifier = 'VME_NPFC_Regulatory_1'
and f.history_id = m.id
and m.id = s.multilingualstring_id 




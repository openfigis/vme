update georef g2
set (g2.vme_id) =(

select v.id  from georef g1
INNER JOIN vme v
ON v.inventoryidentifier like substr(g1.geographicfeatureid,0,length(g1.geographicfeatureid)-5)
where g1.vme_id is null 
and rownum = 1

) where g2.vme_id is null
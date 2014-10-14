
--select * from ref_cover_page

DELETE FROM fs_collection_cover_page 
WHERE CD_COVER_PAGE =861
or CD_COVER_PAGE =861
or CD_COVER_PAGE =862
or CD_COVER_PAGE =863
or CD_COVER_PAGE =864
or CD_COVER_PAGE =865
or CD_COVER_PAGE =866
or CD_COVER_PAGE =866
or CD_COVER_PAGE =867
or CD_COVER_PAGE =868



DELETE FROM ref_cover_page 
WHERE CD_COVER_PAGE =861
or CD_COVER_PAGE =861
or CD_COVER_PAGE =862
or CD_COVER_PAGE =863
or CD_COVER_PAGE =864
or CD_COVER_PAGE =865
or CD_COVER_PAGE =866
or CD_COVER_PAGE =866
or CD_COVER_PAGE =867
or CD_COVER_PAGE =868

select * from ref_cover_page
where CD_COVER_PAGE = 861
 

Insert into ref_cover_page (CD_COVER_PAGE,TITLE_E,  IDENTIFIER,PUBLISHER) 
values (861,'CCAMLR Conservation and Enforcement measures', 'CCAMLR_CEM_CP','FAO');
Insert into ref_cover_page (CD_COVER_PAGE,TITLE_E, IDENTIFIER,PUBLISHER) 
values (862,'GFCM Conservation and Enforcement measures','GFCM_CEM_CP','FAO');
Insert into ref_cover_page (CD_COVER_PAGE,TITLE_E, IDENTIFIER,PUBLISHER) 
values (863,'NAFO Conservation and Enforcement measures','NAFO_CEM_CP','FAO');
Insert into ref_cover_page (CD_COVER_PAGE,TITLE_E, IDENTIFIER,PUBLISHER) 
values (864,'NEAFC Conservation and Enforcement measures','NEAFC_CEM_CP','FAO');
Insert into ref_cover_page (CD_COVER_PAGE,TITLE_E, IDENTIFIER,PUBLISHER) 
values (865,'NPFC Conservation and Enforcement measures','NPFC_CEM_CP','FAO');
Insert into ref_cover_page (CD_COVER_PAGE,TITLE_E, IDENTIFIER,PUBLISHER) 
values (866,'SEAFO Conservation and Enforcement measures','SEAFO_CEM_CP','FAO');
Insert into ref_cover_page (CD_COVER_PAGE,TITLE_E, IDENTIFIER,PUBLISHER) 
values (867,'SPRFMO Conservation and Enforcement measures','SPRFMO_CEM_CP','FAO');
Insert into ref_cover_page (CD_COVER_PAGE,TITLE_E, IDENTIFIER,PUBLISHER) 
values (868,'WECAFC Conservation and Enforcement measures','WECAFC_CEM_CP','FAO');


Insert into fs_collection_cover_page (CD_COLLECTION,CD_COVER_PAGE,ROLE)
values (7300,861,'factsheet');
Insert into fs_collection_cover_page (CD_COLLECTION,CD_COVER_PAGE,ROLE)
values (7300,862,'factsheet');
Insert into fs_collection_cover_page (CD_COLLECTION,CD_COVER_PAGE,ROLE)
values (7300,863,'factsheet');
Insert into fs_collection_cover_page (CD_COLLECTION,CD_COVER_PAGE,ROLE)
values (7300,864,'factsheet');
Insert into fs_collection_cover_page (CD_COLLECTION,CD_COVER_PAGE,ROLE)
values (7300,865,'factsheet');
Insert into fs_collection_cover_page (CD_COLLECTION,CD_COVER_PAGE,ROLE)
values (7300,866,'factsheet');
Insert into fs_collection_cover_page (CD_COLLECTION,CD_COVER_PAGE,ROLE)
values (7300,867,'factsheet');
Insert into fs_collection_cover_page (CD_COLLECTION,CD_COVER_PAGE,ROLE)
values (7300,868,'factsheet');


Insert into fs_collection_organisation (CD_COLLECTION,CD_PRGRMID,CD_INSTITUTE,CD_PROJECT,ROLE) 
values (7300,null,6426,null,3);

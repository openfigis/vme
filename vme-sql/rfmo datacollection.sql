-- No Wecafc
-- delete 7300

Insert into REF_DATA_COLLECTION (CD_COLLECTION,CD_META,NAME_E,CODE) 
values (7351,267000,'CCAMLR VME Factsheets','CCAMLR_VME');
Insert into REF_DATA_COLLECTION (CD_COLLECTION,CD_META,NAME_E,CODE) 
values (7352,267000,'GFCM VME Factsheets','GFCM_VME');
Insert into REF_DATA_COLLECTION (CD_COLLECTION,CD_META,NAME_E,CODE) 
values (7353,267000,'NAFO VME Factsheets','NAFO_VME');
Insert into REF_DATA_COLLECTION (CD_COLLECTION,CD_META,NAME_E,CODE) 
values (7354,267000,'NEAFC VME Factsheets','NEAFC_VME');
Insert into REF_DATA_COLLECTION (CD_COLLECTION,CD_META,NAME_E,CODE) 
values (7355,267000,'SEAFO VME Factsheets','SEAFO_VME');
Insert into REF_DATA_COLLECTION (CD_COLLECTION,CD_META,NAME_E,CODE) 
values (7356,267000,'NPFC VME Factsheets','NPFC_VME');
Insert into REF_DATA_COLLECTION (CD_COLLECTION,CD_META,NAME_E,CODE) 
values (7357,267000,'SPRFMO VME Factsheets','SPRFMO_VME');

Insert into fs_collection_organisation (CD_COLLECTION, CD_INSTITUTE,ROLE) 
values (7351, 20010, 3);
Insert into fs_collection_organisation (CD_COLLECTION, CD_INSTITUTE,ROLE) 
values (7352, 22080, 3);
Insert into fs_collection_organisation (CD_COLLECTION, CD_INSTITUTE,ROLE) 
values (7353, 20220, 3);
Insert into fs_collection_organisation (CD_COLLECTION, CD_INSTITUTE,ROLE) 
values (7354, 21580, 3);
Insert into fs_collection_organisation (CD_COLLECTION, CD_INSTITUTE,ROLE) 
values (7355, 22140, 3);
Insert into fs_collection_organisation (CD_COLLECTION, CD_INSTITUTE,ROLE) 
values (7356, 24564, 3);
Insert into fs_collection_organisation (CD_COLLECTION, CD_INSTITUTE,ROLE) 
values (7357, 24558, 3);


--Define VME as a security collection:
Insert into security_collection (CD_COLLECTION, NAME, NAME_E , COLLECTION_ACRONYM) values 
(7351,'CCAMLR_VME','CCAMLR_VME','CCAMLR_VME');
Insert into security_collection (CD_COLLECTION, NAME, NAME_E , COLLECTION_ACRONYM) values 
(7352,'GFCM_VME','GFCM_VME','GFCM_VME');
Insert into security_collection (CD_COLLECTION, NAME, NAME_E , COLLECTION_ACRONYM) values 
(7353,'NAFO_VME','NAFO_VME','NAFO_VME');
Insert into security_collection (CD_COLLECTION, NAME, NAME_E , COLLECTION_ACRONYM) values 
(7354,'NEAFC_VME','NEAFC_VME','NEAFC_VME');
Insert into security_collection (CD_COLLECTION, NAME, NAME_E , COLLECTION_ACRONYM) values 
(7355,'SEAFO_VME','SEAFO_VME','SEAFO_VME');
Insert into security_collection (CD_COLLECTION, NAME, NAME_E , COLLECTION_ACRONYM) values 
(7356,'NPFC_VME','NPFC_VME','NPFC_VME');
Insert into security_collection (CD_COLLECTION, NAME, NAME_E , COLLECTION_ACRONYM) values 
(7357,'SPRFMO_VME','SPRFMO_VME','SPRFMO_VME');



-- Create the VME group:
Insert into security_user (CD_USER,LOGON,AUTH_TYPE,IS_GROUP)
values (7351,'CCAMLR_VME','G',1);

Insert into security_user (CD_USER,LOGON,AUTH_TYPE,IS_GROUP)
values (7352,'GFCM_VME','G',1);

Insert into security_user (CD_USER,LOGON,AUTH_TYPE,IS_GROUP)
values (7353,'NAFO_VME','G',1);

Insert into security_user (CD_USER,LOGON,AUTH_TYPE,IS_GROUP)
values (7354,'NEAFC_VME','G',1);

Insert into security_user (CD_USER,LOGON,AUTH_TYPE,IS_GROUP)
values (7355,'SEAFO','G',1);

Insert into security_user (CD_USER,LOGON,AUTH_TYPE,IS_GROUP)
values (7356,'NPFC_VME','G',1);

Insert into security_user (CD_USER,LOGON,AUTH_TYPE,IS_GROUP)
values (7357,'SPRFMO_VME','G',1);

--Give this VME group grants on the VME security collection. 
Insert into security_rights (CD_COLLECTION,CD_USER,RIGHTS,DELEGATE)
values (7351,7351,65535,0);
Insert into security_rights (CD_COLLECTION,CD_USER,RIGHTS,DELEGATE)
values (7352,7352,65535,0);
Insert into security_rights (CD_COLLECTION,CD_USER,RIGHTS,DELEGATE)
values (7353,7353,65535,0);
Insert into security_rights (CD_COLLECTION,CD_USER,RIGHTS,DELEGATE)
values (7354,7354,65535,0);
Insert into security_rights (CD_COLLECTION,CD_USER,RIGHTS,DELEGATE)
values (7355,7355,65535,0);
Insert into security_rights (CD_COLLECTION,CD_USER,RIGHTS,DELEGATE)
values (7356,7356,65535,0);
Insert into security_rights (CD_COLLECTION,CD_USER,RIGHTS,DELEGATE)
values (7357,7357,65535,0);



--Add Erik to the VME group:
Insert into security_user_grp (CD_GROUP,CD_USER) values (7351, 2830);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7352, 2830);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7353, 2830);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7354, 2830);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7355, 2830);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7356, 2830);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7357, 2830);
--Add Aureliano to the VME group: 221
Insert into security_user_grp (CD_GROUP,CD_USER) values (7351, 221);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7352, 221);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7353, 221);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7354, 221);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7355, 221);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7356, 221);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7357, 221);
--Add Marco to the VME group: 111
Insert into security_user_grp (CD_GROUP,CD_USER) values (7351, 111);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7352, 111);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7353, 111);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7354, 111);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7355, 111);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7356, 111);
Insert into security_user_grp (CD_GROUP,CD_USER) values (7357, 111);





select * from ref_institute where inst_acronym = 'CCAMLR'
or inst_acronym = 'GFCM'
or inst_acronym = 'NAFO'
or inst_acronym = 'NEAFC'
or inst_acronym = 'SEAFO'
or inst_acronym = 'NPFC'
or inst_acronym = 'SPRFMO'




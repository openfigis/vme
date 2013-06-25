
Insert into ref_vme (CD_VME,CD_META,INVENTORY_ID,NAME_E,NAME_F,NAME_S,NAME_A,NAME_C,NAME_R) values (10,172000,'PIPPO','Van Ingen Laziale',null,null,null,null,null); 


Insert into md_refobject (ID,NAME_E,NAME_F,NAME_S,PARENT,ANCESTOR,ISMAJOR,DOC,OBJ_CLSINIT,READER_CLSINIT,LIST_FORMAT,NAME_A,NAME_C,NAME_R)values (172000,'Vulnerable Marine Ecosystem','Vulnerable Marine Ecosystem','Vulnerable Marine Ecosystem',1,172000,1,'Data collections',null,172000,null,null,null,null);

Insert into md_refattr (ID,OBJID,FLAGS,NAMINGSYSTEM,NAME_E,NAME_F,NAME_S,CLASSNAME,INIT,READER_CLSINIT,NAME_A,NAME_C,NAME_R) 
values (1,172000,11,1,'Name','Nom','Nombre','org.fao.fi.figis.refservice.metadata.attr.MetaAttrName','Keyword=100; MaxLen=200;',1007,null,null,null);

Insert into md_refattr (ID,OBJID,FLAGS,NAMINGSYSTEM,NAME_E,NAME_F,NAME_S,CLASSNAME,INIT,READER_CLSINIT,NAME_A,NAME_C,NAME_R)
values (12,172000,2,12,'Inventory Identifier','Inventory Identifier','Inventory Identifier','org.fao.fi.figis.refservice.metadata.attr.MetaAttrString','Keyword=80;',172012,null,null,null);

Insert into md_classinit (ID,CLASSNAME,INIT_XML) 
values (172000,'org.fao.fi.figis.refservice.data.DefaultObjectAccessor','Table=REF_VME;IDColumn=CD_VME;MetaColumn=172000'); 

Insert into md_classinit (ID,CLASSNAME,INIT_XML)
values (172012,'org.fao.fi.figis.refservice.data.MainTableAttributeAccessor','Column=INVENTORY_ID');

Insert into fs_collection_cover_page (CD_COLLECTION,CD_COVER_PAGE,ROLE)
values (7300,791,'default');


-- RTMS water area ref
Insert into md_refobject 
(ID,NAME_E,NAME_F,NAME_S,PARENT,ANCESTOR,ISMAJOR,DOC,OBJ_CLSINIT,READER_CLSINIT,LIST_FORMAT,NAME_A,NAME_C,NAME_R) 
values (280002,'VME fishery management area','','',24030,24030,0,'VME fishery management areas',null,24000,null,null,null,null);

Insert into ref_water_area (CD_WATER_AREA,NAME,CD_UN_CODE,CD_WATER_AREA_TYPE,GRP_IND,AREA,CD_META,EXTERNAL_ID,MIN_LONG,MAX_LONG,MIN_LAT,MAX_LAT,AREA_SIZE,CD_ISO3_CODE)
values (5067,'VME_5066',null,7,'D',null,280001,'VME_5067',null,null,null,null,null,null);


-- redirect rules 33800 

-- in order to avoid variable substitution in Oracle SQL Developer 
set define off;

Insert into "redirect_map" (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS) 
values (33801,'/fishery/vme/*','/fiweb/website/VMERetrieveAction.do?dom=vme&fid=<1>&oid=<3>&lang=<5>&draft=<7>&view=<9>',1,to_date('14-JUN-13','DD-MON-RR'),null,'Regexp countryprofile factsheets',1,0,'(\d+)(/(\d+))?(/(en|es|fr|ar|zh|ru))?(/(draft))?(/(data))?',null,null);

Insert into "redirect_map" (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS) 
values (33802,'/fishery/factsheets/search/xml/vme*','/fiweb/website/FullSearchActionXML.do?dslist[0]=vme',0,to_date('14-JUN-13','DD-MON-RR'),null,'XML Full Search',0,0,null,null,null);

Insert into "redirect_map" (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS) 
values (33803,'/fishery/search/vme*','/fishery/vme/search*',0,to_date('14-JUN-13','DD-MON-RR'),null,'vme search pages',1,1,null,null,null);

Insert into "redirect_map" (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS) 
values (33804,'/fishery/vme/search*','/fishery/countryprofiles/search*',0,to_date('14-JUN-13','DD-MON-RR'),null,'vme search pages',1,1,null,null,null);

Insert into "redirect_map" (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS) 
values (33805,'/fishery/xml/vme/*','/fiweb/website/RetrieveXML.do?dom=vme&fid=<1>&oid=<3>&lang=<5>&draft=<7>&bypasslogin=true',1,to_date('18-JAN-13','DD-MON-RR'),null,'Regexp vme xml',1,0,'(\d+)(/(\d+))?(/(en|es|fr|ar|zh|ru))?(/(draft))?',null,null);

Insert into "redirect_map" (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS) 
values (33806,'/fishery/inputxml/vme/*','/fiweb/website/RetrieveXML.do?dom=vme&inputxml=true&fid=<1>&oid=<3>&lang=<5>&draft=<7>&bypasslogin=true',1,to_date('17-JAN-13','DD-MON-RR'),null,'Regexp vme xml',1,0,'(\d+)(/(\d+))?(/(en|es|fr|ar|zh|ru))?(/(draft))?',null,null);

Insert into "redirect_map" (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS) 
values (33807,'/fishery/factsheets/search/xml/vme*','/fiweb/website/FullSearchActionXML.do?dslist[0]=vme',0,to_date('14-JUN-13','DD-MON-RR'),null,'XML Full Search',0,0,null,null,null);

Insert into "redirect_map" (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS) 
values (33808,'/vme*','/fiweb/website/VMERetrieveAction.do?dom=vme&fid=<1>&lang=<2>&draft=<4>',1,to_date('21-JUN-07','DD-MON-RR'),null,'VME  page',1,0,'(/(en|es|fr|ar|zh|ru))?(/(draft))?',null,null);





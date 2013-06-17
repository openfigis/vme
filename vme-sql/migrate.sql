
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


-- redirect rules 33800 
Insert into redirect_map (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS) 
values (33801,'/fishery/vme/*','/fiweb/website/FIRetrieveAction.do?dom=vme=<1>=<3>=<5>=<7>=<9>',1,to_date('14-JUN-13','DD-MON-RR'),null,'Regexp countryprofile factsheets',1,0,'(\d+)(/(\d+))?(/(en|es|fr|ar|zh|ru))?(/(draft))?(/(data))?',null,null);

Insert into redirect_map (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS) 
values (33802,'/fishery/factsheets/search/xml/vme*','/fiweb/website/FullSearchActionXML.do?dslist[0]=vme',0,to_date('14-JUN-13','DD-MON-RR'),null,'XML Full Search',0,0,null,null,null);

Insert into redirect_map (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS) 
values (33803,'/fishery/search/vme*','/fishery/vme/search*',0,to_date('14-JUN-13','DD-MON-RR'),null,'vme search pages',1,1,null,null,null);

Insert into redirect_map (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS)
 values (33804,'/fishery/vme/search*','/fishery/countryprofiles/search*',0,to_date('14-JUN-13','DD-MON-RR'),null,'vme search pages',1,1,null,null,null);

-- problem with editor
Insert into redirect_map (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS)
 values (33805,'/fishery/xml/vme/*','/fiweb/website/RetrieveXML.do?dom=vme&fid=<1>&oid=<3>&lang=<5>&draft=<7>&bypasslogin=true',1,to_date('18-JAN-13','DD-MON-RR'),null,'Regexp vme xml',1,0,'(\d+)(/(\d+))?(/(en|es|fr|ar|zh|ru))?(/(draft))?',null,null);

-- problem with editor
Insert into redirect_map (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS)
 values (33806,'/fishery/inputxml/vme/*','/fiweb/website/RetrieveXML.do?dom=vme&inputxml=true&fid=<1>&oid=<3>&lang=<5>&draft=<7>&bypasslogin=true',1,to_date('17-JAN-13','DD-MON-RR'),null,'Regexp vme xml',1,0,'(\d+)(/(\d+))?(/(en|es|fr|ar|zh|ru))?(/(draft))?',null,null);

-- problem with editor
Insert into redirect_map (CD_RULE,FROM_URL,TO_URL,TYPE,DT_ENTERED,DT_CHANGED,NOTE,ACTIVE,REDIRECT,REGEXP,SERVER_NAME,PARAMETERS)
 values (33807,'/fishery/factsheets/search/xml/vme*','/fiweb/website/FullSearchActionXML.do?dslist[0]=vme',0,to_date('14-JUN-13','DD-MON-RR'),null,'XML Full Search',0,0,null,null,null);

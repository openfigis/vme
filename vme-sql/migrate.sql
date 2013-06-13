
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

select * from REF_DATA_COLLECTION
where CD_COLLECTION = 7300

select * from ref_cover_page
where CD_COVER_PAGE = 791

-- problem
Insert into ref_data_collection (CD_COLLECTION,CD_META,NAME_E,NAME_F,NAME_S,SHORT_NAME,CODE,DESCRIPTION,NAME_A,NAME_C,NAME_R) 
values (7300,267000,'VME fact sheets',null,null,'VME fact sheets','VME_FS',null,null,null,null);

-- problem
Insert into ref_cover_page (CD_COVER_PAGE,TITLE_E,TITLE_F,TITLE_S,AUTHOR_E,AUTHOR_F,AUTHOR_S,IDENTIFIER,SUBJECT,PUBLISHER,SERIES_E,SERIES_F,SERIES_S,ALT_TITLE_E,ALT_TITLE_F,ALT_TITLE_S,TITLE_A,TITLE_C,TITLE_R,ALT_TITLE_A,ALT_TITLE_C,ALT_TITLE_R,AUTHOR_A,AUTHOR_C,AUTHOR_R,SERIES_A,SERIES_C,SERIES_R) 
values (791,'VME',null,null,null,null,null,'VME',null,'FAO',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);


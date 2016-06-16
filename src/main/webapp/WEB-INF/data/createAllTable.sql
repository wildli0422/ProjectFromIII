
DROP SEQUENCE HostelOrderDetailNo_seq;
DROP TABLE HostelOrderDetail;
DROP SEQUENCE HostelOrderNo_seq;
DROP TABLE HostelOrder;
DROP SEQUENCE HostelNewsNo_seq;
DROP TABLE HostelNews;
DROP SEQUENCE HostelTraceNo_seq;
DROP TABLE HostelTrace;
DROP SEQUENCE BOARDNO_seq;
DROP TABLE BOARD;
DROP SEQUENCE adNo_seq;
DROP TABLE AD;
DROP SEQUENCE PROMNO_SEQ;
DROP TABLE PROMINFO;
DROP SEQUENCE VIEWLISTNo_seq;
DROP TABLE VIEWLIST;
DROP SEQUENCE VIEWPICNO_SEQ;
DROP TABLE VIEWPHOTO;
DROP SEQUENCE VIEWNO_SEQ;
DROP TABLE VIEWINFO;
DROP SEQUENCE roomTypePicNo_seq;
DROP TABLE roomTypePic;
DROP SEQUENCE hostelPicNo_seq;
DROP TABLE hostelPic;
DROP SEQUENCE roomNo_seq;
DROP TABLE room;
DROP SEQUENCE roomTypeNo_seq;
DROP TABLE roomType;
DROP SEQUENCE FacilityNo_seq;
DROP TABLE Facility;
DROP SEQUENCE hostelNo_seq;
DROP TABLE hostel;
DROP SEQUENCE dealerNo_seq;
DROP TABLE dealer;

DROP SEQUENCE empAccessNo_seq;
DROP TABLE empAccess;
DROP TABLE access_emp;

DROP SEQUENCE tenantNo_seq;
DROP TABLE tenant;
DROP SEQUENCE empNo_seq;
DROP TABLE emp;



CREATE TABLE emp (
 empNo               NUMBER(4) NOT NULL,
 empAccount          VARCHAR2(10) unique,
 empPassword         VARCHAR2(16),
 empName             VARCHAR2(10),
 empSex              VARCHAR2(6),
 empAddress          VARCHAR2(65),
 empPhone            varchar2(15),
 empMail             VARCHAR2(80),
 empPic	             Blob,
 registerDate       DATE,
 
 CONSTRAINT emp_empNo_PK PRIMARY KEY (empNo)
);


 
CREATE SEQUENCE empNo_seq
INCREMENT BY 1
START WITH 1001
NOMAXVALUE
NOCYCLE
NOCACHE;







CREATE TABLE tenant (
 tenantNo               NUMBER(4) NOT NULL,
 tenantMail             VARCHAR2(80) unique,
 tenantPassword         VARCHAR2(16),
 tenantName             VARCHAR2(10),
 tenantSex              VARCHAR2(6),
 tenantAddress             VARCHAR2(65),
 tenantPhone               varchar2(15),
 tenantPic              BLOB,
 registerDate       DATE,
 
 CONSTRAINT tenant_tenantNo_PK PRIMARY KEY (tenantNo)
 );
 
CREATE SEQUENCE tenantNo_seq
INCREMENT BY 1
START WITH 2001
NOMAXVALUE
NOCYCLE
NOCACHE;


CREATE TABLE access_emp (
 empNo              NUMBER(4),
 accessNo        NUMBER(4),
	CONSTRAINT access_emp_PK PRIMARY KEY (empNo, accessNo),
	CONSTRAINT access_emp_FK FOREIGN KEY (empNo) REFERENCES emp(empNo)
);


CREATE TABLE empAccess (
 empAccessNo           NUMBER(4),
 empAccess             VARCHAR2(30),
 CONSTRAINT empAccess_PK PRIMARY KEY (empAccessNo)
);

 
CREATE SEQUENCE empAccessNo_seq
INCREMENT BY 1
START WITH 9001
NOMAXVALUE
NOCYCLE
NOCACHE;




create table dealer(
dealerNo		Number(10) NOT NULL,	
dealerPassword	Varchar2(20),	
dealerName	Varchar2(10),	
dealerSex		Varchar2(5),	
dealerAddress	Varchar2(65),	
dealerPhone	Varchar2(15),	
dealerMail		Varchar2(80),	
dealerState	Number(1),	
dealerAccount	Varchar2(15),	
CONSTRAINT DEALER_dealerNo_PK PRIMARY KEY(dealerNo));

create sequence dealerNo_seq
increment by 1
start with 1001
nomaxvalue
nocycle
nocache;

create table hostel(
hostelNo		Number(10) NOT NULL,
dealerNo		Number(10) NOT NULL,
hostelName		Varchar2(25),
hostelPhone		Varchar2(20),	
hostelAddress		Varchar2(150),	
hostelWebPages		Varchar2(100),		
hostelState		Number(1),
hostelVerification	Number(1),
hostelLat		Number(10,5),	
hostelLon		Number(10,5),		
hostelPicture		Blob,
dealerVerify		Blob,
hostelCautions		Varchar2(4000),
hostelContent		Varchar2(4000),	
CONSTRAINT HOSTEL_hostelNo_PK PRIMARY KEY(hostelNo),
CONSTRAINT HOSTEL_DEALERNO_FK FOREIGN KEY (dealerNo) references dealer (dealerNo));

create sequence hostelNo_seq
increment by 1
start with 2001
nomaxvalue
nocycle
nocache;


CREATE TABLE Facility (
 FacilityNo    NUMBER(5) NOT NULL,
 television            NUMBER(1,0),
 wifi                  NUMBER(1,0),
 kitchen               NUMBER(1,0),
 parking               NUMBER(1,0),
 bathroom              NUMBER(1,0),
 airConditioning       NUMBER(1,0),
 pet                   NUMBER(1,0),
 toiletries            NUMBER(1,0),
 roomPhone             NUMBER(1,0),
 roomBedService        NUMBER(1,0),
 CONSTRAINT Facility_FacilityNo_PK PRIMARY KEY (FacilityNo)
);

 
CREATE SEQUENCE FacilityNo_seq
INCREMENT BY 1
START WITH 4001
NOMAXVALUE
NOCYCLE
NOCACHE;



create table roomType(
roomTypeNo		  Number(10) NOT NULL,
hostelNo			  Number(10) NOT NULL,
FacilityNo		  Number(5)  NOT NULL,
roomTypeName		Varchar2(14),
roomTypeContain	Number(5),		
roomTypePrice		Number(10),	
roomTypeContent	Varchar2(3000),
roomTypePicture Blob,
CONSTRAINT roomType_roomTypeNo_PK PRIMARY KEY(roomTypeNo),
CONSTRAINT roomType_hostelNo_FK FOREIGN KEY (hostelNo) references hostel (hostelNo),
CONSTRAINT roomType_FacilityNo_FK FOREIGN KEY (FacilityNo) references Facility (FacilityNo));


create sequence roomTypeNo_seq
increment by 1
start with 3001
nomaxvalue
nocycle
nocache;

create table room(
roomNo		Number(10) NOT NULL,
hostelNo		Number(10) NOT NULL,
roomTypeNo	Number(10) NOT NULL,
hostelOrderDetailNo Number(10),
roomState	Varchar2(10),	
CONSTRAINT room_roomNo_PK PRIMARY KEY(roomNo),
CONSTRAINT room_hostelNo_FK FOREIGN KEY (hostelNo) references hostel (hostelNo),
CONSTRAINT room_roomTypeNo_FK FOREIGN KEY (roomTypeNo) references roomType (roomTypeNo));

create sequence roomNo_seq
increment by 1
start with 5001
nomaxvalue
nocycle
nocache;

create table hostelPic(
hostelPicNo	Number(10) NOT NULL,
hostelNo		Number(10) NOT NULL,
hostelPhoto	Blob,
CONSTRAINT hostelPic_hostelPicNo_PK PRIMARY KEY (hostelPicNo),
CONSTRAINT hostelPic_hostelNo_FK FOREIGN KEY (hostelNo) references hostel (hostelNo)
);

create sequence hostelPicNo_seq
increment by 1
start with 6001
nomaxvalue
nocycle
nocache;

create table roomTypePic(
roomTypePicNo	Number(10) NOT NULL,
roomTypeNo	Number(10) NOT NULL,
roomTypePhoto	Blob,
CONSTRAINT roomTypePic_roomTypePicNo_PK PRIMARY KEY (roomTypePicNo),
CONSTRAINT roomTypePic_roomTypeNo_FK FOREIGN KEY (roomTypeNo) references roomType (roomTypeNo)
);

create sequence roomTypePicNo_seq
increment by 1
start with 7001
nomaxvalue
nocycle
nocache;


         

CREATE TABLE VIEWINFO(
  VIEWNO NUMBER(10) NOT NULL,
  VIEWNAME VARCHAR2(25) NOT NULL,
  VIEWMANAGER VARCHAR2(100),
  VIEWPHONE VARCHAR2(20),
  VIEWADDRESS VARCHAR2(300) ,
  VIEWWEB VARCHAR2(300),
  VIEWLON NUMBER(35,15),
  VIEWLAT NUMBER(35,15),
  VIEWOPEN VARCHAR2(300),
  VIEWTICKET VARCHAR2(500),
  VIEWEQUI VARCHAR2(500),
  VIEWCONTENT VARCHAR2(3000),
  CONSTRAINT VIEINFO_PRIMARY_KEY PRIMARY KEY (VIEWNO)
);


CREATE SEQUENCE VIEWNO_SEQ
INCREMENT BY 1
START WITH 7001
NOMAXVALUE
NOCYCLE
NOCACHE;

  
  



CREATE TABLE VIEWPHOTO (
 VIEWPICNO           NUMBER(10) NOT NULL,
 VIEWNO              NUMBER(10),
 VIEWPIC             BLOB,
 CONSTRAINT VIEWPHOTO_PRIMARY_KEY PRIMARY KEY (VIEWPICNO),
 CONSTRAINT VIEWPHOTO_VIEWNO_FK FOREIGN KEY (VIEWNO) REFERENCES VIEWINFO (VIEWNO)
);
 
CREATE SEQUENCE VIEWPICNO_SEQ
INCREMENT BY 1
START WITH 8001
NOMAXVALUE
NOCYCLE
NOCACHE;


CREATE TABLE VIEWLIST (
 VIEWLISTNO            NUMBER(6) NOT NULL,
 TENANTNO              NUMBER(6) ,
 VIEWNO                NUMBER(6) ,
 VIEWLISTDATE	       	  DATE,
 CONSTRAINT VIEWLIST_PRIMARY_KEY PRIMARY KEY (VIEWLISTNO) ,
 CONSTRAINT VIEWLIST_TENANTNO_FK FOREIGN KEY (TENANTNO) REFERENCES tenant (tenantNo),
 CONSTRAINT VIEWLIST_VIEWNO_FK FOREIGN KEY (VIEWNO) REFERENCES VIEWINFO (VIEWNO)
);
 
CREATE SEQUENCE VIEWLISTNo_seq
INCREMENT BY 1
START WITH 9001
NOMAXVALUE
NOCYCLE
NOCACHE;

CREATE TABLE PROMINFO (
 PROMNO             NUMBER(10) NOT NULL,
 HOSTELNO             NUMBER(10),
 PROMTITLE            VARCHAR2(100),
 PROMCONTENT          VARCHAR2(300),
 PROMDISCOUNT          NUMBER(5,3),
 PROMDUE     DATE,
 PROMEND     DATE,
 CONSTRAINT PROMINFO_PRIMARY_KEY PRIMARY KEY (PROMNO),
 CONSTRAINT PROMINFO_HOSTELNO_FK FOREIGN KEY (HOSTELNO) REFERENCES hostel (HOSTELNO)
);
 
CREATE SEQUENCE PROMNO_SEQ
INCREMENT BY 1
START WITH 6001
NOMAXVALUE
NOCYCLE
NOCACHE;


CREATE TABLE AD (
 ADNO           NUMBER(10) NOT NULL,
 DEALERNO       NUMBER(10),
 ADODDATE       DATE,
 ODSTATUS       NUMBER(1),
 ADPIC          BLOB,
 ODPRICE        NUMBER(10),
 ADSTATUS       NUMBER(1),
 ADSTARTLINE    DATE,
 ADDEADLINE     DATE,

 CONSTRAINT AD_ADNO_PK PRIMARY KEY (ADNO),
 CONSTRAINT AD_DEALERNO_FK FOREIGN KEY (DEALERNO) REFERENCES DEALER (DEALERNO)
);
 
CREATE SEQUENCE adNo_seq
INCREMENT BY 1
START WITH 20000
NOMAXVALUE 
NOCYCLE
NOCACHE; 



CREATE TABLE BOARD (
 BOARDNO             NUMBER(10) NOT NULL,
 HOSTELNO            NUMBER(10),
 TENANTNO            NUMBER(10),
 bdDATE              TIMESTAMP,
 WHICH               NUMBER(10),
 bdCONTENT           VARCHAR2(1000),
 
  
 CONSTRAINT BOARD_BOARDNO_PK PRIMARY KEY(BOARDNO),
 CONSTRAINT BOARD_HOSTELNO_FK FOREIGN KEY (HOSTELNO) REFERENCES hostel (HOSTELNO),
 CONSTRAINT BOARD_TENANTNO_FK FOREIGN KEY (TENANTNO) REFERENCES TENANT (TENANTNO)
);
 
CREATE SEQUENCE BOARDNO_seq
INCREMENT BY 1
START WITH 10000
NOMAXVALUE 
NOCYCLE
NOCACHE;

CREATE TABLE HostelTrace (
HostelTraceNo       NUMBER(10) NOT NULL,
HostelNo            NUMBER(10),
TenantNo            NUMBER(10),
ViewDate            DATE,

 CONSTRAINT HostelTrace_HostelTraceNo_PK PRIMARY KEY(HostelTraceNo),
 CONSTRAINT HostelTrace_HOSTELNO_FK FOREIGN KEY (HOSTELNO) REFERENCES hostel (HOSTELNO),
 CONSTRAINT HostelTrace_TENANTNO_FK FOREIGN KEY (TENANTNO) REFERENCES TENANT (TENANTNO)
);

CREATE SEQUENCE HostelTraceNo_seq
INCREMENT BY 1
START WITH 10000
NOMAXVALUE 
NOCYCLE
NOCACHE;


CREATE TABLE HostelNews (
HostelNewsNo        NUMBER(10) NOT NULL,
HostelNo            NUMBER(10),
NewsContent         VARCHAR2(1000),
UpdateDate          DATE,

 CONSTRAINT HostelNews_HostelNewsNo_PK PRIMARY KEY(HostelNewsNo),
 CONSTRAINT HostelNews_HOSTELNO_FK FOREIGN KEY (HOSTELNO) REFERENCES hostel (HOSTELNO)
);

CREATE SEQUENCE HostelNewsNo_seq
INCREMENT BY 1
START WITH 11000
NOMAXVALUE 
NOCYCLE
NOCACHE;

CREATE TABLE HostelOrder (
HostelOrderNo        NUMBER(10) NOT NULL,
HostelNo            NUMBER(10),
TenantNo            NUMBER(10),
HostelOrderDate     DATE,
HostelScore         NUMBER(4),
HostelComment       VARCHAR2(1000),
TenantScore         NUMBER(4),
CustomerQuantity    NUMBER(4),
PaymentWay          VARCHAR2(100),
PaymentState        VARCHAR2(100),
orderRemark					  VARCHAR2(1000),
orderState							 VARCHAR2(100),


 CONSTRAINT HostelOrder_HostelOrderNo_PK PRIMARY KEY(HostelOrderNo),
 CONSTRAINT HostelOrder_HOSTELNO_FK FOREIGN KEY (HOSTELNO) REFERENCES hostel (HOSTELNO),
 CONSTRAINT HostelOrder_TENANTNO_FK FOREIGN KEY (TENANTNO) REFERENCES TENANT (TENANTNO)
);

CREATE SEQUENCE HostelOrderNo_seq
INCREMENT BY 1
START WITH 12000
NOMAXVALUE 
NOCYCLE
NOCACHE;

CREATE TABLE HostelOrderDetail (
HostelOrderDetailNo NUMBER(10) NOT NULL,
HostelOrderNo       NUMBER(10),
RoomTypeNo          NUMBER(10),
RoomQuantity        NUMBER(10),
CheckInDate         TIMESTAMP,
CheckOutDate        TIMESTAMP,
totalPrice							NUMBER(10),



 CONSTRAINT HostelOrderDetailNo_PK PRIMARY KEY(HostelOrderDetailNo),
 CONSTRAINT HostelOrderNo_FK FOREIGN KEY (HostelOrderNo) REFERENCES HostelOrder (HostelOrderNo),
 CONSTRAINT RoomTypeNo_FK FOREIGN KEY (RoomTypeNo) REFERENCES roomType (roomTypeNo)
);

CREATE SEQUENCE HostelOrderDetailNo_seq
INCREMENT BY 1
START WITH 13000
NOMAXVALUE 
NOCYCLE
NOCACHE;


commit;



CREATE TABLE IF NOT EXISTS "message" (
"serialid"  TEXT(15) NOT NULL,
"action"  TEXT(50),
"warehouseid" TEXT(50),
"senderid"  TEXT(50),
"sendername"  TEXT(50),
"receiverid"  TEXT(50),
"receivername"  TEXT(50),
"sendtime"  TEXT(50),
"info"  TEXT(400),
"isreceived"  TEXT(5),
PRIMARY KEY ("serialid")
);

CREATE TABLE IF NOT EXISTS "user" (
"id" TEXT(50)  NOT NULL,
"password"  TEXT(50),
"name" TEXT(50),
"offpassword"  TEXT(50),
"warehouseid" TEXT(50),  
PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "bo_swy_user" (
"userid" TEXT(50)  NOT NULL,
"userName"  TEXT(50),
"versionname" TEXT(50),
"deviceid"  TEXT(50),
"registerDate" TEXT(50),  
"code" TEXT(50),  
"database" TEXT(50),  
"accountset" TEXT(50),  
"sleep" INT(50),  
"state" INT(50),  
"message" TEXT(50),  
"memory" TEXT(50)  
);


## commented test code to verify table behavior
	#show tables from flexbase_schema;
	#describe habit;
	#select * from habit;

# create HABIT table to store all attributes of user's habits
create table HABIT (
 HABIT_ID int not null auto_increment primary key,
 #USER_ID char(50) not null,
 HABIT_NAME char(50) not null,
 HABIT_PRIORITY int not null,
 HABIT_TIME_START time,
 HABIT_TIME_END time
 #foreign key USER_ID references (USER_INFO) USER_ID
 );
 
#create table USER_INFO (
#USER_ID int not null auto_increment primary key,
#USER_NAME char(50) not null,
#USER_PASS char(50) not null
#);


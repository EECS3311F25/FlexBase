## commented test code to verify table behavior
	#show tables from flexbase_schema;
	#describe habit;
	#select * from habit;

# create HABIT table to store all attributes of user's habits
create table HABIT (
 HABIT_ID int not null auto_increment primary key,
 HABIT_NAME char(50) not null,
 HABIT_PRIORITY int not null,
 HABIT_TIME_START time,
 HABIT_TIME_END time
 );
 
 
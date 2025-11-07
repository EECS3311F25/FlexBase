## commented test code to verify table behavior
	#show tables from flexbase_schema;
	#describe habit;
	#select * from habit;

# create USER_INFO table to store user information
create table USER_INFO (
USER_ID int not null auto_increment primary key,
USER_NAME char(50) not null,
USER_PASS char(50) not null
);

# create HABIT table to store all attributes of user's habits
create table HABIT (
 HABIT_ID int not null auto_increment primary key,
 USER_ID char(50) not null,
 HABIT_NAME char(50) not null,
 HABIT_PRIORITY int not null,
 HABIT_TIME_START time,
 HABIT_TIME_END time
 #foreign key (USER_ID) references USER_INFO(USER_ID)
 );
 
 # create OPTIMIZED_HABIT table to store optimized habits and their attributes
 create table OPTIMIZED_HABIT (
 O_HABIT_ID int not null auto_increment primary key,
 USER_ID char(50) not null,
 O_HABIT_NAME char(50) not null,
 O_HABIT_PRIORITY int not null,
 O_HABIT_TIME_START time,
 O_HABIT_TIME_END time,
 foreign key (O_HABIT_ID) references HABIT(HABIT_ID),
 foreign key (USER_ID) references USER(USER_ID)
 );



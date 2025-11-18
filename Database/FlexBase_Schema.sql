## commented test code to verify table behavior
	#show tables from flexbase_schema;
    
    #describe user_info;
	#describe habit;
    #describe optimized_habit;
    
	#select * from habit;
    #select * from user_info;
    #select * from optimized_habit;
    
    #drop table habit, user_info;

# create USER_INFO table to store user information
create table USER_INFO (
USER_ID int not null auto_increment primary key,
USER_NAME char(50) not null,
USER_PASS char(50) not null
);

# create HABIT table to store all attributes of user's habits
create table HABIT (
 HABIT_ID int not null auto_increment primary key,
 USER_ID int not null,
 HABIT_NAME char(50) not null,
 HABIT_PRIORITY int not null,
 HABIT_TIME_START time,
 HABIT_TIME_END time,
 foreign key (USER_ID) references USER_INFO(USER_ID)
 );
 
 # create OPTIMIZED_HABIT table to store optimized habits and their attributes
 create table OPTIMIZED_HABIT (
 O_HABIT_ID int not null auto_increment primary key,
 USER_ID int not null,
 O_HABIT_NAME char(50) not null,
 O_HABIT_PRIORITY int not null,
 O_HABIT_TIME_START time,
 O_HABIT_TIME_END time,
 foreign key (O_HABIT_ID) references HABIT(HABIT_ID),
 foreign key (USER_ID) references USER_INFO(USER_ID)
 );


## funtionality testing with manually inputted values

	#insert into user_info (user_name, user_pass) values
	#('pranay', 'password'),
	#('thor', 'pass'),
	#('amraj', '123')
	#;

	#select * from user_info;

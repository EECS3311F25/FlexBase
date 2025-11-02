#show tables from 3421_a2_2025;
#describe assign;

create table HABIT (
 HABIT_ID int not null primary key,
 HABIT_NAME char(50) not null,
 HABIT_PRIORITY int not null,
 HABIT_TIME_START time,
 HABIT_TIME_END time
 );
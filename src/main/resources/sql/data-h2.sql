insert into user (msrl, name, password, uid) values (null, '정후영', '{bcrypt}$2a$10$NzO9HD1FPFNY/X1uK0Wtm.B6wug9XnZtPo/jrl8GPCJTDn8twUK6q', 'whize3');
insert into user_roles (user_msrl, roles) values (1, 'ROLE_USER');
insert into keyword (query, hit) values ('인천', 10), ('서울', 9), ('부산', 8), ('성남', 3), ('분당', 2), ('여의도', 25), ('강서구', 15), ('국회의사당', 3), ('청와대', 6), ('롯데타워', 7), ('남산타워', 5);
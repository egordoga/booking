insert into user values (1, 'cat@google.com', 'Cat');
insert into user values (2, 'dog@google.com', 'Dog');
insert into user values (3, 'cow@google.com', 'Cow');
insert into user values (4, 'tiger@google.com', 'Tiger');

insert into category values (1, 'luxury');
insert into category values (2, 'semi-luxury');
insert into category values (3, 'standard');
insert into category values (4, 'economy');

insert into option values (1, 'breakfast', 10);
insert into option values (2, 'cleaning', 7);
insert into option values (3, 'minibar', 15);
insert into option values (4, 'ironing ', 7);

insert into room values (1, 10, 100, 1);
insert into room values (2, 11, 70, 2);
insert into room values (3, 12, 50, 3);
insert into room values (4, 13, 30, 4);
insert into room values (5, 14, 100, 1);
insert into room values (6, 15, 70, 2);
insert into room values (7, 16, 50, 3);
insert into room values (8, 17, 30, 4);
insert into room values (9, 18, 100, 1);
insert into room values (10, 19, 70, 2);
insert into room values (11, 20, 50, 3);
insert into room values (12, 21, 30, 4);

insert into booking values (1, '2019-01-20', '2019-01-15', 1, 1);
insert into booking values (2, '2019-05-20', '2019-02-15', 12, 2);
insert into booking values (3, '2019-06-20', '2019-06-15', 6, 3);
insert into booking values (4, '2019-12-31', '2019-11-15', 11, 4);
insert into booking values (5, '2019-12-26', '2019-12-20', 5, 1);
insert into booking values (6, '2019-11-30', '2019-11-22', 2, 2);
insert into booking values (7, '2019-12-01', '2019-10-25', 10, 3);
insert into booking values (8, '2019-12-20', '2019-11-05', 4, 4);
insert into booking values (9, '2019-11-20', '2019-11-15', 8, 1);
insert into booking values (10, '2018-12-05', '2018-12-01', 2, 2);
insert into booking values (11, '2019-01-20', '2018-12-27', 5, 3);
insert into booking values (12, '2018-12-22', '2018-11-15', 1, 4);

insert into room_option values (1, 1);
insert into room_option values (2, 2);
insert into room_option values (3, 3);
insert into room_option values (3, 2);
insert into room_option values (4, 4);
insert into room_option values (6, 3);
insert into room_option values (9, 4);
insert into room_option values (10, 2);
insert into room_option values (11, 2);
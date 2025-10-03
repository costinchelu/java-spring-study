INSERT INTO course_details (id, name, last_updated, created_time, is_deleted) VALUES (10001, 'COURSE 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO course_details (id, name, last_updated, created_time, is_deleted) VALUES (10002, 'COURSE 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO course_details (id, name, last_updated, created_time, is_deleted) VALUES (10003, 'COURSE 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO course_details (id, name, last_updated, created_time, is_deleted) VALUES (10004, 'COURSE 4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO course_details (id, name, last_updated, created_time, is_deleted) VALUES (10005, 'COURSE 5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO course_details (id, name, last_updated, created_time, is_deleted) VALUES (10006, 'COURSE 6', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO course_details (id, name, last_updated, created_time, is_deleted) VALUES (10007, 'COURSE 7', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO course_details (id, name, last_updated, created_time, is_deleted) VALUES (10008, 'COURSE 8', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO course_details (id, name, last_updated, created_time, is_deleted) VALUES (10009, 'COURSE 9', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO course_details (id, name, last_updated, created_time, is_deleted) VALUES (10010, 'COURSE 10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);

INSERT INTO passport (id, number) VALUES (40001, 'L1245');
INSERT INTO passport (id, number) VALUES (40002, 'T4125');
INSERT INTO passport (id, number) VALUES (40003, 'J4585');

INSERT INTO student (id, name, passport_id) VALUES (20001, 'John', 40001);
INSERT INTO student (id, name, passport_id) VALUES (20002, 'Adam', 40002);
INSERT INTO student (id, name, passport_id) VALUES (20003, 'Jane', 40003);

INSERT INTO review (id, rating, review_body, course_id, student_id) VALUES (50001, 'FIVE', 'Great course', 10001, 20001);
INSERT INTO review (id, rating, review_body, course_id, student_id) VALUES (50002, 'FOUR', 'Wonderful course', 10001, 20003);
INSERT INTO review (id, rating, review_body, course_id, student_id) VALUES (50003, 'FIVE', 'Awesome course', 10003, 20001);

INSERT INTO student_course(student_id, course_id) VALUES (20001, 10001);
INSERT INTO student_course(student_id, course_id) VALUES (20002, 10001);
INSERT INTO student_course(student_id, course_id) VALUES (20003, 10001);
INSERT INTO student_course(student_id, course_id) VALUES (20001, 10003);



INSERT INTO course_details (id, name, last_updated, created_time) VALUES (10001, 'COURSE 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO course_details (id, name, last_updated, created_time) VALUES (10002, 'COURSE 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO course_details (id, name, last_updated, created_time) VALUES (10003, 'COURSE 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO passport (id, number) VALUES (40001, 'L1245');
INSERT INTO passport (id, number) VALUES (40002, 'T4125');
INSERT INTO passport (id, number) VALUES (40003, 'J4585');

INSERT INTO student (id, name, passport_id) VALUES (20001, 'John', 40001);
INSERT INTO student (id, name, passport_id) VALUES (20002, 'Adam', 40002);
INSERT INTO student (id, name, passport_id) VALUES (20003, 'Jane', 40003);

INSERT INTO review (id, rating, review_body, course_id, student_id) VALUES (50001, '5', 'Great course', 10001, 20001);
INSERT INTO review (id, rating, review_body, course_id, student_id) VALUES (50002, '4', 'Wonderful course', 10001, 20003);
INSERT INTO review (id, rating, review_body, course_id, student_id) VALUES (50003, '5', 'Awesome course', 10003, 20001);

INSERT INTO student_course(student_id, course_id) VALUES (20001, 10001);
INSERT INTO student_course(student_id, course_id) VALUES (20002, 10001);
INSERT INTO student_course(student_id, course_id) VALUES (20003, 10001);
INSERT INTO student_course(student_id, course_id) VALUES (20001, 10003);



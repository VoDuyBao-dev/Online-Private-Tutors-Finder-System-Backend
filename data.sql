-- USE tutors_finder_system;

-- mật khẩu 12345678 
INSERT INTO users (full_name, email, password_hash, role, phone_number, status, updated_at, avatar_image)
VALUES
('Nguyễn Văn Admin', 'admin1@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ADMIN', '0901111222', 'Active', NOW(), "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
('Trần Thị B', 'tutor1@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0903333444', 'Active', NOW(), "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
('Lê Văn C', 'tutor2@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0905555666', 'Active', NOW(), "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
('Phạm Thị D', 'tutor3@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0907777888', 'inActive', NOW(), "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
('Ngô Minh E', 'parent1@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0911111222', 'Active', NOW(), "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
('Trần Anh F', 'parent2@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0913333444', 'Active', NOW(), "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
('Hoàng Văn G', 'parent3@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0915555666', 'Active', NOW(), "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
('Vũ Thị H', 'tutor4@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0908888999','Active', NOW(), "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
('Nguyễn Minh I', 'tutor5@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0902222333', 'Active', NOW(), "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
('Lâm Thị K', 'parent4@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0917777888', 'Active', NOW(), "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
('Lâm Văn H', 'parent5@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0917777999', 'Active', NOW(), "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp");


INSERT INTO subjects (subject_name) VALUES
-- Các môn học cốt lõi
('Toán'),
('Vật lí'),
('Hóa học'),
('Sinh học'),
('Ngữ văn'),
('Tiếng Việt'), -- Dành cho cấp 1
('Lịch sử'),
('Địa lí'),
('Tiếng Anh'),

-- Các môn xã hội và giáo dục
('Giáo dục công dân (GDCD)'),
('Đạo đức'), -- Dành cho cấp 1
('Giáo dục kinh tế và pháp luật'), -- Dành cho cấp 3 (Chương trình mới)
('Giáo dục Quốc phòng và An ninh'),

-- Các môn tích hợp (Theo chương trình mới)
('Tự nhiên và Xã hội'), -- Lớp 1, 2, 3
('Khoa học'), -- Lớp 4, 5
('Khoa học tự nhiên'), -- Cấp 2
('Lịch sử và Địa lí'), -- Cấp 2

-- Các môn công nghệ và năng khiếu
('Tin học'),
('Công nghệ'),
('Âm nhạc'),
('Mĩ thuật'),
('Giáo dục thể chất'),
('Hoạt động trải nghiệm, hướng nghiệp'),

-- Các môn kỹ năng và luyện thi phổ biến
('Luyện viết chữ đẹp'),
('Tiếng Anh giao tiếp'),
('Luyện thi IELTS'),
('Luyện thi TOEIC'),
('Lập trình'),

-- Các ngoại ngữ khác
('Tiếng Pháp'),
('Tiếng Nhật'),
('Tiếng Trung'),
('Tiếng Hàn'),
('Tiếng Đức');


INSERT INTO tutors (user_id, gender, address, university, educational_level, introduction, price_per_hour, verification_status)
VALUES
(2, 'FEMALE', 'Hà Nội', 'ĐH Sư phạm Hà Nội', 'Đại học', 'Kinh nghiệm 3 năm dạy Toán', 150000, 'APPROVED'),
(3, 'MALE', 'TP.HCM', 'ĐH Bách Khoa', 'Đại học', 'Dạy Lý chuyên cấp 3', 180000, 'APPROVED'),
(4, 'FEMALE', 'Đà Nẵng', 'ĐH Sư phạm Đà Nẵng', 'Đại học', 'Dạy Hóa cơ bản', 160000, 'PENDING'),
(8, 'FEMALE', 'Cần Thơ', 'ĐH Cần Thơ', 'Cao học', 'Dạy tiếng Anh giao tiếp', 200000, 'APPROVED'),
(9, 'MALE', 'Huế', 'ĐH Khoa học Huế', 'Đại học', 'Gia sư luyện thi TOEIC', 220000, 'APPROVED');

-- ===== CERTIFICATES FOR TUTOR 1 - 5 =====
INSERT INTO tutor_certificates (certificate_id, tutor_id, certificate_name, approved)
VALUES
-- Tutor 1
(1, 1, 'Chứng chỉ Sư phạm Toán học', TRUE),
(2, 1, 'IELTS 6.5 Academic', TRUE),
(3, 1, 'Giấy chứng nhận “Dạy kèm Toán THCS nâng cao”', TRUE),

-- Tutor 2
(4, 2, 'Chứng chỉ Sư phạm Vật Lý', TRUE),
(5, 2, 'Giải Nhì Học sinh giỏi Vật Lý cấp tỉnh', TRUE),
(6, 2, 'Chứng chỉ Bồi dưỡng phương pháp dạy học STEM', TRUE),

-- Tutor 3
(7, 3, 'IELTS 7.5 Academic', TRUE),
(8, 3, 'TESOL Certificate', TRUE),
(9, 3, 'Chứng chỉ Sư phạm tiếng Anh', TRUE),

-- Tutor 4
(10, 4, 'Chứng chỉ Sư phạm Hóa học', TRUE),
(11, 4, 'Chứng chỉ Ứng dụng CNTT cơ bản', TRUE),
(12, 4, 'Giấy chứng nhận Dạy kèm Hóa THPT nâng cao', TRUE),

-- Tutor 5
(13, 5, 'MOS Microsoft Office Specialist', TRUE),
(14, 5, 'Chứng chỉ Ứng dụng CNTT nâng cao', TRUE),
(15, 5, 'Chứng chỉ Lập trình cơ bản – Bộ GD&ĐT', TRUE);

INSERT INTO tutor_certificate_files 
(file_id, certificate_id, file_url, status, is_active, uploaded_at)
VALUES
-- Tutor 1
(1, 1, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(2, 2, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(3, 3, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),

-- Tutor 2
(4, 4, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(5, 5, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(6, 6, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),

-- Tutor 3
(7, 7, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(8, 8, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(9, 9, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),

-- Tutor 4
(10, 10, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(11, 11, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(12, 12, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),

-- Tutor 5
(13, 13, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(14, 14, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(15, 15, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW());




INSERT INTO tutor_subjects (tutor_id, subject_id)
VALUES
(1, 1),  -- Toán
(1, 9),  -- Tiếng Anh
(2, 2),  -- Vật lý
(3, 3),  -- Hóa học
(4, 27), -- Tiếng Anh giao tiếp
(5, 29), -- Luyện thi TOEIC
(5, 28), -- Luyện thi IELTS
(2, 18), -- Tin học
(3, 4),  -- Sinh học
(4, 9);  -- Tiếng Anh


INSERT INTO learner (user_id, full_name, gender, grade, school, description, address, address_detail)
VALUES
(5, 'Ngô Minh E', 'MALE', 'Lớp 7', 'THCS Trần Phú', 'Muốn học thêm Toán nâng cao', 'Hà Nội', '123 Nguyễn Trãi'),
(6, 'Trần Anh F', 'FEMALE', 'Lớp 9', 'THCS Lý Tự Trọng', 'Chuẩn bị thi vào 10', 'TP.HCM', '45 Trần Hưng Đạo'),
(7, 'Hoàng Văn G', 'MALE', 'Lớp 12', 'THPT Bùi Thị Xuân', 'Ôn thi đại học môn Lý', 'Huế', '10 Lê Lợi'),
(10, 'Lâm Thị K', 'FEMALE', 'Lớp 8', 'THCS Nguyễn Du', 'Cần học tiếng Anh giao tiếp', 'Cần Thơ', '22 Nguyễn Văn Cừ'),
(11, 'Lâm Văn H', 'MALE', 'Lớp 5', 'Tiểu học Kim Đồng', 'Cần học thêm Toán và Tiếng Việt', 'Hà Nội', '78 Trường Chinh');


INSERT INTO tutor_availability (tutor_id, start_time, end_time, status)
VALUES
(1, '2025-11-03 18:00:00', '2025-11-03 20:00:00', 'AVAILABLE'),
(1, '2025-11-05 18:00:00', '2025-11-05 20:00:00', 'BOOKED'),
(2, '2025-11-06 09:00:00', '2025-11-06 11:00:00', 'AVAILABLE'),
(2, '2025-11-07 09:00:00', '2025-11-07 11:00:00', 'Cancelled'),
(3, '2025-11-08 14:00:00', '2025-11-08 16:00:00', 'AVAILABLE'),
(3, '2025-11-09 14:00:00', '2025-11-09 16:00:00', 'BOOKED'),
(4, '2025-11-10 17:00:00', '2025-11-10 19:00:00', 'AVAILABLE'),
(4, '2025-11-12 17:00:00', '2025-11-12 19:00:00', 'AVAILABLE'),
(5, '2025-11-13 18:00:00', '2025-11-13 20:00:00', 'AVAILABLE'),
(5, '2025-11-14 18:00:00', '2025-11-14 20:00:00', 'BOOKED');

INSERT INTO tutor_availability (tutor_id, start_time, end_time, status)
VALUES
(1, '2025-12-02 18:00:00', '2025-12-02 20:00:00', 'AVAILABLE'),
(1, '2025-12-04 18:00:00', '2025-12-04 20:00:00', 'AVAILABLE'),
(1, '2025-12-06 09:00:00', '2025-12-06 11:00:00', 'AVAILABLE'),
(1, '2025-12-08 18:30:00', '2025-12-08 20:00:00', 'BOOKED'),
(1, '2025-12-10 17:00:00', '2025-12-10 19:00:00', 'CANCELLED'),
(2, '2025-12-01 09:00:00', '2025-12-01 11:00:00', 'AVAILABLE'),
(2, '2025-12-03 09:00:00', '2025-12-03 11:00:00', 'BOOKED'),
(2, '2025-12-05 14:00:00', '2025-12-05 16:00:00', 'AVAILABLE'),
(2, '2025-12-08 09:00:00', '2025-12-08 11:00:00', 'AVAILABLE'),
(2, '2025-12-10 13:00:00', '2025-12-10 15:00:00', 'CANCELLED');


   
INSERT INTO tutor_recurring_pattern 
(tutor_id, repeat_type, days_of_week, days_of_month, start_time, end_time, repeat_start, repeat_end)
VALUES
(1, 'WEEKLY', '["Monday","Wednesday","Friday"]', NULL, '18:00:00', '20:00:00', '2025-11-01', '2025-12-01'),
(2, 'WEEKLY', '["Tuesday","Thursday"]', NULL, '09:00:00', '11:00:00', '2025-11-01', '2025-12-15'),
(3, 'MONTHLY', NULL, '[5,15,25]', '14:00:00', '16:00:00', '2025-11-01', '2026-02-01'),
(4, 'WEEKLY', '["Saturday"]', NULL, '08:00:00', '10:00:00', '2025-11-01', '2025-11-30'),
(5, 'MONTHLY', NULL, '[10,20]', '18:00:00', '20:00:00', '2025-11-01', '2026-01-01');
-- dựa vào 2 bảng trên để hiển thị chính xác những lớp nào mà gia sư bận ở trong giao diện lịch của gia sư
   


INSERT INTO class_requests (learner_id, tutor_id, subject, total_sessions, sessions_per_week, start_date, end_date, additional_notes, status, type)
VALUES
(1, 1, 1, 10, 2, '2025-12-01', '2025-12-30', 'Muốn học tối thứ 2,4', 'CONFIRMED', 'OFFICIAL'),
(2, 2, 2, 8, 2, '2025-11-03', '2025-12-03', 'Học để ôn thi học kỳ', 'PENDING', 'TRIAL'),
(3, 3, 3, 12, 3, '2025-11-05', '2026-01-05', 'Học chiều thứ 3,5,7', 'CONFIRMED', 'OFFICIAL'),
(4, 4, 27, 6, 2, '2025-11-02', '2025-11-30', 'Luyện nói tiếng Anh', 'CONFIRMED', 'OFFICIAL'),
(5, 5, 28, 15, 3, '2025-11-06', '2026-02-06', 'Luyện thi IELTS 7.0', 'PENDING', 'TRIAL'),
(1, 2, 18, 5, 2, '2025-11-07', '2025-12-07', 'Học tin học cơ bản', 'CONFIRMED', 'OFFICIAL'),
(2, 3, 4, 8, 2, '2025-11-08', '2025-12-08', 'Học ôn thi học kỳ', 'PENDING', 'TRIAL'),
(3, 5, 29, 10, 2, '2025-11-10', '2026-01-10', 'Luyện thi TOEIC 700+', 'CONFIRMED', 'OFFICIAL'),
(4, 1, 9, 6, 2, '2025-11-12', '2025-12-12', 'Học tiếng Anh giao tiếp', 'CONFIRMED', 'OFFICIAL'),
(5, 4, 1, 12, 3, '2025-11-14', '2026-01-14', 'Học Toán nâng cao', 'PENDING', 'TRIAL');
INSERT INTO class_requests (learner_id, tutor_id, subject, total_sessions, sessions_per_week, start_date, end_date, additional_notes, status, type)
VALUES
(3, 1, 1, 8, 2, '2026-01-05', '2026-02-05', 'Ôn tập học kỳ môn Toán', 'PENDING', 'TRIAL'),
(4, 1, 9, 12, 3, '2026-01-10', '2026-03-10', 'Cần cải thiện phát âm', 'CONFIRMED', 'OFFICIAL'),
(1, 2, 2, 6, 2, '2026-01-03', '2026-02-03', 'Lý cơ bản cho học sinh lớp 7', 'PENDING', 'TRIAL'),
(5, 2, 18, 10, 3, '2026-01-15', '2026-03-15', 'Học lập trình Python cơ bản', 'CONFIRMED', 'OFFICIAL');




INSERT INTO calendar_class (request_id, day_of_week, start_time, end_time)
VALUES
(1, 'MONDAY', '18:00:00', '19:30:00'),
(1, 'WEDNESDAY', '18:00:00', '19:30:00'),
(2, 'TUESDAY', '17:00:00', '18:30:00'),
(3, 'THURSDAY', '14:00:00', '15:30:00'),
(4, 'FRIDAY', '19:00:00', '20:30:00'),
(5, 'SATURDAY', '09:00:00', '10:30:00'),
(6, 'SUNDAY', '14:00:00', '15:30:00'),
(7, 'MONDAY', '08:00:00', '09:30:00'),
(8, 'WEDNESDAY', '19:00:00', '20:30:00'),
(9, 'FRIDAY', '08:00:00', '09:30:00');

-- Tutor 1
INSERT INTO calendar_class (request_id, day_of_week, start_time, end_time)
VALUES
(11, 'MONDAY', '18:00:00', '19:30:00'),
(11, 'THURSDAY', '18:00:00', '19:30:00'),
(12, 'TUESDAY', '19:00:00', '20:30:00'),
(12, 'FRIDAY', '19:00:00', '20:30:00');

-- Tutor 2
INSERT INTO calendar_class (request_id, day_of_week, start_time, end_time)
VALUES
(13, 'WEDNESDAY', '09:00:00', '10:30:00'),
(13, 'FRIDAY', '09:00:00', '10:30:00'),
(14, 'SATURDAY', '14:00:00', '16:00:00'),
(14, 'SUNDAY', '14:00:00', '16:00:00');



INSERT INTO classes (request_id, status, completed_sessions)
VALUES
(1, 'ONGOING', 3),
(2, 'ONGOING', 1),
(3, 'COMPLETED', 12),
(4, 'ONGOING', 5),
(5, 'PENDING', 0),
(6, 'ONGOING', 2),
(7, 'CANCELLED', 0),
(8, 'COMPLETED', 10),
(9, 'ONGOING', 4),
(10, 'PENDING', 0),
(11, 'PENDING', 0),
(12, 'ONGOING', 1),
(13, 'PENDING', 0),
(14, 'ONGOING', 2);



INSERT INTO ratings (class_id, score, comment)
VALUES
(1, 4.5, 'Gia sư dạy nhiệt tình'),
(2, 5.0, 'Rất dễ hiểu'),
(3, 4.0, 'Ổn nhưng hơi nhanh'),
(4, 4.8, 'Tốt'),
(5, 3.5, 'Chưa rõ ràng lắm'),
(6, 4.9, 'Tận tâm'),
(7, 4.7, 'Giải thích kỹ'),
(8, 5.0, 'Rất tốt'),
(9, 4.6, 'Giỏi và thân thiện'),
(10, 4.2, 'Hài lòng'),
(12, 4.7, 'Gia sư nhiệt tình và giảng dễ hiểu'),
(14, 4.9, 'Giảng chi tiết, dễ thực hành');


INSERT INTO notifications (user_id, type, title, content, is_read)
VALUES
(1, 'ACCOUNT_VERIFIED', 'Tài khoản admin đã được xác minh', 'Quyền quản trị đã sẵn sàng.', TRUE),
(2, 'REQUEST_ACCEPTED', 'Yêu cầu học đã được chấp nhận', 'Bạn đã chấp nhận một yêu cầu học mới.', FALSE),
(3, 'NEW_REQUEST', 'Bạn có yêu cầu lớp học mới', 'Phụ huynh vừa gửi yêu cầu môn Vật lý.', FALSE),
(4, 'REQUEST_REJECTED', 'Yêu cầu bị từ chối', 'Bạn đã từ chối một yêu cầu không phù hợp.', TRUE),
(5, 'CLASS_CONFIRMED', 'Lớp học đã được xác nhận', 'Lịch học Toán bắt đầu từ 2025-11-01.', FALSE),
(6, 'SESSION_REMINDER', 'Nhắc lịch học tối nay', 'Buổi học lúc 18:00–19:30 hôm nay.', FALSE),
(7, 'RATING_RECEIVED', 'Bạn nhận được đánh giá mới', 'Điểm: 4.8 – “Giải thích dễ hiểu”.', TRUE),
(8, 'MATERIAL_SHARED', 'Gia sư đã chia sẻ tài liệu', 'File “Tiếng Anh giao tiếp.pdf” đã được tải lên.', FALSE),
(9, 'MESSAGE', 'Bạn có tin nhắn mới', 'Phụ huynh vừa nhắn tin: “Hẹn giờ học CN?”', FALSE),
(10, 'CLASS_CANCELLED', 'Một buổi học đã bị hủy', 'Buổi học Chủ nhật 14:00–15:30 đã hủy.', TRUE),
(3, 'NEW_REQUEST', 'Bạn có yêu cầu học mới', 'Học viên lớp 7 muốn học Toán', FALSE),
(4, 'NEW_REQUEST', 'Bạn có yêu cầu học mới', 'Học viên lớp 8 muốn học Tiếng Anh', FALSE),
(2, 'NEW_REQUEST', 'Bạn có yêu cầu học mới', 'Phụ huynh lớp 7 cần học Lý', FALSE),
(2, 'CLASS_CONFIRMED', 'Lịch học mới', 'Lớp Tin học nâng cao đã xác nhận', FALSE);





INSERT INTO ebooks (title, type, file_path, uploaded_by, created_at)
VALUES
('Toán nâng cao lớp 7', 'SACH_GIAO_KHOA', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 2, NOW()),
('Vật lý cơ bản', 'TAI_LIEU', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 2, NOW()),
('Hóa học thực hành', 'TAI_LIEU', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 2, NOW()),
('Tiếng Anh giao tiếp', 'TAI_LIEU', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 2, NOW()),
('Ôn thi IELTS 7.0', 'DE_THI_THAM_KHAO', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 2, NOW()),
('Luyện thi TOEIC 700+', 'DE_THI_THAM_KHAO', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 2, NOW()),
('Tin học cơ bản', 'SACH_GIAO_KHOA', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 2, NOW()),
('Kỹ năng học tập hiệu quả', 'TAI_LIEU', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 2, NOW()),
('Bài tập Hóa nâng cao', 'TAI_LIEU', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 2, NOW()),
('Bộ đề thi THPT Quốc gia', 'DE_THI_THAM_KHAO', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 3, NOW()),
('Toán chuyên nâng cao', 'TAI_LIEU', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 2, NOW()),
('Lý nâng cao 12', 'TAI_LIEU', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 3, NOW());



INSERT INTO stickers (image_url) VALUES
('/stickers/sticker_001.png'),
('/stickers/sticker_002.png'),
('/stickers/sticker_003.png'),
('/stickers/sticker_004.png'),
('/stickers/sticker_005.png'),
('/stickers/sticker_006.png'),
('/stickers/sticker_007.png'),
('/stickers/sticker_008.png'),
('/stickers/sticker_009.png'),
('/stickers/sticker_010.png'),
('/stickers/sticker_011.png'),
('/stickers/sticker_012.png'),
('/stickers/sticker_013.png'),
('/stickers/sticker_014.png'),
('/stickers/sticker_015.png'),
('/stickers/sticker_016.png'),
('/stickers/sticker_017.png'),
('/stickers/sticker_018.png'),
('/stickers/sticker_019.png'),
('/stickers/sticker_020.png'),
('/stickers/sticker_021.png'),
('/stickers/sticker_022.png'),
('/stickers/sticker_023.png'),
('/stickers/sticker_024.png'),
('/stickers/sticker_025.png'),
('/stickers/sticker_026.png'),
('/stickers/sticker_027.png'),
('/stickers/sticker_028.png'),
('/stickers/sticker_029.png'),
('/stickers/sticker_030.png'),
('/stickers/sticker_031.png'),
('/stickers/sticker_032.png'),
('/stickers/sticker_033.png'),
('/stickers/sticker_034.png'),
('/stickers/sticker_035.png'),
('/stickers/sticker_036.png'),
('/stickers/sticker_037.png'),
('/stickers/sticker_038.png'),
('/stickers/sticker_039.png'),
('/stickers/sticker_040.png'),
('/stickers/sticker_041.png'),
('/stickers/sticker_042.png'),
('/stickers/sticker_043.png'),
('/stickers/sticker_044.png'),
('/stickers/sticker_045.png'),
('/stickers/sticker_046.png'),
('/stickers/sticker_047.png'),
('/stickers/sticker_048.png'),
('/stickers/sticker_049.png'),
('/stickers/sticker_050.png'),
('/stickers/sticker_051.png'),
('/stickers/sticker_052.png'),
('/stickers/sticker_053.png'),
('/stickers/sticker_054.png'),
('/stickers/sticker_055.png'),
('/stickers/sticker_056.png'),
('/stickers/sticker_057.png'),
('/stickers/sticker_058.png'),
('/stickers/sticker_059.png'),
('/stickers/sticker_060.png'),
('/stickers/sticker_061.png'),
('/stickers/sticker_062.png'),
('/stickers/sticker_063.png'),
('/stickers/sticker_064.png'),
('/stickers/sticker_065.png'),
('/stickers/sticker_066.png'),
('/stickers/sticker_067.png'),
('/stickers/sticker_068.png'),
('/stickers/sticker_069.png'),
('/stickers/sticker_070.png'),
('/stickers/sticker_071.png'),
('/stickers/sticker_072.png'),
('/stickers/sticker_073.png'),
('/stickers/sticker_074.png'),
('/stickers/sticker_075.png'),
('/stickers/sticker_076.png'),
('/stickers/sticker_077.png'),
('/stickers/sticker_078.png'),
('/stickers/sticker_079.png'),
('/stickers/sticker_080.png'),
('/stickers/sticker_081.png'),
('/stickers/sticker_082.png'),
('/stickers/sticker_083.png'),
('/stickers/sticker_084.png'),
('/stickers/sticker_085.png'),
('/stickers/sticker_086.png'),
('/stickers/sticker_087.png'),
('/stickers/sticker_088.png'),
('/stickers/sticker_089.png'),
('/stickers/sticker_090.png'),
('/stickers/sticker_091.png'),
('/stickers/sticker_092.png'),
('/stickers/sticker_093.png'),
('/stickers/sticker_094.png'),
('/stickers/sticker_095.png'),
('/stickers/sticker_096.png'),
('/stickers/sticker_097.png');



INSERT INTO chat_messages (sender_id, receiver_id, sticker_id, content, is_read)
VALUES
-- Cuộc trò chuyện giữa Tutor A (2) và Parent A (5)
(5, 2, NULL, 'Chào thầy, em muốn học Toán lớp 8.', FALSE),
(2, 5, NULL, 'Chào em, thầy có thể dạy vào buổi tối nhé.', TRUE),
(5, 2, 1, NULL, TRUE),  -- gửi sticker cười
(2, 5, 3, NULL, TRUE),  -- sticker “ok”

-- Cuộc trò chuyện giữa Tutor B (3) và Parent B (6)
(6, 3, NULL, 'Thầy có thể dạy Lý lớp 12 không?', FALSE),
(3, 6, NULL, 'Có em nhé, tối thứ 3 và thứ 5 được không?', TRUE),
(6, 3, NULL, 'Dạ được thầy ạ.', TRUE),
(3, 6, 2, NULL, TRUE),  -- sticker gật đầu

-- Cuộc trò chuyện giữa Tutor C (4) và Parent C (7)
(7, 4, NULL, 'Em muốn học tiếng Anh giao tiếp, thầy có dạy không?', FALSE),
(4, 7, NULL, 'Có nhé, thầy dạy theo giáo trình thực hành.', TRUE),
(7, 4, 4, NULL, TRUE),  -- sticker cảm ơn
(4, 7, 5, NULL, TRUE),  -- sticker “thumbs up”
(5, 2,NULL, 'Thầy ơi cho em hỏi lịch học Lý ạ?', FALSE),
(2, 5,NULL, 'Chúng ta sẽ học thứ 4 và thứ 6 nhé.', TRUE),
(4, 1,NULL, 'Cô ơi em muốn đổi giờ học được không?', FALSE),
(1, 4,NULL, 'Được em nhé, cô sẽ cập nhật lại.', TRUE);





-- === 1. Thêm 20 users (10 tutor, 10 learner) ===
INSERT INTO users (user_id, full_name, email, password_hash, role, phone_number, status, created_at, updated_at, avatar_image)
VALUES
  (12, 'Nguyễn Gia Tutor 1', 'tutor6@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0906000001', 'ACTIVE', '2025-12-01 09:00:00', '2025-12-01 09:15:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (13, 'Trần Minh Tutor 2', 'tutor7@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0907000002', 'ACTIVE', '2025-12-02 09:00:00', '2025-12-02 09:15:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (14, 'Lê Hoài Tutor 3', 'tutor8@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0908000003', 'ACTIVE', '2025-12-03 09:00:00', '2025-12-03 09:15:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (15, 'Phạm Hải Tutor 4', 'tutor9@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0909000004', 'ACTIVE', '2025-12-04 09:00:00', '2025-12-04 09:15:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (16, 'Võ Thái Tutor 5', 'tutor10@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0901000005', 'ACTIVE', '2025-12-05 09:00:00', '2025-12-05 09:15:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (17, 'Đỗ Quang Tutor 6', 'tutor11@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0901100006', 'ACTIVE', '2025-12-06 09:00:00', '2025-12-06 09:15:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (18, 'Hoàng Yến Tutor 7', 'tutor12@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0901200007', 'ACTIVE', '2025-12-07 09:00:00', '2025-12-07 09:15:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (19, 'Bùi Phúc Tutor 8', 'tutor13@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0901300008', 'ACTIVE', '2025-12-08 09:00:00', '2025-12-08 09:15:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (20, 'Đinh Trang Tutor 9', 'tutor14@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0901400009', 'ACTIVE', '2025-12-09 09:00:00', '2025-12-09 09:15:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (21, 'Cao Nhã Tutor 10', 'tutor15@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0901500010', 'ACTIVE', '2025-12-10 09:00:00', '2025-12-10 09:15:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (22, 'Lê Học Learner 1', 'learner6@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0916000001', 'ACTIVE', '2025-12-06 10:00:00', '2025-12-06 10:10:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (23, 'Nguyễn Học Learner 2', 'learner7@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0917000002', 'ACTIVE', '2025-12-07 10:00:00', '2025-12-07 10:10:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (24, 'Trần Học Learner 3', 'learner8@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0918000003', 'ACTIVE', '2025-12-08 10:00:00', '2025-12-08 10:10:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (25, 'Phạm Học Learner 4', 'learner9@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0919000004', 'ACTIVE', '2025-12-09 10:00:00', '2025-12-09 10:10:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (26, 'Huỳnh Học Learner 5', 'learner10@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0911000005', 'ACTIVE', '2025-12-10 10:00:00', '2025-12-10 10:10:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (27, 'Vũ Học Learner 6', 'learner11@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0911100006', 'ACTIVE', '2025-12-11 10:00:00', '2025-12-11 10:10:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (28, 'Đoàn Học Learner 7', 'learner12@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0911200007', 'ACTIVE', '2025-12-12 10:00:00', '2025-12-12 10:10:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (29, 'Lâm Học Learner 8', 'learner13@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0911300008', 'ACTIVE', '2025-12-13 10:00:00', '2025-12-13 10:10:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (30, 'Đặng Học Learner 9', 'learner14@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0911400009', 'ACTIVE', '2025-12-14 10:00:00', '2025-12-14 10:10:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp"),
  (31, 'Ngô Học Learner 10', 'learner15@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0911500010', 'ACTIVE', '2025-12-15 10:00:00', '2025-12-15 10:10:00', "https://drive.google.com/uc?id=1vPbK0Bk8_TdF4Us0Bo7-PVofUk3SVRrp");

-- === 2. Thêm 10 tutors mới (tutor_id 6–15) ===
INSERT INTO tutors (tutor_id, address, educational_level, gender, introduction, price_per_hour, university, verification_status, user_id)
VALUES
  (6, 'TP.HCM', 'Đại học', 'FEMALE', 'Kinh nghiệm 2 năm dạy Toán THCS', 150000,  'ĐH Sư phạm TP.HCM', 'APPROVED', 12),
  (7, 'Hà Nội', 'Cao học', 'MALE', 'Gia sư Lý, luyện thi vào 10', 170000, 'ĐH Sư phạm Hà Nội', 'APPROVED', 13),
  (8, 'Đà Nẵng', 'Đại học', 'FEMALE', 'Dạy Hóa cơ bản và nâng cao', 160000, 'ĐH Bách Khoa Đà Nẵng', 'APPROVED', 14),
  (9, 'Cần Thơ', 'Đại học', 'MALE', 'Dạy tiếng Anh giao tiếp cho sinh viên', 200000, 'ĐH Cần Thơ', 'APPROVED', 15),
  (10, 'Huế', 'Đại học', 'FEMALE', 'Gia sư Ngữ văn, luyện thi THPT', 155000, 'ĐH Sư phạm Huế', 'APPROVED', 16),
  (11, 'Hải Phòng', 'Đại học', 'MALE', 'Dạy Tin học cơ bản và nâng cao', 180000, 'ĐH Hàng Hải', 'APPROVED', 17),
  (12, 'Biên Hòa', 'Đại học', 'FEMALE', 'Gia sư Toán cấp 2 và 3', 190000, 'ĐH Lạc Hồng', 'APPROVED', 18),
  (13, 'Nha Trang', 'Đại học', 'MALE', 'Dạy Sinh học, kinh nghiệm 4 năm', 175000, 'ĐH Nha Trang', 'APPROVED', 19),
  (14, 'Vũng Tàu', 'Đại học', 'FEMALE', 'Dạy tiếng Anh giao tiếp và IELTS', 210000, 'ĐH Bà Rịa – Vũng Tàu', 'APPROVED', 20),
  (15, 'Buôn Ma Thuột', 'Cao học', 'MALE', 'Dạy Lý và Hóa cho học sinh THPT', 185000, 'ĐH Tây Nguyên', 'APPROVED', 21);

-- === 3. Thêm 10 learners mới (learner_id 6–15) ===
INSERT INTO learner (learner_id, address, address_detail, description, full_name, gender, grade, school, user_id)
VALUES
  (6, 'Hà Nội', 'Số 1 Trần Duy Hưng', 'Cần học Toán và Lý cơ bản', 'Learner 6', 'MALE', 'Lớp 7', 'THCS Nguyễn Trãi', 22),
  (7, 'TP.HCM', '25 Lê Lợi', 'Cần cải thiện Tiếng Anh giao tiếp', 'Learner 7', 'FEMALE', 'Lớp 8', 'THCS Lê Quý Đôn', 23),
  (8, 'Đà Nẵng', '10 Phan Chu Trinh', 'Ôn thi vào 10 môn Toán', 'Learner 8', 'MALE', 'Lớp 9', 'THCS Hòa Khánh', 24),
  (9, 'Huế', '5 Nguyễn Huệ', 'Cần học thêm Hóa học', 'Learner 9', 'FEMALE', 'Lớp 10', 'THPT Quốc Học', 25),
  (10, 'Cần Thơ', '20 3/2', 'Luyện thi đại học khối A', 'Learner 10', 'MALE', 'Lớp 11', 'THPT Châu Văn Liêm', 26),
  (11, 'Hải Phòng', '15 Trần Phú', 'Cần củng cố kiến thức cơ bản', 'Learner 11', 'FEMALE', 'Lớp 6', 'THCS Ngô Quyền', 27),
  (12, 'Nha Trang', '8 Trần Hưng Đạo', 'Luyện thi IELTS 6.5', 'Learner 12', 'MALE', 'Lớp 12', 'THPT Lê Quý Đôn', 28),
  (13, 'Biên Hòa', '12 Đồng Khởi', 'Cần học tốt Tiếng Việt và Toán', 'Learner 13', 'FEMALE', 'Lớp 5', 'Tiểu học Tân Mai', 29),
  (14, 'Vũng Tàu', '7 Lý Thường Kiệt', 'Cần làm quen với Tiếng Anh', 'Learner 14', 'MALE', 'Lớp 4', 'Tiểu học Lê Lợi', 30),
  (15, 'Buôn Ma Thuột', '18 Hai Bà Trưng', 'Cần luyện viết chữ đẹp', 'Learner 15', 'FEMALE', 'Lớp 3', 'Tiểu học Nguyễn Du', 31);

-- === 4. Môn dạy cho 10 tutors mới ===
INSERT INTO tutor_subjects (tutor_id, subject_id)
VALUES
  (6, 1),
  (6, 9),
  (7, 2),
  (7, 18),
  (8, 3),
  (8, 4),
  (9, 9),
  (9, 27),
  (10, 1),
  (10, 18),
  (10, 28),
  (11, 2),
  (11, 3),
  (12, 4),
  (12, 9),
  (13, 1),
  (13, 27),
  (14, 28),
  (14, 29),
  (15, 18),
  (15, 9);

-- === 5. Chứng chỉ cho 10 tutors mới ===
INSERT INTO tutor_certificates (certificate_id, tutor_id, certificate_name, approved)
VALUES
(100, 6, 'Chứng chỉ sư phạm cơ bản 6', TRUE),
(101, 6, 'Chứng chỉ chuyên môn nâng cao 6', TRUE),
(102, 7, 'Chứng chỉ sư phạm cơ bản 7', TRUE),
(103, 7, 'Chứng chỉ chuyên môn nâng cao 7', TRUE),
(104, 8, 'Chứng chỉ sư phạm cơ bản 8', TRUE),
(105, 8, 'Chứng chỉ chuyên môn nâng cao 8', TRUE),
(106, 9, 'Chứng chỉ sư phạm cơ bản 9', TRUE),
(107, 9, 'Chứng chỉ chuyên môn nâng cao 9', TRUE),
(108, 10, 'Chứng chỉ sư phạm cơ bản 10', TRUE),
(109, 10, 'Chứng chỉ chuyên môn nâng cao 10', TRUE),
(110, 11, 'Chứng chỉ sư phạm cơ bản 11', TRUE),
(111, 11, 'Chứng chỉ chuyên môn nâng cao 11', TRUE),
(112, 12, 'Chứng chỉ sư phạm cơ bản 12', TRUE),
(113, 12, 'Chứng chỉ chuyên môn nâng cao 12', TRUE),
(114, 13, 'Chứng chỉ sư phạm cơ bản 13', TRUE),
(115, 13, 'Chứng chỉ chuyên môn nâng cao 13', TRUE),
(116, 14, 'Chứng chỉ sư phạm cơ bản 14', TRUE),
(117, 14, 'Chứng chỉ chuyên môn nâng cao 14', TRUE),
(118, 15, 'Chứng chỉ sư phạm cơ bản 15', TRUE),
(119, 15, 'Chứng chỉ chuyên môn nâng cao 15', TRUE);

INSERT INTO tutor_certificate_files (file_id, certificate_id, file_url, status, is_active, uploaded_at)
VALUES
(200, 100, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(201, 101, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(202, 102, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(203, 103, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(204, 104, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(205, 105, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(206, 106, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(207, 107, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),

(208, 108, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(209, 109, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),

(210, 110, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(212, 112, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(213, 113, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),

(214, 114, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(215, 115, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),

(216, 116, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(217, 117, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),

(218, 118, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW()),
(219, 119, 'https://drive.google.com/uc?id=1aeQacmvYQMq3cEYj0f4mZoeYUN7mkoXuRhZzXmev3u0', 'APPROVED', TRUE, NOW());



-- === 6. Lịch trống chi tiết (tutor_availability) cho 10 tutors mới ===
INSERT INTO tutor_availability (tutor_id, created_at, start_time, end_time, status, updated_at)
VALUES
  (6, '2025-12-01 08:00:00', '2025-12-01 17:00:00', '2025-12-01 18:30:00', 'AVAILABLE', '2025-12-01 08:05:00'),
  (6, '2025-12-02 08:00:00', '2025-12-02 18:00:00', '2025-12-02 19:30:00', 'BOOKED', '2025-12-02 08:05:00'),
  (6, '2025-12-03 08:00:00', '2025-12-03 19:00:00', '2025-12-03 20:30:00', 'AVAILABLE', '2025-12-03 08:05:00'),
  (7, '2025-12-03 08:00:00', '2025-12-03 17:00:00', '2025-12-03 18:30:00', 'AVAILABLE', '2025-12-03 08:05:00'),
  (7, '2025-12-04 08:00:00', '2025-12-04 18:00:00', '2025-12-04 19:30:00', 'BOOKED', '2025-12-04 08:05:00'),
  (7, '2025-12-05 08:00:00', '2025-12-05 19:00:00', '2025-12-05 20:30:00', 'AVAILABLE', '2025-12-05 08:05:00'),
  (8, '2025-12-05 08:00:00', '2025-12-05 17:00:00', '2025-12-05 18:30:00', 'AVAILABLE', '2025-12-05 08:05:00'),
  (8, '2025-12-06 08:00:00', '2025-12-06 18:00:00', '2025-12-06 19:30:00', 'BOOKED', '2025-12-06 08:05:00'),
  (8, '2025-12-07 08:00:00', '2025-12-07 19:00:00', '2025-12-07 20:30:00', 'AVAILABLE', '2025-12-07 08:05:00'),
  (9, '2025-12-07 08:00:00', '2025-12-07 17:00:00', '2025-12-07 18:30:00', 'AVAILABLE', '2025-12-07 08:05:00'),
  (9, '2025-12-08 08:00:00', '2025-12-08 18:00:00', '2025-12-08 19:30:00', 'BOOKED', '2025-12-08 08:05:00'),
  (9, '2025-12-09 08:00:00', '2025-12-09 19:00:00', '2025-12-09 20:30:00', 'AVAILABLE', '2025-12-09 08:05:00'),
  (10, '2025-12-09 08:00:00', '2025-12-09 17:00:00', '2025-12-09 18:30:00', 'AVAILABLE', '2025-12-09 08:05:00'),
  (10, '2025-12-10 08:00:00', '2025-12-10 18:00:00', '2025-12-10 19:30:00', 'BOOKED', '2025-12-10 08:05:00'),
  (10, '2025-12-11 08:00:00', '2025-12-11 19:00:00', '2025-12-11 20:30:00', 'AVAILABLE', '2025-12-11 08:05:00'),
  (11, '2025-12-11 08:00:00', '2025-12-11 17:00:00', '2025-12-11 18:30:00', 'AVAILABLE', '2025-12-11 08:05:00'),
  (11, '2025-12-12 08:00:00', '2025-12-12 18:00:00', '2025-12-12 19:30:00', 'BOOKED', '2025-12-12 08:05:00'),
  (11, '2025-12-13 08:00:00', '2025-12-13 19:00:00', '2025-12-13 20:30:00', 'AVAILABLE', '2025-12-13 08:05:00'),
  (12, '2025-12-13 08:00:00', '2025-12-13 17:00:00', '2025-12-13 18:30:00', 'AVAILABLE', '2025-12-13 08:05:00'),
  (12, '2025-12-14 08:00:00', '2025-12-14 18:00:00', '2025-12-14 19:30:00', 'BOOKED', '2025-12-14 08:05:00'),
  (12, '2025-12-15 08:00:00', '2025-12-15 19:00:00', '2025-12-15 20:30:00', 'AVAILABLE', '2025-12-15 08:05:00'),
  (13, '2025-12-15 08:00:00', '2025-12-15 17:00:00', '2025-12-15 18:30:00', 'AVAILABLE', '2025-12-15 08:05:00'),
  (13, '2025-12-16 08:00:00', '2025-12-16 18:00:00', '2025-12-16 19:30:00', 'BOOKED', '2025-12-16 08:05:00'),
  (13, '2025-12-17 08:00:00', '2025-12-17 19:00:00', '2025-12-17 20:30:00', 'AVAILABLE', '2025-12-17 08:05:00'),
  (14, '2025-12-17 08:00:00', '2025-12-17 17:00:00', '2025-12-17 18:30:00', 'AVAILABLE', '2025-12-17 08:05:00'),
  (14, '2025-12-18 08:00:00', '2025-12-18 18:00:00', '2025-12-18 19:30:00', 'BOOKED', '2025-12-18 08:05:00'),
  (14, '2025-12-19 08:00:00', '2025-12-19 19:00:00', '2025-12-19 20:30:00', 'AVAILABLE', '2025-12-19 08:05:00'),
  (15, '2025-12-19 08:00:00', '2025-12-19 17:00:00', '2025-12-19 18:30:00', 'AVAILABLE', '2025-12-19 08:05:00'),
  (15, '2025-12-20 08:00:00', '2025-12-20 18:00:00', '2025-12-20 19:30:00', 'BOOKED', '2025-12-20 08:05:00'),
  (15, '2025-12-01 08:00:00', '2025-12-01 19:00:00', '2025-12-01 20:30:00', 'AVAILABLE', '2025-12-01 08:05:00');

-- === 7. Mẫu lịch lặp lại (tutor_recurring_pattern) cho một số tutors mới ===
INSERT INTO tutor_recurring_pattern (tutor_id, repeat_type, days_of_week, days_of_month, start_time, end_time, repeat_start, repeat_end)
VALUES
  (6, 'WEEKLY', '["Monday","Wednesday"]', NULL, '18:00:00', '20:00:00', '2025-12-01', '2025-12-20'),
  (7, 'WEEKLY', '["Tuesday","Thursday"]', NULL, '19:00:00', '21:00:00', '2025-12-02', '2025-12-19'),
  (8, 'MONTHLY', NULL, '[5,10,15]', '17:00:00', '19:00:00', '2025-12-01', '2025-12-20'),
  (9, 'WEEKLY', '["Friday"]', NULL, '08:00:00', '10:00:00', '2025-12-05', '2025-12-19'),
  (10, 'MONTHLY', NULL, '[3,13]', '09:00:00', '11:00:00', '2025-12-03', '2025-12-20');

-- === 8. Yêu cầu lớp học (class_requests) giữa learners & tutors mới ===
INSERT INTO class_requests (request_id, additional_notes, created_at, end_date, sessions_per_week, start_date, status, total_sessions, type, updated_at, learner_id, subject, tutor_id)
VALUES
  (100, 'Yêu cầu học thêm môn với request 100', '2025-12-02 07:30:00', '2025-12-12', 2, '2025-12-02', 'CONFIRMED', 6, 'OFFICIAL', '2025-12-02 07:45:00', 6, 1, 6),
  (101, 'Yêu cầu học thêm môn với request 101', '2025-12-03 07:30:00', '2025-12-13', 3, '2025-12-03', 'PENDING', 7, 'TRIAL', '2025-12-03 07:45:00', 7, 2, 7),
  (102, 'Yêu cầu học thêm môn với request 102', '2025-12-04 07:30:00', '2025-12-14', 4, '2025-12-04', 'CONFIRMED', 8, 'OFFICIAL', '2025-12-04 07:45:00', 8, 3, 8),
  (103, 'Yêu cầu học thêm môn với request 103', '2025-12-05 07:30:00', '2025-12-15', 2, '2025-12-05', 'PENDING', 9, 'TRIAL', '2025-12-05 07:45:00', 9, 4, 9),
  (104, 'Yêu cầu học thêm môn với request 104', '2025-12-06 07:30:00', '2025-12-16', 3, '2025-12-06', 'CONFIRMED', 10, 'OFFICIAL', '2025-12-06 07:45:00', 10, 9, 10),
  (105, 'Yêu cầu học thêm môn với request 105', '2025-12-07 07:30:00', '2025-12-17', 4, '2025-12-07', 'PENDING', 11, 'TRIAL', '2025-12-07 07:45:00', 11, 18, 11),
  (106, 'Yêu cầu học thêm môn với request 106', '2025-12-08 07:30:00', '2025-12-18', 2, '2025-12-08', 'CONFIRMED', 12, 'OFFICIAL', '2025-12-08 07:45:00', 12, 27, 12),
  (107, 'Yêu cầu học thêm môn với request 107', '2025-12-09 07:30:00', '2025-12-19', 3, '2025-12-09', 'PENDING', 13, 'TRIAL', '2025-12-09 07:45:00', 13, 28, 13),
  (108, 'Yêu cầu học thêm môn với request 108', '2025-12-10 07:30:00', '2025-12-20', 4, '2025-12-10', 'CONFIRMED', 14, 'OFFICIAL', '2025-12-10 07:45:00', 14, 29, 14),
  (109, 'Yêu cầu học thêm môn với request 109', '2025-12-11 07:30:00', '2025-12-20', 2, '2025-12-11', 'PENDING', 15, 'TRIAL', '2025-12-11 07:45:00', 15, 1, 15);

-- === 9. Thời khóa biểu lớp (calendar_class) cho các request mới ===
INSERT INTO calendar_class (calendar_class_id, created_at, day_of_week, end_time, start_time, updated_at, request_id)
VALUES
  (1000, '2025-12-01 06:00:00', 'MONDAY', '17:00:00', '18:30:00', '2025-12-01 06:10:00', 100),
  (1001, '2025-12-02 06:00:00', 'TUESDAY', '18:00:00', '19:30:00', '2025-12-02 06:10:00', 100),
  (1002, '2025-12-02 06:00:00', 'TUESDAY', '17:00:00', '18:30:00', '2025-12-02 06:10:00', 101),
  (1003, '2025-12-03 06:00:00', 'WEDNESDAY', '18:00:00', '19:30:00', '2025-12-03 06:10:00', 101),
  (1004, '2025-12-03 06:00:00', 'WEDNESDAY', '17:00:00', '18:30:00', '2025-12-03 06:10:00', 102),
  (1005, '2025-12-04 06:00:00', 'THURSDAY', '18:00:00', '19:30:00', '2025-12-04 06:10:00', 102),
  (1006, '2025-12-04 06:00:00', 'THURSDAY', '17:00:00', '18:30:00', '2025-12-04 06:10:00', 103),
  (1007, '2025-12-05 06:00:00', 'FRIDAY', '18:00:00', '19:30:00', '2025-12-05 06:10:00', 103),
  (1008, '2025-12-05 06:00:00', 'FRIDAY', '17:00:00', '18:30:00', '2025-12-05 06:10:00', 104),
  (1009, '2025-12-06 06:00:00', 'SATURDAY', '18:00:00', '19:30:00', '2025-12-06 06:10:00', 104),
  (1010, '2025-12-06 06:00:00', 'SATURDAY', '17:00:00', '18:30:00', '2025-12-06 06:10:00', 105),
  (1011, '2025-12-07 06:00:00', 'SUNDAY', '18:00:00', '19:30:00', '2025-12-07 06:10:00', 105),
  (1012, '2025-12-07 06:00:00', 'SUNDAY', '17:00:00', '18:30:00', '2025-12-07 06:10:00', 106),
  (1013, '2025-12-08 06:00:00', 'MONDAY', '18:00:00', '19:30:00', '2025-12-08 06:10:00', 106),
  (1014, '2025-12-08 06:00:00', 'MONDAY', '17:00:00', '18:30:00', '2025-12-08 06:10:00', 107),
  (1015, '2025-12-09 06:00:00', 'TUESDAY', '18:00:00', '19:30:00', '2025-12-09 06:10:00', 107),
  (1016, '2025-12-09 06:00:00', 'TUESDAY', '17:00:00', '18:30:00', '2025-12-09 06:10:00', 108),
  (1017, '2025-12-10 06:00:00', 'WEDNESDAY', '18:00:00', '19:30:00', '2025-12-10 06:10:00', 108),
  (1018, '2025-12-10 06:00:00', 'WEDNESDAY', '17:00:00', '18:30:00', '2025-12-10 06:10:00', 109),
  (1019, '2025-12-11 06:00:00', 'THURSDAY', '18:00:00', '19:30:00', '2025-12-11 06:10:00', 109);

-- === 10. Bản ghi classes cho các request mới ===
INSERT INTO classes (class_id, completed_sessions, status, request_id)
VALUES
  (100, 1, 'ONGOING', 100),
  (101, 0, 'PENDING', 101),
  (102, 3, 'COMPLETED', 102),
  (103, 0, 'CANCELLED', 103),
  (104, 5, 'ONGOING', 104),
  (105, 0, 'PENDING', 105),
  (106, 7, 'COMPLETED', 106),
  (107, 0, 'CANCELLED', 107),
  (108, 9, 'ONGOING', 108),
  (109, 0, 'PENDING', 109);

-- === 11. Đánh giá (ratings) cho một số lớp mới ===
INSERT INTO ratings (rating_id, comment, created_at, score, class_id)
VALUES
  (100, 'Gia sư dạy dễ hiểu, đúng giờ.', '2025-12-06 20:30:00', 4.5, 100),
  (101, 'Buổi học diễn ra tốt, học viên tiến bộ.', '2025-12-07 20:30:00', 4.8, 101),
  (102, 'Cần thêm bài tập về nhà, nhưng nhìn chung ổn.', '2025-12-08 20:30:00', 4.0, 102),
  (103, 'Rất nhiệt tình và hỗ trợ ngoài giờ.', '2025-12-09 20:30:00', 5.0, 103),
  (104, 'Bài giảng sinh động, nhiều ví dụ thực tế.', '2025-12-10 20:30:00', 4.7, 104);

-- === 12. Thông báo (notifications) cho 20 users mới ===
INSERT INTO notifications (notification_id, content, created_at, is_read, title, type, user_id)
VALUES
  (1000, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 12.', '2025-12-04 12:00:00', 0, 'Thông báo cho user 12', 'SYSTEM', 12),
  (1001, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 13.', '2025-12-05 12:00:00', 1, 'Thông báo cho user 13', 'CLASS_UPDATE', 13),
  (1002, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 14.', '2025-12-06 12:00:00', 0, 'Thông báo cho user 14', 'MESSAGE', 14),
  (1003, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 15.', '2025-12-07 12:00:00', 1, 'Thông báo cho user 15', 'RATING', 15),
  (1004, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 16.', '2025-12-08 12:00:00', 0, 'Thông báo cho user 16', 'REMINDER', 16),
  (1005, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 17.', '2025-12-09 12:00:00', 1, 'Thông báo cho user 17', 'SYSTEM', 17),
  (1006, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 18.', '2025-12-10 12:00:00', 0, 'Thông báo cho user 18', 'CLASS_UPDATE', 18),
  (1007, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 19.', '2025-12-11 12:00:00', 1, 'Thông báo cho user 19', 'MESSAGE', 19),
  (1008, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 20.', '2025-12-12 12:00:00', 0, 'Thông báo cho user 20', 'RATING', 20),
  (1009, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 21.', '2025-12-13 12:00:00', 1, 'Thông báo cho user 21', 'REMINDER', 21),
  (1010, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 22.', '2025-12-05 12:00:00', 0, 'Thông báo cho user 22', 'SYSTEM', 22),
  (1011, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 23.', '2025-12-06 12:00:00', 1, 'Thông báo cho user 23', 'CLASS_UPDATE', 23),
  (1012, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 24.', '2025-12-07 12:00:00', 0, 'Thông báo cho user 24', 'MESSAGE', 24),
  (1013, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 25.', '2025-12-08 12:00:00', 1, 'Thông báo cho user 25', 'RATING', 25),
  (1014, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 26.', '2025-12-09 12:00:00', 0, 'Thông báo cho user 26', 'REMINDER', 26),
  (1015, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 27.', '2025-12-10 12:00:00', 1, 'Thông báo cho user 27', 'SYSTEM', 27),
  (1016, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 28.', '2025-12-11 12:00:00', 0, 'Thông báo cho user 28', 'CLASS_UPDATE', 28),
  (1017, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 29.', '2025-12-12 12:00:00', 1, 'Thông báo cho user 29', 'MESSAGE', 29),
  (1018, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 30.', '2025-12-13 12:00:00', 0, 'Thông báo cho user 30', 'RATING', 30),
  (1019, 'Đây là thông báo hệ thống liên quan đến tài khoản user_id = 31.', '2025-12-03 12:00:00', 1, 'Thông báo cho user 31', 'REMINDER', 31);

-- === 13. Tin nhắn (chat_messages) giữa tutors & learners mới ===
INSERT INTO chat_messages (message_id, content, is_read, sent_at, receiver_id, sender_id, sticker_id)
VALUES
  (1000, 'Chào thầy/cô, em muốn hỏi về lịch học ạ.', 0, '2025-12-03 19:00:00', 12, 22, NULL),
  (1001, NULL, 1, '2025-12-03 19:10:00', 22, 12, 1),
  (1002, 'Buổi tối thứ 3 cô còn trống không ạ?', 0, '2025-12-04 19:00:00', 13, 23, NULL),
  (1003, NULL, 1, '2025-12-04 19:10:00', 23, 13, 2),
  (1004, 'Thầy cho em thêm bài tập được không ạ?', 0, '2025-12-05 19:00:00', 14, 24, NULL),
  (1005, NULL, 1, '2025-12-05 19:10:00', 24, 14, 3),
  (1006, 'Em cảm ơn thầy vì buổi học hôm trước.', 0, '2025-12-06 19:00:00', 15, 25, NULL),
  (1007, NULL, 1, '2025-12-06 19:10:00', 25, 15, 4),
  (1008, 'Tuần này mình có thể học thêm 1 buổi không ạ?', 0, '2025-12-07 19:00:00', 16, 26, NULL),
  (1009, NULL, 1, '2025-12-07 19:10:00', 26, 16, 5),
  (1010, 'Em bận thi nên xin dời lịch ạ.', 0, '2025-12-08 19:00:00', 17, 27, NULL),
  (1011, NULL, 1, '2025-12-08 19:10:00', 27, 17, 1),
  (1012, 'Thầy/cô cho em xin tài liệu ôn tập với ạ.', 0, '2025-12-09 19:00:00', 18, 28, NULL),
  (1013, NULL, 1, '2025-12-09 19:10:00', 28, 18, 2),
  (1014, 'Bài kiểm tra của em được 9 điểm rồi ạ.', 0, '2025-12-10 19:00:00', 19, 29, NULL),
  (1015, NULL, 1, '2025-12-10 19:10:00', 29, 19, 3),
  (1016, 'Em muốn đăng ký thêm môn Tiếng Anh.', 0, '2025-12-11 19:00:00', 20, 30, NULL),
  (1017, NULL, 1, '2025-12-11 19:10:00', 30, 20, 4),
  (1018, 'Cô góp ý giúp em về phát âm được không ạ?', 0, '2025-12-12 19:00:00', 21, 31, NULL),
  (1019, NULL, 1, '2025-12-12 19:10:00', 31, 21, 5);

-- === 14. Ebooks mới do tutors mới upload ===
INSERT INTO ebooks (title, type, file_path, uploaded_by, created_at)
VALUES
  ('Chuyên đề Toán THCS nâng cao', 'TAI_LIEU', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 12, '2025-12-05 16:00:00'),
  ('Bài tập Vật lí luyện thi', 'TAI_LIEU', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 13, '2025-12-06 16:00:00'),
  ('Hóa học cơ bản cho THPT', 'SACH_GIAO_KHOA', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 14, '2025-12-07 16:00:00'),
  ('Tiếng Anh giao tiếp hàng ngày', 'TAI_LIEU', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 15, '2025-12-08 16:00:00'),
  ('Tài liệu luyện thi IELTS 6.5+', 'DE_THI_THAM_KHAO', 'https://drive.google.com/uc?id=1xSAlqzBTv7BQ4drOuCG56IxHWYyxCblg9yXs7eC8Pzo', 16, '2025-12-09 16:00:00');


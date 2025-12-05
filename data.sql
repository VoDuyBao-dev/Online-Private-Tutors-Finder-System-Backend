-- USE tutors_finder_system;

-- mật khẩu 12345678 
INSERT INTO users (full_name, email, password_hash, role, phone_number, status, updated_at)
VALUES
('Nguyễn Văn Admin', 'admin1@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ADMIN', '0901111222', 'Active', NOW()),
('Trần Thị B', 'tutor1@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0903333444', 'Active', NOW()),
('Lê Văn C', 'tutor2@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0905555666', 'Active', NOW()),
('Phạm Thị D', 'tutor3@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0907777888', 'inActive', NOW()),
('Ngô Minh E', 'parent1@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0911111222', 'Active', NOW()),
('Trần Anh F', 'parent2@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0913333444', 'Active', NOW()),
('Hoàng Văn G', 'parent3@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0915555666', 'Active', NOW()),
('Vũ Thị H', 'tutor4@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0908888999','Active', NOW()),
('Nguyễn Minh I', 'tutor5@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'TUTOR', '0902222333', 'Active', NOW()),
('Lâm Thị K', 'parent4@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0917777888', 'Active', NOW()),
('Lâm Văn H', 'parent5@example.com', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'LEARNER', '0917777999', 'Active', NOW());


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



INSERT INTO tutors (user_id, gender, address, university, proof_file_url, educational_level, introduction, price_per_hour, verification_status)
VALUES
(2, 'FEMALE', 'Hà Nội', 'ĐH Sư phạm Hà Nội', 'proof1.pdf', 'Đại học', 'Kinh nghiệm 3 năm dạy Toán', 150000, 'APPROVED'),
(3, 'MALE', 'TP.HCM', 'ĐH Bách Khoa', 'proof2.pdf', 'Đại học', 'Dạy Lý chuyên cấp 3', 180000, 'APPROVED'),
(4, 'FEMALE', 'Đà Nẵng', 'ĐH Sư phạm Đà Nẵng', 'proof3.pdf', 'Đại học', 'Dạy Hóa cơ bản', 160000, 'PENDING'),
(8, 'FEMALE', 'Cần Thơ', 'ĐH Cần Thơ', 'proof4.pdf', 'Cao học', 'Dạy tiếng Anh giao tiếp', 200000, 'APPROVED'),
(9, 'MALE', 'Huế', 'ĐH Khoa học Huế', 'proof5.pdf', 'Đại học', 'Gia sư luyện thi TOEIC', 220000, 'APPROVED');


INSERT INTO tutor_certificates (tutor_id, certificate) VALUES
-- Tutor 1: Trần Thị B
(1, 'Chứng chỉ Sư phạm Toán học'),
(1, 'IELTS 6.5 Academic'),
(1, 'Giấy chứng nhận “Dạy kèm Toán THCS nâng cao”'),

-- Tutor 2: Lê Văn C
(2, 'Chứng chỉ Sư phạm Vật Lý'),
(2, 'Giải Nhì Học sinh giỏi Vật Lý cấp tỉnh'),
(2, 'Chứng chỉ Bồi dưỡng phương pháp dạy học STEM'),

-- Tutor 3: Phạm Thị D
(3, 'IELTS 7.5 Academic'),
(3, 'TESOL Certificate - Teaching English to Speakers of Other Languages'),
(3, 'Chứng chỉ Sư phạm tiếng Anh'),

-- Tutor 4: Vũ Thị H
(4, 'Chứng chỉ Sư phạm Hóa học'),
(4, 'Chứng chỉ Ứng dụng CNTT cơ bản'),
(4, 'Giấy chứng nhận Dạy kèm Hóa THPT nâng cao'),

-- Tutor 5: Nguyễn Minh I
(5, 'MOS Microsoft Office Specialist'),
(5, 'Chứng chỉ Ứng dụng CNTT nâng cao'),
(5, 'Chứng chỉ Lập trình cơ bản – Bộ GD&ĐT');

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
('Toán nâng cao lớp 7', 'SACH_GIAO_KHOA', 'ebooks/toan7.pdf', 2, NOW()),
('Vật lý cơ bản', 'TAI_LIEU', 'ebooks/ly10.pdf', 2, NOW()),
('Hóa học thực hành', 'TAI_LIEU', 'ebooks/hoa.pdf', 2, NOW()),
('Tiếng Anh giao tiếp', 'TAI_LIEU', 'ebooks/english.pdf', 2, NOW()),
('Ôn thi IELTS 7.0', 'DE_THI_THAM_KHAO', 'ebooks/ielts.pdf', 2, NOW()),
('Luyện thi TOEIC 700+', 'DE_THI_THAM_KHAO', 'ebooks/toeic.pdf', 2, NOW()),
('Tin học cơ bản', 'SACH_GIAO_KHOA', 'ebooks/tin.pdf', 2, NOW()),
('Kỹ năng học tập hiệu quả', 'TAI_LIEU', 'ebooks/skill.pdf', 2, NOW()),
('Bài tập Hóa nâng cao', 'TAI_LIEU', 'ebooks/hoa2.pdf', 2, NOW()),
('Bộ đề thi THPT Quốc gia', 'DE_THI_THAM_KHAO', 'ebooks/thpt.pdf', 3, NOW()),
('Toán chuyên nâng cao', 'TAI_LIEU', 'ebooks/toanchuyen.pdf', 2, NOW()),
('Lý nâng cao 12', 'TAI_LIEU', 'ebooks/ly12nangcao.pdf', 3, NOW());



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







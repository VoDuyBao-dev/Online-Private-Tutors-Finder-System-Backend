package com.example.tutorsFinderSystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

// Định nghĩa các mã lỗi và message của lỗi
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR), // exception chưa được đinhj
                                                                                            // nghĩa hoặc loại ex chưa
                                                                                            // bắt
    USER_EXISTED(1002, "user existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "user not existed", HttpStatus.NOT_FOUND),
    SAVE_USER_FAILED(1014, "save user failed", HttpStatus.INTERNAL_SERVER_ERROR),
    UPDATE_USER_FAILED(1015, "update user failed", HttpStatus.INTERNAL_SERVER_ERROR),
    // lỗi invalid password
    // PASSWORD_INVALID(1003, "password must at least 8 charaters"),
    // USERNAME_INVALID(1004, "username must at least 2 charaters"),
    // Xử lí nhập vào sai key Enum
    INVALID_KEY(1001, "invalid message key", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1017, "you do not have permission", HttpStatus.FORBIDDEN),

    INVALID_OTP(1008, "invalid otp", HttpStatus.BAD_REQUEST),
    EMAIL_SEND_FAILED(1009, "email send failed", HttpStatus.INTERNAL_SERVER_ERROR),
    OTP_SAVE_FAILED(1010, "otp save failed", HttpStatus.INTERNAL_SERVER_ERROR),
    OTP_EXPIRED(1011, "otp expired", HttpStatus.BAD_REQUEST),
    OTP_NOT_FOUND(1016, "otp not found for specified email", HttpStatus.NOT_FOUND),

    // loi login
    INVALID_CREDENTIALS(1012, "invalid credentials", HttpStatus.UNAUTHORIZED),
    PASSWORDS_DO_NOT_MATCH(1013, "Password and confirm password do not match", HttpStatus.BAD_REQUEST),

    // Admin manage users
    // CANNOT_DELETE_ADMIN(1018, "cannot delete admin user", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND(1019, "user not found", HttpStatus.NOT_FOUND),
    INVALID_STATUS(1020, "invalid account status", HttpStatus.BAD_REQUEST),
    CANNOT_DELETE_ADMIN(1021, "cannot block admin user", HttpStatus.FORBIDDEN),
    EMAIL_ALREADY_EXISTS(1022, "email already exists", HttpStatus.BAD_REQUEST),
    USER_STATUS_UNCHANGED(1023, "user status is unchanged", HttpStatus.BAD_REQUEST),

    // TUTOR REGISTER VALIDATION
    // THÔNG TIN TÀI KHOẢN
    FULLNAME_REQUIRED(2001, "Full name is required", HttpStatus.BAD_REQUEST),
    FULLNAME_LENGTH_INVALID(2002, "Full name must be between 2 and 50 characters", HttpStatus.BAD_REQUEST),
    EMAIL_REQUIRED(2003, "Email is required", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(2004, "Invalid email format", HttpStatus.BAD_REQUEST),
    PASSWORD_REQUIRED(2005, "Password is required", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_SHORT(2006, "Password must be between 6 and 30 characters", HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_REQUIRED(2007, "Confirm password is required", HttpStatus.BAD_REQUEST),
    PHONE_REQUIRED(2008, "Phone number is required", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(2009, "Invalid Vietnamese phone number format", HttpStatus.BAD_REQUEST),
    // THÔNG TIN CÁ NHÂN & NGHỀ NGHIỆP
    GENDER_REQUIRED(2010, "Gender is required", HttpStatus.BAD_REQUEST),
    ADDRESS_REQUIRED(2011, "Address is required", HttpStatus.BAD_REQUEST),
    ADDRESS_TOO_LONG(2012, "Address must not exceed 255 characters", HttpStatus.BAD_REQUEST),
    UNIVERSITY_REQUIRED(2013, "University name is required", HttpStatus.BAD_REQUEST),
    UNIVERSITY_TOO_LONG(2014, "University name must not exceed 100 characters", HttpStatus.BAD_REQUEST),
    CERTIFICATES_REQUIRED(2015, "At least one certificate is required", HttpStatus.BAD_REQUEST),
    CERTIFICATE_NAME_REQUIRED(2016, "Certificate name cannot be blank", HttpStatus.BAD_REQUEST),
    INTRODUCTION_REQUIRED(2017, "Introduction is required", HttpStatus.BAD_REQUEST),
    PRICE_REQUIRED(2019, "Price per hour is required", HttpStatus.BAD_REQUEST),
    PRICE_TOO_LOW(2020, "Minimum price per hour is 50,000 VND", HttpStatus.BAD_REQUEST),
    PRICE_TOO_HIGH(2021, "Maximum price per hour is 1,000,000 VND", HttpStatus.BAD_REQUEST),
    // THÔNG TIN DẠY HỌC
    SUBJECTS_REQUIRED(2022, "At least one subject must be selected", HttpStatus.BAD_REQUEST),
    SUBJECT_ID_REQUIRED(2023, "Subject ID cannot be null", HttpStatus.BAD_REQUEST),
    // UPLOAD FILE
    AVATAR_URL_REQUIRED(2024, "Avatar URL is required", HttpStatus.BAD_REQUEST),
    PROOF_URL_REQUIRED(2025, "Proof file URL is required", HttpStatus.BAD_REQUEST),
    EDUCATIONAL_LEVEL_REQUIRED(2026, "Educational level is required", HttpStatus.BAD_REQUEST),

    // RESPONSE VALIDATION (TUTOR REGISTER RESPONSE)
    TUTOR_ID_REQUIRED(2050, "Tutor ID is missing in response", HttpStatus.INTERNAL_SERVER_ERROR),
    VERIFICATION_STATUS_REQUIRED(2051, "Verification status is missing in response", HttpStatus.INTERNAL_SERVER_ERROR),
            
    
    // TUTOR REGISTER EXCEPTION 
    SUBJECT_NOT_SELECTED(2100, "No subjects selected", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(2101, "Password and confirm password do not match", HttpStatus.BAD_REQUEST),
                    
    FILE_SAVE_ERROR(2200, "Error saving file", HttpStatus.INTERNAL_SERVER_ERROR),
    URL_REQUIRE(2201, "File URL is required", HttpStatus.BAD_REQUEST),
    STACK_OVERFLOW(2202, "Recursive data detected or infinite loop while processing the request.", HttpStatus.INTERNAL_SERVER_ERROR),

    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}

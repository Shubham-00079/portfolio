package com.shubham.portfolio.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {

    private Long id;
    private String fullName;
    private String designation;
    private String about;
    private String email;
    private String phone;
    private String location;
    private String profileImage;
    private String resumeUrl;
    private String github;
    private String linkedin;

}
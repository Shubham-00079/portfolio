package com.shubham.portfolio.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequest {

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

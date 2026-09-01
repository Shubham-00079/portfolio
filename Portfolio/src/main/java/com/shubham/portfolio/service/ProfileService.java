package com.shubham.portfolio.service;

import com.shubham.portfolio.dto.request.ProfileRequest;
import com.shubham.portfolio.dto.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse saveProfile(ProfileRequest request);

    ProfileResponse getProfile();

    ProfileResponse updateProfile(Long id, ProfileRequest request);

}
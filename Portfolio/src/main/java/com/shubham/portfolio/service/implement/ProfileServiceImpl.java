package com.shubham.portfolio.service.implement;

import org.springframework.stereotype.Service;

import com.shubham.portfolio.dto.request.ProfileRequest;
import com.shubham.portfolio.dto.response.ProfileResponse;
import com.shubham.portfolio.entity.Profile;
import com.shubham.portfolio.repository.ProfileRepository;
import com.shubham.portfolio.service.ProfileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
	private final ProfileRepository profileRepository;
	@Override
	public ProfileResponse saveProfile(ProfileRequest request) {

	    Profile profile = Profile.builder()
	            .fullName(request.getFullName())
	            .designation(request.getDesignation())
	            .about(request.getAbout())
	            .email(request.getEmail())
	            .phone(request.getPhone())
	            .location(request.getLocation())
	            .profileImage(request.getProfileImage())
	            .resumeUrl(request.getResumeUrl())
	            .github(request.getGithub())
	            .linkedin(request.getLinkedin())
	            .build();

	    Profile savedProfile = profileRepository.save(profile);

	    return ProfileResponse.builder()
	            .id(savedProfile.getId())
	            .fullName(savedProfile.getFullName())
	            .designation(savedProfile.getDesignation())
	            .about(savedProfile.getAbout())
	            .email(savedProfile.getEmail())
	            .phone(savedProfile.getPhone())
	            .location(savedProfile.getLocation())
	            .profileImage(savedProfile.getProfileImage())
	            .resumeUrl(savedProfile.getResumeUrl())
	            .github(savedProfile.getGithub())
	            .linkedin(savedProfile.getLinkedin())
	            .build();
	}

    @Override
    public ProfileResponse getProfile() {
        return null;
    }

    @Override
    public ProfileResponse updateProfile(Long id, ProfileRequest request) {
        return null;
    }

}
package com.example.pjatk.librarymanagementapplication.model.dto;

import com.example.pjatk.librarymanagementapplication.model.enums.MembershipType;

public class UserCreateDto {
    private String email;

    private MembershipType membershipType; // Nowe pole


    public String getEmail() {
        return email;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }
}


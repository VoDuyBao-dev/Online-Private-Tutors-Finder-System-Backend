package com.example.tutorsFinderSystem.controller.learner;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tutors/profile")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_LEARNER')")
public class DashboardUserController {
}

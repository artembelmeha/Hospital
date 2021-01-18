package com.example.hospital.controller;

public interface Constants {
	String REDIRECT_PREFIX = "redirect:";
	String PAGE_LOGIN = "login";
	String PAGE_HOME = "home";
	String PAGE_REGISTRATION = "registration";
	String PAGE_USERS_RECENT = "users-recent";
	String PAGE_NURSES = "users-nurse";
	String PAGE_DOCTORS = "users-doctors";
	String PAGE_PATIENTS = "users-patients";
	String PAGE_MEDICAL_CARD = "medical-card";

	String PAGE_DOCTOR_REGISTRATION = "doctor-registration";
	String PAGE_PATIENT_REGISTRATION = "patient-registration";
	String PAGE_PATIENT_INFO = "patient-info";

	String REDIRECT_TO_PAGE_NURSES = REDIRECT_PREFIX + "/users/nurses";;
	String REDIRECT_TO_PAGE_DOCTORS = REDIRECT_PREFIX + "/users/doctors";
	String REDIRECT_TO_PAGE_PATIENTS_OF = REDIRECT_PREFIX + "/users/patients/of/";
	String REDIRECT_TO_PAGE_PATIENTS = REDIRECT_PREFIX + "/users/patients/";
	String REDIRECT_TO_PAGE_HOME = REDIRECT_PREFIX + "/home";
	String REDIRECT_TO_REGISTRATION = REDIRECT_PREFIX + "/users";



	String REDIRECT_TO_PEOPLE_PAGE = REDIRECT_PREFIX + "/people";

	String INDEX_PAGE = "index";

	String USERS = "users";
	String USER = "user";
	String ASSIGNMENTS = "assignment";
	String DOCTORS = "doctors";
	String PATIENT_DTO = "patientDTO";
	String PATIENT_INFO_DTO = "patientInfoDto";
	String ASSIGNMENT_DTO = "assignmentDto";
}

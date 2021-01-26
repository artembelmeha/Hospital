package com.example.hospital.controller;

public interface Constants {

	String REDIRECT_PREFIX = "redirect:";
	String PREFIX_INFO = "/info";
	String PAGE_LOGIN = "login";
	String PAGE_HOME = "home";
	String PAGE_REGISTRATION = "registration";
	String PAGE_USERS_UNDEFINE = "users-undefine";
	String PAGE_NURSES = "users-nurse";
	String PAGE_DOCTORS = "users-doctors";
	String PAGE_PATIENTS = "users-patients";
	String PAGE_MEDICAL_CARD = "medical-card";
	String PAGE_ASSIGNMENT_NEW = "assignment-new";
	String PAGE_ASSIGNMENT = "assignment";


	String PAGN_ASC = "asc";
	String PAGN_DESC = "desc";
	String PAGN_REVERSE_SORT_DIR = "reverseSortDir";
	String PAGN_CURRENT_PAGE = "currentPage";
	String PAGN_TOTAL_PAGES = "totalPages";
	String PAGN_TOTAL_USER = "totalUser";
	String PAGN_SORT_FIELD = "sortField";
	String PAGN_SORT_DIRECTION = "sortDirection";
	String PAGENATION_SORT_BY_DEFAULT = "/1/?sortField=firstName&sortDir=asc";

	String PAGE_DOCTOR_REGISTRATION = "doctor-registration";
	String PAGE_PATIENT_REGISTRATION = "patient-registration";
	String PAGE_PATIENT_INFO = "patient-info";

	String REDIRECT_TO_PAGE_NURSES = REDIRECT_PREFIX + "/users/nurses";
	String REDIRECT_TO_PAGE_PATIENTS = REDIRECT_PREFIX + "/users/patients/";
	String REDIRECT_TO_PAGE_DOCTORS = REDIRECT_PREFIX + "/users/doctors";
	String REDIRECT_TO_PAGE_PATIENTS_OF = REDIRECT_PREFIX + "/users/patients/of/";
	String REDIRECT_TO_PAGE_HOME = REDIRECT_PREFIX + "/home";
	String REDIRECT_TO_MEDICAL_CARD = REDIRECT_PREFIX + "/medicalCard/";
	String REDIRECT_TO_ASSIGNMENT = REDIRECT_PREFIX + "/assignment";
	String REDIRECT_TO_ASSIGNMENT_VIEW = REDIRECT_TO_ASSIGNMENT + "/view/";
	String REDIRECT_TO_PAGE_REGISTRATION = REDIRECT_TO_ASSIGNMENT + "/registration";
	String ANONYMOUS = "anonymousUser";
	String USERS = "users";
	String USER = "user";
	String NURSES = "nurses";
	String DOCTORS = "doctors";
	String REGISTRATION_INFO = "registrationInfo";
	String PATIENT_DTO = "patientDto";
	String PATIENT_INFO_DTO = "patientInfoDto";
	String ASSIGNMENT_DTO = "assignmentDto";
	String MEDICAL_CARD = "medicalCard";
	String DIAGNOSIS = "diagnosis";
	String ID_OF_USER = "id";
	String FIELD_FIRST_NAME = "firstName";
}

package com.example.hospital.dto;

import com.example.hospital.model.MedicalCard;
import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;


@Data
public class MedicalCardDto {

	private long id;
	private UserDto user;
	private String finalDiagnosis;
	private List<AssignmentDto> assignmentList;

	public MedicalCardDto(MedicalCard medicalCard) {
		this.id = medicalCard.getId();
		this.finalDiagnosis = medicalCard.getFinalDiagnosis();
		this.user = new UserDto(medicalCard.getUser());
		this.assignmentList = emptyIfNull(medicalCard.getAssignmentList()).stream()
				.map(AssignmentDto::new)
				.collect(toList());
	}
}

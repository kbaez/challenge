package com.kbaez.challange.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kbaez.challange.dto.SatelliteDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopSecretRequest {

	@Size(min = 3, max = 3, message = "the amount of satellite cannot be less or more than 3")
	@NotNull
	@Valid
	private List<SatelliteDTO> satellites;
}

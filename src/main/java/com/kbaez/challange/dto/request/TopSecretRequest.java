package com.kbaez.challange.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.kbaez.challange.model.Satellite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TopSecretRequest {

	  @Size(min = 3, max = 3, message = "the amount of satellite cannot be less or more than 3")
	  @NotNull
	  @Valid
	  private List<Satellite> satellites;

}

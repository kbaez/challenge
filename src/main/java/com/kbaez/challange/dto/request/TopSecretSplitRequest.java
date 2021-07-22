package com.kbaez.challange.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopSecretSplitRequest {

  @PositiveOrZero(message = "distance can`t be smaller than the 0")
  @NotNull
  private Float distance;

  @NotNull
  @NotEmpty
  private String[] message;
}

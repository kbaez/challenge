package com.kbaez.challange.model;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Satellite implements Serializable {

  private static final long serialVersionUID = 7867671085213678632L;

  @PositiveOrZero(message = "distance can't be smaller than the 0")
  @NotNull
  private Float distance;

  @NotBlank
  private String name;

  @NotNull
  @NotEmpty
  private String[] message;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Satellite satellite = (Satellite) o;
    return name.equals(satellite.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}

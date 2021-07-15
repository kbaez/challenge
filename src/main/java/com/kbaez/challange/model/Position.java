package com.kbaez.challange.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Position implements Serializable {

  private static final long serialVersionUID = 4359789328354335107L;

  private float x;
  private float y;
}

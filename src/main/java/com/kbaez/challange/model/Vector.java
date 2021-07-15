package com.kbaez.challange.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jefferson Mendoza, mendosajefferson@gmail.com
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vector {
  private String name;
  private double positionx;
  private double positiony;
}

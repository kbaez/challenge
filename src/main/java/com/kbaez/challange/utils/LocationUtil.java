package com.kbaez.challange.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.model.Satellite;

@Component
public class LocationUtil {

//	public UbicacionDto getUbicacionDto(Ubicacion ubicacion) {
//		
//		UbicacionDto response = new UbicacionDto();
//		
//		response.setX(ubicacion.getX());
//		response.setY(ubicacion.getY());
//		
//		return response;
//	}
//	
//	public UbicacionPedidoAuxilio getUbicacionPedidoAuxilioFromUbicacionDto(UbicacionDto ubicacion) {
//		
//		UbicacionPedidoAuxilio response = new UbicacionPedidoAuxilio();
//		
//		response.setX(ubicacion.getX());
//		response.setY(ubicacion.getY());
//		
//		return response;
//	}
//	
	public void validateDistances(float[] distances) throws ConflictException {
		if (distances == null) {
			throw new ConflictException("Error. La cantidad de distancias es vacia");
		}
		if (distances.length < 3) {
			throw new ConflictException("Error. La cantidad de distancias es menor a 3. No se puede realizar la trilateracion para ubicar a la nave");
		}
	}

	public void validateSatelites(List<Satellite> satellites) throws ConflictException {
		if (satellites == null) {
			throw new ConflictException("Error. La cantidad de satelites es vacia");
		}
		if (satellites.size() < 3) {
			throw new ConflictException("Error. La cantidad de satelites es menor a 3. No se puede realizar la trilateracion para ubicar a la nave");
		}
	}

}

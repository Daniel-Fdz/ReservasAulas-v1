package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public class Reservas {

	private List<Reserva> coleccionReservas;
	
	// Constructor por defecto que crea un ArrayList
	public Reservas() {
		coleccionReservas = new ArrayList<Reserva>();
	}
	
	// Constructor copia
	public Reservas(Reservas reservas) {
		if(reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		} else {
			setReservas(reservas);
		}
	}
	
	// Método que establece las reservas
	private void setReservas(Reservas reservas) {
		if(reservas == null) {
			throw new NullPointerException("ERROR: No se pueden establecer reservas nulas.");
		} else {
			coleccionReservas = reservas.getReservas();
		}
	}
	
	// Implementación del método de copia profunda
	private List<Reserva> copiaProfundaReservas(List<Reserva> reservas) {
		List<Reserva> copia = new ArrayList<>();
		Iterator<Reserva> it = reservas.iterator();
		
		while(it.hasNext()) {
			copia.add(new Reserva(it.next()));
		}
		return copia;
	}
	
	// Método que devuelve una copia profunda de las reservas
	public List<Reserva> getReservas() {
		return copiaProfundaReservas(coleccionReservas);
	}
	
	// Devuelve el número de reservas
	public int getNumReservas() {
		return coleccionReservas.size();
	}
	
	// Inserta una reserva pasada por parámetro
	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if(reserva == null) {
			throw new NullPointerException("ERROR: No se puede realizar una reserva nula.");
		} else if(reserva.getPermanencia().getDia().compareTo(LocalDate.now()) < 0) {
			throw new IllegalArgumentException("ERROR: No se puede insertar una cita a una fecha pasada.");
		}
		
		if(coleccionReservas.contains(reserva)) {
			throw new OperationNotSupportedException("ERROR: La reserva ya existe.");
		}
		
		coleccionReservas.add(new Reserva(reserva));
	}
	
	// Comprueba que la reserva pasada como parámetro es una reserva para el mes siguiente o posteriores. 
	// En caso afirmativo devolverá true mientras que en caso negativo devolverá false
	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		if(reserva == null) {
			throw new NullPointerException("ERROR: La reserva no puede estar nula.");
		}
		
		if(reserva.getPermanencia().getDia().getMonth().compareTo(LocalDate.now().getMonth()) < 0) {
			return false;
		} else {
			return true;
		}
	}
	
	// Devuelve una lista de todas la reservas del profesor para el mes pasado por parámetro
	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate mes) {
		if(profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		
		if(mes == null) {
			throw new NullPointerException("ERROR: El mes no puede ser nulo.");
		}
		
		List<Reserva> reservas = new ArrayList<>();
		Iterator<Reserva> it = coleccionReservas.iterator();
		
		while(it.hasNext()) {
			Reserva proxReserva = it.next();
			if(proxReserva.getProfesor().equals(profesor) && proxReserva.getPermanencia().getDia().getMonth().equals(mes)) {
				reservas.add(new Reserva(proxReserva));
			}
		}
		return reservas;
	}
	
	// Devuelve una reserva si existe para el aula indicado como parámetro
	private Reserva getReservaAulaDia(Aula aula, LocalDate dia) {
		if(aula == null) {
			throw new NullPointerException("ERROR: El aula no puede ser nula.");
		}
		
		if(dia == null) {
			throw new NullPointerException("ERROR: El día no puede ser nulo.");
		}
		
		Reserva reservaDia = null;
		Iterator<Reserva> it = coleccionReservas.iterator();
		
		while(it.hasNext()) {
			Reserva proxReserva = it.next();
			if(proxReserva.getAula().equals(aula) && proxReserva.getPermanencia().getDia().equals(dia)) {
				reservaDia = new Reserva(proxReserva);
			}
		}
		return reservaDia;
	}
	
	// Busca la reserva pasada por parámetro y la devuelve
	public Reserva buscar(Reserva reserva) {
		if(reserva == null) {
			throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
		} else if(coleccionReservas.isEmpty()) {
			throw new NullPointerException("ERROR: La lista está vacía.");
		}
		
		int indice = coleccionReservas.indexOf(reserva);
		
		if(coleccionReservas.contains(reserva)) {
		  	return new Reserva(coleccionReservas.get(indice));
		} else {
			throw new IllegalArgumentException("ERROR: El aula introducida no se encuentra en la base de datos.");
		}
	}
	
	// Elimina una reserva
	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if(reserva == null) {
			throw new NullPointerException("ERROR: No se puede anular una reserva nula.");
		} else if(buscar(reserva) == null) {
			throw new OperationNotSupportedException("ERROR: La reserva a anular no existe.");
		} else {
			if(coleccionReservas.contains(reserva)) {
				coleccionReservas.remove(reserva);
			}
		}
	}
	
	// Devuelve las reservas realizadas por un profesor
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		if(profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		} else {
			List<Reserva> reservas = new ArrayList<>();
			Iterator<Reserva> it = coleccionReservas.iterator();
		
			while(it.hasNext()) {
				Reserva proxReserva = it.next();
				if(profesor.equals(proxReserva.getProfesor())) {
					reservas.add(new Reserva(proxReserva));
				}
			}
			return reservas;
		}
	}
	
	// Devuelve las reservas por aula
	public List<Reserva> getReservasAula(Aula aula) {
		if(aula == null) {
			throw new NullPointerException("ERROR: El aula no puede ser nula.");
		} else {
			List<Reserva> reservas = new ArrayList<>();
			Iterator<Reserva> it = coleccionReservas.iterator();
		
			while(it.hasNext()) {
				Reserva proxReserva = it.next();
				if(aula.equals(proxReserva.getAula())) {
					reservas.add(new Reserva(proxReserva));
				}
			}
			return reservas;
		}
	}
	
	// Devuelve las reservas por permanencia
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		if(permanencia == null) {
			throw new NullPointerException("ERROR: La permanencia no puede ser nula.");
		} else {
			List<Reserva> reservas = new ArrayList<>();
			Iterator<Reserva> it = coleccionReservas.iterator();
			
			while(it.hasNext()) {
				Reserva proxReserva = it.next();
				if(permanencia.equals(proxReserva.getPermanencia())) {
					reservas.add(new Reserva(proxReserva));
				}
			}
			return reservas;
		}
	}
	
	// Consulta la disponibilidad de una reserva. Para ello, pide un aula y una permanencia
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if(aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		}
		
		if(permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}
		
		Iterator<Reserva> it = coleccionReservas.iterator();
		
		while(it.hasNext()) {
			Reserva proxReserva = it.next();
			if(aula.equals(proxReserva.getAula()) && permanencia.equals(proxReserva.getPermanencia())) {
				return false;
			}
		}
		return true;
	}
	
	// Representación de las reservas
	public List<String> representar() {
		List<String> reservas = new ArrayList<>();
		Iterator<Reserva> it = coleccionReservas.iterator();
		
		while(it.hasNext()) {
			reservas.add(it.next().toString());
		}
		return reservas;
	}
}

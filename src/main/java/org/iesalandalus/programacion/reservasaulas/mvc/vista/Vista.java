package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import java.util.Iterator;
import java.util.List;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public class Vista {

	private static final String ERROR = "ERROR: No hay reservas.";
	private static final String NOMBRE_VALIDO = "Daniel";
	private static final String CORREO_VALIDO = "daniel@mail.com";
	private Controlador controlador;
	
	// Constructor que establece la vista
	public Vista() {
		Opcion.setVista(this);
	}
	
	// Método que establece el controlador
	public void setControlador(Controlador controlador) {
		if(controlador == null) {
			throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
		} else {
			this.controlador = controlador;
		}
	}
	
	// Muestra la cabecera y el menú hasta que el usuario decide salir
	public void comenzar() {
		Consola.mostrarCabecera("Programa para la gestión de reservas de espacios del IES Al-Ándalus");
		int ordinalOpcion;
		
		do 
		{
			Consola.mostrarMenu();
			ordinalOpcion = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinalOpcion);
			opcion.ejecutar();
		} while (ordinalOpcion != Opcion.SALIR.ordinal());
	}
	
	// Método para salir de la aplicación
	public void salir() {
		Consola.mostrarCabecera("El programa se ha cerrado.");
		controlador.terminar();
	}
	
	// Inserta un aula y en caso afirmativo nos informa del resultado de la operación
	public void insertarAula() {
		Consola.mostrarCabecera("Insertar un aula");
		
		try {
			controlador.insertarAula(Consola.leerAula());
			System.out.println("Se ha insertado el aula.\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Elimina un aula y nos informa del resultado de la operación
	public void borrarAula() {
		Consola.mostrarCabecera("Eliminar un aula");
		
		try {
			controlador.borrarAula(Consola.leerAula());
			System.out.println("Se ha eliminado el aula.\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Busca un aula y la muestra por consola
	public void buscarAula() {
		Aula aula;
		Consola.mostrarCabecera("Buscar un aula");
		
		try {
			aula = controlador.buscarAula(Consola.leerAula());
			System.out.println(aula.toString());
		} catch(IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Muestra el listado de las aulas por consola
	public void listarAulas() {
		List<String> aulas = controlador.representarAulas();
		Iterator<String> it = aulas.iterator();
		Consola.mostrarCabecera("Listado de aulas");
		
		if(aulas.size() == 0) {
			System.out.println("No hay aulas reservadas.\n");
		} else {
			while(it.hasNext()) {
				System.out.println(it.next().toString());
			}
		}
	}
	
	// Inserta un profesor y en caso afirmativo nos informa del resultado de la operación
	public void insertarProfesor() {
		Consola.mostrarCabecera("Insertar un profesor");
		
		try {
			controlador.insertarProfesor(Consola.leerProfesor());
			System.out.println("Se ha insertado el profesor.\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Elimina un profesor y en caso afirmativo nos informa del resultado de la operación
	public void borrarProfesor() {
		Consola.mostrarCabecera("Eliminar un profesor");
		
		try {
			controlador.borrarProfesor(Consola.leerProfesor());
			System.out.println("Se ha eliminado el profesor.\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Busca un profesor y lo muestra por consola
	public void buscarProfesor() {
		Profesor profesor;
		Consola.mostrarCabecera("Buscar un profesor");
		
		try {
			profesor = controlador.buscarProfesor(Consola.leerProfesor());
			System.out.println(profesor.toString());
		} catch(IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Muestra el listado de los profesores por consola
	public void listarProfesores() {
		List<String> profesores = controlador.representarProfesores();
		Iterator<String> it = profesores.iterator();
		Consola.mostrarCabecera("Listado de profesores");
		
		if(profesores.size() == 0) {
			System.out.println("No hay profesores.\n");
		} else {
			while(it.hasNext()) {
				System.out.println(it.next().toString());
			}
		}
	}
	
	// Permite insertar una reserva en nuestro ArrayList de reservas
	public void realizarReserva() {
		Consola.mostrarCabecera("Realizar una reserva");

		try {
			Profesor profesor = controlador.buscarProfesor(Consola.leerProfesor());
		
			if(profesor == null) {
				System.out.println("ERROR: El profesor no existe en la base de datos.");
			} else {
				controlador.realizarReserva(leerReserva(profesor));
				System.out.println("Se ha realizado la reserva.\n");
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Crea una nueva permanencia con sus datos y devuelve una reserva
	private Reserva leerReserva(Profesor profesor) {
		Consola.mostrarCabecera("Leer una reserva");
		Permanencia permanencia = new Permanencia(Consola.leerDia(), Consola.leerTramo());
		return new Reserva(profesor, Consola.leerAula(), permanencia);
	}
	
	// Permite anular una reserva
	public void anularReserva() {
		Consola.mostrarCabecera("Anular una reserva");
		
		try {
			controlador.anularReserva(leerReserva(Consola.leerProfesor()));
			System.out.println("Se ha anulado la reserva.\n");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Lista las reservas que tenemos en el ArrayList por consola
	public void listarReservas() {
		List<String> reservas = controlador.representarReservas();
		Iterator<String> it = reservas.iterator();
		Consola.mostrarCabecera("Listado de reservas");
		
		if(reservas.size() == 0) {
			System.out.println("No hay reservas.\n");
		} else {
			while(it.hasNext()) {
				System.out.println(it.next().toString());
			}
		}
	}
	
	// Lista las reservas por aula que tenemos en el ArrayList por consola
	public void listarReservasAula() {
		List<Reserva> reservas = controlador.getReservasAula(Consola.leerAula());
		Iterator<Reserva> it = reservas.iterator();
		Consola.mostrarCabecera("Listado de reservas por aula");
		
		if(reservas.size() == 0) {
			System.out.println("No hay aulas reservadas.\n");
		} else {
			while(it.hasNext()) {
				System.out.println(it.next().toString());
			}
		}
	}
	
	// Lista las reservas por profesor que tenemos en el ArrayList por consola
	public void listarReservasProfesor() {
		List<Reserva> reservas = controlador.getReservasProfesor(Consola.leerProfesor());
		Iterator<Reserva> it = reservas.iterator();
		Consola.mostrarCabecera("Listado de reservas por profesor");
		
		if(reservas.size() == 0) {
			System.out.println("No hay aulas reservadas.\n");
		} else {
			while(it.hasNext()) {
				System.out.println(it.next().toString());
			}
		}
	}
	
	// Lista las reservas por permanencia que tenemos en el ArrayList por consola
	public void listarReservasPermanencia() {
		List<Reserva> reservas = controlador.getReservasPermanencia(new Permanencia(Consola.leerDia(), Consola.leerTramo()));
		Iterator<Reserva> it = reservas.iterator();
		Consola.mostrarCabecera("Listado de reservas por permanencia");
		
		if(reservas.size() == 0) {
			System.out.println("No hay aulas reservadas.\n");
		} else {
			while(it.hasNext()) {
				System.out.println(it.next().toString());
			}
		}
	}
	
	// Muestra la disponibilidad de un aula por consola
	public void consultarDisponibilidad() {
		Consola.mostrarCabecera("Disponibilidad de las reservas");
		Permanencia permanencia = new Permanencia(Consola.leerDia(), Consola.leerTramo());
		
		if(controlador.consultarDisponibilidad(Consola.leerAula(), permanencia)) {
			System.out.println("El aula consultada se encuentra disponible.\n");
		} else {
			System.out.println("El aula consultada no se encuentra disponible.\n");
		}
	}
}

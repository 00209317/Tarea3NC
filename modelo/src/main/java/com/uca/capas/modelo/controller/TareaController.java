package com.uca.capas.modelo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TareaController {

	@RequestMapping("/ingresar")
	public String index() {

		return "clases/tarea/index";
	}

	@RequestMapping("/params")
	public ModelAndView parametros2(@RequestParam String usuario, @RequestParam String apellido,
			@RequestParam @DateTimeFormat(iso = ISO.DATE) Date date, @RequestParam String nacimiento,
			@RequestParam String procedencia, @RequestParam int tel_fijo, @RequestParam int tel_movil) {
		ModelAndView mav = new ModelAndView();
		List<String> listaE = new ArrayList<>();
		List<String> listaG = new ArrayList<>();

		int bandera25 = 0;
		int banderaN = 0;
		validacion25(usuario, listaE, listaG, bandera25);
		bandera25 = bandera25 + 1;
		validacion25(apellido, listaE, listaG, bandera25);
		bandera25 = bandera25 + 1;
		validarFecha(date, listaE, listaG);
		validacion25(nacimiento, listaE, listaG, bandera25);
		validacion100(procedencia, listaE, listaG);
		validacionNumeros(Integer.toString(tel_fijo), listaE, listaG, banderaN);
		banderaN = banderaN + 1;
		validacionNumeros(Integer.toString(tel_movil), listaE, listaG, banderaN);

		if (listaG.size() == 7) {
			mav.addObject("lista_guardados", listaG);
			mav.setViewName("clases/tarea/resultado2");
		} else {
			mav.addObject("lista_errores", listaE);
			mav.setViewName("clases/tarea/resultado");
		}
		return mav;
	}

	int bandera = 0;

	public void validacion25(String srt, List<String> listaE, List<String> listaG, int bandera) {
		String[] campo = { "Nombre", "Apellido", "Lugar de nacimiento" };
		if (srt.length() == 0 || srt.length() > 25) {
			String val = "El campo " + campo[bandera] + " no puede ser mayor a 25 caracteres, ó, ir sin caracteres";
			listaE.add(val);
		} else {
			listaG.add(srt);
		}
	}

	public void validacion100(String srt, List<String> listaE, List<String> listaG) {
		if (srt.length() < 1 || srt.length() > 100) {
			String val = "El campo de instituto o colegio de procedencia no puede ser mayor a 100, ni menor a 1";
			listaE.add(val);
		} else {
			listaG.add(srt);
		}
	}

	public void validacionNumeros(String srt, List<String> listaE, List<String> listaG, int bandera) {
		String[] campo = { "Teléfono fijo", "Teléfono móvil" };
		if (srt.length() == 8) {
			listaG.add(srt);
		} else {
			String val = "El campo " + campo[bandera] + " debe tener 8 dígitos";
			listaE.add(val);
		}
	}

	public void validarFecha(Date date, List<String> listaE, List<String> listaG) {
		String fechaInicio = "01-01-2003"; // fecha de ejemplo
		SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		Date fechaInicioDate;
		try {
			fechaInicioDate = fecha.parse(fechaInicio);
			if (fechaInicioDate.after(date)) {
				String fec = "La fecha es menor a 01-01-2003";
				listaE.add(fec);
			} else {
				listaG.add(date.toString());// fecha actual mayor
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // String a date
	}

}

package com.cmrise.ejb.backing.mrq.preview;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.PrimeRequestContext;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

import com.cmrise.exception.CmriseRuntimeException;
import com.cmrise.utils.Utilitarios;
import com.google.gson.Gson;

public class Poligonos {

	static class Punto 
	{ 
		double x; 
		double y; 

			public Punto(double x, double y) 
			{ 
				this.x = x; 
				this.y = y; 
			} 
		
	}; 
	
	public double obtenerPuntuacion(int puntuacion,int poligonos,int ancho,String respuestaPreguntaCandidato ,String respuestas) {
		double valorPuntuacion=0;
		try {
			valorPuntuacion=puntuacion(puntuacion,poligonos,ancho,respuestaPreguntaCandidato ,respuestas);		
		}
		catch (CmriseRuntimeException  e) {
			throw e;
		}
		catch(Exception e) {			  		 
	   		  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Utilitarios.ERROR_PUNTUACION, null));
			
		}
		return valorPuntuacion;
	}
	private double puntuacion(int puntuacion,int poligonos,int ancho,String respuestaPreguntaCandidato ,String respuestas) {
		Gson g = new Gson();
		JSONObject model = new JSONObject(respuestas);
		JSONArray conjuntoPoligonos = model.getJSONObject("model").getJSONArray("nodeDataArray");
		  
		JSONArray usuarioCordenadas = new JSONArray(respuestaPreguntaCandidato);
		
		if(conjuntoPoligonos.length() > 0) {
			if(usuarioCordenadas.length() == 0) {
				throw new CmriseRuntimeException("Por favor, marque al menos un punto en la imagen.");
			}
		}
		
		double valor=(double)puntuacion/(double)poligonos;
		double total=0;
		for(int i=0;i<conjuntoPoligonos.length();i++) {
			 JSONObject poligono  = conjuntoPoligonos.getJSONObject(i);
			
			JSONArray px= poligono.optJSONArray("pointX");
			JSONArray py= poligono.optJSONArray("pointY");
			
			for(int j=0;j<usuarioCordenadas.length();j++) {
				JSONObject punto  = usuarioCordenadas.getJSONObject(j);
				String x=punto.getString("x");
				String y=punto.getString("y");
				if(validarPuntoDentro(ancho,Double.valueOf(x),Double.valueOf(y),px, py)) {
					total+=valor;	
					break; // TO Stop, for multiple point in same polygon
					
				}
				
				
			}			
		}
		return total;
		
	}
	
	
	
	public double calculateScore(int puntuacion,int poligonos,int width, JSONArray points ,String respuestas) {
		double valorPuntuacion=0;
		try {
			valorPuntuacion=calculatePuntuacion(puntuacion,poligonos,width,points,respuestas);		
		}
		catch(Exception e) {			  		 
	   		  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Utilitarios.ERROR_PUNTUACION, null));
			
		}
		return valorPuntuacion;
	}
	private double calculatePuntuacion(int puntuacion,int poligonos,int width,JSONArray points,String respuestas) {
		JSONObject model = new JSONObject(respuestas);
		JSONArray conjuntoPoligonos = model.getJSONObject("model").getJSONArray("nodeDataArray");
		double valor=(double)puntuacion/(double)poligonos;
		double total=0;
		
		for(int j=0;j<points.length();j++) {
			points.getJSONObject(j).put("color", "red");
		}	
		
		for(int i=0;i<conjuntoPoligonos.length();i++) {
    		JSONObject poligono  = conjuntoPoligonos.getJSONObject(i);
			JSONArray px= poligono.optJSONArray("pointX");
			JSONArray py= poligono.optJSONArray("pointY");
			for(int j=0;j<points.length();j++) {
				JSONObject punto  = points.getJSONObject(j);
				String x=punto.getString("x");
				String y=punto.getString("y");
				if(validarPuntoDentro(width,Double.valueOf(x),Double.valueOf(y),px, py)) {
					punto.put("color", "green");
					total+=valor;	
					break; // TO Stop, for multiple point in same polygon
				}
			}			
		}
		return total;
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private boolean validarPuntoDentro(int ancho,double usuarioX,double usuarioY,JSONArray px, JSONArray py)
	{
		int lados=px.length();
		int vecesCruza=0;
		Punto usuarioA=new Punto(usuarioX,usuarioY);
		Punto usuarioB=new Punto(usuarioX+ancho,usuarioY);
		for(int i=0;i<lados;i++) {
			Punto lineaA=null;
			Punto lineaB=null;
			if(i<lados-1) {
			lineaA=new Punto(px.optDouble(i), py.optDouble(i));
			lineaB=new Punto(px.optDouble(i+1), py.optDouble(i+1));
			}else {
				lineaA=new Punto(px.optDouble(i), py.optDouble(i));
				lineaB=new Punto(px.optDouble(0), py.optDouble(0));
			}
			if(validarInterseccion(usuarioA,usuarioB,lineaA,lineaB))
				vecesCruza++;
		}
		if(vecesCruza%2!=0)
			return true;
		return false;
		
	}

	private double obtenerOrientacion(Punto p, Punto q, Punto r) 
	{ 
		
		double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y); 
		if (val == 0) return 0; 
		return (val > 0)? 1: 2; 
	} 
	
	private boolean validarInterseccion(Punto p1, Punto q1, Punto p2, Punto q2) 
	{ 
		
		double o1 = obtenerOrientacion(p1, q1, p2); 
		double o2 = obtenerOrientacion(p1, q1, q2); 
		double o3 = obtenerOrientacion(p2, q2, p1); 
		double o4 = obtenerOrientacion(p2, q2, q1); 

		
		if (o1 != o2 && o3 != o4) 
			return true; 		
		if (o1 == 0 && esColinear(p1, p2, q1)) return true; 		
		if (o2 == 0 && esColinear(p1, q2, q1)) return true; 		
		if (o3 == 0 && esColinear(p2, p1, q2)) return true; 		
		if (o4 == 0 && esColinear(p2, q1, q2)) return true; 

		return false; 
	} 

	
	private  boolean esColinear(Punto p, Punto q, Punto r) 
	 { 
	 	if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && 
	 		q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y)) 
	 	return true; 

	 	return false; 
	 } 
}





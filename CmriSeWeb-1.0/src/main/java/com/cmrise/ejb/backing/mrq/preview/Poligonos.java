package com.cmrise.ejb.backing.mrq.preview;

import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

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
		
		Gson g = new Gson();
		JSONArray conjuntoPoligonos = new JSONArray(respuestas);
		  
		JSONArray usuarioCordenadas = new JSONArray(respuestaPreguntaCandidato);

		
		double valor=(double)puntuacion/(double)poligonos;
		double total=0;
		for(int i=0;i<conjuntoPoligonos.length();i++) {
			 JSONObject poligono  = conjuntoPoligonos.getJSONObject(i);
			
			String[] px= poligono.get("pointX").toString().replace('[', ' ').replace(']', ' ').trim().split(",");
			String[] py=poligono.get("pointY").toString().replace('[', ' ').replace(']', ' ').trim().split(",");
			
			for(int j=0;j<usuarioCordenadas.length();j++) {
				JSONObject punto  = usuarioCordenadas.getJSONObject(j);
				String x=punto.getString("x");
				String y=punto.getString("y");
				if(validarPuntoDentro(ancho,Double.valueOf(x),Double.valueOf(y),px, py))
				total+=valor;	
				
			}			
		}
		return total;
		
	}
	
	
	private boolean validarPuntoDentro(int ancho,double usuarioX,double usuarioY,String[] px, String[] py)
	{
		int lados=px.length;
		int vecesCruza=0;
		Punto usuarioA=new Punto(usuarioX,usuarioY);
		Punto usuarioB=new Punto(usuarioX+ancho,usuarioY);
		for(int i=0;i<lados;i++) {
			Punto lineaA=null;
			Punto lineaB=null;
			if(i<lados-1) {
			lineaA=new Punto(Double.valueOf(px[i]),Double.valueOf(py[i]));
			lineaB=new Punto(Double.valueOf(px[i+1]),Double.valueOf(py[i+1]));
			}else {
				lineaA=new Punto(Double.valueOf(px[i]),Double.valueOf(py[i]));
				lineaB=new Punto(Double.valueOf(px[0]),Double.valueOf(py[0]));
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





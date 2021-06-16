package com.cmrise.ejb.backing.exams.mrqs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonCandidatosV1;
import com.cmrise.ejb.model.admin.AdmonUsuarios;
import com.cmrise.ejb.model.admin.AdmonUsuariosRolesV1;
import com.cmrise.ejb.services.admin.AdmonUsuariosLocal;
import com.cmrise.ejb.services.admin.AdmonUsuariosRolesLocal;
import com.cmrise.jpa.dto.admin.AdmonCandidatosV1Dto;
import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class CreateMrqsCandidatesForm {
	
	    private List<AdmonUsuariosRolesV1Dto> listAdmonUsuariosRolesV1Dto = new ArrayList<AdmonUsuariosRolesV1Dto>();
	    private List<AdmonUsuarios> listAdmonUsuarios = new ArrayList<AdmonUsuarios>();
	    private long numeroRol;
		private long numeroUsuario;
		private Date fechaEfectivaDesde;
		private Date fechaEfectivaHasta;
	    private String nombre; 
	    private String apellidoPaterno; 
	    private String apellidoMaterno; 
	    private String contrasenia; 
	    private String correoElectronico;
	    private String curp; 
	    private String estado; 
	    private String sedeHospital; 
	    private String errorCaptura;
	    ///*****Variables filtro*****///
	    private String findCurp;
	    private String findNombre;
	    private String findAPaterno;
	    private String findAMaterno;
	    private String findNombreActPor;
	    private String findFechaAct;
	    
		private AdmonUsuarios admonUsuariosForAction = new AdmonUsuarios();
	    
		@ManagedProperty(value="#{userLogin}")
		private UserLogin userLogin;
		
	    @Inject 
		AdmonUsuariosLocal admonUsuariosLocal; 
	    
		@Inject
		AdmonUsuariosRolesLocal admonUsuariosRolesLocal; 

		@Inject 
		UtilitariosLocal utilitariosLocal;
		
	    @PostConstruct
		 public void init() {
	    	System.out.println("Entra Init");
				 refreshEntity();
				 System.out.println("Sale Init");
		 }		
		
	    public void refreshEntity() {
	    	 List<AdmonUsuariosRolesV1Dto> listAdmonUsuariosRolesV1Dto = admonUsuariosLocal.findCand(); 
			 Iterator<AdmonUsuariosRolesV1Dto> itertAAdmonUsuariosRolesV1Dto = listAdmonUsuariosRolesV1Dto.iterator();
			 listAdmonUsuarios = new ArrayList<AdmonUsuarios>();
			 while(itertAAdmonUsuariosRolesV1Dto.hasNext()) {
				AdmonUsuariosRolesV1Dto admonUsuariosRolesV1Dto = itertAAdmonUsuariosRolesV1Dto.next(); 
				AdmonUsuarios admonUsuarios = new AdmonUsuarios();

				System.out.println("Entra admonUsuarios");
				admonUsuarios.setNumero(admonUsuariosRolesV1Dto.getNumeroUsuario());
				System.out.println("admonCandidatosV1.getNumeroRol()");
				admonUsuarios.setNumeroRol(admonUsuariosRolesV1Dto.getNumeroRol());
				admonUsuarios.setNombre(admonUsuariosRolesV1Dto.getNombreUsuario());
				admonUsuarios.setApellidoPaterno(admonUsuariosRolesV1Dto.getApellidoPaterno());
				admonUsuarios.setApellidoMaterno(admonUsuariosRolesV1Dto.getApellidoMaterno());
				admonUsuarios.setCorreoElectronico(admonUsuariosRolesV1Dto.getCorreoElectronico());
				admonUsuarios.setFechaEfectivaDesde(utilitariosLocal.toUtilDate(admonUsuariosRolesV1Dto.getFedAu()));
				if(Utilitarios.endOfTime.equals(admonUsuariosRolesV1Dto.getFehAu())) {
					admonUsuarios.setFechaEfectivaHasta(null);	
				}else {
					admonUsuarios.setFechaEfectivaHasta(utilitariosLocal.toUtilDate(admonUsuariosRolesV1Dto.getFehAu()));	
				}
				
				admonUsuarios.setCurp(admonUsuariosRolesV1Dto.getCurp());
				admonUsuarios.setEstado(admonUsuariosRolesV1Dto.getEstado());
				admonUsuarios.setSedeHospital(admonUsuariosRolesV1Dto.getSedeHospital());
				admonUsuarios.setNumeroRol(admonUsuariosRolesV1Dto.getNumeroRol());

				if(admonUsuariosRolesV1Dto.getNombreActPor() != null)
					admonUsuarios.setActualizadoPorName(admonUsuariosRolesV1Dto.getNombreActPor());
				else
					admonUsuarios.setActualizadoPorName("No disponible");
				
				admonUsuarios.setFechaActualizacion(admonUsuariosRolesV1Dto.getFechaActualizacion());
				admonUsuarios.setFechaActString(admonUsuariosRolesV1Dto.getFechaActualizacion());	
				System.out.println("admonCandidatosV1.getCorreoElectronico()");
				System.out.println("admonCandidatosV1.getCurp()");
				System.out.println("Sale admonCandidatos");
				listAdmonUsuarios.add(admonUsuarios);
	    
			}
	    }
	    
		public void create() {
			 java.sql.Date sqlFechaEfectivaDesde = null; 
			 java.sql.Date sqlFechaEfectivaHasta = null; 
			 
			if(fechaEfectivaDesde != null) 
				sqlFechaEfectivaDesde = new java.sql.Date(fechaEfectivaDesde.getTime());
			else
				errorCaptura += "Fecha efectiva hasta vacia. " + '\n';
			
			if(fechaEfectivaHasta != null) 
				sqlFechaEfectivaHasta = new java.sql.Date(fechaEfectivaHasta.getTime());
			else 			
				sqlFechaEfectivaHasta = Utilitarios.endOfTime;
			
			if(isEmail(correoElectronico)==false)
				errorCaptura += "Correo invalido. " + '\n';
			
			if(errorCaptura=="" || errorCaptura==null)
				insertOnetoOneCandidate(getCurp(), getNombre(), getApellidoPaterno(), getApellidoMaterno(), getCorreoElectronico(), getContrasenia(), getEstado(), getSedeHospital(), sqlFechaEfectivaDesde, sqlFechaEfectivaHasta, getNumeroRol(), 0);
			
			if(errorCaptura!= ""){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Errores en el archivo: " + '\n', errorCaptura );
			    FacesContext.getCurrentInstance().addMessage(null, msg);
			    errorCaptura = "";
			}else {
			    FacesMessage msg = new FacesMessage(" El candidato", " registrado y asignado correctamente");
			    FacesContext.getCurrentInstance().addMessage(null, msg);
			}	
		}
		
		public void selectForAction(AdmonUsuarios pAdmonUsuarios) {
			System.out.println("*pAdmonUsuarios.getNumero():"+pAdmonUsuarios.getNumero());
			admonUsuariosForAction.setNumero(pAdmonUsuarios.getNumero());
			admonUsuariosForAction.setCurp(pAdmonUsuarios.getCurp());
			admonUsuariosForAction.setNombre(pAdmonUsuarios.getNombre());
			admonUsuariosForAction.setApellidoPaterno(pAdmonUsuarios.getApellidoPaterno());
			admonUsuariosForAction.setApellidoMaterno(pAdmonUsuarios.getApellidoMaterno());
			admonUsuariosForAction.setCorreoElectronico(pAdmonUsuarios.getCorreoElectronico());
			admonUsuariosForAction.setFechaEfectivaDesde(pAdmonUsuarios.getFechaEfectivaDesde());
			admonUsuariosForAction.setFechaEfectivaHasta(pAdmonUsuarios.getFechaEfectivaHasta());
			admonUsuariosForAction.setContrasenia(pAdmonUsuarios.getContrasenia());
			admonUsuariosForAction.setNumeroRol(pAdmonUsuarios.getNumeroRol());
		}
		
		public void delete() {
			boolean deleteIn = false; 
			admonUsuariosLocal.delete(admonUsuariosForAction.getNumero());
			refreshEntity();
			deleteIn = true; 
			PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
		}
		
		
		public UserLogin getUserLogin() {
			return userLogin;
		}
		public void setUserLogin(UserLogin userLogin) {
			this.userLogin = userLogin;
		}
		public Date getFechaEfectivaDesde() {
			return fechaEfectivaDesde;
		}
		public void setFechaEfectivaDesde(Date fechaEfectivaDesde) {
			this.fechaEfectivaDesde = fechaEfectivaDesde;
		}
		public Date getFechaEfectivaHasta() {
			return fechaEfectivaHasta;
		}
		public void setFechaEfectivaHasta(Date fechaEfectivaHasta) {
			this.fechaEfectivaHasta = fechaEfectivaHasta;
		}
		public long getNumeroRol() {
			return numeroRol;
		}
		public void setNumeroRol(long numeroRol) {
			this.numeroRol = numeroRol;
		}
		public long getNumeroUsuario() {
			return numeroUsuario;
		}
		public void setNumeroUsuario(long numeroUsuario) {
			this.numeroUsuario = numeroUsuario;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getApellidoPaterno() {
			return apellidoPaterno;
		}
		public void setApellidoPaterno(String apellidoPaterno) {
			this.apellidoPaterno = apellidoPaterno;
		}
		public String getApellidoMaterno() {
			return apellidoMaterno;
		}
		public void setApellidoMaterno(String apellidoMaterno) {
			this.apellidoMaterno = apellidoMaterno;
		}
		public String getContrasenia() {
			return contrasenia;
		}
		public void setContrasenia(String contrasenia) {
			this.contrasenia = contrasenia;
		}
		public String getCorreoElectronico() {
			return correoElectronico;
		}
		public void setCorreoElectronico(String correoElectronico) {
			this.correoElectronico = correoElectronico;
		}
		public String getCurp() {
			return curp;
		}
		public void setCurp(String curp) {
			this.curp = curp;
		}	
		public String getSedeHospital() {
			return this.sedeHospital;
		}
		public void setSedeHospital(String sedeHospital) {
			this.sedeHospital = sedeHospital;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
		public List<AdmonUsuariosRolesV1Dto> getListAdmonUsuariosRolesV1Dto() {
			return listAdmonUsuariosRolesV1Dto;
		}
		public void setListAdmonUsuariosRolesV1(List<AdmonUsuariosRolesV1Dto> listAdmonUsuariosRolesV1Dto) {
			this.listAdmonUsuariosRolesV1Dto = listAdmonUsuariosRolesV1Dto;
		}
		public List<AdmonUsuarios> getListAdmonUsuarios() {
			return listAdmonUsuarios;
		}
		public void setListAdmonUsuarios(List<AdmonUsuarios> listAdmonUsuarios) {
			this.listAdmonUsuarios = listAdmonUsuarios;
		}
		
		public void handleFileUpload(FileUploadEvent event) throws IOException, ParseException {
			errorCaptura = "";
			int lineaAct = 0;
			String validaValores;
			java.sql.Date fechaEfDesde = null;
			java.sql.Date fechaEfHasta = null;
			UploadedFile uploadedFile = event.getFile();
			BufferedReader reader = new BufferedReader(new InputStreamReader( uploadedFile.getInputStream(), StandardCharsets.UTF_8 ) );
			
			String line = null;
			while ((line = reader.readLine()) != null){
				lineaAct ++;
				String [] columns = line.split(",|;");
				 
				if(!line.contains("CURP")) { 
					validaValores = "";
			    	curp = columns[0].toUpperCase().trim();
			    	nombre = columns[1].toUpperCase().trim();
			    	apellidoPaterno = columns[2].toUpperCase().trim();
			    	apellidoMaterno = columns[3].toUpperCase().trim();
			    	correoElectronico = columns[4].trim();
			    	contrasenia = columns[5].trim();
			    	estado = columns[6].toUpperCase().trim();
			    	sedeHospital = columns[7].toUpperCase().trim();
			    	String fechaD = columns[8].trim();
			    	String fechaH = "";
			    	
			    	if(columns.length > 9)
			    		fechaH = columns[9].trim();
			    				    	
			    	if(curp == null || curp.length() == 0) {
			    		validaValores += "Linea "+lineaAct+" : 'CURP' vacio. " + '\n';
			    	}
			    	if(curp.length() < 18 || curp.length() > 18) {
			    		validaValores += "Linea "+lineaAct+ " : 'CURP' no tiene 18 caracteres. " + '\n';
			    	}
			    	if(nombre == null || nombre.length() == 0) {
			    		validaValores += "Linea "+lineaAct+" : 'Nombre' vacio. " + '\n';
			    	}
			    	if(apellidoPaterno == null || apellidoPaterno.length() == 0) {
			    		validaValores += "Linea "+lineaAct+" : 'Apellido Paterno' vacio. " + '\n';
			    	}
			    	if(apellidoMaterno == null || apellidoMaterno.length() == 0) {
			    		validaValores += "Linea "+lineaAct+" : 'Apellido Materno' vacio. " + '\n';
			    	}
			    	if(correoElectronico == null || correoElectronico.length() == 0) {
			    		validaValores += "Linea "+lineaAct+" : 'Correo' vacio. " + '\n';
			    	}else {
			    		if(isEmail(correoElectronico)==false)
			    			validaValores += "Linea "+lineaAct+" : Correo invalido. " + '\n';
			    	}
			    	if(contrasenia == null || contrasenia.length() == 0) {
			    		validaValores += "Linea "+lineaAct+" : 'Contraseña' vacia. " + '\n';
			    	}
			    	if(sedeHospital == null || sedeHospital.length() == 0) {
			    		validaValores += "Linea "+lineaAct+" : 'Sede Hospitalaria' vacio. " + '\n';
			    	}
			    	
			    	try {
			    		if(fechaD == null ) 
				    		validaValores += "Linea "+lineaAct+" : 'Fecha Desde' vacio. " + '\n';
				    	else
				    		fechaEfDesde = ConvertToDate(fechaD);
			    	}catch(Exception e) {
			    		validaValores += "Linea "+lineaAct+" : 'Fecha Desde' formato incorrecto. " + '\n';
			    	}
			    	
			    	try {
			    		fechaEfHasta = ConvertToDate(fechaH);
			    	}catch(Exception e){
			    		fechaEfHasta = null;
			    	}			    	
			    	
			    	if(validaValores == null || validaValores.length()==0)
			    		insertOnetoOneCandidate(curp, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, contrasenia, estado, sedeHospital, fechaEfDesde, fechaEfHasta, 1, lineaAct);
			    	else
			    		errorCaptura += validaValores;
				}
	         }
			
			reader.close();
			
			if(errorCaptura != ""){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Errores en el archivo: " + '\n', errorCaptura );
		        FacesContext.getCurrentInstance().addMessage(null, msg);
		        errorCaptura = "";
			}else {
		        FacesMessage msg = new FacesMessage("Registros guardados exitosamente.");
		        FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		
		public boolean insertOnetoOneCandidate(String curp, String nombre, String apPaterno, String apMaterno, String email, String contrasenia, String estado, String sedeH, java.sql.Date fechaEfDesde, java.sql.Date fechaEfHasta, long nRol, int linea){
			
			 AdmonUsuariosDto admonUsuariosDto = new AdmonUsuariosDto();
			 boolean createIn = false; 
			 
			 try{
				 
				 admonUsuariosDto.setCurp(curp);
				 admonUsuariosDto.setNombre(nombre);
				 admonUsuariosDto.setApellidoPaterno(apPaterno);
				 admonUsuariosDto.setApellidoMaterno(apMaterno);
				 admonUsuariosDto.setContrasenia(contrasenia);
				 admonUsuariosDto.setCorreoElectronico(email);
				 admonUsuariosDto.setEstado(estado);
				 admonUsuariosDto.setSedeHospital(sedeH);
				 
				 if(fechaEfDesde!=null) {
				   admonUsuariosDto.setFechaEfectivaDesde(fechaEfDesde);
				 }				 
				 if(fechaEfHasta==null) {
					 fechaEfHasta = Utilitarios.endOfTime;
				 }
				 
				 admonUsuariosDto.setFechaEfectivaHasta(fechaEfHasta);
				 admonUsuariosDto.setCreadoPor(userLogin.getNumeroUsuario());
				 admonUsuariosDto.setActualizadoPor(userLogin.getNumeroUsuario());
				 
				 System.out.println("userLogin.getNumeroUsuario():"+userLogin.getNumeroUsuario());
				 long longNumeroUsusario = admonUsuariosLocal.insert(admonUsuariosDto);
				 
				    /*************************************************************************/
				    AdmonUsuariosRolesDto admonUsuariosRolesDto = new AdmonUsuariosRolesDto();
					AdmonRolesDto admonRolesDto = new AdmonRolesDto();
					AdmonUsuariosDto admonUsuariosV2Dto = new AdmonUsuariosDto();
					
					System.out.println("longNumeroUsusario:"+longNumeroUsusario);
					System.out.println("this.getNumeroRol():"+ "1");
					
					admonUsuariosV2Dto.setNumero(longNumeroUsusario);
					admonRolesDto.setNumero(nRol);
					admonUsuariosRolesDto.setAdmonUsuario(admonUsuariosV2Dto);
					admonUsuariosRolesDto.setAdmonRole(admonRolesDto);
					admonUsuariosRolesDto.setEstatus(true);
					admonUsuariosRolesDto.setFechaEfectivaDesde(utilitariosLocal.toSqlDate(fechaEfDesde));
					if(null!=fechaEfHasta) 
						admonUsuariosRolesDto.setFechaEfectivaHasta(utilitariosLocal.toSqlDate(fechaEfHasta));	
					else 
						admonUsuariosRolesDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
					
					admonUsuariosRolesLocal.insert(admonUsuariosRolesDto);
				    /***********************************************************************/
				    
					createIn = true; 
					PrimeFaces.current().ajax().addCallbackParam("createIn", createIn);
					refreshEntity();
					return createIn;
					
			} catch (Exception  ex) {
				Throwable e;
				String mg = "";
				 if( (e = ex.getCause()) != null) {
						while( e.getCause() != null ) {e = e.getCause();}				
						if(e.getMessage().contains("clave duplicada") ) {
							mg = e.getMessage().substring(e.getMessage().indexOf("duplicada es"));
							errorCaptura += "Linea " + linea + " : clave "+ mg + '\n';
						}else {
							errorCaptura += "Linea " + linea + " : "+ e.getMessage() + '\n';
						}
					}				
				return createIn;
			}
		}
		
		public java.sql.Date ConvertToDate(String fechaDesde) throws ParseException{
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	        java.util.Date parsed = format.parse(fechaDesde);
	        java.sql.Date fecha = new java.sql.Date(parsed.getTime());        
	        return fecha;
		}
		
		public String getFindCurp() {
			return findCurp;
		}
		public void setFindCurp(String findCurp) {
			this.findCurp = findCurp;
		}
		public String getFindNombre() {
			return findNombre;
		}
		public void setFindNombre(String findNombre) {
			this.findNombre = findNombre;
		}
		public String getFindAPaterno() {
			return findAPaterno;
		}
		public void setFindAPaterno(String findAPaterno) {
			this.findAPaterno = findAPaterno;
		}
		public String getFindAMaterno() {
			return findAMaterno;
		}
		public void setFindAMaterno(String findAMaterno) {
			this.findAMaterno = findAMaterno;
		}
		public String getFindNombreActPor() {
			return findNombreActPor;
		}
		public void setFindNombreActPor(String findNombreActPor) {
			this.findNombreActPor = findNombreActPor;
		}
		public String getFindFechaAct() {
			return findFechaAct;
		}
		public void setFindFechaAct(String findFechaAct) {
			this.findFechaAct = findFechaAct;
		}	
		 
		public void findCandidateBy() {
	    	 List<AdmonUsuariosRolesV1Dto> listAdmonUsuariosRolesV1Dto = admonUsuariosLocal.findCandidateBy(this.getFindCurp(), this.getFindNombre(), this.getFindAPaterno(), this.getFindAMaterno(), this.getFindNombreActPor(), this.getFindFechaAct()); 
			 Iterator<AdmonUsuariosRolesV1Dto> itertAAdmonUsuariosRolesV1Dto = listAdmonUsuariosRolesV1Dto.iterator();
			 listAdmonUsuarios = new ArrayList<AdmonUsuarios>();
			 while(itertAAdmonUsuariosRolesV1Dto.hasNext()) {
				AdmonUsuariosRolesV1Dto admonUsuariosRolesV1Dto = itertAAdmonUsuariosRolesV1Dto.next(); 
				AdmonUsuarios admonUsuarios = new AdmonUsuarios();
				
				System.out.println("Entra admonUsuarios");
				admonUsuarios.setNumero(admonUsuariosRolesV1Dto.getNumeroUsuario());
				System.out.println("admonCandidatosV1.getNumeroRol()");
				admonUsuarios.setNumeroRol(admonUsuariosRolesV1Dto.getNumeroRol());
				admonUsuarios.setNombre(admonUsuariosRolesV1Dto.getNombreUsuario());
				admonUsuarios.setApellidoPaterno(admonUsuariosRolesV1Dto.getApellidoPaterno());
				admonUsuarios.setApellidoMaterno(admonUsuariosRolesV1Dto.getApellidoMaterno());
				admonUsuarios.setCorreoElectronico(admonUsuariosRolesV1Dto.getCorreoElectronico());
				admonUsuarios.setFechaEfectivaDesde(utilitariosLocal.toUtilDate(admonUsuariosRolesV1Dto.getFedAu()));
				if(Utilitarios.endOfTime.equals(admonUsuariosRolesV1Dto.getFehAu())) {
					admonUsuarios.setFechaEfectivaHasta(null);	
				}else {
					admonUsuarios.setFechaEfectivaHasta(utilitariosLocal.toUtilDate(admonUsuariosRolesV1Dto.getFehAu()));	
				}
				
				admonUsuarios.setCurp(admonUsuariosRolesV1Dto.getCurp());
				admonUsuarios.setEstado(admonUsuariosRolesV1Dto.getEstado());
				admonUsuarios.setSedeHospital(admonUsuariosRolesV1Dto.getSedeHospital());
				admonUsuarios.setNumeroRol(admonUsuariosRolesV1Dto.getNumeroRol());

				if(admonUsuariosRolesV1Dto.getNombreActPor() != null)
					admonUsuarios.setActualizadoPorName(admonUsuariosRolesV1Dto.getNombreActPor());
				else
					admonUsuarios.setActualizadoPorName("No disponible");

				admonUsuarios.setFechaActualizacion(admonUsuariosRolesV1Dto.getFechaActualizacion());
				admonUsuarios.setFechaActString(admonUsuariosRolesV1Dto.getFechaActualizacion());				
				System.out.println("admonCandidatosV1.getCorreoElectronico()");
				System.out.println("admonCandidatosV1.getCurp()");
				System.out.println("Sale admonCandidatos");
				listAdmonUsuarios.add(admonUsuarios);
	    
			}
	    }
		 
		 public void clearFiltroForm(){
				setFindCurp("");
				setFindNombre("");
				setFindAPaterno("");
				setFindAMaterno("");
				setFindNombreActPor("");
				setFindFechaAct("");
				refreshEntity();
		    }

		 public boolean isEmail(String email) {
				Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		        boolean result = false;
		        Matcher mather = pattern.matcher(email);
		 
		        if (mather.find() == true) 
		        	result = true;
		        
		        return result;
			}
		 
}

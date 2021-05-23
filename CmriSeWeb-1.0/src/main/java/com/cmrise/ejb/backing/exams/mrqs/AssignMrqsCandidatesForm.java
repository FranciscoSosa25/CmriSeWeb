
package com.cmrise.ejb.backing.exams.mrqs;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonUsuariosRolesV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
import com.cmrise.ejb.services.admin.AdmonUsuariosLocal;
import com.cmrise.ejb.services.admin.AdmonUsuariosRolesLocal;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@ManagedBean
@ViewScoped
public class AssignMrqsCandidatesForm {

	private java.sql.Date fechaEfectivaDesde;
	private java.sql.Date fechaEfectivaHasta;
	private Date fechaED;
	private Date fechaEH;
	private long numeroRol;
	private long numeroUsuario;
    private String nombre; 
    private String apellidoPaterno; 
    private String apellidoMaterno; 
    private String correoElectronico;
    private String contrasenia;
    private String curp; 
    private String estado; 
    private String sedeHospital; 
	private long numeroMrqsExamen; 
	private String ErroresCaptura;
	///Variables para el filtro de candidatos en el examen
	private String findCurp; 
	private String findNombreUsuario; 
	private String findaMaterno;
	private String findaPaterno;
	private String findActPor;
	private String findFechaAct;
	///Variables para el filtro de candidatos no en el examen
	private String findCurpNE; 
	private String findNombreUsuarioNE; 
	private String findaMaternoNE;
	private String findaPaternoNE;
	private String findActPorNE;
	private String findFechaActNE;
	private boolean createIn;
	private String tipoExamenAsig;
	private String pantallaVolver;
	private List<AdmonUsuariosRolesV1> listAdmonUsuariosRolesV1 = new ArrayList<AdmonUsuariosRolesV1>();
	private List<AdmonUsuariosRolesV1> selectedsAdmonUsuariosRolesV1 = new ArrayList<AdmonUsuariosRolesV1>(); 
    private List<CandExamenesV2> listCandExamenesV2 = new ArrayList<CandExamenesV2>(); 
    private List<CandExamenesV2> selectedCandExamenesV2 = new ArrayList<CandExamenesV2>();     
	private CandExamenesV2 candExamenesV2ForAction = new CandExamenesV2(); 
    
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject
	AdmonUsuariosRolesLocal admonUsuariosRolesLocal;
	
	@Inject 
	AdmonUsuariosLocal admonUsuariosLocal; 
	
	@Inject 
	CandExamenesLocal candExamenesLocal; 
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin;
	
	@PostConstruct
	public void init() {
		System.out.println("Comienza AssignMrqsCandidatesForm init()");
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Object objNumeroMrqsExamen = session.getAttribute("NumeroMrqsExamenSV");
		this.numeroMrqsExamen = utilitariosLocal.objToLong(objNumeroMrqsExamen); 
		Object objTipoExamenAsig = session.getAttribute("tipoExamen");
		setTipoExamenAsig(objTipoExamenAsig.toString().trim());
		Object objNombrePantalla = session.getAttribute("nombrePantalla");
		setPantallaVolver(objNombrePantalla.toString().trim());
		
		refreshEntity(); 
	    System.out.println("Finaliza AssignMrqsCandidatesForm init()");
	 }
	 
	private void refreshEntity() {
		listCandExamenesV2 = candExamenesLocal.findByExamen(this.getNumeroMrqsExamen(), getTipoExamenAsig() ); 
		listAdmonUsuariosRolesV1 = admonUsuariosRolesLocal.findWithFilterExam(this.getNumeroMrqsExamen(), getTipoExamenAsig() ); 		
	}
	
	public String cancel() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		return pantallaVolver; 
	}
	
	public String addMRQsCandidates() {
		for(AdmonUsuariosRolesV1 admonUsuariosRolesV1:selectedsAdmonUsuariosRolesV1) {
			CandExamenesDto candExamenesDto = new CandExamenesDto(); 
			candExamenesDto.setNumeroUsuario(admonUsuariosRolesV1.getNumeroUsuario());
			candExamenesDto.setNumeroExamen(this.getNumeroMrqsExamen());
			candExamenesDto.setTipo(getTipoExamenAsig());
			candExamenesDto.setActualizadoPor(userLogin.getNumeroUsuario());
			candExamenesDto.setEstatus(Utilitarios.EE_ASIGNADO);
			candExamenesLocal.insert(candExamenesDto); 
		}
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroMrqsExamenSV", this.getNumeroMrqsExamen());
		return "Assign-MRQs-Candidates"; 
	}
	
	public String deleteMRQsCandidates() {
		for(CandExamenesV2 candExamenesV2:selectedCandExamenesV2) {
			candExamenesLocal.delete(candExamenesV2.getNumero()); 
			refreshEntity();
		}
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroMrqsExamenSV", this.getNumeroMrqsExamen());
		return "Assign-MRQs-Candidates"; 
	}

	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}
	public Date getfechaED() {
		return fechaED;
	}
	public void setfechaED(Date fechaED) {
		this.fechaED = fechaED;
	}
	public Date getfechaEH() {
		return fechaEH;
	}
	public void setfechaEH(Date fechaEH) {
		this.fechaEH = fechaEH;
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
	public String getFindCurpNE() {
		return findCurpNE;
	}
	public void setFindCurpNE(String findCurpNE) {
		this.findCurpNE = findCurpNE;
	}
	public String getFindNombreUsuarioNE() {
		return findNombreUsuarioNE;
	}
	public void setFindNombreUsuarioNE(String findNombreUsuarioNE) {
		this.findNombreUsuarioNE = findNombreUsuarioNE;
	}
	public String getFindaMaternoNE() {
		return findaMaternoNE;
	}
	public void setFindaMaternoNE(String findaMaternoNE) {
		this.findaMaternoNE = findaMaternoNE;
	}
	public String getFindaPaternoNE() {
		return findaPaternoNE;
	}
	public void setFindaPaternoNE(String findaPaternoNE) {
		this.findaPaternoNE = findaPaternoNE;
	}
	public String getFindActPorNE() {
		return findActPorNE;
	}
	public void setFindActPorNE(String findActPorNE) {
		this.findActPorNE = findActPorNE;
	}
	public String getFindFechaActNE() {
		return findFechaActNE;
	}
	public void setFindFechaActNE(String findFechaActNE) {
		this.findFechaActNE = findFechaActNE;
	}
	public long getNumeroMrqsExamen() {
		System.out.println("numero examen" + numeroMrqsExamen);
		return numeroMrqsExamen;
	}
	public void setNumeroMrqsExamen(long numeroMrqsExamen) {
		this.numeroMrqsExamen = numeroMrqsExamen;
	}
	public List<CandExamenesV2> getListCandExamenesV2() {
		return listCandExamenesV2;
	}
	public void setListCandExamenesV2(List<CandExamenesV2> listCandExamenesV2) {
		this.listCandExamenesV2 = listCandExamenesV2;
	}
	public List<CandExamenesV2> getSelectedCandExamenesV2() {
		return selectedCandExamenesV2;
	}
	public void setSelectedCandExamenesV2(List<CandExamenesV2> selectedCandExamenesV2) {
		this.selectedCandExamenesV2 = selectedCandExamenesV2;
	}
	public List<AdmonUsuariosRolesV1> getSelectedsAdmonUsuariosRolesV1() {
		return selectedsAdmonUsuariosRolesV1;
	}
	public void setSelectedsAdmonUsuariosRolesV1(List<AdmonUsuariosRolesV1> selectedsAdmonUsuariosRolesV1) {
		this.selectedsAdmonUsuariosRolesV1 = selectedsAdmonUsuariosRolesV1;
	}
	public CandExamenesV2 getCandExamenesV2ForAction() {
		return candExamenesV2ForAction;
	}
	public void setCandExamenesV2ForAction(CandExamenesV2 candExamenesV2ForAction) {
		this.candExamenesV2ForAction = candExamenesV2ForAction;
	}
	public List<AdmonUsuariosRolesV1> getListAdmonUsuariosRolesV1() {
		return listAdmonUsuariosRolesV1;
	}
	public void setListAdmonUsuariosRolesV1(List<AdmonUsuariosRolesV1> listAdmonUsuariosRolesV1) {
		this.listAdmonUsuariosRolesV1 = listAdmonUsuariosRolesV1;
	}
	public String getFindCurp() {
		return findCurp;
	}
	public void setFindCurp(String findCurp) {
		this.findCurp = findCurp;
	}
	public String getFindNombreUsuario() {
		return findNombreUsuario;
	}
	public void setFindNombreUsuario(String findNombreUsuario) {
		this.findNombreUsuario = findNombreUsuario;
	}
	public String getFindaMaterno() {
		return findaMaterno;
	}
	public void setFindaMaterno(String findaMaterno) {
		this.findaMaterno = findaMaterno;
	}
	public String getFindaPaterno() {
		return findaPaterno;
	}
	public void setFindaPaterno(String findaPaterno) {
		this.findaPaterno = findaPaterno;
	}
	public String getFindActPor() {
		return findActPor;
	}
	public void setFindActPor(String findActPor) {
		this.findActPor = findActPor;
	}
	public String getFindFechaAct() {
		return findFechaAct;
	}
	public void setFindFechaAct(String findFechaAct) {
		this.findFechaAct = findFechaAct;
	}

	public void selectForAction(CandExamenesV2 pCandExamenesV2) {
		candExamenesV2ForAction.setNumero(pCandExamenesV2.getNumero());
	}
	
	public void delete() {
		candExamenesLocal.delete(candExamenesV2ForAction.getNumero());
		refreshEntity(); 
	}
	
	public void create() {
		
		if(fechaED != null) 
			fechaEfectivaDesde = new java.sql.Date(fechaED.getTime());
		else
			fechaEfectivaDesde = new java.sql.Date(fechaED.getTime());
		
		if(fechaEH != null) 
			fechaEfectivaHasta = new java.sql.Date(fechaEH.getTime());
		else 			
			fechaEfectivaHasta = Utilitarios.endOfTime;
		
		if(isEmail(correoElectronico)==false)
			ErroresCaptura += "Correo invalido. " + '\n';
		
		if(ErroresCaptura=="" || ErroresCaptura==null)
			createIn = insertOnetoOneCandidate(getCurp(), getNombre(), getApellidoPaterno(), getApellidoMaterno(), getCorreoElectronico(), getContrasenia(), getEstado(), getSedeHospital(), fechaEfectivaDesde, fechaEfectivaHasta, getNumeroRol(), 0);
	    
	    if(ErroresCaptura!= ""){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Errores en el archivo: " + '\n', ErroresCaptura );
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        ErroresCaptura = "";
		}else {
	        FacesMessage msg = new FacesMessage(" El candidato", " registrado y asignado correctamente");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		PrimeFaces.current().ajax().addCallbackParam("createIn", createIn);
		refreshEntity();
	}
	
	public void handleFileUpload(FileUploadEvent event) throws IOException, ParseException {
		ErroresCaptura = "";
		int lineaAct = 0;
		String validaValores;
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
		    		validaValores += "Linea "+lineaAct+" : 'CURP' no tiene 18 caracteres. " + '\n';
		    	}
		    	if(nombre == null || nombre.length() == 0) {
		    		validaValores += "Linea "+lineaAct+" : 'Nombre' vacio. " + '\n';
		    	}
		    	if(apellidoPaterno == null || apellidoPaterno.length() == 0) {
		    		validaValores += "Linea "+lineaAct+" : 'Apellido Paterno' vacio. " + '\n';
		    	}
		    	if(apellidoMaterno == null || apellidoMaterno.length()==0) {
		    		validaValores += "Linea "+lineaAct+" : 'Apellido Materno' vacio. " + '\n';
		    	}
		    	if(correoElectronico == null || correoElectronico.length() == 0) {
		    		validaValores += "Linea "+lineaAct+" : 'Correo' vacio. " + '\n';
		    	}else {
		    		if(isEmail(correoElectronico)==false)
		    			validaValores += "Linea "+lineaAct+" : Correo invalido. " + '\n';
		    	}		    	
		    	if(contrasenia == null || contrasenia.length() == 0) {
		    		validaValores += "Linea "+lineaAct+" : 'Contraseña' vacio. " + '\n';
		    	}		    	
		    	if(sedeHospital == null || sedeHospital.length()==0) {
		    		validaValores += "Linea "+lineaAct+" : 'Sede Hospitalaria' vacio. " + '\n';
		    	}
		    	
		    	try {
		    		if(fechaD == null ) 
			    		validaValores += "Linea "+lineaAct+" : 'Fecha Desde' vacio. " + '\n';
		    		else
		    			fechaEfectivaDesde = ConvertToDate(fechaD);
		    	}catch(Exception e) {
		    		validaValores += "Linea "+lineaAct+" : 'Fecha Desde' formato incorrecto. " + '\n';
		    	}
		    	
		    	try {
		    		fechaEfectivaHasta = ConvertToDate(fechaH);
		    	}catch(Exception e){ 
		    		fechaEfectivaHasta = null;
		    	}		    		    		    	
		    	
		    	if(validaValores == null || validaValores.length()==0)
		    		insertOnetoOneCandidate(curp, nombre, apellidoPaterno, apellidoMaterno, correoElectronico,contrasenia, estado, sedeHospital, fechaEfectivaDesde, fechaEfectivaHasta, 1, lineaAct);
		    	else
		    		ErroresCaptura += validaValores;
			}
         }
		
		reader.close();
		
		if( ErroresCaptura != ""){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Errores en el archivo: " + '\n', ErroresCaptura );
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        ErroresCaptura = "";
		}else {
	        FacesMessage msg = new FacesMessage("Registros guardados exitosamente.");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public boolean insertOnetoOneCandidate(String curp, String nombre, String apPaterno, String apMaterno, String email,String contrasenia, 
											String estado, String sedeH, java.sql.Date fechaEfDesde, java.sql.Date fechaEfHasta, long nRol, int linea){
					
		 AdmonUsuariosDto admonUsuariosDto = new AdmonUsuariosDto();
		 createIn = false; 
		 
		 try{
			 
			 admonUsuariosDto.setCurp(curp);
			 admonUsuariosDto.setNombre(nombre);
			 admonUsuariosDto.setApellidoPaterno(apPaterno);
			 admonUsuariosDto.setApellidoMaterno(apMaterno);
			 admonUsuariosDto.setContrasenia(contrasenia);
			 admonUsuariosDto.setCorreoElectronico(email);
			 admonUsuariosDto.setEstado(estado);
			 admonUsuariosDto.setSedeHospital(sedeH);
			 admonUsuariosDto.setFechaEfectivaDesde(fechaEfDesde);
			 
			 if(fechaEfHasta==null) 
				 fechaEfHasta = Utilitarios.endOfTime;
			 
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
				if(fechaEfHasta!=null) 
					admonUsuariosRolesDto.setFechaEfectivaHasta(utilitariosLocal.toSqlDate(fechaEfHasta));	
				else 
					admonUsuariosRolesDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
				
				admonUsuariosRolesLocal.insert(admonUsuariosRolesDto);
			    /***********************************************************************/
				CandExamenesDto candExamenesDto = new CandExamenesDto(); 
				candExamenesDto.setNumeroUsuario(admonUsuariosV2Dto.getNumero());
				candExamenesDto.setNumeroExamen(this.getNumeroMrqsExamen());
				candExamenesDto.setTipo(getTipoExamenAsig());
				candExamenesDto.setEstatus(Utilitarios.EE_ASIGNADO);
				candExamenesLocal.insert(candExamenesDto); 
				
				/***********************************************************************/
				createIn = true; 
				return createIn;
				
		} catch (Exception  ex) {
			Throwable e;
			String mg = "";
			 if( (e = ex.getCause()) != null) {
					while( e.getCause() != null ) {e = e.getCause();}				
					if(e.getMessage().contains("clave duplicada") ) {
						mg = e.getMessage().substring(e.getMessage().indexOf("duplicada es"));
						ErroresCaptura += "Linea " + linea + " : clave "+ mg + '\n';
					}else {
						ErroresCaptura += "Linea " + linea + " : "+ e.getMessage() + '\n';
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
	
	public String getNombreUsuario(String numeroUsuario) {
		long numUsu = 0;
		String nombreUsuMod = "No disponible";
		AdmonUsuariosDto usrQueModifico;
				
		if(StringUtils.isNotEmpty(numeroUsuario) && numeroUsuario != "" && numeroUsuario != null && numeroUsuario != "-1")
			numUsu = Long.parseLong(numeroUsuario);
		
		if(numUsu > 0)
		{
			usrQueModifico = admonUsuariosLocal.selectUsuario(numUsu);
			nombreUsuMod = usrQueModifico.getNombre() + " " + usrQueModifico.getApellidoPaterno() + " " + usrQueModifico.getApellidoMaterno();
		}
		return nombreUsuMod;
	}
	
	public void findCandidateByExam() {
		listCandExamenesV2 = candExamenesLocal.findCandidateByExam(this.findCurp, this.findNombreUsuario, this.findaPaterno, 
				this.findaMaterno, this.findActPor, this.findFechaAct, this.getNumeroMrqsExamen(), getTipoExamenAsig() ); 		
	}
	
	public void findCandidateNotExam() {		
		listAdmonUsuariosRolesV1 = admonUsuariosRolesLocal.findCandidateNotExam(this.findCurpNE, this.findNombreUsuarioNE, this.findaPaternoNE, 
				this.findaMaternoNE, this.findActPorNE, this.findFechaActNE,this.getNumeroMrqsExamen(), getTipoExamenAsig() ); 		
	}
	
	public void clearFormCE(){
		setFindCurp("");
		setFindNombreUsuario("");
		setFindaPaterno("");
		setFindaMaterno("");
		setFindActPor("");
		setFindFechaAct("");
		findCandidateByExam();
    }
	
	public void clearFormCNotE(){
		setFindCurpNE("");
		setFindNombreUsuarioNE("");
		setFindaPaternoNE("");
		setFindaMaternoNE("");
		setFindActPorNE("");
		setFindFechaActNE("");
		findCandidateNotExam();
    }
	
	public boolean isEmail(String email) {
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        boolean result = false;
        Matcher mather = pattern.matcher(email);
 
        if (mather.find() == true) 
        	result = true;
        
        return result;
	}

	public String getTipoExamenAsig() {
		System.out.println("Tipo examen" + tipoExamenAsig);
		return tipoExamenAsig;
	}

	public void setTipoExamenAsig(String tipoExamenAsig) {
		this.tipoExamenAsig = tipoExamenAsig;
	}

	public String getPantallaVolver() {
		return pantallaVolver;
	}

	public void setPantallaVolver(String pantallaVolver) {
		this.pantallaVolver = pantallaVolver;
	}

}


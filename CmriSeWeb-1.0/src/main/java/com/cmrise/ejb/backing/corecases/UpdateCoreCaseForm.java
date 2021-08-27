package com.cmrise.ejb.backing.corecases;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

import com.cmrise.ejb.helpers.GuestPreferences;
import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.admin.AdmonMateriaHdr;
import com.cmrise.ejb.model.admin.AdmonSubMateria;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.corecases.img.CcImagenes;
import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.admin.AdmonMateriaHdrLocal;
import com.cmrise.ejb.services.admin.AdmonSubMateriaLocal;
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasHdrLocal;
import com.cmrise.ejb.services.corecases.img.CcImagenesGrpLocal;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.utils.Utilitarios;


@ManagedBean
@ViewScoped
public class UpdateCoreCaseForm {

	private CcHdrV1 ccHdrV1 = new CcHdrV1(); 
	private CcHdrDto ccHdrDto;
	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<AdmonMateriaHdr> materiasHdr = new ArrayList<AdmonMateriaHdr>();
	private List<AdmonSubMateria> subMaterias = new ArrayList<AdmonSubMateria>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>(); 
	private List<SelectItem> selectMateriasHdr = new ArrayList<SelectItem>();  
	private List<SelectItem> selectSubMaterias = new ArrayList<SelectItem>(); 
	
   private long numeroCcHdr;
	private String estatus;
	private Timestamp fechaActualizacion;
	private CcPreguntasHdrV1 ccPreguntasHdrV1ForAction = new CcPreguntasHdrV1();

	private UploadedFiles presentationFiles;
	private CcImagenesGrp presentCcImagenesGrp = new CcImagenesGrp();
	private CcImagenesGrp editPresentCcImagenesGrp = new CcImagenesGrp();
	
	private List<CcImagenesGrp> listPresentCcImagenesGrp = new ArrayList<CcImagenesGrp>();
	private long numeroFta;
	
	private CcImagenes editCCImagenes = new CcImagenes();
	
	

   private List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
   @Inject 
   CcHdrLocal ccHdrLocal; 
   
   @Inject 
   CcPreguntasHdrLocal ccPreguntasHdrLocal;
   
	
   @Inject 
	AdmonExamenHdrLocal admonExamenHdrLocal; 
	
	@Inject 
	AdmonMateriaHdrLocal admonMateriaHdrLocal; 
	
	@Inject 
	AdmonSubMateriaLocal admonSubMateriaLocal; 
	
	@Inject
	CcImagenesGrpLocal ccImagenesGrpLocal;
	
   
   @ManagedProperty(value="#{guestPreferences}")
   GuestPreferences guestPreferences; 
	
     @PostConstruct
	 public void init() {
		 System.out.println("Entra "+this.getClass()+" init()");
		 FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     Object obNumeroCcHdr= session.getAttribute("NumeroCcHdrSV");
	     if(null!=obNumeroCcHdr) {
	    	 if(obNumeroCcHdr instanceof Long) {
	    		 long numeroCcHdr = (Long)obNumeroCcHdr;
	    		 System.out.println("numeroCcHdr:"+numeroCcHdr);
	    		 this.numeroCcHdr = numeroCcHdr; 
	    	 }else {
	    		 System.out.println("numeroCcHdr instanceof Long:false");
	    	 }
	     }else {
	    	 System.out.println("(null!=numeroCcHdr:false");
	    	 return;
	     }	
	     
		 refreshEntity();
		 System.out.println("Sale "+this.getClass()+" init() Sale init");
	 }		 
	 
   
  private void refreshEntity() {
	ccHdrV1 = ccHdrLocal.findByNumeroObjMod(this.numeroCcHdr);
	
	listPresentCcImagenesGrp = ccImagenesGrpLocal.findByCcHDR(this.numeroCcHdr, Utilitarios.INTRODUCCION);
	

	listCcPreguntasHdrV1 = ccHdrV1.getListCcPreguntasHdrV1();
	examenesHdr = admonExamenHdrLocal.findByTipo(Utilitarios.CORE_CASES); 
	 selectExamenesHdr = new ArrayList<SelectItem>(); 
	 for(AdmonExamenHdr i:examenesHdr) {
		 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
		 selectExamenesHdr.add(selectItem); 
	 }
/**	 materiasHdr = admonMateriaHdrLocal.findByNumeroAdmonExamen(ccHdrV1.getAdmonExamen()); 
		selectMateriasHdr = new ArrayList<SelectItem>();  
		for(AdmonMateriaHdr i:materiasHdr) {
			 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			 selectMateriasHdr.add(selectItem); 
		}
		subMaterias = admonSubMateriaLocal.findByNumeroMateria(ccHdrV1.getAdmonExamen()); 
		selectSubMaterias = new ArrayList<SelectItem>();  
			for(AdmonSubMateria i:subMaterias) {
				 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				 selectSubMaterias.add(selectItem); 
			}
			**/
    onAdmonExamenChange(); 
    System.out.println("Entr onAdmonExmaneChange init");
    onAdmonMateriaChange(); 
    System.out.println("Entr onAdmonMateriaChange init");
  }


  public String createCoreQuestion(){
	  String retval = null; 
	  retval ="Crear-Pregunta-CoreCase"; 
	  return retval; 
  }
  
  public String updatePregunta(CcPreguntasHdrV1 pCcPreguntasHdrV1) {
	  FacesContext context = FacesContext.getCurrentInstance(); 
	  HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	  long lNumeroCcHdr = pCcPreguntasHdrV1.getNumeroCcHdr();
	  long lNumeroPreguntaHdr = pCcPreguntasHdrV1.getNumero();
	  session.setAttribute("NumeroCcHdrSV", lNumeroCcHdr);
      session.setAttribute("NumeroCcPreguntaHdrSV", lNumeroPreguntaHdr);
	  return  "Actualizar-Pregunta-Fta-CoreCase";
  }  
  public void selectForAction(CcPreguntasHdrV1 pCcPreguntasHdrV1) {
	  ccPreguntasHdrV1ForAction.setNumero(pCcPreguntasHdrV1.getNumero());
	}
	
  public void deletePregunta(CcPreguntasHdrV1 pCcPreguntasHdrV1) {
System.out.println("Entra deletePregunta");
         boolean deleteIn = false; 
	    // System.out.println("la pregunta es:"+pCcPreguntasHdrV1.getNumero());
//	     long lNumeroPreguntaHdr = pCcPreguntasHdrV1.getNumero();
         try {
         ccImagenesGrpLocal.deleteByCcFTA(pCcPreguntasHdrV1.getNumero(), Utilitarios.INTRODUCCION);
         }catch (RuntimeException e) {
			// TODO: handle exception
		}
		 ccHdrLocal.deletePregunta(pCcPreguntasHdrV1.getNumero());
		 System.out.println("se elimino pregunta:"+ccPreguntasHdrV1ForAction.getNumero());
	//	System.out.println("se eliminara la pregunta:"+pCcPreguntasHdrV1.getNumero());
		 refreshEntity();
		 deleteIn = true;
		 PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
		 System.out.println("Sale "+this.getClass()+" delete()");
		 
  } 
  
  public void actualizar () {
	  System.out.println("Entra actualizar CC");
	  CcHdrDto ccHdrDto = new CcHdrDto();
	  ccHdrDto.setNumero(ccHdrV1.getNumero());
	  ccHdrDto.setEstatus(ccHdrV1.getEstatus());
	  ccHdrDto.setAdmonExamen(ccHdrV1.getAdmonExamen());
	  System.out.println("ccHdrV1.getAdmonMateria():"+ccHdrV1.getAdmonExamen());
	  ccHdrDto.setAdmonMateria(ccHdrV1.getAdmonMateria());
	  System.out.println("ccHdrV1.getAdmonMateria():"+ccHdrV1.getAdmonMateria());
	  ccHdrDto.setAdmonSubmateria(ccHdrV1.getAdmonSubMateria());
	  System.out.println("ccHdrV1.getAdmonSubMateria():"+ccHdrV1.getAdmonSubMateria());
	  ccHdrDto.setHistorialClinico(ccHdrV1.getHistorialClinico());
	  System.out.println("ccHdrV1.getHistorialClinico():"+ccHdrV1.getHistorialClinico());
	  ccHdrDto.setEtiquetas(ccHdrV1.getEtiquetas());
	  System.out.println("ccHdrV1.getEtiquetas():"+ccHdrV1.getEtiquetas());
	  ccHdrDto.setNota(ccHdrV1.getNota());
	  System.out.println("ccHdrV1.getNota():"+ccHdrV1.getNota());
		 
	  ccHdrLocal.update(ccHdrV1.getNumero(), ccHdrDto);
	 
	  refreshEntity();
	  FacesContext context = FacesContext.getCurrentInstance();
      context.addMessage(null, new FacesMessage("Se actualizaron los datos correctamente", "Actualizacion correcta"));
	  System.out.println("Sale Actualizar");
  }
    private boolean validar() {
    	ccHdrV1 = ccHdrLocal.findByNumeroObjMod(this.numeroCcHdr);
		listCcPreguntasHdrV1 = ccHdrV1.getListCcPreguntasHdrV1();
		if(listCcPreguntasHdrV1.isEmpty())
			return false;
		return true;
    	
    }
	public String saveAndPreview() {
		if(validar()==false) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, Utilitarios.ALERTA_PREGUNTAS_VACIO, null));
			return "#"; 
		}			
		System.out.println("Entra SaveAndPreview");
		 actualizar(); 
		 getGuestPreferences().setTheme("deep-purple");
	     FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     session.setAttribute("NumeroCcHdrSV", this.getNumeroCcHdr());	
	     System.out.println("Sale saveAndPreview()");
	    return "CoreCase-Preview-Full"; 
	}
	public String preview() {			
		 actualizar(); 
	     FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     session.setAttribute("NumeroCcHdrSV", this.getNumeroCcHdr());	
	     System.out.println("Sale saveAndPreview()");
	    return "CoreCase-Preview-Full"; 
	}
	 public void onAdmonExamenChange() {
		 System.out.println("Entrar onAdmonExamenChange");
		 System.out.println("ccHdrV1.getAdmonMateria():"+ccHdrV1.getAdmonExamen());
			if(0!=ccHdrV1.getAdmonExamen()) {
				materiasHdr = admonMateriaHdrLocal.findByNumeroAdmonExamen(ccHdrV1.getAdmonExamen()); 
				selectMateriasHdr = new ArrayList<SelectItem>();  
				for(AdmonMateriaHdr i:materiasHdr) {
					 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
					 selectMateriasHdr.add(selectItem); 
				}
			}

			 System.out.println("Sale onAdmonExamenChange");
		}
	 public void onAdmonMateriaChange() {
		 System.out.println("Entrar onAdmonExamenChange");
		 System.out.println("ccHdrV1.getAdmonMateria():"+ccHdrV1.getAdmonMateria());
			if(0!=ccHdrV1.getAdmonMateria()) {
				subMaterias = admonSubMateriaLocal.findByNumeroMateria(ccHdrV1.getAdmonMateria()); 
				selectSubMaterias = new ArrayList<SelectItem>(); 
				for(AdmonSubMateria i:subMaterias) {
					System.out.println("*");
					SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
					selectSubMaterias.add(selectItem); 
				}
			}
		}
	 
	 public void updateCCImageGroup(CcImagenesGrp ccImagenesGrp) {
		 
	 }
	 
	 public void deleteCCImageGroup(CcImagenesGrp ccImagenesGrp) {
		 this.selCcImagenesGrpDelete = ccImagenesGrp;
	 }
	 public void deleteCCImageGroup() {
		 FacesContext context = FacesContext.getCurrentInstance();
		 try {			
			 if(this.ccImagenesGrpLocal.deleteGroup(selCcImagenesGrpDelete)) {
				 context.addMessage(null, new FacesMessage("Deleted successfully", "Actualizacion correcta"));
				 listPresentCcImagenesGrp = ccImagenesGrpLocal.findByCcHDR(this.numeroCcHdr, Utilitarios.INTRODUCCION);
			 }else {
				 context.addMessage(null, new FacesMessage("Failed to delete .dcm image group.", "Actualizacion correcta"));
			 }
		 }catch (RuntimeException e) {
			
		      context.addMessage(null, new FacesMessage("Error while delete .dcm image group.", "Actualizacion correcta"));
		}
		 
	 }
	 
	 private CcImagenesGrp selCcImagenesGrpDelete;
	 private CcImagenes selCcImagenesToDelete;
	
	public void onEditCCImageGroup(CcImagenesGrp ccImageGroup) {
		this.editPresentCcImagenesGrp = ccImageGroup;
		 
	 }
	
	 public void deleteImageFromGroup(CcImagenesGrp ccImageGroup, CcImagenes imagenes) {
		 this.selCcImagenesGrpDelete = ccImageGroup;
		 this.selCcImagenesToDelete = imagenes;
		 
	 }
	 
	 public void deleteImageFromGroup() {
		this.editPresentCcImagenesGrp.getListCcImagenes().remove(selCcImagenesToDelete);
		 ccImagenesGrpLocal.deleteGroupImage(selCcImagenesGrpDelete, selCcImagenesToDelete);
		  FacesContext context = FacesContext.getCurrentInstance();
	      context.addMessage(null, new FacesMessage("Dicom image deleted successfully.", "Actualizacion correcta"));
		  System.out.println("Sale Actualizar");
		 refreshEntity();
	 }
	 
	 public void uploadMultiple() {
		 System.out.println("Entra uploadMultiple");
			if (this.presentationFiles != null) {
				if (this.presentationFiles.getSize() > 0) {

					CcImagenesGrp ccPresentaciones = new CcImagenesGrp();
					ccPresentaciones.setTituloSuperior(this.presentCcImagenesGrp.getTituloSuperior());
					ccPresentaciones.setModality(this.presentCcImagenesGrp.getModality());
					List<CcImagenes> lListCcPresentaciones = new ArrayList<CcImagenes>();

					for (UploadedFile f : this.presentationFiles.getFiles()) {

						byte[] byteContent = f.getContent();

						System.out.println(f.getFileName());
						CcImagenes presentacionImagen = new CcImagenes();

						presentacionImagen.setNombreImagen(f.getFileName());
						presentacionImagen.setImagenContent(byteContent);
						lListCcPresentaciones.add(presentacionImagen);
					}

					ccPresentaciones.setListCcImagenes(lListCcPresentaciones);
					this.listPresentCcImagenesGrp.add(ccPresentaciones);
				}
			}
			
			
			System.out.println("Sale uploadMultiple");
			  try {
				  for (CcImagenesGrp ccImagenesGrp : listPresentCcImagenesGrp) {
						ccImagenesGrp.setTipo(Utilitarios.CORE_CASES);
						ccImagenesGrp.setSeccion(Utilitarios.INTRODUCCION);
						ccImagenesGrp.setCcHDR(true);				
						updateImagenesGrp(ccHdrV1.getNumero(), ccImagenesGrp);
					}
			  }catch (Exception e) {
				// TODO: handle exception
			  }
			  listPresentCcImagenesGrp = ccImagenesGrpLocal.findByCcHDR(this.numeroCcHdr, Utilitarios.INTRODUCCION);
			  FacesContext context = FacesContext.getCurrentInstance();
		      context.addMessage(null, new FacesMessage("Se actualizaron los datos correctamente", "Actualizacion correcta"));
			  System.out.println("Sale Actualizar");
			
	  
		 
	 }
	 
	 public void uploadMultiple(CcImagenesGrp pCcImagenesGrp) {
		 pCcImagenesGrp = this.editPresentCcImagenesGrp;
		 pCcImagenesGrp.getListCcImagenes().clear();
		 System.out.println("Entra uploadMultiple");
			if (this.presentationFiles != null) {
				if (this.presentationFiles.getSize() > 0) {
					List<CcImagenes> lListCcPresentaciones = new ArrayList<CcImagenes>();
					for (UploadedFile f : this.presentationFiles.getFiles()) {
						byte[] byteContent = f.getContent();

						System.out.println(f.getFileName());
						CcImagenes presentacionImagen = new CcImagenes();

						presentacionImagen.setNombreImagen(f.getFileName());
						presentacionImagen.setImagenContent(byteContent);
						lListCcPresentaciones.add(presentacionImagen);
					}

					pCcImagenesGrp.setListCcImagenes(lListCcPresentaciones);
					
				}
			}
			
			  ccImagenesGrpLocal.update(ccHdrV1.getNumero(), pCcImagenesGrp);
			  refreshEntity();
			  FacesContext context = FacesContext.getCurrentInstance();
		      context.addMessage(null, new FacesMessage("Update DICOM image group successfully.", "Actualizacion correcta"));
			  System.out.println("Sale Actualizar");
			
	  
		 
	 }
	 
		private void updateImagenesGrp(long pNumeroCcHRD, CcImagenesGrp pCcImagenesGrp) {
			if (0 != pCcImagenesGrp.getNumero()) {

			} else {
				insertaImagenesGrp(pNumeroCcHRD, pCcImagenesGrp);
			}
		}

		private void insertaImagenesGrp(long pNumeroCcHRD, CcImagenesGrp pCcImagenesGrp) {
			ccImagenesGrpLocal.insert(pNumeroCcHRD, pCcImagenesGrp);
		}
		
		public void updateCcImagenes(CcImagenes ccImagenes) {
			this.editCCImagenes = ccImagenes;
		}
		
		public void savePolygon() {
			ccImagenesGrpLocal.savePolygon(this.editCCImagenes);
		}

	

public long getNumeroCcHdr() {
	return numeroCcHdr;
 }

 public void setNumeroCcHdr(long numeroCcHdr) {
	this.numeroCcHdr = numeroCcHdr;
 }

public List<CcPreguntasHdrV1> getListCcPreguntasHdrV1() {
	return listCcPreguntasHdrV1;
}


public void setListCcPreguntasHdrV1(List<CcPreguntasHdrV1> listCcPreguntasHdrV1) {
	this.listCcPreguntasHdrV1 = listCcPreguntasHdrV1;
}
public GuestPreferences getGuestPreferences() {
	return guestPreferences;
}

public void setGuestPreferences(GuestPreferences guestPreferences) {
	this.guestPreferences = guestPreferences;
}


public CcHdrV1 getCcHdrV1() {
	return ccHdrV1;
}


public void setCcHdrV1(CcHdrV1 ccHdrV1) {
	this.ccHdrV1 = ccHdrV1;
}

public CcHdrDto getCcHdrDto() {
	return ccHdrDto;
}


public void setCcHdrDto(CcHdrDto ccHdrDto) {
	this.ccHdrDto = ccHdrDto;
}
public String getEstatus() {
	return estatus;
}

public void setEstatus(String estatus) {
	this.estatus = estatus;
}
public Timestamp getFechaActualizacion() {
	return this.fechaActualizacion;
}

public void setFechaActualizacion(Timestamp fechaActualizacion) {
	this.fechaActualizacion = fechaActualizacion;
}
public CcPreguntasHdrV1 getCcPreguntasHdrV1ForAction() {
	return ccPreguntasHdrV1ForAction;
}

public void setCcPreguntasHdrV1ForAction(CcPreguntasHdrV1 ccPreguntasHdrV1ForAction) {
	this.ccPreguntasHdrV1ForAction = ccPreguntasHdrV1ForAction;
} 	
public List<AdmonExamenHdr> getExamenesHdr() {
	return examenesHdr;
}

public void setExamenesHdr(List<AdmonExamenHdr> examenesHdr) {
	this.examenesHdr = examenesHdr;
}

public List<AdmonMateriaHdr> getMateriasHdr() {
	return materiasHdr;
}

public void setMateriasHdr(List<AdmonMateriaHdr> materiasHdr) {
	this.materiasHdr = materiasHdr;
}

public List<AdmonSubMateria> getSubMaterias() {
	return subMaterias;
}

public void setSubMaterias(List<AdmonSubMateria> subMaterias) {
	this.subMaterias = subMaterias;
}

public List<SelectItem> getSelectExamenesHdr() {
	return selectExamenesHdr;
}

public void setSelectExamenesHdr(List<SelectItem> selectExamenesHdr) {
	this.selectExamenesHdr = selectExamenesHdr;
}

public List<SelectItem> getSelectMateriasHdr() {
	return selectMateriasHdr;
}

public void setSelectMateriasHdr(List<SelectItem> selectMateriasHdr) {
	this.selectMateriasHdr = selectMateriasHdr;
}

public List<SelectItem> getSelectSubMaterias() {
	return selectSubMaterias;
}

public void setSelectSubMaterias(List<SelectItem> selectSubMaterias) {
	this.selectSubMaterias = selectSubMaterias;
}



public UploadedFiles getPresentationFiles() {
	return presentationFiles;
}


public void setPresentationFiles(UploadedFiles presentationFiles) {
	System.out.println("Upload multiple ");
	this.presentationFiles = presentationFiles;
}


public CcImagenesGrp getPresentCcImagenesGrp() {
	return presentCcImagenesGrp;
}


public void setPresentCcImagenesGrp(CcImagenesGrp presentCcImagenesGrp) {
	this.presentCcImagenesGrp = presentCcImagenesGrp;
}


public List<CcImagenesGrp> getListPresentCcImagenesGrp() {
	return listPresentCcImagenesGrp;
}


public void setListPresentCcImagenesGrp(List<CcImagenesGrp> listPresentCcImagenesGrp) {
	this.listPresentCcImagenesGrp = listPresentCcImagenesGrp;
}


public long getNumeroFta() {
	return numeroFta;
}


public void setNumeroFta(long numeroFta) {
	this.numeroFta = numeroFta;
}


public CcImagenesGrp getEditPresentCcImagenesGrp() {
	return editPresentCcImagenesGrp;
}


public void setEditPresentCcImagenesGrp(CcImagenesGrp editPresentCcImagenesGrp) {
	this.editPresentCcImagenesGrp = editPresentCcImagenesGrp;
}


public CcImagenes getEditCCImagenes() {
	return editCCImagenes;
}


public void setEditCCImagenes(CcImagenes editCCImagenes) {
	this.editCCImagenes = editCCImagenes;
}








}

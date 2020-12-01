package com.cmrise.jpa.jdbc.dcm;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cmrise.utils.Utilitarios;

public abstract class AbstractJpaDao < T extends Serializable >{
	private Class<T> clazz;
	
	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;

	  public T update( T entity ){
	      return em.merge( entity );	     
	   }
	 
	   public void delete( T entity ){
	      em.remove( entity );
	   }
	   
	   public void deleteById( long entityId ){
	      T entity = findById( entityId );
	      delete( entity );
	   }
	   
	   public T findById( long id ){
		      return em.find( clazz, id );
	  }
	   
		public Class<T> getClazz() {
			return clazz;
		}
	
		public void setClazz(Class<T> clazz) {
			this.clazz = clazz;
		}
	
	
	
}

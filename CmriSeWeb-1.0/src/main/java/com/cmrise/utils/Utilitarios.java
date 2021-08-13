package com.cmrise.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

public class Utilitarios {

    public static final String PCUNITNAME = "CMRISEPU";
    public static final java.sql.Date endOfTime = java.sql.Date.valueOf("9999-12-31");
    public static final java.sql.Date startOfTime = java.sql.Date.valueOf("0001-01-01");
    public static final java.sql.Timestamp endOfTimeTimestamp = java.sql.Timestamp.valueOf("9999-12-31 00:00:00");
    public static final String SOCIEDAD = "CMRI - Consejo Mexicano de Radiolog\u00eda e Imagen";
    public static final String INITIAL_STATUS_MRQ = "PARA_REVISAR";
    public static final String INITIAL_STATUS_CC_EXAM = "EN_REVISION";
    public static final String LIMIT_RESP_TEXTO_LIBRE = "LIMIT_RESP_TEXTO_LIBRE";
    public static final String RESP_TEXTO_LIBRE = "RESP_TEXTO_LIBRE";
    public static final String OPCION_MULTIPLE = "OPCION_MULTIPLE";
    public static final String IMAGEN_INDICADA = "IMAGEN_INDICADA";
    public static final String IMAGEN_ANOTADA = "IMAGEN_ANOTADA";
    public static final String WRONG_CORRECT = "WRONG_CORRECT";
    public static final String PROP_SCORING = "PROP_SCORING";
    public static final String INCLUDED_WORDS = "INCLUDED_WORDS";
    public static final String CAN_WORDS = "CAN_WORDS";
    public static final String EXCLUDED_WORDS = "EXCLUDED_WORDS";
    public static final String ROL_ADMIN = "ADMINISTRADOR";

    public static final String ROL_MAESTRO_REACT = "MAESTRO DE REACTIVO";

    public static final String ROL_MAESTRO_CASOS = "MAESTRO DE CASOS";
    public static final String EXAMINADOR = "EXAMINADOR";
    public static final String REVISOR = "REVISOR";
    public static final String ROL_ALUMNO = "ALUMNO";
    public static final String MRQS = "MRQS";
    public static final String CORE_CASES = "CORE_CASES";
    public static final String CORE_CASES_TITLE = "CASOS CLINICOS";
    public static final String CORRELACION_COLUMNA = "CORRELACION_COLUMNA";
    public static final float CORRELACION_COLUMNA_VALOR_REACTIVO = 5.0f;
    public static final float OPCION_MULTIPLE_VALOR_REACTIVO = 5.0f;
    public static final String ERROR_CARGAR_IMAGEN = "Error al cargar imagen";
    public static final String ERROR_ELIMINAR = "Error al eliminar imagen";
    public static final String ERROR_PUNTOS_USUARIO = "No se han señalado puntos";
    public static final String ERROR_PUNTUACION = "Error al obtener puntuacion";
    
    /**
     * Casos Clinicos
     **/
    public static final String EE_ASIGNADO = "ASIGNADO"; /*Estatus Examen ASIGNADO*/
    public static final String EE_EN_PROGRESO = "EN_PROGRESO"; /*Estatus Examen EN_PROGRESO*/
    public static final String EE_TERMINADO = "TERMINADO"; /*Estatus Examen TERMINADO*/
    public static final String EE_CALIFCADO = "CALIFCADO"; /*Estatus Examen CALIFCADO*/
    public static final String JPG = "JPG";
    public static final String DCM = "DCM";
    public static final String INTRODUCCION = "INTRODUCCION";
    public static final String CONCLUSION = "CONCUSION";
    public static final String ERROR_REDIRECCIONAR ="Error";
    public static final String ALERTA_PREGUNTAS_VACIO ="No existen preguntas";
    public static final String ERROR_LISTA_PREGUNTAS_VACIA = "No es posible previsualizar caso clinico sin pregunta";
    //public static final String FS_ROOT = (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0 || System.getProperty("os.name").toLowerCase().indexOf("nix") >= 0 || System.getProperty("os.name").toLowerCase().indexOf("nux") >= 0) ? StringUtils.EMPTY : System.getProperty("os.name").contains("Windows Server") ? "D:" : "C:";
    //public static final String FS_MRQS = File.separator + "CmriSeFs" + File.separator + "MRQs"; /** File System MRQS **/
    //public static final String FS_MRQS = "/Users/mauozuna/Documents/Trabajo/Personal/CmriSeWeb/Media/MRQs";

    public static final String FS_ROOT = System.getProperty("os.name").contains("Windows Server")?"D:":"C:";
    public static final String FS_MRQS = "\\CmriSeFs\\MRQs"; /** File System MRQS **/
    public static final String FS_CORE_CASES = "\\CmriSeFs\\CoreCases"; /** File System MRQS **/

    /**
     * File System MRQS
     **/
    //public static final String FS_CORE_CASES = File.separator + "CmriSeFs" + File.separator + "CoreCases";
    /**
     * File System MRQS
     **/
    public static final String DEFAULT_THEME = "deep-purple";
    public static final String JPG_SUFFIX = ".jpg";
    public static final String THUMBNAIL_SUFFIX = "_thumbnail" + JPG_SUFFIX;


    public java.sql.Date toSqlDate(java.util.Date pUtilDate) {
        java.sql.Date retval = null;
        if (null != pUtilDate) {
            retval = new java.sql.Date(pUtilDate.getTime());
        }
        return retval;
    }

    public java.sql.Timestamp toSqlTimestamp(java.util.Date pUtilDate) {
        java.sql.Timestamp retval = null;
        if (null != pUtilDate) {
            retval = new java.sql.Timestamp(pUtilDate.getTime());
        }
        return retval;
    }

    public java.util.Date toUtilDate(java.sql.Date pSqlDate) {
        java.util.Date retval = null;
        if (null != pSqlDate) {
            retval = new java.util.Date(pSqlDate.getTime());
        }
        return retval;
    }

    public java.util.Date toUtilDate(java.sql.Timestamp pSqlTimestamp) {
        java.util.Date retval = null;
        if (null != pSqlTimestamp) {
            retval = new java.util.Date(pSqlTimestamp.getTime());
        }
        return retval;
    }

    public static long objToLong(Object pObject) {
        long retval = 0;
        if (null != pObject) {
            if (pObject instanceof Long) {
                long numeroCcHdr = (Long) pObject;
                retval = numeroCcHdr;
            } else {
                System.out.println("pObject instanceof Long:false");
                return 0;
            }
        } else {
            System.out.println("null!=pObject");
            return 0;
        }
        return retval;
    }

    public static Date sqlDateToUtilDate(java.sql.Date pFecha) {
        return new java.util.Date(pFecha.getTime());
    }

    public static Timestamp utilDateToTimestamp(Date pFecha) {
        return new java.sql.Timestamp(pFecha.getTime());
    }

    public static java.sql.Date utilDateToSqlDate(Date pFecha) {
        return new java.sql.Date(pFecha.getTime());
    }

    public static Date timestampDateToUtilDate(java.sql.Timestamp pFecha) {
        return new java.util.Date(pFecha.getTime());
    }

}

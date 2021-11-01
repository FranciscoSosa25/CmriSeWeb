---liquibase formatted sql

--changeset kisha.wappnet:23234 
--comment May 15, 2021 4:10:43 AM wappnet_release, .dcm file upload
ALTER TABLE [CC_IMAGENES_GRP] ALTER COLUMN NUMERO_FTA [bigint] NULL;
ALTER TABLE [CC_IMAGENES_GRP] ADD NUMERO_CC_HDR [bigint] NULL;
ALTER TABLE [CC_IMAGENES_GRP] ADD MODALITY [varchar](500) NULL;

ALTER TABLE [dbo].[CC_IMAGENES_GRP]  WITH CHECK ADD  CONSTRAINT [CC_IMAGENES_GRP_FK2] FOREIGN KEY(NUMERO_CC_HDR)
REFERENCES [dbo].[CC_HDR] ([NUMERO]);
------------------------------------------------------------
--changeset kisha.wappnet:23234 
--comment July 30, 2021 6:26:43 AM wappnet_release, Core Case DICOM image- Draw Poligonos

ALTER TABLE [CC_IMAGENES] ADD POLIGONO_MODEL [varchar](max) NULL,
ALTER TABLE [CC_IMAGENES] ADD HEIGHT [int] NULL;
ALTER TABLE [CC_IMAGENES] ADD WIDTH [int] NULL;
ALTER TABLE [CC_IMAGENES] ADD POLIGONOS [int] NULL;
------------------------------------------------------------
--changeset kisha.wappnet:23234 
--comment Sep 11, 2021 6:26:43 AM wappnet_release, Core Case DICOM image- Draw Poligonos
ALTER TABLE CC_IMAGENES ADD CONTENT_TYPE [varchar](200) NULL ;
------------------------------------------------------------
--changeset kisha.wappnet:23235 
--comment Oct 04, 2021 6:26:43 AM wappnet/release/1.0.0 , Add duration column in respuestas 

ALTER TABLE CAND_EXAM_RESPUESTAS ADD [DURATION] [int] NULL;
------------------------------------------------------------
--changeset kisha.wappnet:23236 
--comment Oct 04, 2021 6:26:43 AM wappnet/release/1.0.0 , Update view CAND_EXAM_RESPUESTAS_V1

ALTER    VIEW [dbo].[CAND_EXAM_RESPUESTAS_V1] AS
SELECT CER.[NUMERO]
      ,CER.[NUMERO_CAND_EXAMEN]
      ,CER.[NUMERO_GRUPO]
      ,CER.[NUMERO_PREGUNTA_HDR]
      ,CER.[NUMERO_PREGUNTA_FTA]
      ,CER.[RESPUESTA]
	  ,ISNULL((SELECT m.VALOR_PUNTUACION FROm MRQS_PREGUNTAS_FTA M WHERE M.NUMERO = CER.[NUMERO_PREGUNTA_FTA]),0) AS VALOR_PUNTUACION
      ,CER.[PUNTUACION]
      ,CER.[ESTATUS]
      ,ISNULL(CER.[NUM_OPC_CORRECTAS],0)  NUM_OPC_CORRECTAS
      ,ISNULL(CER.[NUM_OPC_INCORRECTAS],0)  NUM_OPC_INCORRECTAS
      ,CER.[CREADO_POR]
      ,CER.[FECHA_CREACION]
      ,CER.[ACTUALIZADO_POR]
      ,CER.[FECHA_ACTUALIZACION]
	  ,CER.DURATION	
	  ,CE.NUMERO_EXAMEN 
	  ,CE.NUMERO_USUARIO
	  ,CE.TIPO TIPO_EXAMEN
	  ,'MPHV.TITULO_PREGUNTA' TITULO_PREGUNTA
	  ,MPHV.TIPO_PREGUNTA
	  ,MPHV.TIPO_PREGUNTA_DESC
  FROM [dbo].[CAND_EXAM_RESPUESTAS] CER
      ,[dbo].[CAND_EXAMENES] CE
	  ,[dbo].[MRQS_PREGUNTAS_HDR_V1] MPHV
 WHERE CE.NUMERO = CER.NUMERO_CAND_EXAMEN
   AND CE.TIPO = 'MRQS'
   AND MPHV.NUMERO = CER.NUMERO_PREGUNTA_HDR
   UNION 
   SELECT CER.[NUMERO]
      ,CER.[NUMERO_CAND_EXAMEN]
      ,CER.[NUMERO_GRUPO]
      ,CER.[NUMERO_PREGUNTA_HDR]
      ,CER.[NUMERO_PREGUNTA_FTA]
      ,CER.[RESPUESTA]
	  ,ISNULL((SELECT m.VALOR_PUNTUACION FROm CC_PREGUNTAS_FTA M WHERE M.NUMERO = CER.[NUMERO_PREGUNTA_FTA]),0) AS VALOR_PUNTUACION
      ,CER.[PUNTUACION]
      ,CER.[ESTATUS]
      ,ISNULL(CER.[NUM_OPC_CORRECTAS],0)  NUM_OPC_CORRECTAS
      ,ISNULL(CER.[NUM_OPC_INCORRECTAS],0)  NUM_OPC_INCORRECTAS
      ,CER.[CREADO_POR]
      ,CER.[FECHA_CREACION]
      ,CER.[ACTUALIZADO_POR]
      ,CER.[FECHA_ACTUALIZACION]
	  ,CER.DURATION
	  ,CE.NUMERO_EXAMEN 
	  ,CE.NUMERO_USUARIO
	  ,CE.TIPO TIPO_EXAMEN
	  ,'CPHV.TITULO_PREGUNTA' TITULO_PREGUNTA
	  ,CPHV.TIPO_PREGUNTA
	  ,CPHV.TIPO_PREGUNTA_DESC
  FROM [dbo].[CAND_EXAM_RESPUESTAS] CER
      ,[dbo].[CAND_EXAMENES] CE
	  ,[dbo].[CC_PREGUNTAS_HDR_V1] CPHV
 WHERE CE.NUMERO = CER.NUMERO_CAND_EXAMEN
   AND CE.TIPO = 'CORE_CASES'
   AND CPHV.NUMERO = CER.NUMERO_PREGUNTA_HDR;


------------------------------------------------------------
--changeset kisha.wappnet:23236 
--comment Oct 04, 2021 6:26:43 AM wappnet/release/1.0.0 , Update view CAND_EXAMENES_V2

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER    VIEW [dbo].[CAND_EXAMENES_V2] AS 
SELECT NUMERO
	, NUMERO_USUARIO
	, NUMERO_EXAMEN
	, DESCRIPCION
	, TITULO
	, FECHA_EFECTIVA_DESDE_EXAMEN
	, FECHA_EFECTIVA_HASTA_EXAMEN
	, TIPO
	, ESTATUS
	, CREADO_POR
	, FECHA_CREACION
	, ACTUALIZADO_POR
	, FECHA_ACTUALIZACION
	, MATRICULA
	, NOMBRE_USUARIO
	, APELLIDO_PATERNO
	, APELLIDO_MATERNO
	, NOMBRE_COMPLETO_USUARIO
	, NOMBRE_ROL
	, DESCRIPCION_ROL
	, CURP
	, TIEMPO_LIMITE
	, TOTAL_PUNTUACION
	, NOMBRE_ACT_POR 
 FROM( 
	(SELECT CE.[NUMERO]       
	  ,CE.[NUMERO_USUARIO]
      ,CE.[NUMERO_EXAMEN]
	  ,(SELECT NOMBRE FROM [ADMON_EXAMEN_HDR] AEH WHERE NUMERO = ME.ADMON_EXAMEN) AS DESCRIPCION
	  ,ME.DESCRIPCION TITULO
	  ,ME.FECHA_EFECTIVA_DESDE FECHA_EFECTIVA_DESDE_EXAMEN
	  ,ME.FECHA_EFECTIVA_HASTA FECHA_EFECTIVA_HASTA_EXAMEN
      ,CE.[TIPO]
	  ,CE.[ESTATUS]
      ,CE.[CREADO_POR]
	  ,CE.[FECHA_CREACION]
      ,AD.[ACTUALIZADO_POR]
	  ,AD.[FECHA_ACTUALIZACION]
      ,AURV1.[MATRICULA]
	  ,AURV1.[NOMBRE_USUARIO]
      ,AURV1.[APELLIDO_PATERNO]
	  ,AURV1.[APELLIDO_MATERNO]
      ,AURV1.[NOMBRE_COMPLETO_USUARIO]
	  ,AURV1.[NOMBRE_ROL]
      ,AURV1.[DESCRIPCION_ROL]
	  ,AURV1.[CURP]
	  ,ME.[TIEMPO_LIMITE]
	  ,ISNULL((SELECT SUM(ISNUll(CER.PUNTUACION,0)) FROM dbo.[CAND_EXAM_RESPUESTAS] CER WHERE CER.NUMERO_CAND_EXAMEN = CE.NUMERO ),0) TOTAL_PUNTUACION
	  ,(select au.NOMBRE + ' ' + au.APELLIDO_PATERNO + ' ' + au.APELLIDO_PATERNO from dbo.ADMON_USUARIOS au where au.numero = [AD].[ACTUALIZADO_POR] ) NOMBRE_ACT_POR
  FROM [dbo].[CAND_EXAMENES] CE
      ,[dbo].[MRQS_EXAMENES] ME
	  ,[dbo].[ADMON_USUARIOS_ROLES_V1] AURV1
	  ,[dbo].ADMON_USUARIOS AD
  WHERE CE.TIPO = 'MRQS' AND CE.NUMERO_USUARIO = AURV1.NUMERO_USUARIO 
 AND   ME.NUMERO = CE.[NUMERO_EXAMEN] AND AD.NUMERO = AURV1.NUMERO_USUARIO
 AND CE.[ESTATUS] IN ('ASIGNADO','REVISADO') 
 )

 UNION 

 (SELECT CE.[NUMERO]
	  ,CE.[NUMERO_USUARIO]
      ,CE.[NUMERO_EXAMEN]
	  ,(SELECT NOMBRE FROM [ADMON_EXAMEN_HDR] AEH WHERE NUMERO = CCE.EXAMEN) AS DESCRIPCION
	  ,CCE.[DESCRIPCION] TITULO
	  ,CCE.FECHA_EFECTIVA_DESDE FECHA_EFECTIVA_DESDE_EXAMEN
	  ,CCE.FECHA_EFECTIVA_HASTA FECHA_EFECTIVA_HASTA_EXAMEN
      ,CE.[TIPO]
	  ,CE.[ESTATUS]
      ,CE.[CREADO_POR]
	  ,CE.[FECHA_CREACION]
      ,AD.[ACTUALIZADO_POR]
	  ,AD.[FECHA_ACTUALIZACION]
      ,AURV1.[MATRICULA]
	  ,AURV1.[NOMBRE_USUARIO]
      ,AURV1.[APELLIDO_PATERNO]
	  ,AURV1.[APELLIDO_MATERNO]
      ,AURV1.[NOMBRE_COMPLETO_USUARIO]
	  ,AURV1.[NOMBRE_ROL]
      ,AURV1.[DESCRIPCION_ROL]
	  ,AURV1.[CURP]
	  ,CCE.TIEMPO_LIMITE
	  ,ISNULL((SELECT SUM(ISNUll(CER.PUNTUACION,0)) FROM dbo.[CAND_EXAM_RESPUESTAS] CER WHERE CER.NUMERO_CAND_EXAMEN = CE.NUMERO ),0) TOTAL_PUNTUACION
	  ,(select au.NOMBRE + ' ' + au.APELLIDO_PATERNO + ' ' + au.APELLIDO_PATERNO from dbo.ADMON_USUARIOS au where au.numero = [AD].[ACTUALIZADO_POR] ) NOMBRE_ACT_POR
  FROM [dbo].[CAND_EXAMENES] CE
      ,[dbo].[CC_EXAMENES] CCE
	  ,[dbo].[ADMON_USUARIOS_ROLES_V1] AURV1
	  ,[dbo].ADMON_USUARIOS AD
	  ,[dbo].[ADMON_EXAMEN_HDR] AEH
  WHERE CE.TIPO = 'CORE_CASES' AND CE.NUMERO_USUARIO = AURV1.NUMERO_USUARIO
 AND   CCE.NUMERO = CE.[NUMERO_EXAMEN] AND AD.NUMERO = AURV1.NUMERO_USUARIO
 AND CE.[ESTATUS] IN ('ASIGNADO','REVISADO') ) 
 )  C_EXMA
   GROUP BY NUMERO, NUMERO_USUARIO, NUMERO_EXAMEN, DESCRIPCION, TITULO, FECHA_EFECTIVA_DESDE_EXAMEN
	, FECHA_EFECTIVA_HASTA_EXAMEN, TIPO, ESTATUS, CREADO_POR,FECHA_CREACION
 ,ACTUALIZADO_POR, FECHA_ACTUALIZACION, MATRICULA, NOMBRE_USUARIO, APELLIDO_PATERNO, APELLIDO_MATERNO
 ,NOMBRE_COMPLETO_USUARIO, NOMBRE_ROL, DESCRIPCION_ROL, CURP, TIEMPO_LIMITE, TOTAL_PUNTUACION, NOMBRE_ACT_POR
 
GO
----------------------------------------------------------

ALTER TABLE CAND_EXAMENES ADD EXAM_START_TIME [datetime] NULL;
ALTER TABLE CAND_EXAMENES ADD EXAM_END_TIME [datetime] NULL;
----------------------------------------------------------
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
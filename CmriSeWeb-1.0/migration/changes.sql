---liquibase formatted sql

--changeset kisha.wappnet:23234 
--comment May 15, 2021 4:10:43 AM wappnet_release, .dcm file upload
ALTER TABLE [CC_IMAGENES_GRP] ALTER COLUMN NUMERO_FTA [bigint] NULL;
ALTER TABLE [CC_IMAGENES_GRP] ADD NUMERO_CC_HDR [bigint] NULL;
ALTER TABLE [CC_IMAGENES_GRP] ADD MODALITY [varchar](500) NULL;

ALTER TABLE [dbo].[CC_IMAGENES_GRP]  WITH CHECK ADD  CONSTRAINT [CC_IMAGENES_GRP_FK2] FOREIGN KEY(NUMERO_CC_HDR)
REFERENCES [dbo].[CC_HDR] ([NUMERO]);
------------------------------------------------------------
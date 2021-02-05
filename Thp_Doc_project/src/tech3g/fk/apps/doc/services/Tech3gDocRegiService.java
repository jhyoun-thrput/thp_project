package tech3g.fk.apps.doc.services;

import java.util.List;

import org.apache.struts.upload.FormFile;

import tech3g.common.exceptions.ApplicationException;
import tech3g.fk.apps.doc.actions.Tech3gDocRegiBean;

public interface Tech3gDocRegiService {


	abstract void initTechDocRegi(Tech3gDocRegiBean viewBean);

	abstract void searchDocIndex(Tech3gDocRegiBean viewBean);

	abstract void confrmDocInfo(Tech3gDocRegiBean viewBean);

	abstract void insertDocRegi(String userId, Tech3gDocRegiBean viewBean) throws  Exception;

	abstract void multiFileUpload(Tech3gDocRegiBean viewBean) throws Exception;

	abstract void clearMultiFiles(Tech3gDocRegiBean viewBean);

	abstract List<FormFile> getMultiFilesList();
}

package br.com.totvs.plugins.bacen.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

import javax.xml.bind.UnmarshalException;

import br.com.totvs.exceptions.ConfigException;
import br.com.totvs.exceptions.InfraException;
import br.com.totvs.exceptions.LayoutException;
import br.gov.bcb.scr2.operacional.webservice.BCServicoException_Exception;

public abstract class ResourceErroAbstract {

	public void tratarException(Exception e, HashMap<String, Object> hashOut) throws InfraException, LayoutException, ConfigException {

		if (e instanceof BCServicoException_Exception) {
			throw new InfraException(e.getMessage());
		} else if (e instanceof MalformedURLException || e instanceof FileNotFoundException) {
			throw new ConfigException(e.getMessage());
		} else if (e instanceof LayoutException || e instanceof UnmarshalException || e instanceof IOException) {
			throw new LayoutException(e.getMessage());
		} else {
			throw new InfraException(e.getMessage());
		}
	}
}
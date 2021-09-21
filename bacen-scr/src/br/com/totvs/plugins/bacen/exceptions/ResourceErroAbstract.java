package br.com.totvs.plugins.bacen.exceptions;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;

import br.com.totvs.exceptions.ConfigException;
import br.com.totvs.exceptions.InfraException;
import br.com.totvs.exceptions.LayoutException;
import br.gov.bcb.scr2.operacional.webservice.BCServicoException_Exception;

public abstract class ResourceErroAbstract {

	private static final Logger LOGGER = Logger.getLogger("ResourceErroAbstract");

	public void tratarException(Exception e) throws InfraException, LayoutException, ConfigException {

		LOGGER.error(e);

		if (e instanceof BCServicoException_Exception) {
			throw new InfraException(e.getMessage());
		} else if (e instanceof MalformedURLException || e instanceof FileNotFoundException) {
			throw new ConfigException(e.getMessage());
		} else if (e instanceof LayoutException) {
			throw new LayoutException(e.getMessage());
		} else {
			throw new InfraException(e.getMessage());
		}

	}
}
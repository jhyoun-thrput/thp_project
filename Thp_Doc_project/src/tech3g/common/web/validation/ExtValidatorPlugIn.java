package tech3g.common.web.validation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.ValidatorResources;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.validator.ValidatorPlugIn;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResourcePatternResolver;
import org.xml.sax.SAXException;

/**
 * <pre>
 * ExtValidatorPlugInクラス。
 * 正規表現で指定したバリデーション定義ファイルも取得する。
 * </pre>
 *
 */
public class ExtValidatorPlugIn extends ValidatorPlugIn {

	/**
     * Commons Logging instance.
     */
    private static Log log = LogFactory.getLog(ValidatorPlugIn.class);

    /**
     * Delimitter for Validator resources.
     */
    private final static String RESOURCE_DELIM = ",";

	/**
     * The {@link ActionServlet} owning this application.
     */
    private ActionServlet servlet = null;

    /* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorPlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
	 */
	@Override
	public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
		this.servlet = servlet;
		super.init(servlet, config);
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorPlugIn#destroy()
	 */
	@Override
	public void destroy() {
		servlet = null;
		super.destroy();
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorPlugIn#initResources()
	 */
	@Override
	protected void initResources() throws IOException, ServletException {
		if ((getPathnames() == null) || (getPathnames().length() <= 0)) {
            return;
        }

        StringTokenizer st = new StringTokenizer(getPathnames(), RESOURCE_DELIM);

        List<URL> urlList = new ArrayList<URL>();

        ServletContextResourcePatternResolver patternResolver = new ServletContextResourcePatternResolver(servlet.getServletContext());

        try {
            while (st.hasMoreTokens()) {
                String validatorRules = st.nextToken().trim();

                if (log.isInfoEnabled()) {
                    log.info("Loading validation rules file from '"
                        + validatorRules + "'");
                }

                URL input = servlet.getServletContext().getResource(validatorRules);

                // If the config isn't in the servlet context, try the class
                // loader which allows the config files to be stored in a jar
                if (input == null) {
                    input = getClass().getResource(validatorRules);
                }

                Resource[] inputs = null;
                if (input == null) {
                	inputs = patternResolver.getResources(validatorRules);
                }

                if (input != null) {
                    urlList.add(input);
                }
                else if (inputs != null) {
                	for (int i = 0; i < inputs.length; i++) {
                		urlList.add(inputs[i].getURL());
                	}
                }
                else {
                    throw new ServletException(
                        "Skipping validation rules file from '"
                        + validatorRules + "'.  No url could be located.");
                }
            }

            int urlSize = urlList.size();
            URL[] urlArray = new URL[urlSize];

            for (int urlIndex = 0; urlIndex < urlSize; urlIndex++) {
                urlArray[urlIndex] = (URL) urlList.get(urlIndex);
            }

            this.resources = new ValidatorResources(urlArray);
        } catch (SAXException sex) {
            log.error("Skipping all validation", sex);
            throw new ServletException(sex);
        }
	}
}

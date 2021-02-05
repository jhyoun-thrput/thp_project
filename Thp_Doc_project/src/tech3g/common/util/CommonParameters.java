package tech3g.common.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.avalon.framework.parameters.ParameterException;
import org.apache.commons.beanutils.BeanUtils;

public class CommonParameters {

	private final HashMap<String, String[]> parameters;
	private final HashMap<String, Object> attributes;

	public CommonParameters() {
		parameters = new HashMap<String, String[]>();
		attributes = new HashMap<String, Object>();
	}

	@SuppressWarnings("unchecked")
	public CommonParameters(HttpServletRequest req) {
		this();
		Enumeration<String> enumeration = req.getParameterNames();
		for (; enumeration.hasMoreElements();) {
			String key = enumeration.nextElement();
			String values[] = req.getParameterValues(key);
			// if (values != null && values.length == 1)
			// parameter.put(key, values[0]);
			// else
			parameters.put(key, values);
		}
	}

	public Map<String, Object> getAttributeMap() {
		return attributes;
	}
	public String[] getParameterValues(String key) {
		return parameters.get(key);
	}

	public void setParameterValues(String key, String[] values) {
		parameters.put(key, values);
	}

	public String getParameter(String key) {
		String retValue = null;
		String[] values = getParameterValues(key);
		if (values != null) {
			if (values.length > 1) {
				retValue = "";
				for (int i = 0; i < values.length; i++)
					retValue += (retValue.length() > 0 ? "," : "") + values[i];
			} else if (values.length == 1) {
				retValue = values[0];
			}
		}
		return retValue;
	}

	public String setParameter(String key, String value) {
		String old = getParameter(key);
		parameters.put(key, new String[] { value });
		return old;
	}

	public String getParameter(String key, String defValue) {
		String value = getParameter(key);
		if (value == null || value.length() == 0)
			value = defValue;

		return value;
	}

	public boolean getParameterAsBoolean(String key) throws ParameterException {
		final String value = getParameter(key);
		if (value.equalsIgnoreCase("true")) {
			return true;
		} else if (value.equalsIgnoreCase("false")) {
			return false;
		} else {
			throw new ParameterException("Could not return a boolean value");
		}
	}

	public boolean getParameterAsBoolean(String key, boolean defValue) throws ParameterException {
		final String value = getParameter(key);
		if (value == null || value.length() == 0) {
			return defValue;
		} else if (value.equalsIgnoreCase("true")) {
			return true;
		} else if (value.equalsIgnoreCase("false")) {
			return false;
		} else {
			throw new ParameterException("Could not return a boolean value");
		}
	}

	public float getParameterAsFloat(String key) throws ParameterException {
		try {
			return Float.parseFloat(getParameter(key));
		} catch (final NumberFormatException e) {
			throw new ParameterException("Could not return a float value", e);
		}
	}

	public float getParameterAsFloat(String key, float defValue) {
		try {
			final String value = getParameter(key, null);
			if (value == null) {
				return defValue;
			}

			return Float.parseFloat(value);
		} catch (final NumberFormatException pe) {
			return defValue;
		}

	}

	public int getParameterAsInteger(String key) throws ParameterException {
		try {
			return Integer.parseInt(getParameter(key));
		} catch (final NumberFormatException e) {
			throw new ParameterException("Could not return an integer value", e);
		}

	}

	public int getParameterAsInteger(String key, int defValue) {
		try {
			final String value = getParameter(key, null);
			if (value == null) {
				return defValue;
			}

			return Integer.parseInt(value);
		} catch (final NumberFormatException e) {
			return defValue;
		}
	}

	public long getParameterAsLong(String key) throws ParameterException {
		try {
			return Long.parseLong(getParameter(key));
		} catch (final NumberFormatException e) {
			throw new ParameterException("Could not return a long value", e);
		}
	}

	public long getParameterAsLong(String key, long defValue) {
		try {
			final String value = getParameter(key, null);
			if (value == null) {
				return defValue;
			}

			return Long.parseLong(value);
		} catch (final NumberFormatException e) {
			return defValue;
		}

	}

	public boolean isParameter(String key) {

		return parameters.containsKey(key);
	}

	public CommonParameters merge(CommonParameters other) {
		final String[] names = other.getNames();

		for (int i = 0; i < names.length; i++) {
			final String name = names[i];
			String[] value = other.getParameterValues(name);
			setParameterValues(name, value);
		}

		return this;

	}

	public void removeParameter(String key) {

		parameters.remove(key);
	}

	public String[] getNames() {
		ArrayList<String> names = new ArrayList<String>();
		Iterator<String> itr = parameters.keySet().iterator();
		while (itr.hasNext()) {
			names.add(itr.next());
		}
		return names.toArray(new String[0]);
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	public void setAttribute(String key, Object values) {
		attributes.put(key, values);
	}

	public Object populate(Class<? extends Object> clazz) throws Exception {
		Object retObj = clazz.newInstance();
		BeanUtils.populate(retObj, parameters);
		BeanUtils.populate(retObj, attributes);

		return retObj;
	}
}
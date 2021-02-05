
package tech3g.common.db;

import java.util.Map;

import org.apache.velocity.context.Context;

public class ExtMapSqlParameterSourceContext implements Context {
	private Map parameterSource = null;

	public ExtMapSqlParameterSourceContext(
			Map parameterSource) {
		this.parameterSource = parameterSource;
	}

	public boolean containsKey(Object key) {
		return parameterSource.containsKey(key);
	}

	public Object get(String key) {
		try {
			return parameterSource.get(key);
		} catch (Exception e) {
			return null;
		}
	}

	public Object[] getKeys() {
		return parameterSource.keySet().toArray(new Object[0]);
	}

	public Object put(String key, Object value) {
		return parameterSource.values().toArray(new Object[0]);
	}

	public Object remove(Object key) {
		return parameterSource.remove(key);
	}
}

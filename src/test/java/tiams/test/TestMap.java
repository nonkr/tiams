package tiams.test;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestMap {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TestMap.class);

	// jdk1.4遍历Map方法
	@Test
	public void testMap14() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("name", "宋滨彬");
		m.put("pwd", "admin");
		Iterator it = m.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			logger.info("key: [" + key + "]");
			logger.info("value: [" + value + "]");
		}
	}

	// jdk1.5遍历Map方法
	@Test
	public void testMap15() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("name", "宋滨彬");
		m.put("pwd", "admin");
		for (String o : m.keySet()) {
			String key = o;
			String value = (String) m.get(o);
			logger.info("key: [" + key + "]");
			logger.info("value: [" + value + "]");
		}
	}
}

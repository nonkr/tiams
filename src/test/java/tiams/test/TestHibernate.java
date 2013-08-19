package tiams.test;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tiams.model.User;
import tiams.service.UserServiceI;

public class TestHibernate {

	@Test
	public void test() {
//		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"classpath:spring.xml", "classpath:spring-hibernate.xml"});
//		UserServiceI userService = (UserServiceI) ac.getBean("userService");
//		
//		User u = new User();
//		u.setId(UUID.randomUUID().toString());
//		u.setName("宋滨彬");
//		u.setPwd("admin");
//		u.setCreatedatetime(new Date());
//		
//		userService.save(u);
	}
}

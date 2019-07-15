package com.lc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;

import com.lc.bean.TransportPlan;
import com.lc.service.TransPlanService;
import com.lc.util.CommUtil;
import com.lc.util.GetTimestamp;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJpaAuditing(auditorAwareRef = "SpringDateAuditorAware")
@AutoConfigureMockMvc
public class LzAgvApplicationTests {
	@Test
	public void f1() {
		Random random = new Random();
		String url = "http://192.168.1.116:8080/LzAGV/plan/t/";
		CommUtil.doGet(url + CommUtil.objToJson(new TransportPlan("123", random.nextInt(20), String.valueOf((char) random.nextInt(100)), "p2", "123",GetTimestamp.getDateTimestamp(),GetTimestamp.getDateTimestamp())));
	}

	@Test
	public void contextLoads() throws InterruptedException {
		// 线程1，添加数据
		Thread t1 = new Thread(() -> {
			while (true) {
				Random random = new Random();
				ids.add(transPlanService.save(new TransportPlan("123", random.nextInt(20), String.valueOf((char) random.nextInt(100)), "p2", "123",GetTimestamp.getDateTimestamp(),GetTimestamp.getDateTimestamp())).getId());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		// 线程2，更新状态
		Thread t2 = new Thread(() -> {
			while (true) {
				synchronized (ids) {
					if (ids.size() > 0) {
						ids.forEach(id -> {
							transPlanService.updateStToTrue(id);
							System.out.println("--> 当前更新的transportPlan.id : " + id);
						});
						ids.clear();
					}
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		// Thread.currentThread().join();
		// t1.join();
		// t2.join();
		t1.start();
		t2.start();
		Thread.currentThread().sleep(1000000);
	}

	private static volatile List<Integer> ids = new ArrayList<>();

	@Autowired
	private TransPlanService transPlanService;
}

package com.lc.controller;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.EqualsExclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.HttpResource;

import com.lc.bean.CurrStatus;
import com.lc.bean.HisTransport;
import com.lc.bean.StorageAuto;
import com.lc.bean.TransportPlan;
import com.lc.service.CarService;
import com.lc.service.HisTransportService;
import com.lc.service.OtherService;
import com.lc.service.StorageAutoService;
import com.lc.service.TransPlanService;
import com.lc.util.CommUtil;
import com.lc.util.ExportExcelUtil_format;
import com.lc.util.GetTimestamp;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Controller
@RequestMapping("/plan")
public class TransPlanController {

//	private static volatile LinkedBlockingQueue<Integer> ids = new LinkedBlockingQueue<>();
	private static List<Integer> ids = new ArrayList<>();
	private static volatile boolean flag = true;
	@Autowired
	private TransPlanService service;
	@Autowired
	private HisTransportService hisTransportService;
	@Autowired
	private CarService carService;
	@Autowired
	private StorageAutoService storageAutoService;
	@Autowired
	private OtherService otherService;
	
	//故障测试
	@RequestMapping("socketMsg")
	public String socketMsg(String bid,@RequestParam(value = "status", defaultValue = "-1")Integer status) {
		System.out.println("获取bid："+bid+"当前状态："+status);
		if(status==-1) {
			WebSocket.webSockets.forEach(obj -> obj.sendToCurr(bid));
			carService.updateStatus("故障", "障碍物", bid);
		}else {
			carService.updateStatus("正常", "无", bid);
			String nid="n"+bid;
			WebSocket.webSockets.forEach(obj -> obj.sendToCurr(nid));
			return "redirect:/carstatus?id="+bid+"&status="+1;
		}
		return "redirect:/send";
	}
	//打开库位页面
	@ResponseBody
	@RequestMapping("/storage")
	public Map<String, Object> storage() {
		Map<String, Object> map=new HashMap<>();
		List<StorageAuto> list=storageAutoService.findAll();
		String StorageNumTwo=null;
		String StorageNumOne=null;
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getCountNum()==1) {
				StorageNumOne="NumOne"+list.get(i).getStorageNum()+"a";
			}
			if(list.get(i).getCountNum()==2) {
				StorageNumTwo="NumTwo"+list.get(i).getStorageNum()+"a";
			}
		}
		map.put("code", 0);
		map.put("StorageNumOne", StorageNumOne);
		map.put("StorageNumTwo", StorageNumTwo);
		return map;
	}
	//库存测试
	@RequestMapping("storageMsg")
	public String storageMsg(String bid,@RequestParam(value = "status", defaultValue = "-1")Integer status) {
		System.out.println("获取bid："+bid+"当前状态："+status);
		if(status==-1) {
			//库存设置为满
			String nid="o"+bid;
			WebSocket.webSockets.forEach(obj -> obj.sendToCurr(nid));
			//判断优先级，16以下表示生产线
			int priority=1;
			if(Integer.parseInt(bid)>16) {
				priority=2;
			}
			//判断库里是否存在该库位信息，若存在更新库位，反则添加
			List<StorageAuto> list=storageAutoService.findByNum(Integer.parseInt(bid));
			System.out.println("输出list："+list);
			if(list.size()!=0) {
				storageAutoService.update(2, Integer.parseInt(bid));
			}else {
				StorageAuto storageAuto=new StorageAuto(bid, GetTimestamp.getDateTimestamp(), priority, 2);
				storageAutoService.sava(storageAuto);
			}
		}else {
			List<StorageAuto> list=storageAutoService.findByNum(Integer.parseInt(bid));
			
			if(list.get(0).getCountNum()==2) {
				//二次运输，更改运输次数
				String cid="count"+bid;
				WebSocket.webSockets.forEach(obj -> obj.sendToCurr(cid));
				storageAutoService.update(1, Integer.parseInt(bid));
			}else {
				//库存设置为空
				String nid="oNo"+bid;
				WebSocket.webSockets.forEach(obj -> obj.sendToCurr(nid));
				storageAutoService.deleteByNum(Integer.parseInt(bid));
			}
		}
		return "redirect:/send";
	}
	//库位手动、自动
	@ResponseBody
	@RequestMapping("storageAuto")
	public Map<String, Object> storageAuto(Integer storageNum) {
		List<StorageAuto> list=storageAutoService.findAll();
		List<CurrStatus> currStatus=otherService.findAll();
		if(currStatus.get(0).getCurrName().equals("手动")) {
			System.out.println("手动操作");
			//手动
			if(list.size()!=0) {
				if(list.get(0).getCountNum()==1) {
					//向C++继续发送信号，坐标为list.get(0).getStorageNum()
				}else {
					//向C++继续发送信号，坐标为storageNum
				}
			}else {
				//停止小车
			}
		}else {
			System.out.println("自动操作");
			//自动
			if(list.size()!=0) {
				//向C++继续发送信号，坐标为list.get(0).getStorageNum()
			}else {
				//停止小车
			}
		}
		Map<String, Object> map=new HashMap<>();
		map.put("code", 0);
		return map;
	}
	
//	@RequestMapping(value = "/t", method = RequestMethod.POST)
//	@ResponseBody
//	public String test(@RequestBody String parm) {
//		System.out.println("test http post");
//		return "->" + parm;
//	}
//
//	@RequestMapping(value = "/t/{value}", method = RequestMethod.GET)
//	@ResponseBody
//	public String tes1t1(@PathVariable String value) {
//		System.out.println(value);
//		return "value";
//	}
	@ResponseBody
	@RequestMapping("testlayui")
	public Map<String, Object> Test_layui(@RequestParam(value = "currTime", defaultValue = "-1") String currTime,@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "5")int limit) {
		Pageable pageable=new PageRequest(page, limit);
		List<TransportPlan> list=null;
		List<HisTransport> hisList=null;
		if(currTime.equals("-1")) {
			list=service.findAllLimit(GetTimestamp.getTimestamp(),pageable);
		}
		//时间成立
		if(!currTime.equals("-1")) {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> map=new HashMap<String, Object>();
			Integer count=0;
			try {
				Date begin = fmt.parse(GetTimestamp.getTimestamp());
				Date end = fmt.parse(currTime); //结束日期
				count=hisTransportService.findAllWhereCurrTime(currTime).size();
				if(end.before(begin)) {
					hisList=hisTransportService.findAllLimit(currTime,pageable);
					map.put("data", hisList);
				}else {
					list=service.findAllLimit(currTime,pageable);
					map.put("data", list);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} //开始日期
			map.put("code", 0);
			map.put("count", count);
			return map;
		}
		
		Integer count=service.findAllWhereCurrTime(GetTimestamp.getTimestamp()).size();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("code", 0);
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	/**
	 * 	测试Echarts图表
	 * @return Json
	 */
	@ResponseBody
	@RequestMapping("testEcharts")
	public Map<String, Object> testEcharts(@RequestParam(value = "currTime", defaultValue = "-1") String currTime) {
		Map<String, Object> map=new HashMap<>();
		//统计小车运输次数
		List<Integer> Carcount=new ArrayList<Integer>();
		//统计小车型号
		List<String> CarNumcount=new ArrayList<String>();
		List<TransportPlan> list=null;
		List<HisTransport> hisList=null;
		List<String> car_num=null;
		if(currTime.equals("-1")) {
			list=service.findAllWhereCurrTime(GetTimestamp.getTimestamp());
			car_num=service.findGroup(GetTimestamp.getTimestamp());
			for (String num : car_num) {
				int count=0;
				for (TransportPlan Transport : list) {
					if(num.equals(Transport.getCarNum())) {
						count++;
					}
				}
				Carcount.add(count);
				CarNumcount.add(num);
			}
		}
		
		//时间成立
		if(!currTime.equals("-1")) {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date begin = fmt.parse(GetTimestamp.getTimestamp());
				Date end = fmt.parse(currTime); //结束日期
				if(end.before(begin)) {
					hisList=hisTransportService.findAllWhereCurrTime(currTime);
					car_num=hisTransportService.findGroup(currTime);
					for (String num : car_num) {
						int count=0;
						for (HisTransport hisTransport : hisList) {
							if(num.equals(hisTransport.getCarNum())) {
								count++;
							}
						}
						Carcount.add(count);
						CarNumcount.add(num);
					}
				}else {
					list=service.findAllWhereCurrTime(currTime);
					car_num=service.findGroup(currTime);
					for (String num : car_num) {
						int count=0;
						for (TransportPlan Transport : list) {
							if(num.equals(Transport.getCarNum())) {
								count++;
							}
						}
						Carcount.add(count);
						CarNumcount.add(num);
					}
					
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		map.put("x", CarNumcount);
		map.put("y", Carcount);
		return map;
		
	}
	/**
	 * 	导出车辆数据
	 */
	@RequestMapping("dateExcle")
	public void dateExcle(HttpServletRequest req,HttpServletResponse res) {
		String title="车辆信息表"; // 导出表格的表名
		 String[] rowName={"车辆编号","数量","起点站","终点站","开始时间"};// 导出表格的列名
		 List<Object[]>  dataList = new ArrayList<Object[]>(); // 对象数组的List集合
		 List<TransportPlan> list=service.findAllWhereCurrTime(GetTimestamp.getTimestamp());
		 //将集合stu中的对象都放入到dataList集合中去
		 
		 for(int i=0;i<list.size();i++){
			 dataList.add(new Object[]{
					 list.get(i).getCarNum(),
					 list.get(i).getQuantity(),
					 list.get(i).getStartPlace(),
					 list.get(i).getEndPlace(),
					 list.get(i).getStartTime()}
			 );
		 }
		 
		 //实例化对象: 导出.xls格式文件
		 ExportExcelUtil_format  export=new ExportExcelUtil_format(title,rowName,dataList,req,res); 
		 try {
			export.exportData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 	添加定时任务，将昨天数据存入历史库，每天晚上00:00:00执行一次。
	 */
    @Scheduled(cron = "0 0 0 * * ?")
    //或直接指定时间间隔
    //@Scheduled(fixedRate=5000)
    public void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        //存入今天的记录总数 
		List<TransportPlan> list=service.findAllTPlan(GetTimestamp.getTimestamp());
		System.out.println(list.size());
		for (TransportPlan transportPlan : list) {
				ids.add(transportPlan.getId());
		}
		System.out.println(ids.size());
		//去重复
	    HashSet<Integer> hs = new HashSet<Integer>(ids);
	    //更新临时表,数据保存HisTransport表
		hs.forEach(id -> {
			service.updateStToTrue(id);
		});
		ids.clear();
    }
	/**
	 *	页面显示及保存数据
	 * @param tPlan1
	 * @return
	 */
	@RequestMapping("/socket")
	public String socket(TransportPlan tPlan1) {
			//添加数据
			TransportPlan tPlan = service.save(new TransportPlan(tPlan1.getProduct(), tPlan1.getQuantity(), tPlan1.getStartPlace(), tPlan1.getEndPlace(), tPlan1.getCarNum(),GetTimestamp.getDateTimestamp(),GetTimestamp.getDateTimestamp()));
			String json = getTransportFieldsToJson(tPlan);
			ids.add(tPlan.getId());
			//System.out.println("testData："+tPlan+"\t size："+ids.size());
			WebSocket.webSockets.forEach(obj -> obj.sendToCurr(json));
		    //移除界面上的信息
			if(ids.size()>=10) {
				ids.forEach(id -> {
					//service.updateStToTrue(id);
					WebSocket.webSockets.forEach(obj -> obj.sendToCurr(id + ""));
				});
				ids.clear();
			}
			//测试路径
		return "redirect:/send";
	}
	/**
	 * 
	 * @return 请求显示界面
	 */
	@RequestMapping("/show")
	public String index() {
		if (flag) {
			// 测试代码
			flag = false;
			// 线程1，添加数据
			Thread t1 = new Thread(() -> {
				TransportPlan tPlan;
				Random random = new Random();
				while (true) {
					tPlan = service.save(new TransportPlan("1", random.nextInt(20), String.valueOf((char) random.nextInt(100)), "p2", "2",GetTimestamp.getDateTimestamp(),GetTimestamp.getDateTimestamp()));
					String json = getTransportFieldsToJson(tPlan);
					synchronized (ids) {
						ids.add(tPlan.getId());
						WebSocket.webSockets.forEach(obj -> obj.sendToCurr(json));
					}
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
//			 线程2，更新状态
			Thread t2 = new Thread(() -> {
				while (true) {
					synchronized (ids) {
						ids.forEach(id -> {
							//service.updateStToTrue(id);
							WebSocket.webSockets.forEach(obj -> obj.sendToCurr(id + ""));
						});
						ids.clear();
					}
					try {
						Thread.sleep(8000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			t1.start();
			t2.start();
		}
		return "plan/show";
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	// 在页面上解析完整的json格式的字符串
	public String saveInfo(@RequestBody String jsonString) {
		// new TransportPlan("product11", 10, "startPlace2", "endPlace3", "carNum")
		TransportPlan tPlan = service.save(CommUtil.jsonToObj(jsonString, TransportPlan.class));
		try {
			// 在web页面上显示
			WebSocket.webSockets.forEach(obj -> obj.sendToCurr(getTransportFieldsToJson(tPlan)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{" + tPlan.getId() + "}";
	}

	@ResponseBody
	@RequestMapping(value = "/arrive/{planId}", method = RequestMethod.GET)
	public String changePlanStat(@PathVariable Integer planId) {
		service.updateStToTrue(planId);
		try {
			// 更新状态，通知页面移除对应的记录
			WebSocket.webSockets.forEach(obj -> obj.sendToCurr(planId + ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*//移除界面上的信息
		if(ids.size()>=10) {
			ids.forEach(id -> {
				//service.updateStToTrue(id);
				WebSocket.webSockets.forEach(obj -> obj.sendToCurr(id + ""));
			});
			ids.clear();
		}*/
		return "ok";
	}

	private String getTransportFieldsToJson(TransportPlan tPlan) {
		Field[] fields = tPlan.getClass().getDeclaredFields();
		Object[] vals = new Object[fields.length];
		int i = 0;
		for (Field f : fields) {
			try {
				f.setAccessible(true);
				if (f.getGenericType().getTypeName().equals("java.util.Date")) {
					vals[i++] = CommUtil.date2LongString((Date) f.get(tPlan));
				} else {
					vals[i++] = f.get(tPlan);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return CommUtil.objToJson(vals);
	}
}

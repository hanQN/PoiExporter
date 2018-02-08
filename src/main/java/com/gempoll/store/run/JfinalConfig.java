package com.gempoll.store.run;

import org.beetl.core.GroupTemplate;
import org.apache.log4j.Logger;

import com.gempoll.store.beetl.func.MapValue;
import com.gempoll.store.beetl.render.MyBeetlRenderFactory;
import com.gempoll.store.common.DictKeys;
import com.gempoll.store.hander.GlobalHandler;
import com.gempoll.store.interceptor.LogInterceptor;
import com.gempoll.store.thread.ThreadParamInit;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Const;
import com.jfinal.core.JFinal;
import com.jfinal.ext.plugin.jms.JmsPlugin;
import com.jfinal.ext.plugin.quartz.QuartzPlugin;
import com.jfinal.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.ext.plugin.tablebind.SimpleNameStyles;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.EhCachePlugin;

public class JfinalConfig extends JFinalConfig {
	private static Logger log = Logger.getLogger(JfinalConfig.class);

	@Override
	public void configConstant(Constants me) {
		log.info("configConstant 缓存 properties");
		loadPropertyFile("init.properties");
		PropKit.use("init.properties");

		log.info("configConstant 设置字符集");
		me.setEncoding("UTF-8");

		log.info("configConstant 设置是否开发模式");
		me.setDevMode(true);

		log.info("configConstant 视图Beetl设置");
		me.setMainRenderFactory(new MyBeetlRenderFactory());

		log.info("设置上传文件最大值为100M");
		me.setMaxPostSize(10 * Const.DEFAULT_MAX_POST_SIZE);

		GroupTemplate gt = MyBeetlRenderFactory.groupTemplate;
		gt.registerFunction("mapValue", new MapValue());
	}

	@Override
	public void configRoute(Routes me) {
		log.info("自动路由扫描注册");
		me.add(new AutoBindRoutes());
	}

	@Override
	public void configPlugin(Plugins me) {
		log.info("配置数据库连接池...");
		DruidPlugin druidPlugin = new DruidPlugin(
				getProperty("jdbcUrl").trim(), getProperty("userName").trim(),
				getProperty("passWord").trim());
		me.add(druidPlugin);
		
		log.info("表关系映射...");
		AutoTableBindPlugin atbp = new AutoTableBindPlugin(druidPlugin,
				SimpleNameStyles.LOWER);
		atbp.setShowSql(true);
		me.add(atbp);

		log.info("启用JmslPlugin");
		JmsPlugin jmsPlugin = new JmsPlugin("jms.properties");
		me.add(jmsPlugin);
		
		log.info("EhCachePlugin EhCache缓存");
		me.add(new EhCachePlugin());

		log.info("QuartzPlugin 任务调度开启");
		QuartzPlugin quartzPlugin = new QuartzPlugin("job.properties");
		me.add(quartzPlugin);
	}

	@Override
	// 配置拦截
	public void configInterceptor(Interceptors me) {
		log.info("configInterceptor 未登录拦截");
		me.add(new LogInterceptor());
		// me.add(new TxByRegex("(.*save.*|.*update.*)"));
		// me.add(new TxByMethods("save","update"));
	}

	@Override
	public void configHandler(Handlers me) {
		log.info("开启前端处理器...");
		me.add(new GlobalHandler());
	}

	@Override
	public void afterJFinalStart() {
		log.info("缓存数据");
		new ThreadParamInit().start();
	}

	@Override
	public void beforeJFinalStop() {
		// log.info("清楚数据");
		CacheKit.removeAll(DictKeys.system_cache);
	}
	
	public static void main(String[] args) {
		
		//
		JFinal.start("src/main/webapp", 80, "/",5);
	}

}

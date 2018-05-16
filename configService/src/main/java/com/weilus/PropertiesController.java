package com.weilus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConfigurationProperties("spring.cloud.config.server.native")
public class PropertiesController {
	
	private String searchLocations;
	public String getSearchLocations() {
		return searchLocations;
	}
	public void setSearchLocations(String searchLocations) {
		this.searchLocations = searchLocations;
	}
	
	/**
	 * 配置中心与业务系统解耦  故需单独部署
	 * 新增或修改配置中心的值 
	 * @param application
	 * @param profile
	 * @param lable
	 * @param version
	 * @param map 需要修改的键值对
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value   = {"{application}/{profile}.properties",
					   		   "{application}/{profile}/{lable}.properties",
					   		   "{application}/{profile}/{lable}/{version}.properties"},method = RequestMethod.POST)
	public @ResponseBody Object update(@PathVariable(name="application",required=true) String application,
									   @PathVariable(name="profile",required=true) String profile,
									   @PathVariable(name="lable",required=false) String lable,
									   @PathVariable(name="version",required=false) String version,
									   @RequestBody Map<String,String> map) throws IOException{
		String filename = application+"-"+profile;
		if(!StringUtils.isEmpty(lable))filename = filename+"-"+lable;
		if(!StringUtils.isEmpty(version))filename = filename+"-"+version;
		filename = filename + ".properties";
		File properties_file = new File(searchLocations.substring(5)+File.separatorChar+filename);
		if(!properties_file.exists()){
			properties_file.createNewFile();
		}
		Properties pro = new Properties();
		InputStream in = null;
		OutputStream os = null;
		try {
				in = new BufferedInputStream (new FileInputStream(properties_file));
				pro.load(in);
				pro.putAll(map);
				os = new FileOutputStream(properties_file);
				pro.store(os,"updated the " + filename);
		} catch (IOException e) {
				e.printStackTrace();
		}finally{
				if(null != os)try {os.close();} catch (IOException e) {}
				if(null != in)try {in.close();} catch (IOException e) {}
		}
		return Collections.singletonMap("opretion", "succ");
	}
}

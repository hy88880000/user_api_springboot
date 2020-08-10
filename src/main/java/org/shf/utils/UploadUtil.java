package org.shf.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {
	
	
	/**
	 * 下载
	 * @throws IOException 
	 */
	public static void downLoad(String fileName,String realFileName,HttpServletRequest request,HttpServletResponse response) throws IOException{
		if(StringUtils.isNotBlank(fileName)){
			String realPath = request.getSession().getServletContext().getRealPath("/");
			File f= new File(realPath+fileName);
			if(f.exists()){
				//设置MIME类型
				response.setContentType("application/octet-stream");			
				//或者为response.setContentType("application/x-msdownload");
				
				//设置头信息,设置文件下载时的默认文件名，同时解决中文名乱码问题
				response.addHeader("Content-disposition", "attachment;filename="+new String(realFileName.getBytes(), "ISO-8859-1"));
				
				InputStream inputStream=new FileInputStream(f);
				ServletOutputStream outputStream=response.getOutputStream();
				byte[] bs=new byte[1024];
				while((inputStream.read(bs)>0)){
					outputStream.write(bs);
				}
				outputStream.close();
				inputStream.close();	
			}
			
		}
	}
	
	/**
	 * 上传
	 * @param fileName
	 * @param image
	 * @param request
	 * @return
	 */
	public static Map<String,Object> upload(String fileName, MultipartFile multi, HttpServletRequest request,String fileType) {
		Map<String,Object> map = new HashMap<String,Object>();
		deleteFile(fileName,request);
		String contextPath = request.getSession().getServletContext().getRealPath(fileType);
		File file = new File(contextPath);
		//判断文件夹是否存在
		if(!file.exists()){
			file.mkdir();
		}
		//真实文件名
		String originalFilename = multi.getOriginalFilename();
		//生成32位不重复的字符串
		String uuid = UUID.randomUUID().toString();
		//截取后缀名  1234.56789.jpg lastIndexOf(".")
		String type = originalFilename.substring(originalFilename.lastIndexOf("."));
		//为了防止用户上传的图片名称一样 拼接一个新的名字
		String filePath=uuid+type;
		if("images".equals(fileType)){
			
			map.put("fileName", "images/"+filePath);
		}else{
			//文件大小
			String size = calculateFileSize(multi.getSize());
			map.put("attachmentType", type);
			map.put("attachmentSize", size);
			map.put("attachmentName", originalFilename);
			map.put("attachmentPath", "attachment/"+filePath);
		}
		File f = new File(contextPath+"/"+filePath);
		try {
			//上传图片或文件
			multi.transferTo(f);
			map.put("message", 1);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			map.put("message", 2);
		}
		return map;
		
	}
	

	public static void deleteFile(String fileName, HttpServletRequest request){
		if(StringUtils.isNotBlank(fileName)){
			//获取路径
			String realPath = request.getSession().getServletContext().getRealPath("/");
			File f= new File(realPath+fileName);
			//判断是否存在图片
			if(f.exists()){
				//删除图片
				f.delete();
			}
		}
	}
	
	
	public static String calculateFileSize(long size) {
		DecimalFormat df = new DecimalFormat("0.00");
		if (size < 1024) {
			return size+"B";
		} else if (size < 1024 * 1024) {
			return df.format((double)size / 1024)+"KB";
		} else if (size < 1024 * 1024 * 1024) {
			return df.format((double)size / (1024 * 1024))+"MB";
		} else {
			return df.format((double)size / (1024 * 1024 * 1024))+"GB";
		}
	}
	
	
	


}

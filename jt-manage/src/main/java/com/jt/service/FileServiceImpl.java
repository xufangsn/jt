package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.ImageVo;

@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {
//	定义本地磁盘路径
	@Value("${image.localDirPath}")
	private String localDirPath; 
	//定义虚拟路径名称
	@Value("${image.urlPath}")
	private String urlPath;

	/**
	 * 1.获取图片名称
	 * 2.校验是否为图片类型 jpg/png/gif
	 * 3.校验是否为恶意程序 木马.exe.jpg
	 * 4.准备文件夹 分文件保存 按时间储存 yyyy/MM//dd
	 * 5.防止文件重名 UUID 32位16进制数+毫秒数
	 */
	@Override
	public ImageVo updateFile(MultipartFile uploadFile) {
		ImageVo imageVo = new ImageVo();
		//1.获取图片名称
		String fileName = uploadFile.getOriginalFilename();
		System.out.println(fileName);
		//将字符串转换为小写
		fileName = fileName.toLowerCase();
		//2.校验图片类型 使用正则表达式判断字符串
		if(!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			imageVo.setError(1);
			return imageVo;
		}
		
		//3.判断是否为恶意程序
		try {
			//将文件注入图片模板看 是否可行
			BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
			//获取文件的宽高属性,若无此属性则不为图片
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if(width==0||height==0) {
				imageVo.setError(1);
				return imageVo;
			}
			
			System.out.println(width+"+"+height);
			//4.时间转化为字符串
			String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			
			//5.准备文件夹	E:/smthing/concurrence/image/yyyy/MM/dd
			String localDir = localDirPath + dateDir;
			
			File dirFile = new File(localDir);
			if(!dirFile.exists())
				//如果文件不存在,则创建文件夹
				dirFile.mkdirs();
			
			//6.使用UUID定义文件名称 uuid.jpg
			String uuid = UUID.randomUUID().toString().replace("-","");
			//图片类型 a.jpg 动态获取 ".jpg"
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			
			//拼接新的文件名
			String realLocalPath = localDir+"/"+uuid+fileType;
			
			//7.完成文件上传
			uploadFile.transferTo(new File(realLocalPath));
			
			String realUrlPath = urlPath+dateDir+"/"+uuid+fileType;
			
			imageVo.setError(0).setHeight(height).setWidth(width).setUrl(realUrlPath);
			
		} catch (IOException e) {
			e.printStackTrace();
			imageVo.setError(1);
			return imageVo;
		}
		
		return imageVo;
	}
	
}

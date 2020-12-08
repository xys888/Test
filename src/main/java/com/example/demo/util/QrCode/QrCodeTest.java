package com.example.demo.util.QrCode;

public class QrCodeTest {

	public static void main(String[] args) throws Exception {
		// 存放在二维码中的内容
		String text = "http://39.97.3.46:8889/";
		// 嵌入二维码的图片路径
		String imgPath = "C:/Users/E480/Desktop/1.png";
		// 生成的二维码的路径及名称
		String destPath = "C:/Users/E480/Desktop/t.jpg";
		//生成二维码
		QRCodeUtil.encode(text, imgPath, destPath, true);
		// 解析二维码
		String str = QRCodeUtil.decode(destPath);
		// 打印出解析出的内容
		System.out.println(str);

	}
}
package com.javatechie.qrcodegen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

@SpringBootApplication
public class QRcodeGenerator {
	
	private static  String QRCODE_PATH="C:\\Users\\PANDURANG PATIL\\Desktop\\QRCODE-SERVER\\";
	
	
	public String writeQRCode(PaytmRequestBody paytmRequestBody) throws Exception {
		
		String qrcode=QRCODE_PATH+paytmRequestBody.getUserName()+ "QRCODE.png";
		
		QRCodeWriter writer=new QRCodeWriter();
		BitMatrix bitMatrix=writer.encode(
		paytmRequestBody.getUserName()+"\n"+paytmRequestBody.getAccountNo()+"\n"
		         +paytmRequestBody.getAccountType()+"\n"+paytmRequestBody.getMobileNo(), 
		BarcodeFormat.QR_CODE, 350, 350);
   Path path=FileSystems.getDefault().getPath(qrcode);	
   MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		return "QRCODE is generated successfully.....";
		
	}
	
	
	public String readQRCode(String qrcodeImage) throws IOException, NotFoundException {
		
		BufferedImage  bufferedImage=ImageIO.read(new File(qrcodeImage));
		LuminanceSource luminanceSource=new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap binaryBitmap=new BinaryBitmap(new HybridBinarizer(luminanceSource));
		Result result=new MultiFormatReader().decode(binaryBitmap);
		return result.getText();
		
	}
	

	public static void main(String[] args) throws Exception {
		QRcodeGenerator codeGenerator=new QRcodeGenerator();
	/*	System.out.println(
				codeGenerator.writeQRCode(new PaytmRequestBody("javatechie", "9765718501", "Personal", "acc324690")));*/
		System.out.println(codeGenerator.readQRCode(QRCODE_PATH+"javatechieQRCODE.png"));
	}

}

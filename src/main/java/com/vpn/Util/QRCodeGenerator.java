package com.vpn.Util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class QRCodeGenerator {

    public byte[] generate_QR(String text){
       try {
           int height = 300;
           int width = 300;

           BitMatrix matrix = new MultiFormatWriter()
                   .encode(text, BarcodeFormat.QR_CODE, height, width);

           ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
           MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);

           return outputStream.toByteArray();
       } catch (Exception e) {
           throw new RuntimeException("QR Code generation Failed");
       }
    }
}

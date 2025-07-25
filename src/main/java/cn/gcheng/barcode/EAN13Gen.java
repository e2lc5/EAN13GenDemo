package cn.gcheng.barcode;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.jbarcode.util.ImageUtil;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class EAN13Gen {
    private static String code = "6972036347104";
    private static String codeJbarcode = "697203634710";
    private static String configPath = "D:\\Projects\\IdeaProjects\\EAN13Gen\\src\\main\\resources\\ean13_config.xml";
    private static String outputPath = "D:\\Projects\\IdeaProjects\\EAN13Gen\\src\\main\\java\\cn\\gcheng\\images\\ean13_barcode4j.png";
    private static String outputPathJbarcode = "D:\\Projects\\IdeaProjects\\EAN13Gen\\src\\main\\java\\cn\\gcheng\\images\\ean13_jbarcode.png";
    private static String outputBarbecue = "D:\\Projects\\IdeaProjects\\EAN13Gen\\src\\main\\java\\cn\\gcheng\\images\\ean13_barbecue.png";
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ean13Barcode4j();
        System.out.println("barcode4j cost time: " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        ean13Jbarcode();
        System.out.println("jbarcode4j cost time: " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        ean13Barbecue();
        System.out.println("barbecue cost time: " + (System.currentTimeMillis() - start));

    }

    private static void ean13Barcode4j(){
        try{
            DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
            Configuration cfg = builder.buildFromFile(new File(configPath));
            BarcodeGenerator gen = BarcodeUtil.getInstance().createBarcodeGenerator( cfg );

            OutputStream out = new FileOutputStream(outputPath);
            BitmapCanvasProvider provider = new BitmapCanvasProvider(
                    out, "image/x-png", 300, BufferedImage.TYPE_BYTE_GRAY, true, 0);
            gen.generateBarcode(provider, code);
            provider.finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void ean13Jbarcode(){
        try{
            // check send a 12 bit String
            BarcodeUtil_Jbarcode.createBarcode(codeJbarcode,new File(outputPathJbarcode),"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void ean13Barbecue(){
        try{
            FileOutputStream os = new FileOutputStream(new File(outputBarbecue));
            Barcode barcode = BarcodeFactory.createEAN13(codeJbarcode);
            ImageUtil.encodeAndWrite(BarcodeImageHandler.getImage(barcode), ImageUtil.PNG, os, ImageUtil.DEFAULT_DPI, ImageUtil.DEFAULT_DPI);
            os.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

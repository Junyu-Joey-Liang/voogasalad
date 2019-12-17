package voogasalad.authoring.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.StringConverter;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.data.exception.UnknownDataException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class StorageUtil {
    private static XStream mySerializer = new XStream(new StaxDriver()) {
        @Override
        protected void setupConverters() {
            // converters not done yet
        }
    };

    /**
     * @param authoringFeature
     * @param filePath
     */
    public static void saveAuthoringFeatureToFile(AuthoringFeature authoringFeature, String filePath) {
        mySerializer.registerConverter(new ReflectionConverter(mySerializer.getMapper(), mySerializer.getReflectionProvider()), XStream.PRIORITY_VERY_LOW);
        mySerializer.registerConverter(new IntConverter(), XStream.PRIORITY_NORMAL);
        mySerializer.registerConverter(new StringConverter(), XStream.PRIORITY_NORMAL);
        mySerializer.registerConverter(new CollectionConverter(mySerializer.getMapper()), XStream.PRIORITY_NORMAL);
        try {
            FileWriter writer = new FileWriter(filePath);
            mySerializer.toXML(authoringFeature, writer);
        } catch (IOException e) {
            throw new UnknownDataException(e);
        }

//        try{
//            FileOutputStream fos= new FileOutputStream(filePath);
//            XMLEncoder encoder = new XMLEncoder(fos);
//            encoder.setExceptionListener(new ExceptionListener() {
//                public void exceptionThrown(Exception e) {
//                    e.printStackTrace();
//                    //TODO: error
//                }
//            });
//            encoder.writeObject(authoringFeature);
//            encoder.close();
//            fos.close();
//        }catch (IOException e){
//            //TODO: error
//        }
    }

    /**
     * @param authoringFeature
     * @param e
     */
    public static void saveAuthoringFeatureToFile(AuthoringFeature authoringFeature, ActionEvent e) {
        File file = getDirectoryPrompt(e);
        if (file != null) {
            String filePath = file.getPath() + "/" + authoringFeature.getName() + ".xml";
            saveAuthoringFeatureToFile(authoringFeature, filePath);
        }
    }
//
//    public static AuthoringFeature loadAuthoringFeatureFromFile(ActionEvent e) {
//        File file = getFilePrompt(e);
//        if (file == null) {
//            return null;
//        }
//        return loadAuthoringFeatureFromFile(file.getPath());
//    }

//    public static AuthoringFeature loadAuthoringFeatureFromFile(String filepath) {
//        try {
//            String mySavedStorage = new Scanner(new File(filepath)).useDelimiter("\\Z").next();
//            return (AuthoringFeature) mySerializer.fromXML(mySavedStorage);
//        } catch (FileNotFoundException e) {
//            throw new UnknownDataException(e);
//        }
//    }


//    public static void saveToFile(File sourceFile, File destinationFile) throws FileNotFoundException, IOException {
//        FileInputStream fileInputStream = new FileInputStream(sourceFile);
//        FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
//
//        int bufferSize;
//        byte[] bufffer = new byte[512];
//        while ((bufferSize = fileInputStream.read(bufffer)) > 0) {
//            fileOutputStream.write(bufffer, 0, bufferSize);
//        }
//        fileInputStream.close();
//        fileOutputStream.close();
//    }

    /**
     * @param e
     * @return
     */
    public static File getFilePrompt(ActionEvent e) {
        Node source = (Node) e.getSource();
        Window stage = source.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }

    /**
     * @param e
     * @return
     */
    public static File getDirectoryPrompt(ActionEvent e) {
        Node source = (Node) e.getSource();
        Window stage = source.getScene().getWindow();
        DirectoryChooser fileChooser = new DirectoryChooser();
        File file = fileChooser.showDialog(stage);
//        if (file == null) return null;
        return file;
    }
}

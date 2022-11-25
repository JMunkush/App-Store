package kz.springboot.springbootdemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ManifestReader {

    public static void main(String[] args) throws IOException {
        Manifest manifest = new Manifest();

        // Не забудьте положить файл манифеста по указанному пути!
        try (InputStream inputStream = new FileInputStream("D:\\3-cource\\Java EE\\Another\\springbootdemo\\springbootdemo\\.mvn\\wrapper\\META-INF")) {
            manifest.read(inputStream);
        }

        Attributes attributes = manifest.getMainAttributes();
        System.out.println("Built-By: " + attributes.getValue("Built-By"));
        System.out.println("Bundle-Name: " + attributes.getValue("Bundle-Name"));

        System.out.println("==========");
        System.out.println("All entries:");
        attributes.forEach((key, value) -> {
            System.out.println(key + ": " + value);
        } );
    }

}
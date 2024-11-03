package org.example;

import org.example.reflection.HttpController;

import java.util.Arrays;

/**
 * Hello world!
 *
 */

public class App 
{
    public static void main( String[] args )
    {
        System.out.println(Arrays.toString(Package.getPackages() ));
        System.out.println( "Hello World!" );

        HttpController httpController = new HttpController();
        Test test = new Test();
        httpController.generateUrls(Test.class, test);
    }
}

package org.example;

import org.example.reflection.HttpController;
import org.example.reflection.HttpControllers;

/**
 * Hello world!
 *
 */

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        HttpControllers httpController = new HttpControllers();
        Test test = new Test();
        httpController.generateUrls(Test.class, test);
    }
}

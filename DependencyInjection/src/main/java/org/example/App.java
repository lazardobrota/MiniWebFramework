package org.example;

import org.example.logger.Test;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //DIEngine DIEngine = org.example.DIEngine.getInjector(new DependencyContainer());
        DIEngine diEngine = new DIEngine();
        Test test = diEngine.inject(Test.class);
        Test test2 = diEngine.inject(Test.class);
        test.log();
        test2.log();
        test2.log();
    }
}

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
        DIEngine DIEngine = org.example.DIEngine.getInjector(new DependencyContainer2());

        Test test = DIEngine.inject(Test.class);
        Test test2 = DIEngine.inject(Test.class);
        test.log();
        test2.log();
        test2.log();
    }
}

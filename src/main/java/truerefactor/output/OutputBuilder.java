/**
 * 
 */
package truerefactor.output;

import java.io.File;

/**
 * @author Isaac
 *
 */
public abstract class OutputBuilder {

    /**
     * 
     */
    public abstract void createClasses();
    
    /**
     * 
     */
    public abstract void createAttributes();
    
    /**
     * 
     */
    public abstract void createMethods();
    
    /**
     * 
     */
    public abstract void createOutputFiles();
    
    /**
     * 
     * @return
     */
    public abstract File getResult();
}

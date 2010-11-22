/**
 * 
 */
package truerefactor;

import truerefactor.error.SystemControllerException;
import truerefactor.input.CPPGraphParser;
import truerefactor.input.CodeGraphBuilder;
import truerefactor.input.JavaGraphParser;
import truerefactor.output.OutputDirector;
import truerefactor.refactor.RefactoringController;

/**
 * @author Isaac
 */
public class SystemController {

    /**
     * 
     */
    private CodeGraphBuilder graphBuilder;
    /**
     * 
     */
    private RefactoringController controller;
    /**
     * 
     */
    private OutputDirector outputDirector;
    /**
     *     
     */
    private String logFileLoc;
    /**
     * 
     */
    private String umlOutLoc;
    /**
     * 
     */
    private String codeOutLoc;
    /**
     * 
     */
    private String codeInLoc;
    /**
     * 
     */
    private String language;

    /**
     * 
     */
    public SystemController()
    {
        logFileLoc = null;
        umlOutLoc = null;
        codeOutLoc = null;
        codeInLoc = null;
        language = null;
    }

    /**
     * 
     */
    private void selectParser()
    {
        if (language == null)
        {
            try
            {
                deriveLanguage();
            }
            catch (SystemControllerException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (language.equalsIgnoreCase(JavaGraphParser.LANGUAGE))
        {
            graphBuilder = new CodeGraphBuilder(new JavaGraphParser());
        }
        else if (language.equalsIgnoreCase(CPPGraphParser.LANGUAGE))
        {
            graphBuilder = new CodeGraphBuilder(new CPPGraphParser());
        }
    }

    /**
     * 
     * @throws SystemControllerException
     */
    private void deriveLanguage() throws SystemControllerException
    {
        // look at files in directory and determine based on
        // file extensions what language is being employed

        // if no language is assigned, because of a bad directory
        // location or file problems, then throw an exception
        if (language == null)
        {
            throw new SystemControllerException(
                    "A Lanaguage was not provided, and one could not be derived. Processing therefore has failed.");
        }
    }

    /**
     * @return the outputDirector
     */
    public OutputDirector getOutputDirector()
    {
        return outputDirector;
    }

    /**
     * @param outputDirector the outputDirector to set
     */
    public void setOutputDirector(OutputDirector outputDirector)
    {
        this.outputDirector = outputDirector;
    }

    /**
     * @return the logFileLoc
     */
    public String getLogFileLoc()
    {
        return logFileLoc;
    }

    /**
     * @param logFileLoc the logFileLoc to set
     */
    public void setLogFileLoc(String logFileLoc)
    {
        this.logFileLoc = logFileLoc;
    }

    /**
     * @return the umlOutLoc
     */
    public String getUmlOutLoc()
    {
        return umlOutLoc;
    }

    /**
     * @param umlOutLoc the umlOutLoc to set
     */
    public void setUmlOutLoc(String umlOutLoc)
    {
        this.umlOutLoc = umlOutLoc;
    }

    /**
     * @return the codeOutLoc
     */
    public String getCodeOutLoc()
    {
        return codeOutLoc;
    }

    /**
     * @param codeOutLoc the codeOutLoc to set
     */
    public void setCodeOutLoc(String codeOutLoc)
    {
        this.codeOutLoc = codeOutLoc;
    }

    /**
     * @return the codeInLoc
     */
    public String getCodeInLoc()
    {
        return codeInLoc;
    }

    /**
     * @param codeInLoc the codeInLoc to set
     */
    public void setCodeInLoc(String codeInLoc)
    {
        this.codeInLoc = codeInLoc;
    }

    /**
     * @return the language
     */
    public String getLanguage()
    {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language)
    {
        this.language = language;
    }
}

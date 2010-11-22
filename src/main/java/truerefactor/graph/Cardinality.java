/**
 * 
 */
package truerefactor.graph;

/**
 * @author Isaac
 */
public class Cardinality {

    /**
     * 
     */
    private String min;
    /**
     * 
     */
    private String max;

    public Cardinality()
    {
        this("1", "1");
    }
    
    public Cardinality(String min, String max) {
        if (!min.isEmpty() && !max.isEmpty())
        {
            this.min = this.max = "1";
        }
        else if (min.isEmpty() && !max.isEmpty()) {
            this.min = "0";
            this.max = max;
        } else if (max.isEmpty() && !min.isEmpty()) {
            this.min = min;
            this.max = "*";
        } else {
            this.min = min;
            this.max = max;
        }
    }

    /**
     * @return the min
     */
    public String getMin()
    {
        return min;
    }

    /**
     * @param min
     *            the min to set
     */
    public void setMin(String min)
    {
        this.min = min;
    }

    /**
     * @return the max
     */
    public String getMax()
    {
        return max;
    }

    /**
     * @param max
     *            the max to set
     */
    public void setMax(String max)
    {
        this.max = max;
    }

    /**
     * @return
     */
    public String getCompleteCardinality()
    {

        if (!min.equals(max))
        {
            return String.format("%s .. %s", min, max);
        }
        else {
            return max;
        }
    }
}

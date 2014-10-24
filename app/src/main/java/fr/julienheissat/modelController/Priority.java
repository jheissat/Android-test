
package fr.julienheissat.modelController;

/**
 * Created by juju on 15/09/2014.
 */
public class Priority
{

    private Long priorityId;
    private String priorityName;
    private String priorityDescription;
    private String priorityRessource;


    public Priority(String name)
    {
        this.priorityName = name;
    }

    public Priority()
    {
    }

    @Override
    public String toString()
    {
        return priorityName;
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getPriorityDescription() {
        return priorityDescription;
    }

    public void setPriorityDescription(String priorityDescription) {
        this.priorityDescription = priorityDescription;
    }

    public String getPriorityRessource() {
        return priorityRessource;
    }

    public void setPriorityRessource(String priorityRessource) {
        this.priorityRessource = priorityRessource;
    }

}
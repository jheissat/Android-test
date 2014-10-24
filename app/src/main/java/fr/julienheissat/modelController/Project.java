
package fr.julienheissat.modelController;

/**
 * Created by juju on 15/09/2014.
 */
public class Project
{

    private Long projectID;
    private String projectName;
    private String projectColor;
    private String projectDescription;
    // protected List<Users> ProjectUsers;

    public Project(String name)
    {
        this.projectName = projectName;
    }

    public Project()
    {
    }

    @Override
    public String toString()
    {
        return projectName;
    }

    public Long getProjectID()
    {
        return projectID;
    }

    public void setProjectID(Long projectID)
    {
        this.projectID = projectID;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getProjectColor()
    {
        return projectColor;
    }

    public void setProjectColor(String projectColor)
    {
        this.projectColor = projectColor;
    }

    public String getProjectDescription()
    {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription)
    {
        this.projectDescription = projectDescription;
    }
}
package model;

public class Hall {
	
	private int id;
	private String name;
	private ProjectType projectType;
	
	public Hall() {};
	
	public Hall(int id, String name, ProjectType projectType) {
		this.id = id;
		this.name = name;
		this.projectType = projectType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}
	
}

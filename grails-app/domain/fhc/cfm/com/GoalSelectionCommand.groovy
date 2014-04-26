package fhc.cfm.com

class GoalSelectionCommand implements Serializable{
	List<Item> items = []
    static constraints = {
    }
}

class Item implements Serializable{
	String name
	String value
	boolean isSelected
}

/***
 * This class represents a Mesonet Weather station and holds the four character station ID.
 * @author Sierra Jo Sallee
 * @version 03/30/2019
 */
public class MesoStation {

	// The String station ID 
	private String StID;
	
	/**
	 * This constructor method initializes the station and the station ID.
	 * @param StID The four character string representing the station ID.
	 */
	public MesoStation(String StID)
	{
		this.StID = StID;
	}

	/**
	 * This accessor method return the station ID.
	 * @return Returns the four character string representing the station ID.
	 */
	public String getStID() {
		return StID;
	}

}

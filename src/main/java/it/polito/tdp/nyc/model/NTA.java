package it.polito.tdp.nyc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nyc.db.DBConnect;

public class NTA implements Comparable<NTA> {
	private String ntaCode; 
	private Set<String> ssidSet; 
	
	public NTA(String ntaCode, Set<String> ssidSet) {
		super();
		this.ntaCode = ntaCode;
		this.ssidSet = ssidSet;
	}

	public String getNtaCode() {
		return ntaCode;
	}

	public void setNtaCode(String ntaCode) {
		this.ntaCode = ntaCode;
	}

	public Set<String> getSsidSet() {
		return ssidSet;
	}

	public void setSsidSet(Set<String> ssidSet) {
		this.ssidSet = ssidSet;
	}

	@Override
	public int compareTo(NTA o) {
		return -(this.ssidSet.size()-o.getSsidSet().size());
	} 
	
	
	
	

}

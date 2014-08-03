package com.tanzoft.habarihub.datamodels;

public class NewsSource {

	/*
	 * A data model for each news source*/
	private int _id;
	private String displayName;
	private String url;
	
	
	public NewsSource() {
	}
	
	public NewsSource(String displayName,String url) throws Exception{
		
		if(displayName.equals("")||url.equals("")){
			throw new Exception();
		}else{
		this.displayName=displayName;
		this.url=url;
		}
	}
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	
	

}

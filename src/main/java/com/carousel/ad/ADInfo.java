package com.carousel.ad;

/**
 * 描述：广告信息</br>
 */
public class ADInfo {
	
	String id = "";
	String url = "";
	String content = "";
	String type = "";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	String title;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	String date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

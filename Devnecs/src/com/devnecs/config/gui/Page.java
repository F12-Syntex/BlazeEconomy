package com.devnecs.config.gui;

public class Page {
	
	private int page;
	private boolean allPaged;
	
	public Page(int page, boolean allPages) {
		this.page = page;
		this.allPaged = allPages;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public boolean isAllPaged() {
		return allPaged;
	}

	public void setAllPaged(boolean allPaged) {
		this.allPaged = allPaged;
	}

	public static Page GET_ALL_PAGES() {
		return new Page(0, true);
	}
	
}

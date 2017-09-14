package com.ddebbie.pagination;
import java.util.ArrayList;
import java.util.List;

import com.ddebbie.service.descriptors.BaseView;
import com.fasterxml.jackson.annotation.JsonView;

/** * @author RAMMOHAN * */
public class Paginator<T> {
	
	@JsonView(BaseView.class)
	private List<T> list;
	
	@JsonView(BaseView.class)
	private long count;

	public Paginator() {
	}

	public Paginator(List<T> list, long count) {
		this.list = (list == null) ? new ArrayList<T>() : list;
		this.count = count;
	}

	public Paginator(Paginator<T> paginator) {
		this.list = (paginator.list == null) ? new ArrayList<T>()
				: paginator.list;
		this.count = paginator.count;
	}

	/** * @return the list */
	public List<T> getList() {
		return list;
	}

	/** * @param list * the list to set */
	public void setList(List<T> list) {
		this.list = list;
	}

	/** * @return the total count */
	public long getCount() {
		return count;
	}

	/** * @param totalcount * the total count to set */
	public void setCount(long count) {
		this.count = count;
	}
}

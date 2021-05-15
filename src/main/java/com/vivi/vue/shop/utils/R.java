package com.vivi.vue.shop.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
public class R<T> {
	private static final long serialVersionUID = 1L;

	private T data;

	private Map<String, String> meta = new HashMap<>();

	public static R create(String status, String msg) {
		R r = new R();
		r.getMeta().put("status", status);
		r.getMeta().put("msg", msg);
		return r;
	}
	
	public R() {
	}

	public static R ok() {
		return create("200", "操作成功");
	}


	public static R error(String status, String msg) {
		return create(status, msg);
	}

	public R setMsg(String msg) {
		this.getMeta().put("msg", msg);
		return this;
	}

	public R setData(T data) {
		this.data = data;
		return this;
	}

	public T getData() {
		return data;
	}

	public Map<String, String> getMeta() {
		return meta;
	}
}

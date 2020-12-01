package com.zqkj.exception;

import com.zqkj.utils.R;

/**
 * 自定义异常
 * 
 */
public class ValidataException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private R r;
    
    public ValidataException(R r) {
		super((String) r.get(R.MSG_KEY));
		this.r = r;
	}
	
	public ValidataException(R r, Throwable e) {
		super((String) r.get(R.MSG_KEY), e);
		this.r = r;
	}

	public R getR() {
		return r;
	}

	public void setR(R r) {
		this.r = r;
	}
	
}

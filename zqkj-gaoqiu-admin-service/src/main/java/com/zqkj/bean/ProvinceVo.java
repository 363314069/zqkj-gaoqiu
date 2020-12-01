package com.zqkj.bean;

import java.util.List;

public class ProvinceVo {

	private String name;
	private List<CityVo> city;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CityVo> getCity() {
		return city;
	}
	public void setCity(List<CityVo> city) {
		this.city = city;
	}
}

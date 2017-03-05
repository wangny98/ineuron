package com.ineuron.domain.nlp.valueobject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductSelection {
	// 产品
	private String productName;
	// 颜色
	private Set<String> colors;
	// 形态
	private Set<String> forms;
	// 功能
	private Set<String> functions;
	// 用途
	private Set<String> scopes;
	// 品质
	private Set<String> qualities;

	// 时间
	private List<String> date;

	// 数量
	private List<String> quantity;

	// 其他属性
	private Set<String> otherAttributes;

	public String toString() {

		StringBuilder ps = new StringBuilder();
		ps.append("productName = ").append(productName).append("\n").append("color = ");

		if (colors != null) {
			for (String color : colors) {
				ps.append(color).append(",");
			}
		}

		ps.append("\n").append("form = ");
		if (forms != null) {
			for (String form : forms) {
				ps.append(form).append(",");
			}
		}

		ps.append("\n").append("scope = ");
		if (scopes != null) {
			for (String scope : scopes) {
				ps.append(scope).append(",");
			}
		}

		ps.append("\n").append("function = ");
		if (functions != null) {
			for (String function : functions) {
				ps.append(function).append(",");
			}
		}

		ps.append("\n").append("date = ");
		if (date != null) {
			for (String d : date) {
				ps.append(d).append(",");
			}
		}

		ps.append("\n").append("quantity = ");
		if (quantity != null) {
			for (String q : quantity) {
				ps.append(q).append(",");
			}
		}

		ps.append("\n").append("quality = ");
		if (qualities != null) {
			for (String quality : qualities) {
				ps.append(quality).append(",");
			}
		}

		ps.append("\n").append("otherAttributes = ");

		if (otherAttributes != null) {
			for (String attr : otherAttributes) {
				ps.append(attr).append(",");
			}
		}

		return ps.toString();

	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Set<String> getColors() {
		return colors;
	}

	public void addColor(String color) {
		if (colors == null) {
			colors = new HashSet<String>();
		}
		colors.add(color);
	}

	public Set<String> getForms() {
		return forms;
	}

	public void addForm(String form) {
		if (forms == null) {
			forms = new HashSet<String>();
		}
		forms.add(form);
	}

	public Set<String> getFunctions() {
		return functions;
	}

	public void addFunction(String function) {
		if (functions == null) {
			functions = new HashSet<String>();
		}
		functions.add(function);
	}

	public Set<String> getScopes() {
		return scopes;
	}

	public void addScope(String scope) {
		if (scopes == null) {
			scopes = new HashSet<String>();
		}
		scopes.add(scope);
	}

	public Set<String> getQualities() {
		return qualities;
	}

	public void addQuality(String quality) {
		if (qualities == null) {
			qualities = new HashSet<String>();
		}
		qualities.add(quality);
	}

	public List<String> getDate() {
		return date;
	}

	public void addDateElement(String element) {
		if (date == null) {
			date = new ArrayList<String>();
		}
		date.add(element);
	}

	public List<String> getQuantity() {
		return quantity;
	}

	public void addQuantityElement(String element) {
		if (quantity == null) {
			quantity = new ArrayList<String>();
		}
		quantity.add(element);
	}

	public Set<String> getOtherAttributes() {
		return otherAttributes;
	}

	public void setOtherAttributes(Set<String> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}

}

/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package bros.common.core.validation.library.impl;

import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.common.core.validation.library.ValidationLibrary;
import java.io.Serializable;
import java.util.Map;

public class IsPositiveInt implements ValidationLibrary, Serializable {
	private static final long serialVersionUID = -7202112084705975056L;

	public boolean validator(String str, Map<String, Object> attrMap)
			throws ServiceException {
		String pattarn = "^[0-9]*[1-9][0-9]*$";
		return (!(ValidateUtil.regexPattern(str, pattarn)));
	}
}
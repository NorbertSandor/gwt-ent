/*
 * GwtEnt - Gwt ent library.
 * 
 * Copyright (c) 2007, James Luo(JamesLuo.au@gmail.com)
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.gwtent.reflection;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtent.client.reflection.AccessDef;
import com.gwtent.client.reflection.TypeOracle;
import com.gwtent.reflection.accessadapter.JFeildAdapter;
import com.gwtent.reflection.accessadapter.JMethodAdapter;

public class GeneratorHelper {
	public static int AccessDefToInt(AccessDef accessDef){
		int result = 0;
		
		if (accessDef.isFinal()) result += TypeOracle.MOD_FINAL;
		if (accessDef.isPrivate()) result += TypeOracle.MOD_PRIVATE;
		if (accessDef.isProtected()) result += TypeOracle.MOD_PROTECTED;
		if (accessDef.isPublic()) result += TypeOracle.MOD_PUBLIC;
		if (accessDef.isStatic()) result += TypeOracle.MOD_STATIC;
		
		return result;
	}
	
	public static int AccessDefToInt(JField field){
		JFeildAdapter adapter = new JFeildAdapter(field);
		return AccessDefToInt(adapter);
	}
	
	public static int AccessDefToInt(JMethod method){
		JMethodAdapter adapter = new JMethodAdapter(method);
		return AccessDefToInt(adapter);
	}
	
	
	
	public static String stringArrayToCode(String[] strs){
		StringBuilder result = new StringBuilder("new String[]{");
		
		for (int i = 0; i < strs.length; i++){
			result.append("\"" + processInvertedComma(strs[i]) + "\"");
			
			if (i < (strs.length - 1)) result.append(", ");
		}
		
		result.append("}");
		
		return result.toString();
	}
	
	public static String getUnitName(JClassType classType){
		return classType.getParameterizedQualifiedSourceName()
			+ "Wrapper";
	}
	
	public static String getSimpleUnitName(JClassType classType){
		return classType.getSimpleSourceName() + "Wrapper";
	}
	
	/**
	 * generator metaData
	 * @param dest field or method or class
	 * @param source source to print code
	 * @param metaData 
	 */
	public static void addMetaDatas(String dest, SourceWriter source, com.google.gwt.core.ext.typeinfo.HasMetaData metaData) {
		String[] tags = metaData.getMetaDataTags();
		for (int j = 0; j < tags.length; j++){
			String[][] metas = metaData.getMetaData(tags[j]);
			for (int k = 0; k < metas.length; k++){
				source.println(dest + ".addMetaData(\"" + tags[j] + "\", " + GeneratorHelper.stringArrayToCode(metas[k]) +  ");");
			}
			
		}
	}
	
	public static String processInvertedComma(String str){
//		return str.replaceAll("\"", "\\\"");
		StringBuffer sb = new StringBuffer();
		int length = str.length();
		for (int i = 0; i < length; i++){
			//not first char and last char  (i != 0) && (i != (length - 1)) && 
			if ((str.charAt(i) == '"')){
				sb.append("\\\"");
			}else{
				sb.append(str.charAt(i));
			}
		}
		return sb.toString();
		
	}
	
	
	
}